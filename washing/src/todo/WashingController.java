package todo;

import javax.swing.JOptionPane;

import done.*;

public class WashingController implements ButtonListener {	
	
	private AbstractWashingMachine machine;
	private double speed;
	
	private TemperatureController tempCtrl;
	private SpinController spinCtrl;
	private WaterController waterCtrl;
	
    public WashingController(AbstractWashingMachine theMachine, double theSpeed) {
		machine = theMachine;
		speed = theSpeed;
		tempCtrl = new TemperatureController(machine, speed);
		spinCtrl = new SpinController(machine, speed);
		waterCtrl = new WaterController(machine, speed);
		tempCtrl.start();
		spinCtrl.start();
		waterCtrl.start();
    }

    public void processButton(int theButton) {		
		System.out.println("Program " + theButton + " is running.");
		
		switch (theButton) {
			case 0: 
				break;
			case 1: (new WashingProgram1(machine, speed, tempCtrl, waterCtrl, spinCtrl)).start();
				break;
			case 2: (new WashingProgram2(machine, speed, tempCtrl, waterCtrl, spinCtrl)).start();
				break;
			case 3: (new WashingProgram3(machine, speed, tempCtrl, waterCtrl, spinCtrl)).start();
				break;
		}
    }
}
