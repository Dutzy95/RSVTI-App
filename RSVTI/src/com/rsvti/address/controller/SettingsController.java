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
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.geometry.Pos;
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
	private ComboBox<String> homeDateIntervalUnit;
	
	@FXML
	private ComboBox<String> dateFormatChooser;
	
	@FXML
	private TextField maximumLogSize;
	
	private SimpleDateFormat dateFormat = new SimpleDateFormat(DBServices.getDatePattern());
	
	private Stage stage;
	
	private String backupPath; 
	
	@FXML
	private void initialize() {
		try {
			Utils.setDisabledDaysForDatePicker(datePicker);
			Utils.setDisplayFormatForDatePicker(datePicker);
			datesListView.setItems(FXCollections.observableArrayList(datesToStrings(DBServices.getVariableVacationDates())));
			filePathField.setText(DBServices.getBackupPath());
			homeDateIntervalUnit.setItems(FXCollections.observableArrayList(Arrays.asList("zile", "luni", "ani")));
			homeDateIntervalField.setText(DBServices.getHomeDateDisplayInterval().split(" ")[0]);
			if(!DBServices.getHomeDateDisplayInterval().equals("")) {
				switch(Integer.parseInt(DBServices.getHomeDateDisplayInterval().split(" ")[1])) {
				case Calendar.DATE : {
					homeDateIntervalUnit.getSelectionModel().select(0);
					break;
				}
				case Calendar.MONTH : {
					homeDateIntervalUnit.getSelectionModel().select(1);
					break;
				}
				case Calendar.YEAR : {
					homeDateIntervalUnit.getSelectionModel().select(2);
					break;
				}
				}
			} else {
				homeDateIntervalUnit.getSelectionModel().select(0);
			}
			homeDateIntervalUnit.setOnAction(new EventHandler<ActionEvent>() {
				@Override
				public void handle(ActionEvent event) {
					if(!homeDateIntervalField.getText().equals("")) {
						switch(homeDateIntervalUnit.getValue()) {
						case "zile": {
							DBServices.saveHomeDateDisplayInterval(Integer.parseInt(homeDateIntervalField.getText()), Calendar.DATE);
							break;
						}
						case "luni": {
							DBServices.saveHomeDateDisplayInterval(Integer.parseInt(homeDateIntervalField.getText()), Calendar.MONTH);
							break;
						}
						case "ani": {
							DBServices.saveHomeDateDisplayInterval(Integer.parseInt(homeDateIntervalField.getText()), Calendar.YEAR);
							break;
						}
						}
					}
				}
			});
			homeDateIntervalField.textProperty().addListener((observable, oldvalue, newvalue) -> {
					if(!newvalue.equals("")) {
						switch(homeDateIntervalUnit.getValue()) {
						case "zile": {
							DBServices.saveHomeDateDisplayInterval(Integer.parseInt(newvalue), Calendar.DATE);
							break;
						}
						case "luni": {
							DBServices.saveHomeDateDisplayInterval(Integer.parseInt(newvalue), Calendar.MONTH);
							break;
						}
						case "ani": {
							DBServices.saveHomeDateDisplayInterval(Integer.parseInt(newvalue), Calendar.YEAR);
							break;
						}
						}
					}
			});
			
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
			dateFormatChooser.setOnAction(new EventHandler<ActionEvent>() {
				@Override
				public void handle(ActionEvent event) {
					DBServices.saveDatePattern(patterns.get(dateFormatChooser.getSelectionModel().getSelectedIndex()));
					//refresh settings stage in real time
					Utils.setDisabledDaysForDatePicker(datePicker);
					Utils.setDisplayFormatForDatePicker(datePicker);
					datesListView.setItems(FXCollections.observableArrayList(datesToStrings(DBServices.getVariableVacationDates())));
					datesListView.refresh();
				}
			});
			
			maximumLogSize.setAlignment(Pos.CENTER);
			homeDateIntervalField.setAlignment(Pos.CENTER);
			
			maximumLogSize.setText(DBServices.getMaximumLogSize() + "");
			maximumLogSize.textProperty().addListener((observable, oldValue, newValue) -> {
				DBServices.saveMaximumLogSize(Integer.parseInt(maximumLogSize.getText()));
				Utils.synchronizeLog();
			});
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
				} catch(Exception e) {
					e.printStackTrace();
				}
				datesListView.setItems(FXCollections.observableArrayList(datesToStrings(DBServices.getVariableVacationDates())));
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
		DBServices.saveBackupPath(backupPath);	//save back-up path
		stage.close();
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
	
	public void setJavaFxMain(JavaFxMain javaFxMain) {
		this.javaFxMain = javaFxMain;
	}
}
