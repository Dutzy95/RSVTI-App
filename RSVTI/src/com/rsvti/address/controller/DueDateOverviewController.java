package com.rsvti.address.controller;

import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.List;

import com.rsvti.address.JavaFxMain;
import com.rsvti.common.Constants;
import com.rsvti.common.Utils;
import com.rsvti.database.entities.Employee;
import com.rsvti.database.entities.EmployeeWithDetails;
import com.rsvti.database.entities.Rig;
import com.rsvti.database.entities.RigWithDetails;
import com.rsvti.database.services.DBServices;

import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.CheckBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableRow;
import javafx.scene.control.TableView;

public class DueDateOverviewController {

	@FXML
	private DatePicker dateFrom;
	@FXML
	private DatePicker dateTo;
	@FXML
	private CheckBox allCheckbox;
	
	@FXML
	private TableView<RigWithDetails> rigTable;
	@FXML
	private TableColumn<RigWithDetails,String> rigColumn;
	@FXML
	private TableColumn<RigWithDetails,String> firmNameColumn;
	@FXML
	private TableColumn<RigWithDetails,String> rigDueDateColumn;
	
	private SimpleDateFormat simpleDateFormat = new SimpleDateFormat(DBServices.getDatePattern());
	
	@FXML
	private TableView<EmployeeWithDetails> employeeTable;
	@FXML
	private TableColumn<EmployeeWithDetails,String> employeeFirstNameColumn;
	@FXML
	private TableColumn<EmployeeWithDetails,String> employeeLastNameColumn;
	@FXML
	private TableColumn<EmployeeWithDetails,String> employeeFirmNameColumn;
	@FXML
	private TableColumn<EmployeeWithDetails,String> employeeDueDateColumn;
	
	@FXML
	private JavaFxMain javaFxMain;
	
