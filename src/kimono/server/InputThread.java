package kimono.server;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Scanner;

public class InputThread extends Thread {

	Socket socket;
	KimonoServer server;
	
	Scanner in;
	PrintWriter out;
	
	boolean persist = true;
	
	public InputThread(Socket socket, KimonoServer server) {
		this.socket = socket;
		this.server = server;
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
				System.out.println(in.nextLine());
			}
		}
	}
	
}
