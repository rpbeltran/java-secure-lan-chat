package kimono.server;

import java.io.Closeable;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;


public class ConnectionThread extends Thread implements Closeable {

	private KimonoServer server;
	boolean persist = true;
	
	public ConnectionThread(KimonoServer server) {
		this.server = server;
		this.setName("ConnectionThread");
	}
	
	@Override
	public void run() {
		System.out.println("Waiting for client...");
		while(persist) {
			ServerSocket ss = server.getServerSocket();
			Socket s = null;
			try {
				s = ss.accept();
			} catch (IOException e) {
				//Do nothing. It was clogging the console.
			}
			if (s == null) continue;
			System.out.println(s);
			ClientThread inputThread = new ClientThread(s, server);
			server.getClientThreads().add(inputThread);
			inputThread.start();
			System.out.println("Waiting for client...");
		}
	}

	@Override
	public void close() {
		persist = false;
	}
	
}
