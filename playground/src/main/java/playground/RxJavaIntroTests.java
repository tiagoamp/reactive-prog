package playground;

import java.util.List;
import java.util.Random;
import java.util.concurrent.Callable;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import rx.Observable;
import rx.Observer;
import rx.Single;
import rx.functions.Action1;
import rx.functions.Func1;

public class RxJavaIntroTests {
	
	public static void main(String[] args) {
		helloWorld1();
		System.out.println();
		
		helloWorld2();
		System.out.println();
		
		helloWorld3();
		System.out.println();
		
		helloWorld4();
		System.out.println();
		
		helloWorld5();
		System.out.println();
		
		operatorsExamples();
		System.out.println();
		
		intervalExample();
		System.out.println();
		
		observableFromListExample();
		System.out.println();
		
		observableFromCallableExample();
		System.out.println();
		
		observableFromFutureExample();
		System.out.println();
		
		starWarsBattle();
	}
	
	
	// simplest way (in-line)
	public static void helloWorld1() {
		Observable.just("Hello Reactive World 1!").subscribe(System.out::println);
	}
	
	// explicit onNext function call
	public static void helloWorld2() {
		Observable<String> myObservable = Observable.just("Hello World 2!"); // not emitted yet
		Action1<String> onNextFunction = msg -> System.out.println(msg); 
		myObservable.subscribe(onNextFunction); // item emitted at subscription time (cold observable)!		
	}
	
	// explicit onNext and OnError functions call
	public static void helloWorld3() {
		Observable<String> myObservable = Observable.just("Hello World 3!"); // not emitted yet
		Action1<String> onNextFunction = System.out::println;
		Action1<Throwable> onErrorFunction = RuntimeException::new;
		myObservable.subscribe(onNextFunction, onErrorFunction); // item emitted at subscription time (cold observable)!		
	}
	
	// segregating Observable x Observer objects 
	public static void helloWorld4() {
		Observable<String> myObservable = Observable.just("Hello World 4!");
		
		Observer<String> myObserver = new Observer<String>() {
			@Override
			public void onCompleted() {
				System.out.println("onCompleted called!");				
			}
			@Override
			public void onError(Throwable e) {
				System.out.println("onError called!");				
			}
			@Override
			public void onNext(String msg) {
				System.out.println("onNext => Message received: " + msg);				
			}			
		};
		
		myObservable.subscribe(myObserver); 		
	}
	
	// since it is emitted just one item, it can be a Single object
	public static void helloWorld5() {
		Single<String> mySingle = Single.just("Hello World 5 from Single!"); 
		mySingle.subscribe(System.out::println, RuntimeException::new);		
	}
		
	// Operators examples
	public static void operatorsExamples() {
		// filter = apply predicate, filtering numbers that are not even
		Func1<Integer, Boolean> evenNumberFunc = x -> x%2 == 0;
		// map = transform each elements emitted, double them in this case 
		Func1<Integer, Integer> doubleNumberFunc = x -> 2*x;
		
		Observable<Integer> myObservable = Observable.range(1, 10) // emits int values from the range
			.filter(evenNumberFunc)
			.map(doubleNumberFunc);
		
		myObservable.subscribe(System.out::println); // prints 4 8 12 16 20
	}
	
	// Interval operator
	private static void intervalExample() {
		Observable.interval(2, TimeUnit.SECONDS)  // emits a sequential number every 2 seconds 
			.take(5)  // limit to first 5 elements
			.toBlocking()  // converts to a blocking observable
			.subscribe(System.out::println); // prints 0 to 4 in 2 seconds interval
	}
	
	// Creating Observables from a Collection/List
	private static void observableFromListExample() {
		List<Integer> intList = IntStream.rangeClosed(1, 10).mapToObj(Integer::new).collect(Collectors.toList());
		Observable.from(intList).subscribe(System.out::println); // prints from 1 to 10
	}
	
	// Creating Observables from Callable function
	private static void observableFromCallableExample() {
		Callable<String> callable = () -> "From Callable";		
		Observable.fromCallable(callable) // defers the callable execution until subscription time
			.subscribe(System.out::println);
	}
	
	// Creating Observables from Future instances
	private static void observableFromFutureExample() {
		CompletableFuture<String> future = CompletableFuture.supplyAsync(() -> "From Future");
		Observable.from(future) // converts a Future into Observable
			.subscribe(System.out::println);
	}
	
	// StarWar battle: Let's get nerdy...
	private static void starWarsBattle() {
		Random random = new Random();
		
		// Stormtrooper class
		class Stormtrooper {
			private int imperialNr;
			
			public Stormtrooper(int imperialNumber) {
				this.imperialNr = imperialNumber;
			}			
			public String getName() {
				return "#" + imperialNr;
			}				
		}
		
		// callable func that creates a Stormtroper after 3 seconds delay
		Callable<Stormtrooper> trooperGenerator = () -> {  
			Thread.sleep(3 * 1000);
			return new Stormtrooper(random.nextInt(1000));
		};
		
		// Creating Observables of Stormtrooper creation
		List<Observable<Stormtrooper>> observables = IntStream.rangeClosed(1, 4)
			.mapToObj(n -> Observable.fromCallable(trooperGenerator)).collect(Collectors.toList());
		
		// Jedi observer to fight every tropper created in time
		Observer<Stormtrooper> jedi = new Observer<Stormtrooper>() {
			@Override
			public void onCompleted() {
				System.out.println("May the force be with you!");				
			}
			@Override
			public void onError(Throwable e) {
				throw new RuntimeException(e);				
			}
			@Override
			public void onNext(Stormtrooper t) {
				System.out.println("Jedi defeated Stormtrooper " + t.getName());				
			}						
		};
		
		// Jedi subscribe to listen to every Stormtrooper creation event
		observables.forEach(o -> o.subscribe(jedi)); // Battle inits at subscription time		
	}
	
}
