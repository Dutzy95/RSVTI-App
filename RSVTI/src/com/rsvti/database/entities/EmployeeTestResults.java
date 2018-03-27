package com.rsvti.database.entities;

import java.util.ArrayList;
import java.util.List;

public class EmployeeTestResults {

	private String employeeName;
	private String theoryResult;
	private String practiseResult;
	private String personalIdentificationNumber;
	
	public EmployeeTestResults(String employeeName, String personalIdentificationNumber, String theoryResult, String practiseResult) {
		this.setEmployeeName(employeeName);
		this.setTheoryResult(theoryResult);
		this.setPractiseResult(practiseResult);
		this.setPersonalIdentificationNumber(personalIdentificationNumber);
	}

	public String getEmployeeName() {
		return employeeName;
	}

	public void setEmployeeName(String employeeName) {
		this.employeeName = employeeName;
	}

	public String getTheoryResult() {
		return theoryResult;
	}

	public void setTheoryResult(String theoryResult) {
		this.theoryResult = theoryResult;
	}

	public String getPractiseResult() {
		return practiseResult;
	}

	public void setPractiseResult(String practiseResult) {
		this.practiseResult = practiseResult;
	}
	
	public static List<EmployeeTestResults> getEmployeeTestResults(List<Employee> employees) {
		List<EmployeeTestResults> tmp = new ArrayList<>();
		for(Employee index : employees) {
			tmp.add(new EmployeeTestResults(index.getLastName() + " " + index.getFirstName(), index.getPersonalIdentificationNumber(), "", ""));
		}
		return tmp;
	}

	public String getPersonalIdentificationNumber() {
		return personalIdentificationNumber;
	}

	public void setPersonalIdentificationNumber(String personalIdentificationNumber) {
		this.personalIdentificationNumber = personalIdentificationNumber;
	}
	
	public boolean isEmpty() {
		return employeeName.isEmpty() && personalIdentificationNumber.isEmpty() && practiseResult.isEmpty() && theoryResult.isEmpty();
	}
	
	public String toString() {
		return "employeeName: " + employeeName +
				"\ntheoryResult: " + theoryResult +
				"\npractiseResult: " + practiseResult +
				"\npersonalIdentificationNumber: " + personalIdentificationNumber;
	}
}
