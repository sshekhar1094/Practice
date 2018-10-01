package com.lambdas.example;

//Suppose you want to pass a block of code, how would you do that

interface Executable{
	void execute();
}

class Runner {
	public void run(Executable e) {
		System.out.println("Executing code bloxk...");
		e.execute();
	}
}

public class Lambdas1 {
	public void main() {
		Runner runner = new Runner();
		runner.run(new Executable() {
			public void execute() {
				System.out.println("Hello there!");
			}
		});
		
		// Now do the same thing using lambdas
		System.out.println("===================");
		
		runner.run( () -> {
			System.out.println("Hello there from lambda"); 
		} );
	}
}
