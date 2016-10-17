package todo;

import se.lth.cs.realtime.*;
import done.AbstractWashingMachine;

public class WaterController extends PeriodicThread {
	
	private AbstractWashingMachine machine;
	private int mode = WaterEvent.WATER_IDLE;
	private double level;
	private RTThread source;

	public WaterController(AbstractWashingMachine mach, double speed) {
		super((long) (3 * 1000/speed));
		machine = mach;
	}

	public void perform() {
		WaterEvent event = (WaterEvent) mailbox.tryFetch();

		// Lets handle our event, if such exists
		if(event != null) {
			mode = event.getMode();
			level = event.getLevel() / 20;
			source = (RTThread) event.getSource();
			
			switch (event.getMode()) {
			case WaterEvent.WATER_IDLE: 
				System.out.println("Switch off water regulation");
				machine.setFill(false); 
				machine.setDrain(false);
				break;
				
			case WaterEvent.WATER_FILL: 
				System.out.print("Fill water to " + (20 * level) + " l ... ");
				machine.setFill(true);
				machine.setDrain(false);
				break;
				
			case WaterEvent.WATER_DRAIN:
				sleep(1); System.out.print("Drain ... ");
				machine.setDrain(true);
				machine.setFill(false);
			}
		}

		// Keep filling the machine until it is above target level
		if (mode == WaterEvent.WATER_FILL && machine.getWaterLevel() >= level) {
			machine.setFill(false);
			mode = WaterEvent.WATER_IDLE;
			System.out.println("ACK");
			source.putEvent(new AckEvent(this));
		}
		// Keep draining the machine until it is below target level
		else if (mode == WaterEvent.WATER_DRAIN && machine.getWaterLevel() <= level) {
			machine.setDrain(false);
			mode = WaterEvent.WATER_IDLE;
			System.out.println("ACK");
			source.putEvent(new AckEvent(this));
		}
	}
}
