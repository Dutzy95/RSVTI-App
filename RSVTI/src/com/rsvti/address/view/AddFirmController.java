package com.rsvti.address.view;

import com.rsvti.address.JavaFxMain;
import com.rsvti.database.entities.Administrator;
import com.rsvti.database.entities.Firm;
import com.rsvti.database.entities.Rig;
import com.rsvti.database.services.DBServices;

import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.ListView;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.ComboBoxListCell;

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
	private JavaFxMain javaFxMain;
	
	public AddFirmController() {
		
	}
	
	@FXML
	private void initialize() {
		rigTable.setItems(FXCollections.observableArrayList(DBServices.getAllRigs()));
		rigColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getRigName()));
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
				null), false);
	}
	
	@FXML
	private void handleAddRig() {
		javaFxMain.showAddRigsToFirm();
	}
	
	public void setJavaFxMain(JavaFxMain javaFxMain) {
		this.javaFxMain = javaFxMain;
	}
}
