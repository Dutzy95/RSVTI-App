package com.rsvti.main;

import java.io.File;
import java.io.PrintStream;
import java.lang.invoke.ConstantCallSite;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Calendar;
import java.util.Locale;

import com.rsvti.database.services.DBServices;

import javafx.scene.control.Alert;
import javafx.scene.control.DateCell;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Alert.AlertType;
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
		    		String completeJarFilePath = new File(Utils.class.getProtectionDomain().getCodeSource().getLocation().toURI().getPath()).getAbsolutePath();
					String jarFilePath = completeJarFilePath.substring(0, completeJarFilePath.lastIndexOf("\\")) + "\\";
					File file = new File(jarFilePath + Constants.ERROR_LOG_FILE);
					System.setErr(new PrintStream(file));
					
					SimpleDateFormat simpleDateFormat = new SimpleDateFormat(Constants.DATE_FORMAT_EXTENDED);
					Calendar fileReplaceBegin = Calendar.getInstance();
					fileReplaceBegin.add(Constants.ERR_LOG_REPLACE_TIME_UNIT, Constants.ERR_LOG_REPLACE_INTERVAL);
					Calendar refreshIntervalBegin = Calendar.getInstance();
					refreshIntervalBegin.add(Constants.ERR_LOG_REFRESH_TIME_UNIT, Constants.ERR_LOG_REFRESH_INTERVAL);
					while(true) {
						Calendar instance = Calendar.getInstance();
						if(instance.equals(refreshIntervalBegin) || instance.after(refreshIntervalBegin)) {
							System.err.println("[" + simpleDateFormat.format(refreshIntervalBegin.getTime()) + "]");
							System.out.println("[" + simpleDateFormat.format(refreshIntervalBegin.getTime()) + "]");
							refreshIntervalBegin.add(Constants.ERR_LOG_REFRESH_TIME_UNIT, Constants.ERR_LOG_REFRESH_INTERVAL);
						}
						if(instance.equals(fileReplaceBegin) || instance.after(fileReplaceBegin)) {
							Files.delete(Paths.get(jarFilePath + Constants.ERROR_LOG_FILE));
							file.createNewFile();
						}
					}
				} catch(Exception e) {
					e.printStackTrace();
				}
		    }
		}.start();
	}
}
