package lift;

public class Application {

	public static void main(String[] args) {
		LiftView view = new LiftView();
		Monitor monitor = new Monitor(view);
		(new Lift(monitor)).start();
		for(int i = 0; i < 20; i++)
			(new Person(monitor)).start();
	}
}
