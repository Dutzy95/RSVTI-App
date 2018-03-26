package com.rsvti.address.controller;

import java.awt.Desktop;
import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import com.rsvti.address.JavaFxMain;
import com.rsvti.common.Constants;
import com.rsvti.common.Utils;
import com.rsvti.database.entities.EmployeeWithDetails;
import com.rsvti.database.services.DBServices;
import com.rsvti.generator.Generator;

import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.SelectionMode;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;

public class GenerateTestController {

	@FXML
	private JavaFxMain javaFxMain;
	
	@FXML
	private TableView<EmployeeWithDetails> employeeTable;
	@FXML
	private TableColumn<EmployeeWithDetails,String> firmNameColumn;
	@FXML
	private TableColumn<EmployeeWithDetails,String> firstNameColumn;
	@FXML
	private TableColumn<EmployeeWithDetails,String> lastNameColumn;
	@FXML
	private TableColumn<EmployeeWithDetails,String> titleColumn;
	
	@FXML
	private TextField numberOfQuestionsField;
	
	@FXML
	private Button generateButton;
	
	@FXML
	private void initialize() {
		try {
			employeeTable.setItems(FXCollections.observableArrayList(DBServices.getEmployeesBetweenDateInterval(Constants.LOW_DATE, Constants.HIGH_DATE)));
			firmNameColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getFirmName()));
			firstNameColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getEmployee().getFirstName()));
			lastNameColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getEmployee().getLastName()));
			titleColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getEmployee().getTitle()));
			generateButton.setDisable(true);
			employeeTable.getSelectionModel().selectedItemProperty().addListener(event -> {generateButton.setDisable(false);});
			employeeTable.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
			employeeTable.setPlaceholder(new Label(Constants.TABLE_PLACEHOLDER_MESSAGE));
		} catch (Exception e) {
			DBServices.saveErrorLogEntry(e);
		}
	}
	
	@FXML
	private void handleGenerate() {
		try {
			List<EmployeeWithDetails> selection = employeeTable.getSelectionModel().getSelectedItems();
			List<File> files = new ArrayList<File>();
			for(EmployeeWithDetails index : selection) {
				files.add(Generator.generateTest(Integer.parseInt(numberOfQuestionsField.getText()), index));
			}
			
			String bodyMessage;
			if(DBServices.getBackupPath().equals("")) {
				bodyMessage = "Fișierele se pot găsi in docs\\teste.\nDoriți să vizualizați fișierele generate?";
			} else {
				bodyMessage = "Fișierele se pot găsi in docs\\teste. Sau în " + DBServices.getBackupPath() + "\\teste\nDoriți să vizualizați fișierele generate?";
			}
			
			Optional<ButtonType> choice = Utils.alert(AlertType.INFORMATION, "Generare Test", "Generararea s-a terminat cu succes", bodyMessage, true);
			if(choice.get().getButtonData() == ButtonType.YES.getButtonData()) {
				for(File file : files) {
					Desktop.getDesktop().open(file);
				}
			}
			
			Utils.synchronizeLog();
		} catch (Exception e) {
			DBServices.saveErrorLogEntry(e);
		}
	}
	
	public void setJavaFxMain(JavaFxMain javaFxMain) {
		this.javaFxMain = javaFxMain;
	}
}
