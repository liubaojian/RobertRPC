package com.robert.rpc.server;

public class ServerFactory {
	
	private static Server server;
	
	static{
		server = new Server(8001, 10);
		server.start();
	}
	
	public static Server getServer(){
		return server;
	}

}