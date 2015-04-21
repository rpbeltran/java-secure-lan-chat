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
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.DefaultComboBoxModel;
import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFormattedTextField;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JScrollPane;
import javax.swing.JSeparator;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.ListModel;
import javax.swing.UIManager;
import javax.swing.border.Border;
import javax.swing.text.MaskFormatter;

import com.jgoodies.looks.windows.WindowsLookAndFeel;

import kimono.server.MakeServer;

public class FrontEnd {
	
	public static final int STARTWIDTH = 600;
	public static final int STARTHEIGHT = 400;
	public static final int SIDEWIDTH = 200;
	
	private final JFrame frame;
	
	private final Box loginBox;
	private JButton loginButton;
	private JButton serverButton;
	private JTextField userField = new JTextField();
	private JTextField ipField;
	private JFormattedTextField portField;
	private JPasswordField passField = new JPasswordField();
	private NumberFormat ipFormat;
	private NumberFormat portFormat;
	
	private String defusername;
	private String defpassword;
	private String defhostname;
	private int defportnum;
	
	private Box chatBox;
	private JComboBox<String> chooseChatBox;
	private JButton joinChatButton;
	private JButton chatBackButton;
	private JTextField chatField;
	private JButton chatSubmit;
	private JTextArea chatMessArea;
	private JList<String> userList;
	
	private Box loadBox;
	private JButton loadBackButton;
	
	private Box saveBox;
	private JButton saveButton;
	private JButton saveBackButton;
	
	private Box initBox;
	private JButton save = new JButton("Save a message");
	private JButton load = new JButton("Load a message");
	private JButton chat = new JButton("Enter Chat");
	private JButton logOut = new JButton("Log out");
	
	private BackEnd backend;
	
	public FrontEnd(String u, String p, String host, int port) {
		
		   try {
			      UIManager.setLookAndFeel(new WindowsLookAndFeel());
			   } catch (Exception e) {}
			
		
		defusername = u;
		defpassword = p;
		defhostname = host;
		defportnum = port;
		
		//Make a blank JFrame
		frame = new JFrame("Closed Kimono ver. "+Kimono.VERSION);
		frame.setResizable(false);
		
		//Add padding
		JPanel contentPanel = new JPanel();
		Border padding = BorderFactory.createEmptyBorder(10,10,10,10);
		contentPanel.setBorder(padding);

		frame.setContentPane(contentPanel);
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
	
	private void sendMessage() {
		backend.sendMessage(chatField.getText());
		chatField.setText("");
	}
	
	private void joinRoom() {
		backend.joinRoom(chooseChatBox.getSelectedItem().toString());
	}
	
	public void updateMessages(List<List<String>> list) {
		int col = chatMessArea.getColumns();
		int row = chatMessArea.getLineCount();
		ArrayList<String> messages = new ArrayList<String>();
		for (List<String> values: list) {
			String s = values.get(3)+":"+values.get(1)+":"+values.get(2);
			if (s.length() >= col) {
				
			} else {
				messages.add(s);
			}
		}
		String st = messages.get(Math.max(0, messages.size()-row));
		for (int i= Math.max(0, messages.size()-row)+1; i < messages.size();i++) {
			st+="\n"+messages.get(i);
		}
		chatMessArea.setText(st);
		
	}
	
	public void updateUsers(List<String> list) {
		DefaultComboBoxModel<String> model = new DefaultComboBoxModel<String>();
		for(String s: list){
			model.addElement(s);
		}
		chooseChatBox.setModel(model);
	}
	
	public void updateRooms(List<String> list) {
		DefaultListModel<String> model = new DefaultListModel<String>();
		for(String s: list){
			model.addElement(s);
		}
		userList.setModel(model);
	}
	
	public void stop() {
		logout();
	}
	
	private void login() {
		String username = userField.getText();
		String password = new String(passField.getPassword());
		String hostname = ipField.getText();
		int port = Integer.parseInt(portField.getText());
		
		try {
			backend = new BackEnd(username, password, hostname, port);
			backend.setFrontEnd(this);
		} catch (UnknownHostException e) {
			JOptionPane.showMessageDialog(frame, "Could not reach server "+hostname+":"+Integer.toString(port), "Connection Error", JOptionPane.ERROR_MESSAGE);
			return;
		} catch (IOException e) {
			JOptionPane.showMessageDialog(frame, "IO Error: "+e.getMessage(), "Connection Error", JOptionPane.ERROR_MESSAGE);
			return;
		} 
		
		userField.setText(defusername);
		passField.setText(defpassword);
		
		frame.remove(loginBox);
		frame.add(chatBox);
		frame.setResizable(true);
		frame.pack();
	}
	
	private void logout() {
		//tell BackEnd to log the user out.
		frame.remove(initBox);
		frame.add(loginBox);
		frame.pack();
	}
	
	private void setupActionListeners(){
		ActionListener al = new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent ae) {
				
				JButton source = (JButton) ae.getSource();
				
				if (source == loginButton){
					login();
				}
				else if (source == logOut) {
					logout();
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
					
					userField.setText(defusername);
					passField.setText(defpassword);
					MakeServer myServer = new MakeServer(username, password);
					myServer.makeServer(32800);
				}
				else if (source == chatSubmit) {
					sendMessage();
				}
				else if (source == joinChatButton) {
					joinRoom();
				}
			}
		};
		loginButton.addActionListener(al);
		saveButton.addActionListener(al);
		saveBackButton.addActionListener(al);
		loadBackButton.addActionListener(al);
		chatBackButton.addActionListener(al);
		joinChatButton.addActionListener(al);
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
		
