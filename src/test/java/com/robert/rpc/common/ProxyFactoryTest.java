package com.robert.rpc.common;

import org.junit.Test;

import com.robert.rpc.proxy.ProxyFactory;

public class ProxyFactoryTest {

	@Test
	public void testGetProxy(){
		ProxyFactoryTest.MyTestInterface myTestInterface = (ProxyFactoryTest.MyTestInterface)ProxyFactory.getConsumerProxy(ProxyFactoryTest.MyTestInterface.class, "127.0.0.1");
		myTestInterface.method();
	}
	
	public static interface MyTestInterface{
		
		public void method();
		
	}
}