package todo;

import javax.swing.JOptionPane;

import done.*;

public class WashingController implements ButtonListener {	
	
	private AbstractWashingMachine machine;
	private double speed;
	
	private TemperatureController tempCtrl;
	private SpinController spinCtrl;
	private WaterController waterCtrl;
	
	private int prevButton;
	
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
    	if(!machine.isLocked())
    		prevButton = 0;
    	
		switch (theButton) {
			case 0: (new WashingProgram0(machine, speed, tempCtrl, waterCtrl, spinCtrl)).start();
				break;
			case 1: 
				if(prevButton == 0) {
					(new WashingProgram1(machine, speed, tempCtrl, waterCtrl, spinCtrl)).start();					
				}
				else {
					sendMsg(prevButton);
					return;
				}
				break;
			case 2: 
				if(prevButton == 0) {
					(new WashingProgram2(machine, speed, tempCtrl, waterCtrl, spinCtrl)).start();
				} else {
					sendMsg(prevButton);
					return;
				}
				break;
			case 3: 
				if(prevButton == 0) {
					(new WashingProgram3(machine, speed, tempCtrl, waterCtrl, spinCtrl)).start();
				} else {
					sendMsg(prevButton);
					return;
				}
		}
		
		System.out.println();
    	System.out.println("Program " + theButton + " is running:");
    	prevButton = theButton;
    }
    
    public void sendMsg(int program) {
    	JOptionPane.showMessageDialog(null, 
    			"Please stop your current program first", 
    			"Program " + program + " is running.", 
    			JOptionPane.INFORMATION_MESSAGE);
    }
}
