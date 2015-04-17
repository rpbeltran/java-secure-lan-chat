package kimono.server;

import java.io.Closeable;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Scanner;

public class ClientThread extends Thread implements Closeable {
	
	private KimonoServer server;
	private Socket socket;
	
	private Scanner in;
	private PrintWriter out;
	
	private static int threadNumber = 1;
	
	boolean persist = true;
	
	public ClientThread(Socket socket, KimonoServer server) {
		this.server = server;
		this.socket = socket;
		this.setName("ClientThread" + threadNumber++);
		try {
			in = new Scanner(socket.getInputStream());
			out = new PrintWriter(socket.getOutputStream());
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	
	@Override
	public void run() {
		while(persist) {
			if (in.hasNext()) {
				String input = in.nextLine();
				System.out.println(this.getName()+" received input: "+input);
			}
		}
	}
	
	@Override
	public void close() {
		persist = false;
		server.getClientThreads().remove(this);
		try {
			socket.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void evaluateInput(String input) {
		
		if (input.startsWith("QUIT")) {
			close();
		} else if (input.startsWith("LOGIN")) { // Input should be in the form of 
			String[] params = input.split(":"); // 
			String username = params[1];
			String password = params[2];
		}
		
	}
	
}
