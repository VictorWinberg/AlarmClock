/*
 * Real-time and concurrent programming course, laboratory 3
 * Department of Computer Science, Lund Institute of Technology
 *
 * PP 980812 Created
 * PP 990924 Revised
 */

package todo;

import done.*;

/**
 * Program 3 of washing machine. Does the following:
 * <UL>
 *   <LI>Switches off heating
 *   <LI>Switches off spin
 *   <LI>Pumps out water
 *   <LI>Unlocks the hatch.
 * </UL>
 */
class WashingProgram2 extends WashingProgram {
	
	// ------------------------------------------------------------- CONSTRUCTOR

	/**
	 * @param   mach             The washing machine to control
	 * @param   speed            Simulation speed
	 * @param   tempController   The TemperatureController to use
	 * @param   waterController  The WaterController to use
	 * @param   spinController   The SpinController to use
	 */
	public WashingProgram2(AbstractWashingMachine mach,
			double speed,
			TemperatureController tempController,
			WaterController waterController,
			SpinController spinController) {
		super(mach, speed, tempController, waterController, spinController);
	}

	// ---------------------------------------------------------- PUBLIC METHODS

	/**
	 * This method contains the actual code for the washing program. Executed
	 * when the start() method is called.
	 */
	protected void wash() throws InterruptedException {

		// Lock
		myMachine.setLock(true);
		System.out.println("Machine locked");
		
		// Pre wash
		System.out.println("Pre wash");
		wash(40, 15);
		
		// Main wash
		System.out.println("Main wash");
		wash(90, 30);
		
		// Rinse 5 times in cold water for 2 minutes
		for(int i = 0; i < 5; i++){
			System.out.println("Rinse " + (i+1));
			myWaterController.putEvent(new WaterEvent(this, 
					WaterEvent.WATER_FILL, 
					10.0));
			mailbox.doFetch(); // Wait for Ack
			
			System.out.println("Wait 2 minutes");
			sleep(2 * myMinute);
			
			myWaterController.putEvent(new WaterEvent(this, 
					WaterEvent.WATER_DRAIN, 
					0.0));
			mailbox.doFetch(); // Wait for Ack
		}
		
		// Centrifuge for 5 minutes
		mySpinController.putEvent(new SpinEvent(this, 
				SpinEvent.SPIN_FAST));
		
		System.out.println("Centrifuge 5 minutes");
		sleep(5 * myMinute);
		
		mySpinController.putEvent(new SpinEvent(this, 
				SpinEvent.SPIN_OFF));
		
		// Unlock
		myMachine.setLock(false);
		System.out.println("Machine unlocked");
	}
}
