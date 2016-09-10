import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class ServerController {
	
	static ServerSocket server;
	static Socket player1;
	static Socket player2;
	static DataOutputStream out;
	static DataInputStream in;
	private static String input = "";
	private static String currentState = "";
	private Boolean valid = true;
	private String currentPlayer = "P1";
	private Socket currentSocket = player1;
	
	/*
	private Window window;
	private Map map;
	private InfoPanel infoPanel;
	private InfoPanel2 infoPanel2;
	private Satellite[] all;
	private Player p1;
	private Player p2;
	private Player currPlayer;
	private Player winner;
	private String status = "";
	*/
	
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
	
	private void switchCurrPlayer() {
		if (currentPlayer.equals("P1")) {
			currentPlayer = "P2";
			currentSocket = player2;
			}
		else {
			currentPlayer = "P1";
			currentSocket = player1;
		}
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
			error();
		}
		try {
			out.writeUTF(currentState);
		} catch (IOException e) {
			error();
		}
		System.out.println("Sent message to Socket " + socket.getInetAddress() + ": \n" + currentState);
	}
	
	public void sendStatus() {
		sendMessage(player1);
		sendMessage(player2);
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
	
	public void startUp() {
		currentState = "startUp ";
		
		//window = new Window(this);
		//infoPanel = new InfoPanel(this);
		//map = new Map(this);
		//infoPanel2 = new InfoPanel2(this);
		
		// ??Planet(x, y, s, numResources, name)
		//Satellite s00 = new Sun(this);
 
		// "satellite__=type_X___Y___size___resource___owner_name__"
		currentState += "s01=tWx450y350s030r003o0n01 ";
		//Satellite s01 = new WaterPlanet(this, 450, 350, 30, 3, "1");
		currentState += "s02=tWx766y388s058r002o0n02 ";
		//Satellite s02 = new WaterPlanet(this, 766, 388, 58, 2, "2");
		currentState += "s03=tWx140y142s070r007o0n03 ";
		//Satellite s03 = new WaterPlanet(this, 140, 142, 70, 7, "3");
		currentState += "s04=tWx217y530s035r005o0n04 ";
		//Satellite s04 = new	WaterPlanet(this, 217, 530, 35, 5, "4");
		currentState += "s05=tMx800y104s040r005o0n05 ";
		//Satellite s05 = new MetalPlanet(this, 800, 104, 40, 5, "5");
		currentState += "s06=tMx657y720s062r007o0n06 ";
		//Satellite s06 = new MetalPlanet(this, 657, 720, 62, 7, "6");
		currentState += "s07=tMx340y460s027r002o0n07 ";
		//Satellite s07 = new MetalPlanet(this, 340, 460, 27, 2, "7");
		currentState += "s08=tMx500y680s036r002o0n08 ";
		//Satellite s08 = new MetalPlanet(this, 500, 680, 36, 2, "8");
		currentState += "s09=tGx645y040s026r005o0n09 ";
		//Satellite s09 = new GasPlanet(this, 645, 40, 26, 5, "9"); 
		currentState += "s10=tGx300y200s083r003o0n10 ";
		//Satellite s10 = new GasPlanet(this, 300, 200, 83, 3, "10");
		currentState += "s11=tGx240y730s047r002o0n11 ";
		//Satellite s11 = new GasPlanet(this, 240, 730, 47, 2, "11");
		currentState += "s12=tWx700y600s050r003o0n12 ";
		//Satellite s12 = new GasPlanet(this, 700, 600, 50, 3, "12");

		// "station_=X___Y___S___N_
		currentState += "s13=tSx700y100s030r000o0n13 ";
		//Satellite station00 = new Station(this, 700, 100, 30);
		currentState += "s14=tSx313y545s030r000o0n14 ";
		//Satellite station01 = new Station(this, 313, 545, 30);
		currentState += "s15=tSx168y232s030r000o0n15 ";
		//Satellite station02 = new Station(this, 168, 232, 30);
		currentState += "s16=tSx826y630s030r000o0n16 ";
		//Satellite station03 = new Station(this, 826, 630, 30);

		currentState += currentPlayer + " ";
		switchCurrPlayer();
		currentState += "END";
	}
	
	public void validate() {
		// validate input
		valid = true;
	}
	
	public void invalid(Socket player) {
		System.out.println(player.getInetAddress() + " sent invalid status.");
	}
	
	public void getStatus() {
		getMessage(player1);
		validate();
		if (!valid) {
			invalid(player1);
		}
		getMessage(player2);
		if (!valid) {
			invalid(player2);
		}
	}
	
	public static void main(String[] args) {
		ServerController start = new ServerController();
		start.connectToClients();
		start.startUp();
		start.sendStatus();
		start.getStatus();
	}

	
	
}
