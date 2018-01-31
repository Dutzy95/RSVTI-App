package com.rsvti.common;

import java.io.File;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;

import com.rsvti.database.entities.Administrator;
import com.rsvti.database.entities.Employee;
import com.rsvti.database.entities.EmployeeAuthorization;
import com.rsvti.database.entities.Firm;
import com.rsvti.database.entities.ParameterDetails;
import com.rsvti.database.entities.Rig;
import com.rsvti.database.entities.RigParameter;
import com.rsvti.database.entities.TestQuestion;
import com.rsvti.database.services.DBServices;

public class Data {

	public static void populate() {
		try {
			
			String jarFilePath = Utils.getJarFilePath();
			new File(jarFilePath + Constants.XML_FIRMS_FILE_NAME).delete();
			new File(jarFilePath + Constants.XML_RIG_PARAMETERS_FILE_NAME).delete();
			new File(jarFilePath + Constants.XML_TEST_DATA_FILE_NAME).delete();
			
			Employee employee1 = new Employee("FirstName1", "LastName1", "CT", "123456", "195042033495", getDate(1978, 10, 15), "Arad", "Str. nume lung de strada, nr.4, Bl.6, Sc.B, Ap.17, Et.3", "Arad", 
					new EmployeeAuthorization("123948273", addFromTodayDate(0, 0, 20), addFromTodayDate(0, 0, 25)), "manevrant", true);
			Employee employee2 = new Employee("FirstName2", "LastName2", "BH", "098742", "240928735387", getDate(1969, 2, 24), "Timisoara", "Adresa2", "Timis",
					new EmployeeAuthorization("56732894", addFromTodayDate(0, 1, 0), addFromTodayDate(0, 1, 5)), "macaragist", false);
			Employee employee3 = new Employee("FirstName3", "LastName3", "GZ", "472893", "2190287463728", getDate(1980, 5, 16), "Oradea", "Adresa3", "Bihor",
					new EmployeeAuthorization("2357234345", addFromTodayDate(0, 0, 15), addFromTodayDate(0, 0, 17)), "manevrant", true);
			Employee employee4 = new Employee("FirstName4", "LastName4", "AR", "125489", "1234567890123", getDate(1950, 12, 4), "Arad", "Adresa4", "Arad",
					new EmployeeAuthorization("254965846", addFromTodayDate(0, 1, 15), addFromTodayDate(0, 1, 20)), "legător sarcină", false);
			Employee employee5 = new Employee("FirstName5", "LastName5", "AR", "57984", "197846583245", getDate(1955, 9, 3), "Arad", "Adresa5", "Arad",
					new EmployeeAuthorization("254965846", addFromTodayDate(0, 4, 15), addFromTodayDate(0, 4, 20)), "legător sarcină", true);
			Employee employee6 = new Employee("FirstName6", "LastName6", "MH", "479465", "13456879546", getDate(1950, 12, 4), "Severin", "Adresa6", "Severin",
					new EmployeeAuthorization("254965846", addFromTodayDate(0, 2, 10), addFromTodayDate(0, 2, 15)), "manevrant", false);
			
			Rig liftingRig1 = new Rig("macara", addFromTodayDate(0, 2, 0), "de ridicat");
			liftingRig1.setAuthorizationExtension(0);
			liftingRig1.addParameter(new ParameterDetails("inaltime_maxima","23","m"));
			liftingRig1.addParameter(new ParameterDetails("greutate_maxima","44", "kg"));
			
			DBServices.saveEntry(new Firm("SC Gigi SRL", "ABC123", "uroi1273", "Str.Oituz, Nr.7", "012398423", "238120948", 
					"email@domain.com", "Gigi Bank", "RO34 2134 4366 3456 4568 8457", 
					new Administrator("Ion", "Ionescu", "AR", "123678", "4128309478"), Collections.singletonList(liftingRig1), Arrays.asList(employee1,employee2)), false);
			
			Rig liftingRig2 = new Rig("stivuitor", addFromTodayDate(0, 11, 12), "de ridicat");
			liftingRig2.setAuthorizationExtension(1);
			liftingRig2.addParameter(new ParameterDetails("ceva","45","cevauri"));
			liftingRig2.addParameter(new ParameterDetails("altceva","96","altcevauri"));
			
			Rig pressureRig = new Rig("cazan", addFromTodayDate(1, 2, 3), "sub presiune");
			pressureRig.setAuthorizationExtension(1);
			pressureRig.addParameter(new ParameterDetails("volum_maxim","98","m3"));
			pressureRig.addParameter(new ParameterDetails("presiune_maxima","74","bar"));
			
			DBServices.saveEntry(new Firm("SC DURU SRL", "CDE348", "234hjk213", "Str.Florii, Nr.3", "1297048613", "532784921", 
					"email2ter@domain.com", "Duru Bank", "RO34 1234 2345 3734 8567 5600", 
					new Administrator("Doru", "Georgescu", "MH", "147283", "5328934729"), Arrays.asList(liftingRig2,pressureRig), Arrays.asList(employee3, employee4, employee5, employee6)), false);
			
			DBServices.saveEntry(new RigParameter("de ridicat", "inaltime_maxima", "m"));
			DBServices.saveEntry(new RigParameter("de ridicat", "greutate_maxima", "kg"));
			DBServices.saveEntry(new RigParameter("de ridicat", "inaltime_minima", "m"));
			DBServices.saveEntry(new RigParameter("sub presiune", "volum_maxim", "m3"));
			DBServices.saveEntry(new RigParameter("sub presiune", "volum_minim", "m3"));
			DBServices.saveEntry(new RigParameter("sub presiune", "presiune_maxima", "bar"));
			
			DBServices.saveEntry(new TestQuestion("MLorem ipsum dolor sit amet, consectetur adipiscing elit. Proin tincidunt lacus ac enim vestibulum, in porta nulla bibendum. Mauris malesuada sodales cursus.", Arrays.asList("raspuns11", "raspuns12", "raspuns13"),"manevrant"));
			DBServices.saveEntry(new TestQuestion("MDonec condimentum, orci non fermentum rutrum, quam nisl maximus ante, id finibus nibh risus eget felis. Curabitur fermentum metus dignissim lectus blandit ullamcorper.", Arrays.asList("raspuns21", "raspuns22", "raspuns23"),"manevrant"));
			DBServices.saveEntry(new TestQuestion("MInteger viverra nisl ac venenatis vehicula. Nulla sit amet convallis ipsum, cursus sodales mauris. Donec bibendum tellus felis, eget sollicitudin nulla consequat et.", Arrays.asList("raspuns31", "raspuns32", "raspuns33"),"manevrant"));
			DBServices.saveEntry(new TestQuestion("MIn hac habitasse platea dictumst. Morbi vel neque blandit, consectetur magna non, laoreet ante.", Arrays.asList("raspuns31", "raspuns32", "raspuns33"),"manevrant"));
			DBServices.saveEntry(new TestQuestion("MQuisque tempus augue sit amet lacus scelerisque, volutpat dapibus urna aliquam. Nunc a gravida lacus, quis mollis enim. Duis ac ligula vel mauris lobortis dignissim eu in nisi. Pellentesque pretium semper lectus, id mollis metus aliquam sed.", Arrays.asList("raspuns31", "raspuns32", "raspuns33"),"manevrant"));
			DBServices.saveEntry(new TestQuestion("MNunc gravida posuere massa, sed gravida massa dapibus eu. Vestibulum lobortis nisl sapien, ut rhoncus magna egestas id.", Arrays.asList("raspuns31", "raspuns32", "raspuns33"),"manevrant"));
			DBServices.saveEntry(new TestQuestion("MVestibulum maximus nibh sed ligula feugiat, eu rutrum elit mollis. Suspendisse ex est, porttitor ac suscipit ut, dignissim vel nisl.", Arrays.asList("raspuns31", "raspuns32", "raspuns33"),"manevrant"));
			DBServices.saveEntry(new TestQuestion("MNunc condimentum at arcu in consequat. Proin finibus volutpat mauris, non imperdiet nisl. Nam fringilla turpis condimentum risus lobortis faucibus. In non risus sem. Maecenas ac metus vel nunc molestie egestas quis at augue.", Arrays.asList("raspuns31", "raspuns32", "raspuns33"),"manevrant"));
			DBServices.saveEntry(new TestQuestion("MDonec tincidunt tincidunt malesuada. Vestibulum et nisi ut lorem finibus pharetra eu id velit. Cras in lectus pharetra, vestibulum urna non, vehicula nulla. Proin vel facilisis leo, quis mattis turpis.", Arrays.asList("raspuns31", "raspuns32", "raspuns33"),"manevrant"));
			DBServices.saveEntry(new TestQuestion("MProin scelerisque, erat vel sodales elementum, augue lacus rutrum dolor, et posuere nunc odio sit amet augue. In consequat sem nec leo congue, eu sodales tellus commodo. Integer ullamcorper condimentum tempor. Donec ut lorem quis eros feugiat fringilla. Integer vel velit purus.", Arrays.asList("raspuns31", "raspuns32", "raspuns33"),"manevrant"));
			DBServices.saveEntry(new TestQuestion("MNullam eget nulla vulputate, luctus orci at, vehicula lorem. Nam quis odio ante. Phasellus in sapien semper, viverra mauris a, placerat justo. Praesent sed lacus sit amet eros vehicula dignissim vel vitae ex.", Arrays.asList("raspuns31", "raspuns32", "raspuns33"),"manevrant"));
			DBServices.saveEntry(new TestQuestion("MLorem ipsum dolor sit amet, consectetur adipiscing elit. Donec ante mauris, tempor ac nulla nec, tincidunt vulputate lorem. Pellentesque sollicitudin sollicitudin ante. Proin tempus metus quis eros mollis, pellentesque pellentesque sem aliquam.", Arrays.asList("raspuns31", "raspuns32", "raspuns33"),"manevrant"));
			DBServices.saveEntry(new TestQuestion("MDonec nec interdum erat. In malesuada metus at aliquet euismod. Donec nec gravida ligula. Duis id ullamcorper nibh, eget maximus mauris. Mauris ornare metus sit amet aliquet efficitur. Quisque vestibulum hendrerit libero sed pellentesque. Morbi nec nunc non enim placerat pretium at id nisl.", Arrays.asList("raspuns31", "raspuns32", "raspuns33"),"manevrant"));
			
			DBServices.saveEntry(new TestQuestion("SLorem ipsum dolor sit amet, consectetur adipiscing elit. Proin tincidunt lacus ac enim vestibulum, in porta nulla bibendum. Mauris malesuada sodales cursus.", Arrays.asList("raspuns11", "raspuns12", "raspuns13"),"stivuitorist"));
			DBServices.saveEntry(new TestQuestion("SDonec condimentum, orci non fermentum rutrum, quam nisl maximus ante, id finibus nibh risus eget felis. Curabitur fermentum metus dignissim lectus blandit ullamcorper.", Arrays.asList("raspuns21", "raspuns22", "raspuns23"),"stivuitorist"));
			DBServices.saveEntry(new TestQuestion("SInteger viverra nisl ac venenatis vehicula. Nulla sit amet convallis ipsum, cursus sodales mauris. Donec bibendum tellus felis, eget sollicitudin nulla consequat et.", Arrays.asList("raspuns31", "raspuns32", "raspuns33"),"stivuitorist"));
			DBServices.saveEntry(new TestQuestion("SIn hac habitasse platea dictumst. Morbi vel neque blandit, consectetur magna non, laoreet ante.", Arrays.asList("raspuns31", "raspuns32", "raspuns33"),"stivuitorist"));
			DBServices.saveEntry(new TestQuestion("SQuisque tempus augue sit amet lacus scelerisque, volutpat dapibus urna aliquam. Nunc a gravida lacus, quis mollis enim. Duis ac ligula vel mauris lobortis dignissim eu in nisi. Pellentesque pretium semper lectus, id mollis metus aliquam sed.", Arrays.asList("raspuns31", "raspuns32", "raspuns33"),"stivuitorist"));
			DBServices.saveEntry(new TestQuestion("SNunc gravida posuere massa, sed gravida massa dapibus eu. Vestibulum lobortis nisl sapien, ut rhoncus magna egestas id.", Arrays.asList("raspuns31", "raspuns32", "raspuns33"),"stivuitorist"));
			DBServices.saveEntry(new TestQuestion("SVestibulum maximus nibh sed ligula feugiat, eu rutrum elit mollis. Suspendisse ex est, porttitor ac suscipit ut, dignissim vel nisl.", Arrays.asList("raspuns31", "raspuns32", "raspuns33"),"stivuitorist"));
			DBServices.saveEntry(new TestQuestion("SNunc condimentum at arcu in consequat. Proin finibus volutpat mauris, non imperdiet nisl. Nam fringilla turpis condimentum risus lobortis faucibus. In non risus sem. Maecenas ac metus vel nunc molestie egestas quis at augue.", Arrays.asList("raspuns31", "raspuns32", "raspuns33"),"stivuitorist"));
			DBServices.saveEntry(new TestQuestion("SDonec tincidunt tincidunt malesuada. Vestibulum et nisi ut lorem finibus pharetra eu id velit. Cras in lectus pharetra, vestibulum urna non, vehicula nulla. Proin vel facilisis leo, quis mattis turpis.", Arrays.asList("raspuns31", "raspuns32", "raspuns33"),"stivuitorist"));
			DBServices.saveEntry(new TestQuestion("SProin scelerisque, erat vel sodales elementum, augue lacus rutrum dolor, et posuere nunc odio sit amet augue. In consequat sem nec leo congue, eu sodales tellus commodo. Integer ullamcorper condimentum tempor. Donec ut lorem quis eros feugiat fringilla. Integer vel velit purus.", Arrays.asList("raspuns31", "raspuns32", "raspuns33"),"stivuitorist"));
			DBServices.saveEntry(new TestQuestion("SNullam eget nulla vulputate, luctus orci at, vehicula lorem. Nam quis odio ante. Phasellus in sapien semper, viverra mauris a, placerat justo. Praesent sed lacus sit amet eros vehicula dignissim vel vitae ex.", Arrays.asList("raspuns31", "raspuns32", "raspuns33"),"stivuitorist"));
			DBServices.saveEntry(new TestQuestion("SLorem ipsum dolor sit amet, consectetur adipiscing elit. Donec ante mauris, tempor ac nulla nec, tincidunt vulputate lorem. Pellentesque sollicitudin sollicitudin ante. Proin tempus metus quis eros mollis, pellentesque pellentesque sem aliquam.", Arrays.asList("raspuns31", "raspuns32", "raspuns33"),"stivuitorist"));
			DBServices.saveEntry(new TestQuestion("SDonec nec interdum erat. In malesuada metus at aliquet euismod. Donec nec gravida ligula. Duis id ullamcorper nibh, eget maximus mauris. Mauris ornare metus sit amet aliquet efficitur. Quisque vestibulum hendrerit libero sed pellentesque. Morbi nec nunc non enim placerat pretium at id nisl.", Arrays.asList("raspuns31", "raspuns32", "raspuns33"),"stivuitorist"));
			
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	public static Date getDate(int year, int month, int day) {
		Calendar date = Calendar.getInstance();
		date.clear();
		date.set(year, month-1, day);
		return date.getTime();
	}
	
	public static Date addFromTodayDate(int year, int month, int day) {
		Calendar date = Calendar.getInstance();
		date.add(Calendar.YEAR, year);
		date.add(Calendar.MONTH, month);
		date.add(Calendar.DATE, day);
		return date.getTime();
	}
}
