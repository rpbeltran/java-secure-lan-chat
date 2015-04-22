package kimono.server;

import java.io.Closeable;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Scanner;

public class ClientThread extends Thread implements Closeable {
	
	public static final char NETWORK_SEPARATOR = ':';
	
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
				evaluateInput(input);
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
		
		String[] split = input.split(""+NETWORK_SEPARATOR);
		
		switch(split[0]) {
			case "QUIT": {
				close();
				break;
			}
			case "LOGIN": {
				String username = split[1];
				String password = split[2];
				
				// TODO do something with these. (authentication)
				break;
			}
			case "ROOM": {
				String room = split[1];
				String username = split[2];
				break;
			}
			case "MESS": {
				String room = split[1];
				String username = split[2];
				String message = split[3];
				String timestamp = split[4];
				
				// TODO do something more intricate. Currently this just echoes back to client.
				
				out.println(formMessage("MESS", new String[]{username, message, timestamp}));
			}
		}
		
	}
	
	private static String formMessage(String key, String[] values) {
		String ret = key;
		for (String s : values) {
			ret += NETWORK_SEPARATOR + s;
		}
		return ret;
	}
	
}
