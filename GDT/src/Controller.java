
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
	
	public void startUp() {

		status = "StartUp";
		
		window = new Window(this);
		map = new Map(this);
		window.getContentPane().add(map);
		window.pack();
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
	
	public void setUp() {
		status = "SetUp";
		
		currPlayer = p1;
		
		System.out.println(currPlayer.getName() + ": Click on a space station to claim it");
		status = "Claiming";
		while (status.equals("Claiming")) {
			System.out.print("");
		}
		
		
		
		status = "";

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
					System.out.println("Matched with " + all[p].getName() + ", getting " + ((Planet) all[p]).getResources() + ".");
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
		System.out.println("Before collecting: w---g---m");
		System.out.println("                   " + currPlayer.getWater() + "   " + currPlayer.getGas() + "   " + currPlayer.getMineral());
		collectResources();
		System.out.println("After  collecting: w---g---m");
		System.out.println("                   " + currPlayer.getWater() + "   " + currPlayer.getGas() + "   " + currPlayer.getMineral());
		
	} 
	 
	public static void main(String[] args) {
		Controller control = new Controller();
		control.startUp();
		control.setUp();
		control.gamePlay();

	}

}
