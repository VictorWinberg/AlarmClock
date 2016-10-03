package lift;

public class Monitor {
	
	// If here == next, the lift is standing still on the floor given by here.
	private int here; 			// from which floor the lift is moving
	private int next; 			// to which floor the lift is moving
	private int [] waitEntry;	// Persons waiting to enter the lift at the various floors.
	private int [] waitEntryDir;
	private int [] waitExit; 	// Persons (inside the lift) waiting to leave the lift at the various floors.
	private int load; 			// The number of people currently occupying the lift.
	private boolean up;
	
	private LiftView view;
	
	public Monitor(LiftView view) {
		this.view = view;
		up = true;
		waitEntry = new int[7];
		waitExit = new int[7];
		waitEntryDir = new int[7];
	}

	public void moveLift() {
		int tempHere, tempNext;
		synchronized (this) {
			tempHere = here; 
			tempNext = next;
		}
		if(!waitEntriesEmpty() && tempHere != tempNext)
			view.moveLift(tempHere, tempNext);
	}
	
	public synchronized void updateLift() throws InterruptedException {
		here = next;
		notifyAll();
		
		// Wait and move lift if we have waiting persons
		while(waitEntriesEmpty()) {
			wait();
		}
		
		// Check if persons are waiting for or want to exit the lift
		while((waitEntryDir[here] > 0 && load < 4) || waitExit[here] > 0) {
			wait();
		}
		
		if(up) {
			up = ++next < 6;
		} else {
			up = --next <= 0;
		}
		notifyAll();
	}
	
	private boolean waitEntriesEmpty() {
		for(int i = 0; i < 6; i++) {
			if(waitEntry[i] > 0) 
				return false;
		}
		return true;
	}
	
	public synchronized void personAction(int initFloor, int targetFloor) throws InterruptedException {
		// Add person to wait on level
		waitEntryDir[initFloor]++;
		drawLevel(initFloor, ++waitEntry[initFloor]);
		notifyAll();
		
		// Wait until person can enter lift
		while(!enterLift(initFloor) || !correctDirection(targetFloor)) {
			boolean reset = false;
			// Lift is standing still but in the wrong direction
			if(here == next && !correctDirection(targetFloor)) {
				waitEntryDir[initFloor]--;
				reset = true;
			}
			wait();
			if(reset) {
				waitEntryDir[initFloor]++;
				reset = false;
			}
		}

		// Proceed to enter the lift
		waitEntry[here]--;
		waitEntryDir[here]--;
		drawLevel(here, waitEntry[here]);
		load++;
		waitExit[targetFloor]++;
		drawLift(here, load);
		notifyAll();
		
		// Wait until person can exit lift
		while(!exitLift(targetFloor)) {
			wait();
		}
		
		// Proceed to exit the lift
		waitExit[here]--;
		load--;
		drawLift(here, load);
		notifyAll();
	}

	private boolean correctDirection(int targetFloor) {
		return up ? targetFloor > here : targetFloor < here;
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