	@FXML
	private void initialize() {
		try {
			dateFrom.setValue(LocalDate.now());
			dateTo.setValue(LocalDate.now());
			Utils.setDisabledDaysForDatePicker(dateFrom);
			Utils.setDisplayFormatForDatePicker(dateFrom);
			Utils.setDisabledDaysForDatePicker(dateTo);
			Utils.setDisplayFormatForDatePicker(dateTo);
			
			rigTable.setItems(FXCollections.observableArrayList(DBServices.getRigsBetweenDateInterval(
					java.sql.Date.valueOf(dateFrom.getValue()),
					java.sql.Date.valueOf(dateTo.getValue()),
					(r1, r2) -> r1.getDueDate().compareTo(r2.getDueDate()))));
			rigTable.setPlaceholder(new Label(Constants.TABLE_PLACEHOLDER_MESSAGE));
			rigColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getRig().getRigName()));
			firmNameColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getFirmName()));
			rigDueDateColumn.setCellValueFactory(cellData -> new SimpleStringProperty(simpleDateFormat.format(cellData.getValue().getDueDate())));
			rigTable.setRowFactory( tv -> {
			    TableRow<RigWithDetails> row = new TableRow<>();
			    row.setOnMouseClicked(event -> {
			        if (event.getClickCount() == 2 && (! row.isEmpty()) ) {
			        	RigWithDetails rowData = row.getItem();
			            javaFxMain.showAddUpdateRigsToFirm(rowData.getRig(), false, true, rowData.getFirmName(), rowData.getFirmId(), false);
			        }
			    });
			    return row ;
			});
			
			employeeTable.setItems(FXCollections.observableArrayList(DBServices.getEmployeesBetweenDateInterval(java.sql.Date.valueOf(dateFrom.getValue()), java.sql.Date.valueOf(dateTo.getValue()))));
			employeeTable.setPlaceholder(new Label(Constants.TABLE_PLACEHOLDER_MESSAGE));
			employeeFirstNameColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getEmployee().getFirstName()));
			employeeLastNameColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getEmployee().getLastName()));
			employeeFirmNameColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getFirmName()));
			employeeDueDateColumn.setCellValueFactory(cellData -> new SimpleStringProperty(simpleDateFormat.format(cellData.getValue().getDueDate())));
			employeeTable.setRowFactory( tv -> {
			    TableRow<EmployeeWithDetails> row = new TableRow<>();
			    row.setOnMouseClicked(event -> {
			        if (event.getClickCount() == 2 && (! row.isEmpty()) ) {
			        	EmployeeWithDetails rowData = row.getItem();
			            javaFxMain.showAddUpdateEmployeesToFirm(rowData.getEmployee(), false, true, rowData.getFirmName(), rowData.getFirmId());
			        }
			    });
			    return row ;
			});
			
			dateFrom.setOnAction(event -> {
				if(dateTo.getValue().isAfter(dateFrom.getValue()) || dateTo.getValue().equals(dateFrom.getValue())) {
					rigTable.setItems(FXCollections.observableArrayList(DBServices.getRigsBetweenDateInterval(
							java.sql.Date.valueOf(dateFrom.getValue()),
							java.sql.Date.valueOf(dateTo.getValue()),
							(r1, r2) -> r1.getDueDate().compareTo(r2.getDueDate()))));
					employeeTable.setItems(FXCollections.observableArrayList(DBServices.getEmployeesBetweenDateInterval(java.sql.Date.valueOf(dateFrom.getValue()), java.sql.Date.valueOf(dateTo.getValue()))));
				} else {
					dateFrom.setValue(LocalDate.now());
					Utils.alert(AlertType.ERROR, "Eroare data", "", "\"De la data\" trebuie sa fie inainte de \"Până la data\"", false);
				}
			});
			dateTo.setOnAction(event -> {
				if(dateTo.getValue().isAfter(dateFrom.getValue()) || dateTo.getValue().equals(dateFrom.getValue())) {
					rigTable.setItems(FXCollections.observableArrayList(DBServices.getRigsBetweenDateInterval(
							java.sql.Date.valueOf(dateFrom.getValue()),
							java.sql.Date.valueOf(dateTo.getValue()),
							(r1, r2) -> r1.getDueDate().compareTo(r2.getDueDate()))));
					employeeTable.setItems(FXCollections.observableArrayList(DBServices.getEmployeesBetweenDateInterval(java.sql.Date.valueOf(dateFrom.getValue()), java.sql.Date.valueOf(dateTo.getValue()))));
				} else {
					dateTo.setValue(LocalDate.now());
					Utils.alert(AlertType.ERROR, "Eroare data", "", "\"De la data\" trebuie sa fie inainte de \"Până la data\"", false);
				}
			});
			allCheckbox.selectedProperty().addListener((observable, oldValue, newValue) -> {
				if(newValue) {
					dateFrom.setDisable(true);
					dateTo.setDisable(true);
					rigTable.setItems(FXCollections.observableArrayList(DBServices.getRigsBetweenDateInterval(
							Constants.LOW_DATE,
							Constants.HIGH_DATE,
							(r1, r2) -> r1.getDueDate().compareTo(r2.getDueDate()))));
					employeeTable.setItems(FXCollections.observableArrayList(DBServices.getEmployeesBetweenDateInterval(Constants.LOW_DATE, Constants.HIGH_DATE)));
				} else {
					dateFrom.setDisable(false);
					dateTo.setDisable(false);
					rigTable.setItems(FXCollections.observableArrayList(DBServices.getRigsBetweenDateInterval(
							java.sql.Date.valueOf(dateFrom.getValue()),
							java.sql.Date.valueOf(dateTo.getValue()),
							(r1, r2) -> r1.getDueDate().compareTo(r2.getDueDate()))));
					employeeTable.setItems(FXCollections.observableArrayList(DBServices.getEmployeesBetweenDateInterval(java.sql.Date.valueOf(dateFrom.getValue()), java.sql.Date.valueOf(dateTo.getValue()))));
				}
			});
		} catch (Exception e) {
			DBServices.saveErrorLogEntry(e);
		}
	}
	
	public void updateRigTable(String firmId, Rig rigToUpdate, Rig newRig) {
		List<RigWithDetails> beforeUpdate = rigTable.getItems();
		for(RigWithDetails index : beforeUpdate) {
			if(index.getRig().equals(rigToUpdate)) {
				index.setRig(newRig);
				index.setDueDate(newRig.getDueDate());
			}
		}
		rigTable.refresh();
		DBServices.updateRigForFirm(firmId, rigToUpdate, newRig);
	}
	
	public void updateEmployeeTable(String firmId, Employee employeeToUpdate, Employee newEmployee) {
		List<EmployeeWithDetails> beforeUpdate = employeeTable.getItems();
		for(EmployeeWithDetails index : beforeUpdate) {
			if(index.getEmployee().equals(employeeToUpdate)) {
				index.setEmployee(newEmployee);
				index.setDueDate(newEmployee.getAuthorization().getDueDate());
			}
		}
		employeeTable.refresh();
		DBServices.updateEmployeeForFirm(firmId, employeeToUpdate, newEmployee);
	}
	
	public void setJavaFxMain(JavaFxMain javaFxMain) {
		this.javaFxMain = javaFxMain;
	}
	
}
