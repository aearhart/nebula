/*   ServerController.java
 *    11/11/2016
 *  		Controls the server end of the game. 
 * 				1. Connects to 2 clients
 * 				2. Sets up the game: setup()
 * 					a. firstContact(): Contacts each player with player information and number
 * 					b. startup(): creates the map and sends the currentState to each client
 * 					c. claimStation(): Allows each client to claim a station
 * 				3. Begins the game: turn()
 * 					a. Continually communicates with both clients and sends the currentState
 * 					b. read(): read the currentSocket's message, validating it and changing the currentState
 * 					c. first checks for gameEnd() each time
 * 				4. Game End: win()
 * 					a. Sends the winning state information and closes the game
 */

/* IMPORTS */

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class ServerController {
	
	// Socket variables
	static ServerSocket server;
	static Socket player1;
	static Socket player2;
	static DataOutputStream out;
	static DataInputStream in;
	private static String input = "";
	
	// Gameplay objects
	private Player p1 = new Player(this);
	private Player p2 = new Player(this);
	private List<Satellite> satellites = new ArrayList<Satellite>();
	private List<String> planetNames = new ArrayList<String>();  
	
	// Player information
	private String currentPlayer = "P0";
	private Socket currentSocket;
	private Player currPlayerObj;
	private Socket opponentSocket;
	private String winner = "P0";

	// Current state and validity
	private static String currentState = "";
	private Boolean valid = true;
	
	
	public ServerController() {
	}
	
	
	/* SERVER - CLIENT methods */
	
	private void error(IOException e) { // unknown error
		System.out.println("FAILED");
		e.printStackTrace();
		System.exit(1);
	}
	
	private void error(IOException e, String s) { // error message s
		System.out.println("Error: " + s);
		System.exit(1);
	}
	
	public  void getMessage(Socket socket) {
		/* input from socket goes to variable input */
		try { in = new DataInputStream(socket.getInputStream()); } 
		catch (IOException e) { error(e); }
		try { input = in.readUTF(); } 
		catch (IOException e) {	error(e); }
		System.out.println("Receiving information from Socket " + socket.getInetAddress() + "...  " + input);
	}
	
	public void sendMessage(Socket socket)  {
		/* sends out currentState to socket */
		try { out = new DataOutputStream(socket.getOutputStream()); }
		catch (IOException e) {	error(e, "Lost connection to client " + socket.getInetAddress()); }
		try { out.writeUTF(currentState); } 
		catch (IOException e) { error(e, "Unable to send message to client " + socket.getInetAddress()); }
		System.out.println("Sent message to Socket " + socket.getInetAddress() + ": \n" + currentState);
	}
	
	public void connectToClients() {
		/* Create server and connect to two clients */
		
		// Create server
		System.out.println("Starting server...");
		try { server = new ServerSocket(7777); }
		catch (IOException e) { error(e, "Unable to start server"); }
		System.out.println("Server started.");
		
		// Connect to Player 1
		try { player1 = server.accept(); }
		catch (IOException e) { error(e, "Could not connect to Player 1"); }
		System.out.println("Successful connection from P1: " + player1.getInetAddress());
		
		// Connect to Player 2
		try { player2 = server.accept(); } 
		catch (IOException e) { error(e, "Could not connect to Player 2"); } 
		System.out.println("Successful connection from P2: " + player2.getInetAddress());	
	}

	
	/* Basic Gameplay methods */
	
	private void switchCurrPlayer() {
		/* switch current player variables */
		if (currentPlayer.equals("P1")) { // switch to P2
			currentPlayer = "P2";
			currentSocket = player2;
			currPlayerObj = p2;
			opponentSocket = player1;
			}
		else { // switch to P1
			currentPlayer = "P1";
			currentSocket = player1;
			currPlayerObj = p1;
			opponentSocket = player2;
		}
	}
	
	public Station getStation(String str) {
		/* given str name, find the station matching that name */
		for (Satellite sat: satellites) {
			if ((sat.getNum()).equals(str))
				return (Station) sat;
		}
		error(null, "Station " + str + " does not exist.");
		return null; // error: did not find the station
	}
	
	public Satellite getSat(String str) {
		/* given str name, find the satellite matching that name */
		for (Satellite sat: satellites) {
			if (sat.getNum().equals(str))
				return sat;
		}
		// didn't find it
		error(null, "Satellite " + str + " does not exist.");
		return null;
	}
	
	public int updatePlayer(String s[], int i, int p) {
		/* update info on given player from the state array */
		if (s[p].equals("P1")) { // update player 1
			i = p1.update(s, i);
		}
		else { // update player 2
			i = p2.update(s, i);
		}
		return i; // new index of s array
	}
	
	public void read() {
		/* default reading of game state (currentState)*/
		//TODO: validate input
		
		// input into currentState
		String s[] = input.split(Globals.delim);
		
		// s[0] = state,  s[1] = currentPlayer, s[2] = player
		switch (s[0]) {
		case "MESSAGE": {
			//System.out.println("GOT A MESSAGE");
			currentState = input;
			return;
		}
		case "NOCHANGE": {
			//System.out.println("NO CHANGE");
			if (currentState.equals(""))
				setCurrentState("NOCHANGE");
			return;
		}
		default: {
			int i = updatePlayer(s, 2, 3);
			i = updatePlayer(s, i, i+1);
			int numOfSat = Integer.parseInt(s[i++]);
			for (int j = 0; j < numOfSat; j++) {
				i = getSat(s[i+2]).update(s, i);
			}	
			if (! s[0].equals("claim end"))
				switchCurrPlayer();
			setCurrentState("TURN");
			return;
		}
		}// end switch
	}	
	
	public void setCurrentState(String state) {
		/* set currentState according to given state*/
		
		// works for turns ONLY
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
		// TODO: validate input, if invalid will need to eventually re-ask current socket
		valid = true;
	}
	
	
	/* GAMEPLAY methods */
	
	public void createMap() {
		/* create the map with randomization of all stations and planets */
		
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
			
			// choose between two types
			r = ran.nextInt(2);
			Satellite s;
			if (r == 0) { // default
					s = new DefaultStation((int)xs[sect], (int)ys[sect], 30, "s" + Integer.toString(key++));
					s.setName("Default");
			}
			else { // slow_default
				s = new Default2Station((int)xs[sect], (int)ys[sect], 30, "s" + Integer.toString(key++));
				s.setName("Default2 (slow start)");
			}
			satellites.add(s);
			
	
			String plans = ((Station) s).getPlanetsToCreate();
			int pos = 0;
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
				((Station)(s)).placePlanet(p, pos);
				satellites.add(p);
				pos++;
					
			}
		} // end station loop
		
		// TODO: add planets to sector
		
		// Add 2 spaceships, one for each player, but with no other data (to be updated by player)
		Satellite ship = new Spaceship(p1, "s" + Integer.toString(key++));
		Satellite ship2 = new Spaceship(p2, "s" + Integer.toString(key++));
		satellites.add(ship);
		satellites.add(ship2);
		
		// set names for planets
		for (Satellite sat: satellites)
			sat.setName(chooseName());
	}
	
	public String chooseName() {
		// choose a random name for a planet
		// TODO: make this better
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
		return p;
	}
	
	
	/* GAMEPLAY states */
	
	/* first contact */
	public void firstContact() {
		/* first contact with one client, getting only player info */
		currentState = "firstContact" + Globals.delim + currentPlayer;
		sendMessage(currentSocket);
		getMessage(currentSocket);
		// update current player information
		String[] s = input.split(Globals.delim);
		updatePlayer(s, 2, 1);
	}
	
	public void startup() {
		/* creation and setup message to clients */
		
		createMap();
		
		setCurrentState("start up"); //TODO: does the state have to be "start up"?
		sendMessage(currentSocket);
		switchCurrPlayer();
		sendMessage(currentSocket);
	}
	
	public void claimStation() {
		/* send the "claimStation" state to the currentPlayer */
		setCurrentState("claim station"); //TODO: standardize state names
		
		sendMessage(currentSocket);
		getMessage(currentSocket);
		read();
	}
	
	public void setUp() {
		/* all set up until both players have chosen a base station */
		
		switchCurrPlayer();
		firstContact();

		switchCurrPlayer();
		firstContact();
		
		switchCurrPlayer(); // back to player 1
		startup();
		
		switchCurrPlayer();
		claimStation();
		switchCurrPlayer();
		claimStation(); // keep on player 2
	}
	
	public Boolean gameEnd() {
		/* return true if there is a game end (win) state */
		if (Globals.playerWin(p1)) {
			winner = p1.getNum();
			return true;
		}
		else if (Globals.playerWin(p2)) {
			winner = p2.getNum();
			return true;
		}
		// TODO: tie?
		return false;
	} 
	
	public void win() {
		/* when the condition "win" has been reached */
		
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
		/* One turn of the game */
		// wait before contacting a client socket
		try { Thread.sleep(500); } catch (InterruptedException e) { e.printStackTrace(); }
		
		if (gameEnd()) { // look for winner
			win();
			System.exit(0);
		}
		
		sendMessage(currentSocket);
		sendMessage(opponentSocket);
		
		currentState = "";
		getMessage(opponentSocket);
		read(); // parse input into current state and update
		getMessage(currentSocket);
		read();
		turn();
		
		//TODO: keep chat open after win
	}

	
	
	public static void main(String[] args) {

		ServerController server = new ServerController();
		server.connectToClients();
		server.setUp();
		server.turn();
		
		System.out.println("END");
	}

}
