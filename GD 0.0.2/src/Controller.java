import java.awt.Color;
import java.awt.Graphics;

import javax.swing.JButton;

public class Controller {

	private Window window;
	private Map map;
	private InfoPanel infoPanel;
	private InfoPanel2 infoPanel2;
	private Satellite[] all;
	private Player p1;
	private Player p2;
	private Player currPlayer;
	private Player winner;
	private String status;
	int AoIx = 0;
	int AoIy = 0;
	int AoIs = 0;
	Color AoIc = Color.RED;
	
	public String getStatus() {
		return status;
	}
	
	public void setStatus(String s) {
		status = s;
	}
	
	public Player getCurrPlayer() {
		return currPlayer;
	}
	
	public void printToInstructionArea(String s) {
		infoPanel.printToInstructionArea(s);
	}
	
	public void printToPlayerArea(String s) {
		infoPanel.printToPlayerArea(s);
	}
	
	public void printToHoverArea(String s) {
		infoPanel2.printToHoverArea(s);
	}
	
	public void startUp() {

		status = "StartUp";
		
		window = new Window(this);
		
		infoPanel = new InfoPanel(this);
		window.add(infoPanel);
		
		//newPanel = new InfoPanel(this);
		//window.add(newPanel);
		
		map = new Map(this);
		window.add(map);
//		window.getContentPane().add(map);
		
		infoPanel2 = new InfoPanel2(this);
		window.add(infoPanel2);
		
		//Map map2 = new Map(this);
		//window.add(map2);
		//		window.getContentPane().add(map2);
		
		
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
			s.setBounds(s.getLocX(), s.getLocY(), s.getBoundSize(), s.getBoundSize());
		}
		
		p1 = new Player(this, "Player 1");
		p2 = new Player(this, "Player 2");
		p2.setColor(Color.MAGENTA);
		status = "";
	}
	
	public void setUp() {
		status = "SetUp";
		
		currPlayer = p1;
		update();
		printToInstructionArea(currPlayer.getName() + ": Click on a space station to claim it");
		status = "Claiming";
		while (status.equals("Claiming")) {
			System.out.print("");
		}
		switchPlayers();
		printToInstructionArea(currPlayer.getName() + ": Click on a space station to claim it");
		status = "Claiming";
		while (status.equals("Claiming")) {
			System.out.print("");
		}
		switchPlayers();
		status = "";
	}
	
	public void switchPlayers() { // how else can we determine the next player?
		if (currPlayer == p1)
			currPlayer = p2;
		else
			currPlayer = p1;
		update();
	}
	
	public void update() {
		printToPlayerArea(currPlayer.info());
	}
	
	public void update(String str) {
		printToPlayerArea(str);
	}
	
	public void drawAoI(Station s) {
		//print("drawing AoI");
		AoIx = s.getMidX() - s.getAoI();
		AoIy = s.getMidY() - s.getAoI();
		AoIs = s.getAoI()*2;
		AoIc = new Color(100, 100, 100, 255);
		map.repaint();
		window.update();
	}
	
	public void removeAoI() {
		//print("removing AoI");
		AoIc = new Color(100, 100, 100, 0);
		map.repaint();
		window.update();
	}
	
	public Boolean withinDistance(Station s, Satellite p) {
		// calculate distance
		Integer distance = (int) Math.sqrt(Math.pow(Math.abs(s.getMidX() - p.getMidX()), 2) + 
				Math.pow(Math.abs(s.getMidY() - p.getMidY()), 2));
		//System.out.println("distance: " + distance);
		if (distance < (s.getAoI() + p.getSz())) {
			return true;
		}
		return false;
	}

	public void collectResources() {
		/* current player collects resources in AoI */
		String str = "";
		Station[] stations = currPlayer.getStations();
		// for each station
		str += "\nResources collected:";
		for (int s = 0; s < currPlayer.getNumStations(); s++) {
			// see which planets are within it
			//System.out.println("Satellite: " + stations[s].getMidX() + " " + stations[s].getMidY());
			currPlayer.addWater(1);
			currPlayer.addGas(1);
			currPlayer.addMineral(1);
			for (int p = 0; p < all.length; p++) {
				// is it a station or sun? --> ignore
				// are they within?
				
				if ( !(all[p] instanceof Station) && (!(all[p] instanceof Sun)) && (withinDistance(stations[s], all[p]))) {
					str += "\n+" + ((Planet) all[p]).getResources() + " " + 
							((Planet) all[p]).getType() + " from " + all[p].getName() + ".";

					//System.out.println("planet: " + all[p].getMidX() + " " + all[p].getMidY());
					//System.out.println("Matched with " + all[p].getName() + ", getting " + ((Planet) all[p]).getResources() + ".");

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
		update(currPlayer.info() + str);
		status = "Upgrade";
		System.out.println("resources collected");
		//done
		
	}
	
	public void upgradeTime() {
		printToInstructionArea("Click on a planet or space station to upgrade.");
		//status = "Upgrade";
	}
	
	
	public Boolean endGame() {
		Boolean win = true;
		if (p1.getGas() > 50 && p1.getMineral() > 50 && p1.getWater() > 50) {
			win = false;
			winner = p1;
		}
		else if (p2.getGas() > 50 && p2.getMineral() > 50 && p2.getWater() > 50) {
			win = false;
			winner = p2;
		}
		return win;
	}
	
	public void gameWon() {
		System.out.println(winner.getName() + " just won.");
		printToInstructionArea(winner.getName() + " just won. Please exit the window.");
	}
	
	public void gamePlay() {
		status = "collectResources";
		while (endGame()) { // returns true if game not end.
		// collect resources for each satellite under the current player
			//System.out.println(status);

			if (status.equals("collectResources")) {
				collectResources(); 
			}
		/*status = "Test";
		while (status.equals("Test")) {
		}*/
			upgradeTime();
			if (status == "collectResources") 
			switchPlayers();
		}
		gameWon();
	} 
	 
	public static void main(String[] args) {
		Controller control = new Controller();
		control.startUp();
		control.setUp();
		control.gamePlay();

	}

}
