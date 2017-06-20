package com.rsvti.database.entities;

import java.util.Date;

public class EmployeeDueDateDetails {
	
	private Employee employee;
	private Rig rig;
	private String firmName;
	private String firmAddress;
	private Date dueDate;
	
	public EmployeeDueDateDetails(Employee employee, Rig rig, String firmName, String firmAddress, Date dueDate) {
		this.setEmployee(employee);
		this.setRig(rig);
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

	public Rig getRig() {
		return rig;
	}

	public void setRig(Rig rig) {
		this.rig = rig;
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
