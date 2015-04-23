package kimono.server;

import java.io.Closeable;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.List;
import java.util.Scanner;


public class ConnectionThread extends Thread implements Closeable {

	private KimonoServer server;
	boolean persist = true;
	private Scanner in;
	
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
			try {
				in = new Scanner(s.getInputStream());
			} catch (IOException e) {
				e.printStackTrace();
			}
			String input = in.nextLine();
			
			String[] values = input.split(KimonoServer.SEP);
			String userName = values[1];
			String password = values[2];
			
			ClientThread inputThread = new ClientThread(s, server);
			server.addUser(userName, password, inputThread);
			inputThread.start();
			System.out.println("Waiting for client...");
		}
	}

	@Override
	public void close() {
		persist = false;
	}
	
}
