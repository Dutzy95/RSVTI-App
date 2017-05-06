package com.rsvti.ui;

import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Collections;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JTextField;

import com.rsvti.database.entities.Administrator;
import com.rsvti.database.entities.Employee;
import com.rsvti.database.entities.EmployeeAuthorization;
import com.rsvti.database.entities.Firm;
import com.rsvti.database.entities.LiftingRig;
import com.rsvti.database.entities.Rig;
import com.rsvti.database.services.DBServices;

public class Forms {
	
	public static void entryForm() {
		JFrame frame = new JFrame("RSVTI");
		
		JLabel idCodeLabel = new JLabel("Serie");
		JLabel idNumberLabel = new JLabel("Numar");
		JLabel firstNameLabel = new JLabel("Nume");
		JLabel lastNameLabel = new JLabel("Prenume");
		JLabel phoneNumberLabel = new JLabel("Telefon");
		
		JTextField idCodeTextField = new JTextField();
		JTextField idNumberTextField = new JTextField();
		JTextField firstNameTextField = new JTextField();
		JTextField lastNameTextField = new JTextField();
		JTextField phoneNumberTextField = new JTextField();
		
		frame.setLayout(new FlowLayout(FlowLayout.LEFT));
		
		Dimension dim = new Dimension(300,20);
		
		idCodeTextField.setPreferredSize(dim);
		idNumberTextField.setPreferredSize(dim);
		firstNameTextField.setPreferredSize(dim);
		lastNameTextField.setPreferredSize(dim);
		phoneNumberTextField.setPreferredSize(dim);
		
		frame.add(idCodeLabel);
		frame.add(idCodeTextField);
		frame.add(idNumberLabel);
		frame.add(idNumberTextField);
		frame.add(firstNameLabel);
		frame.add(firstNameTextField);
		frame.add(lastNameLabel);
		frame.add(lastNameTextField);
		frame.add(phoneNumberLabel);
		frame.add(phoneNumberTextField);
		
		JButton button = new JButton("Submit");
		
		button.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				Calendar date = Calendar.getInstance();
				date.set(2000, 4, 12);
				
				Employee employee1 = new Employee("FirstName1", "LastName1", "CT", "123456", "195042033495", 
						new EmployeeAuthorization("123948273", date.getTime(), date.getTime()), "stivuitorist");
				date.set(2020, 4, 6);
				Employee employee2 = new Employee("FirstName2", "LastName2", "BH", "098742", "240928735387", 
						new EmployeeAuthorization("56732894", date.getTime(), date.getTime()), "macaragist");
				
				Rig liftingRig1 = new LiftingRig(date.getTime(), Arrays.asList(employee1,employee2));
				
				DBServices.updateEntry(new Firm("ABC123", "uroi1273", "Str.Oituz, Nr.7", "012398423", "238120948", 
						"email@domain.com", "Gigi Bank", "RO34 2134 4366 3456 4568 8457", 
						new Administrator("Ion", "Ionescu", "AR", "123678", "4128309478"), Collections.singletonList(liftingRig1)),
						new Firm("ABC123", "uroi1273", "Str.Oituz, Nr.7", "012398423", "238120948", 
								"email@domain.com", "Gigi Bank", "RO34 2134 4366 3456 4568 8457", 
								new Administrator(idCodeTextField.getText(), idNumberTextField.getText(),
										firstNameTextField.getText(), lastNameTextField.getText(),
										phoneNumberTextField.getText()), Collections.singletonList(liftingRig1))
						);			
			}
		});
		
		frame.add(button);
		
		frame.setSize(400, 300);
		frame.setLocation(100, 100);
		frame.setVisible(true);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}
}
