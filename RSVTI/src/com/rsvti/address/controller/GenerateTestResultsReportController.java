package com.rsvti.address.controller;

import java.awt.Desktop;
import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import com.rsvti.address.JavaFxMain;
import com.rsvti.common.Constants;
import com.rsvti.common.Utils;
import com.rsvti.database.entities.Employee;
import com.rsvti.database.entities.EmployeeTestResults;
import com.rsvti.database.entities.Firm;
import com.rsvti.database.services.DBServices;
import com.rsvti.generator.Generator;

import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.geometry.Pos;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ButtonType;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.ComboBoxTableCell;
import javafx.util.converter.DefaultStringConverter;

public class GenerateTestResultsReportController {
	@FXML
	private JavaFxMain javaFxMain;
	
	@FXML
	private TableView<Firm> firmTable;
	@FXML
	private TableColumn<Firm, String> firmColumn;
	
	@FXML
	private TableView<EmployeeTestResults> testResultsTable;
	@FXML
	private TableColumn<EmployeeTestResults, String> employeeNameColumn;
	@FXML
	private TableColumn<EmployeeTestResults, String> theoryResultsColumn;
	@FXML
	private TableColumn<EmployeeTestResults, String> practiseResultsColumn;
	
	@FXML
	private ComboBox<String> employeeTitleComboBox;
	@FXML
	private TextField registrationNumberField;
	@FXML
	private DatePicker registrationDate;
	@FXML
	private ComboBox<String> rsvtiComboBox;
	
	@FXML
	private CheckBox choice1;
	@FXML
	private CheckBox choice2;
	@FXML
	private CheckBox choice3;
	@FXML
	private CheckBox choice4;
	
	private List<String> employeeList = new ArrayList<>();
	
