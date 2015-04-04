package kimono;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class BackEnd {
	
	//TODO: GO THROUGH NEW ROOM INTERFACE
	
	private HashMap<String, List<String>> theData;
	//Go through new room interface
	//private Encoder encoder;
	
	public BackEnd(String filename) {
		theData = new HashMap<String, List<String>>();
		//Go through new room interface
		//Encoder encoder = new Encoder(filename);
		load();
	}
	
	public List<String> getMessages(String s) {
		//Go through new room interface
		return theData.get(s);
	}
	
	public void addMessage(String user, String msg) {
		
		/* Go through new room interface

		if (!theData.keySet().contains(user)) {
			theData.put(user, new ArrayList<String>());
		}
		theData.get(user).add(msg);
		save();
		*/
	}
	
	public List<String> getUsers() {
		//Go through new room interface
		ArrayList<String> userList = new ArrayList<String>();
		userList.addAll(theData.keySet());
		return userList;
	}

	public void save() {
		//Go through new Room interface
		//encoder.write(theData);
	}
	
	public void load() {
		//Go through new room interface
		//theData = encoder.read();
	}
}
