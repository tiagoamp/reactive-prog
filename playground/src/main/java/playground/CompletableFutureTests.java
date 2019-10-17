package playground;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import playground.stub.MyDataObj;

public class CompletableFutureTests {

	
	public static void main(String[] args) throws InterruptedException {
		
		final int qtdThreads = Runtime.getRuntime().availableProcessors();
		ExecutorService executor = Executors.newFixedThreadPool(qtdThreads);
		final int nrOfElemts = 10;
		
		emitsAndProcessElementsWithSupplierAsync(nrOfElemts, executor);
						
		Thread.sleep(10_000); // await 10 seconds for tasks to finish
		System.out.println("----------------");
		
		emitsAndProcessElementsWithRunnableAsync(nrOfElemts, executor);
		
		executor.shutdown();
		System.exit(0);		
	}
	
	
	private static void emitsAndProcessElementsWithSupplierAsync(int nrOfElemts, ExecutorService executor) {
		System.out.println("Supplier results:");
		for(int i=0; i<nrOfElemts; i++) {			
			MyDataObj myDataObj = new MyDataObj(i, "I am id = " + i);			
			CompletableFuture.supplyAsync(() -> myDataObj, executor)
				.exceptionally(ex -> new MyDataObj(-1, "I am error!"))  // callback for exception case, recovers with dummy data
				.thenApply(obj -> obj.getInfo().toUpperCase())          // callback for transformation
				.thenAccept(System.out::println);	                    // callback for successful case						
		}		
	}
	
	private static void emitsAndProcessElementsWithRunnableAsync(int nrOfElemts, ExecutorService executor) {
		System.out.println("Runnable results:");
		for(int i=0; i<nrOfElemts; i++) {
			MyDataObj myDataObj = new MyDataObj(i, "I am id = " + i);
			CompletableFuture<MyDataObj> compfuture = new CompletableFuture<>();			
			CompletableFuture.runAsync(() -> compfuture.complete(myDataObj), executor);
			compfuture
				.exceptionally(ex -> new MyDataObj(-1, "I am error!"))
				.thenApply(obj -> obj.getInfo().toUpperCase())
				.thenAccept(System.out::println);										
		}
	}
	
	
}
