package com.rsvti.database.services;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import com.rsvti.database.entities.Administrator;
import com.rsvti.database.entities.Employee;
import com.rsvti.database.entities.EmployeeAuthorization;
import com.rsvti.database.entities.Firm;
import com.rsvti.database.entities.ParameterDetails;
import com.rsvti.database.entities.Rig;
import com.rsvti.database.entities.TestQuestion;
import com.rsvti.main.Constants;

public class EntityBuilder {

	public static Firm buildFirmFromXml(Node node) {
		ArrayList<String> firmParameterValues = new ArrayList<String>();
		for(int i = 0; i < 9; i++) {
			firmParameterValues.add(node.getChildNodes().item(i).getTextContent());
		}
		
		Node adminNode = node.getChildNodes().item(9);
		ArrayList<String> adminParameterValues = new ArrayList<String>();
		
		for(int j = 0; j < adminNode.getChildNodes().getLength(); j++) {
			adminParameterValues.add(adminNode.getChildNodes().item(j).getTextContent());
		}
		
		List<Rig> rigs = new ArrayList<Rig>();
		
		for(int k = 10; k < node.getChildNodes().getLength(); k++) {
			Node rigNode = node.getChildNodes().item(k);
			List<ParameterDetails> parameters = new ArrayList<ParameterDetails>();
			
			String rigName = rigNode.getChildNodes().item(0).getTextContent();
			
			SimpleDateFormat format = new SimpleDateFormat(Constants.DATE_FORMAT);
			Date revisionDate = new Date();
			try {
				revisionDate = format.parse(rigNode.getChildNodes().item(1).getTextContent());
			} catch(ParseException pe) {
				pe.printStackTrace();
			}
			
			int authorizationExtension = Integer.parseInt(rigNode.getChildNodes().item(2).getTextContent());
			
			ArrayList<Employee> employees = new ArrayList<Employee>();
			String type = "";
			
			for(int l = 3; l < rigNode.getChildNodes().getLength(); l++) {
				type = rigNode.getAttributes().getNamedItem("type").getTextContent();
				
				if(rigNode.getChildNodes().item(l).getChildNodes().getLength() <= 1) {
						parameters.add(new ParameterDetails(rigNode.getChildNodes().item(l).getNodeName(), rigNode.getChildNodes().item(l).getTextContent(), rigNode.getChildNodes().item(l).getAttributes().getNamedItem("mUnit").getTextContent()));
				} else {
					Node employeeNode = rigNode.getChildNodes().item(l);
					ArrayList<String> employeeParameters = new ArrayList<String>();
					for(int m = 0; m < employeeNode.getChildNodes().getLength()-1; m++) {
						employeeParameters.add(employeeNode.getChildNodes().item(m).getTextContent());
					}
					
					Node employeeAuthorizationNode = employeeNode.getChildNodes().item(employeeNode.getChildNodes().getLength()-1);
					ArrayList<String> employeeAuthorsationParameters = new ArrayList<String>();
					for(int n = 0; n < employeeAuthorizationNode.getChildNodes().getLength(); n++) {
						employeeAuthorsationParameters.add(employeeAuthorizationNode.getChildNodes().item(n).getTextContent());
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
			
			Rig rig = new Rig(rigName, parameters, revisionDate, employees, type);
			rig.setAuthorizationExtension(authorizationExtension);
			rigs.add(rig);
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
				firmParameterValues.get(8),
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
	
	public static Rig buildRigFromXml(Node node) {
		List<ParameterDetails> parameters = new ArrayList<ParameterDetails>();
		
		String rigName = node.getChildNodes().item(0).getTextContent();
		
		SimpleDateFormat format = new SimpleDateFormat(Constants.DATE_FORMAT);
		Date dueDate = new Date();
		try {
			dueDate = format.parse(node.getChildNodes().item(1).getTextContent());
		} catch(ParseException pe) {
			pe.printStackTrace();
		}
		
		ArrayList<Employee> employees = new ArrayList<Employee>();
		
		int authorizationExtension = Integer.parseInt(node.getChildNodes().item(2).getTextContent());
		
		String type = "";
		for(int i = 3; i < node.getChildNodes().getLength(); i++) {
			
			if(node.getChildNodes().item(i).getChildNodes().getLength() <= 1) {
					parameters.add(new ParameterDetails(node.getChildNodes().item(i).getNodeName(), node.getChildNodes().item(i).getTextContent(),node.getChildNodes().item(i).getAttributes().getNamedItem("mUnit").getTextContent()));
			} else {
				Node employeeNode = node.getChildNodes().item(i);
				ArrayList<String> employeeParameters = new ArrayList<String>();
				for(int j = 0; j < employeeNode.getChildNodes().getLength()-1; j++) {
					employeeParameters.add(employeeNode.getChildNodes().item(j).getTextContent());
				}
				
				Node employeeAuthorizationNode = employeeNode.getChildNodes().item(employeeNode.getChildNodes().getLength()-1);
				ArrayList<String> employeeAuthorsationParameters = new ArrayList<String>();
				for(int k = 0; k < employeeAuthorizationNode.getChildNodes().getLength(); k++) {
					employeeAuthorsationParameters.add(employeeAuthorizationNode.getChildNodes().item(k).getTextContent());
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
		Rig rig = new Rig(rigName, parameters, dueDate, employees, type);
		rig.setAuthorizationExtension(authorizationExtension);
		return rig;
	}
	
	public static Employee buildEmployeeFromXml(Node node) {
		ArrayList<String> employeeParameters = new ArrayList<String>();
		SimpleDateFormat format = new SimpleDateFormat(Constants.DATE_FORMAT);
		
		for(int i = 0; i < node.getChildNodes().getLength()-1; i++) {
			employeeParameters.add(node.getChildNodes().item(i).getTextContent());
		}
		
		Node employeeAuthorizationNode = node.getChildNodes().item(node.getChildNodes().getLength()-1);
		ArrayList<String> employeeAuthorsationParameters = new ArrayList<String>();
		for(int j = 0; j < employeeAuthorizationNode.getChildNodes().getLength(); j++) {
			employeeAuthorsationParameters.add(employeeAuthorizationNode.getChildNodes().item(j).getTextContent());
		}
		try {
			return new Employee(
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
					node.getAttributes().getNamedItem("title").getTextContent()
					);
		} catch(ParseException pe) {
			pe.printStackTrace();
			return null;
		}
	}
	
	public static List<Firm> buildFirmListFormXml(NodeList nodeList) {
		List<Firm> firms = new ArrayList<Firm>();
		for(int i = 0; i < nodeList.getLength(); i++) {
			firms.add(buildFirmFromXml(nodeList.item(i)));
		}
		return firms;
	}
	
	public static List<Rig> buildRigListFromXml(NodeList nodeList) {
		List<Rig> rigs = new ArrayList<Rig>();
		for(int i = 0; i < nodeList.getLength(); i++) {
			rigs.add(buildRigFromXml(nodeList.item(i)));
		}
		return rigs;
	}
	
	public static List<Employee> buildEmployeeListFromXml(NodeList nodeList) {
		List<Employee> employees = new ArrayList<Employee>();
		for(int i = 0; i < nodeList.getLength(); i++) {
			employees.add(buildEmployeeFromXml(nodeList.item(i)));
		}
		return employees;
	}
	
	public static TestQuestion buildTestQuestionFromXml(Node node) {
		String text = node.getFirstChild().getTextContent();
		List<String> answers = new ArrayList<String>();
		
		String type = node.getAttributes().getNamedItem("type").getTextContent();
		
		for(int i = 1; i < node.getChildNodes().getLength(); i++) {
			answers.add(node.getChildNodes().item(i).getTextContent());
		}
		
		return new TestQuestion(text, answers, type);
	}
}
