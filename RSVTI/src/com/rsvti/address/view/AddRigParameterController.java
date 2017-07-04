package com.rsvti.address.view;

import com.rsvti.common.Constants;
import com.rsvti.database.entities.RigParameter;
import com.rsvti.database.services.DBServices;

import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;

public class AddRigParameterController {
	
	@FXML
	private ComboBox<String> parameterType;
	@FXML
	private TextField parameterNameField;
	@FXML
	private TextField parameterMeasurementUnitField;
	
	@FXML
	private TableView<RigParameter> parameterTable;
	@FXML
	private TableColumn<RigParameter,String> parameterTypeColumn;
	@FXML
	private TableColumn<RigParameter,String> parameterNameColumn;
	@FXML
	private TableColumn<RigParameter,String> parameterMeasurementUnitColumn;
	
	@FXML
	private Button deleteButton;
	
	@FXML
	public void initialize() {
		parameterType.setItems(FXCollections.observableArrayList("de ridicat", "sub presiune"));
		parameterType.getSelectionModel().select(0);
		parameterTable.setItems(FXCollections.observableArrayList(DBServices.getAllRigParameters()));
		parameterTypeColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getType()));
		parameterNameColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getName()));
		parameterMeasurementUnitColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getMeasuringUnit()));
		deleteButton.setDisable(true);
		parameterTable.getSelectionModel().selectedItemProperty().addListener((event) -> {deleteButton.setDisable(false);});
		parameterTable.setPlaceholder(new Label(Constants.TABLE_PLACEHOLDER_MESSAGE));
	}
	
	@FXML
	private void handleAddRigParameter() {
		DBServices.saveEntry(new RigParameter(parameterType.getValue(), parameterNameField.getText(), parameterMeasurementUnitField.getText()));
		parameterTable.setItems(FXCollections.observableArrayList(DBServices.getAllRigParameters()));
	}
	
	@FXML
	private void handleDeleteRigParameter() {
		RigParameter selectedParameter = parameterTable.getSelectionModel().getSelectedItem();
		if(selectedParameter != null) {
			DBServices.deleteEntry(selectedParameter);
			parameterTable.setItems(FXCollections.observableArrayList(DBServices.getAllRigParameters()));
		}
	}
}