		ActionListener alr = new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				JComboBox<String> source = (JComboBox<String>) arg0.getSource();
				if (source == chooseChatBox) {
					joinRoom();
				}
			}
		};
		
		chooseChatBox.addActionListener(alr);
	}
	
	private Box makeMenuBox(){
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
	
	private Box makeSaveBox(){
		Box sb = Box.createVerticalBox();
		
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
	
	private Box makeChatBox()
	{
		Box cb = Box.createVerticalBox();
		Box chatSideBox = Box.createVerticalBox();
		chooseChatBox = new JComboBox<String>();
		chooseChatBox.setEditable(true);
		joinChatButton = new JButton("Join / Create Chat Room");
		chooseChatBox.setMaximumSize(new Dimension(SIDEWIDTH,48));
		chooseChatBox.setAlignmentX(Component.LEFT_ALIGNMENT);
		chooseChatBox.setAlignmentY(Component.TOP_ALIGNMENT);
		
		joinChatButton.setFocusable(false);
		joinChatButton.setMinimumSize(new Dimension(SIDEWIDTH, 48));
		joinChatButton.setMaximumSize(new Dimension(SIDEWIDTH, 48));
		joinChatButton.setAlignmentX(Component.LEFT_ALIGNMENT);
		joinChatButton.setAlignmentY(Component.TOP_ALIGNMENT);
		
		ListModel<String> usernames = new DefaultListModel<String>();
		userList = new JList<String>(usernames);
		userList.setAlignmentX(Component.LEFT_ALIGNMENT);
		userList.setAlignmentY(Component.TOP_ALIGNMENT);
		userList.setMaximumSize(new Dimension(SIDEWIDTH, 2147483647));
		userList.setMinimumSize(new Dimension(SIDEWIDTH, 48));
		userList.setVisibleRowCount(15);
		userList.setBackground(Color.white);
		userList.setVisible(true);
		JScrollPane listScroller = new JScrollPane(userList);
		
		chatSideBox.add(chooseChatBox);
		chatSideBox.add(joinChatButton);
		chatSideBox.add(listScroller);
		chatSideBox.setMinimumSize(new Dimension(SIDEWIDTH, 48));
		chatSideBox.setMaximumSize(new Dimension(SIDEWIDTH, 2147483647));
		chatSideBox.setAlignmentY(Component.TOP_ALIGNMENT);
		
		
		Box chatMessPane = Box.createVerticalBox();
		chatMessArea = new JTextArea();
		chatMessArea.setEditable(false);
		chatMessArea.setBackground(Color.white);
		chatMessArea.setVisible(true);
		chatMessArea.setAlignmentX(Component.LEFT_ALIGNMENT);
		chatMessArea.setText("");
		chatMessArea.setBorder(BorderFactory.createEtchedBorder());
		chatMessPane.add(chatMessArea);
		chatMessPane.setAlignmentX(Component.LEFT_ALIGNMENT);
		chatMessPane.setAlignmentY(Component.TOP_ALIGNMENT);
		
		Box chatTop = Box.createHorizontalBox();
		chatTop.add(chatMessPane);
		chatTop.add(chatSideBox);
		chatTop.setAlignmentX(Component.LEFT_ALIGNMENT);
		
		chatBackButton = new JButton("Back");
		chatBackButton.setFocusable(false);
		chatField = new JTextField();
		chatField.setMaximumSize(new Dimension(2147483647,48));
		chatField.setMinimumSize(new Dimension(10,48));
		chatSubmit = new JButton(">");
		chatSubmit.setFocusable(false);
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
	
	private Box makeLoginBox()

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
		userBox.setBorder(BorderFactory.createEmptyBorder(10,0,0,0));
		JLabel userLabel = new JLabel("Username:");
		userBox.add(userLabel);
		userBox.add(Box.createHorizontalGlue());
		userField = new JTextField();
		userField.setText(defusername);
		
		Box passBox = Box.createHorizontalBox();
		JLabel passLabel = new JLabel("Password:");
		passBox.add(passLabel);
		passBox.add(Box.createHorizontalGlue());
		passField = new JPasswordField();
		passField.setText(defpassword);
		userField.setPreferredSize(new Dimension(80,20));
		passField.setPreferredSize(new Dimension(80,20));
		
		Box ipBox = Box.createHorizontalBox();
		JLabel ipLabel = new JLabel("Server Hostname:");
		ipBox.add(ipLabel);
		ipBox.add(Box.createHorizontalGlue());
		
		Box hostBox = Box.createHorizontalBox();
		ipField = new JTextField();
		ipField.setText(defhostname);
		
		JLabel portLabel = new JLabel("Port:");
		portField = new JFormattedTextField(createFormatter("#####"));
		portField.setText(Integer.toString(defportnum));
		
		hostBox.add(ipField);
		hostBox.add(Box.createHorizontalStrut(50));
		hostBox.add(portLabel);
		hostBox.add(Box.createHorizontalStrut(5));
		hostBox.add(portField);
		
		
		Box loginSubmitBox = Box.createHorizontalBox();
		loginSubmitBox.setBorder(BorderFactory.createEmptyBorder(10,0,0,0));
		loginButton = new JButton("Log in");
		serverButton = new JButton("Start Kimono Chat server");
		
		loginButton.setFocusable(false);
		serverButton.setFocusable(false);
		
		loginSubmitBox.add(loginButton);
		loginSubmitBox.add(serverButton);
		loginSubmitBox.add(Box.createHorizontalGlue());
		
		
		loginBox.add(loginLabelBox);
		loginBox.add(loginLabelBox1);
		loginBox.add(loginLabelBox2);
		loginBox.add(userBox);
		loginBox.add(userField);
		loginBox.add(new JSeparator(JSeparator.HORIZONTAL));
		loginBox.add(passBox);
		loginBox.add(passField);
		loginBox.add(new JSeparator(JSeparator.HORIZONTAL));
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

	private Box getLoadBox()
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
