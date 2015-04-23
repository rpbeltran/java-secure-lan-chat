package kimono.room;

import java.util.ArrayList;
import java.util.List;

public class Hallway {
	
	String name;
	AccessManager accessManager;
	Settings settings;
	
	List<Hallway> hallways;
	List<RoomOld> rooms;
	
	public Hallway(String name, User owner, boolean isPrivate){
	
		this.name = name;
		accessManager = new AccessManager(owner, !isPrivate);
		settings = new Settings();
		
		hallways = new ArrayList<Hallway>();
		rooms = new ArrayList<RoomOld>();

	}
	
	public void addHallway(Hallway child){
		hallways.add(child);
	}
	
	public List<Hallway> getHallways(){
		return hallways;
	}
	
	public void addRoom(RoomOld child){
		rooms.add(child);
	}
	public List<RoomOld> getRooms(){
		return rooms;
	}

}
