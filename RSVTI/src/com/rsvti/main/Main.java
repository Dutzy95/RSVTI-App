package com.rsvti.main;

import java.io.File;
import java.io.PrintStream;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.w3c.dom.Document;

import com.rsvti.database.entities.Administrator;
import com.rsvti.database.entities.Firm;
import com.rsvti.database.services.DBServices;

public class Main {

	public static void main(String[] args) {
		try {
			File file = new File(Constants.XML_DB_FILE_NAME);
			
			file.createNewFile();
			PrintStream ps = new PrintStream(file);
			ps.println("<?xml version=\"1.0\"?>\n<app>\n</app>");
			ps.close();
			
			DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
			DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
			Document document = dBuilder.parse(file);
			
			DBServices.saveEntry(document, new Firm("ABC123", "uroi1273", "Str.Oituz, Nr.7", "012398423", "238120948", "email@domain.com", "Gigi Bank", "RO34 2134 4366 3456 4568 8457", new Administrator("Ion Ionescu", "AR", "123678", "4128309478")));
			DBServices.saveEntry(document, new Firm("CDE348", "234hjk213", "Str.Florii, Nr.3", "1297048613", "532784921", "email2ter@domain.com", "Duru Bank", "RO34 1234 2345 3734 8567 5600", new Administrator("Doru Georgescu", "MH", "147283", "5328934729")));
		} catch(Exception e) {
			e.printStackTrace();
		}
	}

}
