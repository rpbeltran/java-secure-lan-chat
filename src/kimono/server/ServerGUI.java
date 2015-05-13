package kimono.server;

import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.Box;
import javax.swing.JButton;
import javax.swing.JFrame;



public class ServerGUI {
	
	JFrame frame;
	
	Box menuBox;
	JButton menuLogout;
	
	KimonoServer server;
	
	public ServerGUI(KimonoServer server) {
		
		this.server = server;
		
		frame = new JFrame("Start a closed kimono server");
		
		frame.add(makeButton());
		
		setupActionListeners();
		
		frame.pack();
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setResizable(false);
		frame.setVisible(true);	
		
	}
	
	public void setupActionListeners(){
		ActionListener al = new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent ae) {
				
				JButton source = (JButton) ae.getSource();
				
				if(source == menuLogout){
					frame.dispose();
					server.close();
					System.exit(0);
				}
				
			}
			
		};
		menuLogout.addActionListener(al);
	}
	
	public JButton makeButton(){

		menuLogout = new JButton("End Server");
		menuLogout.setFocusable(false);
		menuLogout.setMinimumSize(new Dimension(120,120));
		menuLogout.setPreferredSize(new Dimension(120,120));
		menuLogout.setMaximumSize(new Dimension(120,120));
		
		return menuLogout;
	}
	
}
