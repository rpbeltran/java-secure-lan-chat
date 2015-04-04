
package testing;

import java.nio.file.Files;
import java.nio.file.Paths;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import javax.xml.bind.DatatypeConverter;

import kimono.room.*;

public class Tester {

	public static void main(String[] args) {
		
		User u = new User("11111111","password");
		User i = new User("22222222","passkey");
		
		//2 is most secure, more secure to be implemented 
		
		Room r = new Room("Test Room", u, 2, true);
		r.accessManager.addAllowedUser(i);
		
		r.recordMessage(u, "Alpha");
		r.recordMessage(i, "Alpha");
		
		for (String l:r.roomFile.readContent()){
			if(l.startsWith("***"))
				System.out.println(l);
			else
				System.out.println(r.encryptionType.decrypt(l, r.getRoomKey()));
		}
	}

	
	
	}