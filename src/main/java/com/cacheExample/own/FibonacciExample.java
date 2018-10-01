package com.cacheExample.own;

import java.util.concurrent.Callable;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class FibonacciExample {
	private static final Logger LOGGER = LoggerFactory.getLogger(FibonacciExample.class);
	private final GenericCacheExample<Long, Long> cache = new GenericCacheExample<>();
	
	/**
	 * constructor to warm the cache with initial 2 values
	 */
	public FibonacciExample() {
		cache.setValueIfAbsent(0L, 1L);
		cache.setValueIfAbsent(1L, 1L);
	}
	
	public void FibonacciMain() throws Exception{
		long index = 12;
		final long fn = getNumber(index); //get the 12th fibonacci no
		LOGGER.info("The {}th Fibonacci number is: {}", index, fn);
	}
	
	public long getNumber(long index) throws Exception{
		return cache.getValue(index, new Callable<Long>() {
			@Override
			public Long call() throws Exception{
				LOGGER.info("Computing the {} Fibonacci number", index);
				return getNumber(index-1) + getNumber(index-2);
			}
		});
	}
	
}
