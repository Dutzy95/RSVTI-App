package com.rsvti.database.entities;

import java.util.HashMap;

import org.w3c.dom.Node;

public interface Rig {
	
	public void addParameter(String key, String value);
	
	public HashMap<String,String> getParameters();
	
	public String getType();
	
	@Override
	public boolean equals(Object o);
}
