import java.io.*;
import java.net.*;

public class ServerTest {

	static ServerSocket serverSocket;
	static Socket socket;
	static DataOutputStream out;
	
	public static void main(String[] args) throws Exception {
		serverSocket = new ServerSocket(7777);
		socket = serverSocket.accept();
		out = new DataOutputStream(socket.getOutputStream());
		out.writeUTF("Heyyyy");
	}

}
