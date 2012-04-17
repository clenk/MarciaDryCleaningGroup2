// Scott Hoelsema, Arthur Anderson, and Christopher Lenk
// ServicesPanel sets up the GUI for the "Services" tab.

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.SQLException;

import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import java.sql.*;
import java.util.ArrayList;

public class ServicesPanel extends JFrame
{
	private JPanel ServicesPanel = new JPanel(new BorderLayout(3, 20));
	private JPanel SelectionPanel = new JPanel();
	private JPanel DisplayPanel = new JPanel();
	private JPanel EditPanel = new JPanel(new GridLayout(3, 3, 10, 10));
	private JPanel NewPanel = new JPanel(new GridLayout(3, 3, 10, 10));
	
	
	public ServicesPanel()
	{
		buildServicesPanel();
	}
	
	public JPanel buildServicesPanel()
	{
		// Service Selection
		Border centerBorder = BorderFactory.createTitledBorder("Select a Service:");
		SelectionPanel.setBorder(centerBorder);
		try 
		{
			ArrayList<String> descriptions = new ArrayList();
			PreparedStatement IDLookup = LogIn.getConnection().prepareStatement("SELECT idService FROM service_data");
			ResultSet IDs = IDLookup.executeQuery();
			while(IDs.next())
			{
				PreparedStatement descriptionLookup = LogIn.getConnection().prepareStatement("SELECT ServiceDescription FROM service_data WHERE idService = ?");
				descriptionLookup.setInt(1, IDs.getInt(1));
				ResultSet activeDescription = descriptionLookup.executeQuery();
				activeDescription.next();
				descriptions.add(activeDescription.getString(1));
			}
			String displayDescriptions[] = new String[descriptions.size()];
			for(int i = 0; i < descriptions.size(); i++)
			{
				displayDescriptions[i] = descriptions.get(i);
			}
			JComboBox availableServices = new JComboBox(displayDescriptions);
			SelectionPanel.add(availableServices);
			ServicesPanel.add(SelectionPanel, BorderLayout.CENTER);
			availableServices.addActionListener(new availableServicesListener());
		} 
		catch (SQLException e) 
		{
			JOptionPane.showMessageDialog(null, "SQL input error");
			e.printStackTrace();
		}
		
		// Service Information Display
		JTextArea serviceInformation = new JTextArea(7, 30);
		serviceInformation.setEditable(false);
		serviceInformation.setLineWrap(true);
		DisplayPanel.add(serviceInformation);
		Border eastBorder = BorderFactory.createTitledBorder("Service Information");
		DisplayPanel.setBorder(eastBorder);
		ServicesPanel.add(DisplayPanel, BorderLayout.EAST);
		
		// Edit Service
		Border southBorder = BorderFactory.createTitledBorder("Edit Service:");
		EditPanel.setBorder(southBorder);
		JLabel editDescriptionTag = new JLabel("Edit Description:");
		JLabel editPriceTag = new JLabel("Edit Price:");
		JLabel editTimeRequiredTag = new JLabel("Edit Time Required:");
		JTextField editDescription = new JTextField(15);
		JTextField editPrice = new JTextField("Price in format 0.00", 15);
		JTextField editTimeRequired = new JTextField("Time in minutes", 15);
		JButton descriptionSubmit = new JButton("Submit");
		JButton priceSubmit = new JButton("Submit");
		JButton timeRequiredSubmit = new JButton("Submit");
		EditPanel.add(editDescriptionTag);
		EditPanel.add(editDescription);
		EditPanel.add(descriptionSubmit);
		EditPanel.add(editPriceTag);
		EditPanel.add(editPrice);
		EditPanel.add(priceSubmit);
		EditPanel.add(editTimeRequiredTag);
		EditPanel.add(editTimeRequired);
		EditPanel.add(timeRequiredSubmit);
		ServicesPanel.add(EditPanel, BorderLayout.SOUTH);
		
		// New Service
		Border northBorder = BorderFactory.createTitledBorder("New Service:");
		NewPanel.setBorder(northBorder);
		JLabel newDescriptionTag = new JLabel("New Description:");
		JLabel newPriceTag = new JLabel("New Price:");
		JLabel newTimeRequiredTag = new JLabel("New Time Required:");
		JTextField newDescription = new JTextField(15);
		JTextField newPrice = new JTextField("Price in format 0.00", 15);
		JTextField newTimeRequired = new JTextField("Time in minutes", 15);
		JButton newEntrySubmit = new JButton("Submit New Entry");
		NewPanel.add(newDescriptionTag);
		NewPanel.add(newDescription);
		NewPanel.add(new JLabel(""));
		NewPanel.add(newPriceTag);
		NewPanel.add(newPrice);
		NewPanel.add(new JLabel(""));
		NewPanel.add(newTimeRequiredTag);
		NewPanel.add(newTimeRequired);
		NewPanel.add(newEntrySubmit);
		ServicesPanel.add(NewPanel, BorderLayout.NORTH);
		
		return ServicesPanel;
	}
	
	private class availableServicesListener implements ActionListener 
	{
		public void actionPerformed(ActionEvent e) 
		{

		}

	}
}
