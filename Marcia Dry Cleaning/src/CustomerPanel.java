
// Scott Hoelsema, Arthur Anderson, and Christopher Lenk
// CustomerPanel sets up the GUI for the "Customer" tab.

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;

import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.plaf.basic.BasicArrowButton;

public class CustomerPanel
{
	private JPanel CustomerPanel = new JPanel();
	
	//Database variables
	private Connection conn;
	private PreparedStatement stmt;

	// GUI Variables
	private JTextField firstTF;
	private JTextField lastTF;
	private JTextField streetTF;
	private JTextField stateTF;
	private JTextField cityTF;
	private JTextField zipTF;
	private JCheckBox isMemberChkBx;
	private JTextField newPhoneTF;
	private JTextField curPhoneTF;
	private JTextField newEmailTF;
	private JTextField curEmailTF;
	private BasicArrowButton leftPhoneBtn;
	private BasicArrowButton rightPhoneBtn;
	private BasicArrowButton leftEmailBtn;
	private BasicArrowButton rightEmailBtn;

	int curPerson; // The id of the current person entry (column: CUSTOMER_DATA.idCustomer)
	ResultSet phoneSet; // Holds all the phones for the current person
	String curPhone = ""; // The current phone entry
	ResultSet emailSet; // Holds all the Emails for the current person
	String curEmail = ""; // The current Email entry
	
	// CONSTRUCTOR
	public CustomerPanel(Connection conn) {
		this.curPerson = -1;
		this.conn = conn;
	}

	// GUI BUILDERS
	
	// Creates the add, find, delete, and edit buttons for the general customer info
	public JPanel buildNameBtnPanel() {
		JPanel p = new JPanel();
		JButton addBtn = new JButton("Add");
		JButton findBtn = new JButton("Find");
		JButton delBtn = new JButton("Delete");
		JButton editBtn = new JButton("Edit");
		addBtn.addActionListener(new NameListener()); // Add the Customer button listeners
		findBtn.addActionListener(new NameListener());
		delBtn.addActionListener(new NameListener());
		editBtn.addActionListener(new NameListener());
		p.add(addBtn);
		p.add(findBtn);
		p.add(delBtn);
		p.add(editBtn);
		return p;
	}

	// Creates the text fields for the general customer info
	public JPanel buildNamePanel() {
		JPanel p = new JPanel();
		p.setLayout(new GridLayout(8, 1, 0, -2));
		Border custBorder = BorderFactory.createTitledBorder("Customer Info:");
		p.setBorder(custBorder);
		
		// First Name label and text field
		JPanel fnamePanel = new JPanel();
		fnamePanel.add(new JLabel("First Name: "));
		firstTF = new JTextField(15);
		fnamePanel.add(firstTF);
		p.add(fnamePanel);

		// Last Name label and text field
		JPanel lnamePanel = new JPanel();
		lnamePanel.add(new JLabel("Last Name: "));
		lastTF = new JTextField(15);
		lnamePanel.add(lastTF);
		p.add(lnamePanel);
		
		// Street label and text field
		JPanel streetPanel = new JPanel();
		streetPanel.add(new JLabel("Street: "));
		streetTF = new JTextField(15);
		streetPanel.add(streetTF);
		p.add(streetPanel);
		
		// City label and text field
		JPanel cityPanel = new JPanel();
		cityPanel.add(new JLabel("City: "));
		cityTF = new JTextField(15);
		cityPanel.add(cityTF);
		p.add(cityPanel);
		
		// State label and text field
		JPanel statePanel = new JPanel();
		statePanel.add(new JLabel("State: "));
		stateTF = new JTextField(15);
		statePanel.add(stateTF);
		p.add(statePanel);
		
		// Zip label and text field
		JPanel zipPanel = new JPanel();
		zipPanel.add(new JLabel("Zip: "));
		zipTF = new JTextField(15);
		zipPanel.add(zipTF);
		p.add(zipPanel);
		
		// IsClubMember label and text field
		JPanel isMemberPanel = new JPanel();
		isMemberChkBx = new JCheckBox("Is Club Member");
		isMemberPanel.add(isMemberChkBx);
		p.add(isMemberPanel);
		
		p.add(buildNameBtnPanel());
		return p;
	}
	
