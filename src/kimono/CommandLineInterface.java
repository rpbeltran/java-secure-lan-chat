package kimono;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.UnknownHostException;
import java.util.HashMap;

public class CommandLineInterface {

	BufferedReader br;
	BackEnd backend;
	private static HashMap<String, String> commands;
	
	public CommandLineInterface(boolean server,String u, String p, String host, int port) {
		System.out.println();
		br = new BufferedReader(new InputStreamReader(System.in));
		while (u.equals("")) {
			System.out.print("Username: ");
			u = input();
		}
		while (p.equals("")) {
			System.out.print("Password: ");
			p = input();
		}
		/*		// Handle Defaults in Main
		if (host.equals("")) {
			System.out.print("Hostname: ");
			host = input();
			if (host.equals("")) host = "127.0.0.1";
		}
		if (port == 0) {
			System.out.print("Hostname: ");
			String porti = input();
			if (porti.equals("")) {
				port = 32800;
			} else {
				port = Integer.parseInt(porti);
			}
		}*/
		
		setUpCommands();
		
		if (server) {
			//Start headless server
			runClient(true);
		} else {
			
			try {
				backend = new BackEnd(u, p, host, port);
			} catch (UnknownHostException e) {
				System.out.println("Could not reach server "+host+":"+Integer.toString(port));
				System.exit(1);
			} catch (IOException e) {
				System.out.println("Could not connect: IO Error");
				System.exit(1);
			} 
			
			runClient(false);
		}
		
	}
	
	private void runClient(boolean admin){
		System.out.println("Welcome to Closed Kimono");
		System.out.println();
		help();
		System.out.println("Avaliable rooms: ");
		//Display available rooms
		//Somehow make CLI here
	}
	
	private boolean runCommand(String cmd, boolean admin) {
		switch (cmd) 
		{
		case "help":
			help();
			return true;
		default:
			return false;
		}
	}
	
	private void setUpCommands() {
		commands = new HashMap<String, String>();
		commands.put("help", "Displays help");
		commands.put("say", "Send a message");
		commands.put("room", "Create or join a room");
		commands.put("rooms", "List avaliable rooms");
		commands.put("close", "Deletes a room if you are the owner");
	}
	
	private void help() {
		System.out.println("Help:");
		for(String s: commands.keySet()) {
			System.out.println("\t!"+s+" - "+commands.get(s));
		}
		System.out.println();
	}
	
	
	public String input() {
		String r = new String();
		try {
			r = br.readLine();
		} catch (IOException ioe) {
			System.out.println("IO error!");
	        System.exit(1);
	    }
		return r;
	}
	
}
