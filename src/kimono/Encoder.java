package kimono;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.HashMap;
import java.util.Scanner;

public class Encoder {
	
	private int key = 0;
	private File file = null;
	private Scanner scanner = null;
	private String content = null, decoded = null;
	
	public Encoder(String plainfilename, String password)
	{
		this(plainfilename);
		key = generateKey(password);
	}
	
	public Encoder(String plainfilename)
	{
		
		try {
			file = new File(plainfilename);
			scanner = new Scanner(file);
		} catch (Exception e) {	return;	}
		
		content = scanner.useDelimiter("\\Z").next();
		decoded = decode(content);
		
	}
	
	public int generateKey(String password)
	{
		return 0;
		
	}
	
	public String decode(String encoded){
		return null;	
	}
	
	public void write(HashMap plainhashmap){
		
	}
	
	public void write(String plaintext){
		
	}
	
	public HashMap read(){
		return null;
	}
	
}
