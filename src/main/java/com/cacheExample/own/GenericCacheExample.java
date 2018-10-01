package com.cacheExample.own;

import java.util.concurrent.Callable;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;
import java.util.concurrent.FutureTask;

/**
 * The caching class needs to be thread-safe and needs to smart enough to
 * prevent the computation of the same value twice even is the first thread has
 * not yet finished yet. The second thread should still use the value being
 * calculated by the first thread
 */
public class GenericCacheExample<K, V> {

	// cache contains future, so even if 2 threads hit at the same time, they both
	// do not insert the same value in the cache
	// putting a future means value will be computed only once
	// when thread1 adds the future instance to the cache, thread2 will add the same
	// instance and will both wait for this to finish
	private final ConcurrentMap<K, Future<V>> cache = new ConcurrentHashMap<>();

	/**
	 * get future from the cache
	 * in case of a miss, insert into the cache
	 */
	private Future<V> createFutureIfAbsent(final K key, final Callable<V> callable) {
		Future<V> future = cache.get(key);

		if (future == null) { // cache miss
			final FutureTask<V> futureTask = new FutureTask<V>(callable);
			future = cache.putIfAbsent(key, futureTask); // returns the previous value of the key
			if (future == null) {
				// if null that means no value was attached and our future was added
				future = futureTask;
				futureTask.run(); // start the task
			}
		}
		return future;
	}

	
	/**
	 * get the cache value.
	 * If exception thrown, invalidate the entry
	 */
	public V getValue(final K key, final Callable<V> callable) throws InterruptedException, ExecutionException {
		try {
			final Future<V> future = createFutureIfAbsent(key, callable);
			return future.get();
		} catch (InterruptedException | ExecutionException | RuntimeException e) {
			cache.remove(key);
			throw e;
		}
	}

	/**
	 * add values to the cache
	 * can be used for the purpose of warming
	 */
	public void setValueIfAbsent(final K key, final V value) {
		createFutureIfAbsent(key, new Callable<V>() {
			@Override
			public V call() throws Exception {
				return value;
			}
		});
	}
}
