import java.io.*;
import java.net.*;

public class ClientTest {

	static Socket socket;
	static DataInputStream in;
	
	public static void main(String[] args) throws Exception {
		System.out.println("Connecting...");
		socket = new Socket("172.16.42.2", 7778);
		System.out.println("Connection established.");
		in = new DataInputStream(socket.getInputStream());
		System.out.println("Receiving information...");
		String s = in.readUTF();
		System.out.println("Message from server: " + s);

	}

}
