package kimono;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class BackEnd {
	
	private HashMap<String, List<String>> theData;
	private Encoder encoder;
	
	public BackEnd(String filename) {
		theData = new HashMap<String, List<String>>();
		Encoder encoder = new Encoder(filename);
		load();
	}
	
	public List<String> getMessages(String s) {
		return theData.get(s);
	}
	
	public void addMessage(String user, String msg) {
		if (!theData.keySet().contains(word)) {
			theData.put(user, new ArrayList<String>());
		}
		theData.get(user).add(msg);
		save();
	}
	
	public List<String> getUsers() {
		ArrayList<String> userList = new ArrayList<String>();
		userList.addAll(theData.keySet());
		return userList;
	}

	public void save() {
		encoder.write(theData);
	}
	
	public void load() {
		theData = encoder.read();
	}
}
