// Scott Hoelsema, Arthur Anderson, and Christopher Lenk
// ServicesPanel sets up the GUI for the "Services" tab.

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.GridLayout;

import javax.swing.*;
import javax.swing.border.Border;

public class ServicesPanel extends JFrame
{
	private static JPanel ServicesPanel = new JPanel(new BorderLayout(3, 50));
	private static JPanel SelectionPanel = new JPanel();
	private static JPanel DisplayPanel = new JPanel();
	private static JPanel EditPanel = new JPanel(new GridLayout(3, 3, 10, 10));
	private static JPanel BlankPanel = new JPanel();
	
	public static JPanel buildServicesPanel()
	{
		ServicesPanel.add(BlankPanel, BorderLayout.NORTH);
		
		// Service Selection
		Border centerBorder = BorderFactory.createTitledBorder("Select a Service:");
		SelectionPanel.setBorder(centerBorder);
		JList availableServices = new JList();
		String services[] = new String[3];
		services[0] = "Wash";
		services[1] = "Dry Clean";
		services[2] = "Press";
		availableServices.setListData(services);
		SelectionPanel.add(availableServices);
		ServicesPanel.add(SelectionPanel, BorderLayout.CENTER);
		
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
		JLabel newDescriptionTag = new JLabel("New Description:");
		JLabel newPriceTag = new JLabel("New Price:");
		JLabel newTimeRequiredTag = new JLabel("New Time Required");
		JTextField newDescription = new JTextField(15);
		JTextField newPrice = new JTextField("Price in format 0.00", 15);
		JTextField newTimeRequired = new JTextField("Time in minutes", 15);
		JButton descriptionSubmit = new JButton("Submit");
		JButton priceSubmit = new JButton("Submit");
		JButton timeRequiredSubmit = new JButton("Submit");
		EditPanel.add(newDescriptionTag);
		EditPanel.add(newDescription);
		EditPanel.add(descriptionSubmit);
		EditPanel.add(newPriceTag);
		EditPanel.add(newPrice);
		EditPanel.add(priceSubmit);
		EditPanel.add(newTimeRequiredTag);
		EditPanel.add(newTimeRequired);
		EditPanel.add(timeRequiredSubmit);
		ServicesPanel.add(EditPanel, BorderLayout.SOUTH);
		
		return ServicesPanel;
	}
}
