package com.rsvti.database.entities;

import java.util.ArrayList;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;

public class Rig{

	private String rigName;
	private List<ParameterDetails> parameters;
	private Date revisionDate;
	private int authorizationExtension;
	private List<Employee> employees;
	private String type;
	
	public Rig(String rigName, Date dueDate, List<Employee> employees, String type) {
		this.setRigName(rigName);
		parameters = new ArrayList<ParameterDetails>();
		this.revisionDate = dueDate;
		this.setEmployees(employees);
		this.setType(type);
		this.setAuthorizationExtension(0);
	}
	
	public Rig(String rigName, List<ParameterDetails> parameters, Date revisionDate, List<Employee> employees, String type) {
		this.setRigName(rigName);
		this.parameters = parameters;
		this.setRevisionDate(revisionDate);
		this.setEmployees(employees);
		this.setType(type);
		this.setAuthorizationExtension(0);
	}
	
	/**
	 * Method that adds a parameter with the name that appears in the .xml file and its value.
	 * @param key parameter name
	 * @param value parameter value
	 */
	public void addParameter(ParameterDetails details) {
		parameters.add(details);
	}
	
	public List<ParameterDetails> getParameters() {
		return parameters;
	}
	
	public Date getRevisionDate() {
		return revisionDate;
	}

	public void setRevisionDate(Date dueDate) {
		this.revisionDate = dueDate;
	}

	public List<Employee> getEmployees() {
		return employees;
	}

	public void setEmployees(List<Employee> employees) {
		this.employees = employees;
	}

	public String getRigName() {
		return rigName;
	}

	public void setRigName(String rigName) {
		this.rigName = rigName;
	}

	public String getType() {
		return type;
	}
	
	public void setType(String type) {
		this.type = type;
	}

	public int getAuthorizationExtension() {
		return authorizationExtension;
	}

	public void setAuthorizationExtension(int authorizationExtension) {
		this.authorizationExtension = authorizationExtension;
	}
	
	public static Date getDueDate(Date revisionDate, int authorizationExtension) {
		GregorianCalendar calendar = new GregorianCalendar();
		calendar.setTime(revisionDate);
		calendar.add(GregorianCalendar.YEAR, authorizationExtension);
		if(calendar.get(GregorianCalendar.DAY_OF_WEEK) == GregorianCalendar.SATURDAY) {
			calendar.add(GregorianCalendar.DAY_OF_MONTH, 2);
		}
		if(calendar.get(GregorianCalendar.DAY_OF_WEEK) == GregorianCalendar.SUNDAY) {
			calendar.add(GregorianCalendar.DAY_OF_MONTH, 1);
		}
		return calendar.getTime();
	}
}
