package playground;

import java.util.Random;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class CompletableFutureTests {

	
	public static void main(String[] args) {
		ExecutorService executor = Executors.newFixedThreadPool(4); // 4 threads pool
		
		for(int i=0; i<10; i++) {
			MyDataObj myDataObj = new MyDataObj(i, "I am id = " + i);
			
			CompletableFuture.supplyAsync(() -> myDataObj, executor)
				.exceptionally(ex -> new MyDataObj(-1, "I am error!"))
				.thenApply(obj -> uppercaseFunction(obj))
				.thenAccept(str -> printFunction(str));
							
		}
		
		
	}
	
	
	private static MyDataObj getRandomDataObj() {
		int randInt = new Random().nextInt();
		return new MyDataObj(randInt, "I am id = " + randInt);
	}
	
	private static String uppercaseFunction(MyDataObj dataObj) {
		return dataObj.getInfo().toUpperCase();
	}
	
	private static void printFunction(String info) {
		System.out.println(info);
	}
	
}

/**
 * Simulates a data object
 */
class MyDataObj {
	private int id;
	private String info;
	
	public MyDataObj(int id, String info) {
		this.id = id;
		this.info = info;
	}	
		
	public int getId() {
		return id;
	}
	public String getInfo() {
		return info;
	}
		
}