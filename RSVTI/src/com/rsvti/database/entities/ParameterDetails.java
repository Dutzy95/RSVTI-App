package com.rsvti.database.entities;

public class ParameterDetails {

	private String value;
	private String measuringUnit;
	
	public ParameterDetails(String value, String measuringUnit) {
		this.setValue(value);
		this.setMeasuringUnit(measuringUnit);
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}

	public String getMeasuringUnit() {
		return measuringUnit;
	}

	public void setMeasuringUnit(String measuringUnit) {
		this.measuringUnit = measuringUnit;
	}
}
