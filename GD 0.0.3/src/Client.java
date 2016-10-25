import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;

import javax.swing.JButton;
import javax.swing.JFrame;

public class Client implements ActionListener {

	// socket info
	static Socket socket;
	static DataInputStream in;
	static DataOutputStream out;
	private static String input;
	public String currentState = "";
	
	
	public String clientNum;
	
	private String ipAddress = "localhost";//"2605:e000:1c02:8e:6d73:7e5:5e67:f8b5";
	private Chat chat;
	private JButton button;
	private Boolean turn = false;
	public Boolean buttonPressed = false;
	public Client() {
 
	}

	/* SERVER METHODS */

	private void error(IOException e) {
		System.out.println("Error. FAILED");
		e.printStackTrace();
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
			error(e);
		}
		try {
			input = in.readUTF();
		} catch (IOException e) {
			error(e);
		}
		System.out.println("Receiving information...  " + input);
	}
	
	public void sendMessage() {
		/* sends currentState to server */
		try {
			out = new DataOutputStream(socket.getOutputStream());
		} catch (IOException e) {
			error(e);
		}
		try {
			out.writeUTF(currentState);
		} catch (IOException e) {
			error(e);
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
	
	
	/* BASIC METHODS */
	
	public void close() {
		/* close the game */
		System.exit(0);
	}
	
	private void setUpChat() {
		// TODO Auto-generated method stub
		chat = new Chat(this);
	}
	
	private void setUpButton() {
		button = new JButton("Not your turn");
		button.setActionCommand("Done");
		button.addActionListener(this);
	}
	
	public void firstContact() {
		getMessage(); // get message from server denoting player number
		clientNum = input;
	}
	
	public void read() {
		String[] s = input.split(Globals.delim);
		switch(s[0]) {
		case "MESSAGE": {
			// it's a message, update the Chat
			if (! s[1].equals(clientNum))
				chat.updateHistory(s[2]);
			return;
		}
		case "TURN": {
			// it's a turn
			if (s[1].equals(clientNum)) {
			// it's your turn
				turn = true;
				button.setText("Done");
			}
			else {
				// it's not your turn, do nothing
				turn = false;
			}
		}
		}
	}
	
	public void play() {
		getMessage();
		read();
		if (! chat.message.equals("")) { // there's a message
			currentState = "MESSAGE@@" + clientNum + "@@" + chat.message;
			sendMessage();
			chat.message = "";
		}
		else if (buttonPressed) {
			// button is pressed
			currentState = "DONE@@" + clientNum;
			sendMessage();
			button.setText("Not your turn");
			buttonPressed = false;
		}
		else {
			// nothing happened
			currentState = "NONE@@" + clientNum;
			sendMessage();
		}
		play();
	}
	
	/* MAIN */
	 
	public static void main(String[] args) {

		Client control = new Client();
		//Globals.setWinSize();
		control.connectToServer();	
		
		control.firstContact();
		
		JFrame f = new JFrame("hello there " + control.clientNum);
		f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); 
		f.setSize(new Dimension(500, 500));
		f.setLayout(new BorderLayout());
		control.setUpChat();
		f.add(control.chat, BorderLayout.NORTH);
		control.setUpButton();
		f.add(control.button, BorderLayout.SOUTH);
		
		f.pack();
		f.setVisible(true);
		
		control.play();
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		if (e.getActionCommand().equals("Done")) {
			if (turn) {
				buttonPressed = true;
				button.setText("Not your turn");
				turn = false;
			}
			System.out.println("CLICKED. Turn = " + turn + " buttonPressed = " + buttonPressed);
		}
	}
}
	
