package kimono.room;

import java.util.HashMap;
import java.util.Map;

import kimono.server.ClientThread;

public class User {
	
	private String username, password;
	private int key;
	private Map<String,Integer> roomKeys; 
	private ClientThread thread;
	
	public User(String username, String password, ClientThread thread){
		this.username = username;
		this.password = password;
		this.key = password.hashCode();
		this.roomKeys = new HashMap(); 
		this.thread = thread;
	}

	public String getUsername() {
		return username;
	}
	
	public int getKey(){
		return key;
	}

	public ClientThread getThread() {
		return thread;
	}
	
	public boolean isPassword(String authentication ) {
		return password.equals(authentication);
	}
	
	@Override
	public String toString() {
		return username;
	}
}
