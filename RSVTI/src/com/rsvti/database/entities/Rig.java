package com.rsvti.database.entities;

import java.util.ArrayList;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;

import com.rsvti.common.Constants;
import com.rsvti.database.services.DBServices;

public class Rig{

	private String rigName;
	private List<ParameterDetails> parameters;
	private Date revisionDate;
	private int authorizationExtension;
	private String productionNumber;
	private int productionYear;
	private String iscirRegistrationNumber;
	private Valve valve;

	private String type;
	
	public Rig(String rigName, Date revisionDate, String type, String productionNumber, int productionYear, String iscirRegistrationNumber, Valve valve) {
		this.setRigName(rigName);
		parameters = new ArrayList<ParameterDetails>();
		this.revisionDate = revisionDate;
		this.setType(type);
		this.setAuthorizationExtension(1);
		this.setProductionNumber(productionNumber);
		this.setProductionYear(productionYear);
		this.setIscirRegistrationNumber(iscirRegistrationNumber);
		this.setValve(valve);
	}
	
	public Rig(String rigName, Date revisionDate, String type, String productionNumber, int productionYear, String iscirRegistrationNumber) {
		this.setRigName(rigName);
		parameters = new ArrayList<ParameterDetails>();
		this.revisionDate = revisionDate;
		this.setType(type);
		this.setAuthorizationExtension(1);
		this.setProductionNumber(productionNumber);
		this.setProductionYear(productionYear);
		this.setIscirRegistrationNumber(iscirRegistrationNumber);
		this.setValve(null);
	}
	
	public Rig(String rigName, List<ParameterDetails> parameters, Date revisionDate, String type, String productionNumber, int productionYear, String iscirRegistrationNumber, Valve valve) {
		this.setRigName(rigName);
		this.parameters = parameters;
		this.setRevisionDate(revisionDate);
		this.setType(type);
		this.setAuthorizationExtension(1);
		this.setProductionNumber(productionNumber);
		this.setProductionYear(productionYear);
		this.setIscirRegistrationNumber(iscirRegistrationNumber);
		this.setValve(valve);
	}
	
	public Rig(String rigName, List<ParameterDetails> parameters, Date revisionDate, String type, String productionNumber, int productionYear, String iscirRegistrationNumber) {
		this.setRigName(rigName);
		this.parameters = parameters;
		this.setRevisionDate(revisionDate);
		this.setType(type);
		this.setAuthorizationExtension(1);
		this.setProductionNumber(productionNumber);
		this.setProductionYear(productionYear);
		this.setIscirRegistrationNumber(iscirRegistrationNumber);
		this.setValve(null);
	}
	
	/**
	 * Method that adds a parameter with the name that appears in the .xml file and its value.
	 * @param key parameter name
	 * @param value parameter value
	 */
	public void addParameter(ParameterDetails details) {
		parameters.add(details);
	}
	
	public List<ParameterDetails> getParameters() {
		return parameters;
	}
	
	public Date getRevisionDate() {
		return revisionDate;
	}

	public void setRevisionDate(Date dueDate) {
		this.revisionDate = dueDate;
	}

	public String getRigName() {
		return rigName;
	}

	public void setRigName(String rigName) {
		this.rigName = rigName;
	}

	public String getType() {
		return type;
	}
	
	public void setType(String type) {
		this.type = type;
	}

	public int getAuthorizationExtension() {
		return authorizationExtension;
	}

	public void setAuthorizationExtension(int authorizationExtension) {
		this.authorizationExtension = authorizationExtension;
	}
	
	public static Date getDueDate(Date revisionDate, int authorizationExtension) {
		GregorianCalendar calendar = new GregorianCalendar();
		calendar.setTime(revisionDate);
		calendar.add(GregorianCalendar.YEAR, authorizationExtension);
		if(authorizationExtension > 0) {
			calendar.add(GregorianCalendar.DAY_OF_MONTH, -1);
			List<Date> variableDates = DBServices.getVariableVacationDates();
			while(Constants.publicHolidays.contains(calendar.get(GregorianCalendar.DATE) + "-" + (calendar.get(GregorianCalendar.MONTH) + 1)) 
					|| calendar.get(GregorianCalendar.DAY_OF_WEEK) == GregorianCalendar.SUNDAY 
					|| calendar.get(GregorianCalendar.DAY_OF_WEEK) == GregorianCalendar.SATURDAY
					|| variableDates.contains(calendar.getTime())) {
				if(calendar.get(GregorianCalendar.DAY_OF_WEEK) == GregorianCalendar.SATURDAY
						|| Constants.publicHolidays.contains(calendar.get(GregorianCalendar.DATE) + "-" + (calendar.get(GregorianCalendar.MONTH) + 1))
						|| variableDates.contains(calendar.getTime())) {
					calendar.add(GregorianCalendar.DAY_OF_MONTH, -1);
				}
				if(calendar.get(GregorianCalendar.DAY_OF_WEEK) == GregorianCalendar.SUNDAY) {
					calendar.add(GregorianCalendar.DAY_OF_MONTH, -2);
				}
			}
		}
		return calendar.getTime();
	}
	
	public Date getDueDate() {
		GregorianCalendar calendar = new GregorianCalendar();
		calendar.setTime(revisionDate);
		calendar.add(GregorianCalendar.YEAR, authorizationExtension);
		if(authorizationExtension > 0) {
			calendar.add(GregorianCalendar.DAY_OF_MONTH, -1);
			List<Date> variableDates = DBServices.getVariableVacationDates();
			while(Constants.publicHolidays.contains(calendar.get(GregorianCalendar.DATE) + "-" + (calendar.get(GregorianCalendar.MONTH) + 1)) 
					|| calendar.get(GregorianCalendar.DAY_OF_WEEK) == GregorianCalendar.SUNDAY 
					|| calendar.get(GregorianCalendar.DAY_OF_WEEK) == GregorianCalendar.SATURDAY
					|| variableDates.contains(calendar.getTime())) {
				if(calendar.get(GregorianCalendar.DAY_OF_WEEK) == GregorianCalendar.SATURDAY
						|| Constants.publicHolidays.contains(calendar.get(GregorianCalendar.DATE) + "-" + (calendar.get(GregorianCalendar.MONTH) + 1))
						|| variableDates.contains(calendar.getTime())) {
					calendar.add(GregorianCalendar.DAY_OF_MONTH, -1);
				}
				if(calendar.get(GregorianCalendar.DAY_OF_WEEK) == GregorianCalendar.SUNDAY) {
					calendar.add(GregorianCalendar.DAY_OF_MONTH, -2);
				}
			}
		}
		return calendar.getTime();
	}
	
	@Override
	public boolean equals(Object o) {
		return o instanceof Rig &&
				((Rig) o).getRigName().equals(rigName) &&
				((Rig) o).getRevisionDate().equals(revisionDate) &&
				((Rig) o).getType().equals(type) &&
				((Rig) o).getAuthorizationExtension() == authorizationExtension;
	}

	public String getProductionNumber() {
		return productionNumber;
	}

	public void setProductionNumber(String productionNumber) {
		this.productionNumber = productionNumber;
	}

	public int getProductionYear() {
		return productionYear;
	}

	public void setProductionYear(int productionYear) {
		this.productionYear = productionYear;
	}

	public String getIscirRegistrationNumber() {
		return iscirRegistrationNumber;
	}

	public void setIscirRegistrationNumber(String iscirRegistrationNumber) {
		this.iscirRegistrationNumber = iscirRegistrationNumber;
	}

	public Valve getValve() {
		return valve;
	}

	public void setValve(Valve valve) {
		this.valve = valve;
	}
}
