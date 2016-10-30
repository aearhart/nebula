import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;

public class Server {
	
	static ServerSocket server;
	static Socket player1;
	static Socket player2;
	static DataOutputStream out;
	static DataInputStream in;
	private static String input = "";
	private String currentState = "";
	private Boolean valid = true;
	
	private String currentPlayer = "P1";
	private Socket currentSocket;
	private Socket opposingSocket;
	

	public Server() {
	}
	
	/* SERVER - CLIENT methods */
	
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
		System.out.println("IN: " + input);
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
		System.out.println("OUT >" + ": " + currentState);
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

	/* Basic Gameplay methods */
	
	private void switchSocket() {
		// switch current player variables
		if (currentPlayer.equals("P1")) {
			currentPlayer = "P2";
			currentSocket = player2;
			opposingSocket = player1;
			}
		else {
			currentPlayer = "P1";
			currentSocket = player1;
			opposingSocket = player2;
		}
	}
	
	public void read(String whichOne) {
		// default reading of game state from currentSocket
		
		// input into currentState
		String s[] = input.split(Globals.delim);
		
		// special chat message
		if (s[0].equals("MESSAGE")) {
			System.out.println("Got a message!");
			String copyState = currentState;
			currentState = input;
			return;
		}
		
		else if (s[0].equals("DONE")){ //DONE@@ClientNum
			// normal turn: switches turn to other socket
			switchSocket();
			currentState = "TURN@@" + currentPlayer;
		}
		else { // NONE@@ClientNum
			if (currentState.equals("")) {
				currentState = "TURN@@" + currentPlayer;
			}
		}
				
	}	
	
	public void firstContact() {
		currentState = "P1";
		sendMessage(player1);
		currentState = "P2";
		sendMessage(player2);
	}
	
	public void play() {
		try {
			Thread.sleep(500);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		sendMessage(currentSocket);
		sendMessage(opposingSocket);
		currentState = "";
		getMessage(opposingSocket);
		read("opp");
		getMessage(currentSocket);
		read("curr");
		play();
		
		// listens for a message from player 1, it's either a message (still player 1 turn) or end turn (switch to player 2)
		
		// listens for a message from player 2 (still player 1 turn)
		
	}
	
	public static void main(String[] args) {

		Server server = new Server();
		server.connectToClients();
		server.firstContact();
		server.currentSocket = player1;
		server.opposingSocket = player2;
		server.currentState = "TURN@@" + server.currentPlayer;
		server.play();
		
		System.out.println("END");
	}



}
