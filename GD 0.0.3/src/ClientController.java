/*   ClientController.java
 * 	   11/11/2016
 * 				Controls the client end of the game
 * 					1. Create a welcome window until able to connect to server: welcome()
 * 					2. Connect to server
 * 					3. Create the player: playerTab()
 * 					4. If connection not successful, go to the test GUI
 * 					5. Otherwise, start the game:
 * 						a. firstContact(): get client num, send player info to server
 * 						b. startup(): create the components and map satellite objects 
 * 						c. claimStation(): claim a station, claim a spaceship
 * 					6.	turn(): enter the turn cycle of the game
 * 						a. waitPhase: no available options, but can gather information
 * 						b. turnPhase: it's the client's turn
 * 							i. mainPhase
 * 							ii. spaceshipPhase
 * 							iii. collectResources
 * 						c. Will continually communicate with server
 * 							i. Sending out information:
 * 								1. MESSAGE
 * 								2. TURN
 * 								3. WIN
 * 								4. NOCHANGE
 * 							ii. read():
 * 								1. MESSAGE
 * 								2. TURN
 * 								3. WIN
 * 								4. (no need to include): NOCHANGE
 * 					7. Upon receiving WIN state, end the game on click
 */

import java.awt.Color;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;
import javax.swing.JPanel;
import javax.swing.JTextField;


public class ClientController {

	// socket info
	static Socket socket;
	static DataInputStream in;
	static DataOutputStream out;
	private static String input;
	public static String currentState = "";
	
	private String ipAddress = "localhost";
	String winner = "";
	
	// Gameplay Components
	private WelcomeTab welcome;
	private Window window;
	private Map map;
	private InfoPanel infoPanel;
	InfoPanel2 infoPanel2;
	private List<Satellite> satellites = new ArrayList<Satellite>();
	private Player player;
	private Player opponent = new Player(this);
	public String clientPlayerNum = "P0";
	private String status = "";
	public Boolean chatEnabled = true;
	
	// Current turn and testing values
	private Boolean turn = false; // not current turn
	public boolean testing = false;

	// AoI information
	int AoIx = 0;
	int AoIy = 0;
	int AoIs = 0;
	Color AoIc;
	

	public ClientController() {
 
	}

	
	/* SERVER METHODS */

	private void error(IOException e) {
		System.out.println("Error. FAILED");
		e.printStackTrace();
		System.exit(1);
	}
	
	private void error(IOException e, String s) {
		System.out.println("Error: " + s);
		System.exit(1);
	}
	
	public void getMessage()  {
		/* reads input from server into variable input */
		try { in = new DataInputStream(socket.getInputStream()); } 
		catch (IOException e) { error(e); }
		try { input = in.readUTF(); } 
		catch (IOException e) { error(e); }
		System.out.println("Receiving information...  " + input);
	}
	
	public void sendMessage() {
		/* sends currentState to server */
		try { out = new DataOutputStream(socket.getOutputStream()); }
		catch (IOException e) { error(e); }
		try { out.writeUTF(currentState); }
		catch (IOException e) { error(e); }
		System.out.println("Message sent: " + currentState);
	} 
	
	public void connectToServer() {
		/* connect to server */
		System.out.println("Connecting to server...");
		try { socket = new Socket(ipAddress, 7777); }
		catch (IOException e) { // set to TEST GUI
			//error("Unable to connect to server.");
			setStatus("test");
		}
		System.out.println("Connection established.");
	}
	
	
	/* CREATING/UPDATING GAME INFO */
	
	public void read() {
		/* default read of state  input from server */
		
		String[] s = input.split(Globals.delim);
		// s[0] = state,  s[1] = currentPlayer, s[2] = player
		switch(s[0]) {
		case "MESSAGE": { // MESSAGE@@FROM_PLAYER@@TEXT
			if (chatEnabled && (! s[1].equals(clientPlayerNum))) { // a new message
				getChatbox().updateHistory(s[2]);
			}
			return;
		}
		case "TURN": { // interpret client's turn and update components
			if (s[1].equals(clientPlayerNum)) { // does current turn match client
				turn = true;
				updateSatObjects(s);
				// begin turn
				setStatus("Upgrade");
				event();
				printToInstructionArea("Click on a planet or space station to upgrade.");
			}
			else { // turn does not match client, it is opponent's turn
				turn = false;
				waitTurn();
			}
			return;
		} // end case TURN
		case "WIN": { // there has been a winner
			winner = s[1];
			updateSatObjects(s);
			win();
			return;
		} // end case WIN
		} // end switch
	}
	
