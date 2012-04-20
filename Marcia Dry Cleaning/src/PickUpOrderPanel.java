// Scott Hoelsema, Arthur Anderson, and Christopher Lenk
// PickUpOrderPanel sets up the GUI for the "Pick Up Order" tab.

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.sql.Connection;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.border.Border;
import javax.swing.plaf.basic.BasicArrowButton;

public class PickUpOrderPanel 
{
	private JPanel PickUpOrderPanel = new JPanel(new BorderLayout());
	private Connection conn;
	private JTextArea dispTA;
	private JPanel searchPanel = new JPanel(new GridLayout(4,3));
	private JPanel displayPanel = new JPanel(new BorderLayout());
	private JPanel confirmPanel = new JPanel();
	private JTextField firstName = new JTextField(15);
	private JTextField lastName = new JTextField(15);
	private JTextField telephone = new JTextField(15);
	private JTextField orderID = new JTextField(15);
	
	public PickUpOrderPanel(Connection conn)
	{
		this.conn = conn;
	}

	public JPanel buildPickUpOrderPanel()
	{
		buildSearchPanel();
		buildDisplayPanel();
		buildConfirmPanel();
		return PickUpOrderPanel;
	}
	
	public void buildSearchPanel()
	{		
		Border northBorder = BorderFactory.createTitledBorder("Specify Order:");
		searchPanel.setBorder(northBorder);
		
		JLabel firstNameTag = new JLabel("First Name:");
		JLabel lastNameTag = new JLabel("Last Name:");
		JLabel telephoneTag = new JLabel("Telephone:");
		JLabel orderIDTag = new JLabel("Order ID:");
		JButton firstNameSubmit = new JButton("Submit");
		JButton lastNameSubmit = new JButton("Submit");
		JButton telephoneSubmit = new JButton("Submit");
		JButton orderIDSubmit = new JButton("Submit");
		searchPanel.add(firstNameTag);
		searchPanel.add(firstName);
		searchPanel.add(firstNameSubmit);
		searchPanel.add(lastNameTag);
		searchPanel.add(lastName);
		searchPanel.add(lastNameSubmit);
		searchPanel.add(telephoneTag);
		searchPanel.add(telephone);
		searchPanel.add(telephoneSubmit);
		searchPanel.add(orderIDTag);
		searchPanel.add(orderID);
		searchPanel.add(orderIDSubmit);
		
		PickUpOrderPanel.add(searchPanel, BorderLayout.NORTH);
	}
		
		
	public void buildDisplayPanel()
	{
		Border centerBorder = BorderFactory.createTitledBorder("Order Information:");
		displayPanel.setBorder(centerBorder);
		
		dispTA = new JTextArea(7, 40);
		dispTA.enableInputMethods(false);
		dispTA.setEditable(false);
		JPanel bp = new JPanel();
		BasicArrowButton leftBtn = new BasicArrowButton(SwingConstants.WEST);
		//leftBtn.addActionListener(new LeftListener());
		bp.add(leftBtn, BorderLayout.WEST);
		BasicArrowButton rightBtn = new BasicArrowButton(SwingConstants.EAST);
		//rightBtn.addActionListener(new RightListener());
		bp.add(rightBtn, BorderLayout.EAST);
		displayPanel.add(dispTA, BorderLayout.NORTH);
		displayPanel.add(bp);
		
		PickUpOrderPanel.add(displayPanel, BorderLayout.CENTER);
	}
	
	public void buildConfirmPanel()
	{
		confirmPanel.add(new JLabel("jfdkslaa;jfdsal;"));
		PickUpOrderPanel.add(confirmPanel, BorderLayout.SOUTH);
	}

}
