
package kimono.room;

import kimono.encryption.*;

public class Room {
	
	private String roomName;
	private int roomKey;
	public CryptoAlgorithm encryptionType;
	public RoomFile roomFile;
	public AccessManager accessManager;
	public Settings settings;
	
	public Room(String name, User owner, int encryption, boolean isPrivate){
		
		roomName = name;
		roomKey = (int)(Math.random()*999);
		
		switch(encryption){
			case 0: encryptionType = new ROT26();
			case 1: encryptionType = new ROT13();
			case 2: encryptionType = new ROTKey();
		}
		
		accessManager = new AccessManager(owner, !isPrivate);
		roomFile = new RoomFile(buildFilename());
		settings = new Settings();
		
		//TODO: Replace with better solution 
		roomFile.writeOver("***Encryption: " + Integer.toHexString(encryption) + "\n");
	}
	
	public void recordMessage(User user, String message){
		
		if(!accessManager.isUserAllowed(user)){
			System.out.println("Authentication Invalid, please log in as entitled user");
			return;
		}
		String fullMessage = ">>>" + user.getUsername() + " said:\n" + "   " + message;
		fullMessage = encryptionType.encrypt(fullMessage, roomKey);
		roomFile.writeAfter(fullMessage);
	}
	
	private String buildFilename(){
		return roomName+".ckr";
	}
	
	public int getRoomKey(){
		return roomKey;
	}

}