	@FXML
	public void initialize() {
		try {
			Utils.setDisabledDaysForDatePicker(registrationDate);
			Utils.setDisplayFormatForDatePicker(registrationDate);
			firmTable.setItems(FXCollections.observableArrayList(DBServices.getAllFirms()));
			firmTable.getSelectionModel().selectedItemProperty().addListener((observable, oldvalue, newValue) -> {
				employeeList = Utils.getEmployeeNames(getEmployeesByTitle(newValue.getEmployees(), employeeTitleComboBox.getSelectionModel().getSelectedItem()));
				employeeNameColumn.setCellFactory(ComboBoxTableCell.forTableColumn(new DefaultStringConverter(),
						FXCollections.observableArrayList(employeeList)));
				testResultsTable.setItems(FXCollections.observableArrayList(getEmptyEmployees(
						getEmployeesByTitle(newValue.getEmployees(), employeeTitleComboBox.getSelectionModel().getSelectedItem()).size())));
				theoryResultsColumn.setCellFactory(ComboBoxTableCell.forTableColumn(new DefaultStringConverter(),
						FXCollections.observableArrayList("admis", "respins")));
				practiseResultsColumn.setCellFactory(ComboBoxTableCell.forTableColumn(new DefaultStringConverter(),
						FXCollections.observableArrayList("admis", "respins")));
			});
			firmColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getFirmName()));
			employeeTitleComboBox.setItems(FXCollections.observableArrayList("manevrant", "legător sarcină"));
			employeeTitleComboBox.getSelectionModel().select(0);
			choice1.setText("transpalet hidraulic");
			choice2.setText("stivuitor manual");
			choice3.setText("palan manual (Qmax <= 1t)");
			choice4.setText("electropalan (Qmax <= 1t)");
			employeeTitleComboBox.setOnAction(event -> {
				if(employeeTitleComboBox.getSelectionModel().getSelectedItem().equals("manevrant")) {
					choice1.setText("transpalet hidraulic");
					choice1.selectedProperty().set(false);
					choice2.setText("stivuitor manual");
					choice2.selectedProperty().set(false);
					choice3.setText("palan manual (Qmax <= 1t)");
					choice3.selectedProperty().set(false);
					choice4.setText("electropalan (Qmax <= 1t)");
					choice4.selectedProperty().set(false);
					choice4.setVisible(true);
				} else {
					choice1.setText("pod rulant");
					choice1.selectedProperty().set(false);
					choice2.setText("macara portal");
					choice2.selectedProperty().set(false);
					choice3.setText("electropalan");
					choice3.selectedProperty().set(false);
					choice4.setVisible(false);
				}
				employeeList = Utils.getEmployeeNames(getEmployeesByTitle(
						firmTable.getSelectionModel().getSelectedItem().getEmployees(), 
						employeeTitleComboBox.getSelectionModel().getSelectedItem()));
				employeeNameColumn.setCellFactory(ComboBoxTableCell.forTableColumn(new DefaultStringConverter(),
						FXCollections.observableArrayList(employeeList)));
				testResultsTable.setItems(FXCollections.observableArrayList(getEmptyEmployees(
						getEmployeesByTitle(
								firmTable.getSelectionModel().getSelectedItem().getEmployees(), 
								employeeTitleComboBox.getSelectionModel().getSelectedItem()).size())));
			});
			Utils.setTextFieldValidator(registrationNumberField, "[0-9]*", "[0-9]+", false, Constants.INFINITE, 
					"Numărul de înregistrare poate conține doar cifre.", JavaFxMain.primaryStage);
			registrationNumberField.setAlignment(Pos.CENTER);
			firmTable.getSelectionModel().selectedItemProperty().addListener((observable, oldvalue, newValue) -> {
				if(firmTable.getSelectionModel().getSelectedItem() != null) {
					rsvtiComboBox.setItems(FXCollections.observableArrayList(Utils.getEmployeeNames(
							DBServices.getRsvtiFromFirm(firmTable.getSelectionModel().getSelectedItem().getFirmName()))));
					rsvtiComboBox.getSelectionModel().select(0);
				}
			});
			employeeNameColumn.setCellFactory(ComboBoxTableCell.forTableColumn(FXCollections
	                .observableArrayList()));
			employeeNameColumn.setOnEditCommit(event -> {
				event.getRowValue().setPersonalIdentificationNumber(
						DBServices.getPersonalIdentificationNumberForEmployee(
								event.getNewValue().substring(0, event.getNewValue().indexOf(" ")),
								event.getNewValue().substring(event.getNewValue().indexOf(" ") + 1, event.getNewValue().length())));
			});
			theoryResultsColumn.setOnEditCommit(event -> {
				event.getRowValue().setTheoryResult(event.getNewValue());
			});
			practiseResultsColumn.setOnEditCommit(event -> {
				event.getRowValue().setPractiseResult(event.getNewValue());
			});
			testResultsTable.setEditable(true);
		} catch (Exception e) {
			DBServices.saveErrorLogEntry(e);
		}
	}
	
	@FXML
	public void handleGenerateTestResultsReport() {
		try {
			Firm firm = firmTable.getSelectionModel().getSelectedItem();
			if(firm != null && registrationNumberField.getBorder() == null && !registrationNumberField.getText().isEmpty() && registrationDate.getValue() != null) {
				String bodyMessage;
				if(DBServices.getBackupPath().equals("")) {
					bodyMessage = "Fișierul se poate găsi in docs\\adeverințe.\nDoriți să vizualizați fișierul generat?";
				} else {
					bodyMessage = "Fișierul se poate găsi in docs\\adeverințe. Sau în " + DBServices.getBackupPath() + "\\adeverințe\nDoriți să vizualizați fișierul generat?";
				}
				
				File file = Generator.generateTestResultsReport(
						firm, 
						registrationNumberField.getText(), 
						java.sql.Date.valueOf(registrationDate.getValue()), 
						employeeTitleComboBox.getSelectionModel().getSelectedItem(),
						new ArrayList<EmployeeTestResults>(removeEmptyEmployees(testResultsTable.getItems())),
						choice1,
						choice2,
						choice3,
						choice4,
						rsvtiComboBox.getSelectionModel().getSelectedItem());
				Optional<ButtonType> choice = Utils.alert(AlertType.INFORMATION, "Generare Rezultate Examinare", "Generararea s-a terminat cu succes", bodyMessage);
				if(choice.get().getButtonData() == ButtonType.YES.getButtonData()) {
					Desktop.getDesktop().open(file);
				}
			}
		} catch (Exception e) {
			DBServices.saveErrorLogEntry(e);
		}
	}
	
	private List<Employee> getEmployeesByTitle(List<Employee> employees, String title) {
		List<Employee> tmp = new ArrayList<>();
		for(Employee index : employees) {
			if(index.getTitle().equals(title)) {
				tmp.add(index);
			}
		}
		return tmp;
	}
	
	private List<EmployeeTestResults> getEmptyEmployees(int length) {
		List<EmployeeTestResults> tmp = new ArrayList<>();
		for(int i = 0; i < length; i++) {
			tmp.add(new EmployeeTestResults("", "", "", ""));
		}
		return tmp;
	}
	
	private List<EmployeeTestResults> removeEmptyEmployees(List<EmployeeTestResults> employees) {
		for(EmployeeTestResults index : employees) {
			if(index.isEmpty()) {
				employees.remove(index);
			}
		}
		return employees;
	}
	
	public void setJavaFxMain(JavaFxMain javaFxMain) {
		this.javaFxMain = javaFxMain;
	}

}
