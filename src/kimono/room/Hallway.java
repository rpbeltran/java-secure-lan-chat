package kimono.room;

import java.util.ArrayList;
import java.util.List;

public class Hallway {
	
	String name;
	AccessManager accessManager;
	Settings settings;
	
	List<Hallway> hallways;
	List<Room> rooms;
	
	public Hallway(String name, User owner, boolean isPrivate){
	
		this.name = name;
		accessManager = new AccessManager(owner, !isPrivate);
		settings = new Settings();
		
		hallways = new ArrayList<Hallway>();
		rooms = new ArrayList<Room>();

	}

}
