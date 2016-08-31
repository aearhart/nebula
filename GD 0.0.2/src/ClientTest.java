import java.io.*;
import java.net.*;

public class ClientTest {

	static Socket socket;
	static DataInputStream in;
	
	public static void main(String[] args) throws Exception {
		socket = new Socket("172.16.42.2", 7777);
		in = new DataInputStream(socket.getInputStream());
		String s = in.readUTF();
		System.out.println("Message from server: " + s);

	}

}
