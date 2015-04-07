package kimono.server;

import javax.swing.JFrame;



public class MakeServer {

	public MakeServer(String user, String password) {
		
		final JFrame frame = new JFrame("Start a closed kimono server");
		
		frame.setResizable(false);
		
		frame.pack();
		
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		frame.setVisible(true);
		
	}
	
	public static void main(String[] args){
		new MakeServer("hello","world");
	}
	
}
