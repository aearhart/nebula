import java.awt.Color;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

public class ClientController2 {

	// socket info
	static Socket socket;
	static DataInputStream in;
	static DataOutputStream out;
	private static String input;
	private static String currentState = "";
	
	// components
	private Window window;
	private Map map;
	private InfoPanel infoPanel;
	private InfoPanel2 infoPanel2;
	private List<Satellite> satellites = new ArrayList<Satellite>();
	private Player player;
	private Player opponent;
	private String currentPlayer = "P0";
	private String status = "";
	
	// AoI information
	int AoIx = 0;
	int AoIy = 0;
	int AoIs = 0;
	Color AoIc;
	
	public ClientController2() {

	}

	/* SERVER METHODS */
	
	private void error() {
		System.out.println("FAILED");
		System.exit(1);
	}
	
	private void error(String s) {
		System.out.println(s);
		System.exit(1);
	}
	
	public void getMessage()  {
		/* reads input from server into variable input */
		try {
			in = new DataInputStream(socket.getInputStream());
		} catch (IOException e) {
			error();
		}
		try {
			input = in.readUTF();
		} catch (IOException e) {
			error();
		}
		System.out.println("Receiving information...  " + input);
	}
	
	public void sendMessage() {
		/* sends currentState to server */
		try {
			out = new DataOutputStream(socket.getOutputStream());
		} catch (IOException e) {
			error();
		}
		try {
			out.writeUTF(currentState);
		} catch (IOException e) {
			error();
		}
		System.out.println("Message sent: " + currentState);
		} 
	
	public void connectToServer() {
		/* connects to server */
		System.out.println("Connecting to server...");
		try {
			socket = new Socket("localhost", 7777); // local server
			//	socket = new Socket("70.95.122.247", 7777);
			//	socket = new Socket("2605:e000:1c02:8e:9587:d3d9:c5cd:9780", 7777); // computer server
		} catch (IOException e) {
			error("Unable to connect to server.");
			//e.printStackTrace();
		}

		System.out.println("Connection established.");
	}
	
	
	/* CREATING/UPDATING GAME INFO */
	
	public void getCurrentState(String state) {
		/* create state string:
		 * STATE_TYPE CURRENT_PLAYER CURRENT_PLAYER_INFO OPPONENT_INFO SATELLITE_INFO ... END
		 */
		currentState = state += " ";
		currentState += "P" + opponent.getNum() + " ";
		currentState += player.playerState();
		currentState += opponent.playerState();
		for (Satellite s: satellites) {
			currentState += s.printState();
		}
		currentState += " END";
	}
	
	public void readMessage() {
		/* interprets input from server */
		String[] s = input.split(" ");
		System.out.println("State: " + s[0] + "");
		
		switch(s[0]) {
		case "startUp": { // create the components
			startUp();
			for (int i = 1; i < s.length; i++) {
				if (s[i].length() > 0) { // satellite info
					if (s[i].charAt(0) == 's')
						addSatellite(s[i]);
					else if (s[i].charAt(0) == 'P' && s[i].length() < 3) // current player info
						currentPlayer = s[i];
					else if (s[i].charAt(0) == 'P') // player info
						createPlayer(s[i]);	
					else if (s[i].charAt(0) == 'E') // end of input
						addToMap();
				}
			}
			return;
		}
		case "turn": { // interpret client's turn and update components
			for (int i = 1; i < s.length; i++) { // update info
				//System.out.println("+" + s[i] + "+");
				if (s[i].length() == 0) {
					System.out.println("bad string");
				}
				else if (s[i].charAt(0) == 'P' && s[i].length() < 3) // current player info
 					currentPlayer = s[i];
 				else if (s[i].charAt(0) == 'P' && s[i].length() > 4) { // player info
 					if (s[i].substring(0, 2).equals(currentPlayer)) { // current player
 						player.update(s[i]);
 					}
 					else {
 						opponent.update(s[i]); // opponent
 					}
 				}
 				else if (s[i].charAt(0) == 's') { // satellite info
 					satellites.get(Integer.parseInt(s[i].substring(1, 3))).update(s[i]);
 				}
			}
			updateMap(); // update map
			
			// begin turn
			status = "collectResources";
			turn();
			return;
		}
		case "WIN": { // there has been a winner
			String winner = s[1];
			System.out.println(winner + " just won.");
			printToInstructionArea(winner + " just won. Thank you for playing. Click anywhere to close");
			this.setStatus("WIN");
			return;
		}
		} // end case
	}
	
	public void startUp() { // create components
		status = "StartUp";
		
		window = new Window(this);
		
		infoPanel = new InfoPanel(this);
		window.add(infoPanel);
		
		map = new Map(this);
		window.add(map);
		
		infoPanel2 = new InfoPanel2(this);
		window.add(infoPanel2);
		
		window.pack();
		window.update();
	}
	
	public Station getStation(String str) {
		/* given str name, find the station matching that name */
		for (Satellite sat: satellites) {
			if (("s" + sat.getName()).equals(str))
				return (Station) sat;
		}
		return null;
	}
	
