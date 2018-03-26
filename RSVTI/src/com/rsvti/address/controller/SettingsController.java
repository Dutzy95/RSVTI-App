package com.rsvti.address.controller;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import com.rsvti.address.JavaFxMain;
import com.rsvti.common.Utils;
import com.rsvti.database.services.DBServices;

import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.stage.DirectoryChooser;
import javafx.stage.Stage;

public class SettingsController {
	
	@FXML
	private JavaFxMain javaFxMain;
	
	@FXML
	private TextField filePathField;
	
	@FXML
	private ListView<String> datesListView;
	@FXML
	private DatePicker datePicker;
	
	@FXML
	private TextField homeDateIntervalField;
	@FXML
	private ComboBox<String> homeDateIntervalUnitComboBox;
	
	@FXML
	private ComboBox<String> dateFormatChooser;
	
	@FXML
	private TextField maximumLogSize;
	
	private SimpleDateFormat dateFormat = new SimpleDateFormat(DBServices.getDatePattern());
	
	private Stage stage;
	private HomeController homeController;
	
	private String backupPath;
	
	@FXML
	private void initialize() {
		try {
			Utils.setDisabledDaysForDatePicker(datePicker);
			Utils.setDisplayFormatForDatePicker(datePicker);
			datesListView.setItems(FXCollections.observableArrayList(datesToStrings(DBServices.getVariableVacationDates())));
			filePathField.setText(DBServices.getBackupPath());
			homeDateIntervalUnitComboBox.setItems(FXCollections.observableArrayList(Arrays.asList("zile", "luni", "ani")));
			homeDateIntervalField.setText(DBServices.getHomeDateDisplayInterval().split(" ")[0]);
			if(!DBServices.getHomeDateDisplayInterval().equals("")) {
				switch(Integer.parseInt(DBServices.getHomeDateDisplayInterval().split(" ")[1])) {
				case Calendar.DATE : {
					homeDateIntervalUnitComboBox.getSelectionModel().select(0);
					break;
				}
				case Calendar.MONTH : {
					homeDateIntervalUnitComboBox.getSelectionModel().select(1);
					break;
				}
				case Calendar.YEAR : {
					homeDateIntervalUnitComboBox.getSelectionModel().select(2);
					break;
				}
				}
			} else {
				homeDateIntervalUnitComboBox.getSelectionModel().select(0);
			}
			
			Utils.setTextFieldValidator(homeDateIntervalField, "[0-9]*", "[0-9]{1,2}", false, 2,
					"Numărul de zile, luni sau ani pentru care se vor afișa date in graficul de pe pagina pricipală.", JavaFxMain.primaryStage);
			
			Calendar calendar = Calendar.getInstance();
			List<String> patterns = Arrays.asList(
					"dd-MM-yyyy",
					"dd-MMM-yyyy",
					"dd-MMMM-yyyy",
					"dd-MMMM-yy",
					"dd/MM/yyyy",
					"dd/MMM/yyyy",
					"dd/MMMM/yyyy",
					"dd/MMMM/yy",
					"dd MM yyyy",
					"dd MMM yyyy",
					"dd MMMM yyyy",
					"dd MMMM yy");
			List<String> formattedDates = new ArrayList<String>();
			for(String index : patterns) {
				formattedDates.add(new SimpleDateFormat(index).format(calendar.getTime()));
			}
			dateFormatChooser.setItems(FXCollections.observableArrayList(formattedDates));
			dateFormatChooser.getSelectionModel().select(new SimpleDateFormat(DBServices.getDatePattern()).format(calendar.getTime()));
			dateFormatChooser.setOnAction(event -> {
				try {
					DBServices.saveDatePattern(patterns.get(dateFormatChooser.getSelectionModel().getSelectedIndex()));
					//refresh settings stage in real time
					Utils.setDisabledDaysForDatePicker(datePicker);
					Utils.setDisplayFormatForDatePicker(datePicker);
					datesListView.setItems(FXCollections.observableArrayList(datesToStrings(DBServices.getVariableVacationDates())));
					datesListView.refresh();
				} catch (Exception e) {
					DBServices.saveErrorLogEntry(e);
				}
			});
			
			maximumLogSize.setText(DBServices.getMaximumLogSize() + "");
			Utils.setTextFieldValidator(maximumLogSize, "[0-9]*", "[0-9]{1,3}", false, 3,
					"Numărul de teste care se vor ține minte indiferent, dacă acestea există sau nu pe disc.", JavaFxMain.primaryStage);
		} catch (Exception e) {
			DBServices.saveErrorLogEntry(e);
		}
	}
	
