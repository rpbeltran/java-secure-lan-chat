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
	
	public BackEnd(String username, String password, String hostname, int port) {
		this.username = username;
		this.password = password;
		this.chatroomname = "";
		try {
			socket = new Socket(hostname, port);
			in = new Scanner(socket.getInputStream());
			out = new PrintWriter(socket.getOutputStream());
			
			out.println("LOGIN:"+username+":"+password);
			
		} catch (UnknownHostException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	//Send message to current chatroom
	public void sendMessage(String message){
		if (chatroomname == "") return; //Not in chat room
		
		out.println("MESS:"+chatroomname+":"+message);
		out.flush();
		
	}
	
	//Join or instantiate a chat room
	public void joinRoom(String roomname){
		if (roomname == "") return;
		chatroomname = roomname;
		//connect to server here
	}
	
	//Return a list of all recent messages
	public List<HashMap<String,String>> getMessages() {
		ArrayList<HashMap<String,String>> messages = new ArrayList<HashMap<String,String>>();
		return messages;
		
	}
	
	//Return a list of all users in the room
	public List<String> getUsers() {
		ArrayList<String> users = new ArrayList<String>();
		return users;
				
	}
	
}