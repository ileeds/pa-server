# pa-server_part1

The ConcreteFactory returns instances of my fully implemented BasicServer, SharedClient, and BasicClient. BasicClient and SharedClient both utilize the parent class MyClient's constructor, and have separate toString methods to display whether they are Basic or Shared (along with industry and name). MyClient's constructor instantiates a Client with the passed in parameters label and industry, a random speed from 0-9, and a requestLevel of 3.

In BasicServer, a Client is allowed to connectInner if the list of accessing Clients is empty. Otherwise, it can also connectInner if it is a SharedClient, there is only one other SharedCleint in the list of accessing Clients, and these two SharedClients are not of the same industry. When a Client calls disconnectInner, it is removed from the list of accessing Clients.
