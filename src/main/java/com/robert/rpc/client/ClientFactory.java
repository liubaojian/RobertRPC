package com.robert.rpc.client;


public class ClientFactory {

	private static Client client = new Client(8001, "127.0.0.1");
	
	static{
		client = new Client(8001, "127.0.0.1");
		client.start();
	}

	public static Client getClient() {
		return client;
	}

}