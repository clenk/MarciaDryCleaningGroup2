// Scott Hoelsema, Arthur Anderson, and Christopher Lenk
/* Central sets up the GUI for Marcia's Dry Cleaning by referencing ServicePanel,
 * CustomerPanel, NewOrderPanel, and PickUpOrderPanel.  In addition, it contains
 * the listeners and therefore the MySQL to get at the database. */

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;
import javax.swing.*;

public class Central extends JFrame
{
	
	// CENTRAL CONSTRUCTOR
	
	public Central(Connection conn)
	{	
		setTitle("Marcia's Dry Cleaning");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		JTabbedPane tabbedPane = new JTabbedPane();
		ServicesPanel sp = new ServicesPanel(conn);
		CustomerPanel cp = new CustomerPanel(conn);
		//NewOrderPanel nop = new NewOrderPanel(conn);
		//PickUpOrderPanel puop = new PickUpOrderPanel(conn);
		tabbedPane.addTab("  Services  ", sp.buildServicesPanel());
		tabbedPane.addTab("  Customers  ", cp.buildCustomerPanel());
		//tabbedPane.addTab("  New Order  ", nop.buildNewOrderPanel());
		//tabbedPane.addTab("  Pick Up Order  ", puop.buildPickUpOrderPanel());
		add(tabbedPane);
		
		setSize(500, 500);
		centerOnScreen();
		setVisible(true);
	}
	
	// CENTER ON SCREEN
	
	public void centerOnScreen() 
	{
		Toolkit toolkit = getToolkit();
		Dimension size = toolkit.getScreenSize();
		setLocation(size.width/2 - getWidth()/2, size.height/2 - getHeight()/2);
	}
}
