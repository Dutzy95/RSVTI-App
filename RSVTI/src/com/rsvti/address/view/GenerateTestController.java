package com.rsvti.address.view;

import java.awt.Desktop;
import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import com.rsvti.address.JavaFxMain;
import com.rsvti.database.entities.EmployeeDueDateDetails;
import com.rsvti.database.services.DBServices;
import com.rsvti.generator.Generator;
import com.rsvti.main.Constants;
import com.rsvti.main.Utils;

import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.SelectionMode;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;

public class GenerateTestController {

	@FXML
	private JavaFxMain javaFxMain;
	
	@FXML
	private TableView<EmployeeDueDateDetails> employeeTable;
	@FXML
	private TableColumn<EmployeeDueDateDetails,String> firmNameColumn;
	@FXML
	private TableColumn<EmployeeDueDateDetails,String> firstNameColumn;
	@FXML
	private TableColumn<EmployeeDueDateDetails,String> lastNameColumn;
	@FXML
	private TableColumn<EmployeeDueDateDetails,String> titleColumn;
	
	@FXML
	private TextField numberOfQuestionsField;
	
	@FXML
	private Button generateButton;
	
	public GenerateTestController() {
	}
	
	@FXML
	private void initialize() {
		employeeTable.setItems(FXCollections.observableArrayList(DBServices.getEmployeesBetweenDateInterval(Constants.LOW_DATE, Constants.HIGH_DATE)));
		firmNameColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getFirmName()));
		firstNameColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getEmployee().getFirstName()));
		lastNameColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getEmployee().getLastName()));
		titleColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getEmployee().getTitle()));
		generateButton.setDisable(true);
		employeeTable.getSelectionModel().selectedItemProperty().addListener(event -> {generateButton.setDisable(false);});
		employeeTable.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
	}
	
	@FXML
	private void handleGenerate() {
		List<EmployeeDueDateDetails> selection = employeeTable.getSelectionModel().getSelectedItems();
		List<File> files = new ArrayList<File>();
		for(EmployeeDueDateDetails index : selection) {
			files.add(Generator.generateTest(Integer.parseInt(numberOfQuestionsField.getText()), index));
		}
		
		String bodyMessage;
		if(DBServices.getBackupPath().equals("")) {
			bodyMessage = "Fisierele se pot găsi in docs/teste.\nDoriți să vizualizați fișierele generate?";
		} else {
			bodyMessage = "Fisierele se pot găsi in docs/teste. Sau în " + DBServices.getBackupPath() + "/teste\nDoriți să vizualizați fișierele generate?";
		}
		
		Optional<ButtonType> choice = Utils.alert(AlertType.INFORMATION, "Generare Test", "Generararea s-a terminat cu succes", bodyMessage);
		if(choice.get().getButtonData() == ButtonType.YES.getButtonData()) {
			for(File file : files) {
				try {
					Desktop.getDesktop().open(file);
				} catch(Exception e) {
					e.printStackTrace();
				}
			}
		}
	}
	
	public void setJavaFxMain(JavaFxMain javaFxMain) {
		this.javaFxMain = javaFxMain;
	}
}
