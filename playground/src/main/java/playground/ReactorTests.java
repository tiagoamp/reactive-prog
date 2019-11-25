package playground;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public class ReactorTests {
	
	
	public static void main(String[] args) throws InterruptedException {
		// Flux example
		Flux<Integer> fluxo = Flux.range(1, 5);
		fluxo.map(nr -> nr *2);
		fluxo.subscribe(System.out::println);
		// Mono Example		
		Mono.just("Bacon").map(str -> str + "!!!").subscribe(System.out::println);		
	}
		
	
}
