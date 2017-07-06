package com.rsvti.address.view;

import java.awt.Desktop;
import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Calendar;

import com.rsvti.common.Constants;
import com.rsvti.common.Utils;
import com.rsvti.database.entities.LoggedTest;
import com.rsvti.database.services.DBServices;

import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.SelectionMode;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;

public class TestLogOverviewController {

	@FXML
	private TableView<LoggedTest> loggedTestsTable;
	@FXML
	private TableColumn<LoggedTest,String> employeeFirstNameColumn;
	@FXML
	private TableColumn<LoggedTest,String> employeeLastNameColumn;
	@FXML
	private TableColumn<LoggedTest,String> employeeTitleColumn;
	@FXML
	private TableColumn<LoggedTest,String> firmNameColumn;
	@FXML
	private TableColumn<LoggedTest,String> generationDateAndTimeColumn;
	
	@FXML
	private Button showTestButton;
	
	@FXML
	private void initialize() {
		loggedTestsTable.setItems(FXCollections.observableArrayList(DBServices.getAllLoggedTests()));
		loggedTestsTable.getSelectionModel().selectedItemProperty().addListener((observable, oldvalue, newValue) -> showTestButton.setDisable(false));
		loggedTestsTable.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
		loggedTestsTable.setPlaceholder(new Label(Constants.TABLE_PLACEHOLDER_MESSAGE));
		employeeFirstNameColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getEmployeeFirstName()));
		employeeLastNameColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getEmployeeLastName()));
		employeeTitleColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getEmployeeTitle()));
		firmNameColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getFirmName()));
		generationDateAndTimeColumn.setCellValueFactory(cellData -> new SimpleStringProperty(new SimpleDateFormat(DBServices.getDatePattern() + " HH:mm:ss.SS").format(cellData.getValue().getGenerationDateAndTime())));
		showTestButton.setDisable(true);
	}
	
	@FXML
	private void handleShowTestLogs() {
		SimpleDateFormat dateFormat = new SimpleDateFormat(Constants.DEFAULT_DATE_FORMAT);
		SimpleDateFormat extendedDateFormat = new SimpleDateFormat(Constants.DEFAULT_DATE_FORMAT + " HH.mm.ss");
		String jarFilePath = Utils.getJarFilePath();
		
		for(LoggedTest loggedTest : loggedTestsTable.getSelectionModel().getSelectedItems()) {
			File file = new File(jarFilePath + "docs\\teste\\logs\\" + dateFormat.format(loggedTest.getGenerationDateAndTime())  + "\\"
					+ loggedTest.getEmployeeLastName() + " " + loggedTest.getEmployeeFirstName() + " " + loggedTest.getEmployeeTitle() 
					+ " " + extendedDateFormat.format(loggedTest.getGenerationDateAndTime()) + ".docx");
			try {
				Desktop.getDesktop().open(file);
			} catch(Exception e) {
				e.printStackTrace();
			}
		}
	}
}
