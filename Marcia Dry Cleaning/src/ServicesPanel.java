// Scott Hoelsema, Arthur Anderson, and Christopher Lenk
// ServicesPanel sets up the GUI for the "Services" tab.

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Time;

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
	private JComboBox availableServices = new JComboBox();
	private JTextArea serviceInformation = new JTextArea(7, 30);
	private JTextField newDescription = new JTextField(15);
	private JTextField newPrice = new JTextField("Price in format 0.00", 15);
	private JTextField newTimeRequired = new JTextField("Time in minutes", 15);
	private JTextField editDescription = new JTextField(15);
	private JTextField editPrice = new JTextField("Price in format 0.00", 15);
	private JTextField editTimeRequired = new JTextField("Time in minutes", 15);
	private int selected = 0;
	
	public JPanel buildServicesPanel()
	{
		buildSelectionPanel(selected);
		buildServiceInformationPanel();
		buildEditPanel();
		buildNewServicePanel();
		return ServicesPanel;
	}
		
	public void buildServiceInformationPanel()
	{
		serviceInformation.setEditable(false);
		serviceInformation.setLineWrap(true);
		DisplayPanel.add(serviceInformation);
		Border eastBorder = BorderFactory.createTitledBorder("Service Information");
		DisplayPanel.setBorder(eastBorder);
		ServicesPanel.add(DisplayPanel, BorderLayout.EAST);
	}
		
		
	public void buildEditPanel()
	{
		Border southBorder = BorderFactory.createTitledBorder("Edit Service:");
		EditPanel.setBorder(southBorder);
		JLabel editDescriptionTag = new JLabel("Edit Description:");
		JLabel editPriceTag = new JLabel("Edit Price:");
		JLabel editTimeRequiredTag = new JLabel("Edit Time Required:");
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
		
		descriptionSubmit.addActionListener(new descriptionSubmitListener());
		priceSubmit.addActionListener(new priceSubmitListener());
		timeRequiredSubmit.addActionListener(new timeRequiredSubmitListener());
		
		ServicesPanel.add(EditPanel, BorderLayout.SOUTH);
		
	}
		
	public void buildNewServicePanel()
	{
		Border northBorder = BorderFactory.createTitledBorder("New Service:");
		NewPanel.setBorder(northBorder);
		JLabel newDescriptionTag = new JLabel("New Description:");
		JLabel newPriceTag = new JLabel("New Price:");
		JLabel newTimeRequiredTag = new JLabel("New Time Required:");
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
		
		newEntrySubmit.addActionListener(new newEntrySubmitListener());
	}
	
	public void buildSelectionPanel(int selected)
	{
		availableServices.removeAllItems();
		
		Border centerBorder = BorderFactory.createTitledBorder("Select a Service:");
		SelectionPanel.setBorder(centerBorder);
		try 
		{
			PreparedStatement IDLookup = LogIn.getConnection().prepareStatement("SELECT idService FROM service_data");
			ResultSet IDs = IDLookup.executeQuery();
			while(IDs.next())
			{
				PreparedStatement descriptionLookup = LogIn.getConnection().prepareStatement("SELECT ServiceDescription FROM service_data WHERE idService = ?");
				descriptionLookup.setInt(1, IDs.getInt(1));
				ResultSet activeDescription = descriptionLookup.executeQuery();
				activeDescription.next();
				availableServices.addItem(activeDescription.getString(1));
			}
			availableServices.addActionListener(new availableServicesListener());
			SelectionPanel.add(availableServices);
			if(selected==-1)
			{
				availableServices.setSelectedIndex(availableServices.getItemCount()-1);
			}
			else
			{
				availableServices.setSelectedIndex(selected);
			}
			ServicesPanel.add(SelectionPanel, BorderLayout.CENTER);
			
		} 
		catch (SQLException e) 
		{
			JOptionPane.showMessageDialog(null, "SQL input error");
			e.printStackTrace();
		}
	}
	
	// LISTENERS for the Services Panel
	
	private class availableServicesListener implements ActionListener 
	{
		public void actionPerformed(ActionEvent e) 
		{
			serviceInformation.setText("");
			int activeItem = availableServices.getSelectedIndex();
			String serviceDescription = "";
			String price = "";
			String timeRequired = "";
			try 
			{
				PreparedStatement infoLookup = LogIn.getConnection().prepareStatement("SELECT ServiceDescription, Price, TimeRequired FROM service_data WHERE idService = ?");
				infoLookup.setInt(1, activeItem+1);
				ResultSet serviceInfo = infoLookup.executeQuery();
				if(serviceInfo.next())
				{
					serviceDescription = serviceInfo.getString("ServiceDescription");
					price = serviceInfo.getString("Price");
					timeRequired = serviceInfo.getString("TimeRequired");
				}
				serviceInformation.append("Service Description: " + serviceDescription + "\nPrice: $" + price + "\nTime Required: " + timeRequired);
			} 
			catch (SQLException e1) 
			{
				JOptionPane.showMessageDialog(null, "SQL input error");
				e1.printStackTrace();
			}
		}

	}
	
	private class newEntrySubmitListener implements ActionListener 
	{
		public void actionPerformed(ActionEvent e) 
		{
			if(newDescription.getText().equals("") || newPrice.getText().equals("") || newTimeRequired.getText().equals(""))
			{
				JOptionPane.showMessageDialog(null, "Null data not accepted!");
				return;
			}
			else
			{
				double price;
				Time timeRequired;
				int hours;
				String stringMinutes;
				
				try
				{
					int time = Integer.parseInt(newTimeRequired.getText());
					hours = time/60;
					int minutes = time%60;
					if(minutes<10)
					{
						stringMinutes = "0" + minutes;
					}
					else
					{
						stringMinutes = Integer.toString(minutes);
					}
				}
				catch(NumberFormatException e1)
				{
					JOptionPane.showMessageDialog(null, "Invalid format for New Time Required! Enter an integer.");
					return;
				}
				try
				{
					price = Double.parseDouble(newPrice.getText());
				}
				catch(NumberFormatException e2)
				{
					JOptionPane.showMessageDialog(null, "Invalid format for New Price! Enter in the format \"0.00\".");
					return;
				}
				try 
				{
					PreparedStatement newServiceEntry = LogIn.getConnection().prepareStatement("INSERT INTO service_data (ServiceDescription, Price, TimeRequired) VALUES(?, ?, ?)");
					newServiceEntry.setString(1, newDescription.getText());
					newServiceEntry.setDouble(2, price);
					newServiceEntry.setTime(3, Time.valueOf(hours + ":" + stringMinutes + ":00"));
					newServiceEntry.executeUpdate();
					JOptionPane.showMessageDialog(null, "Successful addition!");
					buildSelectionPanel(-1);
				} 
				catch (SQLException e1) 
				{
					JOptionPane.showMessageDialog(null, "SQL input error");
					e1.printStackTrace();
				}
			}
		}
	}
	
	private class descriptionSubmitListener implements ActionListener 
	{
		public void actionPerformed(ActionEvent e) 
		{
			if(editDescription.getText().equals(""))
			{
				JOptionPane.showMessageDialog(null, "Null data not accepted!");
				return;
			}
			else
			{
				int activeItem = availableServices.getSelectedIndex();
				try {
					PreparedStatement updateServiceDescription = LogIn.getConnection().prepareStatement("UPDATE service_data SET ServiceDescription = ? WHERE idService = ?");
					updateServiceDescription.setString(1, editDescription.getText());
					updateServiceDescription.setInt(2, activeItem+1);
					updateServiceDescription.executeUpdate();
					JOptionPane.showMessageDialog(null, "Successful update!");
					buildSelectionPanel(activeItem);
				} 
				catch (SQLException e1) 
				{
					JOptionPane.showMessageDialog(null, "SQL input error");
					e1.printStackTrace();
				}
			}
		}
	}
	
	private class priceSubmitListener implements ActionListener 
	{
		public void actionPerformed(ActionEvent e) 
		{
			if(editPrice.getText().equals(""))
			{
				JOptionPane.showMessageDialog(null, "Null data not accepted!");
				return;
			}
			else
			{
				Double price;
				int activeItem = availableServices.getSelectedIndex();
				try
				{
					price = Double.parseDouble(editPrice.getText());
					PreparedStatement updatePrice = LogIn.getConnection().prepareStatement("UPDATE service_data SET Price = ? WHERE idService = ?");
					updatePrice.setDouble(1, price);
					updatePrice.setInt(2, activeItem+1);
					updatePrice.executeUpdate();
					JOptionPane.showMessageDialog(null, "Successful update!");
					buildSelectionPanel(activeItem);
				}
				catch(NumberFormatException e2)
				{
					JOptionPane.showMessageDialog(null, "Invalid format for Edit Price! Enter in the format \"0.00\".");
					return;
				} 
				catch (SQLException e3) 
				{
					JOptionPane.showMessageDialog(null,  "SQL input error");
					e3.printStackTrace();
				}
			}
		}
	}
	
	private class timeRequiredSubmitListener implements ActionListener 
	{
		public void actionPerformed(ActionEvent e) 
		{
			int hours;
			String stringMinutes;
			int activeItem = availableServices.getSelectedIndex();
			
			try
			{
				int time = Integer.parseInt(editTimeRequired.getText());
				hours = time/60;
				int minutes = time%60;
				if(minutes<10)
				{
					stringMinutes = "0" + minutes;
				}
				else
				{
					stringMinutes = Integer.toString(minutes);
				}
				PreparedStatement updateTimeRequired = LogIn.getConnection().prepareStatement("UPDATE service_data SET TimeRequired = ? WHERE idService = ?");
				updateTimeRequired.setTime(1, Time.valueOf(hours + ":" + stringMinutes + ":00"));
				updateTimeRequired.setInt(2, activeItem+1);
				updateTimeRequired.executeUpdate();
				JOptionPane.showMessageDialog(null, "Successful update!");
				buildSelectionPanel(activeItem);
			}
			catch(NumberFormatException e1)
			{
				JOptionPane.showMessageDialog(null, "Invalid format for New Time Required! Enter an integer.");
				return;
			} catch (SQLException e2) 
			{
				JOptionPane.showMessageDialog(null, "SQL input error");
				e2.printStackTrace();
			}
		}
	}
}
