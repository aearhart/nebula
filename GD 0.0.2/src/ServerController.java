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
	
	public static void getMessage(Socket socket) throws IOException  {
		// input from socket goes to variable input
		in = new DataInputStream(socket.getInputStream());
		input = in.readUTF();
		System.out.println("Receiving information from Socket " + socket.getInetAddress() + "...  " + input);
	}
	
	public static void sendMessage(Socket socket) throws IOException {
		// sends out currentState to socket
		out = new DataOutputStream(socket.getOutputStream());
		out.writeUTF(currentState);
		System.out.println("Sent message to Socket " + socket.getInetAddress() + ": \n" + currentState);
	}
	
	public void sendStatus() {
		
		try {
			sendMessage(player1);
		} catch (IOException e) {
			System.out.println("Unable to contact player 1.");
			//e.printStackTrace();
		}
		
		try {
			sendMessage(player2);
		} catch (IOException e) {
			System.out.println("Unable to contact player 2");
			//e.printStackTrace();
		}
	}
	
	public void connectToClients() {
		// Create server and connect to two clients.
		
		// Create server
		System.out.println("Starting server...");
		try {
			server = new ServerSocket(7777);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		System.out.println("Server started.");
		
		// Connect to Player 1
		try {
			player1 = server.accept();
		} catch (IOException e) {
			//e.printStackTrace();
			System.out.println("Could not connect to Player 1");
		}
		System.out.println("Successful connection from: " + player1.getInetAddress());
		
		// Connect to Player 2
		try {
			player2 = server.accept();
		} catch (IOException e) {
			//e.printStackTrace();
			System.out.println("Could not connect to Player 2");
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
 
		// "satellite__=type_X___Y___size___resource___name_"
		currentState += "s01=tWx450y350s030r003n0 ";
		//Satellite s01 = new WaterPlanet(this, 450, 350, 30, 3, "0");
		currentState += "s02=tWx766y388s058r002n1 ";
		//Satellite s02 = new WaterPlanet(this, 766, 388, 58, 2, "1");
		currentState += "s03=tWx140y142s070r007n2 ";
		//Satellite s03 = new WaterPlanet(this, 140, 142, 70, 7, "2");
		currentState += "s04=tWx217y530s035r005n3 ";
		//Satellite s04 = new	WaterPlanet(this, 217, 530, 35, 5, "3");
		currentState += "s05=tMx800y104s040r005n0 ";
		//Satellite s05 = new MetalPlanet(this, 800, 104, 40, 5, "0");
		currentState += "s06=tMx657y720s062r007n1 ";
		//Satellite s06 = new MetalPlanet(this, 657, 720, 62, 7, "1");
		currentState += "s07=tMx340y460s027r002n2 ";
		//Satellite s07 = new MetalPlanet(this, 340, 460, 27, 2, "2");
		currentState += "s08=tMx500y680s036r002n3 ";
		//Satellite s08 = new MetalPlanet(this, 500, 680, 36, 2, "3");
		currentState += "s09=tGx645y040s026r005n0 ";
		//Satellite s09 = new GasPlanet(this, 645, 40, 26, 5, "0"); 
		currentState += "s10=tGx300y200s083r003n1 ";
		//Satellite s10 = new GasPlanet(this, 300, 200, 83, 3, "1");
		currentState += "s11=tGx240y730s047r002n2 ";
		//Satellite s11 = new GasPlanet(this, 240, 730, 47, 2, "2");
		currentState += "s12=tWx700y600s050r003n3 ";
		//Satellite s12 = new GasPlanet(this, 700, 600, 50, 3, "3");

		// "station_=X___Y___S___N_
		currentState += "st0=x700y100s030n0 ";
		//Satellite station00 = new Station(this, 700, 100, 30);
		currentState += "st1=x313y545s030n1 ";
		//Satellite station01 = new Station(this, 313, 545, 30);
		currentState += "st2=x168y232s030n2 ";
		//Satellite station02 = new Station(this, 168, 232, 30);
		currentState += "st3=x826y630s030n3 ";
		//Satellite station03 = new Station(this, 826, 630, 30);

		currentState += "END";
	}
	
	public static void main(String[] args) {
		ServerController start = new ServerController();
		start.connectToClients();
		start.startUp();
		start.sendStatus();
	}

	
	
}
