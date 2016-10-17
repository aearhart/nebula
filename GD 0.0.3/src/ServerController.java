import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

public class ServerController {
	
	static ServerSocket server;
	static Socket player1;
	static Socket player2;
	static DataOutputStream out;
	static DataInputStream in;
	private static String input = "";
	private static String currentState = "";
	private Boolean valid = true;
	private Boolean noName = true;
	
	private Player p1 = new Player(this);
	private Player p2 = new Player(this);
	
	private String currentPlayer = "P0";
	private Socket currentSocket;
	private Player currPlayerObj;
	private String winner = "P0";

	private List<Satellite> satellites = new ArrayList<Satellite>();
	
	private List<String> planetNames = new ArrayList<String>();  
	//(Arrays.asList("Phobos", "Gaspra", "Eros", "Ida I", "Kalliope", "Jupiter XV", "Metis", "Callirrhoe", "Isonoe", "Praxidike", "Hyperion", "Paaliaq", "Mundilfari", "Saturn XXVVI", "Aegir", "Farbuati", "Setebos", "Trinculo", "Halimede", "Psamathe", "Kerberos", "Makemake", "Hi'iaka"));		
	
			
	public ServerController() {
	}
	
	/* SERVER - CLIENT methods */
	
	private void error() {
		System.out.println("FAILED");
		System.exit(1);
	}
	
	private void error(String s) {
		System.out.println(s);
		System.exit(1);
	}
	
	public  void getMessage(Socket socket) {
		// input from socket goes to variable input
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
		System.out.println("Receiving information from Socket " + socket.getInetAddress() + "...  " + input);
	}
	
	public void setName() {
		noName = false;
	}
	
	public void sendMessage(Socket socket)  {
		// sends out currentState to socket

		try {
			out = new DataOutputStream(socket.getOutputStream());
		} catch (IOException e) {
			error("Unable to contact client");
		}
		try {
			out.writeUTF(currentState);
		} catch (IOException e) {
			error("Unable to send message");
		}
		System.out.println("Sent message to Socket " + socket.getInetAddress() + ": \n" + currentState);
	}
	
	public void connectToClients() {
		// Create server and connect to two clients.
		
		// Create server
		System.out.println("Starting server...");
		try {
			server = new ServerSocket(7777);
		} catch (IOException e) {
			error();
		}
		System.out.println("Server started.");
		
		// Connect to Player 1
		try {
			player1 = server.accept();
		} catch (IOException e) {
			//e.printStackTrace();
			error("Could not connect to Player 1");
		}
		System.out.println("Successful connection from: " + player1.getInetAddress());
		
		// Connect to Player 2
		try {
			player2 = server.accept();
		} catch (IOException e) {
			//e.printStackTrace();
			error("Could not connect to Player 2");
		} 
		System.out.println("Successful connection from: " + player2.getInetAddress());	
		
	}

	/* Basic Gameplay methods */
	
	private void switchCurrPlayer() {
		if (currentPlayer.equals("P1")) {
			currentPlayer = "P2";
			currentSocket = player2;
			currPlayerObj = p2;
			}
		else {
			currentPlayer = "P1";
			currentSocket = player1;
			currPlayerObj = p1;
		}
	}
	
	public Station getStation(String str) {
		/* given str name, find the station matching that name */
		for (Satellite sat: satellites) {
			if ((sat.getNum()).equals(str))
				return (Station) sat;
		}
		return null;
	}
	
	public Satellite getSat(String str) {
		/* given str name, find the station matching that name */
		for (Satellite sat: satellites) {
			if (sat.getNum().equals(str))
				return sat;
		}
		return null;
	}
	
	public int updatePlayer(String s[], int i, int p) {
		if (s[p].equals("P1")) {
			i = p1.update(s, i);
		}
		else {
			i = p2.update(s, i);
		}
		return i;
	}
	
	public void read() {
		// input into currentState
		String s[] = input.split(Globals.delim);
		
		// s[0] = claim station
		// s[1] = currentPlayer
		// s[2] = player
		int i = updatePlayer(s, 2, 3);
		i = updatePlayer(s, i, i+1);
		int numOfSat = Integer.parseInt(s[i++]);
		for (int j = 0; j < numOfSat; j++) {
			i = getSat(s[i+2]).update(s, i);
		}	
	}	
	
