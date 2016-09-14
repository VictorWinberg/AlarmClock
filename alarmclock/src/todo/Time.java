package todo;

import done.ClockOutput;

public class Time extends Thread {
	
	private static ClockOutput	output;		// To be removed later with SharedData
	
	public Time(ClockOutput o) {
		output = o;
	}
	
	int t = 0;
	
	public void run() {
		while (true) {
			output.showTime(t++);
			try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}
}
