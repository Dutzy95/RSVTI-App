package com.rsvti.database.services;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import org.w3c.dom.Node;

import com.rsvti.database.entities.Administrator;
import com.rsvti.database.entities.Employee;
import com.rsvti.database.entities.EmployeeAuthorization;
import com.rsvti.database.entities.Firm;
import com.rsvti.database.entities.LiftingRig;
import com.rsvti.database.entities.PressureRig;
import com.rsvti.database.entities.Rig;
import com.rsvti.main.Constants;

public class EntityBuilder {

	public static Firm buildFirmFromXml(Node node) {
		ArrayList<String> firmParameterValues = new ArrayList<String>();
		for(int i = 0; i <= 15; i++) {
			//To see why for loop is up to 15, de-comment next line and set for loop until node.getChildNodes().getLength(). It memorizes only the fields up to the administrator, which is on line 19. 
			//System.out.println(i + ": " + node.getChildNodes().item(i).getTextContent().replaceAll("\t", "\\\\t").replaceAll("\n", "\\\\n"));
			if(!node.getChildNodes().item(i).getTextContent().contains("\t") ||
					!node.getChildNodes().item(i).getTextContent().contains("\n")) {
				firmParameterValues.add(node.getChildNodes().item(i).getTextContent());
			}
		}
		
		Node adminNode = node.getChildNodes().item(17);
		ArrayList<String> adminParameterValues = new ArrayList<String>();
		
		for(int j = 0; j < adminNode.getChildNodes().getLength(); j++) {
			if(!node.getChildNodes().item(j).getTextContent().contains("\t") ||
					!node.getChildNodes().item(j).getTextContent().contains("\n")) {
				adminParameterValues.add(adminNode.getChildNodes().item(j).getTextContent());
			}
		}
		
		List<Rig> rigs = new ArrayList<Rig>();
		
		for(int k = 19; k < node.getChildNodes().getLength(); k+=2) {
			Node rigNode = node.getChildNodes().item(k);
			HashMap<String,String> parametersAndValues = new HashMap<String,String>();
			
			SimpleDateFormat format = new SimpleDateFormat(Constants.DATE_FORMAT);
			Date dueDate = new Date();
			try {
				dueDate = format.parse(rigNode.getChildNodes().item(1).getTextContent());
			} catch(ParseException pe) {
				pe.printStackTrace();
			}
			
			ArrayList<Employee> employees = new ArrayList<Employee>();
					
			for(int l = 3; l < rigNode.getChildNodes().getLength(); l++) {
				
				if(rigNode.getChildNodes().item(l).getChildNodes().getLength() <= 1) {
					if(!rigNode.getChildNodes().item(l).getTextContent().contains("\t") ||
							!rigNode.getChildNodes().item(l).getTextContent().contains("\n")) {
							parametersAndValues.put(rigNode.getChildNodes().item(l).getNodeName(), rigNode.getChildNodes().item(l).getTextContent());
					}
				} else {
					Node employeeNode = rigNode.getChildNodes().item(l);
					ArrayList<String> employeeParameters = new ArrayList<String>();
					for(int m = 0; m < employeeNode.getChildNodes().getLength()-2; m++) {
						if(!employeeNode.getChildNodes().item(m).getTextContent().contains("\t") ||
								!employeeNode.getChildNodes().item(m).getTextContent().contains("\n")) {
							employeeParameters.add(employeeNode.getChildNodes().item(m).getTextContent());
						}
					}
					
					Node employeeAuthorizationNode = employeeNode.getChildNodes().item(employeeNode.getChildNodes().getLength()-2);
					ArrayList<String> employeeAuthorsationParameters = new ArrayList<String>();
					for(int n = 0; n < employeeAuthorizationNode.getChildNodes().getLength(); n++) {
						if(!employeeAuthorizationNode.getChildNodes().item(n).getTextContent().contains("\t") ||
								!employeeAuthorizationNode.getChildNodes().item(n).getTextContent().contains("\n")) {
							employeeAuthorsationParameters.add(employeeAuthorizationNode.getChildNodes().item(n).getTextContent());
						}
					}
					try {
						employees.add(new Employee(
								employeeParameters.get(0),
								employeeParameters.get(1),
								employeeParameters.get(2),
								employeeParameters.get(3),
								employeeParameters.get(4),
								new EmployeeAuthorization(
										employeeAuthorsationParameters.get(0),
										format.parse(employeeAuthorsationParameters.get(1)),
										format.parse(employeeAuthorsationParameters.get(1))
										),
								employeeNode.getAttributes().getNamedItem("title").getTextContent()
								));
					} catch(ParseException pe) {
						pe.printStackTrace();
					}
				}
			}
			
			if(rigNode.getAttributes().getNamedItem("type").getTextContent().equals("de ridicat")) {
				rigs.add(new LiftingRig(parametersAndValues, dueDate, employees));
			} else {
				rigs.add(new PressureRig(parametersAndValues, dueDate, employees));
			}
		}
		
		return new Firm(
				firmParameterValues.get(0),
				firmParameterValues.get(1),
				firmParameterValues.get(2),
				firmParameterValues.get(3),
				firmParameterValues.get(4),
				firmParameterValues.get(5),
				firmParameterValues.get(6),
				firmParameterValues.get(7),
				new Administrator(
						adminParameterValues.get(0),
						adminParameterValues.get(1),
						adminParameterValues.get(2),
						adminParameterValues.get(3),
						adminParameterValues.get(4)
						),
				rigs
				);
	}
}
