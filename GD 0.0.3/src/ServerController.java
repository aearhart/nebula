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
		
		int maxPlanetsPerSector = 6;
		
		Satellite s0 = new Sun();
		satellites.add(s0);
		int key = 1;
		
		Random ran = new Random();
		
		int ws = Globals.mapSize;
		
		int lPlanetChance = 15;
		int mPlanetChance = 35;
		// int sPlanetChance = 50;
		
		// List of the 6 sectors
		List<Integer> sectors = new ArrayList<Integer>();
		for (int i = 0; i < 6; i++)
			sectors.add(i);
		
		// location of satellites
		/*// Radius = 275 
		double[] stationX = {.261843*ws, .500*ws, .738157*ws, .738157*ws, .500*ws, .261843*ws};
		double[] stationY = {.3625*ws, .225*ws, .3625*ws, .6375*ws, .775*ws, .6375*ws}; */
		// Radius = 250 
		double[] stationX = {.283494*ws, .500*ws, .716506*ws, .716506*ws, .500*ws, .283494*ws};
		double[] stationY = {.375*ws, .250*ws, .375*ws, .625*ws, .750*ws, .625*ws};

		
		// location of other planets
		// Radius = 425
		double[] fillPlanetX = {.079359*ws, .091673*ws, .109993*ws, .131939*ws, .163228*ws, .19948*ws,  .240752*ws,
								.331122*ws, .382126*ws, .439286*ws, .500*ws,    .560714*ws, .617874*ws, .668878*ws,
								.759248*ws, .80052*ws,  .836772*ws, .868061*ws, .890007*ws, .908327*ws, .920641*ws,
								.920641*ws, .908327*ws, .890007*ws, .868061*ws, .836772*ws, .80052*ws,  .759248*ws,
								.668878*ws, .617874*ws, .560714*ws, .500*ws,    .439286*ws, .382126*ws, .331122*ws,
								.240752*ws, .19948*ws,  .163228*ws, .131939*ws, .109993*ws, .091673*ws, .079359*ws};
		double[] fillPlanetY = {.439286*ws, .382126*ws, .331122*ws, .2875*ws,   .240752*ws, .19948*ws,  .163228*ws,
								.109993*ws, .091673*ws, .079359*ws, .075*ws,    .079359*ws, .091673*ws, .109993*ws,
								.163228*ws, .19948*ws,  .240752*ws, .2875*ws,   .331122*ws, .382126*ws, .439286*ws,
								.560714*ws, .617874*ws, .668878*ws, .7125*ws,   .759248*ws, .80052*ws,  .836772*ws,
								.890007*ws, .908327*ws, .920641*ws, .925*ws,    .920641*ws, .908327*ws, .890007*ws,
								.836772*ws, .80052*ws,  .759248*ws, .7125*ws,   .668878*ws, .617874*ws, .560714*ws};

		
		// locations of planets in sectors without stations
		int numPositions = 9;
						    // Line =      2           6           2           6           3           5           2           4           6
		                  // Radius =     150         150         250         250         325         325         400         400         400
		double[] emptySectorPlanetX = {.355885*ws, .393934*ws, .259808*ws, .323223*ws, .201760*ws, .242468*ws, .115692*ws, .153590*ws, .217157*ws,
									   .458397*ws, .541603*ws, .430662*ws, .569338*ws, .453571*ws, .546429*ws, .389060*ws, .500000*ws, .610940*ws,
									   .606066*ws, .644115*ws, .676777*ws, .740192*ws, .757532*ws, .798240*ws, .782843*ws, .846410*ws, .884308*ws,
									   .644115*ws, .606066*ws, .740192*ws, .676777*ws, .798240*ws, .757532*ws, .884308*ws, .846410*ws, .782843*ws,
									   .541603*ws, .458397*ws, .569338*ws, .430662*ws, .546429*ws, .453571*ws, .610940*ws, .500000*ws, .389060*ws,
									   .393934*ws, .355885*ws, .323223*ws, .259808*ws, .242468*ws, .201760*ws, .217157*ws, .153590*ws, .115692*ws};
		double[] emptySectorPlanetY = {.458397*ws, .393934*ws, .430662*ws, .323223*ws, .370858*ws, .301752*ws, .389060*ws, .300000*ws, .217157*ws,
				 				 	   .355885*ws, .355885*ws, .259808*ws, .259808*ws, .178333*ws, .178333*ws, .115692*ws, .100000*ws, .115692*ws,
				 				 	   .393934*ws, .458397*ws, .323223*ws, .430662*ws, .301752*ws, .370858*ws, .217157*ws, .300000*ws, .389060*ws,
				 				 	   .541603*ws, .606066*ws, .569338*ws, .676777*ws, .629142*ws, .698248*ws, .610940*ws, .700000*ws, .782843*ws,
				 				 	   .644115*ws, .644115*ws, .740192*ws, .740192*ws, .821667*ws, .821667*ws, .884308*ws, .900000*ws, .884308*ws,
				 				 	   .606066*ws, .541603*ws, .676777*ws, .569338*ws, .698248*ws, .629142*ws, .782843*ws, .700000*ws, .610940*ws};
		

		// initialize planet p as a satellite
		Satellite p;
		
		// Fill 4 of the sectors with stations and then add the planets
		for (int i = 0; i < 4; i++) { // number of satellites = 4
			int r = ran.nextInt(sectors.size()); // 6 sectors
			int sect = sectors.remove(r);
			
			// choose between two types
			r = ran.nextInt(2);
			r = 1; // FORCING SLOW START
			Satellite s;
			if (r == 0) { // default
				s = new DefaultStation((int)stationX[sect], (int)stationY[sect], 30, "s" + Integer.toString(key++));
				s.setName("Default");
			}
			else { // slow_default
				s = new Default2Station((int)stationX[sect], (int)stationY[sect], 30, "s" + Integer.toString(key++));
				s.setName("Default2 (slow start)");
			}
			satellites.add(s);
			
			// Create the planets around the station
			String plans = ((Station) s).getPlanetsToCreate();
			int posIndex = 0;
			for (int j = 0; j < plans.length(); j+=2) {
				// initialize planet p as a satellite
				switch (plans.charAt(j)) {
				case 'G': {p = new GasPlanet(0, 0, plans.substring(j+1, j+2), "s" + Integer.toString(key++)); break;}
				case 'M': {p = new MineralPlanet(0, 0, plans.substring(j+1, j+2), "s" + Integer.toString(key++)); break;}
    /*case 'W'*/default: {p = new WaterPlanet(0, 0, plans.substring(j+1, j+2), "s" + Integer.toString(key++)); break;}
				}
				((Station)(s)).placePlanet(p, posIndex);
				satellites.add(p);
				posIndex++;
			}
			
			// Fill in the rest of the sector
			int remaining = maxPlanetsPerSector - posIndex; //after the previous for loop, posIndex = number of planets created around the station
			
			int fillPlanetMaxDelta = 20; // TODO Calculate a better maxDelta
			
			int[] positionsPossible = {0, 1, 2, 3, 4, 5, 6}; // 7 possible positions
			for (int j = 0; j < remaining;) { 
				r = ran.nextInt(7);
				if (positionsPossible[r] != -1) { // if that position hasn't been eliminated
					// eliminate the position and it's neighbors
					if (r > 0)
						positionsPossible[r-1] = -1;
					positionsPossible[r] = -1;
					if (r < 6)
						positionsPossible[r+1] = -1;
				
					// x coordinate
					int planetX = (int) fillPlanetX[sect*7+r];
					int delta = ran.nextInt(fillPlanetMaxDelta);
					int sign = ran.nextInt(2);
					if (sign == 0) sign--;
					planetX += delta*sign;
					
					// y coordinate
					int planetY = (int) fillPlanetY[sect*7+r];
					delta = ran.nextInt(fillPlanetMaxDelta);
					sign = ran.nextInt(2);
					if (sign == 0) sign--;
					planetY += delta*sign;
					
					// size
					String size = "";
					int randomSize = ran.nextInt(100);
					if (randomSize <= lPlanetChance) size = "l";      
					else if (randomSize <= (mPlanetChance + lPlanetChance)) size = "m";
					else size = "s";
					
					
					// TODO Make this intelligently choose a planet type 	
					int type = ran.nextInt(3); // 3 types of planets
					switch (type) {
					case 0: {p = new GasPlanet(planetX, planetY, size, "s" + Integer.toString(key++)); break;}
					case 1: {p = new MineralPlanet(planetX, planetY, size,  "s" + Integer.toString(key++)); break;}
		  /*case 2*/default: {p = new WaterPlanet(planetX, planetY, size, "s" + Integer.toString(key++)); break;}
					}
					//System.out.println("FILLPLANET: " + p.getLocX() + ", " + p.getLocY());
					satellites.add(p);
					
					j++; // added another planet
				}
			}
			
			//System.out.println("Station in sector " + sect + " has " + pos + " planets");	
		}
		
		
		// Populate the other sectors
		int emptySectorMaxDelta = 40; // TODO Calculate a better maxDelta
		for (int i = 0; i < 2; i++) { // 6 sectors - 4 stations leaves 2 empty sectors
			// sector
			int sect = sectors.get(i);
			//System.out.println("FILLING IN EMPTY SECTOR " + sect);
			
			// possible positions
			List<Integer> positions = new ArrayList<Integer>();
			for (int j = 0; j < numPositions; j++)
				positions.add(j); 
			
			// place planets
			for (int j = 0; j < maxPlanetsPerSector; j++) {
				// position
				int r = ran.nextInt(positions.size());
				int pos = positions.remove(r);
				
				// TODO lower maxDelta and/or weigh it less
				
				// x coordinate
				int planetX = (int) emptySectorPlanetX[sect*numPositions+pos];
				int delta = ran.nextInt(emptySectorMaxDelta);
				int sign = ran.nextInt(2);
				if (sign == 0) sign--;
				planetX += delta*sign;
				
				// y coordinate
				int planetY = (int) emptySectorPlanetY[sect*numPositions+pos];
				delta = ran.nextInt(emptySectorMaxDelta);
				sign = ran.nextInt(2);
				if (sign == 0) sign--;
				planetY += delta*sign;
				
				// size
				String size = "";
				int randomSize = ran.nextInt(100);
				if (randomSize <= lPlanetChance) size = "l";      
				else if (randomSize <= (mPlanetChance + lPlanetChance)) size = "m";
				else size = "s";
				
				// TODO Make this intelligently choose a planet type
				int type = ran.nextInt(3); // 3 types of planets
				switch (type) {
				case 0: {p = new GasPlanet(planetX, planetY, size, "s" + Integer.toString(key++)); break;}
				case 1: {p = new MineralPlanet(planetX, planetY, size, "s" + Integer.toString(key++)); break;}
	  /*case 2*/default: {p = new WaterPlanet(planetX, planetY, size, "s" + Integer.toString(key++)); break;}
				}
				System.out.println("ADDING PLANET IN SECTOR " + sect + " IN POSITION " + pos);
				satellites.add(p);
			}
		}
		
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
		
		// TODO Remove unclaimed stations
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
