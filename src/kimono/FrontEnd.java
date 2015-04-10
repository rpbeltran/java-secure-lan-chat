package kimono;

import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.net.UnknownHostException;
import java.text.NumberFormat;
import java.util.ArrayList;

import javax.swing.Box;
import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFormattedTextField;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPasswordField;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.ListModel;
import javax.swing.text.MaskFormatter;

import kimono.server.MakeServer;

public class FrontEnd {
	
	public static final int STARTWIDTH = 600;
	public static final int STARTHEIGHT = 400;
	public static final int SIDEWIDTH = 200;
	
	final JFrame frame;
	
	final Box loginBox;
	JButton loginButton;
	JButton serverButton;
	JTextField userField = new JTextField();
	JTextField ipField;
	JFormattedTextField portField;
	JPasswordField passField = new JPasswordField();
	NumberFormat ipFormat;
	NumberFormat portFormat;
	
	Box chatBox;
	JButton chatBackButton;
	JTextField chatField;
	JButton chatSubmit;
	
	Box loadBox;
	JButton loadBackButton;
	
	Box saveBox;
	JButton saveButton;
	JButton saveBackButton;
	
	Box initBox;
	JButton save = new JButton("Save a message");
	JButton load = new JButton("Load a message");
	JButton chat = new JButton("Enter Chat");
	JButton logOut = new JButton("Log out");
	
	BackEnd backend;
	
	public FrontEnd() {
		
		//Make a blank JFrame
		frame = new JFrame("Closed Kimono ver. "+Kimono.VERSION);
		frame.setResizable(false);
		
		//Menu screen
		initBox = makeMenuBox();
		
		//Login screen.
		loginBox = makeLoginBox(); 
				
		//Save screen.
		saveBox = makeSaveBox();
		
		//Loading screen.
		loadBox = getLoadBox();
		
		// Chat init screen
		chatBox = makeChatBox();
			
		// Set up action listener for the buttons
		setupActionListeners();
		
		
		//Initialize GUI
		frame.add(loginBox);
		frame.pack();
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setVisible(true);
		
		
		
	}
	
	public void sendMessage() {
		backend.sendMessage(chatField.getText());
		chatField.setText("");
	}
	
	public void login() {
		String username = userField.getText();
		String password = new String(passField.getPassword());
		String hostname = ipField.getText();
		int port = Integer.parseInt(portField.getText());
		
		try {
			backend = new BackEnd(username, password, hostname, port);
		} catch (UnknownHostException e) {
			JOptionPane.showMessageDialog(frame, "Could not reach server "+hostname+":"+Integer.toString(port), "Connection Error", JOptionPane.ERROR_MESSAGE);
			return;
		} catch (IOException e) {
			JOptionPane.showMessageDialog(frame, "IO Error", "Connection Error", JOptionPane.ERROR_MESSAGE);
			return;
		} 
		
		userField.setText("");
		passField.setText("");
		
		
		
		frame.remove(loginBox);
		//frame.add(initBox);
		frame.add(chatBox);
		frame.setResizable(true);
		frame.pack();
	}
	
