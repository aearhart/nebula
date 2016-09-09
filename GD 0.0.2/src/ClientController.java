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
	private Player p1;
	private Player p2;
	private Player currPlayer;
	private Player winner;
	private String status;
	int AoIx = 0;
	int AoIy = 0;
	int AoIs = 0;
	Color AoIc = Color.RED;
	
	public ClientController() {
		// TODO Auto-generated constructor stub
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

		// "st_=X___Y___S___N_
		// "012345678   12  16
		
		// check if satellite (s__) or station (st_)
		if (s.substring(0, 2).equals("st")) {
			// it's a station
			sat = new Station(this, Integer.parseInt(s.substring(5, 8)), Integer.parseInt(s.substring(9, 12)), Integer.parseInt(s.substring(13, 16)));
		}
		else { // it's a planet
			// "s__=T_X___Y___size___resource___name_"
			// "0   4 6   10   14    18   22
			if (s.charAt(5) == 'W') {
				sat = new WaterPlanet(this, Integer.parseInt(s.substring(7, 10)), Integer.parseInt(s.substring(11, 14)), Integer.parseInt(s.substring(15, 18)), Integer.parseInt(s.substring(19, 22)), s.substring(23));
			}
			else if (s.charAt(5) == 'G') {
				sat = new GasPlanet(this, Integer.parseInt(s.substring(7, 10)), Integer.parseInt(s.substring(11, 14)), Integer.parseInt(s.substring(15, 18)), Integer.parseInt(s.substring(19, 22)), s.substring(23));
			}
			else {
				sat = new MineralPlanet(this, Integer.parseInt(s.substring(7, 10)), Integer.parseInt(s.substring(11, 14)), Integer.parseInt(s.substring(15, 18)), Integer.parseInt(s.substring(19, 22)), s.substring(23));
			}
		}

		satellites.add(sat);
	}
	
	public void addToMap() {
		
		for (Satellite s: satellites) {
			map.add(s); 
			s.setBounds(s.getLocX(), s.getLocY(), s.getBoundSize(), s.getBoundSize());
		}
		/*
		p1 = new Player(this, "Player 1");
		p2 = new Player(this, "Player 2");
		p2.setColor(Color.MAGENTA);
		status = "";*/
	}
	
	public void getMessage()  {
		// reads input into variable input
		try {
			in = new DataInputStream(socket.getInputStream());
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		try {
			input = in.readUTF();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		System.out.println("Receiving information...  " + input);
	}
	
	public static void sendMessage() throws IOException {
		out = new DataOutputStream(socket.getOutputStream());
		out.writeUTF(currentState);
		System.out.println("Message sent: " + currentState);
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
				if (s[i].charAt(0) == 'E') {
					addToMap();
				}
			}
		}
		}
	}
	
	public void connectToServer() {
		System.out.println("Connecting to server...");
		try {
			socket = new Socket("localhost", 7777); // local server
			//	socket = new Socket("70.95.122.247", 7777);
			//	socket = new Socket("2605:e000:1c02:8e:9d7f:687b:3d6:73f5", 7777); // computer server
		} catch (IOException e) {
			System.out.println("Unable to connect to server.");
			//e.printStackTrace();
		}

		System.out.println("Connection established.");
	}
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		ClientController control = new ClientController();
		control.connectToServer();	
		control.getMessage();
		control.readMessage();
	}

}