	// Creates the phone number section
	public JPanel buildPhonePanel() {
		JPanel p = new JPanel();
		Border b = BorderFactory.createTitledBorder("Phone Number(s):");
		p.setBorder(b);
		p.setLayout(new BorderLayout());
		
		JPanel newPhonePanel = new JPanel();
		newPhonePanel.add(new JLabel("New: "));
		newPhoneTF = new JTextField(8);
		newPhonePanel.add(newPhoneTF);
		JButton addPhoneBtn = new JButton("Add");
		addPhoneBtn.addActionListener(new PhoneListener());
		newPhonePanel.add(addPhoneBtn);
		p.add(newPhonePanel, BorderLayout.PAGE_START);
		
		JPanel curPhonesPanel = new JPanel();
		curPhonesPanel.add(new JLabel("Current: "));
		curPhoneTF = new JTextField(8);
		curPhoneTF.setEditable(false);
		curPhonesPanel.add(curPhoneTF);
		leftPhoneBtn = new BasicArrowButton(SwingConstants.WEST);
		leftPhoneBtn.addActionListener(new LeftPhoneListener());
		curPhonesPanel.add(leftPhoneBtn);
		rightPhoneBtn = new BasicArrowButton(SwingConstants.EAST);
		rightPhoneBtn.addActionListener(new RightPhoneListener());
		curPhonesPanel.add(rightPhoneBtn);
		p.add(curPhonesPanel, BorderLayout.CENTER);
		
		JPanel updatePhonesPanel = new JPanel();
		JButton delPhonesBtn = new JButton("Delete");
		delPhonesBtn.addActionListener(new PhoneListener());
		updatePhonesPanel.add(delPhonesBtn);
		JButton editPhoneBtn = new JButton("Edit");
		editPhoneBtn.addActionListener(new PhoneListener());
		updatePhonesPanel.add(editPhoneBtn);
		p.add(updatePhonesPanel, BorderLayout.PAGE_END);
		
		return p;
	}
	
	// Creates the email section
	public JPanel buildEmailPanel() {
		JPanel p = new JPanel();
		Border b = BorderFactory.createTitledBorder("Email(s):");
		p.setBorder(b);
		p.setLayout(new BorderLayout());
		
		JPanel newEmailPanel = new JPanel();
		newEmailPanel.add(new JLabel("New: "));
		newEmailTF = new JTextField(12);
		newEmailPanel.add(newEmailTF);
		JButton addPhoneBtn = new JButton("Add");
		addPhoneBtn.addActionListener(new EmailListener());
		newEmailPanel.add(addPhoneBtn);
		p.add(newEmailPanel, BorderLayout.PAGE_START);
		
		JPanel curEmailsPanel = new JPanel();
		curEmailsPanel.add(new JLabel("Current: "));
		curEmailTF = new JTextField(12);
		curEmailTF.setEditable(false);
		curEmailsPanel.add(curEmailTF);
		leftEmailBtn = new BasicArrowButton(SwingConstants.WEST);
		leftEmailBtn.addActionListener(new LeftEmailListener());
		curEmailsPanel.add(leftEmailBtn);
		rightEmailBtn = new BasicArrowButton(SwingConstants.EAST);
		rightEmailBtn.addActionListener(new RightEmailListener());
		curEmailsPanel.add(rightEmailBtn);
		p.add(curEmailsPanel, BorderLayout.CENTER);
		
		JPanel updateEmailsPanel = new JPanel();
		JButton delEmailsBtn = new JButton("Delete");
		delEmailsBtn.addActionListener(new EmailListener());
		updateEmailsPanel.add(delEmailsBtn);
		JButton editEmailBtn = new JButton("Edit");
		editEmailBtn.addActionListener(new EmailListener());
		updateEmailsPanel.add(editEmailBtn);
		p.add(updateEmailsPanel, BorderLayout.PAGE_END);
		
		return p;
	}
	
