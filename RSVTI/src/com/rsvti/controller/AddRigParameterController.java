package com.rsvti.controller;

import com.rsvti.common.Constants;
import com.rsvti.common.Utils;
import com.rsvti.model.database.DBServices;
import com.rsvti.model.entities.RigParameter;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ChangeListener;
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
	private Button saveButton;
	@FXML
	private Button deleteButton;
	
	@FXML
	public void initialize() {
		try {
			parameterType.setItems(FXCollections.observableArrayList(Constants.LIFTING_RIG, Constants.PRESSURE_RIG));
			parameterType.getSelectionModel().select(0);
			parameterTable.setItems(FXCollections.observableArrayList(DBServices.getAllRigParameters()));
			parameterTypeColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getType()));
			parameterNameColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getName()));
			parameterMeasurementUnitColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getMeasuringUnit()));
			deleteButton.setDisable(true);
			parameterTable.getSelectionModel().selectedItemProperty().addListener((event) -> {
				try {
					deleteButton.setDisable(false);
				} catch (Exception e) {
					DBServices.saveErrorLogEntry(e);
				}
			});
			parameterTable.setPlaceholder(new Label(Constants.TABLE_PLACEHOLDER_MESSAGE));
			saveButton.setDisable(true);
			
			ChangeListener<String> listener = (observable, oldValue, newValue) -> {
				if(Utils.allFieldsAreFilled(parameterNameField, parameterMeasurementUnitField)) {
					saveButton.setDisable(false);
				} else {
					saveButton.setDisable(true);
				}
			};
			
			parameterNameField.textProperty().addListener(listener);
			parameterMeasurementUnitField.textProperty().addListener(listener);
		} catch(Exception e) {
			DBServices.saveErrorLogEntry(e);
		}
	}
	
	@FXML
	private void handleAddRigParameter() {
		try {
			DBServices.saveEntry(new RigParameter(parameterType.getValue(), parameterNameField.getText(), parameterMeasurementUnitField.getText()));
			parameterTable.setItems(FXCollections.observableArrayList(DBServices.getAllRigParameters()));
		} catch(Exception e) {
			DBServices.saveErrorLogEntry(e);
		}
	}
	
	@FXML
	private void handleDeleteRigParameter() {
		try {
			RigParameter selectedParameter = parameterTable.getSelectionModel().getSelectedItem();
			if(selectedParameter != null) {
				DBServices.deleteEntry(selectedParameter);
				parameterTable.setItems(FXCollections.observableArrayList(DBServices.getAllRigParameters()));
			}
		} catch(Exception e) {
			DBServices.saveErrorLogEntry(e);
		}
	}
}