	public void definePlayer() {
		// parse input into current player
		String[] s = input.split(Globals.delim);
		updatePlayer(s, 2, 1);
	}
	
	public void getCurrentState(String state) {
		// works for turns
		ArrayList<String> aList = new ArrayList<String>();
		aList.add(state);
		aList.add(currentPlayer);
		aList.add(p1.printState());
		aList.add(p2.printState());
		aList.add(Integer.toString(satellites.size()));
		for (Satellite s : satellites) {
			aList.add(s.printState());
		}
		
		currentState = Globals.addDelims(aList);
	}
	
	public void validate() {
		// validate input, if invalid will need to eventually re-ask current socket
		valid = true;
	}
	
	
	/* GAMEPLAY methods */
	
	
	/* first contact */
	public void firstContact() {
		currentState = "firstContact" + Globals.delim + currentPlayer;
		sendMessage(currentSocket);
		getMessage(currentSocket);
		definePlayer();
	}
	
	public void createMap() {
		// create the map
		
		Satellite s0 = new Sun();
		satellites.add(s0);
		int key = 1;
		
		Random ran = new Random();
		int ws = Globals.winSize;
		// location of satellites
		double[] xs = {.261843*ws, .500*ws, .738157*ws, .738157*ws, .500*ws, .261843*ws};
		double[] ys = {.3625*ws, .225*ws, .3625*ws, .6375*ws, .775*ws, .6375*ws};

		
		List<Integer> sectors = new ArrayList<Integer>();
		sectors.add(0); sectors.add(1); sectors.add(2); sectors.add(3); sectors.add(4); sectors.add(5);
		
		// number of satellites = 4
		for (int i = 0; i < 4; i++) {
			// BASE STATION
			int r = ran.nextInt(sectors.size()); // 6 sectors
			int sect = sectors.get(r);
			sectors.remove(r);
			
			Satellite s = new DefaultStation((int)xs[sect], (int)ys[sect], 30, "s" + Integer.toString(key++));
			satellites.add(s);
			
			
			
			String plans = ((Station) s).getPlanetsToCreate();
			for (int j = 0; j < plans.length(); j+=2) {
				Satellite p;
				if (plans.charAt(j) == 'G') {
					p = new GasPlanet(0, 0, plans.substring(j+1, j+2), "s" + Integer.toString(key++));
				}
				else if (plans.charAt(j) == 'M') {
					p = new MineralPlanet(0, 0, plans.substring(j+1, j+2), "s" + Integer.toString(key++));
				}
				else {
					p = new WaterPlanet(0, 0, plans.substring(j+1, j+2), "s" + Integer.toString(key++));
				}
				((Station)(s)).placePlanet(p, 0);
				satellites.add(p);
					
			}
			
			
		}

	}
	
