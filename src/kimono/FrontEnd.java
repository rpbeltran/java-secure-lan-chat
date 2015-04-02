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
		
		Box vBox = Box.createVerticalBox();
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
		
		ActionListener al = new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent ae) {
				
				JButton source = (JButton) ae.getSource();
				
				switch(source.getText()) {
				case "Save":
					
					//execute save code here
					break;
				case "Back":
					//go to previous window / screen
				}
			}
		};
		
		saveButton.addActionListener(al);
		backButton.addActionListener(al);
		
		vBox.add(userBox);
		vBox.add(userField);
		vBox.add(passBox);
		vBox.add(passField);
		vBox.add(messBox);
		vBox.add(messField);
		
		Box hBox = Box.createHorizontalBox();
		hBox.add(saveButton);
		hBox.add(backButton);
		hBox.add(Box.createHorizontalGlue());
		vBox.add(hBox);
		
		frame.add(vBox);
		frame.pack();
		
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		frame.setVisible(true);
		
	}
	
}