	public JPanel buildCustomerPanel() 
	{
		CustomerPanel.setLayout(new BorderLayout());
		CustomerPanel.add(buildNamePanel(), BorderLayout.PAGE_START);
		CustomerPanel.add(buildPhonePanel(), BorderLayout.LINE_START);
		CustomerPanel.add(buildEmailPanel(), BorderLayout.LINE_END);
		
		return CustomerPanel;
	}
	
	// LOGIC (used by the listeners)
	
	// Clears the form fields 
	private void clear() {
		curPerson = -1;
		firstTF.setText("");
		lastTF.setText("");
		streetTF.setText("");
		cityTF.setText("");
		stateTF.setText("");
		zipTF.setText("");
		isMemberChkBx.setSelected(false);
		curPhoneTF.setText("");
		curEmailTF.setText("");
		newPhoneTF.setText("");
		newEmailTF.setText("");
		leftPhoneBtn.setEnabled(false);
		rightPhoneBtn.setEnabled(false);
		leftEmailBtn.setEnabled(false);
		rightEmailBtn.setEnabled(false);
	}
	
	// Adds a person to the database
	private boolean addPerson(String first, String last, String street, String city, String state, String zip, int isMembr) throws SQLException {
		// First make sure the person is not already in the database
		stmt = conn.prepareStatement("SELECT First, Last FROM CUSTOMER_DATA WHERE First = ? AND Last = ?");
		stmt.setString(1, first);
		stmt.setString(2, last);
		ResultSet rs = stmt.executeQuery();
		if (rs.next()) { // If any entries found...
			JOptionPane.showMessageDialog(null, "Error: Customer already in the database!");
			return false;
		}
		
		// Add a person with the name to the database
		stmt = conn.prepareStatement("INSERT INTO CUSTOMER_DATA(First, Last, Street, City, State, Zip, IsClubMember) VALUES(?,?,?,?,?,?,?)");
		stmt.setString(1, first);
		stmt.setString(2, last);
		stmt.setString(3, street);
		stmt.setString(4, city);
		stmt.setString(5, state);
		stmt.setString(6, zip);
		stmt.setInt(7, isMembr);
		try {
			stmt.executeUpdate();
			JOptionPane.showMessageDialog(null, "Customer added!");
			return true;
		} catch (SQLException e) {
			JOptionPane.showMessageDialog(null, "Error: Customer could not be added!");
			return false;
		}
	}
	
	// Finds the person with the specified first/last name combination in the database
	private boolean findPerson(String first, String last) throws SQLException {
		// Search the database for the name
		stmt = conn.prepareStatement("SELECT idCustomer, First, Last, Street, City, State, Zip, IsClubMember FROM CUSTOMER_DATA WHERE First = ? AND Last = ?");
		stmt.setString(1, first);
		stmt.setString(2, last);
		ResultSet rs = stmt.executeQuery();
		
		if (rs.next()) { // If any entries found...
			clear();
			curPerson = rs.getInt("idCustomer");
			// Fill in the form fields
			firstTF.setText(rs.getString("First"));
			lastTF.setText(rs.getString("Last"));
			streetTF.setText(rs.getString("Street"));
			cityTF.setText(rs.getString("City"));
			stateTF.setText(rs.getString("State"));
			zipTF.setText(rs.getString("Zip"));
			if (rs.getBoolean("IsClubMember")) {
				isMemberChkBx.setSelected(true);
			} else {
				isMemberChkBx.setSelected(false);
			}
			
			// Find their phone info 
			stmt = conn.prepareStatement("SELECT PhoneNum FROM PHONE, CUSTOMER_DATA_HAS_PHONE WHERE idPhone = PHONE_idPhone AND CUSTOMER_DATA_idCustomer = ?");
			stmt.setInt(1, curPerson);
			phoneSet = stmt.executeQuery();
			if (phoneSet.next()) { // If any entries found...
				curPhone = phoneSet.getString(1);
				curPhoneTF.setText(curPhone); // Output the first phone number
				
				// Enable the right button if there is more than 1 result
				if (!phoneSet.isLast()) {
					rightPhoneBtn.setEnabled(true);
				} else {
					rightPhoneBtn.setEnabled(false);
				}
				leftPhoneBtn.setEnabled(false); // Disable the left button because we're on the first result  
			}
			
			// Find their email info
			stmt = conn.prepareStatement("SELECT EmailAddr FROM EMAIL, CUSTOMER_DATA_HAS_EMAIL WHERE idEmail = EMAIL_idEmail AND CUSTOMER_DATA_idCustomer = ?");
			stmt.setInt(1, curPerson);
			emailSet = stmt.executeQuery();
			if (emailSet.next()) { // If any entries found...
				curEmail = emailSet.getString(1);
				curEmailTF.setText(curEmail); // Output the first email
				
				// Enable the right button if there is more than 1 result
				if (!emailSet.isLast()) {
					rightEmailBtn.setEnabled(true);
				} else {
					rightEmailBtn.setEnabled(false);
				}
				leftEmailBtn.setEnabled(false); // Disable the left button because we're on the first result  
			}
			
			return true;
		} else { // If the name isn't in the database, show an error message
			JOptionPane.showMessageDialog(null, "No one with that name found in the database!");
			return false;
		}
	}
	
