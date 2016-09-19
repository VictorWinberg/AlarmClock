package todo;

import done.ClockInput;
import se.lth.cs.realtime.semaphore.Semaphore;

public class Buttons extends Thread {

	private ClockInput input;
	private SharedData data;
	private static Semaphore sem;
	
	public Buttons(ClockInput i, SharedData sd) {
		input = i;
		data = sd;
		sem = i.getSemaphoreInstance();
	}
	
	public void run() {
		while (true) {
			sem.take();
			data.stopAlarm();
			switch (input.getChoice()) {
			case ClockInput.SET_ALARM:
				data.setAlarm(input.getValue());
				break;
			case ClockInput.SET_TIME:
				data.setTime(input.getValue());
				break;
			}
		}
	}
}
