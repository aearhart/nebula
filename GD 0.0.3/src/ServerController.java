import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

public class ServerController {
	
	static ServerSocket server;
	static Socket player1;
	static Socket player2;
	static DataOutputStream out;
	static DataInputStream in;
	private static String input = "";
	private static String currentState = "";
	private Boolean valid = true;
	
	private Player p1 = new Player();
	private Player p2 = new Player();
	
	private String currentPlayer = "P0";
	private Socket currentSocket;
	private Player currPlayerObj;
	private String winner = "P0";

	private List<Satellite> satellites = new ArrayList<Satellite>();
	
	
	public ServerController() {
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
	
	public void definePlayer() {
		// parse input into current player
		String[]s = input.split(Globals.delim);
		if (s[1].equals("P1")) 
			p1.update(s[2]);
		else
			p2.update(s[2]);
	}
	
	/* first contact */
	public void firstContact() {
		currentState = "firstContact" + Globals.delim + currentPlayer;
		sendMessage(currentSocket);
		getMessage(currentSocket);
		//definePlayer();
	}
	

	public void startUp() {
		// create the map
		Satellite s00 = new Sun();
		Satellite s01 = new WaterPlanet(450, 350, 30, 3, "1");
		Satellite s02 = new WaterPlanet(766, 388, 58, 2, "2");
		Satellite s03 = new WaterPlanet(140, 142, 70, 7, "3");
		Satellite s04 = new	WaterPlanet(217, 530, 35, 5, "4");
		Satellite s05 = new MineralPlanet(800, 104, 40, 5, "5");
		Satellite s06 = new MineralPlanet(657, 720, 62, 7, "6");
		Satellite s07 = new MineralPlanet(340, 460, 27, 2, "7");
		Satellite s08 = new MineralPlanet(500, 680, 36, 2, "8");
		Satellite s09 = new GasPlanet(645, 40, 26, 5, "9"); 
		Satellite s10 = new GasPlanet(300, 200, 83, 3, "10");
		Satellite s11 = new GasPlanet(240, 730, 47, 2, "11");
		Satellite s12 = new GasPlanet(700, 600, 50, 3, "12");
		Satellite s13 = new Station(700, 100, 30, "13");
		Satellite s14 = new Station(313, 545, 30, "14");
		Satellite s15 = new Station(168, 232, 30, "15");
		Satellite s16 = new Station(826, 630, 30, "16");

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
		
		ArrayList<String> aList = new ArrayList<String>();
		aList.add("start up");
		aList.add(currentPlayer);
		aList.add(p1.printState());
		aList.add(p2.printState());
		aList.add(Integer.toString(satellites.size()));
		for (Satellite s : satellites) {
			aList.add(s.printState());
		}
		
		System.out.println(Globals.addDelims(aList));
	}
	
	public void setUp() {

		switchCurrPlayer();
		firstContact();

		switchCurrPlayer();
		firstContact();
		
		switchCurrPlayer();
		startUp();
		
	}
	
	
	
	
	
	public static void main(String[] args) {
		ServerController server = new ServerController();
		server.connectToClients();
		server.setUp();
	}
}
