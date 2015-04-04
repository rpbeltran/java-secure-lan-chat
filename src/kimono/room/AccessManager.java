
package kimono.room;

import java.util.ArrayList;
import java.util.List;

public class AccessManager {
	
	private User owner;
	private List<User> allowedUsers;
	private boolean isOpen;
	
	public AccessManager(User owner, boolean isPublic){
		this.owner = owner;
		isOpen = isPublic;
		allowedUsers = new ArrayList<User>();
		allowedUsers.add(owner);
	}
	
	public boolean getIsOpen(){
		return isOpen;
	}
	
	public List<User> getAllowedUsers(){
		return allowedUsers;
	}
	
	public void setAllowedUsers(List<User> users){
		allowedUsers = users;
	}
	
	public boolean isUserAllowed(User user){
		return allowedUsers.contains(user);
	}
	
	public void addAllowedUser(User user){
		allowedUsers.add(user);
	}
	
}
