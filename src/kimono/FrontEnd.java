package kimono;

import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.Box;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPasswordField;
import javax.swing.JTextArea;
import javax.swing.JTextField;

public class FrontEnd {

	public FrontEnd() {
		
		JFrame frame = new JFrame("Closed Kimono ver. "+Kimono.VERSION);
		
		// First, set up a box for the functionality of the menu.
		
		Box initBox = Box.createVerticalBox();
		JButton save = new JButton("Save a message");
		JButton load = new JButton("Load a message");
		
		save.setPreferredSize(new Dimension(320,90));
		load.setPreferredSize(new Dimension(320,90));
		
		save.setMaximumSize(new Dimension(320,90));
		load.setMaximumSize(new Dimension(320,90));
		
		initBox.add(save);
		initBox.add(load);
		
		// Then, one for the loading screen.
		
		Box loadBox = Box.createVerticalBox();
		
		// And finally, one for the save screen.
		
		Box saveBox = Box.createVerticalBox();
		Box userBox = Box.createHorizontalBox();
		JLabel userLabel = new JLabel("Username:");
		userBox.add(userLabel);
		userBox.add(Box.createHorizontalGlue());
		JTextField userField = new JTextField();
		Box passBox = Box.createHorizontalBox();
		JLabel passLabel = new JLabel("Password:");
		passBox.add(passLabel);
		passBox.add(Box.createHorizontalGlue());
		JPasswordField passField = new JPasswordField();
		
		userField.setPreferredSize(new Dimension(80,20));
		passField.setPreferredSize(new Dimension(80,20));
		
		//userField.setMaximumSize(new Dimension(80,20));
		//passField.setMaximumSize(new Dimension(80,20));
		
		Box messBox = Box.createHorizontalBox();
		JLabel messLabel = new JLabel("Message:");
		messBox.add(messLabel);
		messBox.add(Box.createHorizontalGlue());
		JTextArea messField = new JTextArea();
		
		messField.setPreferredSize(new Dimension(320,240));
		
		JButton saveButton = new JButton("Save");
		JButton backButton = new JButton("Back");
		
		saveBox.add(userBox);
		saveBox.add(userField);
		saveBox.add(passBox);
		saveBox.add(passField);
		saveBox.add(messBox);
		saveBox.add(messField);
		
		Box hBox = Box.createHorizontalBox();
		hBox.add(saveButton);
		hBox.add(backButton);
		hBox.add(Box.createHorizontalGlue());
		saveBox.add(hBox);
		
		// Set up action listener for the buttons
		
		ActionListener al = new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent ae) {
				
				JButton source = (JButton) ae.getSource();
				
				switch(source.getText()) {
				case "Save":
					
					//execute save code here
					break;
				case "Back":
					frame.remove(saveBox);
					frame.remove(loadBox);
					frame.add(initBox);
					frame.pack();
					break;
				case "Save a message":
					frame.remove(initBox);
					frame.add(saveBox);
					frame.pack();
					break;
				case "Load a message":
				}
			}
		};
		
		saveButton.addActionListener(al);
		backButton.addActionListener(al);
		save.addActionListener(al);
		load.addActionListener(al);
		
		frame.add(initBox);
		frame.pack();
		
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		frame.setVisible(true);
		
	}
	
}
