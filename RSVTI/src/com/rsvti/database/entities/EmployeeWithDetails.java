package com.rsvti.database.entities;

import java.util.Date;

public class EmployeeWithDetails {
	
	private Employee employee;
	private String firmName;
	private String firmAddress;
	private String executiveName;
	private String firmId;
	private Date dueDate;
	
	public EmployeeWithDetails(Employee employee, Firm firm, Date dueDate) {
		this.setEmployee(employee);
		this.setFirmName(firm.getFirmName());
		this.setDueDate(dueDate);
		this.setFirmAddress(firm.getAddress());
		this.setExecutiveName(firm.getExecutiveName());
		this.setFirmId(firm.getId());
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

	public String getFirmId() {
		return firmId;
	}

	public void setFirmId(String firmId) {
		this.firmId = firmId;
	}
}
