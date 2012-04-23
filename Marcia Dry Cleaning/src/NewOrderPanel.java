// Scott Hoelsema, Arthur Anderson, and Christopher Lenk
// NewOrderPanel sets up the GUI for the "New Order" tab.

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Iterator;
import java.util.LinkedList;

import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.plaf.basic.BasicArrowButton;


public class NewOrderPanel 
{
	private LinkedList orderItems;
	private JPanel NewOrderPanel = new JPanel();
	private Connection conn;
	private JTextField firstTF;
	private JTextField lastTF;
	private JTextField phoneTF;
	private JTextArea dispTA;
	private BasicArrowButton leftBtn;
	private BasicArrowButton rightBtn;
	private JTextField objectName;
	private JList servicesList;
	private JTextArea receipt;
	private JScrollPane scrollReceipt;
	private BasicArrowButton leftPeopleBtn;
	private BasicArrowButton rightPeopleBtn;
	
	private PreparedStatement stmt;
	private String pages;
	private boolean isClubMember;
	private int customerID;
	private double runningTotal;
	private double TAX = .07;
	private ResultSet peopleSet; // Holds all the people found
	private int itemCnt = 0;

	
	public NewOrderPanel(Connection conn)
	{
		this.conn = conn;
	}
	public JPanel buildNewOrderPanel()
	{
		Border panelBorder = BorderFactory.createTitledBorder("New Order Stuff");
		NewOrderPanel.setBorder(panelBorder);
		
		NewOrderPanel.setLayout(new GridBagLayout());
		GridBagConstraints c = new GridBagConstraints();
		c.anchor = GridBagConstraints.CENTER;
		c.fill = GridBagConstraints.BOTH;
		c.weightx = 2;
		c.weighty = 1;
		c.gridy = 0;
		NewOrderPanel.add(buildNorthPanel(), c);
		c.weighty = 10;
		c.gridy = 1;
		NewOrderPanel.add(buildDisplayPanel(),c );
		c.weighty = 50;
		c.gridy = 2;
		NewOrderPanel.add(buildSouthPanel(),c );
		
		return NewOrderPanel;
	}
	public JPanel buildDisplayPanel() {
		
		
		JPanel tp = new JPanel();
		dispTA = new JTextArea(7, 36);
		dispTA.enableInputMethods(false);
		dispTA.setEditable(false);
		dispTA.setFont(new Font(Font.MONOSPACED, Font.PLAIN, 12));
		JScrollPane sp = new JScrollPane(dispTA);
		tp.add(sp);
		JPanel bp = new JPanel();
		leftBtn = new BasicArrowButton(SwingConstants.WEST);
		rightBtn = new BasicArrowButton(SwingConstants.EAST);
		JPanel p = new JPanel();
		p.add(tp);
		return p;
	}
	public JPanel buildSouthPanel() {
		JPanel p = new JPanel();
		p.setLayout(new GridLayout(1, 2));
		Border southBorder = BorderFactory.createTitledBorder("South Panel Stuff");
		p.setBorder(southBorder);
		p.add(lilWestPanel());
		p.add(lilEastPanel());
		
		return p;
	}
	public int getServiceNumber() {
		int num = 0;
		try
		{
			PreparedStatement serviceCountLookup = conn.prepareStatement("SELECT COUNT(*) ServiceDescription FROM service_data");
			ResultSet count = serviceCountLookup.executeQuery();
			if(count.next()) {
				num = count.getInt(1);
			}
			
		} catch (SQLException e)
		{
			e.printStackTrace();
		}
		return num;
	}
	public String[] getServices() {
		int size = getServiceNumber();
		String[] servicesArray = new String[size];
		ResultSet serviceInfo;
		try
		{
			PreparedStatement serviceLookup = conn.prepareStatement("SELECT ServiceDescription FROM service_data");
			serviceInfo = serviceLookup.executeQuery();
			for(int i = 0; i < size; i++) {
				if(serviceInfo.next()) {
					servicesArray[i] = serviceInfo.getString(1);
				}
			}
		} catch (SQLException e)
		{
			e.printStackTrace();
		}
		
		return servicesArray;
	}
	
	public double[] getPrices(String[] services) {
		int size = services.length;
		double[] pricesArray = new double[size];
		ResultSet priceInfo;
		try
		{
			for(int i = 0; i < size; i++) {
				PreparedStatement priceLookup = conn.prepareStatement("SELECT Price FROM service_data WHERE ServiceDescription = ?");
				priceLookup.setString(1, services[i]); 
				priceInfo = priceLookup.executeQuery();
				if(priceInfo.next()) {
					pricesArray[i] = priceInfo.getDouble(1);
				}
			}
		} catch (SQLException e)
		{
			e.printStackTrace();
		}
		
		return pricesArray;
	}
	
