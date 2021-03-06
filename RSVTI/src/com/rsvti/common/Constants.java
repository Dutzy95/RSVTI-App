package com.rsvti.common;

import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javafx.scene.layout.Border;
import javafx.scene.layout.BorderStroke;
import javafx.scene.layout.BorderStrokeStyle;
import javafx.scene.layout.BorderWidths;
import javafx.scene.layout.CornerRadii;
import javafx.scene.paint.Color;

public class Constants {

	public static final String XML_FIRMS_FILE_NAME = "RSVTI_database.xml";
	public static final String XML_RIG_PARAMETERS_FILE_NAME = "RigParameters.xml";
	public static final String XML_TEST_DATA_FILE_NAME = "TestData.xml";
	public static final String XML_CUSTOM_SETTINGS_FILE_NAME = "CustomSettings.xml";
	public static final String XML_LOGGED_TESTS_FILE_NAME = "LoggedTests.xml";
	public static final String XML_ERROR_LOG_FILE = "Errlog.xml";
	
	public static final String APP_NAME = "RSVTIApp";
	public static final String JAR_FILE_NAME = "RSVTIApp.jar";
	
	public static final String DEFAULT_DATE_FORMAT = "dd-MM-yyyy";
	public static final SimpleDateFormat DEFAULT_DATE_FORMATTER = new SimpleDateFormat(DEFAULT_DATE_FORMAT);
	public static final String GENERATED_FILE_DATE_FORMAT = "dd.MM.yyyy";
	public static final String DEFAULT_TIMESTAMP_FORMAT = "HH:mm:ss";
	public static final String ERRLOG_TIMESTAMP_FORMAT = "HH.mm.ss.SSS";
	public static final Date LOW_DATE = new Date(0);
	public static final Date HIGH_DATE = new Date(Long.valueOf("16756748482093"));	//31.12.2500
	public static int ERR_LOG_REFRESH_TIME_UNIT = Calendar.HOUR_OF_DAY;
	public static int ERR_LOG_REFRESH_INTERVAL = 1;
	public static final int INFINITE = 999;
	
	public static final String TABLE_PLACEHOLDER_MESSAGE = "Nu există conținut în tabel";
	
	public static final String DISABLED_COLOR = "lightgray";
	
	public static final String GENERATED_FILE_FONT_FAMILY = "Times New Roman";
	
	public static List<String> publicHolidays = Arrays.asList(
			"1-1", 
			"2-1", 
			"24-1", 
			"1-5", 
			"1-6", 
			"15-8", 
			"30-11", 
			"1-12", 
			"25-12",
			"26-12");
	
	public static final Border redBorder = new Border(new BorderStroke(
															Color.RED, 
															BorderStrokeStyle.SOLID, 
															new CornerRadii(2), 
															new BorderWidths(5)));
	
	public static final String LIFTING_RIG = "de ridicat";
	public static final String PRESSURE_RIG = "sub presiune";
	
	public static final String RIG_PARAMETER_PRESSURE = "P";
	public static final String RIG_PARAMETER_VOLUME = "V";
	
	public static enum DB_FILE_NAMES {
		FIRM_FILE("RSVTI_database.xml"),
		RIG_PARAM_FILE("RigParameters.xml"),
		TEST_DATA_FILE("TestData.xml"),
		CUSTOM_SETTINGS_FILE("CustomSettings.xml"),
		LOGGED_TESTS_FILE("LoggedTests.xml");
		
		private String fileName; 
		
		DB_FILE_NAMES(String fileName) {
			this.fileName = fileName;
		}
		
		public String getName() {
			return fileName;
		}
	}
}
