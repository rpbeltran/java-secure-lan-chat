package kimono;

import java.io.Closeable;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import kimono.server.KimonoServer;

public class BackEnd implements Closeable {
	
	private String username;
	private String password;
	private String chatroomname;
	private Socket socket;
	private Scanner in;
	private PrintWriter out;
	private FrontEnd frontEnd;
	private List<List<String>> messages;
	private ClientInputThread thread;
	
	private static final String SEP = KimonoServer.SEP;
	
	public BackEnd(String user, String pass, String hostname, int port)  throws UnknownHostException, IOException{
		this.username = user;
		this.password = pass;
		this.chatroomname = "";
		
		messages = new ArrayList<List<String>>();
		
		socket = new Socket(hostname, port);
		in = new Scanner(socket.getInputStream());
		out = new PrintWriter(socket.getOutputStream());
		
		out.println("LOGIN"+SEP+username+SEP+password);
		out.flush();
		
		thread = new ClientInputThread(in, this);
		thread.start();
	}
	
	public void setFrontEnd(FrontEnd fe) {
		frontEnd = fe;
	}
	
	//returns the list of local messages
	public List<List<String>> getMessages() {
		return messages;
	}
	
	//Send message to current chatroom
	public void sendMessage(String message){
		if (chatroomname == "") {
			errorMessage("ALERT", "Please join a room");
			return;
		}; //Not in chat room
		String timestamp = "TIME";
		
		
		out.println("MESS"+SEP+chatroomname+SEP+username+SEP+message+SEP+timestamp);
		out.flush();
		
	}
	
	//Join or instantiate a chat room
	public void joinRoom(String roomname){
		if (roomname.equals("") || roomname == null) //Should exit room
			return;
		
		out.println("ROOM"+SEP+roomname+SEP+username); // TODO Handle failure?
		out.flush();
		chatroomname = roomname;
	}
	
	public String getRoom() {
		return chatroomname;
	}
	
	private List<String> parseList(String list) {
		ArrayList<String> things = new ArrayList<String>();
		String[] sp = list.split(KimonoServer.LISTSEP);
		
		for(String s: sp) {
			things.add(s);
		}
		System.out.println(sp.toString());
		return things;
	}
	
	public void handleInput(String input) {
		
		String[] values = input.split(SEP);
		System.out.println(input);
		
		switch(values[0]) {
			case "MESS":
				ArrayList<String> message = new ArrayList<String>();
				message.add(values[1]);
				message.add(values[2]);
				message.add(values[3]);
				messages.add(message);
				frontEnd.updateMessages(messages);
				break;
			case "QUIT":
				frontEnd.returnToLogin("Server closed.");
				break;
			case "ROOMS":
				if (values.length<=1) {
					frontEnd.updateRooms(new ArrayList());
					break;
				}
					
				frontEnd.updateRooms(parseList(values[1])); //TODO: Handle lists here
				break;
			case "USERS":
				frontEnd.updateUsers(parseList(values[1]));
				break;
			default:
				errorMessage("ERROR", "Unknown server message - "+input);

		}		
		
	}
	
	public void errorMessage(String tag, String m){
		ArrayList<String> msg = new ArrayList<String>();
		msg.add(tag);
		msg.add(m);
		msg.add("TIME");//LocalTime
		messages.add(msg);
		frontEnd.updateMessages(messages);
	}

	@Override
	public void close() {
		
		out.println("QUIT"+SEP+username);
		out.flush();
		thread.close();
		try {
			socket.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
	}
}