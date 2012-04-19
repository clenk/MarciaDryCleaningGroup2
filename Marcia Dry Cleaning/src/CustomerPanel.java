
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

	String curPerson = ""; // The name of the current Person entry
	
	// CONSTRUCTOR
	public CustomerPanel(Connection conn) {
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
		p.setLayout(new GridLayout(8, 1));
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
		//addPhoneBtn.addActionListener(new addPhoneListener());
		newPhonePanel.add(addPhoneBtn);
		p.add(newPhonePanel, BorderLayout.PAGE_START);
		
		JPanel curPhonesPanel = new JPanel();
		curPhonesPanel.add(new JLabel("Current: "));
		curPhoneTF = new JTextField(8);
		curPhonesPanel.add(curPhoneTF);
		leftPhoneBtn = new BasicArrowButton(SwingConstants.WEST);
		//leftBtn.addActionListener(new LeftListener());
		curPhonesPanel.add(leftPhoneBtn);
		rightPhoneBtn = new BasicArrowButton(SwingConstants.EAST);
		//rightBtn.addActionListener(new RightListener());
		curPhonesPanel.add(rightPhoneBtn);
		p.add(curPhonesPanel, BorderLayout.CENTER);
		
		JPanel updatePhonesPanel = new JPanel();
		JButton delPhonesBtn = new JButton("Delete");
		//delPhonesBtn.addActionListener(new RightListener());
		updatePhonesPanel.add(delPhonesBtn);
		JButton editPhoneBtn = new JButton("Edit");
		//editPhoneBtn.addActionListener(new RightListener());
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
		//addPhoneBtn.addActionListener(new addPhoneListener());
		newEmailPanel.add(addPhoneBtn);
		p.add(newEmailPanel, BorderLayout.PAGE_START);
		
		JPanel curEmailsPanel = new JPanel();
		curEmailsPanel.add(new JLabel("Current: "));
		curEmailTF = new JTextField(12);
		curEmailsPanel.add(curEmailTF);
		leftEmailBtn = new BasicArrowButton(SwingConstants.WEST);
		//leftBtn.addActionListener(new LeftListener());
		curEmailsPanel.add(leftEmailBtn);
		rightEmailBtn = new BasicArrowButton(SwingConstants.EAST);
		//rightBtn.addActionListener(new RightListener());
		curEmailsPanel.add(rightEmailBtn);
		p.add(curEmailsPanel, BorderLayout.CENTER);
		
		JPanel updateEmailsPanel = new JPanel();
		JButton delPhonesBtn = new JButton("Delete");
		//delPhonesBtn.addActionListener(new RightListener());
		updateEmailsPanel.add(delPhonesBtn);
		JButton editPhoneBtn = new JButton("Edit");
		//editPhoneBtn.addActionListener(new RightListener());
		updateEmailsPanel.add(editPhoneBtn);
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
			int isClubMember = clubBool.compareTo(false);

			if (first.trim().equals("") || last.trim().equals("")) { // Don't allow blank names (NOT NULL)
				JOptionPane.showMessageDialog(null, "Name cannot be blank!");
				return;
			}
			
			// Do different actions depending on which button was pressed
			String buttonText = ((JButton)e.getSource()).getText();
			try {
				// If the Add button was pressed...
				if (buttonText.equals("Add")) {
					// Add a person with the name to the database
					stmt = conn.prepareStatement("INSERT INTO CUSTOMER_DATA(First, Last, Street, City, State, Zip, IsClubMember) VALUES(?,?,?,?,?,?,?)");
					stmt.setString(1, first);
					stmt.setString(2, last);
					stmt.setString(3, street);
					stmt.setString(4, city);
					stmt.setString(5, state);
					stmt.setString(6, zip);
					stmt.setInt(7, isClubMember);
					stmt.executeUpdate();
				
				// If the Find button was pressed...
				} else if (buttonText.equals("Find")) { 
					/*if (findPerson(name)) {
						findPhones();
					}*/

				// If the Delete button was pressed...
				} else if (buttonText.equals("Delete")) {
					if (!curPerson.equals("")) {
						//deletePerson();
					} else {
						JOptionPane.showMessageDialog(null, "Find a person first!");
					}
					
				// If the Edit button was pressed...
				} else if (buttonText.equals("Edit")) {
					
				}
			} catch (SQLException e1) { // Handle Errors
				e1.printStackTrace();
				JOptionPane.showMessageDialog(null, "Database Error!");
			}
		}
	}

/*	// Handles the buttons associated with the Phone field
	private class PhoneListener implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			String newphone = newPhoneTF.getText(); // Get the Phone

			if (newphone.trim().equals("") || newphone.equals("")) { // Don't allow blank entries
				JOptionPane.showMessageDialog(null, "Phone cannot be blank!");
				return;
			} else if (!newphone.matches("\\d{3}-\\d{3}-\\d{4}")) { // Make sure a valid phone number was entered
				JOptionPane.showMessageDialog(null, "Phone number must be in valid format (xxx-xxx-xxxx)!");
			}
			
			// Do different actions depending on which button was pressed
			String buttonText = ((JButton)e.getSource()).getText();
			try {
				// If the Add button was pressed...
				if (buttonText.equals("Add")) {
					if (!curPerson.equals("")) { // Make sure we have a person to add the phone to
						stmt.executeUpdate("INSERT INTO PHONE(Name, Phone, NumType) VALUES('"+curPerson+"','"+phone+"','"+type.getText()+"')");
						
						// Update the output with the newly added phone number
						findPerson(curPerson);
						findPhones();
					} else { // Error
						JOptionPane.showMessageDialog(null, "Find a person or phone first!");
					}
					
				// If the Find button was pressed...
				} else if (buttonText.equals("Find")) { 
					if (findNum(phone)) {
						findPhones();
					}

				// If the Delete button was pressed...
				} else if (buttonText.equals("Delete")) {
					if (!curPerson.equals("")) {
						deletePhone(phone);
					} else {
						JOptionPane.showMessageDialog(null, "Find a person or phone first!");
					}
				}
			} catch (SQLException e1) { // Handle Errors
				JOptionPane.showMessageDialog(null, "Database Error!");
			}
		}
	}

	// Handles the right arrow button
	private class LeftListener implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			try {
				if (nameSet.previous()) { // If another name exists...
					curPerson = nameSet.getString(1);
					dispTA.setText(curPerson + "\n"); // Output the name
					findPhones(); // Output the associated phone numbers
				}
				
				// Reset the other arrow button if it's disable
				if (!rightBtn.isEnabled()) {
					rightBtn.setEnabled(true);
				}
				
				// Disable the button if the end of the result set is reached
				if (nameSet.isFirst()) {
					leftBtn.setEnabled(false);
				}
			} catch (SQLException e1) {
				System.err.println("LeftListener Error");
			}
		}
	}

	// Handles the right arrow button
	private class RightListener implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			try {
				if (nameSet.next()) { // If another name exists...
					curPerson = nameSet.getString(1);
					dispTA.setText(curPerson + "\n"); // Output the name
					findPhones(); // Output the associated phone numbers

					// Reset the other arrow button if it's disable
					if (!leftBtn.isEnabled()) {
						leftBtn.setEnabled(true);
					}
					
					// Disable the button if the end of the result set is reached
					if (nameSet.isLast()) {
						rightBtn.setEnabled(false);
					}
				}
			} catch (SQLException e1) {
				System.err.println("RightListener Error");
			}
		}
	}
*/
}

