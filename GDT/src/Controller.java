
public class Controller {

	private Window window;
	private Map map;
	private Satellite[] all;
	private Player p1;
	private String status;
	
	public String getStatus() {
		return status;
	}
	
	
	public void startUp() {

		status = "StartUp";
		
		window = new Window(this);
		map = new Map(this);
		window.add(map);
		window.update();

		// ??Planet(x, y, s, numResources, name)
		Satellite s00 = new Sun(this);
		Satellite s01 = new WaterPlanet(this, 450, 400, 30, 3, "0");  
		Satellite s02 = new MetalPlanet(this, 800, 104, 40, 5, "1"); 
		Satellite s03 = new GasPlanet(this, 645, 40, 26, 5, "2"); 
		Satellite s04 = new WaterPlanet(this, 766, 388, 58, 2, "3"); 
		Satellite station = new Station(this, 700, 100, 30);
		
		Satellite[] al = {s00, s01, s02, s03, s04, station};
		all = al;
		
		for (int i = 0; i < all.length; i++) {
			Satellite s = all[i];
			map.add(s);
			s.setBounds(s.getLocX(), s.getLocY(), s.getSz(), s.getSz());
		}
		
		p1 = new Player(this, "Player 1");
		
		status = "";
	}
	
	public static void main(String[] args) {
		Controller control = new Controller();
		control.startUp();
		
		

	}

}
