package kimono;

import java.io.BufferedReader;
import java.io.Console;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.UnknownHostException;
import java.util.HashMap;
import java.util.List;
import java.util.Scanner;

import kimono.server.KimonoServer;

public class CommandLineInterface extends FrontEnd {

	BufferedReader br;
	BackEnd backend;
	KimonoServer server;
	private static HashMap<String, String> commands;
	private CLIThread thread;
	String username, password, host;
	int port;
	boolean isServer;
	
	public CommandLineInterface(boolean isServer, String u, String p, String host, int port) {
		
		this.isServer = isServer;
		this.username = u;
		this.password = p;
		this.host = host;
		this.port = port;
		
		br = new BufferedReader(new InputStreamReader(System.in));
		
		
	}
	
	@Override
	public boolean login() {
		System.out.println();
		if (!username.equals("")) System.out.println("User: "+username);
		while (username.equals("")) {
			System.out.print("Username: ");
			username = input();
		}
		while (password.equals("")) {
			System.out.print("Password: ");
			Console c = System.console();
			if (c != null) { // If this is a valid console, it will not print the password.
				password = new String(System.console().readPassword());
			} else { // On the other hand, Eclipse doesn't give us one. This is not the preferred method.
				password = new Scanner(System.in).nextLine();
			}
		}
		
		setUpCommands();
		
		if (isServer) {
			try {
				server = new KimonoServer(port);
			} catch (IOException e) {
				System.out.println("Could not initialize server: IO Error. Check ports? Error: "+e.getMessage());
				System.exit(1);
			}
			runClient(true);
		} else {
			
			try {
				backend = new BackEnd(username, password, host, port);
				backend.setFrontEnd(this);
			} catch (UnknownHostException e) {
				System.out.println("Could not reach server "+host+":"+Integer.toString(port)+" - "+e.getMessage());
				System.exit(1);
			} catch (IOException e) {
				System.out.println("Could not connect: IO Error - "+e.getMessage());
				System.exit(1);
			} 
			
			runClient(false);
		}
		
		return true; // TODO We need to check that the password was correct if we are verifying this.
	}
	
	private void runClient(boolean admin){
		System.out.println("Running client now.");
		thread = new CLIThread(br, backend);
		thread.start();
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

	@Override
	public void returnToLogin(String message) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void addMessage(String message) {
		System.out.println(message);
	}

	@Override
	public void updateRooms(List<String> rooms) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void updateUsers(List<String> users) {
		// TODO Auto-generated method stub
		
	}
	
}
