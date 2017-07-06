package com.rsvti.common;

import java.awt.AWTException;
import java.awt.Image;
import java.awt.MenuItem;
import java.awt.PopupMenu;
import java.awt.SystemTray;
import java.awt.TrayIcon;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.io.PrintStream;
import java.nio.file.Files;
import java.nio.file.LinkOption;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;

import com.rsvti.database.entities.LoggedTest;
import com.rsvti.database.services.DBServices;

import javafx.application.Platform;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ButtonBar.ButtonData;
import javafx.scene.control.ButtonType;
import javafx.scene.control.DateCell;
import javafx.scene.control.DatePicker;
import javafx.stage.Stage;
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
                               
                                //Could not use Calendar.SATURDAY and Calendar.SUNDAY because they are 7 respectively 1, because SUNDAY is first day of week
                                if (item.getDayOfWeek().getValue() == 6 || item.getDayOfWeek().getValue() == 7 ||
                                		Constants.publicHolidays.contains((item.getDayOfMonth() + "-" + item.getMonthValue())) ||
                                		DBServices.getVariableVacationDates().contains(java.sql.Date.valueOf(item))) {
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
		StringConverter<LocalDate> converter = new StringConverter<LocalDate>() {
            DateTimeFormatter dateFormatter = 
                DateTimeFormatter.ofPattern(DBServices.getDatePattern());
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
        datePicker.setPromptText(DBServices.getRomanianDatePattern());
        datePicker.setShowWeekNumbers(true);
	}
	
	public static Optional<ButtonType> alert(AlertType alertType, String title, String header, String content) {
		Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setHeaderText(header);
        alert.setContentText(content);
        
        if(alertType == AlertType.INFORMATION) {
        	ButtonType yes = new ButtonType("Da", ButtonData.YES);
        	ButtonType no = new ButtonType("Nu", ButtonData.NO);
        	alert.getButtonTypes().setAll(yes,no);
        }

        return alert.showAndWait();
	}
	
	public static void setErrorLog() {
		new Thread()
		{
		    public void run() {
		    	try {
					String jarFilePath = Utils.getJarFilePath();
					File file = new File(jarFilePath + Constants.ERROR_LOG_FILE);
					System.setErr(new PrintStream(file));
					
					SimpleDateFormat simpleDateFormat = new SimpleDateFormat(DBServices.getDatePattern() + " HH:mm:ss");
					Calendar refreshIntervalBegin = Calendar.getInstance();
					refreshIntervalBegin.add(Constants.ERR_LOG_REFRESH_TIME_UNIT, Constants.ERR_LOG_REFRESH_INTERVAL);
					while(true) {
						Calendar instance = Calendar.getInstance();
						if(instance.equals(refreshIntervalBegin) || instance.after(refreshIntervalBegin)) {
							System.err.println("[" + simpleDateFormat.format(refreshIntervalBegin.getTime()) + "]");
							refreshIntervalBegin.add(Constants.ERR_LOG_REFRESH_TIME_UNIT, Constants.ERR_LOG_REFRESH_INTERVAL);
						}
					}
				} catch(Exception e) {
					e.printStackTrace();
				}
		    }
		}.start();
	}
	
	public static void setStartup() {
		Path path = Paths.get("C:/Users/" + System.getProperty("user.name") + "/AppData/Roaming/Microsoft/Windows/Start Menu/Programs/Startup/");
		if(Files.exists(path, LinkOption.NOFOLLOW_LINKS)) {
			try {
				String fileName = "Start" + Constants.APP_NAME + ".bat";
				File batchFile = new File(path.toString() + "/" + fileName);
				OutputStream output = new FileOutputStream(batchFile);
				String jarFilePath = new File(ClassLoader.getSystemClassLoader().getResource(Constants.JAR_FILE_NAME).getPath()).getAbsolutePath();
				byte[] bytes = new String("start javaw -jar " + jarFilePath).getBytes();
				output.write(bytes);
				output.close();
			} catch(Exception e) {
				e.printStackTrace();
			}
		}
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
			file = new File(jarFilePath + "docs\\procese verbale");
			file.mkdir();
			file = new File(jarFilePath + "docs\\teste");
			file.mkdir();
			file = new File(jarFilePath + "docs\\teste\\logs");
			file.mkdir();
			file = new File(jarFilePath + "docs\\pdf");
			file.mkdir();
			file = new File(jarFilePath + "docs\\tabele utilaje");
			file.mkdir();
			file = new File(jarFilePath + "images");
			file.mkdir();
			ImageIO.write(ImageIO.read(Utils.class.getResource("/RSVTI_with_text.png")), "png", new File(jarFilePath + "images/RSVTI_with_text.png"));
			ImageIO.write(ImageIO.read(Utils.class.getResource("/RSVTI_without_text.png")), "png", new File(jarFilePath + "images/RSVTI_without_text.png"));
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	public static void setTray(Stage primaryStage) {
		try {
			
			primaryStage.setOnCloseRequest(event -> 
				{Platform.runLater(new Runnable() {
					@Override
					public void run() {
						primaryStage.hide();
					}
				});});
			
			ImageIcon imageIcon = new ImageIcon(Utils.class.getResource("/RSVTI_without_text.png"));
			final SystemTray tray = SystemTray.getSystemTray();
			int trayIconWidth = new TrayIcon(imageIcon.getImage()).getSize().width;
			int trayIconHeight = new TrayIcon(imageIcon.getImage()).getSize().height;
			final TrayIcon trayIcon = new TrayIcon(imageIcon.getImage().getScaledInstance(trayIconWidth, trayIconHeight, Image.SCALE_SMOOTH));
			trayIcon.setImageAutoSize(true);
			tray.add(trayIcon);
			
			final PopupMenu popup = new PopupMenu();
			
	        MenuItem show = new MenuItem("Afișare");
	        MenuItem exit = new MenuItem("Ieșire");
	        
	        exit.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent e) {
					System.exit(0);
				}
			});
	        
	        show.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent e) {
					Platform.runLater(new Runnable() { 
			            @Override
			            public void run() {
			                primaryStage.show();
			            }
			        });
				}
			});
	        
	        trayIcon.addMouseListener(new MouseListener() {
				
				@Override
				public void mouseReleased(MouseEvent e) {
				}
				
				@Override
				public void mousePressed(MouseEvent e) {
				}
				
				@Override
				public void mouseExited(MouseEvent e) {
				}
				
				@Override
				public void mouseEntered(MouseEvent e) {
				}
				
				@Override
				public void mouseClicked(MouseEvent e) {
					if(e.getClickCount() >= 2) {
						Platform.runLater(new Runnable() { 
				            @Override
				            public void run() {
				                primaryStage.show();
				            }
				        });
					}
				}
			});
	       
	        //Add components to pop-up menu
	        popup.add(show);
	        popup.addSeparator();
	        popup.add(exit);
	        
	        trayIcon.setPopupMenu(popup);
	    } catch(AWTException awte) {
	    	awte.printStackTrace();
	    }
	}
	
	private static LoggedTest getOldestLoggedTest() {
		Date minimumDate = Calendar.getInstance().getTime();
		LoggedTest test = null;
		List<LoggedTest> tests = DBServices.getAllLoggedTests();
		for(LoggedTest index : tests) {
			if(index.getGenerationDateAndTime().before(minimumDate)) {
				minimumDate = index.getGenerationDateAndTime();
				test = index;
			}
		}
		return test;
	}
	
	public static void synchronizeLog() {
		List<LoggedTest> loggedTests = DBServices.getAllLoggedTests();
		int maxLogSize = DBServices.getMaximumLogSize();
		if(maxLogSize < loggedTests.size()) {
			for(int i = 0; i < loggedTests.size() - maxLogSize; i++) {
				LoggedTest oldestLoggedTest = getOldestLoggedTest();
				
				DBServices.deleteEntry(oldestLoggedTest);
				
				SimpleDateFormat dateFormat = new SimpleDateFormat(Constants.DEFAULT_DATE_FORMAT);
				SimpleDateFormat extendedDateFormat = new SimpleDateFormat(Constants.DEFAULT_DATE_FORMAT + " HH.mm.ss");
				String jarFilePath = Utils.getJarFilePath();
				File file = new File(jarFilePath + "docs\\teste\\logs\\" + dateFormat.format(oldestLoggedTest.getGenerationDateAndTime())  + "\\"
						+ oldestLoggedTest.getEmployeeLastName() + " " + oldestLoggedTest.getEmployeeFirstName() + " " 
						+ oldestLoggedTest.getEmployeeTitle() + " " + extendedDateFormat.format(oldestLoggedTest.getGenerationDateAndTime()) + ".docx");
				file.delete();
			}
		}
	}
}
