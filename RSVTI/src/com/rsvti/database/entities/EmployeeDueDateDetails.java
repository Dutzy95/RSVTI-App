package com.rsvti.database.entities;

import java.util.Date;

public class EmployeeDueDateDetails {
	
	private Employee employee;
	private String firmName;
	private String firmAddress;
	private Date dueDate;
	
	public EmployeeDueDateDetails(Employee employee, String firmName, String firmAddress, Date dueDate) {
		this.setEmployee(employee);
		this.setFirmName(firmName);
		this.setDueDate(dueDate);
		this.setFirmAddress(firmAddress);
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
}
