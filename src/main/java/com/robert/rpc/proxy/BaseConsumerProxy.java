package com.robert.rpc.proxy;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicLong;

import org.jboss.netty.channel.ChannelFuture;


import com.robert.rpc.client.ClientFactory;
import com.robert.rpc.common.Parameters;
import com.robert.rpc.common.Result;
import com.robert.rpc.compress.Compressor;
import com.robert.rpc.serialize.SerializeException;
import com.robert.rpc.serialize.Serializer;
import com.robert.rpc.util.IdGenerator;

public abstract class BaseConsumerProxy {

	private static final AtomicLong count = new AtomicLong(0);

	private ThreadLocal<ChannelFuture> channelFutureLocal;

	static {
		Thread thread = new Thread(new Runnable() {

			public void run() {
				while (true) {
					System.out.println(count.get());
					try {
						Thread.sleep(1000);
					} catch (InterruptedException e) {
					}
				}
			}

		});
		// thread.start();
	}

	public BaseConsumerProxy(final String remoteAddress) {
		channelFutureLocal = new ThreadLocal<ChannelFuture>() {

			@Override
			protected ChannelFuture initialValue() {
				return ClientFactory.getClient().getConnection(remoteAddress,
						8001);
			}
		};
	}

	protected Object doInterval(String interfactName, Object[] objs) {
		List<Class<?>> clazzs = new ArrayList<Class<?>>(objs.length);
		List<Object> params = new ArrayList<Object>();
		for (Object obj : objs) {
			clazzs.add(obj.getClass());
			params.add(obj);
		}

		Parameters parameters = new Parameters();
		parameters.setInterfaceName(interfactName);
		parameters.setParameterTypes(clazzs);
		parameters.setParameters(params);

		while (!channelFutureLocal.get().getChannel().isConnected())
			;
		try {
			byte[] data = Serializer.serialize(parameters);

			channelFutureLocal.get().getChannel().write(data);
			synchronized (channelFutureLocal.get().getChannel()) {
				channelFutureLocal.get().getChannel().wait();
			}
			data = ClientFactory.getClient().getResult(
					channelFutureLocal.get().getChannel());
			Result result = Serializer.deserializer(data, Result.class);

			if (!result.isSuccess()) {
				System.out.println("");
			}

			return result.getResult();
		} catch (SerializeException e) {
			return null;
		} catch (InterruptedException e) {
			return null;
		}
	}

}