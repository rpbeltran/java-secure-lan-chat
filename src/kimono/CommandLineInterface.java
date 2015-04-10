package kimono;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.UnknownHostException;
import java.util.HashMap;

import kimono.server.KimonoServer;

public class CommandLineInterface {

	BufferedReader br;
	BackEnd backend;
	KimonoServer server;
	private static HashMap<String, String> commands;
	
	public CommandLineInterface(boolean isserver,String u, String p, String host, int port) {
		System.out.println();
		br = new BufferedReader(new InputStreamReader(System.in));
		if (!u.equals("")) System.out.println("User: "+u);
		while (u.equals("")) {
			System.out.print("Username: ");
			u = input();
		}
		while (p.equals("")) {
			System.out.print("Password: ");
			p = new String(System.console().readPassword());
		}
		
		setUpCommands();
		
		if (isserver) {
			try {
				server = new KimonoServer(port);
			} catch (IOException e) {
				System.out.println("Could not initialize server: IO Error. Check ports?");
				System.exit(1);
			}
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