	// Delete the currently selected person
	private boolean delPerson() {
		try {
			// First delete their emails and phones
			phoneSet.beforeFirst();
			while (phoneSet.next()) {
				curPhone = phoneSet.getString(1);
				delPhone(false);
				phoneSet.beforeFirst();
			}
			emailSet.beforeFirst();
			while (emailSet.next()) {
				curEmail = emailSet.getString(1);
				delEmail(false);
				emailSet.beforeFirst();
			}
			System.err.println("phones and emails deled");
			
			// Delete them from the database
			stmt = conn.prepareStatement("DELETE FROM CUSTOMER_DATA WHERE `idCustomer`=?");
			System.err.println("statement prepared");
			stmt.setInt(1, curPerson);
			stmt.executeUpdate();
			
			System.err.println("update executed");
			
			// Clear the form fields
			clear();
			System.err.println("cleared");
			
			JOptionPane.showMessageDialog(null, "Customer deleted!");
			return true;
		} catch (SQLException e) {
			JOptionPane.showMessageDialog(null, "Error: Customer could not be deleted!");
			return false;
		}
	}

	// Edit the data of the current person in the database
	private boolean editPerson(String first, String last, String street, String city, String state, String zip, int isMembr) throws SQLException {
		stmt = conn.prepareStatement("UPDATE CUSTOMER_DATA SET `First`=?, `Last`=?, `Street`=?, `City`=?, `State`=?, `Zip`=?, `IsClubMember`=? WHERE `idCustomer`=?");
		stmt.setString(1, first);
		stmt.setString(2, last);
		stmt.setString(3, street);
		stmt.setString(4, city);
		stmt.setString(5, state);
		stmt.setString(6, zip);
		stmt.setInt(7, isMembr);
		stmt.setInt(8, curPerson);
		try {
			stmt.executeUpdate();
			JOptionPane.showMessageDialog(null, "Customer updated!");
			return true;
		} catch (SQLException e) {
			JOptionPane.showMessageDialog(null, "Error: Customer could not be updated!");
			return false;
		}
	}
	
	// Finds the given number in the database and returns its id
	private int findPhone(String phone) throws SQLException {
		stmt = conn.prepareStatement("SELECT idPhone, PhoneNum FROM PHONE WHERE PhoneNum = ?");
		stmt.setString(1, phone);
		ResultSet rs = stmt.executeQuery();
		if (rs.next()) { // If any entries found...
			return rs.getInt("idPhone");
		} else {
			return -1;
		}
	}
	
	// Finds the given email in the database and returns its id
	private int findEmail(String email) throws SQLException {
		stmt = conn.prepareStatement("SELECT idEmail, EmailAddr FROM EMAIL WHERE EmailAddr = ?");
		stmt.setString(1, email);
		ResultSet rs = stmt.executeQuery();
		if (rs.next()) { // If any entries found...
			return rs.getInt("idEmail");
		} else {
			return -1;
		}
	}
	
