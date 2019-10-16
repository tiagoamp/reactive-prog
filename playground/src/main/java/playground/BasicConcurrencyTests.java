package playground;

import java.util.Random;

class Ping implements Runnable {
	
	@Override
	public void run() {		
		for (int i = 0; i < 3; i++) {
			try { Thread.sleep(new Random().nextInt(2000)); } catch (InterruptedException e) { } // simulates work that costs between from 0 to 4 seconds
			System.out.println("ping");
		}		
	}
	
}

class Pong implements Runnable {
	
	@Override
	public void run() {
		for (int i = 0; i < 3; i++) {
			try { Thread.sleep(new Random().nextInt(2000)); } catch (InterruptedException e) { } // simulates work that costs between from 0 to 4 seconds
			System.out.println("pong");
		}
	}
	
}

class Counter {
	int count;
	
	public synchronized void increment() {
		count++; // count = count +1
	}
}

public class BasicConcurrencyTests {

	public static void main(String[] args) throws InterruptedException {
		Counter counter = new Counter();
		
		Thread c1 = new Thread(() -> {
			for (int i = 0; i < 500; i++) {
				counter.increment();
			}
		});
		Thread c2 = new Thread(() -> {
			for (int i = 0; i < 500; i++) {
				counter.increment();
			}
		});
		
		c1.start(); // async counter thread 1 start
		c2.start(); // async counter thread 1 start
		
		// awating async threads to finish
		c1.join();
		c2.join();
		
		System.out.println(counter.count);		
	}
	
}
