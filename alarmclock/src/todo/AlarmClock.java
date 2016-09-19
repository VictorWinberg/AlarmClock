package todo;
import done.*;

public class AlarmClock extends Thread {

	public AlarmClock(ClockInput i, ClockOutput o) {
		SharedData data = new SharedData(i, o);
		
		Time time = new Time(data);
		time.start();
		
		Buttons buttons = new Buttons(i, data);
		buttons.start();
	}

	// The AlarmClock thread is started by the simulator. No
	// need to start it by yourself, if you do you will get
	// an IllegalThreadStateException. The implementation
	// below is a simple alarmclock thread that beeps upon 
	// each keypress. To be modified in the lab.
	public void run() {
		System.out.println("AlarmClock started!");
	}
}
