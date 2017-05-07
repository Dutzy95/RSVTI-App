package com.rsvti.database.entities;

import java.util.Date;

public class EmployeeAuthorization {
	
	private String authorizationNumber;
	private Date obtainingDate;
	private Date dueDate;
	
	public EmployeeAuthorization(String authorizationNumber, Date obtainigDate, Date dueDate) {
		this.setAuthorizationNumber(authorizationNumber);
		this.setObtainingDate(obtainigDate);
		this.setDueDate(dueDate);
	}

	public String getAuthorizationNumber() {
		return authorizationNumber;
	}

	public void setAuthorizationNumber(String authorizationNumber) {
		this.authorizationNumber = authorizationNumber;
	}

	public Date getObtainingDate() {
		return obtainingDate;
	}

	public void setObtainingDate(Date obtainingDate) {
		this.obtainingDate = obtainingDate;
	}

	public Date getDueDate() {
		return dueDate;
	}

	public void setDueDate(Date dueDate) {
		this.dueDate = dueDate;
	}
}