package com.rsvti.main;

import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class Constants {

	public static final String XML_FIRMS_FILE_NAME = "database/RSVTI_database.xml";
	public static final String XML_RIG_PARAMETERS_FILE_NAME = "database/RigParameters.xml";
	public static final String XML_TEST_DATA_FILE_NAME = "database/TestData.xml";
	public static final String ERROR_LOG_FILE = "errlog.txt";
	
	public static final String APP_NAME = "RSVTI App";
	public static final String JAR_FILE_NAME = "RSVTIApp.jar";
	
	public static String DATE_FORMAT = "dd-MM-yyyy";
	public static String DATE_FORMAT_EXTENDED = "dd-MM-yyyy HH:mm:ss";
	public static String DATE_FORMAT_RO = "zz-ll-aaaa";
	public static final Date LOW_DATE = new Date(Long.MIN_VALUE);
	public static final Date HIGH_DATE = new Date(Long.MAX_VALUE);
	public static int ERR_LOG_REFRESH_TIME_UNIT = Calendar.HOUR_OF_DAY;
	public static int ERR_LOG_REFRESH_INTERVAL = 1;
	
	public static String TABLE_PLACEHOLDER_MESSAGE = "Nu există conținut în tabel";
	
	public static final String DISABLED_COLOR = "lightgray";
	
	public static List<String> publicHolidays = Arrays.asList("1-1", "2-1", "24-1", "1-5", "1-6", "15-8", "30-11", "1-12", "25-12", "26-12");
}
