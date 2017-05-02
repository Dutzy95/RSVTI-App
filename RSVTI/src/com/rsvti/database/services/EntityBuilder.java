package com.rsvti.database.services;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import org.w3c.dom.Node;

import com.rsvti.database.entities.Administrator;
import com.rsvti.database.entities.Firm;
import com.rsvti.database.entities.LiftingRig;
import com.rsvti.database.entities.PressureRig;
import com.rsvti.database.entities.Rig;
import com.rsvti.main.Constants;

public class EntityBuilder {

	public static Firm buildFirmFromXml(Node node) {
		ArrayList<String> firmParameterValues = new ArrayList<String>();
		for(int i = 0; i <= 17; i++) {
			//To see why for loop is up to 17, de-comment next line and set for loop until node.getCChildNodes().getLength(). It memorizes only the fields up to the administrator, which is on line 19. 
			//System.out.println(i + ": " + node.getChildNodes().item(i).getTextContent().replaceAll("\t", "\\\\t").replaceAll("\n", "\\\\n"));
			if(!node.getChildNodes().item(i).getTextContent().contains("\t") ||
					!node.getChildNodes().item(i).getTextContent().contains("\n")) {
				firmParameterValues.add(node.getChildNodes().item(i).getTextContent());
			}
		}
		
		SimpleDateFormat format = new SimpleDateFormat(Constants.DATE_FORMAT);
		Date dueDate = null;
		try {
			 dueDate = format.parse(firmParameterValues.get(8));
		} catch(ParseException pe) {
			pe.printStackTrace();
		}
		
		Node adminNode = node.getChildNodes().item(19);
		ArrayList<String> adminParameterValues = new ArrayList<String>();
		
		for(int j = 0; j < adminNode.getChildNodes().getLength(); j++) {
			if(!node.getChildNodes().item(j).getTextContent().contains("\t") ||
					!node.getChildNodes().item(j).getTextContent().contains("\n")) {
				adminParameterValues.add(adminNode.getChildNodes().item(j).getTextContent());
			}
		}
		
		List<Rig> rigs = new ArrayList<Rig>();
		
		for(int k = 21; k < node.getChildNodes().getLength(); k+=2) {
			Node rigNode = node.getChildNodes().item(k);
			HashMap<String,String> parametersAndValues = new HashMap<String,String>();
			
			for(int l = 0; l < rigNode.getChildNodes().getLength(); l++) {
				if(!rigNode.getChildNodes().item(l).getTextContent().contains("\t") ||
						!rigNode.getChildNodes().item(l).getTextContent().contains("\n")) {
					parametersAndValues.put(rigNode.getChildNodes().item(l).getNodeName(), rigNode.getChildNodes().item(l).getTextContent());
				}
			}
			
			if(rigNode.getAttributes().getNamedItem("type").getTextContent().equals("de ridicat")) {
				rigs.add(new LiftingRig(parametersAndValues));
			} else {
				rigs.add(new PressureRig(parametersAndValues));
			}
		}
		
		return new Firm(
				firmParameterValues.get(0),
				firmParameterValues.get(1),
				firmParameterValues.get(2),
				firmParameterValues.get(3),
				firmParameterValues.get(4),
				firmParameterValues.get(5),
				firmParameterValues.get(6),
				firmParameterValues.get(7),
				dueDate,
				new Administrator(
						adminParameterValues.get(0),
						adminParameterValues.get(1),
						adminParameterValues.get(2),
						adminParameterValues.get(3)
						),
				rigs
				);
	}
}
