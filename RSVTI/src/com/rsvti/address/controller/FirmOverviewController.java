package com.rsvti.address.controller;

import com.rsvti.address.JavaFxMain;
import com.rsvti.common.Constants;
import com.rsvti.database.entities.Firm;
import com.rsvti.database.services.DBServices;

import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;

public class FirmOverviewController {
	
	@FXML
	private TableView<Firm> firmTable;
	@FXML
	private TableColumn<Firm,String> registrationNumberColumn;
	@FXML
	private TableColumn<Firm,String> firmNameColumn;
	
	@FXML
	private Label firmNameLabel;
	@FXML
	private Label registrationNumberLabel;
	@FXML
	private Label fiscalCodeLabel;
	@FXML
	private Label addressLabel;
	@FXML
	private Label phoneNumberLabel;
	@FXML
	private Label faxNumberLabel;
	@FXML
	private Label emailLabel;
	@FXML
	private Label bankNameLabel;
	@FXML
	private Label ibanCodeLabel;
	@FXML
	private Label executiveNameLabel;
	
	@FXML
	private Label adminFirstNameLabel;
	@FXML
	private Label adminLastNameLabel;
	@FXML
	private Label adminIdCodeLabel;
	@FXML
	private Label adminIdNumberLabel;
	@FXML
	private Label adminPhoneNumberLabel;
	
	@FXML
	private Button rigOverviewButton;
	@FXML
	private Button employeeOverviewButton;
	@FXML
	private Button deleteFirmButton;
	@FXML
	private Button rsvtiOverviewButton;
	
	private JavaFxMain javaFxMain;
	
	@FXML
	private void initialize() {
		try {
			firmNameColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getFirmName()));
			registrationNumberColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getRegistrationNumber()));
			
			showFirmDetails(null);
			
			firmTable.setItems(FXCollections.observableArrayList(DBServices.getAllFirms()));
			firmTable.getSelectionModel().selectedItemProperty().addListener((observable, oldvalue, newValue) -> showFirmDetails(newValue));
			firmTable.setPlaceholder(new Label(Constants.TABLE_PLACEHOLDER_MESSAGE));
			rigOverviewButton.setDisable(true);
			deleteFirmButton.setDisable(true);
			employeeOverviewButton.setDisable(true);
			rsvtiOverviewButton.setDisable(true);
		} catch (Exception e) {
			DBServices.saveErrorLogEntry(e);
		}
	}
	
	private void showFirmDetails(Firm firm) {
		try {
			rigOverviewButton.setDisable(false);
			deleteFirmButton.setDisable(false);
			employeeOverviewButton.setDisable(false);
			rsvtiOverviewButton.setDisable(false);
			if(firm != null) {
				firmNameLabel.setText(firm.getFirmName());
				registrationNumberLabel.setText(firm.getRegistrationNumber());
				fiscalCodeLabel.setText(firm.getFiscalCode());
				addressLabel.setText(firm.getAddress());
				phoneNumberLabel.setText(firm.getPhoneNumber());
				faxNumberLabel.setText(firm.getFaxNumber());
				emailLabel.setText(firm.getEmail());
				bankNameLabel.setText(firm.getBankName());
				ibanCodeLabel.setText(firm.getIbanCode());
				executiveNameLabel.setText(firm.getExecutiveName());
				adminFirstNameLabel.setText(firm.getAdministrator().getFirstName());
				adminLastNameLabel.setText(firm.getAdministrator().getLastName());
				adminIdCodeLabel.setText(firm.getAdministrator().getIdCode());
				adminIdNumberLabel.setText(firm.getAdministrator().getIdNumber());
				adminPhoneNumberLabel.setText(firm.getAdministrator().getPhoneNumber());
			} else {
				firmNameLabel.setText("");
				registrationNumberLabel.setText("");
				fiscalCodeLabel.setText("");
				addressLabel.setText("");
				phoneNumberLabel.setText("");
				faxNumberLabel.setText("");
				emailLabel.setText("");
				bankNameLabel.setText("");
				ibanCodeLabel.setText("");
				executiveNameLabel.setText("");
				adminFirstNameLabel.setText("");
				adminLastNameLabel.setText("");
				adminIdCodeLabel.setText("");
				adminIdNumberLabel.setText("");
				adminPhoneNumberLabel.setText("");
			}
		} catch (Exception e) {
			DBServices.saveErrorLogEntry(e);
		}
	}
	
	@FXML
	private void handleDeleteFirm() {
		try {
			int selectedIndex = firmTable.getSelectionModel().getSelectedIndex();
			if (selectedIndex >= 0) {
				DBServices.deleteEntry(firmTable.getItems().get(selectedIndex));
				firmTable.getItems().remove(selectedIndex);
			}
		} catch (Exception e) {
			DBServices.saveErrorLogEntry(e);
		}
	}
	
	@FXML
	private void handleOpenRigOverview() {
		try {
			int selectedIndex = firmTable.getSelectionModel().getSelectedIndex();
			if(selectedIndex >= 0) {
				javaFxMain.showRigOverview(firmTable.getItems().get(selectedIndex).getFirmName(), firmTable.getItems().get(selectedIndex).getRigs());
			}
		} catch (Exception e) {
			DBServices.saveErrorLogEntry(e);
		}
	}
	
	@FXML
	private void handleOpenEmployeeOverview() {
		try {
			int selectedIndex = firmTable.getSelectionModel().getSelectedIndex();
			if(selectedIndex >= 0 ) {
				javaFxMain.showEmployeeOverview(firmTable.getItems().get(selectedIndex).getFirmName(), firmTable.getItems().get(selectedIndex).getEmployees());
			}
		} catch (Exception e) {
			DBServices.saveErrorLogEntry(e);
		}
	}
	
	@FXML
	private void handleOpenRsvtiOverview() {
		try {
			int selectedIndex = firmTable.getSelectionModel().getSelectedIndex();
			if(selectedIndex >= 0 ) {
				String firmName = firmTable.getItems().get(selectedIndex).getFirmName();
				javaFxMain.showEmployeeOverview(firmName, DBServices.getRsvtiFromFirm(firmName));
			}
		} catch (Exception e) {
			DBServices.saveErrorLogEntry(e);
		}
	}
	
	public void setJavaFxMain(JavaFxMain javaFxMain) {
		this.javaFxMain = javaFxMain;
	}
}