	public void updateSatObjects(String[] s) {
		/* Given string array s, update the players and objects on the map */
		int i = updatePlayer(s, 2, 3); 
		i = updatePlayer(s, i, i+1);
		int numOfSat = Integer.parseInt(s[i++]);
		for (int j = 0; j < numOfSat; j++) {
			//System.out.println(i + " " + s[i]);
			i = getSat(s[i+2]).update(s, i);
		}
		updateMap();
	}
	
	public Satellite getSat(String str) {
		/* given str name, find the satellite matching that name */
		for (Satellite sat: satellites) {
			if (sat.getNum().equals(str))
				return sat;
		}
		error(null, "Unable to find satellite " + str);
		return null;
	}
	
	public Station getStation(String str) {
		/* given str name, find the station matching that name */
		for (Satellite sat: satellites) {
			if ((sat.getNum()).equals(str))
				return (Station) sat;
		}
		error(null, "Unable to find station " + str);
		return null;
	}
	
	public int addSat(String[] s, int i) {
		/* given string s info, create a satellite and add it to the satellite list */
		
		Satellite sat;
		// determine type
		if (s[i+1].equals("W")) { // water planet
			//System.out.println(s[i] + " " + s[i+1] + " " + s[i+2] + " " + s[i+3] + " " + s[i+4] + " " + s[i+5] + " " + s[i+6] + " " + s[i+7] + " " + s[i+8] + " " + s[i+9] + " " + s[i+10] + " " + s[i+11] + " " + s[i+12]);
			sat = new WaterPlanet(this, Integer.parseInt(s[i+4]), Integer.parseInt(s[i+5]), s[i+6], s[i+2]);
			//sat = new WaterPlanet(this, s, i);
			i+=16;
		}
		else if (s[i+1].equals("G")) { // gas planet
			//System.out.println(s[i] + " " + s[i+1] + " " + s[i+2] + " " + s[i+3] + " " + s[i+4] + " " + s[i+5] + " " + s[i+6] + " " + s[i+7] + " " + s[i+8] + " " + s[i+9] + " " + s[i+10] + " " + s[i+11] + " " + s[i+12]);
			sat = new GasPlanet(this, Integer.parseInt(s[i+4]), Integer.parseInt(s[i+5]), s[i+6], s[i+2]);
			//sat = new GasPlanet(this, s, i);
			i+=16;
		}
		else if (s[i+1].equals("M")){ // mineral planet
		//	System.out.println(s[i] + " " + s[i+1] + " " + s[i+2] + " " + s[i+3] + " " + s[i+4] + " " + s[i+5] + " " + s[i+6] + " " + s[i+7] + " " + s[i+8] + " " + s[i+9] + " " + s[i+10] + " " + s[i+11] + " " + s[i+12]);
			sat = new MineralPlanet(this, Integer.parseInt(s[i+4]), Integer.parseInt(s[i+5]), s[i+6], s[i+2]);
			//sat = new MineralPlanet(this, s, i);
			i+=16;
		}
		else if (s[i+1].equals("O")) { // the sun
			//System.out.println(s[i] + " " + s[i+1] + " " + s[i+2] + " " + s[i+3] + " " + s[i+4] + " " + s[i+5] + " " + s[i+6]);
			sat = new Sun(this);
			i+=10;
		}
		else if (s[i+1].equals("SS")) {
			//{"spaceship", "SS", num, name, currSatNum, size, currFuel, maxFuel, ownerNum
			//System.out.println(s[i] + ", " + s[i+1] + ", " + s[i+2] + ", " + s[i+3] + ", " + s[i+4] + ", " + s[i+5] + ", " + s[i+6] + ", " + s[i+7] + ", " + s[i+8]);
			sat = new Spaceship(this, s[i+8], s[i+2]);
			i+=9;
		}
		else { // space station
			//System.out.println(s[i] + " " + s[i+1] + " " + s[i+2] + " " + s[i+3] + " " + s[i+4] + " " + s[i+5] + " " + s[i+6] + " " + s[i+7] + " " + s[i+8] + " " + s[i+9] + " " + s[i+10] + " " + s[i+11] + " " + s[i+12] + " " + s[i+13] + " " + s[i+14] + " " + s[i+15]);
			sat = new Station(this, Integer.parseInt(s[i+4]), Integer.parseInt(s[i+5]), Integer.parseInt(s[i+6]), s[i+2]);	
			//sat = new Station(this, s, i);
			sat.setOwner(s[i+15]);
			i+=16;
		}
		satellites.add(sat); // add to list of satellites
		return i;
	}
	
