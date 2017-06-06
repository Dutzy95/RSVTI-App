package com.rsvti.database.entities;

import java.util.Date;
import java.util.HashMap;
import java.util.List;

public interface Rig {
	
	public void addParameter(String key, ParameterDetails details);
	
	public HashMap<String,ParameterDetails> getParameters();
	
	public String getType();
	
	public Date getDueDate();
	
	public String getRigName();
	
	public void setDueDate(Date dueDate);
	
	public void setEmployees(List<Employee> employees);
	
	public List<Employee> getEmployees();
}
