import java.awt.Color;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

public class ClientController {

	static Socket socket;
	static DataInputStream in;
	static DataOutputStream out;
	private static String input;
	private static String currentState = "";
	
	private Window window;
	private Map map;
	private InfoPanel infoPanel;
	private InfoPanel2 infoPanel2;
	private List<Satellite> satellites = new ArrayList<Satellite>();
	private Player player;
	private Player opponent;
	private String currentPlayer = "P0";
	private static String state = "";
	//private Player p2;
	//private Player currPlayer;

	private String status = "";
	int AoIx = 0;
	int AoIy = 0;
	int AoIs = 0;
	Color AoIc = Color.RED;
	
	public ClientController() {
		// TODO Auto-generated constructor stub
	}

	private void error() {
		System.out.println("FAILED");
		System.exit(1);
	}
	
	private void error(String s) {
		System.out.println(s);
		System.exit(1);
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
		
		infoPanel2 = new InfoPanel2(this);
		window.add(infoPanel2);
		
		window.pack();
		window.update();
		Satellite satellite = new Sun(this);
		// ??Planet(x, y, s, numResources, name)
		satellites.add(satellite);
	}
	
	public Station getStation(String str) {
		for (Satellite sat: satellites) {
			if (("s" + sat.getName()).equals(str))
				return (Station) sat;
		}
		return null;
	}
	
	public void addSatellite(String s) {
		System.out.println("Adding satellite: " + s);
		Satellite sat;
		// "s__=T_X___Y___size___resource___owner_name__"
		// "0   4 6   10   14    18         22    24
		
		if (s.charAt(5) == 'W') {
			sat = new WaterPlanet(this, Integer.parseInt(s.substring(7, 10)), Integer.parseInt(s.substring(11, 14)), Integer.parseInt(s.substring(15, 18)), Integer.parseInt(s.substring(19, 22)), s.substring(25));
		}
		else if (s.charAt(5) == 'G') {
			sat = new GasPlanet(this, Integer.parseInt(s.substring(7, 10)), Integer.parseInt(s.substring(11, 14)), Integer.parseInt(s.substring(15, 18)), Integer.parseInt(s.substring(19, 22)), s.substring(25));
		}
		else if (s.charAt(5) == 'M'){
			sat = new MineralPlanet(this, Integer.parseInt(s.substring(7, 10)), Integer.parseInt(s.substring(11, 14)), Integer.parseInt(s.substring(15, 18)), Integer.parseInt(s.substring(19, 22)), s.substring(25));
		}
		else if (s.charAt(5) == 'O'){
			sat = new Sun(this);
		}
		else {
			sat = new Station(this, Integer.parseInt(s.substring(7, 10)), Integer.parseInt(s.substring(11, 14)), Integer.parseInt(s.substring(15, 18)), s.substring(25));		
		}
		sat.setOwner(s.substring(23, 24));
		satellites.add(sat);
	}
	
	public void addToMap() {
		
		for (Satellite s: satellites) {
			map.add(s); 
			s.setBounds(s.getLocX(), s.getLocY(), s.getBoundSize(), s.getBoundSize());
		}
		/*

		p2 = new Player(this, "Player 2");
		p2.setColor(Color.MAGENTA);
		status = "";*/
	}
	
	public void createPlayer(String p) {
		// eventually, ask for a name for the player
		if (p.charAt(1) == currentPlayer.charAt(1)) { // this is the current player
			player = new Player(this, "Player", p.substring(1, 2));
			System.out.println("CURR:::: " + p.charAt(1));
			update();
		}
		else {
			opponent = new Player(this, p);
		}
			
	}
	
	public void getMessage()  {
		// reads input into variable input
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
		System.out.println("Connecting to server...");
		try {
			socket = new Socket("localhost", 7777); // local server
			//	socket = new Socket("70.95.122.247", 7777);
			//	socket = new Socket("2605:e000:1c02:8e:9d7f:687b:3d6:73f5", 7777); // computer server
		} catch (IOException e) {
			error("Unable to connect to server.");
			//e.printStackTrace();
		}

		System.out.println("Connection established.");
	}
	