	// Adds the connection between the customer_data table and the phone table
	private void addPhoneHas(int id) throws SQLException {
		stmt = conn.prepareStatement("INSERT INTO CUSTOMER_DATA_HAS_PHONE(CUSTOMER_DATA_idCustomer, PHONE_idPhone) VALUES(?,?)");
		stmt.setInt(1, curPerson);
		stmt.setInt(2, id);
		stmt.executeUpdate();
	}
	
	// Adds the connection between the customer_data table and the phone table
	private void addEmailHas(int id) throws SQLException {
		stmt = conn.prepareStatement("INSERT INTO CUSTOMER_DATA_HAS_EMAIL(CUSTOMER_DATA_idCustomer, EMAIL_idEmail) VALUES(?,?)");
		stmt.setInt(1, curPerson);
		stmt.setInt(2, id);
		stmt.executeUpdate();
	}

	// Adds a phone to the current person in the database
	private boolean addPhone(String phone) throws SQLException {
		try {
			int PhoneID = findPhone(phone);
			if (PhoneID < 0) { // Phone not in the database, so add it
				stmt = conn.prepareStatement("INSERT INTO PHONE(PhoneNum) VALUES(?)");
				stmt.setString(1, phone);
				stmt.executeUpdate();
				PhoneID = findPhone(phone);
			}
			addPhoneHas(PhoneID);
		} catch (SQLException e) {
			JOptionPane.showMessageDialog(null, "Error: Phone could not be added!");
			return false;
		}
		JOptionPane.showMessageDialog(null, "Phone added!");
		findPerson(firstTF.getText(), lastTF.getText()); // Reset the form data
		return true;
	}

	// Adds a email to the current person in the database
	private boolean addEmail(String email) throws SQLException {
		try {
			int emailID = findEmail(email);
			if (emailID < 0) { // Email not in the database, so add it
				stmt = conn.prepareStatement("INSERT INTO EMAIL(EmailAddr) VALUES(?)");
				stmt.setString(1, email);
				stmt.executeUpdate();
				emailID = findEmail(email);
			}
			addEmailHas(emailID);
		} catch (SQLException e) {
			JOptionPane.showMessageDialog(null, "Error: Email could not be added!");
			return false;
		}
		JOptionPane.showMessageDialog(null, "Email added!");
		findPerson(firstTF.getText(), lastTF.getText()); // Reset the form data
		return true;
	}
	
	// Edits the current phone
	private boolean editPhone() {
		try {
			int PhoneID = findPhone(curPhone);
			stmt = conn.prepareStatement("UPDATE PHONE SET `PhoneNum`=? WHERE `idPhone`=?");
			stmt.setString(1, newPhoneTF.getText());
			stmt.setInt(2, PhoneID);
			stmt.executeUpdate();
			JOptionPane.showMessageDialog(null, "Phone updated!");
			findPerson(firstTF.getText(), lastTF.getText()); // Reset the form data
			return true;
		} catch (SQLException e) {
			JOptionPane.showMessageDialog(null, "Error: Phone could not be updated!");
			return false;
		}
	}
	
	// Edits the current email
	private boolean editEmail() {
		try {
			int EmailID = findEmail(curEmail);
			stmt = conn.prepareStatement("UPDATE EMAIL SET `EmailAddr`=? WHERE `idEmail`=?");
			stmt.setString(1, newEmailTF.getText());
			stmt.setInt(2, EmailID);
			stmt.executeUpdate();
			JOptionPane.showMessageDialog(null, "Email updated!");
			findPerson(firstTF.getText(), lastTF.getText()); // Reset the form data
			return true;
		} catch (SQLException e) {
			JOptionPane.showMessageDialog(null, "Error: Email could not be updated!");
			return false;
		}
	}
	
