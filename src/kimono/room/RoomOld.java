
package kimono.room;

import java.math.BigInteger;
import java.security.SecureRandom;

import kimono.encryption.*;

public class RoomOld {
	
	private String roomName;
	private int roomKey;
	public CryptoAlgorithm encryptionType;
	public RoomFile roomFile;
	public AccessManager accessManager;
	public Settings settings;
	private String roomAESKey;
	
	public RoomOld(String name, User owner,  boolean isPrivate){
		
		roomName = name;
		//Only for insecure test algorithms
		roomKey = (int)(Math.random()*999);
		
		//Used for AES real encryption
		SecureRandom random = new SecureRandom();
		roomAESKey = new BigInteger(130, random).toString(32);
		
		encryptionType = new AES(roomAESKey);
		
		accessManager = new AccessManager(owner, !isPrivate);
		roomFile = new RoomFile(buildFilename());
		settings = new Settings();
		
		roomFile.writeOver("***Encryption: Strong\n");
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
