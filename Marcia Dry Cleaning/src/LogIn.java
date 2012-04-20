// Scott Hoelsema, Arthur Anderson, and Christopher Lenk
// LogIn gets information to connect to the database.

import java.awt.*;
import java.awt.event.*;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;

import javax.swing.*;

public class LogIn extends JFrame
{
	private String username;
	private String password;
	private TextField usernameField;
	private JPasswordField passwordField;
	private Statement stmt;
	private Connection conn = null;
	
	// LOGIN Constructor
	
	public LogIn()
	{
		setTitle("Log In");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		GridLayout logInLayout = new GridLayout(5,1);
		setLayout(logInLayout);
		logInLayout.setVgap(3);
		add(new JLabel("Enter Database Username:"));
		usernameField = new TextField(15);
		add(usernameField);
		add(new JLabel("Enter Database Password:"));
		passwordField = new JPasswordField(15);
		add(passwordField);
		passwordField.addKeyListener(new enterButtonListener());
		JButton enterLogInData = new JButton("Submit Log In Data");
		add(enterLogInData);
		enterLogInData.addActionListener(new enterLogInDataListener());
		
		pack();
		centerOnScreen();
		setVisible(true);
	}
	
	// ENTER LOG IN DATA LISTENER - Activated upon push of "Enter" key when in password field
	
	private class enterButtonListener implements KeyListener
	{
		public void keyPressed(KeyEvent e)
		{
			if( e.getKeyCode() == KeyEvent.VK_ENTER )
			{
				StringBuilder sb = new StringBuilder();
				username = usernameField.getText();
				char pw[] = passwordField.getPassword();
				for(int i = 0; i<pw.length; i++)
				{
					sb.append(pw[i]);
				}
				password = sb.toString();
				try
				{
					String url = "jdbc:mysql://localhost/Group2";
					Class.forName("com.mysql.jdbc.Driver").newInstance();
					conn = DriverManager.getConnection(url, username, password);
					System.out.println("Database connection established");
					stmt = conn.createStatement();
					new Central(conn);
					setVisible(false);
				}
				catch (Exception error) 
				{
					JOptionPane.showMessageDialog(null, "Failed Connection Attempt.");
					error.printStackTrace();
				}
			}
		}
		public void keyReleased(KeyEvent e) {}
		public void keyTyped(KeyEvent e) {}
	}
	
	// ENTER LOGIN DATA LISTENER - Activated upon push of "Submit Log In Data" button
	
	private class enterLogInDataListener implements ActionListener
	{
		public void actionPerformed(ActionEvent e)
		{
			StringBuilder sb = new StringBuilder();
			username = usernameField.getText();
			char pw[] = passwordField.getPassword();
			for(int i = 0; i<pw.length; i++)
			{
				sb.append(pw[i]);
			}
			password = sb.toString();
			try
			{
				String url = "jdbc:mysql://localhost/Group2";
				Class.forName("com.mysql.jdbc.Driver").newInstance();
				conn = DriverManager.getConnection(url, username, password);
				System.out.println("Database connection established");
				stmt = conn.createStatement();
				Central c = new Central(conn);
				setVisible(false);
			}
			catch (Exception error) 
			{
				JOptionPane.showMessageDialog(null, "Failed Connection Attempt.");
				error.printStackTrace();
			}
		}
	}
	
	// CENTER ON SCREEN
	
	public void centerOnScreen() 
	{
		Toolkit toolkit = getToolkit();
		Dimension size = toolkit.getScreenSize();
		setLocation(size.width/2 - getWidth()/2, size.height/2 - getHeight()/2);
	}
	
	public Connection getConnection()
	{
		return conn;
	}

	
	// MAIN - Brings up a LogIn frame
	
	public static void main(String args[])
	{
			LogIn li = new LogIn();
	}
}