	public void createMapOld() {
		// create the map

		Satellite s00 = new Sun();
		Satellite s01 = new WaterPlanet(450, 350, "m", "s01");
		Satellite s02 = new WaterPlanet(766, 388, "l", "s02");
		Satellite s03 = new WaterPlanet(140, 142, "l", "s03");
		Satellite s04 = new	WaterPlanet(217, 530, "m", "s04");
		Satellite s05 = new MineralPlanet(800, 104, "m", "s05");
		Satellite s06 = new MineralPlanet(657, 720,"s", "s06");
		Satellite s07 = new MineralPlanet(340, 460, "s", "s07");
		Satellite s08 = new MineralPlanet(500, 680, "s", "s08");
		Satellite s09 = new GasPlanet(645, 40, "s", "s09"); 
		Satellite s10 = new GasPlanet(300, 200, "l", "s10");
		Satellite s11 = new GasPlanet(240, 730, "m", "s11");
		Satellite s12 = new GasPlanet(700, 600, "m", "s12");
		Satellite s13 = new Station(700, 100, 30, "s13");
		Satellite s14 = new Station(313, 545, 30, "s14");
		Satellite s15 = new Station(168, 232, 30, "s15");
		Satellite s16 = new Station(826, 630, 30,  "s16");

		satellites.add(s00);
		satellites.add(s01);
		satellites.add(s02);
		satellites.add(s03);
		satellites.add(s04);
		satellites.add(s05);
		satellites.add(s06);
		satellites.add(s07);
		satellites.add(s09);
		satellites.add(s10);
		satellites.add(s11);
		satellites.add(s12);
		satellites.add(s13);
		satellites.add(s14);
		satellites.add(s15);
		satellites.add(s16);
	}
	
	
	
	
	public String chooseName() {
		if (planetNames.size() == 0) {
			planetNames.add("Phobos");
			planetNames.add("Gaspra");
			planetNames.add("Eros");
			planetNames.add("Ida I");
			planetNames.add("Kalliope");
			planetNames.add("Jupiter XV");
			planetNames.add("Metis");
			planetNames.add("Callirrhoe");
			planetNames.add("Isonoe");
			planetNames.add("Praxidike");
			planetNames.add("Hyperion");
			planetNames.add("Paaliaq");
			planetNames.add("Mundilfari");
			planetNames.add("Saturn XXVVI");
			planetNames.add("Aegir");
			planetNames.add("Farbuati");
			planetNames.add("Setebos");
			planetNames.add("Trinculo");
			planetNames.add("Halimede");
			planetNames.add("Psamathe");
			planetNames.add("Kerberos");
			planetNames.add("Makemake");
			planetNames.add("Hi'iaka");
		}
		Random ran = new Random();
		int i = ran.nextInt(planetNames.size());
		String p = planetNames.get(i);
		planetNames.remove(i);
		System.out.println(i + " " +p);
		return p;
	}
	
	public void startup() {
		createMap();
		ArrayList<String> aList = new ArrayList<String>();
		aList.add("start up");
		aList.add(currentPlayer);
		aList.add(p1.printState());
		aList.add(p2.printState());
		aList.add(Integer.toString(satellites.size()));
		for (Satellite s : satellites) {
			s.setName(chooseName());// name them
			aList.add(s.printState());
		}
		
		currentState = Globals.addDelims(aList);
		sendMessage(currentSocket);
		switchCurrPlayer();
		sendMessage(currentSocket);
	}
	
	public void claimStation() {
		ArrayList<String> aList = new ArrayList<String>();
		aList.add("claim station");
		aList.add(currentPlayer);
		aList.add(p1.printState());
		aList.add(p2.printState());
		aList.add(Integer.toString(satellites.size()));
		for (Satellite s : satellites) {
			aList.add(s.printState());
		}
		
		currentState = Globals.addDelims(aList);
		
		sendMessage(currentSocket);
		getMessage(currentSocket);
		read();
	}
	
	public void setUp() {
		
		switchCurrPlayer();
		firstContact();

		switchCurrPlayer();
		firstContact();
		
		switchCurrPlayer();
		
		startup();
		
		switchCurrPlayer();
		claimStation();
		switchCurrPlayer();
		claimStation();
	}
	
	public Boolean gameEnd() {
		// has the game ended?
		if (Globals.playerWin(p1)) {
			winner = p1.getNum();
			return true;

		}
		else if (Globals.playerWin(p2)) {
			winner = p2.getNum();
			return true;
		}
		return false;
	} 
	
	public void win() {
		ArrayList<String> aList = new ArrayList<String>();
		aList.add("WIN");
		aList.add(winner);
		
		aList.add(p1.printState());
		aList.add(p2.printState());
		aList.add(Integer.toString(satellites.size()));
		for (Satellite s : satellites) {
			aList.add(s.printState());
		}
		
		currentState = Globals.addDelims(aList);
		
		sendMessage(player1);
		sendMessage(player2);
		System.out.println("Winning state: \n" + currentState);
	}
	
	public void turn() {
		// server sends state to current player
		if (gameEnd()) {
			win();
			System.exit(0);
		}
		
		getCurrentState("turn");
		sendMessage(currentSocket);
		getMessage(currentSocket);
		validate();
		read(); // parse input into current state and update
		switchCurrPlayer();
		turn();
	}
	
	public static void main(String[] args) {
		ServerController server = new ServerController();
		server.connectToClients();
		server.setUp();
		server.turn();
		
		System.out.println("END");
	}



}
