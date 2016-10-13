package todo;

import se.lth.cs.realtime.*;
import done.AbstractWashingMachine;

public class TemperatureController extends PeriodicThread {
	
	private AbstractWashingMachine machine;
	private TemperatureEvent event;

	public TemperatureController(AbstractWashingMachine mach, double speed) {
		super((long) (1000/speed)); // TODO: replace with suitable period
		machine = mach;
	}

	public void perform() {
		TemperatureEvent currentEvent = (TemperatureEvent) mailbox.tryFetch();
		// Well hello, we have a new event!
		if(currentEvent != null) {
			event = currentEvent;
			System.out.println("TempCtrl fetched mode: " + currentEvent.getMode());
		}
		
		// Lets handle our event, if such exists
		if(event != null) {
			switch (event.getMode()) {
			case TemperatureEvent.TEMP_IDLE: 
				break;
			case TemperatureEvent.TEMP_SET:
				break;
			}
		}
	}
}
