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
import com.rsvti.database.entities.ParameterDetails;
import com.rsvti.database.entities.Rig;
import com.rsvti.database.entities.RigParameter;
import com.rsvti.database.services.DBServices;
import com.rsvti.database.services.EntityBuilder;

public class Data {

	public static void populate() {
		try {
			
			String completeJarFilePath = new File(DBServices.class.getProtectionDomain().getCodeSource().getLocation().toURI().getPath()).getAbsolutePath();
			String jarFilePath = completeJarFilePath.substring(0, completeJarFilePath.lastIndexOf("\\")) + "\\";
			new File(jarFilePath + Constants.XML_DB_FILE_NAME).delete();
			new File(jarFilePath + Constants.XML_RIG_PARAMETERS).delete();
			
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
			
			Rig liftingRig1 = new Rig("macara", date.getTime(), Arrays.asList(employee1,employee2), "de ridicat");
			liftingRig1.addParameter(new ParameterDetails("inaltime_maxima","23","m"));
			liftingRig1.addParameter(new ParameterDetails("greutate_maxima","44", "kg"));
			
			DBServices.saveEntry(new Firm("SC Gigi SRL", "ABC123", "uroi1273", "Str.Oituz, Nr.7", "012398423", "238120948", 
					"email@domain.com", "Gigi Bank", "RO34 2134 4366 3456 4568 8457", 
					new Administrator("Ion", "Ionescu", "AR", "123678", "4128309478"), Collections.singletonList(liftingRig1)), false);
			
			date.set(2005, 12, 26);
			Rig liftingRig2 = new Rig("stivuitor", date.getTime(), Arrays.asList(employee3), "de ridicat");
			liftingRig2.addParameter(new ParameterDetails("ceva","45","cevauri"));
			liftingRig2.addParameter(new ParameterDetails("altceva","96","altcevauri"));
			
			date.set(2010, 7, 20);
			Rig pressureRig = new Rig("cazan", date.getTime(), Arrays.asList(employee1,employee3), "sub presiune");
			pressureRig.addParameter(new ParameterDetails("volum_maxim","98","m3"));
			pressureRig.addParameter(new ParameterDetails("presiune_maxima","74","bar"));
			
			DBServices.saveEntry(new Firm("SC DURU SRL", "CDE348", "234hjk213", "Str.Florii, Nr.3", "1297048613", "532784921", 
					"email2ter@domain.com", "Duru Bank", "RO34 1234 2345 3734 8567 5600", 
					new Administrator("Doru", "Georgescu", "MH", "147283", "5328934729"), Arrays.asList(liftingRig2,pressureRig)), false);
			
			EntityBuilder.buildFirmFromXml((Node) DBServices.executeXmlQuery("//firma[@id = 0]", XPathConstants.NODE));
			EntityBuilder.buildRigFromXml((Node) DBServices.executeXmlQuery("//instalatie[parent::firma[@id = 0]]", XPathConstants.NODE));
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
			DBServices.saveEntry(new RigParameter("de ridicat", "inaltime_maxima"));
			DBServices.saveEntry(new RigParameter("de ridicat", "greutate_maxima"));
			DBServices.saveEntry(new RigParameter("de ridicat", "inaltime_minima"));
			DBServices.saveEntry(new RigParameter("sub presiune", "volum_maxim"));
			DBServices.saveEntry(new RigParameter("sub presiune", "volum_minim"));
			DBServices.saveEntry(new RigParameter("sub presiune", "presiune_maxima"));
//			DBServices.deleteEntry(new RigParameter("de ridicat", "ceva"));
		} catch(Exception e) {
			e.printStackTrace();
		}
	}

}
