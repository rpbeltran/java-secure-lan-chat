package kimono;

import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.Box;
import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPasswordField;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.ListModel;

import kimono.server.MakeServer;

public class FrontEnd {
	
	public static final int STARTWIDTH = 600;
	public static final int STARTHEIGHT = 400;
	public static final int SIDEWIDTH = 200;

	public FrontEnd() {
		
		final JFrame frame = new JFrame("Closed Kimono ver. "+Kimono.VERSION);
		
		frame.setResizable(false);
		
		// First, set up a box for the functionality of the menu.
		
		final Box initBox = Box.createVerticalBox();
		final JButton save = new JButton("Save a message");
		final JButton load = new JButton("Load a message");
		final JButton chat = new JButton("Enter Chat");
		final JButton logOut = new JButton("Log out");
		
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
		final JButton loginButton = new JButton("Log in");
		final JButton serverButton = new JButton("Start Kimono Chat server");
		loginSubmitBox.add(loginButton);
		loginSubmitBox.add(serverButton);
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
		
		final JButton saveButton = new JButton("Save");
		final JButton saveBackButton = new JButton("Back");
		
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
		final JButton loadBackButton = new JButton("Back");
		
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
		
		// Chat init screen
		
		
		
		final Box chatBox = Box.createVerticalBox();
		Box chatSideBox = Box.createVerticalBox();
		
		JComboBox<String> chooseChatBox = new JComboBox<String>();
		chooseChatBox.setEditable(true);
		final JButton joinChatButton = new JButton("Join / Create Chat Room");
		chooseChatBox.setMaximumSize(new Dimension(2147483647,48));
		chooseChatBox.setAlignmentX(Component.LEFT_ALIGNMENT);
		chooseChatBox.setAlignmentY(Component.TOP_ALIGNMENT);
		joinChatButton.setMinimumSize(new Dimension(SIDEWIDTH, 48));
		joinChatButton.setAlignmentX(Component.LEFT_ALIGNMENT);
		joinChatButton.setAlignmentY(Component.TOP_ALIGNMENT);
		
		ListModel<String> usernames = new DefaultListModel<String>();
		
		JList<String> userList = new JList<String>(usernames);
		userList.setAlignmentX(Component.LEFT_ALIGNMENT);
		userList.setAlignmentY(Component.TOP_ALIGNMENT);
		userList.setMinimumSize(new Dimension(SIDEWIDTH, 0));
		
		chatSideBox.add(chooseChatBox);
		chatSideBox.add(joinChatButton);
		chatSideBox.add(userList);
		chatSideBox.setMinimumSize(new Dimension(SIDEWIDTH, 48));
		chatSideBox.setMaximumSize(new Dimension(SIDEWIDTH, 2147483647));
		chatSideBox.setAlignmentY(Component.TOP_ALIGNMENT);
		
		Box chatMessPane = Box.createVerticalBox();
		JTextArea chatMessArea = new JTextArea();
		chatMessArea.setEditable(false);
		chatMessArea.setAlignmentX(Component.LEFT_ALIGNMENT);
		chatMessPane.add(chatMessArea);
		chatMessPane.setAlignmentX(Component.LEFT_ALIGNMENT);
		
		Box chatTop = Box.createHorizontalBox();
		chatTop.add(chatMessPane);
		chatTop.add(chatSideBox);
		chatTop.setAlignmentX(Component.LEFT_ALIGNMENT);
		
		final JButton chatBackButton = new JButton("Back");
		JTextField chatField = new JTextField();
		chatField.setMaximumSize(new Dimension(2147483647,48));
		chatField.setMinimumSize(new Dimension(10,48));
		final JButton chatSubmit = new JButton(">");
		chatSubmit.setMinimumSize(new Dimension(48,48));
		Box chatInitLoadBox = Box.createHorizontalBox();
		chatInitLoadBox.add(chatField);
		chatInitLoadBox.add(chatSubmit);
		chatInitLoadBox.add(chatBackButton);
		chatInitLoadBox.setAlignmentX(Component.LEFT_ALIGNMENT);
		
		
		chatBox.add(chatTop);
		chatBox.add(chatInitLoadBox);
		chatBox.setAlignmentX(Component.LEFT_ALIGNMENT);
		
		chatBox.setMinimumSize(new Dimension(300,200));
		frame.setMinimumSize(new Dimension(300,200));
		chatBox.setPreferredSize(new Dimension(STARTWIDTH,STARTHEIGHT));
		
		// Set up action listener for the buttons
		
		ActionListener al = new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent ae) {
				
				JButton source = (JButton) ae.getSource();
				
				if (source == loginButton) {
					
					//tell BackEnd the username and password, etc.
					
					//Then we make sure to clear the username / password fields.
					
					userField.setText("");
					passField.setText("");
					
					frame.remove(loginBox);
					//frame.add(initBox);
					frame.add(chatBox);
					frame.setResizable(true);
					frame.pack();
				}
				else if (source == logOut) {
					
					//tell BackEnd to log the user out.
					frame.remove(initBox);
					frame.add(loginBox);
					frame.pack();
				}
				else if (source == saveButton) {
					
					//execute save code here
				}
				else if (source == chat) {
					frame.remove(initBox);
					frame.add(chatBox);
					frame.setResizable(true);
					frame.pack();
				}	
				else if (source.getText().equals("Back")) {
					frame.remove(saveBox);
					frame.remove(loadBox);
					frame.remove(chatBox);
					//frame.add(initBox);
					frame.add(loginBox);
					frame.setResizable(false);
					frame.pack();
				}
				else if (source == save) {
					frame.remove(initBox);
					frame.add(saveBox);
					frame.pack();
				}
				else if (source == load) {
					frame.remove(initBox);
					frame.add(loadBox);
					frame.pack();
				} 
				else if (source == serverButton) {
					frame.dispose();
					
					String username = userField.getText();
					String password = passField.getText();
					
					userField.setText("");
					passField.setText("");
					new MakeServer(username, password);
				}
			}
		};
		
		saveButton.addActionListener(al);
		saveBackButton.addActionListener(al);
		loadBackButton.addActionListener(al);
		chatBackButton.addActionListener(al);
		save.addActionListener(al);
		load.addActionListener(al);
		chat.addActionListener(al);
		loginButton.addActionListener(al);
		serverButton.addActionListener(al);
		logOut.addActionListener(al);
		
		frame.add(loginBox);
		frame.pack();
		
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		frame.setVisible(true);
		
	}
	
}
