package kimono.encryption;

public interface CryptoAlgorithm {
	
	//Stored alphabets
	String alphabetMixed = "aAbBcCdDeEfFgGhHiIjJkKlLmMnNoOpPqQrRsStTuUvVwWxXyYzZ";
	String alphabetLower = "abcdefghijklmnopqrstuvwxyz";
	String alphabetUpper = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
	
	//Encryption Algorithm
	public String encrypt(String message);
	public String encrypt(String message, int key);
	
	//Decryption Algorithm
	public String decrypt(String message);
	public String decrypt(String message, int key);

}
