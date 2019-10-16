package playground;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

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

class TaskRunn implements Runnable {
	@Override
	public void run() {
		System.out.println("I am " + Thread.currentThread().getName());		
	}	
}

class TaskCall implements Callable<String> {
	@Override
	public String call() throws Exception {
		return "I am " + Thread.currentThread().getName();
	}		
}

public class BasicConcurrencyTests {

	public static void main(String[] args) throws InterruptedException {
		ExecutorService executorService = Executors.newFixedThreadPool(4); // pool of 4 threads
		// creates 3 callables to be executed
		List<Callable<String>> callables = Arrays.asList(new TaskCall(), new TaskCall(), new TaskCall());
		List<Future<String>> futures = executorService.invokeAll(callables);
		
		// getting tasks results
		futures.forEach(f -> {
			try { System.out.println(f.get()); } 
			catch (Exception e) {	}
		});
		
		executorService.shutdown(); // shuts down the executor service
		
		
//		Counter counter = new Counter();
//		
//		Thread c1 = new Thread(() -> {
//			for (int i = 0; i < 500; i++) {
//				counter.increment();
//			}
//		});
//		Thread c2 = new Thread(() -> {
//			for (int i = 0; i < 500; i++) {
//				counter.increment();
//			}
//		});
//		
//		c1.start(); // async counter thread 1 start
//		c2.start(); // async counter thread 1 start
//		
//		// awating async threads to finish
//		c1.join();
//		c2.join();
//		
//		System.out.println(counter.count);		
	}
	
}
