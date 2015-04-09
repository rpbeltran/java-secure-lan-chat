package kimono.server;

import java.io.IOException;
import java.net.ServerSocket;
import java.util.HashSet;
import java.util.Set;

public class KimonoServer {

	ServerSocket serverSocket;
	MakeServer serverGUI;
	
	ConnectionThread connectionThread;
	Set<InputThread> inputThreads;
	
	public KimonoServer(int port, MakeServer makeServer) {
		serverGUI = makeServer;
		try {
			serverSocket = new ServerSocket(port);
		} catch (IOException e) {
			serverGUI.displayError("IOException thrown. Please check console.\nTry closing any other programs using the port "+port+".\nException specified: "+e.toString());
			e.printStackTrace();
		}
		
		inputThreads = new HashSet<InputThread>();
		connectionThread = new ConnectionThread(this);
	}
	
	public synchronized Set<InputThread> getInputThreads() {
		return inputThreads;
	}
	
	public ServerSocket getServerSocket() {
		return serverSocket;
	}
	
}
