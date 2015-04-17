package kimono.server;

import java.io.Closeable;
import java.io.IOException;
import java.net.ServerSocket;
import java.util.HashSet;
import java.util.Set;

public class KimonoServer implements Closeable {

	ServerSocket serverSocket;
	
	ConnectionThread connectionThread;
	Set<ClientThread> inputThreads;
	
	public KimonoServer(int port) throws IOException {
		System.out.println("Server opened on port "+port);
		serverSocket = new ServerSocket(port);
		serverSocket.setSoTimeout(1000);
		
		inputThreads = new HashSet<ClientThread>();
		connectionThread = new ConnectionThread(this);
		connectionThread.start();
	}
	
	
	
	public synchronized Set<ClientThread> getClientThreads() {
		return inputThreads;
	}
	
	public ServerSocket getServerSocket() {
		return serverSocket;
	}

	@Override
	public void close() {
		
		connectionThread.close();
		try {
			getServerSocket().close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		for (ClientThread ct : getClientThreads()) {
			ct.close();
		}
	}
	
}
