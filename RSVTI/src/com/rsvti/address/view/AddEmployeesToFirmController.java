package com.rsvti.address.view;

import com.rsvti.address.JavaFxMain;
import com.rsvti.common.Utils;
import com.rsvti.database.entities.Employee;
import com.rsvti.database.entities.EmployeeAuthorization;

import javafx.fxml.FXML;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;

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
	
	private Employee employeeToUpdate;
	private boolean isUpdate = false;
	private boolean isDueDateUpdate = false;
	private String firmName;
	
	@FXML
	private void initialize() {
		Utils.setDisabledDaysForDatePicker(authorizationObtainigDate);
		Utils.setDisplayFormatForDatePicker(authorizationObtainigDate);
		Utils.setDisabledDaysForDatePicker(authorizationDueDate);
		Utils.setDisplayFormatForDatePicker(authorizationDueDate);
	}
	
	@FXML
	private void handleSave() {
		if(isDueDateUpdate) {
			javaFxMain.getDueDateOverviewController().updateEmployeeTable(firmName, employeeToUpdate, 
															new Employee(firstNameField.getText(),
																		 lastNameField.getText(),
																		 idCodeField.getText(), 
																		 idNumberField.getText(), 
																		 personalIdentificationNumberField.getText(), 
																		 new EmployeeAuthorization(authorizationNumberField.getText(), 
																								   java.sql.Date.valueOf(authorizationObtainigDate.getValue()),
																								   java.sql.Date.valueOf(authorizationDueDate.getValue())),
																	    	titleField.getText()));
			
		} else {
			if(isUpdate) {
				javaFxMain.getAddFirmController().updateEmployeeList(
						employeeToUpdate, true, new Employee(firstNameField.getText(),
															 lastNameField.getText(),
															 idCodeField.getText(), 
															 idNumberField.getText(), 
															 personalIdentificationNumberField.getText(), 
															 new EmployeeAuthorization(authorizationNumberField.getText(), 
																					   java.sql.Date.valueOf(authorizationObtainigDate.getValue()),
																					   java.sql.Date.valueOf(authorizationDueDate.getValue())),
														    	titleField.getText()));
			} else {
				try {
				javaFxMain.getAddFirmController().updateEmployeeList(
						new Employee(firstNameField.getText(),
									 lastNameField.getText(),
									 idCodeField.getText(), 
									 idNumberField.getText(), 
									 personalIdentificationNumberField.getText(), 
									 new EmployeeAuthorization(authorizationNumberField.getText(), 
															   java.sql.Date.valueOf(authorizationObtainigDate.getValue()),
															   java.sql.Date.valueOf(authorizationDueDate.getValue())),
									 titleField.getText()),
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