	// Deletes the current phone
	private boolean delPhone(boolean print) {
		try {
			int PhoneID = findPhone(curPhone);
			stmt = conn.prepareStatement("DELETE FROM PHONE WHERE `idPhone`=?");
			stmt.setInt(1, PhoneID);
			stmt.executeUpdate();
			if (print) JOptionPane.showMessageDialog(null, "Phone deleted!");
			findPerson(firstTF.getText(), lastTF.getText()); // Reset the form data
			return true;
		} catch (SQLException e) {
			JOptionPane.showMessageDialog(null, "Error: Phone could not be deleted!");
			return false;
		}
	}
	
	// Deletes the current phone
	private boolean delEmail(boolean print) {
		try {
			int EmailID = findEmail(curEmail);
			stmt = conn.prepareStatement("DELETE FROM EMAIL WHERE `idEmail`=?");
			stmt.setInt(1, EmailID);
			stmt.executeUpdate();
			System.err.println("DELETING "+curEmail);
			if (print) JOptionPane.showMessageDialog(null, "Email deleted!");
			findPerson(firstTF.getText(), lastTF.getText()); // Reset the form data
			return true;
		} catch (SQLException e) {
			JOptionPane.showMessageDialog(null, "Error: Email could not be deleted!");
			return false;
		}
	}
	
	// LISTENERS

	// Handles the buttons associated with the Name field
	private class NameListener implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			// Get the form values
			String first = firstTF.getText();
			String last = lastTF.getText();
			String street = streetTF.getText();
			String city = cityTF.getText();
			String state = stateTF.getText();
			String zip = zipTF.getText();
			Boolean clubBool = isMemberChkBx.isSelected();
			int isClubMember = clubBool.compareTo(false); // convert boolean to int

			if (first.trim().equals("") || last.trim().equals("")) { // Don't allow blank names (NOT NULL)
				JOptionPane.showMessageDialog(null, "Name cannot be blank!");
				return;
			}
			
