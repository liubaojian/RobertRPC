package com.robert.rpc.compress;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;


import org.iq80.snappy.Snappy;


public class Compressor {

	private static ThreadLocal<byte[]> dataThreadLocal;
	
	static {
		dataThreadLocal = new ThreadLocal<byte[]>() {
			protected byte[] initialValue() {
				return new byte[Snappy.maxCompressedLength(5 * 1024)];
			}
		};
	}

	public static byte[] compress(byte[] data) {
		if (data == null || data.length == 0) {
			return null;
		}
		byte[] compressedData = dataThreadLocal.get();
		if (compressedData.length < Snappy.maxCompressedLength(data.length)) {
			compressedData = new byte[Snappy.maxCompressedLength(data.length)];
		} else {
			Arrays.fill(compressedData, (byte)0);
		}
		int compressSize = Snappy.compress(data, 0, data.length,
				compressedData, 0);
		return Arrays.copyOf(compressedData, compressSize);
	}

	public static byte[] decompress(byte[] compressedData) {
		if (compressedData == null || compressedData.length == 0) {
			return null;
		}
		return Snappy.uncompress(compressedData, 0, compressedData.length);
	}	
}