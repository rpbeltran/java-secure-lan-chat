package kimono.server;

import java.io.Closeable;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;
//import java.time.LocalTime;
import java.util.Scanner;

public class ClientThread extends Thread implements Closeable {
	
	private static final String SEP = KimonoServer.SEP;
	
	private KimonoServer server;
	private Socket socket;
	
	private Scanner in;
	public PrintWriter out;
	
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
				if (input == null) continue;
				server.evaluateInput(input, this);
			}
		}
	}
	
	@Override
	public void close() {
		persist = false;
		
		out.println("QUIT");
		out.flush();
		
		server.getClientThreads().remove(this);
		try {
			socket.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
}
