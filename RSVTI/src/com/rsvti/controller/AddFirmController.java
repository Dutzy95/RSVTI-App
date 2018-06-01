package com.rsvti.controller;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import com.rsvti.JavaFxMain;
import com.rsvti.common.Constants;
import com.rsvti.common.Utils;
import com.rsvti.model.database.DBServices;
import com.rsvti.model.entities.Administrator;
import com.rsvti.model.entities.Employee;
import com.rsvti.model.entities.Firm;
import com.rsvti.model.entities.Rig;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ChangeListener;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.SplitPane;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableRow;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
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
	private TextArea addressArea;
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
	private Button deleteRigButton;
	@FXML
	private Button deleteEmployeeButton;
	
	@FXML
	private TableView<Employee> employeeTable;
	@FXML
	private TableColumn<Employee,String> employeeLastNameColumn;
	@FXML
	private TableColumn<Employee,String> employeeFirstNameColumn;
	
	@FXML
	private Button saveButton;
	
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
				try {
				    row.setOnMouseClicked(event -> {
				        if (event.getClickCount() == 2 && (! row.isEmpty()) ) {
				            Rig rowData = row.getItem();
				            if(update) {
				            	javaFxMain.showAddUpdateRigsToFirm(rowData, true, false, firmTable.getSelectionModel().getSelectedItem().getFirmName(), null, false);
				            } else {
				            	javaFxMain.showAddUpdateRigsToFirm(rowData, true, false, "Modifică utilaj", null, false);
				            }
				        }
				    });
				} catch(Exception e) {
					DBServices.saveErrorLogEntry(e);
				}
				return row ;
			});
			deleteRigButton.setDisable(true);
			rigTable.setPlaceholder(new Label(Constants.TABLE_PLACEHOLDER_MESSAGE));
			rigTable.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
				if(newValue != null) {
					deleteRigButton.setDisable(false);
				} else {
					deleteRigButton.setDisable(true);
				}
			});
			rigColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getRigName()));
			
			employeeTable.setItems(FXCollections.observableArrayList(employeeList));
			employeeTable.setPlaceholder(new Label(Constants.TABLE_PLACEHOLDER_MESSAGE));
			employeeLastNameColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getLastName()));
			employeeFirstNameColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getFirstName()));
			employeeTable.setRowFactory( tv -> {
				TableRow<Employee> row = new TableRow<>();
				try {
				    row.setOnMouseClicked(event -> {
				        if (event.getClickCount() == 2 && (! row.isEmpty()) ) {
				            Employee rowData = row.getItem();
				            if(update) {
				            	javaFxMain.showAddUpdateEmployeesToFirm(rowData, true, false, firmTable.getSelectionModel().getSelectedItem().getFirmName(), null, false);
				            } else {
				            	javaFxMain.showAddUpdateEmployeesToFirm(rowData, true, false, "Modifică angajat", null, false);
				            }
				        }
				    });
				} catch(Exception e) {
					DBServices.saveErrorLogEntry(e);
				}
			    return row ;
			});
			deleteEmployeeButton.setDisable(true);
			employeeTable.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
				if(newValue != null) {
					deleteEmployeeButton.setDisable(false);
				} else {
					deleteEmployeeButton.setDisable(true);
				}
			});
			
			ChangeListener<String> listener = (observable, oldValue, newValue) -> {
				if(Utils.allFieldsAreFilled(firmNameField, registrationNumberField, fiscalCodeField, addressArea, phoneNumberField,
						faxNumberField, emailField, bankNameField, ibanCodeField, executiveNameField, adminFirstNameField, adminLastNameField,
						adminIdCodeField, adminIdNumberField, adminPhoneNumberField)) {
					saveButton.setDisable(false);
				} else {
					saveButton.setDisable(true);
				}
			};
			firmNameField.textProperty().addListener(listener);
			registrationNumberField.textProperty().addListener(listener);
			fiscalCodeField.textProperty().addListener(listener);
			addressArea.textProperty().addListener(listener);
			phoneNumberField.textProperty().addListener(listener);
			faxNumberField.textProperty().addListener(listener);
			emailField.textProperty().addListener(listener);
			bankNameField.textProperty().addListener(listener);
			ibanCodeField.textProperty().addListener(listener);
			executiveNameField.textProperty().addListener(listener);
			adminFirstNameField.textProperty().addListener(listener);
			adminLastNameField.textProperty().addListener(listener);
			adminIdCodeField.textProperty().addListener(listener);
			adminIdNumberField.textProperty().addListener(listener);
			adminPhoneNumberField.textProperty().addListener(listener);
			
			saveButton.setDisable(true);
		} catch (Exception e) {
			DBServices.saveErrorLogEntry(e);
		}
	}
	
	public void initializeIfUpdate() {
		if(!update) {
			splitPane.getItems().remove(anchorPane);
			vbox.setPrefWidth(JavaFxMain.primaryStage.getWidth());
		} else {
			firmTable.setPlaceholder(new Label(Constants.TABLE_PLACEHOLDER_MESSAGE));
			firmTable.setItems(FXCollections.observableArrayList(DBServices.getAllFirms()));
			firmNameColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getFirmName()));
			firmTable.getSelectionModel().clearSelection();
			firmTable.getSelectionModel().selectedItemProperty().addListener((observable, oldvalue, newValue) -> {
				setFieldsAndTables(newValue);
			});
		}
		Utils.setTextFieldValidator(addressArea, "[A-Za-z0-9 ăâțșîÂÎĂȚȘ,\\.-]*", "[A-Za-z0-9 ăâțșîÂÎĂȚȘ,\\.-]*", false, Constants.INFINITE,
				"Adresa poate conține litere majuscule si minuscule cifre si caracterele . , -", JavaFxMain.primaryStage);
		Utils.setTextFieldValidator(adminFirstNameField, "[A-Za-z ăâțșîÂÎĂȚȘ]*", "[A-Za-z ăâțșîÂÎĂȚȘ]*", false, Constants.INFINITE,
				"Prenumele administratorului poate conține doar litere majuscule și minuscule.", JavaFxMain.primaryStage);
		Utils.setTextFieldValidator(adminIdCodeField, "[A-Za-z]*", "[A-Z]{2}", true, 2,
				"Seria de buletin a administratorului poate conține doar două litere majuscule.", JavaFxMain.primaryStage);
		Utils.setTextFieldValidator(adminIdNumberField, "[0-9]*", "[0-9]{6}", false, 6,
				"Numărul de buletin al administratorului poate conține doar 6 cifre.", JavaFxMain.primaryStage);
		Utils.setTextFieldValidator(adminLastNameField, "[A-Za-z ăâțșîÂÎĂȚȘ]*", "[A-Za-z ăâțșîÂÎĂȚȘ]*", false, Constants.INFINITE,
				"Numele administratorului poate conține doar litere majuscule și minuscule.", JavaFxMain.primaryStage);
		Utils.setTextFieldValidator(adminPhoneNumberField, "[+0-9]*", "\\+?([0-9]{11}|[0-9]{10})", false, 12, 
				"Numărul de telefon al administratotorului poate conține doar 10 cifre sau + urmat de 11 cifre.", JavaFxMain.primaryStage);
		Utils.setTextFieldValidator(bankNameField, "[A-Za-z ăâțșîÂÎĂȚȘ]*", "[A-Za-z ăâțșîÂÎĂȚȘ]*", false, Constants.INFINITE,
				"Numele băncii poate conține doar litere majuscule sau minuscule.", JavaFxMain.primaryStage);
		Utils.setTextFieldValidator(emailField, "[A-Za-z0-9_%+-@]*", "[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,6}", false, Constants.INFINITE,
				"Emailul poate contine doar litere majuscule sau minuscule, cifre si caracterele _ % + - .\nExemplu: ion_ionescu.01@exemplu.com", JavaFxMain.primaryStage);
		Utils.setTextFieldValidator(faxNumberField, "[+0-9]*", "\\+?([0-9]{11}|[0-9]{10})", false, 12,
				"Numărul de fax poate conține doar 10 cifre sau + urmat de 11 cifre.", JavaFxMain.primaryStage);
		Utils.setTextFieldValidator(firmNameField, "[A-Za-z ăâțșîÂÎĂȚȘ]*", "[A-Za-z ăâțșîÂÎĂȚȘ]*", false, Constants.INFINITE, 
				"Numele firmei poate conține doar litere majuscule și minuscule.", JavaFxMain.primaryStage);
		Utils.setTextFieldValidator(fiscalCodeField, "[ROro0-9]*", "(RO)?[0-9]{6,9}[0-9]", true, 11,
				"Codul fiscal poate conține RO urmat de minim 7 și maxim 10 cifre.", JavaFxMain.primaryStage);
		Utils.setTextFieldValidator(ibanCodeField, "[A-Za-z0-9 ]*", "RO[0-9][0-9] ?[A-Z]{4}( ?[A-Z0-9]{4}){4}", true, 29,
				"Codul IBAN este format din RO urmat de doua cifre, indicativul bancii format din patru litere, urmat de 16 cifre sau litere.", JavaFxMain.primaryStage);
		Utils.setTextFieldValidator(phoneNumberField, "[+0-9]*", "\\+?([0-9]{11}|[0-9]{10})", false, 12,
				"Numărul de telefon poate conține doar 10 cifre sau + urmat de 11 cifre.", JavaFxMain.primaryStage);
		Utils.setTextFieldValidator(registrationNumberField, "[JCFjcf/0-9]*", "[JCF][0-9][0-9]/[0-9]{1,4}/[0-9]{4}",
				true, 14, "Numărul de înregistrare este de tipul: <J sau C sau F cifra cifra>/<un numar cu maxim 4 cifre>/<un numar cu fix patru cifre>.", JavaFxMain.primaryStage);
		Utils.setTextFieldValidator(executiveNameField, "[A-Za-z ăâțșîÂÎĂȚȘ]*", "[A-Za-z ăâțșîÂÎĂȚȘ]*", false, Constants.INFINITE,
				"Numele directorului poate conține doar litere majuscule sau minuscule.", JavaFxMain.primaryStage);
	}
	
	@FXML
	private void handleSave() {
		try {
			if(Utils.allFieldsAreValid(firmNameField, registrationNumberField, fiscalCodeField, addressArea, phoneNumberField, faxNumberField, 
					emailField, bankNameField, ibanCodeField, adminFirstNameField, adminLastNameField, adminIdCodeField, adminIdNumberField, 
					adminPhoneNumberField)) {
				Firm firm = new Firm(
						firmNameField.getText(),
						registrationNumberField.getText(),
						fiscalCodeField.getText(),
						addressArea.getText(),
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
	
	@FXML
	private void handleAddRig() {
		try {
			javaFxMain.showAddUpdateRigsToFirm(null, false, false, "Adaugă utilaj", null, false);
		} catch (Exception e) {
			DBServices.saveErrorLogEntry(e);
		}
	}
	
	@FXML
	private void handleAddEmployee() {
		try {
			javaFxMain.showAddUpdateEmployeesToFirm(null, false, false,"Adaugă angajat", null, false);
		} catch(Exception e) {
			DBServices.saveErrorLogEntry(e);
		}
	}
	
	@FXML
	private void handleDeleteRig() {
		try {
			Rig rig = rigTable.getSelectionModel().getSelectedItem();
			if(rig != null) {
				rigList.remove(rig);
				Collections.sort(rigList, (r1, r2) -> r1.getRigName().compareToIgnoreCase(r2.getRigName()));
				rigTable.setItems(FXCollections.observableArrayList(rigList));
			}
		} catch(Exception e) {
			DBServices.saveErrorLogEntry(e);
		}
	}
	
	@FXML
	private void handleDeleteEmployee() {
		try {
			Employee employee = employeeTable.getSelectionModel().getSelectedItem();
			if(employee != null) {
				employeeList.remove(employee);
				Collections.sort(employeeList, (e1, e2) -> e1.getLastName().compareToIgnoreCase(e2.getLastName()));
				employeeTable.setItems(FXCollections.observableArrayList(employeeList));
			}
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
		Collections.sort(rigList, (r1, r2) -> r1.getRigName().compareToIgnoreCase(r2.getRigName()));
		rigTable.setItems(FXCollections.observableArrayList(rigList));
		rigTable.refresh();
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
		Collections.sort(employeeList, (e1, e2) -> e1.getLastName().compareToIgnoreCase(e2.getLastName()));
		employeeTable.setItems(FXCollections.observableArrayList(employeeList));
		employeeTable.refresh();
	}
	
	public void setFieldsAndTables(Firm firm) {
		if(firm != null) {
			firmNameField.setText(firm.getFirmName());
			registrationNumberField.setText(firm.getRegistrationNumber());
			fiscalCodeField.setText(firm.getFiscalCode());
			addressArea.setText(firm.getAddress());
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
