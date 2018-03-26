package com.rsvti.database.entities;

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

import com.rsvti.common.Utils;

public class Valve {
	
	private Date dueDate;
	private String registrationNumber;
	private boolean notExtended;

	public Valve(Date dueDate, String registrationNumber, boolean notExtended) {
		this.setDueDate(dueDate);
		this.setRegistrationNumber(registrationNumber);
		this.setNotExtended(notExtended);
	}

	public Date getDueDate(boolean notExtended) {
		Date currentDate = Calendar.getInstance().getTime();
		if(Utils.resetTimeForDate(dueDate).before(Utils.resetTimeForDate(currentDate)) || 
				Utils.resetTimeForDate(dueDate).equals(Utils.resetTimeForDate(currentDate))) {
			Calendar cal = new GregorianCalendar();
			cal.setTime(dueDate);
			cal.add(Calendar.YEAR, notExtended ? 0 : 1);
			return cal.getTime();
		} else {
			return dueDate;
		}
	}

	public void setDueDate(Date dueDate) {
		this.dueDate = dueDate;
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
	
}
