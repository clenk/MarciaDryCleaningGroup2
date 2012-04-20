// Scott Hoelsema, Arthur Anderson, and Christopher Lenk
// PickUpOrderPanel sets up the GUI for the "Pick Up Order" tab.

import java.sql.Connection;

import javax.swing.JPanel;

public class PickUpOrderPanel 
{
	private JPanel PickUpOrderPanel = new JPanel();
	private Connection conn;
	
	public PickUpOrderPanel(Connection conn)
	{
		this.conn = conn;
	}

	public JPanel buildPickUpOrderPanel()
	{
		
		return PickUpOrderPanel;
	}
}
