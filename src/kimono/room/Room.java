package kimono.room;

import java.util.HashSet;
import java.util.Set;

import kimono.server.ClientThread;

public class Room {
	
	public String name;
	private Set<User> users;
	private User admin;
	
	public Room(String name, User admin) {
		this.name = name;
		this.admin = admin;
		
		users = new HashSet<User>();
		users.add(admin);
	}
	
	public void join(User u) {
		users.add(u);
	}
	
	public void leave(User u) {
		users.remove(u);
	}
	
	public void setAdmin(User u) {
		admin = u;
	}
	
	public Set<ClientThread> getClientThreads() {
		Set<ClientThread> threads = new HashSet<ClientThread>();
		for (User u: users) {
			threads.add(u.getThread());
		}
		return threads;
	}
	
	public Set<User> getUsers() {
		return users;
	}
	
	public boolean isAdmin(User u) {
		return admin.equals(u);
	}
	
	public boolean isPresent(User u) {
		for (User user: users) {
			if (user.equals(u)) return true;
		}
		return false;
	}
	
	public boolean isEmpty(){
		if (users.size() <= 0) {
			return true;
		}
		return false;
	}
	
	@Override
	public String toString() {
		return name;
	}
}
