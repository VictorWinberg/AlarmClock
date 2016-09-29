package lift;

public class Lift extends Thread {
	
	private Monitor monitor;
	
	public Lift(Monitor monitor) {
		this.monitor = monitor;
	}
	
	@Override
	public void run() {
		while(true) {
			monitor.updateLift();
			monitor.moveLift();
		}
	}
}
