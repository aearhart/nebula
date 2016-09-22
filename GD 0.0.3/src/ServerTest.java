import java.io.*;
import java.net.*;

public class ServerTest {

	static ServerSocket serverSocket;
	static Socket socket;
	static Socket socket2;
	static DataOutputStream out;
	static DataInputStream in;
	private static String s = "A";
	private static String prev = "A";
	
	public static String getMessage(Socket sock) throws IOException  {
		in = new DataInputStream(sock.getInputStream());
		s = in.readUTF();
		System.out.println("Receiving information...  " + s);
		return s;
	}
	
	public static void sendMessage(Socket sock, String s) throws IOException {
		out = new DataOutputStream(sock.getOutputStream());
		out.writeUTF(s);
		System.out.println("Sent message: " + s);
	}
	
	public static String addOne(String s) {
		char value = s.charAt(0);
		char nextValue = (char)((int)value + 1);
		s = "";
		s += nextValue;
		return s;
	}
	
	public static Boolean viable() {
		char orig = prev.charAt(0);
		char possible = s.charAt(0);
//		System.out.println(orig + " <= " + possible + " ?");
		if (((int)orig) >= ((int)possible)) {
			s = prev;
			return false;
		}
		return true;
	}
	
	public static Boolean stillGo() {
		char orig = prev.charAt(0);
		if (((int)orig) >= ((int)'Z'))
			return false;
		return true;
	}
	
	public static void main(String[] args) throws Exception {
		System.out.println("Starting server...");
		serverSocket = new ServerSocket(7777);
		System.out.println("Server started.");
		socket = serverSocket.accept();
		System.out.println("Connection from: " + socket.getInetAddress());
		
		socket2 = serverSocket.accept(); // 2nd client
		System.out.println("Connection from: " + socket2.getInetAddress());	
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
	

}
