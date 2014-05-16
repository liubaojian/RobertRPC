package com.robert.rpc.server;


public class ServerBootstrap {

	public static void main(String[] args) {
		Server server = ServerFactory.getServer();
		//server.register("me.mayou.rpc.example.TestRpc", new TestRpcImpl());
	}

}