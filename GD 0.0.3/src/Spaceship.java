import javax.swing.JComponent;

public class Spaceship extends JComponent {

	private static final long serialVersionUID = 1L;
	
	protected ClientController control;
	
	private Player controller;

	private int maxFuel = 10;
	private int fuel;
	private int costFuel = 2;
	
	private int range = 100;
	
	private Satellite currSat;
	
	public Spaceship(ClientController clientController, Player p) {
		control = clientController;
		controller = p;
		
		fuel = maxFuel;
	}

	
	public Boolean withinRange(Satellite dest) {
		int distance = (int) Math.sqrt(Math.pow(Math.abs(currSat.getMidX() - dest.getMidX()), 2) + Math.pow(Math.abs(currSat.getMidY() - dest.getMidY()), 2));
		if (distance <= (range + currSat.getSz() + dest.getSz()))
			return true;
		return false;
	}
	
	public void move (Satellite dest) {
		// ASSUMED THAT WITHIN RANGE AND THAT YOU HAVE ENOUGH FUEL
		fuel -= costFuel;
		currSat = dest;
	}


	public int getFuel() {
		return fuel;
	}


	public void setFuel(int fuel) {
		this.fuel = fuel;
	}
	
	
	public Satellite getCurrSat() {
		return currSat;
	}
	
}
