package playground;

import java.util.Random;

class Ping extends Thread {
	
	@Override
	public void run() {		
		for (int i = 0; i < 3; i++) {
			try { Thread.sleep(new Random().nextInt(2000)); } catch (InterruptedException e) { } // simulates work that costs between from 0 to 4 seconds
			System.out.println("ping");
		}		
	}
	
}

class Pong extends Thread {
	
	@Override
	public void run() {
		for (int i = 0; i < 3; i++) {
			try { Thread.sleep(new Random().nextInt(2000)); } catch (InterruptedException e) { } // simulates work that costs between from 0 to 4 seconds
			System.out.println("pong");
		}
	}
	
}

public class BasicConcurrencyTests {

	public static void main(String[] args) {
		System.out.println("Start of main thread");
		
		Thread ping = new Ping();
		Thread pong = new Pong();
		
		ping.start();
		pong.start();
		
		System.out.println("End of main thread");		
	}
	
}
