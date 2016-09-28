package lift;

public class Application {

	public static void main(String[] args) {
		LiftView view = new LiftView();
		Monitor monitor = new Monitor(view);
		monitor.start();
		(new Lift(monitor)).start();
		(new Person(monitor)).start();
	}
}