			// Do different actions depending on which button was pressed
			String buttonText = ((JButton)e.getSource()).getText();
			try {
				// If the Add button was pressed...
				if (buttonText.equals("Add")) {
					addPerson(first, last, street, city, state, zip, isClubMember);
				
				// If the Find button was pressed...
				} else if (buttonText.equals("Find")) { 
					findPerson(first, last);

				// If the Delete button was pressed...
				} else if (buttonText.equals("Delete")) {
					if (curPerson > 0) {
						delPerson();
					} else {
						JOptionPane.showMessageDialog(null, "Error: Find a person first!");
					}
					
				// If the Edit button was pressed...
				} else if (buttonText.equals("Edit")) {
					if (curPerson > 0) {
						editPerson(first, last, street, city, state, zip, isClubMember);
					} else {
						JOptionPane.showMessageDialog(null, "Error: Find a person first!");
					}
				}
			} catch (SQLException e1) { // Handle Errors
				e1.printStackTrace();
				JOptionPane.showMessageDialog(null, "Database Error!");
			}
		}
	}

	// Handles the buttons associated with the Phone field
	private class PhoneListener implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			String newphone = newPhoneTF.getText(); // Get the Phone input
			
			if (curPerson < 0) { // Make sure we have a person to add the phone to
				JOptionPane.showMessageDialog(null, "Find a person first!");
				return;
			} else if (newphone.trim().equals("") || newphone.equals("")) { // Don't allow blank entries
				JOptionPane.showMessageDialog(null, "Phone cannot be blank!");
				return;
			} else if (!newphone.matches("\\d{3}-\\d{3}-\\d{4}")) { // Make sure a valid phone number was entered
				JOptionPane.showMessageDialog(null, "Phone number must be in valid format (xxx-xxx-xxxx)!");
				return;
			}
			
			// Do different actions depending on which button was pressed
			String buttonText = ((JButton)e.getSource()).getText();
			try {
				// If the Add button was pressed...
				if (buttonText.equals("Add")) {
					addPhone(newphone);
					
				// If the Edit button was pressed...
				} else if (buttonText.equals("Edit")) { 
					editPhone();

				// If the Delete button was pressed...
				} else if (buttonText.equals("Delete")) {
					delPhone(true);
				}
			} catch (SQLException e1) { // Handle Errors
				JOptionPane.showMessageDialog(null, "Database Error!");
			}
		}
	}

	// Handles the right phone arrow button
	private class LeftPhoneListener implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			try {
				if (phoneSet.previous()) { // If another name exists...
					curPhone = phoneSet.getString(1);
					curPhoneTF.setText(curPhone); // Output the name
				}
				
				// Reset the other arrow button if it's disable
				if (!rightPhoneBtn.isEnabled()) {
					rightPhoneBtn.setEnabled(true);
				}
				
				// Disable the button if the end of the result set is reached
				if (phoneSet.isFirst()) {
					leftPhoneBtn.setEnabled(false);
				}
			} catch (SQLException e1) {
				System.err.println("LeftPhoneListener Error");
			}
		}
	}

	// Handles the right phone arrow button
	private class RightPhoneListener implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			try {
				if (phoneSet.next()) { // If another phone exists...
					curPhone = phoneSet.getString(1);
					curPhoneTF.setText(curPhone); // Output the phone

					// Reset the other arrow button if it's disable
					if (!leftPhoneBtn.isEnabled()) {
						leftPhoneBtn.setEnabled(true);
					}
					
					// Disable the button if the end of the result set is reached
					if (phoneSet.isLast()) {
						rightPhoneBtn.setEnabled(false);
					}
				}
			} catch (SQLException e1) {
				System.err.println("RightPhoneListener Error");
			}
		}
	}

	// Handles the buttons associated with the Email field
	private class EmailListener implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			String newEmail = newEmailTF.getText(); // Get the Email input
			
			if (curPerson < 0) { // Make sure we have a person to add the phone to
				JOptionPane.showMessageDialog(null, "Find a person first!");
				return;
			} else if (newEmail.trim().equals("") || newEmail.equals("")) { // Don't allow blank entries
				JOptionPane.showMessageDialog(null, "Email cannot be blank!");
				return;
			} else if (!newEmail.matches(".+@.+\\.[a-z]+")) { // Make sure a valid email address was entered
				JOptionPane.showMessageDialog(null, "Email must be in a valid format!");
				return;
			}
			
			// Do different actions depending on which button was pressed
			String buttonText = ((JButton)e.getSource()).getText();
			try {
				// If the Add button was pressed...
				if (buttonText.equals("Add")) {
					addEmail(newEmail);
					
				// If the Edit button was pressed...
				} else if (buttonText.equals("Edit")) { 
					editEmail();

				// If the Delete button was pressed...
				} else if (buttonText.equals("Delete")) {
					delEmail(true);
				}
			} catch (SQLException e1) { // Handle Errors
				JOptionPane.showMessageDialog(null, "Database Error!");
			}
		}
	}

	// Handles the right email arrow button
	private class LeftEmailListener implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			try {
				if (emailSet.previous()) { // If another email exists...
					curEmail = emailSet.getString(1);
					curEmailTF.setText(curEmail); // Output the email
				}
				
				// Reset the other arrow button if it's disable
				if (!rightEmailBtn.isEnabled()) {
					rightEmailBtn.setEnabled(true);
				}
				
				// Disable the button if the end of the result set is reached
				if (emailSet.isFirst()) {
					leftEmailBtn.setEnabled(false);
				}
			} catch (SQLException e1) {
				System.err.println("LeftEmailListener Error");
			}
		}
	}

	// Handles the right email arrow button
	private class RightEmailListener implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			try {
				if (emailSet.next()) { // If another email exists...
					curEmail = emailSet.getString(1);
					curEmailTF.setText(curEmail); // Output the email

					// Reset the other arrow button if it's disable
					if (!leftEmailBtn.isEnabled()) {
						leftEmailBtn.setEnabled(true);
					}
					
					// Disable the button if the end of the result set is reached
					if (emailSet.isLast()) {
						rightEmailBtn.setEnabled(false);
					}
				}
			} catch (SQLException e1) {
				System.err.println("RightEmailListener Error");
			}
		}
	}

}

