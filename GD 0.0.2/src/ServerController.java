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
	private String winner = "P0";
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
			error("Unable to contact client");
		}
		try {
			out.writeUTF(currentState);
		} catch (IOException e) {
			error("Unable to send message");
		}
		System.out.println("Sent message to Socket " + socket.getInetAddress() + ": \n" + currentState);
	}
	/* this may not be right for what we're doing 
	public void sendStatus() {
		sendMessage(player1);
		sendMessage(player2);
	}
	*/
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
	
	public void startingStatus() {
		currentState = "startUp ";
		currentState += currentPlayer + " ";
		// P_=cR___G___B___s__p__gas___w___mineral___>s__,s__,n______...
		currentState += "P1=cR000G000B000s00p00g000w000m000>n ";
		currentState += "P2=cR000G000B000s00p00g000w000m000>n ";
		//window = new Window(this);
		//infoPanel = new InfoPanel(this);
		//map = new Map(this);
		//infoPanel2 = new InfoPanel2(this);
		
		// ??Planet(x, y, s, numResources, name)
		//Satellite s00 = new Sun(this);
		currentState += "s00=tOx450y450s100r000o0n00 ";
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

		currentState += "END";
	}
	
	public void startUp() {
		startingStatus();
		sendMessage(player1);
		getMessage(player1);
		validate();
		if (!valid) {
			invalid(player1);
		}
		sendMessage(player2);
		getMessage(player2);
		validate();
		if (!valid) {
			invalid(player2);
		}
		System.out.println("Start up successful.");
		currentSocket = player1;
	}
	
	public void validate() {
		// validate input
		valid = true;
		currentState = input;
	}
	
	public void invalid(Socket player) {
		System.out.println(player.getInetAddress() + " sent invalid status.");
	}
	
	/* may not need to get status from both
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
	}*/
	
	public void turnStatus() {
		String[] cs = currentState.split(" ");
		cs[0] = "turn";
		cs[1] = currentPlayer;
		currentState = String.join(" ", cs);
	}
	
	public Boolean playerWon(String p) {
		// return true if player has won
		
		// P_=cR___G___B___s__p__gas___w___mineral___>s__,s__,n______...
		// 0   4   8  12   16  19  22   26   30      34 ....,
		int gas = Integer.parseInt(p.substring(23, 26));
		int water = Integer.parseInt(p.substring(27, 30));
		int mineral = Integer.parseInt(p.substring(31, 34));
		if (gas > 20 && water > 20 && mineral > 20) {
			winner = p.substring(0, 2);
			return true;
		}
		return false;
	}
	
	public Boolean gameEnd() {
		// return true if game won
		String[] cs = currentState.split(" ");
		String p1 = cs[2];
		String p2 = cs[3];
		if (playerWon(p1)) return true;
		return playerWon(p2);
	}
	
	public void win() {
		currentState = "WIN " + winner + " END";
		sendMessage(currentSocket);
		switchCurrPlayer();
		sendMessage(currentSocket);
		System.out.println(currentState);
	}
	
	public void gameplay() {
		System.out.println("Starting turn : " + currentPlayer);
		if (gameEnd()) {
			win();
			System.exit(0);
		}
		turnStatus();
		sendMessage(currentSocket);
		getMessage(currentSocket);
		validate();
		if (!valid)
			invalid(currentSocket);
		switchCurrPlayer();
		gameplay();
	}
	
	public static void main(String[] args) {
		ServerController start = new ServerController();
		start.connectToClients();
		start.startUp();
		start.gameplay();
		//start.getStatus();
	}

	
	
}
