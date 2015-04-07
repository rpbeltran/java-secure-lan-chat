package kimono;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class BackEnd {
	
	private String username;
	private String password;
	private String chatroomname;
	private String ip;
	
	public BackEnd(String username, String password, String ip) {
		this.username = username;
		this.password = password;
		this.chatroomname = "";
		this.ip = ip;
	}
	
	//Send message to current chatroom
	public void sendMessage(String message){
		if (chatroomname == "") return; //Not in chat room
		//send message to server here
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