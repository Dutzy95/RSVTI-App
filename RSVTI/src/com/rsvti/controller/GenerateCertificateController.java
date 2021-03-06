package com.rsvti.controller;

import java.awt.Desktop;
import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import com.rsvti.JavaFxMain;
import com.rsvti.common.Constants;
import com.rsvti.common.Utils;
import com.rsvti.generator.Generator;
import com.rsvti.model.database.DBServices;
import com.rsvti.model.entities.EmployeeWithDetails;

import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ButtonType;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;

public class GenerateCertificateController {
	
	@FXML
	private JavaFxMain javaFxMain;
	
	@FXML
	private ComboBox<String> employeeTitleComboBox;
	@FXML
	private TextField registrationNumberField;
	@FXML
	private DatePicker registrationDate;
	@FXML
	private DatePicker certificateIssueDate;
	
	@FXML
	private TableView<EmployeeWithDetails> employeeTable;
	@FXML
	private TableColumn<EmployeeWithDetails, String> employeeFirstNameColumn;
	@FXML
	private TableColumn<EmployeeWithDetails, String> employeeLastNameColumn;
	@FXML
	private TableColumn<EmployeeWithDetails, String> employeeFirmNameColumn;
	
	@FXML
	private GridPane machineChoiceGridPane;
	@FXML
	private Label machineChoiceLabel;
	@FXML
	private CheckBox choice1;
	@FXML
	private CheckBox choice2;
	@FXML
	private CheckBox choice3;
	@FXML
	private CheckBox choice4;
	
	@FXML
	private ComboBox<String> rsvtiComboBox;
	
	@FXML
	private void initialize() {
		try {
			employeeTitleComboBox.setItems(FXCollections.observableArrayList("manevrant", "legător sarcină"));
			employeeTitleComboBox.getSelectionModel().select(0);
			Utils.setDisabledDaysForDatePicker(registrationDate);
			Utils.setDisplayFormatForDatePicker(registrationDate);
			Utils.setDisabledDaysForDatePicker(certificateIssueDate);
			Utils.setDisplayFormatForDatePicker(certificateIssueDate);
			List<EmployeeWithDetails> employees = DBServices.getEmployeesBetweenDateInterval(
					Constants.LOW_DATE,
					Constants.HIGH_DATE,
					(e1, e2) -> e1.getEmployee().getLastName().compareTo(e2.getEmployee().getLastName())); 
			employeeTable.setItems(FXCollections.observableArrayList(getEmployeesByTitle(employees, employeeTitleComboBox.getSelectionModel().getSelectedItem())));
			employeeTable.getSelectionModel().selectedItemProperty().addListener((observable, oldvalue, newValue) -> {
				if(employeeTable.getSelectionModel().getSelectedItem() != null) {
					rsvtiComboBox.setItems(FXCollections.observableArrayList(Utils.getEmployeeNames(
							DBServices.getRsvtiFromFirm(employeeTable.getSelectionModel().getSelectedItem().getFirmName()))));
					rsvtiComboBox.getSelectionModel().select(0);
				}
			});
			employeeFirstNameColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getEmployee().getFirstName()));
			employeeLastNameColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getEmployee().getLastName()));
			employeeFirmNameColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getFirmName()));
			employeeTitleComboBox.setOnAction(e -> {
				try {
					employeeTable.setItems(FXCollections.observableArrayList(getEmployeesByTitle(employees, employeeTitleComboBox.getSelectionModel().getSelectedItem())));
					employeeTable.refresh();
					if (employeeTitleComboBox.getSelectionModel().getSelectedItem().equals("manevrant")) {
						machineChoiceGridPane.setVisible(true);
						machineChoiceLabel.setVisible(true);
					} else {
						machineChoiceGridPane.setVisible(false);
						machineChoiceLabel.setVisible(false);
					}
				} catch (Exception err) {
					DBServices.saveErrorLogEntry(err);
				}
			});
			Utils.setTextFieldValidator(registrationNumberField, "[0-9]*", "[0-9]+", false, Constants.INFINITE, 
					"Numărul de înregistrare poate conține doar cifre.", JavaFxMain.primaryStage);
		} catch (Exception e) {
			DBServices.saveErrorLogEntry(e);
		}
	}
	
	private List<EmployeeWithDetails> getEmployeesByTitle(List<EmployeeWithDetails> employees, String title) {
		List<EmployeeWithDetails> tmp = new ArrayList<>();
		for(EmployeeWithDetails index : employees) {
			if(index.getEmployee().getTitle().equals(title)) {
				tmp.add(index);
			}
		}
		return tmp;
	}
	
	@FXML
	private void handleGenerateCertificate() {
		try {
			EmployeeWithDetails employee = employeeTable.getSelectionModel().getSelectedItem();
			if(employee != null && registrationNumberField.getBorder() == null && !registrationNumberField.getText().isEmpty()
					&& registrationDate.getValue() !=null && certificateIssueDate != null) {
				String bodyMessage;
				if(DBServices.getBackupPath().equals("")) {
					bodyMessage = "Fișierul se poate găsi in docs\\adeverințe.\nDoriți să vizualizați fișierul generat?";
				} else {
					bodyMessage = "Fișierul se poate găsi in docs\\adeverințe. Sau în " + DBServices.getBackupPath() + "\\adeverințe\nDoriți să vizualizați fișierul generat?";
				}
				File file = Generator.generateCertificate(
						employee, 
						Integer.parseInt(registrationNumberField.getText()), 
						java.sql.Date.valueOf(registrationDate.getValue()), 
						java.sql.Date.valueOf(certificateIssueDate.getValue()),
						choice1.selectedProperty().get(),
						choice2.selectedProperty().get(),
						choice3.selectedProperty().get(),
						choice4.selectedProperty().get(),
						rsvtiComboBox.getSelectionModel().getSelectedItem(),
						employee.getExecutiveName());
				
				Optional<ButtonType> choice = Utils.alert(AlertType.INFORMATION, "Generare Adeverință", "Generararea s-a terminat cu succes", bodyMessage, true);
				if(choice.get().getButtonData() == ButtonType.YES.getButtonData()) {
					Desktop.getDesktop().open(file);
				}
				
			}
		} catch (Exception e) {
			DBServices.saveErrorLogEntry(e);
		}
	}
	
	public void setJavaFxMain(JavaFxMain javaFxMain) {
		this.javaFxMain = javaFxMain;
	}

}
