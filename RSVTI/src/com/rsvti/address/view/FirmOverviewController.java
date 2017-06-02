package com.rsvti.address.view;

import com.rsvti.address.JavaFxMain;
import com.rsvti.database.entities.Firm;
import com.rsvti.database.services.DBServices;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;

public class FirmOverviewController {
	
	@FXML
	private TableView<Firm> firmTable;
	@FXML
	private TableColumn<Firm,String> registrationNumberColumn;
	@FXML
	private TableColumn<Firm,String> adminNameColumn;
	
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
	private Label adminFirstNameLabel;
	@FXML
	private Label adminLastNameLabel;
	@FXML
	private Label adminIdCodeLabel;
	@FXML
	private Label adminIdNumberLabel;
	@FXML
	private Label adminPhoneNumberLabel;
	
	private JavaFxMain javaFxMain;
	
	public FirmOverviewController() {
	}
		
	@FXML
	private void initialize() {
		registrationNumberColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getRegistrationNumber()));
		adminNameColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getAdministrator().getFirstName()));
		
		showFirmDetails(null);
		
		firmTable.getSelectionModel().selectedItemProperty().addListener((observable, oldvalue, newValue) -> showFirmDetails(newValue));
	}
	
	private void showFirmDetails(Firm firm) {
		if(firm != null) {
			registrationNumberLabel.setText(firm.getRegistrationNumber());
			fiscalCodeLabel.setText(firm.getFiscalCode());
			addressLabel.setText(firm.getAddress());
			phoneNumberLabel.setText(firm.getPhoneNumber());
			faxNumberLabel.setText(firm.getFaxNumber());
			emailLabel.setText(firm.getEmail());
			bankNameLabel.setText(firm.getBankName());
			ibanCodeLabel.setText(firm.getIbanCode());
			adminFirstNameLabel.setText(firm.getAdministrator().getFirstName());
			adminLastNameLabel.setText(firm.getAdministrator().getLastName());
			adminIdCodeLabel.setText(firm.getAdministrator().getIdCode());
			adminIdNumberLabel.setText(firm.getAdministrator().getIdNumber());
			adminPhoneNumberLabel.setText(firm.getAdministrator().getPhoneNumber());
		} else {
			registrationNumberLabel.setText("");
			fiscalCodeLabel.setText("");
			addressLabel.setText("");
			phoneNumberLabel.setText("");
			faxNumberLabel.setText("");
			emailLabel.setText("");
			bankNameLabel.setText("");
			ibanCodeLabel.setText("");
			adminFirstNameLabel.setText("");
			adminLastNameLabel.setText("");
			adminIdCodeLabel.setText("");
			adminIdNumberLabel.setText("");
			adminPhoneNumberLabel.setText("");
		}
	}
	
	@FXML
	private void handleDeleteFirm() {
		int selectedIndex = firmTable.getSelectionModel().getSelectedIndex();
		if (selectedIndex >= 0) {
			DBServices.deleteEntry(firmTable.getItems().get(selectedIndex));
			firmTable.getItems().remove(selectedIndex);
	    } else {
	        // Nothing selected.
	        Alert alert = new Alert(AlertType.WARNING);
	        alert.initOwner(javaFxMain.getPrimaryStage());
	        alert.setTitle("Nu exista selectie");
	        alert.setHeaderText("Nu ati selectat o firma");
	        alert.setContentText("Selectati o firma din tabel.");

	        alert.showAndWait();
	    }
	}
	
	@FXML
	private void handleOpenRigOverview() {
		int selectedIndex = firmTable.getSelectionModel().getSelectedIndex();
		if(selectedIndex >= 0) {
			javaFxMain.showRigOverview(firmTable.getItems().get(selectedIndex).getRigs());
		}
	}
	
	public void setJavaFxMain(JavaFxMain javaFxMain) {
		this.javaFxMain = javaFxMain;
		firmTable.setItems(javaFxMain.getFirmData());
	}
}
