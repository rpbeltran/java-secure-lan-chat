package testing;

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

import kimono.room.Room;
import kimono.room.User;



public class TesterGUI {
	
	JFrame frame;
	
	Box postBox;
	JButton postSubmitButton;
	JButton postBackButton;
	JTextField username = new JTextField();
	JTextField message = new JTextField();
	
	Box menuBox;
	JButton menuButton1;
	JButton menuButton2;
	JButton menuClose;
	
	Box viewBox;
	JTextArea messageView;
	JButton viewBackBox;
	
	Room r;
	public TesterGUI() {
		User admin = new User("username","password");
		r = new Room("Closed Kimono", admin, false);
		
		frame = new JFrame("Closed Kimono");
		
		menuBox = makeMenuBox();
		postBox = makePostBox();
		viewBox = makeViewBox();
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
				
				if(source == menuClose){
					frame.dispose();
					System.exit(0);
				}
				else if(source == menuButton1){
					frame.remove(menuBox);
					frame.add(viewBox);
					frame.setResizable(true);
					frame.pack();
				}
				else if(source == menuButton2){
					frame.remove(menuBox);
					frame.add(postBox);
					frame.setResizable(true);
					frame.pack();
				}
				else if(source == postBackButton){
					frame.remove(postBox);
					frame.add(menuBox);
					frame.setResizable(true);
					frame.pack();
				}
				else if(source == viewBackBox){
	
					frame.remove(viewBox);
					frame.add(menuBox);
					frame.setResizable(true);
					frame.pack();
				}
				
				else if(source == postSubmitButton){
					r.recordMessage(new User(username.getText(), ""),message.getText());
					updateViewBox();
					message.setText("");
					frame.remove(postBox);
					frame.add(menuBox);
					frame.setResizable(true);
					frame.pack();
				}
				
			}
			
		};
		menuButton1.addActionListener(al);
		menuButton2.addActionListener(al);
		menuClose.addActionListener(al);
		postBackButton.addActionListener(al);
		postSubmitButton.addActionListener(al);
		viewBackBox.addActionListener(al);
	}
	public void updateViewBox(){
		String t = "";
		for (String l:r.roomFile.readContent()){
			if(!l.startsWith("***"))
				t+=("\n"+r.encryptionType.decrypt(l, r.getRoomKey()));
		}
		System.out.println(t);
		messageView.setText(t);
	}
	public Box makeViewBox(){
		Box vb = Box.createVerticalBox();

		messageView = new JTextArea(5, 20);
		messageView.setEditable(false);
		messageView.setText("No messages");
		viewBackBox = new JButton("Back");
		viewBackBox.setPreferredSize(new Dimension(320,30));
		viewBackBox.setMaximumSize(new Dimension(320,30));
		
		vb.add(messageView);
		vb.add(viewBackBox);
		return vb;
	}
	public Box makeMenuBox(){
		Box mb = Box.createVerticalBox();
		menuButton1 = new JButton("View Messages");
		menuButton2 = new JButton("Post Message");
		menuClose = new JButton("Close");
		
		menuButton1.setPreferredSize(new Dimension(320,90));
		menuButton2.setPreferredSize(new Dimension(320,90));
		menuClose.setPreferredSize(new Dimension(320,30));
		
		menuButton1.setMaximumSize(new Dimension(320,90));
		menuButton2.setMaximumSize(new Dimension(320,90));
		menuClose.setMaximumSize(new Dimension(320,30));
		
		mb.add(menuButton1);
		mb.add(menuButton2);
		mb.add(menuClose);
		return mb;
	}
	
	public Box makePostBox(){
		Box loginBox = Box.createVerticalBox();
		Box loginLabelBox = Box.createHorizontalBox();
		JLabel loginLabel = new JLabel("Post a message");
		loginLabel.setFont(loginLabel.getFont().deriveFont(20F));
		loginLabelBox.add(loginLabel);
		
		Box userBox = Box.createHorizontalBox();
		
		JLabel userLabel = new JLabel("Your Name:");
		userBox.add(userLabel);
		userBox.add(Box.createHorizontalGlue());
		username = new JTextField();
		Box passBox = Box.createHorizontalBox();
		JLabel passLabel = new JLabel("Message:");
		passBox.add(passLabel);
		passBox.add(Box.createHorizontalGlue());
		username.setPreferredSize(new Dimension(80,20));
		message.setMinimumSize(new Dimension(500,40));
		message.setPreferredSize(new Dimension(500,40));
		
		Box loginSubmitBox = Box.createHorizontalBox();
		postSubmitButton = new JButton("Submit");
		postBackButton = new JButton("Back");
		loginSubmitBox.add(postSubmitButton);
		loginSubmitBox.add(postBackButton);
		loginSubmitBox.add(Box.createHorizontalGlue());
		loginBox.add(loginLabelBox);
		loginBox.add(userBox);
		loginBox.add(username);
		loginBox.add(passBox);
		loginBox.add(message);
		loginBox.add(loginSubmitBox);
		return loginBox;
	}
	
	public static void main(String[] args){
		new TesterGUI();
	}
	
}
