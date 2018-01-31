package com.rsvti.address.controller;

import java.text.SimpleDateFormat;
import java.util.List;

import com.rsvti.common.Constants;
import com.rsvti.database.entities.Employee;
import com.rsvti.database.services.DBServices;

import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.Control;
import javafx.scene.control.Label;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TableView.ResizeFeatures;
import javafx.scene.text.Text;
import javafx.util.Callback;

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
	private TableColumn<Employee, String> birthDateColumn;
	@FXML
	private TableColumn<Employee, String> birthCityColumn;
	@FXML
	private TableColumn<Employee, String> homeAddressColumn;
	@FXML
	private TableColumn<Employee, String> homeRegionColumn;
	
	@FXML
	private List<Employee> employeeList;
	
	public void setEmployeeList(List<Employee> employeeList) {
		try {
			this.employeeList = employeeList;
			employeeTable.setItems(FXCollections.observableArrayList(employeeList));
			employeeTable.setPlaceholder(new Label(Constants.TABLE_PLACEHOLDER_MESSAGE));
		
			SimpleDateFormat format = new SimpleDateFormat(DBServices.getDatePattern());
			firstNameColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getFirstName()));
			lastNameColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getLastName()));
			idCodeColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getIdCode()));
			idNumberColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getIdNumber()));
			personalIdentificationNumberColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getPersonalIdentificationNumber()));
			birthDateColumn.setCellValueFactory(cellData -> new SimpleStringProperty(format.format(cellData.getValue().getBirthDate())));
			birthCityColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getBirthCity()));
			homeAddressColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getHomeAddress()));
			homeAddressColumn.setCellFactory(new Callback<TableColumn<Employee, String>, TableCell<Employee, String>>() {
		        @Override
		        public TableCell<Employee, String> call(TableColumn<Employee, String> param) {
		            TableCell<Employee, String> cell = new TableCell<>();
		            Text text = new Text();
		            cell.setGraphic(text);
		            cell.setPrefHeight(Control.USE_COMPUTED_SIZE);
		            text.wrappingWidthProperty().bind(cell.widthProperty());
		            text.textProperty().bind(cell.itemProperty());
		            return cell ;
		        }

		    });
			homeRegionColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getHomeRegion()));
			titleColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getTitle()));
			authorizationNumberColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getAuthorization().getAuthorizationNumber()));
			authorizationObtainingDateColumn.setCellValueFactory(cellData -> new SimpleStringProperty(format.format(cellData.getValue().getAuthorization().getObtainingDate())));
			authorizationDueDateColumn.setCellValueFactory(cellData -> new SimpleStringProperty(format.format(cellData.getValue().getAuthorization().getDueDate())));
		} catch (Exception e) {
			DBServices.saveErrorLogEntry(e);
		}
	}
}
