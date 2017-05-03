package com.rsvti.main;

import java.io.File;
import java.io.PrintStream;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Collections;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.xpath.XPathConstants;

import org.w3c.dom.Document;
import org.w3c.dom.Node;

import com.rsvti.database.entities.Administrator;
import com.rsvti.database.entities.Employee;
import com.rsvti.database.entities.EmployeeAuthorization;
import com.rsvti.database.entities.Firm;
import com.rsvti.database.entities.LiftingRig;
import com.rsvti.database.entities.PressureRig;
import com.rsvti.database.entities.Rig;
import com.rsvti.database.services.DBServices;
import com.rsvti.database.services.EntityBuilder;

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
			
			Calendar date = Calendar.getInstance();
			date.set(2000, 4, 12);
			
			Employee employee1 = new Employee("FirstName1", "LastName1", "CT", "123456", "195042033495", 
					new EmployeeAuthorization("123948273", date.getTime(), date.getTime()), "stivuitorist");
			date.set(2020, 4, 6);
			Employee employee2 = new Employee("FirstName2", "LastName2", "BH", "098742", "240928735387", 
					new EmployeeAuthorization("56732894", date.getTime(), date.getTime()), "macaragist");
			date.set(2017, 6, 20);
			Employee employee3 = new Employee("FirstName3", "LastName3", "GZ", "472893", "2190287463728", 
					new EmployeeAuthorization("2357234345", date.getTime(), date.getTime()), "manevrant");
			
			Rig liftingRig1 = new LiftingRig(date.getTime(), Arrays.asList(employee1,employee2));
			liftingRig1.addParameter("inaltime_maxima", "23");
			liftingRig1.addParameter("greutate_maxima", "44");
			
			DBServices.saveEntry(document, new Firm("ABC123", "uroi1273", "Str.Oituz, Nr.7", "012398423", "238120948", 
					"email@domain.com", "Gigi Bank", "RO34 2134 4366 3456 4568 8457", 
					new Administrator("Ion", "Ionescu", "AR", "123678", "4128309478"), Collections.singletonList(liftingRig1)));
			
			date.set(2005, 12, 26);
			Rig liftingRig2 = new LiftingRig(date.getTime(), Arrays.asList(employee3));
			liftingRig2.addParameter("ceva", "45");
			liftingRig2.addParameter("altceva", "96");
			
			date.set(2010, 7, 20);
			Rig pressureRig = new PressureRig(date.getTime(), Arrays.asList(employee1,employee3));
			pressureRig.addParameter("volum_maxim", "98");
			pressureRig.addParameter("presiune_maxima", "74");
			
			DBServices.saveEntry(document, new Firm("CDE348", "234hjk213", "Str.Florii, Nr.3", "1297048613", "532784921", 
					"email2ter@domain.com", "Duru Bank", "RO34 1234 2345 3734 8567 5600", 
					new Administrator("Doru", "Georgescu", "MH", "147283", "5328934729"), Arrays.asList(liftingRig2,pressureRig)));
			
			EntityBuilder.buildFirmFromXml((Node) DBServices.executeXmlQuery(document, "//firma[@id = 2]", XPathConstants.NODE));
			
			//DBServices.deleteEntry(document, new Firm("CDE348", "234hjk213", "Str.Florii, Nr.3", "1297048613", "532784921", 
			//"email2ter@domain.com", "Duru Bank", "RO34 1234 2345 3734 8567 5600", date.getTime(), 
			//new Administrator("Doru Georgescu", "MH", "147283", "5328934729")));
			
		} catch(Exception e) {
			e.printStackTrace();
		}
	}

}
