package com.rsvti.database.entities;

import java.util.Date;

public class EmployeeWithDetails {
	
	private Employee employee;
	private String firmName;
	private String firmAddress;
	private String executiveName;
	private Date dueDate;
	
	public EmployeeWithDetails(Employee employee, String firmName, String firmAddress, String executiveName, Date dueDate) {
		this.setEmployee(employee);
		this.setFirmName(firmName);
		this.setDueDate(dueDate);
		this.setFirmAddress(firmAddress);
		this.setExecutiveName(executiveName);
	}

	public Employee getEmployee() {
		return employee;
	}

	public void setEmployee(Employee employee) {
		this.employee = employee;
	}

	public String getFirmName() {
		return firmName;
	}

	public void setFirmName(String firmName) {
		this.firmName = firmName;
	}

	public Date getDueDate() {
		return dueDate;
	}

	public void setDueDate(Date dueDate) {
		this.dueDate = dueDate;
	}

	public String getFirmAddress() {
		return firmAddress;
	}

	public void setFirmAddress(String firmAddress) {
		this.firmAddress = firmAddress;
	}

	public String getExecutiveName() {
		return executiveName;
	}

	public void setExecutiveName(String executiveName) {
		this.executiveName = executiveName;
	}
}
