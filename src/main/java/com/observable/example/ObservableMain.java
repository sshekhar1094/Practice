package com.observable.example;

import java.util.ArrayList;
import java.util.List;


/**
 * When you need many other objects to receive update when another object changes.
 * Examples:  	1. 	Stock markets with thousands of stocks need to send updates to objects representing individual stocks. 
 * 					The subject(publishers) sends many stocks to the Observers. The Observers(subscribers) takes the ones they want and use them
 * 				2. 	Splitwise group : Anyone adds or updates any entry in the group - all members of group get a notification 
 * 				3. 	Facebook : If one follows a post , he gets added to the observers & any further comments on the same post , send a notification to all the other observers
 * 				4. 	When you subscribe to any website.
 * 				 
 *
 */
public class ObservableMain {
	
	/**
	 * extend observable, implement observer
	 * one class will be doing the change and will be updating many otehr classes
	 * here we create a fishfeeder which is the observable and a TropicalFish which is the Observer. FishFeeder will be feeding the TropicalFish with a message containing how much was fed
	 */
	public void observableMain() {
		System.out.println("******Example for observables Start******");
		
		List<TropicalFish> fishies = new ArrayList<>();	//list of fishes, all the fishes are our observers
		fishies.add(new TropicalFish("inky"));
		fishies.add(new TropicalFish("pinky"));
		fishies.add(new TropicalFish("blinky"));
		
		FishFeeder theFishFeeder = new FishFeeder();	// this is the observable
		
		// now the observable needs to know that it has observers
		for (TropicalFish fish : fishies) {
			theFishFeeder.addObserver(fish);
		}
		// now there are three fishes being observed by the fishFeeder
		
		System.out.println("Now feeding the fish");
		theFishFeeder.feedTheFish(5);
		
		System.out.println("******Example for observables Finish******");
		
	}
}
