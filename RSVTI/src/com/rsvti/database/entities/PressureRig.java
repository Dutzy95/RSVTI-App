package com.rsvti.database.entities;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import org.w3c.dom.Node;

public class PressureRig implements Rig {
	
	private HashMap<String,String> parameters;
	
	public PressureRig() {
		parameters = new HashMap<String,String>();
	}
	
	public PressureRig(HashMap<String,String> parameters) {
		this.parameters = parameters;
	}
	
	/**
	 * Method that adds a parameter with the name that appears in the .xml file and its value.
	 * @param key parameter name
	 * @param value parameter value
	 */
	@Override
	public void addParameter(String key, String value) {
		parameters.put(key,value);
	}
	
	@Override
	public HashMap<String,String> getParameters() {
		return parameters;
	}
	
	@Override
	public String getType() {
		return "sub presiune";
	}
}
