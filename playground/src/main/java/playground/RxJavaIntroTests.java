package playground;

import java.util.Random;
import java.util.function.Predicate;

import rx.Observable;
import rx.Observer;
import rx.Single;
import rx.functions.Action1;
import rx.functions.Func1;

public class RxJavaIntroTests {

	//Hello World
	
	public static void main(String[] args) {
		helloWorld1();
		helloWorld2();
		helloWorld3();
		helloWorld4();
		helloWorld5();
		operatorsExamples();
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
		
		myObservable.subscribe(val -> System.out.print( " " + val));		
	}
	
	private static void starWarsExample() {
		
		TODO: terminar e colocar no github gist
		
		Observable.from(stormTroopersList).interval()
		
	}
	
	interface Warrior { };
	
	class StormTrooper implements Warrior {
		
		private int imperialNr;
		private boolean defeated;
		
		public StormTrooper(int imperialNumber) {
			this.imperialNr = imperialNumber;
		}
		
		public void fightWith(Warrior warrior) {
			if (warrior instanceof Jedi) defeated = true;
		}
		
		public boolean isDefeated() {
			return defeated;
		}
		
		public int getImperialNr() {
			return imperialNr;
		}
	}
	
	class Jedi implements Warrior {
		
		private String name;
		
		public Jedi(String name) {
			this.name = name;
		}
		
		public String getName() {
			return name;
		}
				
	}
	
}
