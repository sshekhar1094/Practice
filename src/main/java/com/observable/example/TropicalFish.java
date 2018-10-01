package com.observable.example;

import java.util.Observable;
import java.util.Observer;


/**
 * the observer
 */
public class TropicalFish implements Observer {

	private String name;
	
	
	/* (non-Javadoc)
	 * thsi function will be called when ever the observable call notifyObservers
	 */
	@Override
	public void update(Observable theObservable, Object message) {
		if(message!=null && message instanceof FishFeederMessage) {
			FishFeederMessage msg = (FishFeederMessage) message;
			System.out.printf("%s was just fed with %d units of food\n", getName(), msg.quantityOfFood);
		}
	}

	public TropicalFish(String name) {
		super();
		this.name = name;
	}

	@Override
	public String toString()  {
		return "TropicalFish [name=" + name + "]";
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	
}

