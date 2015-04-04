
package kimono.room;

import kimono.encryption.*;

public class Room {
	
	private String roomName;
	
	private CryptoAlgorithm encryptionType;
	private RoomFile roomFile;
	private AccessManager accessManager;
	private Settings settings;
	
	public Room(String name, User owner, int encryption, boolean isPrivate)
	{	
		roomName = name;
		switch(encryption){
			case 0: encryptionType = new ROT26();
			case 1: encryptionType = new ROT13();
			case 2: encryptionType = new ROTKey();
		}
		
		accessManager = new AccessManager(owner, !isPrivate);
		roomFile = new RoomFile(buildFilename());
		settings = new Settings();
		
		//TODO: Replace with better solution 
		roomFile.writeOver("Encryption: "+Integer.toHexString(encryption));
	
	}
	
	private String buildFilename(){
		return roomName+((int)Math.random()*200);
	}

}
