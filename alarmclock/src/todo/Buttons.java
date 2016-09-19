package todo;

import done.ClockInput;
import se.lth.cs.realtime.semaphore.Semaphore;

public class Buttons extends Thread {

	private ClockInput input;
	private SharedData data;
	private static Semaphore sem;
	
	public Buttons(ClockInput i, SharedData data) {
		input = i;
		this.data = data;
		sem = i.getSemaphoreInstance();
	}
	
	public void run() {
		while (true) {
			sem.take();
			switch (input.getChoice()) {
			case ClockInput.SET_ALARM:
				data.stopAlarm();
				data.setAlarm(input.getValue());
				break;
			case ClockInput.SET_TIME:
				data.stopAlarm();
				data.setTime(input.getValue());
				break;
			}
		}
	}
}
