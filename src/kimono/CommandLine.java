package kimono;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class CommandLine {

	BufferedReader br;
	BackEnd backend;
	
	
	public CommandLine(boolean server) {
		br = new BufferedReader(new InputStreamReader(System.in));
		System.out.print("Username: ");
		String u = input();
		System.out.print("Password: ");
		String p = input();
		System.out.print("Server IP: ");
		String host = input();
		System.out.print("Server port: ");
		int port = Integer.parseInt(input());
		
		if (server) {
			//Start headless server
		} else {
			backend = new BackEnd(u, p, host, port);
			runClient();
		}
	}
	
	public CommandLine(boolean server, String host, int port) {
		br = new BufferedReader(new InputStreamReader(System.in));
		System.out.print("Username: ");
		String u = input();
		System.out.print("Password: ");
		String p = input();
		
		if (server) {
			//Start headless server
		} else {
			backend = new BackEnd(u, p, host, port);
			runClient();
		}
		
	}
	
	public CommandLine(boolean server, String u, String p, String host, int port) {
		br = new BufferedReader(new InputStreamReader(System.in));
		if (server) {
			//Start headless server
		} else {
			backend = new BackEnd(u, p, host, port);
			runClient();
		}
		
	}
	
	private void runClient(){
		System.out.println();
		//Somehow make CLI here
	}
	
	public String input() {
		String r = new String();
		try {
			r = br.readLine();
		} catch (IOException ioe) {
			System.out.println("IO error trying to read your name!");
	        System.exit(1);
	    }
		return r;
	}
	
}
