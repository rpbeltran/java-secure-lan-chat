package kimono.room;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.PrintWriter;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

public class RoomFile {
	
	public String filepath;
	
	public RoomFile (String filename){
		filepath = filename;	
	}
	
	public void writeOver(String text){
		try{
			FileWriter f2 = new FileWriter(filepath, false);
            f2.write(text);
            f2.close();
		}catch(Exception e){
			e.printStackTrace();
		}
	}
	
	public void writeAfter(String text){
		try{
			PrintWriter out = new PrintWriter(new BufferedWriter(new FileWriter(filepath, true)));
		    out.println(text);
		    out.close();
		}catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public void clearOut(){
		writeOver("");
	}
	
	public List<String> readContent(){
		try {
			
			 return Files.readAllLines(Paths.get(filepath),Charset.defaultCharset());
			 
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

}
