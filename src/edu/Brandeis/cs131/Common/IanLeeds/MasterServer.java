package edu.Brandeis.cs131.Common.IanLeeds;

import java.util.HashMap;
import java.util.LinkedList;

import edu.Brandeis.cs131.Common.Abstract.Client;
import edu.Brandeis.cs131.Common.Abstract.Log.Log;
import edu.Brandeis.cs131.Common.Abstract.Server;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class MasterServer extends Server {

    private final Map<Integer, List<Client>> mapQueues = new HashMap<Integer, List<Client>>();
    private final Map<Integer, Server> mapServers = new HashMap<Integer, Server>();
    private final Lock lock = new ReentrantLock();
    private final Condition notFirst  = lock.newCondition();
    private final Condition noConnect  = lock.newCondition();

    public MasterServer(String name, Collection<Server> servers, Log log) {
        super(name, log);
        Iterator<Server> iter = servers.iterator();
        while (iter.hasNext()) {
            this.addServer(iter.next());
        }
    }

    public void addServer(Server server) {
        int location = mapQueues.size();
        this.mapServers.put(location, server);
        this.mapQueues.put(location, new LinkedList<Client>());
    }

    @Override
    public boolean connectInner(Client client) {
        //prevent race conditions
    	lock.lock();
        int assigned=this.getKey(client);
        List<Client> assignedQueue=mapQueues.get(assigned);
        Server assignedServer=mapServers.get(assigned);
        //client added to queue of assigned server
        assignedQueue.add(client);
        //while the client is not first in its queue, releases lock and waits to be first
        while (assignedQueue.indexOf(client)!=0){
        	try {
				notFirst.await();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
        }
        //while client cannot connect to assigned server, releases lock and waits to be allowed to connect
        while (!assignedServer.connect(client)){
        	try {
				noConnect.await();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
        }
        //removes itself from queue, signals other threads that may be waiting to be first, and lock unlocks
        assignedQueue.remove(client);
        notFirst.signalAll();
        lock.unlock();
        return true;
    }

    @Override
    public void disconnectInner(Client client) {
    	//prevent race condition
    	lock.lock();
        int assigned=this.getKey(client);
        Server assignedServer=mapServers.get(assigned);
        //disconnects from assigned server
        assignedServer.disconnect(client);
        //signals other threads that may be waiting to be allowed to connect, lock unlocks
        noConnect.signalAll();
        lock.unlock();
    }

	//returns a number from 0- mapServers.size -1
    // MUST be used when calling get() on mapServers or mapQueues
    private int getKey(Client client) {
        return client.getSpeed() % mapServers.size();
    }
}
