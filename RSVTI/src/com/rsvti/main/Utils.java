package com.rsvti.main;

import java.io.File;
import java.io.PrintStream;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Calendar;
import java.util.Locale;

import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.DateCell;
import javafx.scene.control.DatePicker;
import javafx.util.Callback;
import javafx.util.StringConverter;

public class Utils {

	public static void setDisabledDaysForDatePicker(DatePicker datePicker) {
		final Callback<DatePicker, DateCell> dayCellFactory = 
                new Callback<DatePicker, DateCell>() {
                    @Override
                    public DateCell call(final DatePicker datePicker) {
                        return new DateCell() {
                            @Override
                            public void updateItem(LocalDate item, boolean empty) {
                                super.updateItem(item, empty);
                               
                                //TODO: make custom list of national holidays that are not on fixed days
                                //Could not use Calendar.SATURDAY and Calendar.SUNDAY because they are 7 respectively 1, because SUNDAY is first day of week
                                if (item.getDayOfWeek().getValue() == 6 || item.getDayOfWeek().getValue() == 7 ||
                                		Constants.publicHolidays.contains((item.getDayOfMonth() + "-" + item.getMonthValue()))) {
                                        setDisable(true);
                                        setStyle("-fx-background-color: " + Constants.DISABLED_COLOR + ";");
                                }   
                        }
                    };
                }
            };
            datePicker.setDayCellFactory(dayCellFactory);
	}
	
	public static void setDisplayFormatForDatePicker(DatePicker datePicker) {
		Locale locale = new Locale("ro", "RO");
		Locale.setDefault(locale);
		
		StringConverter<LocalDate> converter = new StringConverter<LocalDate>() {
            DateTimeFormatter dateFormatter = 
                DateTimeFormatter.ofPattern(Constants.DATE_FORMAT);
            @Override
            public String toString(LocalDate date) {
                if (date != null) {
                    return dateFormatter.format(date);
                } else {
                    return "";
                }
            }
            @Override
            public LocalDate fromString(String string) {
                if (string != null && !string.isEmpty()) {
                    return LocalDate.parse(string, dateFormatter);
                } else {
                    return null;
                }
            }
        };             
        datePicker.setConverter(converter);
        datePicker.setPromptText(Constants.DATE_FORMAT_RO);
        datePicker.setShowWeekNumbers(true);
	}
	
	public static void alert(AlertType alertType, String title, String header, String content) {
		Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setHeaderText(header);
        alert.setContentText(content);

        alert.showAndWait();
	}
	
	public static void setErrorLog() {
		new Thread()
		{
		    public void run() {
		    	try {
					String jarFilePath = Utils.getJarFilePath();
					File file = new File(jarFilePath + Constants.ERROR_LOG_FILE);
					System.setErr(new PrintStream(file));
					
					SimpleDateFormat simpleDateFormat = new SimpleDateFormat(Constants.DATE_FORMAT_EXTENDED);
					Calendar refreshIntervalBegin = Calendar.getInstance();
					refreshIntervalBegin.add(Constants.ERR_LOG_REFRESH_TIME_UNIT, Constants.ERR_LOG_REFRESH_INTERVAL);
					while(true) {
						Calendar instance = Calendar.getInstance();
						if(instance.equals(refreshIntervalBegin) || instance.after(refreshIntervalBegin)) {
							System.err.println("[" + simpleDateFormat.format(refreshIntervalBegin.getTime()) + "]");
							System.out.println("[" + simpleDateFormat.format(refreshIntervalBegin.getTime()) + "]");
							refreshIntervalBegin.add(Constants.ERR_LOG_REFRESH_TIME_UNIT, Constants.ERR_LOG_REFRESH_INTERVAL);
						}
					}
				} catch(Exception e) {
					e.printStackTrace();
				}
		    }
		}.start();
	}
	
	public static String getJarFilePath() {
		//		For .jar file
//		String jarFilePath = new File(ClassLoader.getSystemClassLoader().getResource(Constants.JAR_FILE_NAME).getPath()).getAbsolutePath();
//		return jarFilePath.substring(0, jarFilePath.lastIndexOf("\\")) + "\\";
		
		//		For Eclipse
		String jarFilePath = "";
		try {
			jarFilePath = new File(Utils.class.getProtectionDomain().getCodeSource().getLocation().toURI().getPath()).getAbsolutePath();
		} catch(Exception e) {
			e.printStackTrace();
		}
		return jarFilePath.substring(0, jarFilePath.lastIndexOf("\\")) + "\\";
	}
	
	public static void createFolderHierarchy() {
		try {
			String jarFilePath = getJarFilePath();
			File file = new File(jarFilePath + "database");
			file.mkdir();
			file = new File(jarFilePath + "docs");
			file.mkdir();
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
}
