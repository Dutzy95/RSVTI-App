package com.rsvti.database.entities;

import java.util.Date;

import com.rsvti.common.Constants;
import com.rsvti.common.Utils;

public class Valve {
	
	private Date revisionDate;
	private String registrationNumber;
	private boolean notExtended;

	public Valve(Date revisionDate, String registrationNumber, boolean notExtended) {
		this.setRevisionDate(revisionDate);
		this.setRegistrationNumber(registrationNumber);
		this.setNotExtended(notExtended);
	}

	public Date getDueDate() {
		return Utils.getCalculatedDueDate(revisionDate, notExtended ? 0 : 1);
	}

	public void setRevisionDate(Date dueDate) {
		this.revisionDate = dueDate;
	}
	
	public Date getRevisionDate() {
		return revisionDate;
	}

	public String getRegistrationNumber() {
		return registrationNumber;
	}

	public void setRegistrationNumber(String registrationNumber) {
		this.registrationNumber = registrationNumber;
	}

	public boolean isNotExtended() {
		return notExtended;
	}

	public void setNotExtended(boolean notExtended) {
		this.notExtended = notExtended;
	}
	
	public String toString() {
		return "dueDate: " + Constants.DEFAULT_DATE_FORMATTER.format(revisionDate) +
				"\nregistrationNumber: " + registrationNumber +
				"\nnotExtended: " + notExtended;
	}
}
