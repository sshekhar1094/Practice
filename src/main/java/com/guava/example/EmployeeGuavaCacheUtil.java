package com.guava.example;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import com.google.common.cache.CacheBuilder;
import com.google.common.cache.CacheLoader;
import com.google.common.cache.LoadingCache;

public class EmployeeGuavaCacheUtil {
	
	// here we use the cache to store the cube of a number
	private static LoadingCache<Integer, Integer> cubeCache;
	
	static {
		// this is our cache
		cubeCache = CacheBuilder.newBuilder()											//cache builder
						.maximumSize(100)												//max size of the cache
						.expireAfterWrite(10, TimeUnit.SECONDS)							//invalidate after 10s
						.build(
								new CacheLoader<Integer, Integer>(){
									@Override
									public Integer load(Integer id) throws Exception{
										return getCubeById(id);							//this function will only be called in case of a cache miss
									}
								}
						);
		// use .expireAfterAccess(duration) to remove after a certain time after last access
		// .refreshAfterWrite(1,TimeUnit.MINUTES) = refresh the cache
		
		//Pre load the cache:
		Map<Integer, Integer> map = new HashMap<Integer, Integer>();
	    map.put(1, 1);
	    map.put(2, 8);
	    cubeCache.putAll(map);
	    
	}
	
	// getter for the cache
	public static LoadingCache<Integer, Integer> getLoadingCache(){
		return cubeCache;
	}
	
	//the callable for our cache, this is called in case of a cache miss
	public static Integer getCubeById(int id) {
		System.out.println("Cache miss, finding the cube of " + id);
		return id*id*id;
	}

}
