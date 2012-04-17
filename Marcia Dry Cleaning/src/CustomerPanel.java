
// Scott Hoelsema, Arthur Anderson, and Christopher Lenk
// CustomerPanel sets up the GUI for the "Customer" tab.

import java.awt.*;
import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.plaf.basic.BasicArrowButton;

public class CustomerPanel
{
	private static JPanel CustomerPanel = new JPanel();

	// GUI Variables
	private JTextField firstTF;
	private JTextField lastTF;
	private JTextField streetTF;
	private JTextField stateTF;
	private JTextField cityTF;
	private JTextField zipTF;
	private JCheckBox isMemberChkBx;
	private JTextField phoneTF;
	private JTextField emailTF;

	private static BasicArrowButton leftPhoneBtn;
	private static BasicArrowButton rightPhoneBtn;
	private static BasicArrowButton leftEmailBtn;
	private static BasicArrowButton rightEmailBtn;
	
	// Creates the add, find, delete, and edit buttons for the general customer info
	public static JPanel buildNameBtnPanel() {
		JPanel p = new JPanel();
		JButton addBtn = new JButton("Add");
		JButton findBtn = new JButton("Find");
		JButton delBtn = new JButton("Delete");
		JButton editBtn = new JButton("Edit");
		//addBtn.addActionListener(new NameListener()); // Add the "Name" listeners
		//findBtn.addActionListener(new NameListener());
		//delBtn.addActionListener(new NameListener());
		//editBtn.addActionListener(new NameListener());
		p.add(addBtn);
		p.add(findBtn);
		p.add(delBtn);
		p.add(editBtn);
		return p;
	}

	// Creates the text fields for the general customer info
	public JPanel buildNamePanel() {
		JPanel p = new JPanel();
		p.setLayout(new GridLayout(7, 1));
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
		p.add(lnamePanel);
		
		// State label and text field
		JPanel statePanel = new JPanel();
		statePanel.add(new JLabel("State: "));
		stateTF = new JTextField(15);
		statePanel.add(stateTF);
		p.add(statePanel);
		
		// City label and text field
		JPanel cityPanel = new JPanel();
		cityPanel.add(new JLabel("City: "));
		cityTF = new JTextField(15);
		cityPanel.add(cityTF);
		p.add(cityPanel);
		
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
	public static JPanel buildPhonePanel() {
		JPanel p = new JPanel();
		Border b = BorderFactory.createTitledBorder("Phone Number(s):");
		p.setBorder(b);
		p.setLayout(new BorderLayout());
		
		JPanel newPhonePanel = new JPanel();
		newPhonePanel.add(new JLabel("New: "));
		newPhonePanel.add(new JTextField(8));
		JButton addPhoneBtn = new JButton("Add");
		//addPhoneBtn.addActionListener(new addPhoneListener());
		newPhonePanel.add(addPhoneBtn);
		p.add(newPhonePanel, BorderLayout.PAGE_START);
		
		JPanel curPhonesPanel = new JPanel();
		curPhonesPanel.add(new JLabel("Current: "));
		curPhonesPanel.add(new JTextField(8));
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
	public static JPanel buildEmailPanel() {
		JPanel p = new JPanel();
		Border b = BorderFactory.createTitledBorder("Email(s):");
		p.setBorder(b);
		p.setLayout(new BorderLayout());
		
		JPanel newEmailPanel = new JPanel();
		newEmailPanel.add(new JLabel("New: "));
		newEmailPanel.add(new JTextField(12));
		JButton addPhoneBtn = new JButton("Add");
		//addPhoneBtn.addActionListener(new addPhoneListener());
		newEmailPanel.add(addPhoneBtn);
		p.add(newEmailPanel, BorderLayout.PAGE_START);
		
		JPanel curEmailsPanel = new JPanel();
		curEmailsPanel.add(new JLabel("Current: "));
		curEmailsPanel.add(new JTextField(12));
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

}

