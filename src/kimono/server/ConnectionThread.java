package kimono.server;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;


public class ConnectionThread extends Thread {

	KimonoServer server;
	boolean persist = true;
	
	public ConnectionThread(KimonoServer server) {
		this.server = server;
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
			System.out.println(s);
			InputThread inputThread = new InputThread(s, server);
			server.getInputThreads().add(inputThread);
			inputThread.start();
		}
	}
	
}
