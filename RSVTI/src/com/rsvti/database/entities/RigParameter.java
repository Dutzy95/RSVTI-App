package com.rsvti.database.entities;

public class RigParameter {

	private String type;
	private String name;
	
	public RigParameter(String type, String name) {
		this.setType(type);
		this.setName(name);
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
	
	
}
