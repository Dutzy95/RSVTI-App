package com.rsvti.main;

import java.io.File;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Collections;

import javax.xml.xpath.XPathConstants;

import org.w3c.dom.Node;

import com.rsvti.database.entities.Administrator;
import com.rsvti.database.entities.Employee;
import com.rsvti.database.entities.EmployeeAuthorization;
import com.rsvti.database.entities.Firm;
import com.rsvti.database.entities.LiftingRig;
import com.rsvti.database.entities.PressureRig;
import com.rsvti.database.entities.Rig;
import com.rsvti.database.entities.RigParameter;
import com.rsvti.database.services.DBServices;
import com.rsvti.database.services.EntityBuilder;

public class Main {

	public static void main(String[] args) {
		new File(Constants.XML_DB_FILE_NAME).delete();
		new File(Constants.XML_RIG_PARAMETERS).delete();
		try {
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
			
			DBServices.saveEntry(new Firm("ABC123", "uroi1273", "Str.Oituz, Nr.7", "012398423", "238120948", 
					"email@domain.com", "Gigi Bank", "RO34 2134 4366 3456 4568 8457", 
					new Administrator("Ion", "Ionescu", "AR", "123678", "4128309478"), Collections.singletonList(liftingRig1)), false);
			
			date.set(2005, 12, 26);
			Rig liftingRig2 = new LiftingRig(date.getTime(), Arrays.asList(employee3));
			liftingRig2.addParameter("ceva", "45");
			liftingRig2.addParameter("altceva", "96");
			
			date.set(2010, 7, 20);
			Rig pressureRig = new PressureRig(date.getTime(), Arrays.asList(employee1,employee3));
			pressureRig.addParameter("volum_maxim", "98");
			pressureRig.addParameter("presiune_maxima", "74");
			
			DBServices.saveEntry(new Firm("CDE348", "234hjk213", "Str.Florii, Nr.3", "1297048613", "532784921", 
					"email2ter@domain.com", "Duru Bank", "RO34 1234 2345 3734 8567 5600", 
					new Administrator("Doru", "Georgescu", "MH", "147283", "5328934729"), Arrays.asList(liftingRig2,pressureRig)), false);
			
//			EntityBuilder.buildFirmFromXml((Node) DBServices.executeXmlQuery("//firma[@id = 0]", XPathConstants.NODE));
//			EntityBuilder.buildRigFromXml((Node) DBServices.executeXmlQuery("//instalatie[parent::firma[@id = 0]]", XPathConstants.NODE));
//			EntityBuilder.buildEmployeeFromXml((Node) DBServices.executeXmlQuery("//angajat[@title = \"macaragist\" and parent::instalatie[parent::firma[@id = 0]]]", XPathConstants.NODE));
			
//			DBServices.deleteEntry(new Firm("CDE348", "234hjk213", "Str.Florii, Nr.3", "1297048613", "532784921", 
//					"email2ter@domain.com", "Duru Bank", "RO34 1234 2345 3734 8567 5600", 
//					new Administrator("Doru", "Georgescu", "MH", "147283", "5328934729"), Arrays.asList(liftingRig2,pressureRig)));
			
//			DBServices.updateEntry(new Firm("ABC123", "uroi1273", "Str.Oituz, Nr.7", "012398423", "238120948", 
//					"email@domain.com", "Gigi Bank", "RO34 2134 4366 3456 4568 8457", 
//					new Administrator("Ion", "Ionescu", "AR", "123678", "4128309478"), Collections.singletonList(liftingRig1)),
//						new Firm("XYZ123", "234hjk213", "Str.Florii, Nr.3", "1297048613", "532784921", 
//						"email2ter@domain.com", "Duru Bank", "RO34 1234 2345 3734 8567 5600", 
//						new Administrator("Doru", "Georgescu", "MH", "147283", "5328934729"), Arrays.asList(liftingRig1,pressureRig)));
//			
			DBServices.saveEntry(new RigParameter("de ridicat", "ceva"));
			DBServices.saveEntry(new RigParameter("sub presiune", "altceva"));
			DBServices.deleteEntry(new RigParameter("de ridicat", "ceva"));
		} catch(Exception e) {
			e.printStackTrace();
		}
	}

}
