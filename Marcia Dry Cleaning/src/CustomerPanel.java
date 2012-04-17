// Scott Hoelsema, Arthur Anderson, and Christopher Lenk
// CustomerPanel sets up the GUI for the "Customer" tab.

import java.awt.*;
import javax.swing.*;
import javax.swing.plaf.basic.BasicArrowButton;

public class CustomerPanel
{
	private static JPanel CustomerPanel = new JPanel();

	// GUI Variables
	private static JTextField firstTF;
	private static JTextField lastTF;
	private static JTextField phoneTF;
	private static JTextArea dispTA;
	private static BasicArrowButton leftBtn;
	private static BasicArrowButton rightBtn;
	
	public static JPanel buildNameBtnPanel() {
		JPanel p = new JPanel();
		JButton addBtn = new JButton("Add");
		JButton findBtn = new JButton("Find");
		JButton delBtn = new JButton("Delete");
		//addBtn.addActionListener(new NameListener()); // Add the "Name" listeners
		//findBtn.addActionListener(new NameListener());
		//delBtn.addActionListener(new NameListener());
		p.add(addBtn);
		p.add(findBtn);
		p.add(delBtn);
		return p;

	}

	public static JPanel buildPhoneBtnPanel() {
		JPanel p = new JPanel();
		JButton addBtn = new JButton("Add");
		JButton findBtn = new JButton("Find");
		JButton delBtn = new JButton("Delete");
		//addBtn.addActionListener(new PhoneListener()); // Add the "Phone" listeners
		//findBtn.addActionListener(new PhoneListener());
		//delBtn.addActionListener(new PhoneListener());
		p.add(addBtn);
		p.add(findBtn);
		p.add(delBtn);
		return p;

	}

	public static JPanel buildNamePanel() {
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
		
		p.add(buildNameBtnPanel());
		return p;
	}

	public static JPanel buildDisplayPanel() {
		JPanel tp = new JPanel();
		dispTA = new JTextArea(6, 30);
		dispTA.enableInputMethods(false);
		dispTA.setFont(new Font(Font.MONOSPACED, Font.PLAIN, 14));
		JScrollPane sp = new JScrollPane(dispTA);
		tp.add(sp);
		JPanel bp = new JPanel();
		leftBtn = new BasicArrowButton(SwingConstants.WEST);
		//leftBtn.addActionListener(new LeftListener());
		bp.add(leftBtn);
		rightBtn = new BasicArrowButton(SwingConstants.EAST);
		//rightBtn.addActionListener(new RightListener());
		bp.add(rightBtn);
		JPanel p = new JPanel();
		p.setLayout(new GridLayout(2, 1));
		p.add(tp);
		p.add(bp);
		leftBtn.setEnabled(false);
		rightBtn.setEnabled(false);
		return p;
	}
	
	public static JPanel buildCustomerPanel() 
	{
		JPanel tp = new JPanel();
		tp.setLayout(new GridLayout(2, 1));
		tp.add(buildNamePanel());

		CustomerPanel.setLayout(new GridLayout(2, 1));
		CustomerPanel.add(tp);
		CustomerPanel.add(buildDisplayPanel());
		
		return CustomerPanel;
	}

}
