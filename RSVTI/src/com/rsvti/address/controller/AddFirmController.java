package com.rsvti.address.controller;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;

import com.rsvti.address.JavaFxMain;
import com.rsvti.common.Constants;
import com.rsvti.common.Utils;
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
		try {
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
			Utils.setTextFieldValidator(addressField, "[A-Za-z ăâțșîÂÎĂȚȘ,\\.-]*", "[A-Za-z ăâțșîÂÎĂȚȘ,\\.-]*", false, Constants.INFINITE,
					"Adresa poate conține litere majuscule si minuscule cifre si caracterele . , -");
			adminFirstNameField.setAlignment(Pos.CENTER);
			Utils.setTextFieldValidator(adminFirstNameField, "[A-Za-z ăâțșîÂÎĂȚȘ]*", "[A-Za-z ăâțșîÂÎĂȚȘ]*", false, Constants.INFINITE,
					"Prenumele administratorului poate conține doar litere majuscule și minuscule.");
			adminIdCodeField.setAlignment(Pos.CENTER);
			Utils.setTextFieldValidator(adminIdCodeField, "[A-Za-z]*", "[A-Z]{2}", true, 2,
					"Seria de buletin a administratorului poate conține doar două litere majuscule.");
			adminIdNumberField.setAlignment(Pos.CENTER);
			Utils.setTextFieldValidator(adminIdNumberField, "[0-9]*", "[0-9]{6}", false, 6,
					"Numărul de buletin al administratorului poate conține doar 6 cifre.");
			adminLastNameField.setAlignment(Pos.CENTER);
			Utils.setTextFieldValidator(adminLastNameField, "[A-Za-z ăâțșîÂÎĂȚȘ]*", "[A-Za-z ăâțșîÂÎĂȚȘ]*", false, Constants.INFINITE,
					"Numele administratorului poate conține doar litere majuscule și minuscule.");
			adminPhoneNumberField.setAlignment(Pos.CENTER);
			Utils.setTextFieldValidator(adminPhoneNumberField, "[+0-9]*", "\\+?([0-9]{11}|[0-9]{10})", false, 12, 
					"Numărul de telefon al administratotorului poate conține doar 10 cifre sau + urmat de 11 cifre.");
			bankNameField.setAlignment(Pos.CENTER);
			Utils.setTextFieldValidator(bankNameField, "[A-Za-z ăâțșîÂÎĂȚȘ]*", "[A-Za-z ăâțșîÂÎĂȚȘ]*", false, Constants.INFINITE,
					"Numele băncii poate conține doar litere majuscule sau minuscule.");
			emailField.setAlignment(Pos.CENTER);
			Utils.setTextFieldValidator(emailField, "[A-Za-z0-9_%+-@]*", "[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,6}", false, Constants.INFINITE,
					"Emailul poate contine doar litere majuscule sau minuscule, cifre si caracterele _ % + - .\nExemplu: ion_ionescu.01@exemplu.com");
			faxNumberField.setAlignment(Pos.CENTER);
			Utils.setTextFieldValidator(faxNumberField, "[+0-9]*", "\\+?([0-9]{11}|[0-9]{10})", false, 12,
					"Numărul de fax poate conține doar 10 cifre sau + urmat de 11 cifre.");
			firmNameField.setAlignment(Pos.CENTER);
			Utils.setTextFieldValidator(firmNameField, "[A-Za-z ăâțșîÂÎĂȚȘ]*", "[A-Za-z ăâțșîÂÎĂȚȘ]*", false, Constants.INFINITE, 
					"Numele firmei poate conține doar litere majuscule și minuscule.");
			fiscalCodeField.setAlignment(Pos.CENTER);
			Utils.setTextFieldValidator(fiscalCodeField, "[ROro0-9]*", "RO[0-9]{6,9}[0-9]", true, 11,
					"Codul fiscal poate conține RO urmat de minim 7 și maxim 10 cifre.");
			ibanCodeField.setAlignment(Pos.CENTER);
			Utils.setTextFieldValidator(ibanCodeField, "[A-Za-z0-9 ]*", "RO[0-9][0-9] ?[A-Z]{4}( ?[A-Z0-9]{4}){4}", true, 29,
					"Codul IBAN este format din RO urmat de doua cifre, indicativul bancii format din patru litere, urmat de 16 cifre sau litere.");
			phoneNumberField.setAlignment(Pos.CENTER);
			Utils.setTextFieldValidator(phoneNumberField, "[+0-9]*", "\\+?([0-9]{11}|[0-9]{10})", false, 12,
					"Numărul de telefon poate conține doar 10 cifre sau + urmat de 11 cifre.");
			registrationNumberField.setAlignment(Pos.CENTER);
			Utils.setTextFieldValidator(registrationNumberField, "[JCFjcf/0-9]*", "[JCF][0-9][0-9]/[0-9]{1,4}/[0-9]{4}",
					true, 14, "Numărul de înregistrare este de tipul: <J sau C sau F cifra cifra>/<un numar cu maxim 4 cifre>/<un numar cu fix patru cifre>.");
		} catch (Exception e) {
			DBServices.saveErrorLogEntry(e);
		}
	}
	
	@FXML
	private void handleSave() {
		try {
			if(allFieldsAreCorrect()) {
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
		} catch (Exception e) {
			DBServices.saveErrorLogEntry(e);
		}
	}
	
	private boolean allFieldsAreCorrect() {
		List<TextField> fields = Arrays.asList(firmNameField, registrationNumberField, fiscalCodeField, addressField, phoneNumberField, faxNumberField, 
				emailField, bankNameField, ibanCodeField, adminFirstNameField, adminLastNameField, adminIdCodeField, adminIdNumberField, adminPhoneNumberField);
		
		for(TextField index : fields) {
			if(index.getBorder() != null) {
				return false;
			}
		}
		return true;
	}
	
	@FXML
	private void handleAddRig() {
		try {
			javaFxMain.showAddUpdateRigsToFirm(null, false, false, "Editează utilaj");
		} catch (Exception e) {
			DBServices.saveErrorLogEntry(e);
		}
	}
	
	@FXML
	private void handleAddEmployee() {
		try {
			javaFxMain.showAddUpdateEmployeesToFirm(null, false, false,"Adaugă personal");
		} catch(Exception e) {
			DBServices.saveErrorLogEntry(e);
		}
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
