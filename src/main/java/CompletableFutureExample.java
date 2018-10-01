import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;
import java.util.function.Supplier;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/*
 * Future vs completable future
 * 		
 * Drawbacks of future: https://www.callicoder.com/java-8-completablefuture-tutorial/
 * 		1. Can not be manually completed
 * 		2. Can not perform further action of futures results without blocking: future wont notify of its completion
 * 		3. Multiple futures can not be chained together: once 1st ends we send its reslts to 2nd
 * 		4. Can not combine multiple futures together: you have 10 different Futures that you want to run in parallel and then run some function after all of them completes
 * 		5. No exception handling
 * 
 * Manually completing a future: completableFuture.complete("Future's Result")	//sets a default value fi not yet completed
 * All the clients waiting for this Future will get the specified result
 * 
 * 
 * 
 */

public class CompletableFutureExample {
	
	public void example() {
		
		String str = "People who bought %product% also bought";
		System.out.println(str.replaceAll("%product%","Apples"));
		simpleExample();
		
		// multiple futures can be joined using allOf()
		// allOf completes when all futures have completed
		// anyOf() returns the result of the first future that completes
		combineMultipleFutures();
	}
	
	
	private void simpleExample() {
		// here we calculate BMI where height and weight are calculated in separate threads and then we combine them
		
		System.out.println(System.nanoTime()/1000000 + ":Retrieving weight");
		CompletableFuture<Double> weightFuture = CompletableFuture.supplyAsync( () -> {
			try {
				TimeUnit.SECONDS.sleep(2);
			}catch(InterruptedException e) {
				throw new IllegalStateException(e);
			}
			return 65.0;
		} );
		
		System.out.println(System.nanoTime()/1000000  + ":Retrieving height");
		CompletableFuture<Double> heightFuture = CompletableFuture.supplyAsync( () -> {
			try {
				TimeUnit.SECONDS.sleep(1);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			return 177.8;
		} );
			
//		Double weight=0.0, height=0.0;
//		try {
//			height = heightFuture.get();
//			System.out.println("height = " + height);
//			weight = weightFuture.get();
//			System.out.println("weight = " + weight);
//			
//		} catch (InterruptedException | ExecutionException e1) {
//			// TODO Auto-generated catch block
//			e1.printStackTrace();
//		}
		
		// The callback function passed to thenCombine() will be called when both the Futures are complete.
		System.out.println(System.nanoTime()/1000000  + ":Calculating BMI");
		CompletableFuture<Double>  combinedFuture = weightFuture
													.thenCombine(heightFuture, (weightInKg, heightInCm) -> {
														Double heightInMeter = heightInCm/100;
														return weightInKg/(heightInMeter*heightInMeter);
													});
		try {
			System.out.println(System.nanoTime()/1000000  + ":The BMI is = " + combinedFuture.get());
		} catch (InterruptedException | ExecutionException e) {
			e.printStackTrace();
		}
		
	}
	
	
	private void combineMultipleFutures() {
		// here we create multiple futures and then combine them
		System.out.println("Combining multiple futures");
		CompletableFuture<String> future1 = CompletableFuture.supplyAsync(() -> "Hello");
		CompletableFuture<String> future2 = CompletableFuture.supplyAsync(() -> "Beautiful");
		CompletableFuture<String> future3 = CompletableFuture.supplyAsync(() -> "World");
		
		List<CompletableFuture<String>> futures = new ArrayList<>();
		futures.add(future1);
		futures.add(future2);
		futures.add(future3);
		
		CompletableFuture<Void> endFuture = CompletableFuture.allOf(futures.toArray(new CompletableFuture[futures.size()] ));
		
		CompletableFuture<List<String>> futureList = endFuture.thenApply(v -> 
													futures.stream().
														map(future -> future.join()).
														collect(Collectors.<String>toList())
														);
		
//		String combined = Stream.of(future1, future2, future3)
//							.map(CompletableFuture::join)
//							.collect(Collectors.joining(" "));
//		System.out.println(combined);
		
		try {
			System.out.println("Result = " + futureList.get());
		} catch (InterruptedException | ExecutionException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
