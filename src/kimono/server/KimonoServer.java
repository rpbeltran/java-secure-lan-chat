package kimono.server;

import java.io.IOException;
import java.net.ServerSocket;

public class KimonoServer {

	ServerSocket serverSocket;
	MakeServer serverGUI;
	
	ConnectionThread connectionThread;
	
	public KimonoServer(int port, MakeServer makeServer) {
		serverGUI = makeServer;
		try {
			serverSocket = new ServerSocket(port);
		} catch (IOException e) {
			serverGUI.displayError("IOException thrown. Please check console.\nTry closing any other programs using the port "+port+".\nException specified: "+e.toString());
			e.printStackTrace();
		}
	}
	
}
