import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

public class FutureExample implements Callable<Integer>{

	public void example() {
		System.out.println("Inside futuire example");
	
		simpleExample();
		extendedExample();
		
	}
	
	/*
	 * 1st simple example with a single thread
	 */
	private void simpleExample() {
		ExecutorService executor = Executors.newCachedThreadPool(); 	//the cached thread pool may grow without bounds to accommodate any amount of submitted tasks
		// But when the threads are not needed anymore, they will be disposed of after 60 seconds of inactivity
		
		// similar to submit() there are invoekAny() and invokeAll() which take a list of callables and execute them
		Future<Integer> future = executor.submit(new Callable<Integer>() {

			@Override
			public Integer call() throws Exception {
				Random random = new Random();
				int duration = random.nextInt(2000);
				if(duration > 1000) throw new IOException("Sleeping for too long");
				
				System.out.println("Starting...");
				Thread.sleep(duration);
				return duration;
			}
		});
		
		//shutdown() doesnt cause the immediate destructuction of the executorService, it will make the service stop accepting new tasksand shutdown after all running tasks finish their current work
		executor.shutdown();
		//The shutdownNow() method tries to destroy the ExecutorService immediately, but it doesn’t guarantee that all the running threads will be stopped at the same time. 
		//This method returns a list of tasks which are waiting to be processed.
		//List<Runnable> notExecutedTasks = executorService.shutDownNow();
		
		try {
			/*
			 * The get method blocks until the thread related to future has completed
			 * future.get(200, TimeUnit.MILLISECONDS); : This will timeout in 200ms
			 */
			System.out.println("Slept for " + future.get());
		} catch (InterruptedException | ExecutionException e) {
			System.out.println(e.getMessage());
		}
		
	}
	
	/*
	 * 2nd extended example
	 */
	private void extendedExample() {
		ExecutorService executor = Executors.newFixedThreadPool(10);
		
		// list to hold the future objects
		List<Future<Integer>> list = new ArrayList<Future<Integer>>();
		Callable<Integer> callable = new FutureExample();
		
		for(int i=0; i<100; i++) {
			//submit task to be executed by thread pool
			Future<Integer> future = executor.submit(callable);
			list.add(future);
		}
		
		for(Future<Integer> fut : list) {
			try {
				System.out.println("slept for " + fut.get());
			} catch (InterruptedException | ExecutionException e) {
				e.printStackTrace();
			}
		}
	}

	@Override
	public Integer call() throws Exception {
		Random random = new Random();
		int duration = random.nextInt(1000);
		Thread.sleep(duration);
		return duration;
	}
	
	
}

