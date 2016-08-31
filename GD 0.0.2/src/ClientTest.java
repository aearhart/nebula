import java.io.*;
import java.net.*;

public class ClientTest {

	static Socket socket;
	static DataInputStream in;
	
	public static void main(String[] args) throws Exception {
		System.out.println("Connecting...");
//		socket = new Socket("localhost", 7300);
		socket = new Socket("70.95.122.247", 7777);
		System.out.println("Connection established.");
		in = new DataInputStream(socket.getInputStream());
		System.out.println("Receiving information...");
		String s = in.readUTF();
		System.out.println("Message from server: " + s);

	}

}
