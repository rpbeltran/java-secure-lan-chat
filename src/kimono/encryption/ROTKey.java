package kimono.encryption;

public class ROTKey implements CryptoAlgorithm {

	private int defaultKey = 13;
	
	@Override
	public String encrypt(String message) {
		return encrypt(message, defaultKey);
	}
	
	@Override
	public String encrypt(String message, int key) {
		String toReturn = "";
		for(char c:message.toCharArray())
			if(alphabetMixed.contains(""+c))
				toReturn += alphabetMixed.charAt((alphabetMixed.indexOf(c)+(2*key))%alphabetMixed.length());
			else
				toReturn += c;
		return toReturn;
	}

	@Override
	public String decrypt(String message) {
		return decrypt(message, defaultKey);
	}

	@Override
	public String decrypt(String message, int key) {
		int unKey = 26 - key%26;
		return encrypt(message, unKey);
	}	

}