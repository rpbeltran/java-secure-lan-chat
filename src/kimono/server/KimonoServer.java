package kimono.server;

import java.io.IOException;
import java.net.ServerSocket;
import java.util.HashSet;
import java.util.Set;

public class KimonoServer {

	ServerSocket serverSocket;
	
	ConnectionThread connectionThread;
	Set<ClientThread> inputThreads;
	
	public KimonoServer(int port) throws IOException {
		System.out.println("Server opened on port "+port);
		serverSocket = new ServerSocket(port);
		
		inputThreads = new HashSet<ClientThread>();
		connectionThread = new ConnectionThread(this);
		connectionThread.start();
	}
	
	public synchronized Set<ClientThread> getInputThreads() {
		return inputThreads;
	}
	
	public ServerSocket getServerSocket() {
		return serverSocket;
	}
	
}
