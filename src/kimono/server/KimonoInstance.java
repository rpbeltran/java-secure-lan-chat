package kimono.server;

import java.util.ArrayList;
import java.util.List;

import kimono.room.Hallway;
import kimono.room.RoomOld;
import kimono.room.User;

public class KimonoInstance {
	
	private List<User> Users = new ArrayList<User>();
	
	public KimonoInstance(String adminusername, String adminpassword){
		//User admin = new User(adminusername, adminpassword);
		//Hallway mainHallway = new Hallway("Closed Kimono", admin, false);
	}
	
	public void makeHallway(Hallway root, String name, User owner, boolean isPrivate){
		root.addHallway(new Hallway(name, owner, isPrivate));
	}
	
	public void makeRoom(Hallway root, String name, User owner, boolean isPrivate){
		root.addRoom(new RoomOld(name, owner, isPrivate));
	}
	
	public void addUser(String username, String password){
		//Users.add(new User(username, password));
	}

}
