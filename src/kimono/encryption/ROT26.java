package kimono.encryption;

public class ROT26 implements CryptoAlgorithm {
	
	@Override
	public String encrypt(String message) {
		String toReturn = "";
		for(char c:message.toCharArray())
			if(alphabetMixed.contains(""+c))
				toReturn += alphabetMixed.charAt((alphabetMixed.indexOf(c)+52)%alphabetMixed.length());
			else
				toReturn += c;
		return toReturn;
	}
	
	@Override
	public String encrypt(String message, int key) {
		//key not used
		return encrypt(message);
	}

	@Override
	public String decrypt(String message) {
		//ROT13 directly reversible
		return encrypt(message);
	}

	@Override
	public String decrypt(String message, int key) {
		//ROT13 directly reversible
		//Key not used
		return encrypt(message);
	}	

}