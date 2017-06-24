package com.rsvti.address.view;

import java.awt.event.InputMethodEvent;
import java.io.File;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import com.rsvti.address.JavaFxMain;
import com.rsvti.database.services.DBServices;
import com.rsvti.main.Constants;
import com.rsvti.main.Utils;

import javafx.beans.value.ChangeListener;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.event.EventDispatchChain;
import javafx.event.EventDispatcher;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
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
	
	@FXML
	private TextField homeDateIntervalField;
	@FXML
	private ComboBox<String> homeDateIntervalUnit;
	
	private SimpleDateFormat dateFormat = new SimpleDateFormat(Constants.DATE_FORMAT);
	
	public SettingsController() {
	}
	
	@FXML
	private void initialize() {
		Utils.setDisabledDaysForDatePicker(datePicker);
		Utils.setDisplayFormatForDatePicker(datePicker);
		datesListView.setItems(FXCollections.observableArrayList(datesToStrings(DBServices.getVariableVacationDates())));
		filePathField.setText(DBServices.getBackupPath());
		homeDateIntervalField.setText(DBServices.getHomeDateDisplayInterval().split(" ")[0]);
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
		homeDateIntervalUnit.setItems(FXCollections.observableArrayList(Arrays.asList("zile", "luni", "ani")));
		homeDateIntervalUnit.getSelectionModel().select(0);
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
	
	@FXML
	private void handleSetHomeDateDisplayInterval() {
		
	}
	
	public void setJavaFxMain(JavaFxMain javaFxMain) {
		this.javaFxMain = javaFxMain;
	}
}
