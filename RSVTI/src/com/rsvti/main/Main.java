package com.rsvti.main;

import java.io.File;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Collections;
import java.util.List;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.xpath.XPathConstants;

import org.w3c.dom.Document;
import org.w3c.dom.Node;

import com.rsvti.database.entities.Administrator;
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
			
			Rig liftingRig1 = new LiftingRig();
			liftingRig1.addParameter("inaltime_maxima", "23");
			liftingRig1.addParameter("greutate_maxima", "44");
			
			DBServices.saveEntry(document, new Firm("ABC123", "uroi1273", "Str.Oituz, Nr.7", "012398423", "238120948", "email@domain.com", "Gigi Bank", "RO34 2134 4366 3456 4568 8457", date.getTime(), new Administrator("Ion Ionescu", "AR", "123678", "4128309478"), Collections.singletonList(liftingRig1)));
			
			Rig liftingRig2 = new LiftingRig();
			liftingRig2.addParameter("ceva", "45");
			liftingRig2.addParameter("altceva", "96");
			
			Rig pressureRig = new PressureRig();
			pressureRig.addParameter("volum_maxim", "98");
			pressureRig.addParameter("presiune_maxima", "74");
			
			date.set(2010, 7, 20);
			DBServices.saveEntry(document, new Firm("CDE348", "234hjk213", "Str.Florii, Nr.3", "1297048613", "532784921", "email2ter@domain.com", "Duru Bank", "RO34 1234 2345 3734 8567 5600", date.getTime(), new Administrator("Doru Georgescu", "MH", "147283", "5328934729"), Arrays.asList(liftingRig2,pressureRig)));
			
			EntityBuilder.buildFirmFromXml((Node) DBServices.executeXmlQuery(document, "//firma[@id = 1]", XPathConstants.NODE));
			
			//DBServices.deleteEntry(document, new Firm("CDE348", "234hjk213", "Str.Florii, Nr.3", "1297048613", "532784921", "email2ter@domain.com", "Duru Bank", "RO34 1234 2345 3734 8567 5600", date.getTime(), new Administrator("Doru Georgescu", "MH", "147283", "5328934729")));
			
			System.out.println(DBServices.getLastFirmIndex(document));
			System.out.println(DBServices.getLastRigIndex(document));

			
		} catch(Exception e) {
			e.printStackTrace();
		}
	}

}
