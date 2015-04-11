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
		while(persist) {
			ServerSocket ss = server.getServerSocket();
			Socket s = null;
			System.out.println("Waiting for client...");
			try {
				s = ss.accept();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			if (s == null) continue;
			System.out.println(s);
			ClientThread inputThread = new ClientThread(s, server);
			server.getClientThreads().add(inputThread);
			inputThread.start();
		}
	}

	@Override
	public void close() {
		persist = false;
	}
	
}
