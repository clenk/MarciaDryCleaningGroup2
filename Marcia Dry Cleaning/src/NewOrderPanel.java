// Scott Hoelsema, Arthur Anderson, and Christopher Lenk
// NewOrderPanel sets up the GUI for the "New Order" tab.

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.plaf.basic.BasicArrowButton;

public class NewOrderPanel 
{
	private JPanel NewOrderPanel = new JPanel();
	private Connection conn;
	private JTextField firstTF;
	private JTextField lastTF;
	private JTextField phoneTF;
	private JTextArea dispTA;
	private BasicArrowButton leftBtn;
	private BasicArrowButton rightBtn;
	
	public NewOrderPanel(Connection conn)
	{
		this.conn = conn;
	}
	public JPanel buildNewOrderPanel()
	{
		//JPanel p = new JPanel();
		Border panelBorder = BorderFactory.createTitledBorder("New Order Stuff");
		NewOrderPanel.setBorder(panelBorder);
		NewOrderPanel.setLayout(new GridLayout(3, 1));
		NewOrderPanel.add(buildNorthPanel());
		NewOrderPanel.add(buildDisplayPanel());
		NewOrderPanel.add(buildSouthPanel());
		
		
		//NewOrderPanel.add(p);
		
		
		return NewOrderPanel;
	}
	public JPanel buildDisplayPanel() {
		
		
		JPanel tp = new JPanel();
		dispTA = new JTextArea(4, 36);
		dispTA.enableInputMethods(false);
		dispTA.setEditable(false);
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
		//leftBtn.setEnabled(false); if there are more to the left
		//rightBtn.setEnabled(false); if there are more to the right
		return p;
		
		/*
		JPanel p = new JPanel();
		String[] data = {"hello", "world", "I", "AM", "AWESOME"};
		JList list = new JList(data); //data has type String[]
		list.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		list.setLayoutOrientation(JList.VERTICAL);
		list.setVisibleRowCount(-1);
		JScrollPane listScroller = new JScrollPane(list);
		listScroller.setPreferredSize(new Dimension(250, 80));
		p.add(listScroller);
		return p;
		*/
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
			// TODO Auto-generated catch block
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
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return servicesArray;
	}
	public JPanel lilWestPanel() {
		String[] data = getServices();
		JPanel wp = new JPanel(); 
		//wp.add(new JLabel("Object: "));
		JTextField objectName = new JTextField(15);
		
		JPanel objectInfo = new JPanel();
		objectInfo.add(new JLabel("Object: "), BorderLayout.WEST);
		objectInfo.add(objectName, BorderLayout.EAST);
		
		JList list = new JList(data); //data has type String[]
		list.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		list.setLayoutOrientation(JList.VERTICAL);
		list.setVisibleRowCount(-1);
		JScrollPane listScroller = new JScrollPane(list);
		//listScroller.setPreferredSize(new Dimension(10, 10));
		//listScroller.setSize(10, 5);
		wp.setLayout(new GridLayout(2, 1));
		wp.add(objectInfo);
		wp.add(listScroller/*, BorderLayout.SOUTH*/);
		//wp.add(objectName/*, BorderLayout.NORTH*/);
		
		
		return wp;
	}
	public JPanel lilEastPanel() {
		JPanel ep = new JPanel();
		JButton add = new JButton("Add");
		JTextArea receipt = new JTextArea(6, 15);
		receipt.enableInputMethods(false);
		receipt.setEditable(false);
		receipt.setLineWrap(true);
		ep.add(add, BorderLayout.WEST);
		ep.add(receipt, BorderLayout.EAST);
		return ep;
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
		//findBtn.addActionListener(new NameFindListener());
		p.add(findBtn);
		return p;
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
		//p.add(buildNameBtnPanel());
		return p;
	}
}
