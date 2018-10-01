package com.cacheExample.own;

/**
 * The main class for the cache example. 
 * Here we use the cache in the same way we would use DP for fibonacci series
 * Our cache is built on top of a concurrent hash map
 */
public class cacheExampleMain {
	public void cacheExampleMainFunc() {
		System.out.println("Starting own cache implementation over a hashmap");
		FibonacciExample example = new FibonacciExample();
		try {
			example.FibonacciMain();
		} catch (Exception e) {}
	}
}
