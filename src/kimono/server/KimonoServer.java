package kimono.server;

import java.io.IOException;
import java.net.ServerSocket;
import java.util.HashSet;
import java.util.Set;

public class KimonoServer {

	ServerSocket serverSocket;
	
	ConnectionThread connectionThread;
	Set<InputThread> inputThreads;
	
	public KimonoServer(int port) throws IOException {
		
		serverSocket = new ServerSocket(port);
		
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
