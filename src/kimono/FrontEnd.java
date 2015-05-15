package kimono;

import java.util.List;

public abstract class FrontEnd {

	public abstract void returnToLogin(String message);
	public abstract boolean login();
	public abstract void addMessage(String message);
	public abstract void updateRooms(List<String> rooms);
	public abstract void updateUsers(List<String> users);
	
	public static String formatMessage(List<String> values) {
		return values.get(2)+": <"+values.get(0)+"> "+values.get(1);
	}
	
}
