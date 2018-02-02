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
import com.rsvti.database.entities.RigDueDateDetails;
import com.rsvti.database.services.DBServices;

import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.TableView.ResizeFeatures;
import javafx.util.Callback;
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
	private TableView<RigDueDateDetails> rigTable;
	@FXML
	private TableColumn<RigDueDateDetails,String> rigColumn;
	@FXML
	private TableColumn<RigDueDateDetails,String> firmNameColumn;
	@FXML
	private TableColumn<RigDueDateDetails,String> rigDueDateColumn;
	
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
			
			rigTable.setItems(FXCollections.observableArrayList(DBServices.getRigsBetweenDateInterval(java.sql.Date.valueOf(dateFrom.getValue()), java.sql.Date.valueOf(dateTo.getValue()))));
			rigTable.setPlaceholder(new Label(Constants.TABLE_PLACEHOLDER_MESSAGE));
			rigColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getRig().getRigName()));
			firmNameColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getFirmName()));
			rigDueDateColumn.setCellValueFactory(cellData -> new SimpleStringProperty(simpleDateFormat.format(cellData.getValue().getDueDate())));
			rigTable.setRowFactory( tv -> {
			    TableRow<RigDueDateDetails> row = new TableRow<>();
			    row.setOnMouseClicked(event -> {
			        if (event.getClickCount() == 2 && (! row.isEmpty()) ) {
			        	RigDueDateDetails rowData = row.getItem();
			            javaFxMain.showAddUpdateRigsToFirm(rowData.getRig(), false, true, rowData.getFirmName());
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
			            javaFxMain.showAddUpdateEmployeesToFirm(rowData.getEmployee(), false, true, rowData.getFirmName());
			        }
			    });
			    return row ;
			});
			
			dateFrom.setOnAction(event -> {
				if(dateTo.getValue().isAfter(dateFrom.getValue()) || dateTo.getValue().equals(dateFrom.getValue())) {
					rigTable.setItems(FXCollections.observableArrayList(DBServices.getRigsBetweenDateInterval(java.sql.Date.valueOf(dateFrom.getValue()), java.sql.Date.valueOf(dateTo.getValue()))));
					employeeTable.setItems(FXCollections.observableArrayList(DBServices.getEmployeesBetweenDateInterval(java.sql.Date.valueOf(dateFrom.getValue()), java.sql.Date.valueOf(dateTo.getValue()))));
				} else {
					dateFrom.setValue(LocalDate.now());
					Utils.alert(AlertType.ERROR, "Eroare data", "", "\"De la data\" trebuie sa fie inainte de \"Până la data\"");
				}
			});
			dateTo.setOnAction(event -> {
				if(dateTo.getValue().isAfter(dateFrom.getValue()) || dateTo.getValue().equals(dateFrom.getValue())) {
					rigTable.setItems(FXCollections.observableArrayList(DBServices.getRigsBetweenDateInterval(java.sql.Date.valueOf(dateFrom.getValue()), java.sql.Date.valueOf(dateTo.getValue()))));
					employeeTable.setItems(FXCollections.observableArrayList(DBServices.getEmployeesBetweenDateInterval(java.sql.Date.valueOf(dateFrom.getValue()), java.sql.Date.valueOf(dateTo.getValue()))));
				} else {
					dateTo.setValue(LocalDate.now());
					Utils.alert(AlertType.ERROR, "Eroare data", "", "\"De la data\" trebuie sa fie inainte de \"Până la data\"");
				}
			});
		} catch (Exception e) {
			DBServices.saveErrorLogEntry(e);
		}
	}
	
	public void updateRigTable(String firmName, Rig rigToUpdate, Rig newRig) {
		List<RigDueDateDetails> beforeUpdate = rigTable.getItems();
		for(RigDueDateDetails index : beforeUpdate) {
			if(index.getRig().equals(rigToUpdate)) {
				index.setRig(newRig);
				index.setDueDate(newRig.getDueDate());
			}
		}
		rigTable.refresh();
		DBServices.updateRigForFirm(firmName, rigToUpdate, newRig);
	}
	
	public void updateEmployeeTable(String firmName, Employee employeeToUpdate, Employee newEmployee) {
		List<EmployeeWithDetails> beforeUpdate = employeeTable.getItems();
		for(EmployeeWithDetails index : beforeUpdate) {
			if(index.getEmployee().equals(employeeToUpdate)) {
				index.setEmployee(newEmployee);
				index.setDueDate(newEmployee.getAuthorization().getDueDate());
			}
		}
		employeeTable.refresh();
		DBServices.updateEmployeeForFirm(firmName, employeeToUpdate, newEmployee);
	}
	
	public void setJavaFxMain(JavaFxMain javaFxMain) {
		this.javaFxMain = javaFxMain;
	}
	
}
