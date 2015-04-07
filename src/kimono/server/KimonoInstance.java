package kimono.server;

import kimono.room.Hallway;
import kimono.room.Room;
import kimono.room.User;

public class KimonoInstance {
	
	public KimonoInstance(String adminusername, String adminpassword){
		User admin = new User(adminusername, adminpassword);
		Hallway mainHallway = new Hallway("Closed Kimono", admin, false);
	}
	
	public void makeHallway(Hallway root, String name, User owner, boolean isPrivate){
		root.addHallway(new Hallway(name, owner, isPrivate));
	}
	
	public void makeRoom(Hallway root, String name, User owner, boolean isPrivate){
		root.addRoom(new Room(name, owner, isPrivate));
	}

}
