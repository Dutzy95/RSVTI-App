package com.rsvti.address.controller;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;

import com.rsvti.address.JavaFxMain;
import com.rsvti.common.Constants;
import com.rsvti.common.Utils;
import com.rsvti.database.entities.Employee;
import com.rsvti.database.entities.EmployeeAuthorization;
import com.rsvti.database.entities.EmployeeWithDetails;
import com.rsvti.database.services.DBServices;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ChangeListener;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.SplitPane;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.TextInputControl;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class AddEmployeesToFirmController {

	private JavaFxMain javaFxMain;
	
	@FXML
	private VBox vBox;
	@FXML
	private AnchorPane anchorPane;
	@FXML
	private AnchorPane leftSide;
	@FXML
	private SplitPane splitPane;
	@FXML
	private TableView<EmployeeWithDetails> employeeTable;
	@FXML
	private TableColumn<EmployeeWithDetails, String> firmNameColumn;
	@FXML
	private TableColumn<EmployeeWithDetails, String> employeeNameColumn;
	
	@FXML
	private TextField lastNameField;
	@FXML
	private TextField firstNameField;
	@FXML
	private TextField idCodeField;
	@FXML
	private TextField idNumberField;
	@FXML
	private TextField personalIdentificationNumberField;
	@FXML
	private TextField titleField;
	@FXML
	private TextField authorizationNumberField;
	@FXML
	private DatePicker authorizationObtainigDate;
	@FXML
	private DatePicker authorizationDueDate;
	@FXML
	private DatePicker birthDate;
	@FXML
	private TextField birthCityField;
	@FXML
	private TextArea homeAddressField;
	@FXML
	private TextField homeRegionField;
	@FXML
	private CheckBox rsvtiCheckbox;
	@FXML
	private Button saveButton;
	
	private Employee employeeToUpdate;
	private boolean updateForFirm = false;
	private boolean isDueDateUpdate = false;
	private String firmId;
	private boolean update;
	
	@FXML
	private void initialize() {
		try {
			Utils.setDisabledDaysForDatePicker(authorizationObtainigDate);
			Utils.setDisplayFormatForDatePicker(authorizationObtainigDate);
			Utils.setDisabledDaysForDatePicker(authorizationDueDate);
			Utils.setDisplayFormatForDatePicker(authorizationDueDate);
			Utils.setDisabledDaysForDatePicker(birthDate);
			Utils.setDisplayFormatForDatePicker(birthDate);
			
			ChangeListener<Object> listener = (observable, oldValue, newValue) -> {
				if(Utils.allFieldsAreFilled(lastNameField, firstNameField, idCodeField,	idNumberField, personalIdentificationNumberField,
						titleField,	authorizationNumberField, birthCityField, homeAddressField, homeRegionField) &&
						Utils.allDatePickersAreFilled(birthDate, authorizationObtainigDate, authorizationDueDate)) {
					saveButton.setDisable(false);
				} else {
					saveButton.setDisable(true);
				}
			};
			
			lastNameField.textProperty().addListener(listener);
			firstNameField.textProperty().addListener(listener);
			idCodeField.textProperty().addListener(listener);
			idNumberField.textProperty().addListener(listener);
			personalIdentificationNumberField.textProperty().addListener(listener);
			titleField.textProperty().addListener(listener);
			authorizationNumberField.textProperty().addListener(listener);
			birthCityField.textProperty().addListener(listener);
			homeAddressField.textProperty().addListener(listener);
			homeRegionField.textProperty().addListener(listener);
			authorizationObtainigDate.valueProperty().addListener(listener);
			authorizationDueDate.valueProperty().addListener(listener);
			birthDate.valueProperty().addListener(listener);
			
			saveButton.setDisable(true);
		} catch (Exception e) {
			DBServices.saveErrorLogEntry(e);
		}
	}
	
	public void initializeIfUpdate() {
		try {
			if(update) {
				employeeTable.setItems(FXCollections.observableArrayList(DBServices.getEmployeesBetweenDateInterval(
						Constants.LOW_DATE,
						Constants.HIGH_DATE,
						(e1, e2) -> e1.getEmployee().getLastName().compareTo(e2.getEmployee().getLastName()))));
				employeeTable.setPlaceholder(new Label(Constants.TABLE_PLACEHOLDER_MESSAGE));
				firmNameColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getFirmName()));
				employeeNameColumn.setCellValueFactory(cellData -> new SimpleStringProperty(
						cellData.getValue().getEmployee().getLastName() + " " + cellData.getValue().getEmployee().getFirstName()));
				employeeTable.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
					if(newValue != null) {
						showEmployeeDetails(newValue.getEmployee());
					}
				});
			} else {
				splitPane.getItems().remove(leftSide);
				anchorPane.setPrefWidth(600);
				anchorPane.setPrefHeight(700);
				vBox.setPrefWidth(600);
				vBox.setPrefHeight(700);
			}
		} catch(Exception e) {
			DBServices.saveErrorLogEntry(e);
		}
	}
	
	public void setValidators(Stage stage) {
		Utils.setTextFieldValidator(authorizationNumberField, "[A-Za-z0-9]*", "[A-Za-z0-9]*", true, Constants.INFINITE, 
				"Numărul de autorizație este format din cifre și litere majuscule.", stage);
		Utils.setTextFieldValidator(firstNameField, "[A-Za-z ăâțșîÂÎĂȚȘ]*", "[A-Za-z ăâțșîÂÎĂȚȘ]*", false, Constants.INFINITE, 
				"Numele poate conține doar litere majuscule și minuscule.", stage);
		Utils.setTextFieldValidator(idCodeField, "[A-Za-z]*", "[A-Z]{2}", true, 2,
				"Seria de buletin poate conține doar două litere majuscule.", stage);
		Utils.setTextFieldValidator(idNumberField, "[0-9]*", "[0-9]{6}", false, 6,
				"Numărul de buletin poate conține doar 6 cifre.", stage);
		Utils.setTextFieldValidator(lastNameField, "[A-Za-z ăâțșîÂÎĂȚȘ]*", "[A-Za-z ăâțșîÂÎĂȚȘ]*", false, Constants.INFINITE, 
				"Numele poate conține doar litere majuscule și minuscule.", stage);
		Utils.setTextFieldValidator(personalIdentificationNumberField, "[0-9]*", "[0-9]{13}", false, 13,
				"Codul numeric personal este format din 13 cifre.", stage);
		Utils.setTextFieldValidator(titleField, "[A-Za-z ăâțșîÂÎĂȚȘ]*", "[A-Za-z ăâțșîÂÎĂȚȘ]*", false, Constants.INFINITE, 
				"Numele poate conține doar litere majuscule și minuscule.", stage);
		Utils.setTextFieldValidator(birthCityField, "[A-Za-z ăâțșîÂÎĂȚȘ]*", "[A-Za-z ăâțșîÂÎĂȚȘ]*", false, Constants.INFINITE, 
				"Localitatea poate conține doar litere majuscule și minuscule.", stage);
		Utils.setTextFieldValidator(homeRegionField, "[A-Za-z ăâțșîÂÎĂȚȘ]*", "[A-Za-z ăâțșîÂÎĂȚȘ]*", false, Constants.INFINITE, 
				"Județul poate conține doar litere majuscule și minuscule.", stage);
		Utils.setTextFieldValidator(homeAddressField, "[A-Za-z0-9 ăâțșîÂÎĂȚȘ,\\.-]*", "[A-Za-z0-9 ăâțșîÂÎĂȚȘ,\\.-]*", false, Constants.INFINITE,
				"Adresa poate conține litere majuscule si minuscule cifre si caracterele . , -", stage);
	}
	
	@FXML
	private void handleSave() {
		try {
			if(Utils.allFieldsAreValid(lastNameField, firstNameField, idCodeField,	idNumberField, personalIdentificationNumberField,
					titleField,	authorizationNumberField, birthCityField, homeAddressField, homeRegionField)) {
				Employee newEmployee = new Employee(firstNameField.getText(),
					 lastNameField.getText(),
					 idCodeField.getText(), 
					 idNumberField.getText(), 
					 personalIdentificationNumberField.getText(),
					 java.sql.Date.valueOf(birthDate.getValue()),
					 birthCityField.getText(),
					 homeAddressField.getText(),
					 homeRegionField.getText(),
					 new EmployeeAuthorization(authorizationNumberField.getText(), 
											   java.sql.Date.valueOf(authorizationObtainigDate.getValue()),
											   java.sql.Date.valueOf(authorizationDueDate.getValue())),
				    	titleField.getText(),
				    	rsvtiCheckbox.selectedProperty().get());
				if(isDueDateUpdate) {
					javaFxMain.getDueDateOverviewController().updateEmployeeTable(firmId, employeeToUpdate, newEmployee);
					javaFxMain.getAddEmployeesToFirmStage().close();
				} else {
					if(updateForFirm) {
						javaFxMain.getAddFirmController().updateEmployeeList(employeeToUpdate, true, newEmployee);
						javaFxMain.getAddEmployeesToFirmStage().close();
					} else if (update) {
						DBServices.updateEmployeeForFirm(employeeTable.getSelectionModel().getSelectedItem().getFirmId(), employeeToUpdate, newEmployee);
						employeeTable.setItems(FXCollections.observableArrayList(DBServices.getEmployeesBetweenDateInterval(
								Constants.LOW_DATE,
								Constants.HIGH_DATE,
								(e1, e2) -> e1.getEmployee().getLastName().compareTo(e2.getEmployee().getLastName()))));
						employeeTable.refresh();
					} else {
						javaFxMain.getAddFirmController().updateEmployeeList(newEmployee, false, null);
						javaFxMain.getAddEmployeesToFirmStage().close();
					}
				}
				isDueDateUpdate = false;
				updateForFirm = false;
			}
		} catch (Exception e) {
			DBServices.saveErrorLogEntry(e);
		}
	}
	
	public void showEmployeeDetails(Employee employee) {
		employeeToUpdate = employee;
		
		firstNameField.setText(employee.getFirstName());
		lastNameField.setText(employee.getLastName());
		idCodeField.setText(employee.getIdCode());
		idNumberField.setText(employee.getIdNumber());
		personalIdentificationNumberField.setText(employee.getPersonalIdentificationNumber());
		titleField.setText(employee.getTitle());
		authorizationNumberField.setText(employee.getAuthorization().getAuthorizationNumber());
		authorizationObtainigDate.setValue(new java.sql.Date(employee.getAuthorization().getObtainingDate().getTime()).toLocalDate());
		authorizationDueDate.setValue(new java.sql.Date(employee.getAuthorization().getDueDate().getTime()).toLocalDate());
		birthDate.setValue(new java.sql.Date(employee.getBirthDate().getTime()).toLocalDate());
		birthCityField.setText(employee.getBirthCity());
		homeRegionField.setText(employee.getHomeRegion());
		homeAddressField.setText(employee.getHomeAddress());
		rsvtiCheckbox.selectedProperty().set(employee.isRsvti());
	}
	
	public void setUpdateForFirm(boolean updateForFirm) {
		this.updateForFirm = updateForFirm;
	}
	
	public void setUpdate(boolean update) {
		this.update = update;
	}
	
	public void setIsDueDateUpdate(boolean isDueDateUpdate) {
		this.isDueDateUpdate = isDueDateUpdate;
	}
	
	public void setFirmId(String firmId) {
		this.firmId = firmId;
	}
	
	public void setJavaFxMain(JavaFxMain javaFxMain) {
		this.javaFxMain = javaFxMain;
	}
}
