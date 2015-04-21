package kimono;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Scanner;

public class BackEnd {
	
	private String username;
	private String password;
	private String chatroomname;
	private Socket socket;
	private Scanner in;
	private PrintWriter out;
	private FrontEnd frontEnd;
	private List<List<String>> messages;
	
	public BackEnd(String username, String password, String hostname, int port)  throws UnknownHostException, IOException{
		this.username = username;
		this.password = password;
		this.chatroomname = "";
		
		messages = new ArrayList<List<String>>();
		
		socket = new Socket(hostname, port);
		in = new Scanner(socket.getInputStream());
		out = new PrintWriter(socket.getOutputStream());
		
		out.println("LOGIN:"+username+":"+password);
		out.flush();
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
		
		out.println("MESS:"+chatroomname+":"+username+":"+message);
		out.flush();
		
	}
	
	//Join or instantiate a chat room
	public void joinRoom(String roomname){
		if (roomname == "") //Should exit room
			return;
		
		out.println("ROOM:"+roomname+":"+username); // Handle failure?
		out.flush();
		chatroomname = roomname;
	}
	
	private List<String> parseList(String list) {
		ArrayList<String> things = new ArrayList<String>();
		
		for(String s: list.split("|")) {
			things.add(s);
		}
		
		return things;
	}
	
	public void handleInput(String input) {
		
		String[] values = input.split(":");
		
		switch(values[0]) {
			case "MESS":
				ArrayList<String> message = new ArrayList<String>();
				message.add(values[1]);
				message.add(values[2]);
				message.add(values[3]);
				messages.add(message);
				frontEnd.updateMessages(messages);
				break;
			case "STOP":
				frontEnd.stop();
				break;
			case "ROOMS":
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
		msg.add(Integer.toString(messages.size()+1));//LocalTime
		messages.add(msg);
		frontEnd.updateMessages(messages);
	}
}