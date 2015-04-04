package testing;


import kimono.room.*;

public class Tester {

	public static void main(String[] args) {
		
		User u = new User("User 1","password");
		User i = new User("User 2","passkey");
		
		Room r = new Room("Test Room", u, 3, true);
		r.accessManager.addAllowedUser(i);
		
		r.recordMessage(u, "Test Message");
		r.recordMessage(i, "Another Test Message");
		
		for (String l:r.roomFile.readContent()){
			if(l.startsWith("***"))
				System.out.println(l);
			else
				System.out.println(r.encryptionType.decrypt(l, r.getRoomKey()));
		}
	}

	
	
	}