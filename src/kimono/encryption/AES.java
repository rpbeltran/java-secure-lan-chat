package kimono.encryption;

import java.security.spec.KeySpec;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.PBEKeySpec;
import javax.crypto.spec.SecretKeySpec;

import sun.misc.BASE64Decoder;
import sun.misc.BASE64Encoder;

public class AES implements CryptoAlgorithm {
	
	private static final byte[] SALT = {
        (byte) 0xA9, (byte) 0x9B, (byte) 0xC8, (byte) 0x32,
        (byte) 0x56, (byte) 0x35, (byte) 0xE3, (byte) 0x03
    };
    private static final int ITERATION_COUNT = 65536;
    private static final int KEY_LENGTH = 256/2;
    private Cipher ecipher;
    private Cipher dcipher;
    
    public boolean ErrorState = false;
    
    public AES(String keyPhrase){
    	try {
			SecretKeyFactory factory = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA1");
			KeySpec spec = new PBEKeySpec(keyPhrase.toCharArray(), SALT, ITERATION_COUNT, KEY_LENGTH);
			SecretKey tmp = factory.generateSecret(spec);
			SecretKey secret = new SecretKeySpec(tmp.getEncoded(), "AES");
 
			ecipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
			ecipher.init(Cipher.ENCRYPT_MODE, secret);
      
			dcipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
			byte[] iv = ecipher.getParameters().getParameterSpec(IvParameterSpec.class).getIV();
			dcipher.init(Cipher.DECRYPT_MODE, secret, new IvParameterSpec(iv));
		} catch (Exception e) {
			ErrorState = true;
			e.printStackTrace();
			System.out.println("Error Initializing AES");
		}
    }
	@Override
	public String encrypt(String message, int key) {
		return encrypt(message);
	}

	@Override
	public String encrypt(String message) {
		try{
			 byte[] bytes = message.getBytes("UTF8");
		     byte[] encrypted = ecipher.doFinal(bytes);
		     return new BASE64Encoder().encode(encrypted);
		}
		catch(Exception e){System.out.println("Error during AES Encryption");}
		return null;
	}


	@Override
	public String decrypt(String message, int key) {
		return decrypt(message);
	}

	@Override
	public String decrypt(String message) {
		try{
			byte[] bytes = new BASE64Decoder().decodeBuffer(message);
	        byte[] decrypted = dcipher.doFinal(bytes);
	        return new String(decrypted, "UTF8");
		}
		catch(Exception e){
			System.out.println("AES Decryption Errored");
			return null;
		}
	}
}
