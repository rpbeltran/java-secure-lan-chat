package kimono.server;

import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.Box;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;



public class MakeServer {
	
	JFrame frame;
	
	Box menuBox;
	JButton menuButton1;
	JButton menuButton2;
	JButton menuButton3;
	JButton menuLogout;
	
	KimonoServer server;
	
	public MakeServer(String user, String password) {
		
		frame = new JFrame("Start a closed kimono server");
		
		menuBox=makeMenuBox();
		
		setupActionListeners();
		
		frame.add(menuBox);
		
		frame.setResizable(false);
		frame.pack();
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setVisible(true);	
		
	}
	
	public void setupActionListeners(){
		ActionListener al = new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent ae) {
				
				JButton source = (JButton) ae.getSource();
				
				if(source == menuLogout){
					frame.dispose();
					System.exit(0);
				}
				
			}
			
		};
		menuLogout.addActionListener(al);
	}
	
	public Box makeMenuBox(){
		Box mb = Box.createVerticalBox();
		menuButton1 = new JButton("Modify Settings");
		menuButton2 = new JButton("Manage Users");
		menuButton3 = new JButton("Export Messages");
		menuLogout = new JButton("End Server");
		
		menuButton1.setPreferredSize(new Dimension(320,90));
		menuButton2.setPreferredSize(new Dimension(320,90));
		menuButton3.setPreferredSize(new Dimension(320,90));
		menuLogout.setPreferredSize(new Dimension(320,30));
		
		menuButton1.setMaximumSize(new Dimension(320,90));
		menuButton2.setMaximumSize(new Dimension(320,90));
		menuButton3.setMaximumSize(new Dimension(320,90));
		menuLogout.setMaximumSize(new Dimension(320,30));
		
		mb.add(menuButton1);
		mb.add(menuButton2);
		mb.add(menuButton3);
		mb.add(menuLogout);
		return mb;
	}
	
	public void displayError(String text) {
		JOptionPane.showMessageDialog(frame, text, "Error!", JOptionPane.ERROR_MESSAGE);
	}
	
	public void makeServer(int port) {
		server = new KimonoServer(port, this);
	}
	
}
