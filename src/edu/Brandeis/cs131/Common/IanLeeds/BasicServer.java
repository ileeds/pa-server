package edu.Brandeis.cs131.Common.IanLeeds;

import java.util.ArrayList;

import edu.Brandeis.cs131.Common.Abstract.Client;
import edu.Brandeis.cs131.Common.Abstract.Server;

public class BasicServer extends Server {

	final ArrayList <Client> accessing = new ArrayList<Client> ();
	
	public BasicServer(String name) {
		super(name);
	}

	@Override
	public boolean connectInner(Client client) {
		//if no accessing clients
		if (accessing.isEmpty()){
			accessing.add(client);
			return true;
		//if only one client already accessing, this client is a shared client, and they don't have shared industry
		}else if (accessing.size()==1 && client instanceof SharedClient && !accessing.get(0).getIndustry().equals(client.getIndustry())){
			accessing.add(client);
			return true;
		}
		//otherwise cannot access
		return false;
	}

	@Override
	public void disconnectInner(Client client) {
		//releases lock and removes client from list of accessing clients
		accessing.remove(client);
	}
}
