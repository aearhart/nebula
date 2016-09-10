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
	//private Player p2;
	//private Player currPlayer;

	private String status;
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
		else {
			sat = new Station(this, Integer.parseInt(s.substring(7, 10)), Integer.parseInt(s.substring(11, 14)), Integer.parseInt(s.substring(15, 18)), s.substring(25));		
		}
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
		player = new Player(this, p, p.substring(1));
		update();
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
			startUp();
			for (int i = 1; i < s.length; i++) {
				if (s[i].charAt(0) == 's')
					addSatellite(s[i]);
				else if (s[i].charAt(0) == 'P') {
					createPlayer(s[i]);
				}
				else if (s[i].charAt(0) == 'E') {
					addToMap();
				}
			}
		}
		}
	}
	
	public void getCurrentState(String state) {
		currentState = state += " ";
		for (Satellite s: satellites) {
			currentState += s.printState();
		}
		currentState += player.playerState();
	}
	
	
	
	public String getStatus() {
		return status;
	}
	
	public void setStatus(String s) {
		status = s;
	}
	
	public Player getCurrPlayer() {
		return player;
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
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		ClientController control = new ClientController();
		control.connectToServer();	
		control.getMessage();
		control.readMessage();
		control.setUp();
		control.getCurrentState("StationChosen");
		control.sendMessage();
		System.exit(0);
	}

}
