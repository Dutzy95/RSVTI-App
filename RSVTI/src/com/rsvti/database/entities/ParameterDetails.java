package com.rsvti.database.entities;

public class ParameterDetails {

	private String name;
	private String value;
	private String measuringUnit;
	
	public ParameterDetails(String name, String value, String measuringUnit) {
		this.setName(name);
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

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
}
