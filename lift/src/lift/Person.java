package lift;

public class Person extends Thread {
	
	private Monitor monitor;
	
	public Person(Monitor monitor) {
		this.monitor = monitor;
	}
	
	@Override
	public void run() {
		while(true) {
			// Activated after a randomly chosen delay (range 0-45 seconds).
			int delay = 1000 * ((int)(Math.random() * 46.0)); // 0 - 45 seconds in milliseconds
			try {
				Thread.sleep(delay);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			int initFloor = ((int)(Math.random() * 7.0));
			int targetFloor = ((int)(Math.random() * 7.0));
			while(targetFloor == initFloor) {
				targetFloor = ((int)(Math.random() * 7.0));
			}
			monitor.liftAction(initFloor, targetFloor);
		}
	}
}
