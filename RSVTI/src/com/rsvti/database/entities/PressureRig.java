package com.rsvti.database.entities;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

public class PressureRig implements Rig {
	
	private String rigName;
	private List<ParameterDetails> parameters;
	private Date dueDate;
	private List<Employee> employees;
	
	public PressureRig(String rigName, Date dueDate, List<Employee> employees) {
		this.setRigName(rigName);
		parameters = new ArrayList<ParameterDetails>();
		this.dueDate = dueDate;
		this.setEmployees(employees);
	}
	
	public PressureRig(String rigName, List<ParameterDetails> parameters, Date dueDate, List<Employee> employees) {
		this.setRigName(rigName);
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
	public void addParameter(ParameterDetails details) {
		parameters.add(details);
	}
	
	@Override
	public List<ParameterDetails> getParameters() {
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

	public String getRigName() {
		return rigName;
	}

	public void setRigName(String rigName) {
		this.rigName = rigName;
	}
}
