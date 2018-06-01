package com.rsvti.common;

import java.awt.Image;
import java.awt.MenuItem;
import java.awt.PopupMenu;
import java.awt.SystemTray;
import java.awt.TrayIcon;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.io.PrintStream;
import java.net.URLDecoder;
import java.nio.file.Files;
import java.nio.file.LinkOption;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.Optional;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;

import com.rsvti.model.database.DBServices;
import com.rsvti.model.entities.Employee;
import com.rsvti.model.entities.LoggedTest;

import javafx.application.Platform;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ButtonBar.ButtonData;
import javafx.scene.control.ButtonType;
import javafx.scene.control.DateCell;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextInputControl;
import javafx.scene.control.Tooltip;
import javafx.stage.Stage;
import javafx.util.Callback;
import javafx.util.StringConverter;
import mslinks.ShellLink;
import net.sf.image4j.codec.ico.ICOEncoder;

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
	
	public static Optional<ButtonType> alert(AlertType alertType, String title, String header, String content, boolean yesNo) {
		Alert alert = new Alert(alertType);
		try {
	        alert.setTitle(title);
	        alert.setHeaderText(header);
	        alert.setContentText(content);
	        
	        if(yesNo) {
	        	ButtonType yes = new ButtonType("Da", ButtonData.YES);
	        	ButtonType no = new ButtonType("Nu", ButtonData.NO);
	        	alert.getButtonTypes().setAll(yes,no);
	        } else {
	        	ButtonType ok = new ButtonType("OK", ButtonData.OK_DONE);
	        	alert.getButtonTypes().setAll(ok);
	        }
		} catch (Exception e) {
			DBServices.saveErrorLogEntry(e);
		}
        return alert.showAndWait();
	}
	
	public static void setStartup() {
		try {
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
		} catch (Exception e) {
			DBServices.saveErrorLogEntry(e);
		}
	}
	
	public static String getJarFilePath() {
		String jarFilePath = "";
		// For .jar file
		try {
			jarFilePath = new File(ClassLoader.getSystemClassLoader().getResource(Constants.JAR_FILE_NAME).getPath()).getAbsolutePath();
			jarFilePath = URLDecoder.decode(jarFilePath, "UTF-8");
		} catch(Exception ex) {
			// For Eclipse
			try {
				jarFilePath = new File(Utils.class.getProtectionDomain().getCodeSource().getLocation().toURI().getPath()).getAbsolutePath();
			} catch(Exception e) {}
			return jarFilePath.substring(0, jarFilePath.lastIndexOf("\\")) + "\\";
		}
		return jarFilePath.substring(0, jarFilePath.lastIndexOf("\\")) + "\\";
	}
	
	public static void createFolderHierarchy() {
		try {
			String jarFilePath = getJarFilePath();
			File file = new File(jarFilePath + "database");
			file.mkdir();
			Runtime.getRuntime().exec("attrib +H " + file.getAbsolutePath());	//hide Folder
			file = new File(jarFilePath + "docs");
			file.mkdir();
			file = new File(jarFilePath + "docs\\adeverințe");
			file.mkdir();
			file = new File(jarFilePath + "docs\\procese verbale");
			file.mkdir();
			file = new File(jarFilePath + "docs\\procese verbale\\rezultate examinare");
			file.mkdir();
			file = new File(jarFilePath + "docs\\procese verbale\\verificare tehnică utilaje");
			file.mkdir();
			file = new File(jarFilePath + "docs\\teste");
			file.mkdir();
			file = new File(jarFilePath + "docs\\teste\\logs");
			file.mkdir();
			hideFolder(file);
			file = new File(jarFilePath + "docs\\tabele utilaje");
			file.mkdir();
			
			//hide app resource folder
			file = new File(jarFilePath + Constants.APP_NAME + "_lib");
			if(file.exists()) {
				hideFolder(file);
			}
		} catch(Exception e) {
			DBServices.saveErrorLogEntry(e);
		}
	}
	
	private static void hideFolder(File file) {
		try {
			Path path = Paths.get(file.getAbsolutePath());
			Boolean hidden = (Boolean) Files.getAttribute(path, "dos:hidden", LinkOption.NOFOLLOW_LINKS);
			if (hidden != null && !hidden) {
				Files.setAttribute(path, "dos:hidden", Boolean.TRUE, LinkOption.NOFOLLOW_LINKS);
			}
		} catch(Exception e) {
			DBServices.saveErrorLogEntry(e);
		}
	}
	
	public static void setTray(Stage primaryStage) {
		try {
			Platform.setImplicitExit(false);
			primaryStage.setOnCloseRequest(event -> {
				Platform.runLater(() -> {
						primaryStage.hide();
				});
			});
			
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
	        
	        exit.addActionListener(e -> {
				System.exit(0);
			});
	        
	        show.addActionListener(e -> {
				Platform.runLater(() -> {
	                primaryStage.show();
		        });
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
						Platform.runLater(() ->{
			                primaryStage.show();
				        });
					}
				}
			});
	       
	        //Add components to pop-up menu
	        popup.add(show);
	        popup.addSeparator();
	        popup.add(exit);
	        
	        trayIcon.setPopupMenu(popup);
	    } catch(Exception e) {
	    	DBServices.saveErrorLogEntry(e);
	    }
	}
	
	private static LoggedTest getOldestLoggedTest() {
		LoggedTest test = null;
		try {
			Date minimumDate = Calendar.getInstance().getTime();
			List<LoggedTest> tests = DBServices.getAllLoggedTests();
			for(LoggedTest index : tests) {
				if(index.getGenerationDateAndTime().before(minimumDate)) {
					minimumDate = index.getGenerationDateAndTime();
					test = index;
				}
			}
		} catch (Exception e) {
			DBServices.saveErrorLogEntry(e);
		}
		return test;
	}
	
	public static void synchronizeLog() {
		try {
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
		} catch (Exception e) {
			DBServices.saveErrorLogEntry(e);
		}
	}
	
	public static void setTextFieldValidator(TextInputControl textField, String allowedCharacters, String finalPattern, boolean onlyUpperCase, int maxLength, String tooltipText, Stage parentStage) {
		Tooltip tooltip = new Tooltip();
		tooltip.setText(tooltipText);
		tooltip.setWrapText(true);
		tooltip.setMaxWidth(300);
		textField.setTooltip(tooltip);
		Tooltip emptyTooltip = new Tooltip();
		emptyTooltip.setText("Câmpul nu poate fi lăsat gol.");
		
		textField.textProperty().addListener((observable, oldValue, newValue) -> {
			try {
				if(newValue.matches(allowedCharacters)) {
					if(newValue.length() > maxLength && maxLength != Constants.INFINITE) {
						newValue = newValue.substring(0, maxLength);
					}
					if(onlyUpperCase) {
							newValue = newValue.toUpperCase();
					}
					if(textField.getId().equals("ibanCodeField")) {
						String tmp = "";
						String text = newValue;
						text = text.replaceAll(" ", "");
						for(int i = 0; i < text.length(); i+=4) {
							if(text.substring(i, text.length()).length() > 4) {
								tmp += text.substring(i, i+4) + " ";
							} else {
								tmp += text.substring(i, text.length());
							}
						}
						newValue = tmp;
					}
					textField.setText(newValue);
				} else {
					textField.setText(oldValue);
				}
				if(textField.getText().isEmpty()) {
					textField.setBorder(Constants.redBorder);
				} else if(!textField.getText().matches(finalPattern)) {
					textField.setBorder(Constants.redBorder);
				} else {
					textField.setBorder(null);
				}
			} catch(Exception e) {
				DBServices.saveErrorLogEntry(e);
			}
		});
	}
	
	public static List<String> getEmployeeNames(List<Employee> employees) {
		List<String> employeeNames = new ArrayList<String>();
		for(Employee index : employees) {
			employeeNames.add(index.getLastName() + " " + index.getFirstName());
		}
		return employeeNames;
	}
	
	public static Date resetTimeForDate(Date date) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		calendar.set(Calendar.HOUR_OF_DAY, 0);
		calendar.set(Calendar.MINUTE, 0);
		calendar.set(Calendar.SECOND, 0);
		calendar.set(Calendar.MILLISECOND, 0);
		
		return calendar.getTime();
	}

	public static Date getCalculatedDueDate(Date revisionDate, int authorizationExtension) {
		GregorianCalendar calendar = new GregorianCalendar();
		calendar.setTime(revisionDate);
		calendar.add(GregorianCalendar.YEAR, authorizationExtension);
		if(authorizationExtension > 0) {
			calendar.add(GregorianCalendar.DAY_OF_MONTH, -1);
			List<Date> variableDates = DBServices.getVariableVacationDates();
			while(Constants.publicHolidays.contains(calendar.get(GregorianCalendar.DATE) + "-" + (calendar.get(GregorianCalendar.MONTH) + 1)) 
					|| calendar.get(GregorianCalendar.DAY_OF_WEEK) == GregorianCalendar.SUNDAY 
					|| calendar.get(GregorianCalendar.DAY_OF_WEEK) == GregorianCalendar.SATURDAY
					|| variableDates.contains(calendar.getTime())) {
				if(calendar.get(GregorianCalendar.DAY_OF_WEEK) == GregorianCalendar.SATURDAY
						|| Constants.publicHolidays.contains(calendar.get(GregorianCalendar.DATE) + "-" + (calendar.get(GregorianCalendar.MONTH) + 1))
						|| variableDates.contains(calendar.getTime())) {
					calendar.add(GregorianCalendar.DAY_OF_MONTH, -1);
				}
				if(calendar.get(GregorianCalendar.DAY_OF_WEEK) == GregorianCalendar.SUNDAY) {
					calendar.add(GregorianCalendar.DAY_OF_MONTH, -2);
				}
			}
		}
		return calendar.getTime();
	}
	
	public static boolean allFieldsAreFilled(TextInputControl... fields) {
		for(int i = 0; i < fields.length; i++) {
			if(fields[i].getText().isEmpty()) {
				return false;
			}
		}
		return true;
	}
	
	public static boolean allFieldsAreValid(TextInputControl... fields) {
		for(int i = 0; i < fields.length; i++) {
			if(fields[i].getBorder() != null) {
				return false;
			}
		}
		return true;
	}
	
	public static boolean allDatePickersAreFilled(DatePicker... datePicker) {
		for(int i = 0; i < datePicker.length; i++) {
			if(datePicker[i].getValue() == null) {
				return false;
			}
		}
		return true;
	}
	
	public static void generateReadMe() {
		try {
			File file = new File(getJarFilePath() + "ReadMe.txt");
			if(file.createNewFile()) {
				PrintStream out = new PrintStream(new FileOutputStream(file));
				out.println("When starting the app for the first time on a new device, the backup feature must be configured.");
				out.println("A GMail login will be required to backup the database files on Google Drive. The credentials are:\n");
				out.println("Username: rsvti.app@gmail.com");
				out.println("Password: rsvti1234");
				out.close();
			}
		} catch(Exception e) {
			DBServices.saveErrorLogEntry(e);
		}
	}
	
	public static void createDesktopShortcut() {
		try {
			File file = new File(getJarFilePath() + "RSVTIApp_lib\\RSVTI_without_text.ico");
			if(new File(getJarFilePath() + "RSVTIApp_lib").exists()) {
				if(!file.exists()) {
					BufferedImage bi = ImageIO.read(Utils.class.getResource("/RSVTI_without_text.png"));
			        ICOEncoder.write(bi, new File(getJarFilePath() + "RSVTIApp_lib\\RSVTI_without_text.ico"));
				}
			ShellLink sl = ShellLink.createLink(getJarFilePath() + Constants.APP_NAME + ".jar")
					.setIconLocation(getJarFilePath() + "RSVTIApp_lib\\RSVTI_without_text.ico");
				sl.getConsoleData()
					.setFont(mslinks.extra.ConsoleData.Font.Consolas)
					.setFontSize(24)
					.setTextColor(5);
				sl.saveTo(System.getProperty("user.home") + "\\Desktop\\" + Constants.APP_NAME + ".lnk");
			}
		} catch(Exception e) {
			DBServices.saveErrorLogEntry(e);
		}
	}
}
