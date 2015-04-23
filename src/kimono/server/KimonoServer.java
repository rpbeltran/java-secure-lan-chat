package kimono.server;

import java.io.Closeable;
import java.io.IOException;
import java.net.ServerSocket;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import kimono.room.Room;
import kimono.room.User;

public class KimonoServer implements Closeable {
	
	public static final String SEP = "\t";
	public static final String LISTSEP = "ß";

	ServerSocket serverSocket;
	
	ConnectionThread connectionThread;
	Set<ClientThread> inputThreads;
	Map<String,User> users;
	Map<String,Room> rooms;
	
	public KimonoServer(int port) throws IOException {
		System.out.println("Server opened on port "+port);
		serverSocket = new ServerSocket(port);
		serverSocket.setSoTimeout(1000);
		
		inputThreads = new HashSet<ClientThread>();
		users = new HashMap<String,User>();
		rooms = new HashMap<String,Room>();
		
		connectionThread = new ConnectionThread(this);
		connectionThread.start();
	}
	
public void evaluateInput(String input, ClientThread thread) {
		
		String[] split = input.split(SEP);
		System.out.println(input);
		
		switch(split[0]) {
			case "QUIT": {
				String username = split[1];
				inputThreads.remove(thread);
				thread.close();
				kick(users.get(username));
				users.remove(username);
				break;
			}
			case "LOGIN": { //Does this even get reached?
				String username = split[1];
				String password = split[2];
				
				// TODO do something with these. (authentication)
				break;
			}
			case "ROOM": {
				String room = split[1];
				String username = split[2];
				User user = users.get(username);
				if (room.equals("")) {
					kick(user);
					updateRooms();
					updateAllUsers();
					break;
				}
				
				if (rooms.containsKey(room)) {
					kick(user);
					Room r = rooms.get(room);
					r.join(user);
					updateRooms();
					updateAllUsers();
				} else {
					kick(user);
					makeRoom(room, user);
					updateRooms();
					updateAllUsers();
				}
				break;
			}
			case "MESS": {
				String room = split[1];
				String username = split[2];
				String message = split[3];
				String timestamp = split[4];
				//String timestamp = LocalTime.now().toString();
				
				// TODO do something more intricate. Currently this just echoes back to client.
				
				Room r = rooms.get(room);
				
				for (ClientThread ct: r.getClientThreads()) {
					ct.out.println(formMessage("MESS", new String[]{username, message, timestamp}));
					ct.out.flush();
				}
				
			}
		}
		
	}
	
	public void addUser(String name, String pass, ClientThread thread) {
		users.put(name, new User(name, pass, thread));
		inputThreads.add(thread);
		updateRooms();
	}

	public void makeRoom(String name, User admin) {
		rooms.put(name, new Room(name, admin));
	}
	
	public void kick(User u) {
		for (Room r: getRooms()) {
			if (r.isPresent(u)) r.leave(u);
			if (r.isEmpty()) rooms.remove(r.name);
		}
	}
	
	public Set<Room> getRooms() {
		Set<Room> r = new HashSet<Room>();
		for (String s: rooms.keySet()) {
			r.add(rooms.get(s));
		}
		return r;
	}
	
	public Set<User> getUsers() {
		Set<User> r = new HashSet<User>();
		for (String s: users.keySet()) {
			r.add(users.get(s));
		}
		return r;
	}
	
	private void updateUsers(Room r) {
		String[] usernames = setToStrings(r.getUsers());
		String mes = "USERS"+SEP+joinList(usernames, LISTSEP);
		
		for (ClientThread ct: r.getClientThreads()) {
			ct.out.println(mes);
			ct.out.flush();
		}
	}
	
	private void updateAllUsers() {
		for (Room r: getRooms()) {
			updateUsers(r);
		}
	}
	
	private void updateRooms() {
		String[] roomnames = setToStrings(getRooms());
		String mes = "ROOMS"+SEP+joinList(roomnames, LISTSEP);
		
		for (ClientThread ct: getClientThreads()) {
			ct.out.println(mes);
			ct.out.flush();
		}
	}

	public synchronized Set<ClientThread> getClientThreads() {
		return inputThreads;
	}
	
	public ServerSocket getServerSocket() {
		return serverSocket;
	}

	@Override
	public void close() {
		connectionThread.close();
		
		for (ClientThread ct : new ArrayList<ClientThread>(getClientThreads())) {
			ct.close();
		}
		try {
			getServerSocket().close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public static String[] setToStrings(@SuppressWarnings("rawtypes") Set s) {
		String[] ret = new String[s.size()];
		int i=0;
		for (Object o:s) {
			ret[i] = o.toString();
			i++;
		}
		return ret;
	}
	
	public static String joinList(String[] l, String sep) {
		if (l.length <= 0) return "";
		String s = l[0];
		for (int i=1; i<l.length;i++) {
			s += sep + l[i];
		}
		return s;
	}
	
	private static String formMessage(String key, String[] values) {
		String ret = key;
		for (String s : values) {
			ret += SEP + s;
		}
		return ret;
	}
	
}