	public void addSatellite(String s) {
		/* given string s info, create a satellite 
		 * 	 "s__=T_X___Y___size___resource___owner_name__"
		 *   "0   4 6   10   14    18         22    24      */
		
		Satellite sat;
		// determine type
		if (s.charAt(5) == 'W') { // water planet
			sat = new WaterPlanet(this, Integer.parseInt(s.substring(7, 10)), Integer.parseInt(s.substring(11, 14)), Integer.parseInt(s.substring(15, 18)), Integer.parseInt(s.substring(19, 22)), s.substring(25));
		}
		else if (s.charAt(5) == 'G') { // gas planet
			sat = new GasPlanet(this, Integer.parseInt(s.substring(7, 10)), Integer.parseInt(s.substring(11, 14)), Integer.parseInt(s.substring(15, 18)), Integer.parseInt(s.substring(19, 22)), s.substring(25));
		}
		else if (s.charAt(5) == 'M'){ // mineral planet
			sat = new MineralPlanet(this, Integer.parseInt(s.substring(7, 10)), Integer.parseInt(s.substring(11, 14)), Integer.parseInt(s.substring(15, 18)), Integer.parseInt(s.substring(19, 22)), s.substring(25));
		}
		else if (s.charAt(5) == 'O'){ // the sun
			sat = new Sun(this);
		}
		else { // space station
			sat = new Station(this, Integer.parseInt(s.substring(7, 10)), Integer.parseInt(s.substring(11, 14)), Integer.parseInt(s.substring(15, 18)), s.substring(25));		
		}
		sat.setOwner(s.substring(23, 24));
		satellites.add(sat); // add to list of satellites
	}
	
	public void addToMap() {
		/* add satellites to map */
		for (Satellite s: satellites) {
			map.add(s); 
			s.setBounds(s.getLocX(), s.getLocY(), s.getBoundSize(), s.getBoundSize());
		}
	}
	
	public void createPlayer(String p) {
		/* given string p information, create a player */
		
		// eventually, ask for a name for the player
		if (p.charAt(1) == currentPlayer.charAt(1)) { // this is the current player
			player = new Player(this, "Player", p.substring(1, 2));
			System.out.println("CURR:::: " + p.charAt(1));
			printToPlayerArea();
		}
		else {
			opponent = new Player(this, p);
		}		
	}
	
	public void updateMap() {
		for (Satellite sat: satellites) {
			if (sat instanceof WaterPlanet) {
				((WaterPlanet)sat).repaint();
			}
			else if (sat instanceof MineralPlanet) {
				((MineralPlanet)sat).repaint();
			}
			else if (sat instanceof GasPlanet) {
				((GasPlanet)sat).repaint();
			}
			else if (sat instanceof Station) {
				((Station)sat).repaint();
			}
		}
	}
	

	/* GAME TURN */
	
	public void setUp() {
		/* claim a space station set up */
		status = "SetUp";

		printToInstructionArea(player.getName() + ": Click on a space station to claim it");
		status = "Claiming";
		while (status.equals("Claiming")) {
			System.out.print("");
		}
		
		status = "";
	}
	
	public void collectResources() {
		/* current player collects resources in AoI */
		String str = "";
		// for each station
		str += "\nResources collected:";
		for (Station stat: player.getStations()) {
			player.addWater(1);
			player.addGas(1);
			player.addMineral(1);
			for (Satellite sat: satellites) {
				if ( !(sat instanceof Station) && (!(sat instanceof Sun)) && (withinDistance(stat, sat))) {
					str += "\n+" + ((Planet) sat).getResources() + " " + 
							((Planet) sat).getType() + " from " + sat.getName() + ".";

					//System.out.println("planet: " + all[p].getMidX() + " " + all[p].getMidY());
					//System.out.println("Matched with " + all[p].getName() + ", getting " + ((Planet) all[p]).getResources() + ".");

					if (sat instanceof WaterPlanet) {
						player.addWater(((Planet) sat).getResources());
					}
					else if (sat instanceof MineralPlanet){
						player.addMineral(((Planet) sat).getResources());
					}
					else
						player.addGas(((Planet) sat).getResources());
				}
				}
		}
		printToPlayerArea(player.info() + str);
		status = "Upgrade";
		//System.out.println("resources collected");
		//done
		
	}
	
	public void upgradeTime() {
		/* upgrade or buy a space station/planet */
		printToInstructionArea("Click on a planet or space station to upgrade.");
		while (status.equals("Upgrade")) { // continue until player successfully spends turn
			System.out.println("!"); 
		}
	}
	
	public void turn() {
		/* one turn for current player */
		collectResources();
		upgradeTime();
		// update server
		getCurrentState("turn");
		sendMessage();
		printToInstructionArea("Please wait. Opponent's turn.");
		// wait for next turn
		status = "Wait";
		getMessage();
		readMessage();
	}
	
	
	/* DRAWING AOI */
	
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
		AoIc = new Color(88, 232, 232, 0);
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
	
	
	/* BASIC METHODS */
	
	public static void close() {
		/* close the game */
		System.exit(0);
	}
	
	public String getStatus() {
		return status;
	}
	
	public void setStatus(String s) {
		status = s;
	}
	
	public String getCurrPlayer() { // return string num "P_" of current player
		return currentPlayer; 
	}
	
	public Player getPlayer() {
		return player;
	}
	
	public Player getOpponent() {
		return opponent;
	}
	
	public void printToInstructionArea(String s) {
		infoPanel.printToInstructionArea(s);
	}
	
	public void printToPlayerArea() {
		infoPanel.printToPlayerArea(player.info());
	}
	
	public void printToPlayerArea(String s) {
		infoPanel.printToPlayerArea(s);
	}
	
	public void printToHoverArea(String s) {
		infoPanel2.printToHoverArea(s);
	}
	
	public void printToHoverArea() { // default
		infoPanel2.printToHoverArea("Hover over a satellite for more information.");
	}	
	
	
	/* MAIN */
	
	public static void main(String[] args) {

		
		ClientController2 control = new ClientController2();
		control.connectToServer();	
		control.getMessage();
		control.readMessage();
		control.setUp();
		control.getCurrentState("startUp");
		control.sendMessage();
		control.getMessage();
		control.readMessage();
		close();
	}

}
