package com.rsvti.address.view;

import java.io.File;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.rsvti.address.JavaFxMain;
import com.rsvti.database.services.DBServices;
import com.rsvti.main.Constants;
import com.rsvti.main.Utils;

import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.DatePicker;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.stage.DirectoryChooser;
import javafx.stage.FileChooser;
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
	
	private SimpleDateFormat dateFormat = new SimpleDateFormat(Constants.DATE_FORMAT);
	
	public SettingsController() {
	}
	
	@FXML
	private void initialize() {
		Utils.setDisabledDaysForDatePicker(datePicker);
		Utils.setDisplayFormatForDatePicker(datePicker);
		datesListView.setItems(FXCollections.observableArrayList(datesToStrings(DBServices.getVariableVacationDates())));
		filePathField.setText(DBServices.getBackupPath());
	}
	
	@FXML
	private void handleAddDate() {
		if(datePicker.getValue() != null) {
			DBServices.saveEntry(java.sql.Date.valueOf(datePicker.getValue()));
			datesListView.setItems(FXCollections.observableArrayList(datesToStrings(DBServices.getVariableVacationDates())));
		}
	}
	
	@FXML
	private void handleDeleteDate() {
		String selectedItem = datesListView.getSelectionModel().getSelectedItem();
		if(selectedItem != null) {
			try {
				DBServices.deleteEntry(dateFormat.parse(datesListView.getSelectionModel().getSelectedItem()));
			} catch(Exception e) {
				e.printStackTrace();
			}
			datesListView.setItems(FXCollections.observableArrayList(datesToStrings(DBServices.getVariableVacationDates())));
		}
	}
	
	private List<String> datesToStrings(List<Date> dates) {
		List<String> dateStrings = new ArrayList<String>(); 
		for(Date index : dates) {
			dateStrings.add(dateFormat.format(index));
		}
		return dateStrings;
	}
	
	@FXML
	private void handleChangeBackupPath() {
		DirectoryChooser directoryChooser = new DirectoryChooser();
		directoryChooser.setTitle("Cale auxiliară pentru fișierele generate");
		directoryChooser.setInitialDirectory(new File(System.getProperty("user.home")));
		String backupPath = directoryChooser.showDialog(new Stage()).getAbsolutePath();
		filePathField.setText(backupPath);
		DBServices.saveBackupPath(backupPath);
	}
	
	public void setJavaFxMain(JavaFxMain javaFxMain) {
		this.javaFxMain = javaFxMain;
	}
}
