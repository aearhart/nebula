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
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class ClientController {

	// socket info
	static Socket socket;
	static DataInputStream in;
	static DataOutputStream out;
	private static String input;
	private static String currentState = "";
	
	private String ipAddress = "localhost";//"2605:e000:1c02:8e:6d73:7e5:5e67:f8b5";
	String winner = "";
	
	// components
	private Window window;
	private Map map;
	private InfoPanel infoPanel;
	private InfoPanel2 infoPanel2;
	private List<Satellite> satellites = new ArrayList<Satellite>();
	private Player player;
	private Player opponent = new Player(this);
	private String clientPlayerNum = "P0";
	private String status = "";
	
	// AoI information
	int AoIx = 0;
	int AoIy = 0;
	int AoIs = 0;
	Color AoIc;
	
	public ClientController() {
 
	}

	/* SERVER METHODS */
	
	private Player Player(ClientController clientController) {
		// TODO Auto-generated method stub
		return null;
	}

	private void error() {
		System.out.println("Error. FAILED");
		System.exit(1);
	}
	
	private void error(String s) {
		System.out.println("Error: " + s);
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
			socket = new Socket(ipAddress, 7777);
		} catch (IOException e) {
			error("Unable to connect to server.");
			//e.printStackTrace();
		}

		System.out.println("Connection established.");
	}
	
	
	/* CREATING/UPDATING GAME INFO */
	
	public void read() {
		/* default read of state  input from server */
		
		String[] s = input.split(Globals.delim);
		// s[0] = win
		// s[1] = winner
		// s[2] = player
		switch(s[0]) {
		case "turn": { // interpret client's turn and update components
			System.out.println(player.getNum() + " is playing.");
			
			if (s[1].equals(opponent.getNum())) {
				error("not current player's turn!");
				// this should be done in validate
			}
			
			int i = updatePlayer(s, 2, 3);
			i = updatePlayer(s, i, i+1);
			int numOfSat = Integer.parseInt(s[i++]);
			for (int j = 0; j < numOfSat; j++) {
				i = getSat(s[i+2]).update(s, i);
			}	
			updateMap(); // update map
			
			// begin turn
			status = "collectResources";
			turn();
			return;
		}
		case "WIN": { // there has been a winner

			winner = s[1];
			System.out.println(winner + " just won.");
			
			int i = updatePlayer(s, 2, 3); 
			i = updatePlayer(s, i, i+1);
			int numOfSat = Integer.parseInt(s[i++]);
			for (int j = 0; j < numOfSat; j++) {
				//System.out.println(i + " " + s[i]);
				i = getSat(s[i+2]).update(s, i);
			}
			updateMap();
			
			setStatus("WIN");
			return;
		}
		} // end case
	}
	

	
	public Satellite getSat(String str) {
		/* given str name, find the satellite matching that name */
		for (Satellite sat: satellites) {
			if (sat.getNum().equals(str))
				return sat;
		}
		return null;
	}
	
	public Station getStation(String str) {
		/* given str name, find the station matching that name */
		for (Satellite sat: satellites) {
			if ((sat.getNum()).equals(str))
				return (Station) sat;
		}
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
			i+=15;
		}
		else if (s[i+1].equals("G")) { // gas planet
			//System.out.println(s[i] + " " + s[i+1] + " " + s[i+2] + " " + s[i+3] + " " + s[i+4] + " " + s[i+5] + " " + s[i+6] + " " + s[i+7] + " " + s[i+8] + " " + s[i+9] + " " + s[i+10] + " " + s[i+11] + " " + s[i+12]);
			sat = new GasPlanet(this, Integer.parseInt(s[i+4]), Integer.parseInt(s[i+5]), s[i+6], s[i+2]);
			//sat = new GasPlanet(this, s, i);
			i+=15;
		}
		else if (s[i+1].equals("M")){ // mineral planet
			//System.out.println(s[i] + " " + s[i+1] + " " + s[i+2] + " " + s[i+3] + " " + s[i+4] + " " + s[i+5] + " " + s[i+6] + " " + s[i+7] + " " + s[i+8] + " " + s[i+9] + " " + s[i+10] + " " + s[i+11] + " " + s[i+12]);
			sat = new MineralPlanet(this, Integer.parseInt(s[i+4]), Integer.parseInt(s[i+5]), s[i+6], s[i+2]);
			//sat = new MineralPlanet(this, s, i);
			i+=15;
		}
		else if (s[i+1].equals("O")) { // the sun
			//System.out.println(s[i] + " " + s[i+1] + " " + s[i+2] + " " + s[i+3] + " " + s[i+4] + " " + s[i+5] + " " + s[i+6]);
			sat = new Sun(this);
			i+=10;
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
		// update the visuals on the map: have to repaint each satellite in order
		// for changes to take effect
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
	
	public Boolean withinSector (double x, double y, int sector) {
		double D = .5*Globals.winSize;
		double root3 = Math.pow(3, .5);
		if (x < 0 || x > Globals.winSize || y < 0 || y > Globals.winSize)
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
		String str = "";
		// for each station
		str += "\nResources collected:";
		for (Station stat: player.getStations()) {
			str += stat.collectResources(player);
			for (Satellite sat: satellites) {
				if (!(sat instanceof Sun) && withinDistance(stat, sat)) {
					str += sat.collectResources(player);
					//System.out.println("planet: " + all[p].getMidX() + " " + all[p].getMidY());
					//System.out.println("Matched with " + all[p].getName() + ", getting " + ((Planet) all[p]).getResources() + ".");
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
			try {
				Thread.sleep(500);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				//e.printStackTrace();
				System.out.print("threading error");
			}
		}
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
	
	public void close() {
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
		return clientPlayerNum; 
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
	
	
	/* Main functions */
	
	public int firstContact() {
		getMessage();
		String s[] = input.split(Globals.delim);
		// validate:
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
	
	public void createComponents() {
		/*
		status = "StartUp";
		
		window = new Window(this, player.getName());
		
		infoPanel = new InfoPanel(this);
		window.add(infoPanel);
		
		map = new Map(this);
		window.add(map);
		
		infoPanel2 = new InfoPanel2(this);
		window.add(infoPanel2);
		
		window.pack();
		window.update();	
		*/
		status = "StartUp";
		
		//tab Menu
		MenuTab menuTab = new MenuTab(this);
		SoundTest st = null;
		try {
			st = new SoundTest();
		} catch (LineUnavailableException | UnsupportedAudioFileException | IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		menuTab.createTab(st);
		
		//tab Map
		MapTab mapTab = new MapTab(this);
		infoPanel = new InfoPanel(this);
		map = new Map(this);
		infoPanel2 = new InfoPanel2(this);
		mapTab.addComponents(infoPanel, map, infoPanel2);
		
		JPanel[] tabs = {menuTab, mapTab};
		window.addTabs(tabs);
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
	
	public void startup() { // create components
		createComponents();
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
			//i = getSat(s[i+1]).update(s, i);
			i = addSat(s, i);
			//getSat(s[copy+1]).update(s, copy);
		}
		// add satellites in list to map 
		for (Satellite sat: satellites) {
			map.add(sat); 
			//sat.setBounds(sat.getLocX(), sat.getLocY(), sat.getBoundSize(), sat.getBoundSize());
		}
		// TODO Fix this. Inefficient
		for (int j = 0; j < numOfSat; j++) {
			copy = getSat(s[copy+2]).update(s, copy);
		}
		updateMap();
		status = "Wait";
		
		if (! s[1].equals(clientPlayerNum)) {
			printToInstructionArea(opponent.getName() + " goes first. Please wait.");
		}
		
	}
	
	public void claimStation() {
		getMessage();
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
		updateMap();
		
		
		printToInstructionArea(player.getName() + ": Click on a space station to claim it");
		status = "Claiming";
		while (status.equals("Claiming")) {
			try {
				Thread.sleep(500);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				//e.printStackTrace();
				System.out.print("threading error");
			}
		}
		
		waitTurn();
		
		getCurrentState("claim end");
		sendMessage();
		
	}
	
	public void getCurrentState(String state) {
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
		status = "Wait";
		printToInstructionArea("It's " + opponent.getName() + "'s turn now. Please wait.");
	}
	
	public void validate() {
		// validate input 
		return;
	}
	
	public void event() {
		
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
		collectResources();
		event();
		upgradeTime();
		getCurrentState("turn");
		sendMessage();
		waitTurn();
		gameplay();
	}
	public void gameplay() {
		getMessage();
		validate(); // could be done in read()
		read();
	}
	
	public void win() {
		if (winner.equals(clientPlayerNum)) {
		printToInstructionArea("You won! Congratulations " + player.getName() + ". Thank you for playing. I'm sure your momma is as proud as can be. Click anywhere to close.");
		}
		else
			printToInstructionArea(opponent.getName() + " just won. Ouch that stings. You should play again to enact your revenge! Click anywhere to close.");
		
	}
	
	public void welcome() {
		window = new Window(this, "player");
		JPanel waiting = new JPanel();
		waiting.add(new JTextField("Waiting to connect to server....", 100));
		window.addTab(waiting, "Waiting to connect...");
		player = new Player(this, "");
		
	}
	
	public void playerTab() {
		window.removeAtab(0);

		
		WelcomeTab welcome = new WelcomeTab(this);
		window.addTab(welcome, "Connection successful");
		while (welcome.notFinished()) {
			try {
				Thread.sleep(500);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				//e.printStackTrace();
				System.out.print("threading error");
			}
		} 
		window.removeAtab(0);
	}
	
	/* MAIN */
	 
	public static void main(String[] args) {

		
		ClientController control = new ClientController();
		control.welcome();
		control.connectToServer();	
		control.playerTab();
		control.firstContact();

		control.startup(); // create the window

		control.claimStation();
		control.gameplay();
		control.win();
		//close();
	}

}
