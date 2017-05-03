package com.rsvti.database.entities;

import java.util.Date;
import java.util.HashMap;
import java.util.List;

public class PressureRig implements Rig {
	
	private HashMap<String,String> parameters;
	private Date dueDate;
	private List<Employee> employees;
	
	public PressureRig(Date dueDate, List<Employee> employees) {
		parameters = new HashMap<String,String>();
		this.dueDate = dueDate;
		this.setEmployees(employees);
	}
	
	public PressureRig(HashMap<String,String> parameters, Date dueDate, List<Employee> employees) {
		this.parameters = parameters;
		this.dueDate = dueDate;
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
		return "sub presiune";
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
}
