package kimono.encryption;

public interface CryptoAlgorithm {
	public String encrypt(String message);
	public String decrypt(String message);
}