	@FXML
	private void handleAddDate() {
		try {
			if(datePicker.getValue() != null) {
				DBServices.saveEntry(java.sql.Date.valueOf(datePicker.getValue()));
				datesListView.setItems(FXCollections.observableArrayList(datesToStrings(DBServices.getVariableVacationDates())));
				Utils.setDisabledDaysForDatePicker(datePicker);
			}
		} catch (Exception e) {
			DBServices.saveErrorLogEntry(e);
		}
	}
	
	@FXML
	private void handleDeleteDate() {
		try {
			String selectedItem = datesListView.getSelectionModel().getSelectedItem();
			if(selectedItem != null) {
				try {
					DBServices.deleteEntry(dateFormat.parse(datesListView.getSelectionModel().getSelectedItem()));
					datesListView.setItems(FXCollections.observableArrayList(datesToStrings(DBServices.getVariableVacationDates())));
					Utils.setDisabledDaysForDatePicker(datePicker);
				} catch(Exception e) {
					e.printStackTrace();
				}
			}
		} catch (Exception e) {
			DBServices.saveErrorLogEntry(e);
		}
	}
	
	private List<String> datesToStrings(List<Date> dates) {
		List<String> dateStrings = new ArrayList<String>();
		dateFormat = new SimpleDateFormat(DBServices.getDatePattern());
		for(Date index : dates) {
			dateStrings.add(dateFormat.format(index));
		}
		return dateStrings;
	}
	
	@FXML
	private void handleChangeBackupPath() {
		try {
			DirectoryChooser directoryChooser = new DirectoryChooser();
			directoryChooser.setTitle("Cale auxiliară pentru fișierele generate");
			directoryChooser.setInitialDirectory(new File(System.getProperty("user.home")));
			File directory;
			if((directory = directoryChooser.showDialog(new Stage())) != null) {
				backupPath = directory.getAbsolutePath();
				filePathField.setText(backupPath);
			}
		} catch (Exception e) {
			DBServices.saveErrorLogEntry(e);
		}
	}
	
	@FXML
	private void handleSave() {
		try {
			//save back-up path
			if(backupPath != null) {
				DBServices.saveBackupPath(backupPath);	
			}
			//set home date interval for due dates' graph
			int homeDateIntervalValue = Integer.parseInt(homeDateIntervalField.getText());
			String homeDateIntervalUnit = homeDateIntervalUnitComboBox.getValue(); 
			switch(homeDateIntervalUnit) {
			case "zile": {
				DBServices.saveHomeDateDisplayInterval(homeDateIntervalValue, Calendar.DATE);
				break;
			}
			case "luni": {
				DBServices.saveHomeDateDisplayInterval(homeDateIntervalValue, Calendar.MONTH);
				break;
			}
			case "ani": {
				DBServices.saveHomeDateDisplayInterval(homeDateIntervalValue, Calendar.YEAR);
				break;
			}
			}
			//set maximum log size
			int maxLogSize = Integer.parseInt(maximumLogSize.getText());
			DBServices.saveMaximumLogSize(maxLogSize);
			Utils.synchronizeLog();
			
			homeController.refresh();
			stage.close();
		} catch(Exception e) {
			DBServices.saveErrorLogEntry(e);
		}
	}
	
	@FXML
	private void handleCancel() {
		try {
			stage.close();
		} catch (Exception e) {
			DBServices.saveErrorLogEntry(e);
		}
	}
	
	public void setStage(Stage stage) {
		this.stage = stage;
	}
	
	public void setHomeController(HomeController homeController) {
		this.homeController = homeController;
	}
	
	public void setJavaFxMain(JavaFxMain javaFxMain) {
		this.javaFxMain = javaFxMain;
	}
}
