package com.rsvti.database.entities;

import java.util.Date;
import java.util.HashMap;
import java.util.List;

public class LiftingRig implements Rig{

	private String rigName;
	private HashMap<String,String> parameters;
	private Date dueDate;
	private List<Employee> employees;
	
	public LiftingRig(String rigName, Date dueDate, List<Employee> employees) {
		this.setRigName(rigName);
		parameters = new HashMap<String,String>();
		this.dueDate = dueDate;
		this.setEmployees(employees);
	}
	
	public LiftingRig(String rigName, HashMap<String,String> parameters, Date dueDate, List<Employee> employees) {
		this.setRigName(rigName);
		this.parameters = parameters;
		this.setDueDate(dueDate);
		this.setEmployees(employees);
	}
	
	/**
	 * Method that adds a parameter with the name that appears in the .xml file and its value.
	 * @param key parameter name
	 * @param value parameter value
	 */
	@Override
	public void addParameter(String key, String value) {
		parameters.put(key,value);
	}
	
	@Override
	public HashMap<String,String> getParameters() {
		return parameters;
	}
	
	@Override
	public String getType() {
		return "de ridicat";
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
}
