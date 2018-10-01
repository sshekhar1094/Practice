package com.observable.example;

import java.util.Observable;

/**
 * The observable: class being observed
 */
public class FishFeeder extends Observable {

	public void feedTheFish(int quantityOfFood) {
		FishFeederMessage msg = new FishFeederMessage();
		msg.quantityOfFood = quantityOfFood;
		
		setChanged();			// makrs this observable object as has been changed
		// the arg msg is passed to the update function of all the observers
		notifyObservers(msg);		// notify all the observers that this has changed and then marks it as no longer changed
		// this will now call update on all its obervers
	}
	
}
