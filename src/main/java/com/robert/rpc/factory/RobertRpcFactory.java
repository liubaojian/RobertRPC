package com.robert.rpc.factory;


import com.robert.rpc.proxy.ProxyFactory;
import com.robert.rpc.server.ServerFactory;


/*public class RobertRpcFactory implements RpcFactory {
	
	@Override
	public <T> void export(Class<T> type, T serviceObject) {
		ServerFactory.getServer().register(type.getName(), serviceObject);
	}

	@SuppressWarnings("unchecked")
	@Override
	public <T> T getReference(Class<T> type, String ip) {
		return (T)ProxyFactory.getConsumerProxy(type, ip);
	}

	@Override
	public int getClientThreads() {
		return 80;
	}

	@Override
	public String getAuthorId() {
		return "mayou.lyt";
	}

}*/