	public void readMessage() {
		String[] s = input.split(" ");
		System.out.println("State: " + s[0] + "");
		switch(s[0]) {
		case "startUp": {
			state = "startUp";
			startUp();
			for (int i = 1; i < s.length; i++) {
				if (s[i].length() > 0) {
					if (s[i].charAt(0) == 's')
						addSatellite(s[i]);
					else if (s[i].charAt(0) == 'P' && s[i].length() < 3) 
						currentPlayer = s[i];
					else if (s[i].charAt(0) == 'P')
						createPlayer(s[i]);	
					else if (s[i].charAt(0) == 'E') 
						addToMap();
					
				}
			}
			return;
		}
		case "turn": {
			for (int i = 1; i < s.length; i++) { // update info
				//System.out.println("+" + s[i] + "+");
				if (s[i].length() == 0) {
					System.out.println("bad string");
				}
				else if (s[i].charAt(0) == 'P' && s[i].length() < 3)
 					currentPlayer = s[i];
 				else if (s[i].charAt(0) == 'P' && s[i].length() > 4) {
 					System.out.println("UPDATE PLAYER: " + s[i].substring(0,  2));
 					System.out.println("currentPlayer: " + currentPlayer);
 					if (s[i].substring(0, 2).equals(currentPlayer)) {
 						player.update(s[i]);
 						System.out.println("currentPlayer updated");
 					}
 					else {
 						opponent.update(s[i]);
 						System.out.println("opponent updated");
 					}
 				}
 				else if (s[i].charAt(0) == 's') {
 					satellites.get(Integer.parseInt(s[i].substring(1, 3))).update(s[i]);
 				}
			}
			status = "collectResources";
			turn();
			return;
		}
		case "WIN": {
			String winner = s[1];
			System.out.println(winner + " just won.");
			printToInstructionArea(winner + " just won. Thank you for playing. Click anywhere to close");
			this.setStatus("WIN");
			return;
		}
		}
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
		update(player.info() + str);
		status = "Upgrade";
		System.out.println("resources collected");
		//done
		
	}
	
	public void upgradeTime() {
		printToInstructionArea("Click on a planet or space station to upgrade.");
		//status = "Upgrade";
	}
	
	public void turn() {
		collectResources();
		upgradeTime();

		while (status.equals("Upgrade")) {
			System.out.println("!");
		}
		System.out.println("here");
		getCurrentState("turn");
		sendMessage();
		printToInstructionArea("Please wait. Opponent's turn.");
		getMessage();
		readMessage();
	}
	
	public static void close() {
		System.exit(0);
	}
	
	public void getCurrentState(String state) {
		currentState = state += " ";
		currentState += "P" + opponent.getNum() + " ";
		currentState += player.playerState();
		currentState += opponent.playerState();
		for (Satellite s: satellites) {
			currentState += s.printState();
		}
		currentState += " END";

	}
	
	
	
	public String getStatus() {
		return status;
	}
	
	public void setStatus(String s) {
		status = s;
	}
	
	public String getCurrPlayer() {
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
	
	public void printToPlayerArea(String s) {
		infoPanel.printToPlayerArea(s);
	}
	
	public void printToHoverArea(String s) {
		infoPanel2.printToHoverArea(s);
	}
	
	public void update() {
		printToPlayerArea(player.info());
	}
	
	public void update(String str) {
		printToPlayerArea(str);
	}
	
	public void setUp() {
		status = "SetUp";

		printToInstructionArea(player.getName() + ": Click on a space station to claim it");
		status = "Claiming";
		while (status.equals("Claiming")) {
			System.out.print("");
		}
		
		status = "";
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
	
	public static void main(String[] args) {

		
		ClientController control = new ClientController();
		control.connectToServer();	
		control.getMessage();
		control.readMessage();
		control.setUp();
		control.getCurrentState(state);
		control.sendMessage();
		control.getMessage();
		control.readMessage();
		close();
	}

}
