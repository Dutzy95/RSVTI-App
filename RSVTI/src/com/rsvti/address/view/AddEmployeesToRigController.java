package com.rsvti.address.view;

import java.time.LocalDateTime;
import java.util.Date;

import com.rsvti.address.JavaFxMain;
import com.rsvti.database.entities.Employee;
import com.rsvti.database.entities.EmployeeAuthorization;

import javafx.fxml.FXML;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;

public class AddEmployeesToRigController {

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
	private void handleSave() {
		javaFxMain.getAddRigsToFirmController().updateEmployeeList(
				new Employee(firstNameField.getText(),
							 lastNameField.getText(),
							 idCodeField.getText(), 
							 idNumberField.getText(), 
							 personalIdentificationNumberField.getText(), 
							 new EmployeeAuthorization(authorizationNumberField.getText(), 
													   java.sql.Date.valueOf(authorizationObtainigDate.getValue()),
													   java.sql.Date.valueOf(authorizationDueDate.getValue())),
							 titleField.getText()));
		javaFxMain.getAddEmployeesToRigStage().close();
	}
	
	public void showEmployeeDetails(Employee employee) {
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
	
	public void setJavaFxMain(JavaFxMain javaFxMain) {
		this.javaFxMain = javaFxMain;
	}
}
