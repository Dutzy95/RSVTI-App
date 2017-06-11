package com.rsvti.database.entities;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class Rig{

	private String rigName;
	private List<ParameterDetails> parameters;
	private Date dueDate;
	private List<Employee> employees;
	private String type;
	
	public Rig(String rigName, Date dueDate, List<Employee> employees, String type) {
		this.setRigName(rigName);
		parameters = new ArrayList<ParameterDetails>();
		this.dueDate = dueDate;
		this.setEmployees(employees);
		this.setType(type);
	}
	
	public Rig(String rigName, List<ParameterDetails> parameters, Date dueDate, List<Employee> employees, String type) {
		this.setRigName(rigName);
		this.parameters = parameters;
		this.setDueDate(dueDate);
		this.setEmployees(employees);
		this.setType(type);
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
	
	public Date getDueDate() {
		return dueDate;
	}

	public void setDueDate(Date dueDate) {
		this.dueDate = dueDate;
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
}
