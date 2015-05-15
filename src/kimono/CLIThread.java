package kimono;

import java.io.BufferedReader;
import java.io.Closeable;
import java.io.IOException;

public class CLIThread extends Thread implements Closeable {

	private boolean persist = true;
	private BufferedReader br;
	private BackEnd backend;
	
	public CLIThread(BufferedReader br, BackEnd backend) {
		this.br = br;
		this.backend = backend;
	}
	
	@Override
	public void run() {
		while (persist) {
			try {
				if (br.ready()) {
					backend.handleCLIInput(br.readLine());
				}
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
	
	@Override
	public void close() {
		persist = false;
	}
	
}
