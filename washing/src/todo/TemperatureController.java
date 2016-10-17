package todo;

import se.lth.cs.realtime.*;
import se.lth.cs.realtime.event.AckEvent;
import done.AbstractWashingMachine;

public class TemperatureController extends PeriodicThread {
	
	private AbstractWashingMachine machine;
	private int mode = TemperatureEvent.TEMP_IDLE;
	private double temperature, upperlimit, lowerlimit;
	private boolean isHeating, sendAck;
	private RTThread source;

	public TemperatureController(AbstractWashingMachine mach, double speed) {
		super((long) (10 * 1000/speed));
		machine = mach;
	}

	public void perform() {
		TemperatureEvent event = (TemperatureEvent) mailbox.tryFetch();
		
		// Lets handle our event, if such exists
		if(event != null) {
			mode = event.getMode();
			temperature = event.getTemperature();
			source = (RTThread) event.getSource();
			
			switch (event.getMode()) {
			case TemperatureEvent.TEMP_IDLE:
				System.out.println("Switch off temp regulation");
				machine.setHeating(false);
				isHeating = false;
				break;
				
			case TemperatureEvent.TEMP_SET:
				upperlimit = temperature - 1;
				lowerlimit = temperature - 1.8;
				sendAck = true;
				if(true) System.out.print("Set temperature to " + temperature + " degrees ... ");
			}
		}
		
		// Keep the temperature at the target temperature
		if (mode == TemperatureEvent.TEMP_SET) {
			if(machine.getTemperature() >= upperlimit && isHeating) {
				machine.setHeating(false);
				isHeating = false;
				if(sendAck) {
					System.out.println("ACK");
					source.putEvent(new AckEvent(this));
					sendAck = false;
				}
			} else if (machine.getTemperature() <= lowerlimit && !isHeating){
				machine.setHeating(true);
				isHeating = true;
			}
		}
	}
}
