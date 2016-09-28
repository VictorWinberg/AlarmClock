package lift;

public class Person extends Thread {
	
	public Person(Monitor monitor) {
		// TODO Auto-generated constructor stub
	}
	
	@Override
	public void run() {
		// activated after a randomly chosen delay (range 0-45 seconds).
		int delay = 1000 * ((int)(Math.random() * 46.0)); // 0 - 45 seconds in milliseconds
	}
}
