package kimono.room;

public class User {
	
	private String username, password;
	
	public User(String username, String password){
		this.username = username;
		this.password = password;		
	}

	public String getUsername() {
		return username;
	}

	public boolean isPassword(String authentication ) {
		return password.equals(authentication);
	}
	
}
