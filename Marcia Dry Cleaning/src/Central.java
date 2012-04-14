// Scott Hoelsema, Arthur Anderson, and Christopher Lenk
/* Central sets up the GUI for Marcia's Dry Cleaning by referencing ServicePanel,
 * CustomerPanel, NewOrderPanel, and PickUpOrderPanel.  In addition, it contains
 * the listeners and therefore the MySQL to get at the database. */

import java.awt.*;
import java.sql.*;
import javax.swing.*;

public class Central extends JFrame
{
	
	// CENTRAL CONSTRUCTOR
	
	public Central()
	{	
		setTitle("Marcia's Dry Cleaning");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		JTabbedPane tabbedPane = new JTabbedPane();
		tabbedPane.addTab("  Services  ", ServicesPanel.buildServicesPanel());
		tabbedPane.addTab("  Customers  ", CustomerPanel.buildCustomerPanel());
		tabbedPane.addTab("  New Order  ", NewOrderPanel.buildNewOrderPanel());
		tabbedPane.addTab("  Pick Up Order  ", PickUpOrderPanel.buildPickUpOrderPanel());
		add(tabbedPane);
		
		setSize(500, 500);
		centerOnScreen();
		setVisible(true);
	}
	
	// MAIN - Brings up a LogIn frame
	
	public static void main(String args[])
	{
			new LogIn();
	}
	
	// CENTER ON SCREEN
	
	public void centerOnScreen() 
	{
		Toolkit toolkit = getToolkit();
		Dimension size = toolkit.getScreenSize();
		setLocation(size.width/2 - getWidth()/2, size.height/2 - getHeight()/2);
	}
}
