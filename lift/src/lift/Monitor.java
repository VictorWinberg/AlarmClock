package lift;

public class Monitor extends Thread {
	
	// If here == next, the lift is standing still on the floor given by here.
	int here; 			// from which floor the lift is moving
	int next; 			// to which floor the lift is moving
	int [] waitEntry;	// Persons waiting to enter the lift at the various floors.
	int [] waitExit; 	// Persons (inside the lift) waiting to leave the lift at the various floors.
	int load; 			// The number of people currently occupying the lift.
	
	public Monitor(LiftView view) {
		// TODO Auto-generated constructor stub
	}
	
	@Override
	public void run() {
		// TODO Auto-generated method stub
		super.run();
	}
}
