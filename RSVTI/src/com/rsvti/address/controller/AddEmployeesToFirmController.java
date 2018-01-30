package com.rsvti.address.controller;

import java.util.Arrays;
import java.util.List;

import com.rsvti.address.JavaFxMain;
import com.rsvti.common.Constants;
import com.rsvti.common.Utils;
import com.rsvti.database.entities.Employee;
import com.rsvti.database.entities.EmployeeAuthorization;
import com.rsvti.database.services.DBServices;

import javafx.fxml.FXML;
import javafx.geometry.Pos;
import javafx.scene.control.CheckBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class AddEmployeesToFirmController {

	@FXML
	private JavaFxMain javaFxMain;
	
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
	private TextField homeAddressField;
	@FXML
	private TextField homeRegionField;
	@FXML
	private CheckBox rsvtiCheckbox;
	
	private Employee employeeToUpdate;
	private boolean isUpdate = false;
	private boolean isDueDateUpdate = false;
	private String firmName;
	
	@FXML
	private void initialize() {
		try {
			Utils.setDisabledDaysForDatePicker(authorizationObtainigDate);
			Utils.setDisplayFormatForDatePicker(authorizationObtainigDate);
			Utils.setDisabledDaysForDatePicker(authorizationDueDate);
			Utils.setDisplayFormatForDatePicker(authorizationDueDate);
			Utils.setDisabledDaysForDatePicker(birthDate);
			Utils.setDisplayFormatForDatePicker(birthDate);
			authorizationNumberField.setAlignment(Pos.CENTER);
			firstNameField.setAlignment(Pos.CENTER);
			idCodeField.setAlignment(Pos.CENTER);
			idNumberField.setAlignment(Pos.CENTER);
			lastNameField.setAlignment(Pos.CENTER);
			personalIdentificationNumberField.setAlignment(Pos.CENTER);
			titleField.setAlignment(Pos.CENTER);
			birthCityField.setAlignment(Pos.CENTER);
			homeRegionField.setAlignment(Pos.CENTER);
			homeAddressField.setAlignment(Pos.CENTER);
		} catch (Exception e) {
			DBServices.saveErrorLogEntry(e);
		}
	}
	
	public void setValidators(Stage stage) {
		//TODO validation of authorisationNumber
		Utils.setTextFieldValidator(authorizationNumberField, "", "", true, Constants.INFINITE, "tooltip text", stage);
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
		Utils.setTextFieldValidator(homeAddressField, "[A-Za-z ăâțșîÂÎĂȚȘ,\\.-]*", "[A-Za-z ăâțșîÂÎĂȚȘ,\\.-]*", false, Constants.INFINITE,
				"Adresa poate conține litere majuscule si minuscule cifre si caracterele . , -", JavaFxMain.primaryStage);
	}
	
	private boolean allFieldsAreCorrect() {
		List<TextField> fields = Arrays.asList(authorizationNumberField, firstNameField, idCodeField, idNumberField, lastNameField, 
				personalIdentificationNumberField, titleField, birthCityField, homeRegionField, homeAddressField);
		
		for(TextField index : fields) {
			if(index.getBorder() != null) {
				return false;
			}
		}
		return true;
	}
	
	@FXML
	private void handleSave() {
		try {
			if(allFieldsAreCorrect()) {
				if(isDueDateUpdate) {
					javaFxMain.getDueDateOverviewController().updateEmployeeTable(firmName, employeeToUpdate, 
																	new Employee(firstNameField.getText(),
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
																			    	rsvtiCheckbox.selectedProperty().get()));
					
				} else {
					if(isUpdate) {
						javaFxMain.getAddFirmController().updateEmployeeList(
								employeeToUpdate, true, new Employee(firstNameField.getText(),
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
																    	rsvtiCheckbox.selectedProperty().get()));
					} else {
						try {
						javaFxMain.getAddFirmController().updateEmployeeList(
								new Employee(firstNameField.getText(),
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
											 rsvtiCheckbox.selectedProperty().get()),
								false,
								null);
						} catch(Exception e) {
							e.printStackTrace();
						}
					}
				}
				javaFxMain.getAddEmployeesToFirmStage().close();
				isDueDateUpdate = false;
				isUpdate = false;
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
	}
	
	public void setIsUpdate(boolean isUpdate) {
		this.isUpdate = isUpdate;
	}
	
	public void setIsDueDateUpdate(boolean isDueDateUpdate) {
		this.isDueDateUpdate = isDueDateUpdate;
	}
	
	public void setFirmName(String firmName) {
		this.firmName = firmName;
	}
	
	public void setJavaFxMain(JavaFxMain javaFxMain) {
		this.javaFxMain = javaFxMain;
	}
}
