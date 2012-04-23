// Scott Hoelsema, Arthur Anderson, and Christopher Lenk
// PickUpOrderPanel sets up the GUI for the "Pick Up Order" tab.

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
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
	private JTextArea dispTA = new JTextArea(7, 40);;
	private JPanel searchPanel = new JPanel(new GridLayout(4,3,10,8));
	private JPanel displayPanel = new JPanel(new BorderLayout());
	private JPanel confirmPanel = new JPanel();
	private JTextField firstName = new JTextField(15);
	private JTextField lastName = new JTextField(15);
	private JTextField telephone = new JTextField(15);
	private JTextField orderID = new JTextField(15);
	private JComboBox paymentOptions = new JComboBox();
	private int activeOrderID = 0;
	private ArrayList<String> orders = new ArrayList<String>();
	private ArrayList<Integer> activeOrderIDIndices = new ArrayList<Integer>();
	private int entriesIndex;
	private BasicArrowButton leftBtn;
	private BasicArrowButton rightBtn;
	
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
		JButton personSubmit = new JButton("Submit");
		JButton orderIDSubmit = new JButton("Submit");
		searchPanel.add(firstNameTag);
		searchPanel.add(firstName);
		searchPanel.add(new JLabel(""));
		searchPanel.add(lastNameTag);
		searchPanel.add(lastName);
		searchPanel.add(new JLabel(""));
		searchPanel.add(telephoneTag);
		searchPanel.add(telephone);
		searchPanel.add(personSubmit);
		searchPanel.add(orderIDTag);
		searchPanel.add(orderID);
		searchPanel.add(orderIDSubmit);
		
		personSubmit.addActionListener(new nameListener());
		orderIDSubmit.addActionListener(new orderIDListener());
		
		PickUpOrderPanel.add(searchPanel, BorderLayout.NORTH);
	}
		
		
	public void buildDisplayPanel()
	{		
		Border centerBorder = BorderFactory.createTitledBorder("Order Information:");
		displayPanel.setBorder(centerBorder);
		
		dispTA.enableInputMethods(false);
		dispTA.setEditable(false);
		JPanel bp = new JPanel();
		leftBtn = new BasicArrowButton(SwingConstants.WEST);
		leftBtn.addActionListener(new leftListener());
		bp.add(leftBtn, BorderLayout.WEST);
		rightBtn = new BasicArrowButton(SwingConstants.EAST);
		rightBtn.addActionListener(new rightListener());
		bp.add(rightBtn, BorderLayout.EAST);
		displayPanel.add(dispTA, BorderLayout.NORTH);
		displayPanel.add(bp);
		
		PickUpOrderPanel.add(displayPanel, BorderLayout.CENTER);
	}
	
	public void buildConfirmPanel()
	{
		Border selectPaymentOptionsBorder = BorderFactory.createTitledBorder("Pick Up Order:");
		confirmPanel.setBorder(selectPaymentOptionsBorder);
		
		JPanel selectPaymentOption = new JPanel();
		paymentOptions.addItem("Cash");
		paymentOptions.addItem("Check");
		paymentOptions.addItem("Visa");
		paymentOptions.addItem("MasterCard");
		paymentOptions.addItem("Discover");
		selectPaymentOption.add(paymentOptions);
		confirmPanel.add(selectPaymentOption, BorderLayout.WEST);
		
		JPanel submitPickUpOrder = new JPanel();
		JButton submitPickUp = new JButton("Submit Pick Up");
		submitPickUpOrder.add(submitPickUp);
		confirmPanel.add(submitPickUpOrder, BorderLayout.EAST);
		
		submitPickUp.addActionListener(new submitPickUpListener());
		
		PickUpOrderPanel.add(confirmPanel, BorderLayout.SOUTH);
	}
	
	// LISTENERS for the PickUpOrderPanel
	
	// FIRST NAME, LAST NAME, TELEPHONE search listener
	private class nameListener implements ActionListener 
	{
		public void actionPerformed(ActionEvent e) 
		{
			dispTA.setText("");
			orders.clear();
			activeOrderIDIndices.clear();
			rightBtn.setEnabled(true);
			leftBtn.setEnabled(true);
			
			try
			{
				if(!firstName.getText().equals("") && !lastName.getText().equals("")) 
				{
					PreparedStatement nameQuery = conn.prepareStatement("SELECT * FROM CUSTOMER_DATA WHERE FIRST = ? and LAST = ?");
					String first = firstName.getText();
					String last = lastName.getText();
					nameQuery.setString(1, first);
					nameQuery.setString(2, last);
					ResultSet rs = nameQuery.executeQuery();
					rs.next();
					int customerID = rs.getInt("idCustomer");
					PreparedStatement orderQueryByID = conn.prepareStatement("SELECT * FROM ORDER_DATA WHERE CUSTOMER_DATA_idCustomer = ? and DatePickedUp is null;");
					orderQueryByID.setInt(1, customerID);
					ResultSet orderIDrs = orderQueryByID.executeQuery();
					while(orderIDrs.next())
					{
						activeOrderID = orderIDrs.getInt("OrderNumber");
						
						StringBuilder sb = new StringBuilder();
						sb.append("Order ID: ");
						sb.append(activeOrderID);
						sb.append("\n\nDate Dropped Off: ");
						sb.append(orderIDrs.getString("DateDroppedOff"));
						sb.append("\nDate Promised: ");
						sb.append(orderIDrs.getString("DatePromised"));
						sb.append("\nDate Picked Up: ");
						sb.append(orderIDrs.getString("DatePickedUp"));
						sb.append("\n\nPrice: ");
						sb.append(orderIDrs.getString("Price"));
						sb.append("\nTax: ");
						sb.append(orderIDrs.getString("Tax"));
						sb.append("\nTotal: ");
						sb.append(orderIDrs.getString("Total"));
						sb.append("\n\nPayment Method: ");
						sb.append(orderIDrs.getString("PaymentMethod"));
						orders.add(sb.toString());
						activeOrderIDIndices.add(activeOrderID);
					}
					if(orders.size()!=0)
					{
						dispTA.setText(orders.get(0));
						entriesIndex = 0;
					}
				} 
				else if(!telephone.equals(null))
				{
					PreparedStatement telephoneQuery = conn.prepareStatement("select idCustomer from customer_data, customer_data_has_phone, phone where customer_data.idCustomer = customer_data_has_phone.CUSTOMER_DATA_idCustomer && customer_data_has_phone.PHONE_idPhone = phone.idPhone && phone.phoneNum = ?");
					telephoneQuery.setString(1,telephone.getText());
					ResultSet custID = telephoneQuery.executeQuery();
					custID.next();
					int customerID = custID.getInt("idCustomer");
					
					PreparedStatement orderQueryByID = conn.prepareStatement("SELECT * FROM ORDER_DATA WHERE CUSTOMER_DATA_idCustomer = ? and DatePickedUp is null;");
					orderQueryByID.setInt(1, customerID);
					ResultSet orderIDrs = orderQueryByID.executeQuery();
					while(orderIDrs.next())
					{
						activeOrderID = orderIDrs.getInt("OrderNumber");
						
						StringBuilder sb = new StringBuilder();
						sb.append("Order ID: ");
						sb.append(activeOrderID);
						sb.append("\n\nDate Dropped Off: ");
						sb.append(orderIDrs.getString("DateDroppedOff"));
						sb.append("\nDate Promised: ");
						sb.append(orderIDrs.getString("DatePromised"));
						sb.append("\nDate Picked Up: ");
						sb.append(orderIDrs.getString("DatePickedUp"));
						sb.append("\n\nPrice: ");
						sb.append(orderIDrs.getString("Price"));
						sb.append("\nTax: ");
						sb.append(orderIDrs.getString("Tax"));
						sb.append("\nTotal: ");
						sb.append(orderIDrs.getString("Total"));
						sb.append("\n\nPayment Method: ");
						sb.append(orderIDrs.getString("PaymentMethod"));
						orders.add(sb.toString());
						activeOrderIDIndices.add(activeOrderID);
					}
					if(orders.size()!=0)
					{
						dispTA.setText(orders.get(0));
						entriesIndex = 0;
					}
				} 
				else 
				{
					JOptionPane.showMessageDialog(null, "You must have first & last names OR phone number");
				}
			} 
			catch (SQLException e1)
			{
				JOptionPane.showMessageDialog(null, "Invalid arguments.");
				e1.printStackTrace();
				return;
			}
		}
	}
	
	// LEFT LISTENER
	private class leftListener implements ActionListener 
	{
		public void actionPerformed(ActionEvent e) 
		{
			if(entriesIndex-1>-1)
			{
				entriesIndex = entriesIndex-1;
				dispTA.setText(orders.get(entriesIndex));
				activeOrderID = activeOrderIDIndices.get(entriesIndex);
			}
		}
	}
	
	//RIGHT LISTENER
	private class rightListener implements ActionListener 
	{
		public void actionPerformed(ActionEvent e) 
		{
			if(entriesIndex+1<orders.size())
			{
				entriesIndex = entriesIndex+1;
				dispTA.setText(orders.get(entriesIndex));
				activeOrderID = activeOrderIDIndices.get(entriesIndex);
			}
		}
	}
	
	// ORDER ID search listener
	private class orderIDListener implements ActionListener 
	{
		public void actionPerformed(ActionEvent e) 
		{
			dispTA.setText("");
			leftBtn.setEnabled(true);
			rightBtn.setEnabled(true);
			
			try 
			{
				int orderNumber = Integer.parseInt(orderID.getText());
				PreparedStatement orderIDLookup = conn.prepareStatement("SELECT * FROM order_data WHERE OrderNumber = ?");
				orderIDLookup.setInt(1, orderNumber);
				ResultSet orderIDrs = orderIDLookup.executeQuery();
				orderIDrs.next();
				
				activeOrderID = orderIDrs.getInt("OrderNumber");
				
				StringBuilder sb = new StringBuilder();
				sb.append("Order ID: ");
				sb.append(orderNumber);
				sb.append("\n\nDate Dropped Off: ");
				sb.append(orderIDrs.getString("DateDroppedOff"));
				sb.append("\nDate Promised: ");
				sb.append(orderIDrs.getString("DatePromised"));
				sb.append("\nDate Picked Up: ");
				sb.append(orderIDrs.getString("DatePickedUp"));
				sb.append("\n\nPrice: ");
				sb.append(orderIDrs.getString("Price"));
				sb.append("\nTax: ");
				sb.append(orderIDrs.getString("Tax"));
				sb.append("\nTotal: ");
				sb.append(orderIDrs.getString("Total"));
				sb.append("\n\nPayment Method: ");
				sb.append(orderIDrs.getString("PaymentMethod"));
				
				dispTA.append(sb.toString());				
			} 
			catch (SQLException e1) 
			{
				JOptionPane.showMessageDialog(null, "Enter a valid Order ID!");
				return;
			}
			catch(NumberFormatException e1)
			{
				JOptionPane.showMessageDialog(null, "Enter a valid Order ID!");
				return;
			}
		}
	}
	private class submitPickUpListener implements ActionListener 
	{
		public void actionPerformed(ActionEvent e) 
		{
			if(activeOrderID==0)
			{
				JOptionPane.showMessageDialog(null, "First, look up an order.");
				return;
			}
			PreparedStatement checkIfPickedUp;
			try 
			{
				checkIfPickedUp = conn.prepareStatement("SELECT * FROM order_data WHERE OrderNumber = ?");
				checkIfPickedUp.setInt(1, activeOrderID);
				ResultSet order = checkIfPickedUp.executeQuery();
				order.next();
				if(order.getString("DatePickedUp")!=null)
				{
					JOptionPane.showMessageDialog(null, "Order already picked up!");
					return;
				}
				else
				{
					try 
					{				
						PreparedStatement pickUpOrderSubmit = conn.prepareStatement("UPDATE order_data SET PaymentMethod = ?, DatePickedUp = ? WHERE OrderNumber = ?");
						String paymentMethod = (String) paymentOptions.getSelectedItem();
						pickUpOrderSubmit.setString(1, paymentMethod);
						
						Date currentDatetime = new Date(System.currentTimeMillis());
						Timestamp timestamp = new Timestamp(currentDatetime.getTime());
						pickUpOrderSubmit.setTimestamp(2, timestamp);
						
						pickUpOrderSubmit.setInt(3, activeOrderID);
						
						pickUpOrderSubmit.executeUpdate();
						JOptionPane.showMessageDialog(null, "Order picked up: " + timestamp);
						leftBtn.setEnabled(false);
						rightBtn.setEnabled(false);
					} 
					catch (SQLException e1) 
					{
						e1.printStackTrace();
					}
				}
			} 
			catch (SQLException e2) 
			{
				e2.printStackTrace();
			}
			
			dispTA.setText("");
			
			try 
			{
				PreparedStatement orderIDLookup = conn.prepareStatement("SELECT * FROM order_data WHERE OrderNumber = ?");
				orderIDLookup.setInt(1, activeOrderID);
				ResultSet orderIDrs = orderIDLookup.executeQuery();
				orderIDrs.next();
				
				StringBuilder sb = new StringBuilder();
				sb.append("Order ID: ");
				sb.append(activeOrderID);
				sb.append("\n\nDate Dropped Off: ");
				sb.append(orderIDrs.getString("DateDroppedOff"));
				sb.append("\nDate Promised: ");
				sb.append(orderIDrs.getString("DatePromised"));
				sb.append("\nDate Picked Up: ");
				sb.append(orderIDrs.getString("DatePickedUp"));
				sb.append("\n\nPrice: ");
				sb.append(orderIDrs.getString("Price"));
				sb.append("\nTax: ");
				sb.append(orderIDrs.getString("Tax"));
				sb.append("\nTotal: ");
				sb.append(orderIDrs.getString("Total"));
				sb.append("\n\nPayment Method: ");
				sb.append(orderIDrs.getString("PaymentMethod"));
				
				dispTA.append(sb.toString());				
			} 
			catch (SQLException e1) 
			{
				JOptionPane.showMessageDialog(null, "Enter a valid Order ID!");
				return;
			}
			catch(NumberFormatException e1)
			{
				JOptionPane.showMessageDialog(null, "Enter a valid Order ID!");
				return;
			}
		}
		
	}
}
