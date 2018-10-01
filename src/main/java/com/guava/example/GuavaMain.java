package com.guava.example;

import java.util.concurrent.ExecutionException;

import com.google.common.cache.LoadingCache;

/**
 *	An example for using guava cache, here we use the cache to store the cube of a number
 */
public class GuavaMain {
	
	public void guavaMainFunc() {
		System.out.println("Checking guava cache:");
		for(int i=0; i<5; i++)
			try {
				System.out.println(getCubeGuava(i));
			} catch (ExecutionException e) {
				e.printStackTrace();
			}
		
		for(int i=0; i<10; i++) {
			try {
				Thread.sleep(2000);
				//long t1 = System.nanoTime();
				System.out.println(getCubeGuava(3));
				//System.out.println("Time taken = " + (System.nanoTime()-t1));
			} catch (InterruptedException | ExecutionException e) {
				e.printStackTrace();
			}
		}
	}
	
	private int getCubeGuava(int no) throws ExecutionException {
		LoadingCache<Integer, Integer> cubeCache = EmployeeGuavaCacheUtil.getLoadingCache();
		return cubeCache.get(no);
	}
}