	public void updateMap() {
		/* update the visuals on the map: repaint each satellite */
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
			else if (sat instanceof Spaceship) { //TODO: placeShip() may already account for this
				((Spaceship)sat).repaint();
			}
		}
	}
	
	public Boolean withinSector (double x, double y, int sector) {
		/* return true if x and y values are within sector*/
		double D = .5*Globals.mapSize;
		double root3 = Math.pow(3, .5);
		if (x < 0 || x > Globals.mapSize || y < 0 || y > Globals.mapSize)
			return false;
		switch(sector) {
			case 1: {
				if (y > D)
					return false;
				if (x  > ((y/root3) + (D - (D/root3))))
					return false;
				break;
			}
			case 2: {
				if (y > D)
					return false;
				if (x  < ((y/root3)  + (D - (D/root3))))
					return false;
				if (x > (((D - y)/root3) + D))
					return false;
				break;
			}
			case 3: {
				if (y > D)
					return false;
				if (x < (((D - y)/root3) + D))
					return false;
				break;
			}
			case 4: {
				if (y < D)
					return false;
				if (x < (((y - D)/root3) + D))
					return false;
				break;
			}
			case 5: {
				if (y < D)
					return false;
				if (x > (((y - D)/root3) + D))
					return false;
				if (x < (((D - y)/root3) + D))
					return false;
				break;
			}
			case 6: {
				if (y < D)
					return false;
				if (x > (((D - y)/root3) + D))
					return false;
			}
		}
		return true;
	}

	public int findSector (double x, double y) {
		/* Given x and y, determine which sector the location resides in */
		for (int i = 1; i <= 6; i++)
			if(withinSector(x, y, i)) {
				//System.out.println(i);
				return i;
			}
		return 7;
	}
	
	
	/* GAME TURN */
	
	public void collectResources() {
		/* current player collects resources in AoI */
		//TODO: take care of overlapping instances (split, etc.)
		//TODO: move collectResources to serverController, occurs after both players' turn

		String str = "";
		// for each station
		str += "\nResources collected:";
		Station stat = player.getBase();
		str += stat.collectResources(player);
		for (Satellite sat: satellites) {
			if (!(sat instanceof Sun) && !(sat instanceof Spaceship) && withinDistance(stat, sat)) {
				str += sat.collectResources(player);
			}
		}
		printToPlayerArea(player.info() + str);
	}
	
	public void upgradeTime() { //TODO: change name to mainPhase() or something
		/* main phase: upgrade or buy a planet or station */
		printToInstructionArea("Click on a planet or space station to upgrade.");
	
		while (status.equals("Upgrade")) { //TODO: change "Upgrade" to something else
			try {
				Thread.sleep(500);
			} catch (InterruptedException e) {
				//e.printStackTrace();
				System.out.print("threading error"); }
		}
	}
	
	/* DRAWING AOI */
	
	public void drawAoI(Station s) { 
		/* print AOI on map */
		AoIx = s.getMidX() - s.getAoI();
		AoIy = s.getMidY() - s.getAoI();
		AoIs = s.getAoI()*2;
		AoIc = new Color(100, 100, 0, 255);
		map.repaint();
		window.update();
	}
	
	public void drawAoI(Spaceship s) { //TODO: location is off (at 0, 0)
		/* print AOI on map */
		AoIx = s.getMidX() - s.getRange();
		AoIy = s.getMidY() - s.getRange();
		AoIs = s.getRange()*2;
		AoIc = new Color(0, 100, 100, 255);
		map.repaint();
		window.update();
	}
	
	public void removeAoI() {
		/* remove AOI on map */
		AoIc = new Color(88, 232, 232, 0);
		map.repaint();
		window.update();
	}
	
	public Boolean withinDistance(Station s, Satellite p) {
		/* calculate distance from station s to satellite p */
		Integer distance = (int) Math.sqrt(Math.pow(Math.abs(s.getMidX() - p.getMidX()), 2) + 
				Math.pow(Math.abs(s.getMidY() - p.getMidY()), 2));
		if (distance < (s.getAoI() + p.getSz())) {
			return true;
		}
		return false;
	}
	
	public void placeShip(Spaceship ship) {
		/* place the ship at it's current sat */
		Satellite sat = ship.getCurrSat();
		System.out.println("Placing ship at " + sat.getType() + " " + sat.getNum());
		
		ship.setBounds(sat.getMidX()-ship.getHalfSize(), sat.getMidY()-ship.getHalfSize(), ship.getFullSize(), ship.getFullSize());
		updateMap();
	}
	
	/* BASIC METHODS */
	
	public void close() {
		/* close the game */
		System.exit(0);
	}
	
	public String getStatus() {
		return status;
	}
	
	public void setStatus(String s) {
		/* set the status to s and change selectPanel to correct phase */
		status = s;
		if (status.equals("Claiming")) infoPanel2.getSelectPanel().claimPhase();
		else if (status.equals("Upgrade")) infoPanel2.getSelectPanel().mainPhase();
		else if (status.equals("Wait")) infoPanel2.getSelectPanel().waitPhase();
		else if (status.equals("Spaceship_Phase")) infoPanel2.getSelectPanel().spaceshipPhase();
	}
	
	public ChatBox getChatbox() {
		return infoPanel2.getChatbox();
	}
	
	public String getCurrPlayer() { // return string num "P_" of current player
		return clientPlayerNum; 
	}
	
	public Player getPlayer() {
		return player;
	}
	
	public Window getWindow() {
		return window;
	}
	
	public Map getMap() {
		return map;
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
	
	public void printToHoverArea(String s) { //TODO: change to selectarea
		infoPanel2.printToHoverArea(s);
	}
	
	public void printToHoverArea() { // default
		infoPanel2.printToHoverArea("Hover over a satellite for more information.");
	}	
	
	
	/* Main functions */
	
	public int firstContact() {
		/* First contact with server, get clientNum and share player info */
		getMessage();
		String s[] = input.split(Globals.delim);
		// TODO: validate
		if (! s[0].equals("firstContact"))
			return -1;
		clientPlayerNum = s[1];
		player.setNum(s[1]);
		ArrayList<String> aList = new ArrayList<String>();
		aList.add("playerSetup"); 
		aList.add(clientPlayerNum);
		aList.add(player.printState());
		
		currentState = Globals.addDelims(aList);
		
		sendMessage();
		
		return 0;
	}
	
	public void createMenu() {
		/* Create the Menu tab */
		MenuTab menuTab = new MenuTab(this);
		SoundTest st = null;
		try { st = new SoundTest(); } // TODO: don't name it soundtest
		catch (LineUnavailableException | UnsupportedAudioFileException | IOException e) { error((IOException) e); }
		menuTab.createTab(st);
		window.addTab(menuTab, "Menu");
		System.out.println("added menu tab");
	}
	
	public void createComponents() { //TODO: maybe call this createMapTab or something
		/* create the components for the GUI for mainPhase*/
		setStatus("StartUp");

		//tab Map
		MapTab mapTab = new MapTab(this);
		infoPanel = new InfoPanel(this);
		map = new Map(this);

		infoPanel2 = new InfoPanel2(this);
		mapTab.addComponents(infoPanel, map, infoPanel2);
		
		JPanel[] tabs = {mapTab};
		window.addTabs(tabs);
		setStatus("pause");
		map.hover("Please wait while we are setting up the game.");
		window.setMapFront();
	}
	
	public int updatePlayer(String s[], int i, int p) {
		if (s[p].equals(clientPlayerNum)) {
			i = player.update(s, i);
		}
		else {
			i = opponent.update(s, i);
		}
		return i;
	}
	
	public void startup() { 
		/* create GUI components and populate map*/
		createComponents();
		map.clear();
		getMessage();
		
		String s[] = input.split(Globals.delim);
		// state = start up

		// currentPlayer = s[1]
		// s[2] = "player"
		int i = updatePlayer(s, 2, 3);
		i = updatePlayer(s, i, i+1);
		int numOfSat = Integer.parseInt(s[i++]);
		int copy = i;
		for (int j = 0; j < numOfSat; j++) {
			i = addSat(s, i);
		}
		// add satellites in list to map
		for (Satellite sat: satellites) {
			map.add(sat, 2); 
			sat.setBounds(sat.getLocX(), sat.getLocY(), sat.getBoundSize(), sat.getBoundSize());
		}
		// TODO Fix this. Inefficient
		for (int j = 0; j < numOfSat; j++) {
			copy = getSat(s[copy+2]).update(s, copy);
		}
		updateMap();
		setStatus("Wait");
		printToPlayerArea();
		if (! s[1].equals(clientPlayerNum)) {
			printToInstructionArea(opponent.getName() + " goes first. Please wait.");
		}
	}
	
	public void claimStation() {
		/* Update map and allow client to enter the claim phase */
		getMessage();
		String s[] = input.split(Globals.delim);
		updateSatObjects(s);		
		
		printToInstructionArea(player.getName() + ": Click on a space station to claim it");
		setStatus("Claiming");
		System.out.println("Before waiting");
		while (status.equals("Claiming")) {
			System.out.println("Waiting..");
			try {
				Thread.sleep(500);
			} catch (InterruptedException e) {
				//e.printStackTrace();
				System.out.print("threading error");
			}
		}
		
		// TODO: add spaceship to the player's station
		getPlayer().getSpaceship().setCurrSat(getPlayer().getBase());
		placeShip(getPlayer().getSpaceship());
		updateMap();
		
		waitTurn();
		
		getCurrentState("claim end");
		sendMessage();
		// turn on chat
		if (chatEnabled)
			getChatbox().turnOn();
		
	}
	
	public void getCurrentState(String state) {
		/* set currentState to the state of the client's game */
		
		ArrayList<String> aList = new ArrayList<String>();
		aList.add(state);
		aList.add(clientPlayerNum);
		aList.add(player.printState());
		aList.add(opponent.printState());
		aList.add(Integer.toString(satellites.size()));
		for (Satellite s : satellites) {
			aList.add(s.printState());
		}
		
		currentState = Globals.addDelims(aList);
		
	}
	
	public void waitTurn() {
		/* place client in to wait phase */
		setStatus("Wait");
		printToInstructionArea("It's " + opponent.getName() + "'s turn now. Please wait.");
	}
	
	public void validate() { 
		/* validate input */
		return;
	}
	
	public void event() {
		/* enact a random event on the player */ 
		//TODO: events
		Random ran = new Random();
		double e = ran.nextInt(20) * player.eventChance;
		System.out.println("event: " + e);
		if (e <= 10) {
			// do nothing
			printToPlayerArea(player.info() + "\nIt's real quiet out in space.");
		}
		else if (e <= 15) {
			// malfunction
			printToPlayerArea(player.info() + "\nOh noes an asteroid hit the station! Limited gas supply until fixed.");
			player.getBase().malfunction('g');
		}
		else // between 16 & 20
		{
			// pirate attack
			printToPlayerArea(player.info() + "\nYou're under attack!! Pirates have attacked the base!");
		
			//player.getBase().pirateAttack();
		} 
		
	}
	
	public void turn() {
		/* one turn for a client. continually contacts server */
		getMessage();
		read();
		System.out.println("TURN MESSAGE: " + "msg:" + (!getChatbox().message.equals("")) + 
				" endTurn: " + turn +  " " + (! status.equals("Upgrade")) + " other: " + status);
		// check to see if switch to spaceship mode TODO: I don't like how this is being done here
		if (turn && status.equals("endMain")) { // end of upgrade phase, change to spaceship phase
			setStatus("Spaceship_Phase");
			printToInstructionArea("What do you want to do with your spaceship?");
		}
		if (chatEnabled && (! getChatbox().message.equals(""))) {
			currentState = "MESSAGE@@" + clientPlayerNum + "@@" + getChatbox().message; 
			getChatbox().emptyMessage();
			sendMessage();
		}
		else if (turn && status.equals("End Turn")) {
			collectResources(); //TODO: ? collect resources should be somewhere else, rearrange order
			getCurrentState("TURN");
			sendMessage();
			turn = false;
		}
		else if (status.equals("WIN")) {
			// do nothing
			return;
		}
		else { // no change
			getCurrentState("NOCHANGE");
			sendMessage();
		}
		turn();

	}
	
	public void win() {
		/* client enters win phase */
		setStatus("WIN");
		if (winner.equals(clientPlayerNum)) {
		printToInstructionArea("You won! Congratulations " + player.getName() + ". Thank you for playing. I'm sure your momma is as proud as can be. Click anywhere to close.");
		}
		else
			printToInstructionArea(opponent.getName() + " just won. Ouch that stings. You should play again to enact your revenge! Click anywhere to close.");
		
	}
	
	public void beginGame() {
		System.out.println("beginning game");
		
		window.removeAtab(0);
		//System.out.println("remove tab 0");	

		createMenu();
		
		connectAndPlay();
		//TODO: server must be connected to both before screen pops up correctly... ??
	}
	
	public void welcome() {
		/* welcome the player before they have been able to connect to the server */
		// TODO: I don't think this really does much in terms of visuals
		window = new Window(this, "player");
		player = new Player(this, "");
		setStatus("Welcome");
		welcome = new WelcomeTab(this);
		window.addTab(welcome, "Welcome");
		
		while (welcome.notFinished()) {
			try {
				Thread.sleep(500);
			} catch (InterruptedException e) {
				//e.printStackTrace();
				System.out.print("threading error");
			}
		}
		beginGame();
	}
	
	public void testMap() {
		/* for testing purposes only */
		satellites.add(new WaterPlanet(this, 250, 250, "s", "s01"));
		satellites.add(new WaterPlanet(this, 250, 750, "m", "s02"));
		satellites.add(new WaterPlanet(this, 750, 250, "l", "s03"));
		satellites.add(new WaterPlanet(this, 750, 750, "s", "s04"));
		for (Satellite sat: satellites) {
			map.add(sat, 2); 
			sat.setName(sat.num + " water planet");
			sat.setBounds(sat.getLocX(), sat.getLocY(), sat.getBoundSize(), sat.getBoundSize());
		}
		Station sat = new DefaultStation(this, 780, 690, 50, "s5");
		Spaceship testShip = new Spaceship(this, clientPlayerNum, "s6");
		map.add(sat, 5);
		map.add(testShip,3);
		testShip.setCurrSat(sat);
		placeShip(testShip);
		updateMap();
		
	}
	
	public void connectAndPlay() {
		connectToServer();	
		if (! getStatus().equals("test")) {

			firstContact();
			
			startup(); // create the window

			claimStation();
			turn();
			//close();
		}
		else {
			testing  = true;
			createComponents();
			getMap().hoverBoxOn("This is just a test");
			//control.getMap().clear();
			testMap();
			getMap().hover("This is just a test.");
		}
	}
	
	/* MAIN */
	 
	public static void main(String[] args) {

		ClientController control = new ClientController();
		//Globals.setWinSize();
		control.welcome();
		
	}

}