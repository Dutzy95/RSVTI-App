package com.rsvti.database.entities;

import java.util.Date;

public class LoggedTest {

	private String employeeFirstName;
	private String employeeLastName;
	private String employeeTitle;
	private String firmName;
	private Date generationDateAndTime;
	
	public LoggedTest(String employeeFirstName, String employeeLastName, String employeeTitle, String firmName,	Date generationDateAndTime) {
		this.setEmployeeFirstName(employeeFirstName);
		this.setEmployeeLastName(employeeLastName);
		this.setEmployeeTitle(employeeTitle);
		this.setFirmName(firmName);
		this.setGenerationDateAndTime(generationDateAndTime);
	}

	public Date getGenerationDateAndTime() {
		return generationDateAndTime;
	}

	public void setGenerationDateAndTime(Date generationDateAndTime) {
		this.generationDateAndTime = generationDateAndTime;
	}

	public String getEmployeeFirstName() {
		return employeeFirstName;
	}

	public void setEmployeeFirstName(String employeeFirstName) {
		this.employeeFirstName = employeeFirstName;
	}

	public String getEmployeeLastName() {
		return employeeLastName;
	}

	public void setEmployeeLastName(String employeeLastName) {
		this.employeeLastName = employeeLastName;
	}

	public String getFirmName() {
		return firmName;
	}

	public void setFirmName(String firmName) {
		this.firmName = firmName;
	}

	public String getEmployeeTitle() {
		return employeeTitle;
	}

	public void setEmployeeTitle(String employeeTitle) {
		this.employeeTitle = employeeTitle;
	}
	
	@Override
	public boolean equals(Object o) {
		return o instanceof LoggedTest &&
				((LoggedTest) o).getEmployeeFirstName().equals(employeeFirstName) &&
				((LoggedTest) o).getEmployeeLastName().equals(employeeLastName) &&
				((LoggedTest) o).getEmployeeTitle().equals(employeeTitle) &&
				((LoggedTest) o).getFirmName().equals(firmName) &&
				((LoggedTest) o).getGenerationDateAndTime().equals(generationDateAndTime);
	}
}
