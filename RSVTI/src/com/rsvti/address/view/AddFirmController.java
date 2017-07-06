package com.rsvti.address.view;

import java.util.ArrayList;
import java.util.List;

import com.rsvti.address.JavaFxMain;
import com.rsvti.common.Constants;
import com.rsvti.database.entities.Administrator;
import com.rsvti.database.entities.Employee;
import com.rsvti.database.entities.Firm;
import com.rsvti.database.entities.Rig;
import com.rsvti.database.services.DBServices;

import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableRow;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;

public class AddFirmController {
	
	@FXML
	private TextField firmNameField;
	@FXML
	private TextField registrationNumberField;
	@FXML
	private TextField fiscalCodeField;
	@FXML
	private TextField addressField;
	@FXML
	private TextField phoneNumberField;
	@FXML
	private TextField faxNumberField;
	@FXML
	private TextField emailField;
	@FXML
	private TextField bankNameField;
	@FXML
	private TextField ibanCodeField;
	@FXML
	private TextField adminFirstNameField;
	@FXML
	private TextField adminLastNameField;
	@FXML
	private TextField adminIdCodeField;
	@FXML
	private TextField adminIdNumberField;
	@FXML
	private TextField adminPhoneNumberField;
	
	@FXML
	private TableView<Rig> rigTable;
	@FXML
	private TableColumn<Rig,String> rigColumn;
	
	@FXML
	private TableView<Employee> employeeTable;
	@FXML
	private TableColumn<Employee,String> employeeLastNameColumn;
	@FXML
	private TableColumn<Employee,String> employeeFirstNameColumn;
	
	private List<Employee> employeeList = new ArrayList<Employee>();
	
	private List<Rig> rigList = new ArrayList<Rig>();
	
	@FXML
	private JavaFxMain javaFxMain;
	
	@FXML
	private void initialize() {
		rigTable.setItems(FXCollections.observableArrayList(rigList));
		rigColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getRigName()));
		rigTable.setRowFactory( tv -> {
		    TableRow<Rig> row = new TableRow<>();
		    row.setOnMouseClicked(event -> {
		        if (event.getClickCount() == 2 && (! row.isEmpty()) ) {
		            Rig rowData = row.getItem();
		            javaFxMain.showAddUpdateRigsToFirm(rowData, true, false, "Adaugă utilaj");
		        }
		    });
		    return row ;
		});
		rigTable.setPlaceholder(new Label(Constants.TABLE_PLACEHOLDER_MESSAGE));
		rigColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getRigName()));
		
		employeeTable.setItems(FXCollections.observableArrayList(employeeList));
		employeeTable.setPlaceholder(new Label(Constants.TABLE_PLACEHOLDER_MESSAGE));
		employeeLastNameColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getLastName()));
		employeeFirstNameColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getFirstName()));
		employeeTable.setRowFactory( tv -> {
		    TableRow<Employee> row = new TableRow<>();
		    row.setOnMouseClicked(event -> {
		        if (event.getClickCount() == 2 && (! row.isEmpty()) ) {
		            Employee rowData = row.getItem();
		            javaFxMain.showAddUpdateEmployeesToFirm(rowData, true, false, "Editează personal");
		        }
		    });
		    return row ;
		});
		
		addressField.setAlignment(Pos.CENTER);
		adminFirstNameField.setAlignment(Pos.CENTER);
		adminIdCodeField.setAlignment(Pos.CENTER);
		adminIdNumberField.setAlignment(Pos.CENTER);
		adminLastNameField.setAlignment(Pos.CENTER);
		adminPhoneNumberField.setAlignment(Pos.CENTER);
		bankNameField.setAlignment(Pos.CENTER);
		emailField.setAlignment(Pos.CENTER);
		faxNumberField.setAlignment(Pos.CENTER);
		firmNameField.setAlignment(Pos.CENTER);
		fiscalCodeField.setAlignment(Pos.CENTER);
		ibanCodeField.setAlignment(Pos.CENTER);
		phoneNumberField.setAlignment(Pos.CENTER);
		registrationNumberField.setAlignment(Pos.CENTER);
		
	}
	
	@FXML
	private void handleSave() {
		DBServices.saveEntry(new Firm(
				firmNameField.getText(),
				registrationNumberField.getText(),
				fiscalCodeField.getText(),
				addressField.getText(),
				phoneNumberField.getText(),
				faxNumberField.getText(),
				emailField.getText(),
				bankNameField.getText(),
				ibanCodeField.getText(),
				new Administrator(
						adminFirstNameField.getText(),
						adminLastNameField.getText(),
						adminIdCodeField.getText(),
						adminIdNumberField.getText(),
						adminPhoneNumberField.getText()),
				rigTable.getItems(), employeeTable.getItems()), false);
		javaFxMain.getTabPane().getTabs().remove(javaFxMain.getAddFirmTab());
	}
	
	@FXML
	private void handleAddRig() {
		javaFxMain.showAddUpdateRigsToFirm(null, false, false, "Editează utilaj");
	}
	
	@FXML
	private void handleAddEmployee() {
		javaFxMain.showAddUpdateEmployeesToFirm(null, false, false,"Adaugă personal");
	}
	
	public void updateRigTable(Rig rig, boolean isUpdate, Rig updatedRig) {
		if(isUpdate) {
			for(int i = 0; i < rigList.size(); i++) {
				if(rigList.get(i).equals(rig)) {
					rigList.set(i, updatedRig);
				}
			}
		} else {
			rigList.add(rig);
		}
		rigTable.setItems(FXCollections.observableArrayList(rigList));
	}
	
	public void updateEmployeeList(Employee employee, boolean isUpdate, Employee updatedEmployee) {
		if(isUpdate) {
			for(int i = 0; i < employeeList.size(); i++) {
				if(employeeList.get(i).equals(employee)) {
					employeeList.set(i, updatedEmployee);
				}
			}
		} else {
			employeeList.add(employee);
		}
		employeeTable.setItems(FXCollections.observableArrayList(employeeList));
	}
	
	public void setJavaFxMain(JavaFxMain javaFxMain) {
		this.javaFxMain = javaFxMain;
	}
}
