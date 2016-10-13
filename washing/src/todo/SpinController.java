package todo;

import se.lth.cs.realtime.*;
import done.AbstractWashingMachine;

public class SpinController extends PeriodicThread {
	
	private AbstractWashingMachine machine;
	private SpinEvent event;
	private int spinDirection;

	public SpinController(AbstractWashingMachine mach, double speed) {
		super((long) (1000/speed)); // TODO: replace with suitable period
		machine = mach;
		spinDirection = AbstractWashingMachine.SPIN_LEFT;
	}

	public void perform() {
		SpinEvent currentEvent = (SpinEvent) mailbox.tryFetch();
		// Well hello, we have a new event!
		if(currentEvent != null) {
			event = currentEvent;
			System.out.println("SpinCtrl fetched mode: " + currentEvent.getMode());
		}
		
		// Lets handle our event, if such exists
		if(event != null) {
			switch (event.getMode()) {
			case SpinEvent.SPIN_OFF: machine.setSpin(AbstractWashingMachine.SPIN_OFF);
				break;
			case SpinEvent.SPIN_SLOW:
				if(spinDirection == AbstractWashingMachine.SPIN_LEFT) {
					spinDirection = AbstractWashingMachine.SPIN_RIGHT;
					machine.setSpin(spinDirection);
					System.out.println("Spin direction RIGHT (SpinController)");
				} else {
					spinDirection = AbstractWashingMachine.SPIN_LEFT;
					machine.setSpin(spinDirection);
					System.out.println("Spin direction LEFT (SpinController)");
				}
				break;
			case SpinEvent.SPIN_FAST: machine.setSpin(AbstractWashingMachine.SPIN_FAST);
				break;
			}
		}
	}
}
