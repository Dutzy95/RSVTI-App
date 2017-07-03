package com.rsvti.database.services;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import com.rsvti.common.Constants;
import com.rsvti.database.entities.Administrator;
import com.rsvti.database.entities.Employee;
import com.rsvti.database.entities.EmployeeAuthorization;
import com.rsvti.database.entities.Firm;
import com.rsvti.database.entities.LoggedTest;
import com.rsvti.database.entities.ParameterDetails;
import com.rsvti.database.entities.Rig;
import com.rsvti.database.entities.TestQuestion;

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
		List<Employee> employees = new ArrayList<Employee>();
		SimpleDateFormat format = new SimpleDateFormat(DBServices.getDatePattern());
		
		for(int k = 10; k < node.getChildNodes().getLength(); k++) {
			Node rigEmployeeNode = node.getChildNodes().item(k);
			
			if(rigEmployeeNode.getNodeName().equals("instalatie")) {
				List<ParameterDetails> parameters = new ArrayList<ParameterDetails>();
				
				String rigName = rigEmployeeNode.getChildNodes().item(0).getTextContent();
				
				Date revisionDate = new Date(Long.parseLong(rigEmployeeNode.getChildNodes().item(1).getTextContent()));
				
				int authorizationExtension = Integer.parseInt(rigEmployeeNode.getChildNodes().item(2).getTextContent());
				
				String type = rigEmployeeNode.getAttributes().getNamedItem("type").getTextContent();
				
				for(int l = 3; l < rigEmployeeNode.getChildNodes().getLength(); l++) {
					parameters.add(new ParameterDetails(rigEmployeeNode.getChildNodes().item(l).getNodeName(), rigEmployeeNode.getChildNodes().item(l).getTextContent(), rigEmployeeNode.getChildNodes().item(l).getAttributes().getNamedItem("mUnit").getTextContent()));
				}
				
				Rig rig = new Rig(rigName, parameters, revisionDate, type);
				rig.setAuthorizationExtension(authorizationExtension);
				rigs.add(rig);
			} else {
				ArrayList<String> employeeParameters = new ArrayList<String>();
				for(int m = 0; m < rigEmployeeNode.getChildNodes().getLength()-1; m++) {
					employeeParameters.add(rigEmployeeNode.getChildNodes().item(m).getTextContent());
				}
				
				Node employeeAuthorizationNode = rigEmployeeNode.getChildNodes().item(rigEmployeeNode.getChildNodes().getLength()-1);
				ArrayList<String> employeeAuthorisationParameters = new ArrayList<String>();
				for(int n = 0; n < employeeAuthorizationNode.getChildNodes().getLength(); n++) {
					employeeAuthorisationParameters.add(employeeAuthorizationNode.getChildNodes().item(n).getTextContent());
				}
				
				employees.add(new Employee(
						employeeParameters.get(0),
						employeeParameters.get(1),
						employeeParameters.get(2),
						employeeParameters.get(3),
						employeeParameters.get(4),
						new EmployeeAuthorization(
								employeeAuthorisationParameters.get(0),
								new Date(Long.parseLong(employeeAuthorisationParameters.get(1))),
								new Date(Long.parseLong(employeeAuthorisationParameters.get(2)))
								),
						rigEmployeeNode.getAttributes().getNamedItem("title").getTextContent()
						));
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
				firmParameterValues.get(8),
				new Administrator(
						adminParameterValues.get(0),
						adminParameterValues.get(1),
						adminParameterValues.get(2),
						adminParameterValues.get(3),
						adminParameterValues.get(4)
						),
				rigs,
				employees
				);
	}
	
	public static Rig buildRigFromXml(Node node) {
		List<ParameterDetails> parameters = new ArrayList<ParameterDetails>();
		
		String rigName = node.getChildNodes().item(0).getTextContent();
		
		SimpleDateFormat format = new SimpleDateFormat(DBServices.getDatePattern());
		
		Date revisionDate = new Date(Long.parseLong(node.getChildNodes().item(1).getTextContent()));
		
		int authorizationExtension = Integer.parseInt(node.getChildNodes().item(2).getTextContent());
		
		String type = "";
		for(int i = 3; i < node.getChildNodes().getLength(); i++) {
			parameters.add(new ParameterDetails(node.getChildNodes().item(i).getNodeName(), node.getChildNodes().item(i).getTextContent(),node.getChildNodes().item(i).getAttributes().getNamedItem("mUnit").getTextContent()));
		}
		Rig rig = new Rig(rigName, parameters, revisionDate, type);
		rig.setAuthorizationExtension(authorizationExtension);
		return rig;
	}
	
	public static Employee buildEmployeeFromXml(Node node) {
		ArrayList<String> employeeParameters = new ArrayList<String>();
		SimpleDateFormat format = new SimpleDateFormat(DBServices.getDatePattern());
		
		for(int i = 0; i < node.getChildNodes().getLength()-1; i++) {
			employeeParameters.add(node.getChildNodes().item(i).getTextContent());
		}
		
		Node employeeAuthorizationNode = node.getChildNodes().item(node.getChildNodes().getLength()-1);
		ArrayList<String> employeeAuthorsationParameters = new ArrayList<String>();
		for(int j = 0; j < employeeAuthorizationNode.getChildNodes().getLength(); j++) {
			employeeAuthorsationParameters.add(employeeAuthorizationNode.getChildNodes().item(j).getTextContent());
		}
		
		return new Employee(
				employeeParameters.get(0),
				employeeParameters.get(1),
				employeeParameters.get(2),
				employeeParameters.get(3),
				employeeParameters.get(4),
				new EmployeeAuthorization(
						employeeAuthorsationParameters.get(0),
						new Date(Long.parseLong(employeeAuthorsationParameters.get(1))),
						new Date(Long.parseLong(employeeAuthorsationParameters.get(2)))
						),
				node.getAttributes().getNamedItem("title").getTextContent()
				);
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
	
	public static LoggedTest buildLoggedTestFormXml(Node node) {
		return new LoggedTest(
				node.getAttributes().getNamedItem("prenume_angajat").getTextContent(), 
				node.getAttributes().getNamedItem("nume_angajat").getTextContent(), 
				node.getAttributes().getNamedItem("titlu_angajat").getTextContent(), 
				node.getAttributes().getNamedItem("nume_firma").getTextContent(), 
				new Date(Long.parseLong(node.getAttributes().getNamedItem("data_si_ora_generarii").getTextContent()))
				);
	}
}
