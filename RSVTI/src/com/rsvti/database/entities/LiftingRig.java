package com.rsvti.database.entities;

import java.util.HashMap;
import java.util.Map;

public class LiftingRig implements Rig{

	private HashMap<String,String> parameters;
	
	public LiftingRig() {
		parameters = new HashMap<String,String>();
	}
	
	public LiftingRig(HashMap<String,String> parameters) {
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
		return "de ridicat";
	}
}