	public void setupActionListeners(){
		ActionListener al = new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent ae) {
				
				JButton source = (JButton) ae.getSource();
				
				if (source == loginButton){
					login();
				}
				if (source == logOut) {
					
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
					String password = new String(passField.getPassword());
					
					userField.setText("");
					passField.setText("");
					MakeServer myServer = new MakeServer(username, password);
					myServer.makeServer(32800);
				}
				else if (source == chatSubmit) {
					sendMessage();
				}
			}
		};
		loginButton.addActionListener(al);
		saveButton.addActionListener(al);
		saveBackButton.addActionListener(al);
		loadBackButton.addActionListener(al);
		chatBackButton.addActionListener(al);
		save.addActionListener(al);
		load.addActionListener(al);
		chat.addActionListener(al);
		serverButton.addActionListener(al);
		logOut.addActionListener(al);
		chatSubmit.addActionListener(al);
		
		ActionListener loginal = new ActionListener(){
			public void actionPerformed(ActionEvent arg0) {
				login();
			}
			
		};
		userField.addActionListener(loginal);
		passField.addActionListener(loginal);
		ipField.addActionListener(loginal);
		portField.addActionListener(loginal);
		
		
		ActionListener alt = new ActionListener(){
			public void actionPerformed(ActionEvent arg0) {
				JTextField source = (JTextField) arg0.getSource();
				if (source == chatField) {
					sendMessage();
				}
			}
			
		};
		chatField.addActionListener(alt);
	}
	
	public Box makeMenuBox(){
		Box ib = Box.createVerticalBox();
		save = new JButton("Save a message");
		load = new JButton("Load a message");
		chat = new JButton("Enter Chat");
		logOut = new JButton("Log out");
		
		save.setPreferredSize(new Dimension(320,90));
		load.setPreferredSize(new Dimension(320,90));
		chat.setPreferredSize(new Dimension(320,90));
		logOut.setPreferredSize(new Dimension(320,30));
		
		save.setMaximumSize(new Dimension(320,90));
		load.setMaximumSize(new Dimension(320,90));
		chat.setMaximumSize(new Dimension(320,90));
		logOut.setMaximumSize(new Dimension(320,30));
		
		ib.add(save);
		ib.add(load);
		ib.add(chat);
		ib.add(logOut);
		
		return ib;
	}
	
	public Box makeSaveBox(){
		Box sb = Box.createVerticalBox();
		
		//userField.setMaximumSize(new Dimension(80,20));
		//passField.setMaximumSize(new Dimension(80,20));
		
		Box messBox = Box.createHorizontalBox();
		JLabel messLabel = new JLabel("Message:");
		messBox.add(messLabel);
		messBox.add(Box.createHorizontalGlue());
		JTextArea messField = new JTextArea();
		
		messField.setPreferredSize(new Dimension(320,240));
		
		saveButton = new JButton("Save");
		saveBackButton = new JButton("Back");
		
		sb.add(messBox);
		sb.add(messField);
		
		Box saveBackBox = Box.createHorizontalBox();
		saveBackBox.add(saveButton);
		saveBackBox.add(saveBackButton);
		saveBackBox.add(Box.createHorizontalGlue());
		sb.add(saveBackBox);
		
		return sb;
	}
	
	public Box makeChatBox()
	{
		Box cb = Box.createVerticalBox();
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
		chatMessPane.setAlignmentY(Component.TOP_ALIGNMENT);
		Box chatTop = Box.createHorizontalBox();
		chatTop.add(chatMessPane);
		chatTop.add(chatSideBox);
		chatTop.setAlignmentX(Component.LEFT_ALIGNMENT);
		
		chatBackButton = new JButton("Back");
		chatField = new JTextField();
		chatField.setMaximumSize(new Dimension(2147483647,48));
		chatField.setMinimumSize(new Dimension(10,48));
		chatSubmit = new JButton(">");
		chatSubmit.setMinimumSize(new Dimension(48,48));
		Box chatInitLoadBox = Box.createHorizontalBox();
		chatInitLoadBox.add(chatField);
		chatInitLoadBox.add(chatSubmit);
		chatInitLoadBox.add(chatBackButton);
		chatInitLoadBox.setAlignmentX(Component.LEFT_ALIGNMENT);
		cb.add(chatTop);
		cb.add(chatInitLoadBox);
		cb.setAlignmentX(Component.LEFT_ALIGNMENT);
		cb.setMinimumSize(new Dimension(300,200));
		frame.setMinimumSize(new Dimension(300,200));
		cb.setPreferredSize(new Dimension(STARTWIDTH,STARTHEIGHT));		
		return cb;
	}
	
	public Box makeLoginBox()

	{
		Box loginBox = Box.createVerticalBox();
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
		userField = new JTextField();
		
		Box passBox = Box.createHorizontalBox();
		JLabel passLabel = new JLabel("Password:");
		passBox.add(passLabel);
		passBox.add(Box.createHorizontalGlue());
		passField = new JPasswordField();
		userField.setPreferredSize(new Dimension(80,20));
		passField.setPreferredSize(new Dimension(80,20));
		
		Box ipBox = Box.createHorizontalBox();
		JLabel ipLabel = new JLabel("Server Hostname:");
		ipBox.add(ipLabel);
		ipBox.add(Box.createHorizontalGlue());
		
		Box hostBox = Box.createHorizontalBox();
		ipField = new JTextField();
		ipField.setText("127.0.0.1");
		
		JLabel portLabel = new JLabel("Port:");
		portField = new JFormattedTextField(createFormatter("#####"));
		portField.setText("32800");
		
		hostBox.add(ipField);
		hostBox.add(Box.createHorizontalStrut(50));
		hostBox.add(portLabel);
		hostBox.add(Box.createHorizontalStrut(5));
		hostBox.add(portField);
		
		
		Box loginSubmitBox = Box.createHorizontalBox();
		loginButton = new JButton("Log in");
		serverButton = new JButton("Start Kimono Chat server");
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
		loginBox.add(ipBox);
		loginBox.add(hostBox);
		loginBox.add(loginSubmitBox);
		return loginBox;
	}
	
	protected MaskFormatter createFormatter(String s) {
	    MaskFormatter formatter = null;
	    try {
	        formatter = new MaskFormatter(s);
	    } catch (java.text.ParseException exc) {
	        System.err.println("formatter is bad: " + exc.getMessage());
	        System.exit(-1);
	    }
	    return formatter;
	}

	public Box getLoadBox()
{
		Box lb = Box.createVerticalBox();
		
		Box messSelBox = Box.createHorizontalBox();
		JLabel messSelLabel = new JLabel("Select message: ");
		JComboBox<String> messList = new JComboBox<String>();
		loadBackButton = new JButton("Back");
		
		messSelBox.add(messSelLabel);
		messSelBox.add(messList);
		lb.add(messSelBox);
		
		JTextArea messShow = new JTextArea();
		messShow.setPreferredSize(new Dimension(320,240));
		messShow.setEditable(false);
		lb.add(messShow);
		
		Box loadBackBox = Box.createHorizontalBox();
		loadBackBox.add(loadBackButton);
		loadBackBox.add(Box.createHorizontalGlue());
		lb.add(loadBackBox);
		return lb;
	}
	
}