	public JPanel lilWestPanel() {
		String[] data = getServices();
		JPanel wp = new JPanel(); 
		objectName = new JTextField(15);
		
		JPanel objectInfo = new JPanel();
		objectInfo.add(new JLabel("Object: "));
		objectInfo.add(objectName);
		
		servicesList = new JList(data);
		servicesList.setSelectionMode(ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
		servicesList.setLayoutOrientation(JList.VERTICAL);
		servicesList.setVisibleRowCount(-1);
		JScrollPane listScroller = new JScrollPane(servicesList);
		wp.setLayout(new BorderLayout());
		wp.add(objectInfo, BorderLayout.PAGE_START);
		wp.add(listScroller, BorderLayout.CENTER);
		wp.add(Box.createRigidArea(new Dimension(50,0)), BorderLayout.LINE_START);
		wp.add(Box.createRigidArea(new Dimension(50,0)), BorderLayout.LINE_END);
		
		return wp;
	}
	public JPanel lilEastPanel() {
		JPanel ep = new JPanel();
		JButton add = new JButton("Add");
		JButton clear = new JButton("Clear");
		JButton submit = new JButton("Submit");
		receipt = new JTextArea(16, 25);
		receipt.enableInputMethods(false);
		receipt.setEditable(false);
		receipt.setLineWrap(true);
		scrollReceipt = new JScrollPane(receipt);
		ep.add(add);
		ep.add(clear);
		ep.add(submit);
		add.addActionListener(new addListener());
		clear.addActionListener(new clearListener());
		submit.addActionListener(new submitListener());
		ep.add(scrollReceipt, BorderLayout.EAST);
		return ep;
	}
	
	private class addListener implements ActionListener
	{
		public void actionPerformed(ActionEvent e)
		{
			if(customerID != 0) {
				
				String object = objectName.getText();
				Object[] servicesO = servicesList.getSelectedValues();
				String[] services = new String[servicesO.length];
				for(int i = 0; i < servicesO.length; i++) {
					services[i] = servicesO[i].toString();
				}
				double[] prices = getPrices(services);
				if(object.equals("") || services.length == 0) {
					JOptionPane.showMessageDialog(null, "You must have both an Object of clothing typed in and one or more services selected");
				} else {
					receiptBuilder(object, services, prices);
				}
			} else {
				JOptionPane.showMessageDialog(null, "You must have a customer selected to add an order!");
			}
		}
	}

	private class clearListener implements ActionListener
	{
		public void actionPerformed(ActionEvent e)
		{
			clear();
		}
	}
	// Clears the receipt text area
	private void clear() {
		receipt.setText("");
		runningTotal = 0.0;
		itemCnt = 0;
	}

	private class submitListener implements ActionListener
	{
		public void actionPerformed(ActionEvent e)
		{
			if(!receipt.equals("")) {
				try
				{
					submitOrder();
				} catch (SQLException e1)
				{
					e1.printStackTrace();
				}
			}
		}
	}
	
	
	private void submitOrder() throws SQLException {
		
		Date currentDatetime = new Date(System.currentTimeMillis());
		Timestamp droppedOff = new Timestamp(currentDatetime.getTime());
		
		double priceTotal = runningTotal+(runningTotal*TAX);
		Timestamp promisedTime = null;
		String[] services;
		Iterator iterator = orderItems.iterator();
		int serviceNum;
		Timestamp timeReqd;
		while (iterator.hasNext()) {
			OrderItem item = (OrderItem) iterator.next();
			int orderNum = getOrderNumber();
			stmt = conn.prepareStatement("INSERT INTO ORDER_ITEM_DATA(ClothingDescription, Quantity, ORDER_DATA_OrderNumber) VALUES(?,?,?)");
			stmt.setString(1, item.getName());
			stmt.setInt(2, 1);
			stmt.setInt(3, orderNum);
			stmt.executeUpdate();
			services = item.getServices();
			for(int i = 0; i < services.length; i++) {
				serviceNum = getServiceNumber(services[i]);
				stmt = conn.prepareStatement("INSERT INTO ORDER_ITEM_DATA_has_SERVICE_DATA(ORDER_ITEM_DATA_idOrderItem, SERVICE_DATA_idService) VALUES(?,?)");
				stmt.setInt(1, orderNum);
				stmt.setInt(2, serviceNum);
				timeReqd = getTime(serviceNum);
				int min = timeReqd.getMinutes();
				int hours = timeReqd.getHours();
				
				promisedTime = new Timestamp(droppedOff.getTime()+(long)(hours*60*60*1000)+(long)(min*60*1000));
			}
		}
		
		stmt = conn.prepareStatement("INSERT INTO ORDER_DATA(DateDroppedOff, DatePromised, Price, Tax, Total, CUSTOMER_DATA_idCustomer) VALUES(?,?,?,?,?,?)");
		stmt.setTimestamp(1, droppedOff);
		stmt.setTimestamp(2, promisedTime);
		stmt.setDouble(3, runningTotal);
		stmt.setDouble(4, TAX);
		stmt.setDouble(5, priceTotal);
		stmt.setInt(6, customerID);
		stmt.executeUpdate();
		
		String finalReceipt = "Date and Time Items Were Dropped Off: "+droppedOff+"\nDate and Time items will be ready: "
		+promisedTime+"\nTotal Price: $"+String.format("%.2f", priceTotal);
		JOptionPane.showMessageDialog(null, finalReceipt);
		

	}
	private Timestamp getTime(int serviceNumber) {
		Date currentDatetime = new Date(System.currentTimeMillis());
		Timestamp timestamp =  new Timestamp(currentDatetime.getTime());
		Timestamp ts = null;
		try
		{
			stmt = conn.prepareStatement("SELECT TimeRequired FROM SERVICE_DATA WHERE idService = ?");
			stmt.setInt(1, serviceNumber);
			ResultSet rs = stmt.executeQuery();
			if(rs.next()) {
				ts = rs.getTimestamp(1);
			}
		} catch (SQLException e)
		{
			e.printStackTrace();
		}
		return ts;
	}
	private int getServiceNumber(String service) {
		int num = 0;
		try
		{
			stmt = conn.prepareStatement("SELECT idService FROM service_data WHERE ServiceDescription = ?");
			stmt.setString(1, service);
			ResultSet rs = stmt.executeQuery();
			if(rs.next()) {
				num = rs.getInt(1);
			}
		} catch (SQLException e)
		{
			e.printStackTrace();
		}
		return num;
	}
	private int getOrderNumber() {
		int num = 0;
		try
		{
			stmt = conn.prepareStatement("SELECT MAX(OrderNumber) FROM ORDER_DATA");
			ResultSet max = stmt.executeQuery();
			if(max.next()) {
				num = max.getInt(1);
			}
		} catch (SQLException e)
		{
			e.printStackTrace();
		}
		return num;
	}
	
	public void receiptBuilder(String object, String[] services, double[] prices) {
		orderItems = new LinkedList();
		OrderItem oi = new OrderItem(object, services, prices);
		orderItems.addLast(oi);
		itemCnt++;
		receipt.append(object+"\n");
		for(int i = 0; i < services.length; i++) {
			receipt.append("    -"+services[i]+"\n");
			double tempPrice = prices[i];
			if (itemCnt == 10 && isClubMember) {
				tempPrice /= 2; // 50% off for the 10th item
			}
			runningTotal += tempPrice;
		}
	}
	
	public JPanel buildNorthPanel() {
		JPanel p = new JPanel();
		p.add(buildNamePanel());
		p.add(buildNameBtnPanel());
		return p;
	}
	public JPanel buildNameBtnPanel(){
		JPanel p = new JPanel();
		JButton findBtn = new JButton("Find");
		findBtn.addActionListener(new FindListener());
		p.add(findBtn);
		leftPeopleBtn = new BasicArrowButton(SwingConstants.WEST);
		leftPeopleBtn.addActionListener(new LeftPeopleListener());
		p.add(leftPeopleBtn);
		rightPeopleBtn = new BasicArrowButton(SwingConstants.EAST);
		rightPeopleBtn.addActionListener(new RightPeopleListener());
		p.add(rightPeopleBtn);
		leftPeopleBtn.setEnabled(false);
		rightPeopleBtn.setEnabled(false);
		return p;
	}
	
	private class FindListener implements ActionListener
	{
		public void actionPerformed(ActionEvent e)
		{
			String first = firstTF.getText();
			String last = lastTF.getText();
			String phone = phoneTF.getText();
			
			peopleSet = null;
			if(!first.equals("") && !last.equals("")) {
				peopleSet = nameQuery(first, last);
			} else if(!phone.equals(null)){
				peopleSet = phoneQuery(phone);
			} else {
				JOptionPane.showMessageDialog(null, "You must have first & last names OR phone number");
			}
			
			try {
				if (peopleSet.next()) { // If any entries found, fill in the form entries for the first person found
					getPerson();
					leftPeopleBtn.setEnabled(false); // Disable the left button because we're on the first result
				} else { // If the name isn't in the database, show an error message
					JOptionPane.showMessageDialog(null, "No one with that name found in the database!");
				}
			} catch (Exception e1) {
				e1.printStackTrace();
				JOptionPane.showMessageDialog(null, "Error while finding person!");
			} 
		}
	}
	
	private void getPerson() throws SQLException {
		dispTA.setText("");
		try {
			pages = extractStringData(peopleSet);
		} catch (Exception e) {
			e.printStackTrace();
		}
		customerID = peopleSet.getInt("idCustomer");
		int clubMem = peopleSet.getInt("isClubMember");
		if(clubMem == 1) {
			isClubMember = true;
		} else {
			isClubMember = false;
		}
		// Enable the right button if there is more than 1 result
		if (!peopleSet.isLast()) {
			rightPeopleBtn.setEnabled(true);
		} else {
			rightPeopleBtn.setEnabled(false);
		}
		if (!peopleSet.isFirst()) {
			leftPeopleBtn.setEnabled(true);
		} else {
			leftPeopleBtn.setEnabled(false);
		}
		dispTA.append(pages);
	}
	
	// Handles the left person arrow button
	private class LeftPeopleListener implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			if (peopleSet == null) {
				return;
			}
			try {
				if (peopleSet.previous()) { // If another person exists...
					getPerson();
				}
				
				// Reset the other arrow button if it's disabled
				if (!rightPeopleBtn.isEnabled()) {
					rightPeopleBtn.setEnabled(true);
				}
				
				// Disable the button if the end of the result set is reached
				if (peopleSet.isFirst()) {
					leftPeopleBtn.setEnabled(false);
				}
				
			} catch (SQLException e1) {
				System.err.println("LeftPeopleListener Error");
			}
		}
	}
	
	// Handles the right person arrow button
	
	private class RightPeopleListener implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			if (peopleSet == null) {
				return;
			}
			try {
				if (peopleSet.next()) { // If another person exists...
					getPerson();

					// Reset the other arrow button if it's disabled
					if (!leftPeopleBtn.isEnabled()) {
						leftPeopleBtn.setEnabled(true);
					}
					
					// Disable the button if the end of the result set is reached
					if (peopleSet.isLast()) {
						rightPeopleBtn.setEnabled(false);
					}
				}
				
			} catch (SQLException e1) {
				System.err.println("RightPeopleListener Error");
			}
		}
	}
	
	public String extractStringData(ResultSet rs) throws Exception {
		int row = rs.getRow(); // Hold our place
		
		String allData = "";
		String first = "";
		String last = "";
		rs.previous();
		while(rs.next()) {
			if(!first.equals(rs.getString("First")) && !last.equals(rs.getString("Last"))) {
				first = rs.getString("First");
				last = rs.getString("Last");
				allData += "%"+first + " "+last;
				allData += "\n"+rs.getString("Street");
				allData += "\n"+rs.getString("City")+", "+rs.getString("State")+" "+rs.getString("Zip");
			}
		}
		rs.absolute(row); // Go back to where we were in the resultset
		
		return allData.substring(allData.indexOf("%")+1);
	}
	
	public ResultSet nameQuery(String first, String last) {
		ResultSet rs = null;
		try
		{
			stmt = conn.prepareStatement("SELECT * FROM CUSTOMER_DATA WHERE FIRST = ? and LAST = ?");
			stmt.setString(1, first);
			stmt.setString(2, last);
			rs = stmt.executeQuery();
		} catch (SQLException e)
		{
			e.printStackTrace();
		}
		
		
		return rs;
	}
	
	public ResultSet phoneQuery(String phone) {
		ResultSet rs = null;
		try
		{
			stmt = conn.prepareStatement("select idCustomer, First, Last, Street, City, State, Zip, isClubMember from customer_data, customer_data_has_phone, phone where customer_data.idCustomer = customer_data_has_phone.CUSTOMER_DATA_idCustomer && customer_data_has_phone.PHONE_idPhone = phone.idPhone && phone.phoneNum = ?");
			stmt.setString(1, phone);
			rs = stmt.executeQuery();
		} catch (SQLException e)
		{
			e.printStackTrace();
		}
		return rs;
	}
	
	
	
	
	public JPanel buildNamePanel() {
		JPanel p = new JPanel();
		p.setLayout(new GridLayout(3, 1));
		
		//First Name label and text field
		JPanel fnamePanel = new JPanel();
		fnamePanel.add(new JLabel("First Name: "));
		firstTF = new JTextField(15);
		fnamePanel.add(firstTF);
		p.add(fnamePanel);

		//Last Name label and text field
		JPanel lnamePanel = new JPanel();
		lnamePanel.add(new JLabel("Last Name: "));
		lastTF = new JTextField(15);
		lnamePanel.add(lastTF);
		p.add(lnamePanel);
		
		//Phone label and text field
		JPanel phonePanel = new JPanel();
		phonePanel.add(new JLabel("Phone Number: "));
		phoneTF = new JTextField(15);
		phonePanel.add(phoneTF);
		p.add(phonePanel);
		return p;
	}
}
