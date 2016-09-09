import java.io.*;
import java.net.*;


public class Server {
	
	static ServerSocket server;
	static Socket player1;
	static Socket player2;
	static DataOutputStream out;
	static DataInputStream in;
	private static String input = "";
	private static String currentState = "";
		
	
	public Server() {
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
	
	public void connectToClients() {
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
	
	public void createGame() {
		// Create game info to set currentState
		ServerController control = new ServerController();
		control.startUp();
	}
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		
		Server start = new Server();
		
		start.connectToClients();
		start.createGame();

		/*out = new DataOutputStream(socket.getOutputStream());
		out.writeUTF("Heyyyy");*/
		/*
		sendMessage("Hi there.");

		
		getMessage();
		System.out.println(s);
		*/
		
		sendMessage(socket, s);
		sendMessage(socket2, s); // 2nd client
		
		while(stillGo()) {
			getMessage(socket);
			if (viable()) {
				s = addOne(s);
				prev = s;
				sendMessage(socket, s);
			}
			else
				sendMessage(socket, s);
			
			getMessage(socket2);         // 2nd client
			if (viable()) {
				s = addOne(s);
				prev = s;
				sendMessage(socket2, s);
			}
			else
				sendMessage(socket2, s);  /// end of second client
		}
		
		getMessage(socket);
		sendMessage(socket, "1");
		
		getMessage(socket2);      /// 2nd client
		sendMessage(socket2, "1"); // end of 2nd client
		
		System.out.println("Done");
	}


	public void startUp() {
		// TODO Auto-generated method stub
		
	}

}
