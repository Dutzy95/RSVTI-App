package com.rsvti.address.view;

import java.text.SimpleDateFormat;
import java.util.List;

import com.rsvti.common.Constants;
import com.rsvti.database.entities.Employee;
import com.rsvti.database.services.DBServices;

import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;

public class EmployeeOverviewController {

	@FXML
	private TableView<Employee> employeeTable;
	@FXML
	private TableColumn<Employee,String> firstNameColumn;
	@FXML
	private TableColumn<Employee,String> lastNameColumn;
	@FXML
	private TableColumn<Employee,String> idCodeColumn;
	@FXML
	private TableColumn<Employee,String> idNumberColumn;
	@FXML
	private TableColumn<Employee,String> personalIdentificationNumberColumn;
	@FXML
	private TableColumn<Employee,String> titleColumn;
	@FXML
	private TableColumn<Employee,String> authorizationColumn;
	@FXML
	private TableColumn<Employee,String> authorizationNumberColumn;
	@FXML
	private TableColumn<Employee,String> authorizationObtainingDateColumn;
	@FXML
	private TableColumn<Employee,String> authorizationDueDateColumn;
	
	@FXML
	private List<Employee> employeeList;
	
	public void setEmployeeList(List<Employee> employeeList) {
		this.employeeList = employeeList;
		employeeTable.setItems(FXCollections.observableArrayList(employeeList));
		employeeTable.setPlaceholder(new Label(Constants.TABLE_PLACEHOLDER_MESSAGE));
		
		firstNameColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getFirstName()));
		lastNameColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getLastName()));
		idCodeColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getIdCode()));
		idNumberColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getIdNumber()));
		personalIdentificationNumberColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getPersonalIdentificationNumber()));
		titleColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getTitle()));
		authorizationNumberColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getAuthorization().getAuthorizationNumber()));
		SimpleDateFormat format = new SimpleDateFormat(DBServices.getDatePattern());
		authorizationObtainingDateColumn.setCellValueFactory(cellData -> new SimpleStringProperty(format.format(cellData.getValue().getAuthorization().getObtainingDate())));
		authorizationDueDateColumn.setCellValueFactory(cellData -> new SimpleStringProperty(format.format(cellData.getValue().getAuthorization().getDueDate())));
	}
}
