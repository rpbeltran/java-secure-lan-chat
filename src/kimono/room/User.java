package kimono.room;

import java.util.HashMap;
import java.util.Map;

public class User {
	
	private String username, password;
	private int key;
	private Map<String,Integer> roomKeys; 
	
	public User(String username, String password){
		this.username = username;
		this.password = password;
		this.key = password.hashCode();
		this.roomKeys = new HashMap(); 
	}

	public String getUsername() {
		return username;
	}
	
	public int getKey(){
		return key;
	}

	public boolean isPassword(String authentication ) {
		return password.equals(authentication);
	}
	
	
}
