package kimono;

import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.Box;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPasswordField;
import javax.swing.JTextArea;
import javax.swing.JTextField;

public class FrontEnd {

	public FrontEnd() {
		
		final JFrame frame = new JFrame("Closed Kimono ver. "+Kimono.VERSION);
		
		frame.setResizable(false);
		
		// First, set up a box for the functionality of the menu.
		
		final Box initBox = Box.createVerticalBox();
		JButton save = new JButton("Save a message");
		JButton load = new JButton("Load a message");
		JButton logOut = new JButton("Log out");
		
		save.setPreferredSize(new Dimension(320,90));
		load.setPreferredSize(new Dimension(320,90));
		logOut.setPreferredSize(new Dimension(320,30));
		
		save.setMaximumSize(new Dimension(320,90));
		load.setMaximumSize(new Dimension(320,90));
		logOut.setMaximumSize(new Dimension(320,30));
		
		initBox.add(save);
		initBox.add(load);
		initBox.add(logOut);
		
		// One for a login screen.
		
		final Box loginBox = Box.createVerticalBox();
		
		Box loginLabelBox = Box.createHorizontalBox();
		JLabel loginLabel = new JLabel("Log in");
		loginLabel.setFont(loginLabel.getFont().deriveFont(20F));
		loginLabelBox.add(loginLabel);
		loginLabelBox.add(Box.createHorizontalGlue());
		
		Box loginLabelBox1 = Box.createHorizontalBox();
		JLabel loginDescLabel1 = new JLabel("This will save your messages under this username and password.");
		loginLabelBox1.add(loginDescLabel1);
		loginLabelBox1.add(Box.createHorizontalGlue());
		
		Box loginLabelBox2 = Box.createHorizontalBox();
		JLabel loginDescLabel2 = new JLabel("Do not forget them; there is no recovery available.");
		loginLabelBox2.add(loginDescLabel2);
		loginLabelBox2.add(Box.createHorizontalGlue());
		
		Box userBox = Box.createHorizontalBox();
		JLabel userLabel = new JLabel("Username:");
		userBox.add(userLabel);
		userBox.add(Box.createHorizontalGlue());
		final JTextField userField = new JTextField();
		
		Box passBox = Box.createHorizontalBox();
		JLabel passLabel = new JLabel("Password:");
		passBox.add(passLabel);
		passBox.add(Box.createHorizontalGlue());
		final JPasswordField passField = new JPasswordField();
		
		userField.setPreferredSize(new Dimension(80,20));
		passField.setPreferredSize(new Dimension(80,20));
		
		Box loginSubmitBox = Box.createHorizontalBox();
		JButton loginButton = new JButton("Log in");
		loginSubmitBox.add(loginButton);
		loginSubmitBox.add(Box.createHorizontalGlue());
		
		loginBox.add(loginLabelBox);
		loginBox.add(loginLabelBox1);
		loginBox.add(loginLabelBox2);
		loginBox.add(userBox);
		loginBox.add(userField);
		loginBox.add(passBox);
		loginBox.add(passField);
		loginBox.add(loginSubmitBox);
		
		// One for the save screen.
		
		final Box saveBox = Box.createVerticalBox();
		
		//userField.setMaximumSize(new Dimension(80,20));
		//passField.setMaximumSize(new Dimension(80,20));
		
		Box messBox = Box.createHorizontalBox();
		JLabel messLabel = new JLabel("Message:");
		messBox.add(messLabel);
		messBox.add(Box.createHorizontalGlue());
		JTextArea messField = new JTextArea();
		
		messField.setPreferredSize(new Dimension(320,240));
		
		JButton saveButton = new JButton("Save");
		JButton saveBackButton = new JButton("Back");
		
		saveBox.add(messBox);
		saveBox.add(messField);
		
		Box saveBackBox = Box.createHorizontalBox();
		saveBackBox.add(saveButton);
		saveBackBox.add(saveBackButton);
		saveBackBox.add(Box.createHorizontalGlue());
		saveBox.add(saveBackBox);
		
		// Then, one for the loading screen.
		
		final Box loadBox = Box.createVerticalBox();
		
		Box messSelBox = Box.createHorizontalBox();
		JLabel messSelLabel = new JLabel("Select message: ");
		JComboBox<String> messList = new JComboBox<String>();
		JButton loadBackButton = new JButton("Back");
		
		messSelBox.add(messSelLabel);
		messSelBox.add(messList);
		loadBox.add(messSelBox);
		
		JTextArea messShow = new JTextArea();
		messShow.setPreferredSize(new Dimension(320,240));
		messShow.setEditable(false);
		loadBox.add(messShow);
		
		Box loadBackBox = Box.createHorizontalBox();
		loadBackBox.add(loadBackButton);
		loadBackBox.add(Box.createHorizontalGlue());
		loadBox.add(loadBackBox);
		
		// Set up action listener for the buttons
		
		ActionListener al = new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent ae) {
				
				JButton source = (JButton) ae.getSource();
				
				switch(source.getText()) {
				case "Log in":
					
					//tell BackEnd the username and password, etc.
					
					//Then we make sure to clear the username / password fields.
					
					userField.setText("");
					passField.setText("");
					
					frame.remove(loginBox);
					frame.add(initBox);
					frame.pack();
					break;
				case "Log out":
					
					//tell BackEnd to log the user out.
					frame.remove(initBox);
					frame.add(loginBox);
					frame.pack();
					break;
				case "Save":
					
					//execute save code here
					break;
				case "Load":
					
					//execute load code here
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
					frame.remove(initBox);
					frame.add(loadBox);
					frame.pack();
					break;
				}
			}
		};
		
		saveButton.addActionListener(al);
		saveBackButton.addActionListener(al);
		loadBackButton.addActionListener(al);
		save.addActionListener(al);
		load.addActionListener(al);
		loginButton.addActionListener(al);
		logOut.addActionListener(al);
		
		frame.add(loginBox);
		frame.pack();
		
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		frame.setVisible(true);
		
	}
	
}
