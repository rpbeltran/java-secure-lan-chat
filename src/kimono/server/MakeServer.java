package kimono.server;

import java.awt.Dimension;

import javax.swing.Box;
import javax.swing.JButton;
import javax.swing.JFrame;



public class MakeServer {

	public MakeServer(String user, String password) {
		
		final JFrame frame = new JFrame("Start a closed kimono server");
		
		frame.add(buildMainBox());
		
		frame.setResizable(false);
		
		frame.pack();
		
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		frame.setVisible(true);
		
		
		
	}
	
	public Box buildMainBox(){
		final Box initBox = Box.createVerticalBox();
		final JButton save = new JButton("Modify Settings");
		final JButton load = new JButton("Manage Users");
		final JButton chat = new JButton("Export Messages");
		final JButton logOut = new JButton("End Server");
		
		save.setPreferredSize(new Dimension(320,90));
		load.setPreferredSize(new Dimension(320,90));
		chat.setPreferredSize(new Dimension(320,90));
		logOut.setPreferredSize(new Dimension(320,30));
		
		save.setMaximumSize(new Dimension(320,90));
		load.setMaximumSize(new Dimension(320,90));
		chat.setMaximumSize(new Dimension(320,90));
		logOut.setMaximumSize(new Dimension(320,30));
		
		initBox.add(save);
		initBox.add(load);
		initBox.add(chat);
		initBox.add(logOut);
		return initBox;
	}
	
	public static void main(String[] args){
		new MakeServer("hello","world");
	}
	
}
