package todo;

import se.lth.cs.realtime.*;
import done.AbstractWashingMachine;

public class SpinController extends PeriodicThread {
	
	private AbstractWashingMachine machine;
	private SpinEvent event;
	private int spinDirection;

	public SpinController(AbstractWashingMachine mach, double speed) {
		super((long) (1000/speed));
		machine = mach;
		spinDirection = AbstractWashingMachine.SPIN_LEFT;
	}

	public void perform() {
		SpinEvent event = (SpinEvent) mailbox.tryFetch();
		
		// Lets handle our event, if such exists
		if(event != null) {
			
			switch (event.getMode()) {
			case SpinEvent.SPIN_OFF: 
				System.out.println("Spin off");
				machine.setSpin(AbstractWashingMachine.SPIN_OFF);
				break;
				
			case SpinEvent.SPIN_SLOW:
				if(spinDirection == AbstractWashingMachine.SPIN_LEFT) {
					System.out.println("Spin slow RIGHT");
					spinDirection = AbstractWashingMachine.SPIN_RIGHT;
					machine.setSpin(spinDirection);
				} else {
					System.out.println("Spin slow LEFT");
					spinDirection = AbstractWashingMachine.SPIN_LEFT;
					machine.setSpin(spinDirection);
				}
				break;
				
			case SpinEvent.SPIN_FAST: 
				System.out.println("Spin fast");
				machine.setSpin(AbstractWashingMachine.SPIN_FAST);
				break;
			}
		}
	}
}
