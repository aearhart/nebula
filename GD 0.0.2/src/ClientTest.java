import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;
import java.net.*;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;

public class ClientTest {

	static Socket socket;
	static DataInputStream in;
	static DataOutputStream out;
	private static String s;
	private static JFrame f;
	private static JButton button;
	private static JPanel panel;
	private static Boolean click = true;
	
	public static String getMessage() throws IOException  {
		in = new DataInputStream(socket.getInputStream());
		s = in.readUTF();
		System.out.println("Receiving information...  " + s);

		return s;
	}
	
	public static void sendMessage(String s) throws IOException {
		out = new DataOutputStream(socket.getOutputStream());
		out.writeUTF(s);
		System.out.println("Message sent: " + s);
		}
	
	public static void main(String[] args) throws Exception {
		System.out.println("Connecting...");
		socket = new Socket("localhost", 7777); // local server
//		socket = new Socket("70.95.122.247", 7777);
//		socket = new Socket("2605:e000:1c02:8e:9d7f:687b:3d6:73f5", 7777); // computer server
		System.out.println("Connection established.");
		
		/*
		getMessage();
		System.out.println("Message from server: " + s);

		sendMessage("Hi back.");
		*/
		getMessage();
		// frame setup
		f = new JFrame("testing sockets");
		f.setVisible(true);
		f.setSize(500, 200);
		f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		panel = new JPanel();
		f.add(panel);
		button = new JButton(s);
		panel.add(button);
		button.addActionListener(new Action1());
		
		
	}
	
	public static String addOne(String s) {
		char value = s.charAt(0);
		char nextValue = (char)((int)value + 1);
		s = "";
		s += nextValue;
		return s;
	}
	
	static class Action1 implements ActionListener {
		@Override
		public void actionPerformed (ActionEvent e) {
			if ((int)s.charAt(0) >= 'Z') {
				click = false;
				button.setText("Done");
			}
			if (click) {
			try {
				sendMessage(addOne(s));
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			try {
				getMessage();
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}

			button.setText(s);
			}
			else {
				
			}
		}
	}

}
