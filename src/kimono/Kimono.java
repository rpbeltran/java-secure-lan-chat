package kimono;

import javax.swing.SwingUtilities;

public class Kimono {
	
	public static final String VERSION = Double.toString(Math.PI);

	public static void main(String args[]) {
		SwingUtilities.invokeLater(new Runnable() {
			@Override
			public void run() {
				new FrontEnd();
			}
		});
	}
	
}
