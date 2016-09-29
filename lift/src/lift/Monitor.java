package lift;

public class Monitor {
	
	// If here == next, the lift is standing still on the floor given by here.
	private int here; 			// from which floor the lift is moving
	private int next; 			// to which floor the lift is moving
	private int [] waitEntry;	// Persons waiting to enter the lift at the various floors.
	private int [] waitExit; 	// Persons (inside the lift) waiting to leave the lift at the various floors.
	private int load; 			// The number of people currently occupying the lift.
	private boolean up;
	
	private LiftView view;
	
	public Monitor(LiftView view) {
		this.view = view;
		up = true;
		waitEntry = new int[7];
		waitExit = new int[7];
	}
	
	public synchronized void updateLift() {
		here = next;
		notifyAll();
		
		// Check if persons are waiting for or want to exit the lift
		while((waitEntry[here] > 0 && load < 4) || waitExit[here] > 0) {
			try {
				wait();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		
		// Move the lift
		if(emptyLift())
			return;
		if(up) {
			if(++next >= 6)
				up = false;
		} else {
			if(--next <= 0)
				up = true;
		}
		notifyAll();
	}
	
	private boolean emptyLift() {
		for(int i = 0; i < 6; i++) {
			if(waitEntry[i] > 0) 
				return false;
		}
		return true;
	}

	public void moveLift() {
		if(!emptyLift() && here != next)
			view.moveLift(here, next);
	}
	
	public synchronized void liftAction(int initFloor, int targetFloor) {
		// Add person to wait on level
		drawLevel(initFloor, ++waitEntry[initFloor]);
		notifyAll();
		
		// Wait until person can enter lift
		while(!enterLift(initFloor)) {
			try {
				wait();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}

		// Proceed to enter the lift
		waitEntry[here]--;
		drawLevel(here, waitEntry[here]);
		load++;
		waitExit[targetFloor]++;
		drawLift(here, load);
		try {	// Sleeping makes animation a bit smoother
			Thread.sleep(40);
		} catch (InterruptedException e1) {
			e1.printStackTrace();
		}
		notifyAll();
		
		// Wait until person can exit lift
		while(!exitLift(targetFloor)) {
			try {
				wait();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		
		// Proceed to exit the lift
		waitExit[here]--;
		load--;
		drawLift(here, load);
		try {	// Sleeping makes animation a bit smoother
			Thread.sleep(40);
		} catch (InterruptedException e1) {
			e1.printStackTrace();
		}
		notifyAll();
	}

	private boolean enterLift(int initFloor) {
		return here == next && here == initFloor && load < 4;
	}
	
	private boolean exitLift(int targetFloor) {
		return here == next && here == targetFloor;
	}

	public synchronized void drawLevel(int floor, int persons) {
		view.drawLevel(floor, persons);
	}
	
	public synchronized void drawLift(int floor, int load) {
		view.drawLift(floor, load);
	}
	
}
