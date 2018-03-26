package com.rsvti.address.controller;

import java.util.ArrayList;
import java.util.Arrays;
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
import javafx.scene.control.SplitPane;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableRow;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

public class AddFirmController {
	
	@FXML
	private TableView<Firm> firmTable;
	@FXML
	private TableColumn<Firm, String> firmNameColumn;
	@FXML
	private SplitPane splitPane;
	@FXML
	private HBox hbox1;
	@FXML
	private HBox hbox2;
	@FXML
	private	AnchorPane anchorPane;
	@FXML
	private VBox vbox;
	
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
	private TextField executiveNameField;
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
	private boolean update;
	
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
		} catch (Exception e) {
			DBServices.saveErrorLogEntry(e);
		}
	}
	
	public void initializeIfUpdate() {
		if(!update) {
			splitPane.getItems().remove(anchorPane);
			vbox.setPrefWidth(JavaFxMain.primaryStage.getWidth());
		} else {
			firmTable.setItems(FXCollections.observableArrayList(DBServices.getAllFirms()));
			firmNameColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getFirmName()));
			firmTable.getSelectionModel().clearSelection();
			firmTable.getSelectionModel().selectedItemProperty().addListener((observable, oldvalue, newValue) -> {
				setFieldsAndTables(newValue);
			});
		}
		addressField.setAlignment(Pos.CENTER);
		Utils.setTextFieldValidator(addressField, "[A-Za-z0-9 ăâțșîÂÎĂȚȘ,\\.-]*", "[A-Za-z0-9 ăâțșîÂÎĂȚȘ,\\.-]*", false, Constants.INFINITE,
				"Adresa poate conține litere majuscule si minuscule cifre si caracterele . , -", JavaFxMain.primaryStage);
		adminFirstNameField.setAlignment(Pos.CENTER);
		Utils.setTextFieldValidator(adminFirstNameField, "[A-Za-z ăâțșîÂÎĂȚȘ]*", "[A-Za-z ăâțșîÂÎĂȚȘ]*", false, Constants.INFINITE,
				"Prenumele administratorului poate conține doar litere majuscule și minuscule.", JavaFxMain.primaryStage);
		adminIdCodeField.setAlignment(Pos.CENTER);
		Utils.setTextFieldValidator(adminIdCodeField, "[A-Za-z]*", "[A-Z]{2}", true, 2,
				"Seria de buletin a administratorului poate conține doar două litere majuscule.", JavaFxMain.primaryStage);
		adminIdNumberField.setAlignment(Pos.CENTER);
		Utils.setTextFieldValidator(adminIdNumberField, "[0-9]*", "[0-9]{6}", false, 6,
				"Numărul de buletin al administratorului poate conține doar 6 cifre.", JavaFxMain.primaryStage);
		adminLastNameField.setAlignment(Pos.CENTER);
		Utils.setTextFieldValidator(adminLastNameField, "[A-Za-z ăâțșîÂÎĂȚȘ]*", "[A-Za-z ăâțșîÂÎĂȚȘ]*", false, Constants.INFINITE,
				"Numele administratorului poate conține doar litere majuscule și minuscule.", JavaFxMain.primaryStage);
		adminPhoneNumberField.setAlignment(Pos.CENTER);
		Utils.setTextFieldValidator(adminPhoneNumberField, "[+0-9]*", "\\+?([0-9]{11}|[0-9]{10})", false, 12, 
				"Numărul de telefon al administratotorului poate conține doar 10 cifre sau + urmat de 11 cifre.", JavaFxMain.primaryStage);
		bankNameField.setAlignment(Pos.CENTER);
		Utils.setTextFieldValidator(bankNameField, "[A-Za-z ăâțșîÂÎĂȚȘ]*", "[A-Za-z ăâțșîÂÎĂȚȘ]*", false, Constants.INFINITE,
				"Numele băncii poate conține doar litere majuscule sau minuscule.", JavaFxMain.primaryStage);
		emailField.setAlignment(Pos.CENTER);
		Utils.setTextFieldValidator(emailField, "[A-Za-z0-9_%+-@]*", "[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,6}", false, Constants.INFINITE,
				"Emailul poate contine doar litere majuscule sau minuscule, cifre si caracterele _ % + - .\nExemplu: ion_ionescu.01@exemplu.com", JavaFxMain.primaryStage);
		faxNumberField.setAlignment(Pos.CENTER);
		Utils.setTextFieldValidator(faxNumberField, "[+0-9]*", "\\+?([0-9]{11}|[0-9]{10})", false, 12,
				"Numărul de fax poate conține doar 10 cifre sau + urmat de 11 cifre.", JavaFxMain.primaryStage);
		firmNameField.setAlignment(Pos.CENTER);
		Utils.setTextFieldValidator(firmNameField, "[A-Za-z ăâțșîÂÎĂȚȘ]*", "[A-Za-z ăâțșîÂÎĂȚȘ]*", false, Constants.INFINITE, 
				"Numele firmei poate conține doar litere majuscule și minuscule.", JavaFxMain.primaryStage);
		fiscalCodeField.setAlignment(Pos.CENTER);
		Utils.setTextFieldValidator(fiscalCodeField, "[ROro0-9]*", "(RO)?[0-9]{6,9}[0-9]", true, 11,
				"Codul fiscal poate conține RO urmat de minim 7 și maxim 10 cifre.", JavaFxMain.primaryStage);
		ibanCodeField.setAlignment(Pos.CENTER);
		Utils.setTextFieldValidator(ibanCodeField, "[A-Za-z0-9 ]*", "RO[0-9][0-9] ?[A-Z]{4}( ?[A-Z0-9]{4}){4}", true, 29,
				"Codul IBAN este format din RO urmat de doua cifre, indicativul bancii format din patru litere, urmat de 16 cifre sau litere.", JavaFxMain.primaryStage);
		phoneNumberField.setAlignment(Pos.CENTER);
		Utils.setTextFieldValidator(phoneNumberField, "[+0-9]*", "\\+?([0-9]{11}|[0-9]{10})", false, 12,
				"Numărul de telefon poate conține doar 10 cifre sau + urmat de 11 cifre.", JavaFxMain.primaryStage);
		registrationNumberField.setAlignment(Pos.CENTER);
		Utils.setTextFieldValidator(registrationNumberField, "[JCFjcf/0-9]*", "[JCF][0-9][0-9]/[0-9]{1,4}/[0-9]{4}",
				true, 14, "Numărul de înregistrare este de tipul: <J sau C sau F cifra cifra>/<un numar cu maxim 4 cifre>/<un numar cu fix patru cifre>.", JavaFxMain.primaryStage);
		executiveNameField.setAlignment(Pos.CENTER);
		Utils.setTextFieldValidator(executiveNameField, "[A-Za-z ăâțșîÂÎĂȚȘ]*", "[A-Za-z ăâțșîÂÎĂȚȘ]*", false, Constants.INFINITE,
				"Numele directorului poate conține doar litere majuscule sau minuscule.", JavaFxMain.primaryStage);
	}
	
	@FXML
	private void handleSave() {
		try {
			if(allFieldsAreCorrect()) {
				Firm firm = new Firm(
						firmNameField.getText(),
						registrationNumberField.getText(),
						fiscalCodeField.getText(),
						addressField.getText(),
						phoneNumberField.getText(),
						faxNumberField.getText(),
						emailField.getText(),
						bankNameField.getText(),
						ibanCodeField.getText(),
						executiveNameField.getText(),
						new Administrator(
								adminFirstNameField.getText(),
								adminLastNameField.getText(),
								adminIdCodeField.getText(),
								adminIdNumberField.getText(),
								adminPhoneNumberField.getText()),
						rigTable.getItems(), employeeTable.getItems());
				if(update) {
					firm.setId(firmTable.getSelectionModel().getSelectedItem().getId());
					DBServices.updateEntry(firmTable.getSelectionModel().getSelectedItem(), firm);
					firmTable.setItems(FXCollections.observableArrayList(DBServices.getAllFirms()));
					firmTable.refresh();
				} else {
					DBServices.saveEntry(firm, false);
					javaFxMain.getTabPane().getTabs().remove(javaFxMain.getAddFirmTab());
				}
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
	
	public void setFieldsAndTables(Firm firm) {
		if(firm != null) {
			firmNameField.setText(firm.getFirmName());
			registrationNumberField.setText(firm.getRegistrationNumber());
			fiscalCodeField.setText(firm.getFiscalCode());
			addressField.setText(firm.getAddress());
			phoneNumberField.setText(firm.getPhoneNumber());
			faxNumberField.setText(firm.getFaxNumber());
			emailField.setText(firm.getEmail());
			bankNameField.setText(firm.getBankName());
			ibanCodeField.setText(firm.getIbanCode());
			executiveNameField.setText(firm.getExecutiveName());
			adminFirstNameField.setText(firm.getAdministrator().getFirstName());
			adminLastNameField.setText(firm.getAdministrator().getLastName());
			adminIdCodeField.setText(firm.getAdministrator().getIdCode());
			adminIdNumberField.setText(firm.getAdministrator().getIdNumber());
			adminPhoneNumberField.setText(firm.getAdministrator().getPhoneNumber());
			rigList = firm.getRigs();
			rigTable.setItems(FXCollections.observableArrayList(rigList));
			employeeList = firm.getEmployees();
			employeeTable.setItems(FXCollections.observableArrayList(employeeList));
		}
	}
	
	public void setUpdate(boolean update) {
		this.update = update;
	}
	
	public void setJavaFxMain(JavaFxMain javaFxMain) {
		this.javaFxMain = javaFxMain;
	}
}
