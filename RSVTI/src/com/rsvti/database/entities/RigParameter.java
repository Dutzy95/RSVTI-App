package com.rsvti.database.entities;

public class RigParameter {

	private String type;
	private String name;
	private String measuringUnit;
	
	public RigParameter(String type, String name, String measuringUnit) {
		this.setType(type);
		this.setName(name);
		this.setMeasuringUnit(measuringUnit);
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getMeasuringUnit() {
		return measuringUnit;
	}

	public void setMeasuringUnit(String measuringUnit) {
		this.measuringUnit = measuringUnit;
	}
	
	@Override
	public boolean equals(Object o) {
		return o instanceof RigParameter &&
				((RigParameter) o).getName().equals(name);
	}
	
	public String toString() {
		return "type: " + type +
				"\nname: " + name +
				"\nmeasuringUnit: " + measuringUnit;
	}
}
