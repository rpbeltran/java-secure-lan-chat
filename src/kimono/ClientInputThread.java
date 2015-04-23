package kimono;

import java.io.Closeable;
import java.util.Scanner;

public class ClientInputThread extends Thread implements Closeable {

	Scanner in;
	boolean continueRunning;
	BackEnd backend;
	
	public ClientInputThread(Scanner in, BackEnd backend) {
		this.in = in;
		this.backend = backend;
		continueRunning = true;
	}
	
	@Override
	public void run() {
		while(continueRunning) {
			if (in.hasNextLine()) {
				backend.handleInput(in.nextLine());
			}
		}
	}
	
	@Override
	public void close() {
		continueRunning = false;
	}
	
}
