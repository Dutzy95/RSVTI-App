package com.rsvti.address.controller;

import java.text.SimpleDateFormat;
import java.util.List;

import com.rsvti.common.Constants;
import com.rsvti.database.entities.ParameterDetails;
import com.rsvti.database.entities.Rig;
import com.rsvti.database.services.DBServices;

import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;

public class RigOverviewController {

	@FXML
	private TableView<Rig> rigTable;
	@FXML
	private TableColumn<Rig,String> rigNameColumn;
	
	@FXML
	private Label rigTypeLabel;
	@FXML
	private Label rigNameLabel;
	@FXML
	private Label dueDateLabel;
	@FXML
	private TableView<ParameterDetails> rigParameterTable;
	@FXML
	private TableColumn<ParameterDetails ,String> parameterNameColumn;
	@FXML
	private TableColumn<ParameterDetails ,Double> parameterValueColumn;
	@FXML
	private TableColumn<ParameterDetails ,String> parameterMeasuringUnit;
	
	@FXML
	private List<Rig> rigList;
	
	private void showRigDetails(Rig rig) {
		try {
			if(rig != null) {
				rigNameLabel.setText(rig.getRigName());
				SimpleDateFormat format = new SimpleDateFormat(DBServices.getDatePattern());
				dueDateLabel.setText(format.format(rig.getDueDate()));
				rigParameterTable.setItems(FXCollections.observableArrayList(rig.getParameters()));
				rigParameterTable.setPlaceholder(new Label(Constants.TABLE_PLACEHOLDER_MESSAGE));
				parameterNameColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getName()));
				parameterValueColumn.setCellValueFactory(cellData -> new SimpleDoubleProperty(Double.parseDouble(cellData.getValue().getValue())).asObject());
				parameterMeasuringUnit.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getMeasuringUnit()));
			} else {
				rigNameLabel.setText("Instalatie");
				dueDateLabel.setText("zz-ll-aaaa");
			}
		} catch (Exception e) {
			DBServices.saveErrorLogEntry(e);
		}
	}
	
	public void setRigList(List<Rig> rigList) {
		try {
			this.rigList = rigList;
			rigTable.setItems(FXCollections.observableArrayList(rigList));
			rigTable.setPlaceholder(new Label(Constants.TABLE_PLACEHOLDER_MESSAGE));
			rigNameColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getRigName()));
			
			showRigDetails(null);
			
			rigTable.getSelectionModel().selectedItemProperty().addListener((observable, oldvalue, newValue) -> showRigDetails(newValue));
		} catch (Exception e) {
			DBServices.saveErrorLogEntry(e);
		}
	}
}
