package kimono.server;

import java.io.IOException;
import java.net.ServerSocket;

public class KimonoServer {

	ServerSocket serverSocket;
	MakeServer serverGUI;
	
	public KimonoServer(int port) {
		try {
			serverSocket = new ServerSocket(port);
		} catch (IOException e) {
			serverGUI.displayError("IOException thrown. Please check console.\nTry closing any other programs using the port "+port+".");
			e.printStackTrace();
		}
	}
	
}
