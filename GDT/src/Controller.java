
public class Controller {

	private Window window;
	private Map map;
	private Satellite[] all;
	private Player p1;
	private Player currPlayer;
	private String status;
	
	public String getStatus() {
		return status;
	}
	
	public void setStatus(String s) {
		status = s;
	}
	
	public Player getCurrPlayer() {
		return currPlayer;
	}
	
	public void print(String s) {
		System.out.println(s);
	}
	
	public void startUp() {

		status = "StartUp";
		
		window = new Window(this);
		map = new Map(this);
		window.getContentPane().add(map);
		//Map map2 = new Map(this);
		//window.getContentPane().add(map2);
		window.pack();
		window.update();

		// ??Planet(x, y, s, numResources, name)
		Satellite s00 = new Sun(this);
		Satellite s01 = new WaterPlanet(this, 450, 350, 30, 3, "0"); 
		Satellite s02 = new WaterPlanet(this, 766, 388, 58, 2, "1");
		Satellite s03 = new WaterPlanet(this, 140, 142, 70, 7, "2");
		Satellite s04 = new	WaterPlanet(this, 217, 530, 35, 5, "3");
		Satellite s05 = new MetalPlanet(this, 800, 104, 40, 5, "0"); 
		Satellite s06 = new MetalPlanet(this, 657, 720, 62, 7, "1");
		Satellite s07 = new MetalPlanet(this, 340, 460, 27, 2, "2");
		Satellite s08 = new MetalPlanet(this, 500, 680, 36, 2, "3");
		Satellite s09 = new GasPlanet(this, 645, 40, 26, 5, "0"); 
		Satellite s10 = new GasPlanet(this, 300, 200, 83, 3, "1");
		Satellite s11 = new GasPlanet(this, 240, 730, 47, 2, "2");
		Satellite s12 = new GasPlanet(this, 700, 600, 50, 3, "3");

		Satellite station00 = new Station(this, 700, 100, 30);
		Satellite station01 = new Station(this, 313, 545, 30);
		Satellite station02 = new Station(this, 168, 232, 30);
		Satellite station03 = new Station(this, 826, 630, 30);
		Satellite[] al = {s00, s01, s02, s03, s04, s05, s06, s07, s08, 
				s09, s10, s11, s12, station00, station01, station02, station03};
		all = al;
		
		for (int i = 0; i < all.length; i++) {
			Satellite s = all[i];
			map.add(s); 
			s.setBounds(s.getLocX(), s.getLocY(), s.getSz(), s.getSz());
		}
		
		p1 = new Player(this, "Player 1");
		
		status = "";
	}
	
	public void setUp() {
		status = "SetUp";
		
		currPlayer = p1;
		
		print(currPlayer.getName() + ": Click on a space station to claim it");
		status = "Claiming";
		while (status.equals("Claiming")) {
			System.out.print("");
		}
		
		
		
		status = "";

	}
	
	public void drawAoIs(Station s) {
		
	}
	
	public Boolean withinDistance(Station s, Satellite p) {
		// calculate distance
		Integer distance = (int) Math.sqrt(Math.pow(Math.abs(s.getMidX() - p.getMidX()), 2) + 
				Math.pow(Math.abs(s.getMidY() - p.getMidY()), 2));
		if (distance < (s.getAoI() + p.getSz())) {
			return true;
		}
		return false;
	}
	// s + wp 0: 250 + 300 = 550, 
	public void collectResources() {
		/* current player collects resources in AoI */
		
		Station[] stations = currPlayer.getStations();
		// for each station
		for (int s = 0; s < currPlayer.getNumStations(); s++) {
			// see which planets are within it
			for (int p = 0; p < all.length; p++) {
				// is it a station or sun? --> ignore
				// are they within?
				if ( !(all[p] instanceof Station) && (!(all[p] instanceof Sun)) && (withinDistance(stations[s], all[p]))) {
					print("Matched with " + all[p].getName() + ", getting " + ((Planet) all[p]).getResources() + ".");
					if (all[p] instanceof WaterPlanet) {
						currPlayer.addWater(((Planet) all[p]).getResources());
					}
					else if (all[p] instanceof MetalPlanet){
						currPlayer.addMineral(((Planet) all[p]).getResources());
					}
					else
						currPlayer.addGas(((Planet) all[p]).getResources());
				}
				// else not within, do nothing
				
			}
		}
		
		//done
		
	}
	
	public void gamePlay() {
		status = "Test";
		while (status.equals("Test")) {
		}
	} 
	 
	public static void main(String[] args) {
		Controller control = new Controller();
		control.startUp();
		control.setUp();
		control.gamePlay();

	}

}
