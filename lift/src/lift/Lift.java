package lift;

public class Lift extends Thread {
	
	private Monitor monitor;
	
	public Lift(Monitor monitor) {
		this.monitor = monitor;
	}
	
	@Override
	public void run() {
		while(true) {
			try {
				monitor.updateLift();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			monitor.moveLift();
		}
	}
}
