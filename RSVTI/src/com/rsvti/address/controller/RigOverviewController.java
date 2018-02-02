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
	private Label rigNameLabel;
	@FXML
	private Label dueDateLabel;
	@FXML
	private Label productionNumberLabel;
	@FXML
	private Label productionYearLabel;
	@FXML
	private Label iscirRegistrationNumberLabel;
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
			SimpleDateFormat format = new SimpleDateFormat(DBServices.getDatePattern());
			if(rig != null) {
				if(rig.isValve()) {
					rigNameLabel.setText(rig.getRigName() + " - supapă");
				} else {
					rigNameLabel.setText(rig.getRigName());
				}
				dueDateLabel.setText(format.format(rig.getDueDate()));
				productionNumberLabel.setText(rig.getProductionNumber());
				productionYearLabel.setText(rig.getProductionYear() + "");
				iscirRegistrationNumberLabel.setText(rig.getIscirRegistrationNumber());
				rigParameterTable.setItems(FXCollections.observableArrayList(rig.getParameters()));
				rigParameterTable.setPlaceholder(new Label(Constants.TABLE_PLACEHOLDER_MESSAGE));
				parameterNameColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getName()));
				parameterValueColumn.setCellValueFactory(cellData -> new SimpleDoubleProperty(Double.parseDouble(cellData.getValue().getValue())).asObject());
				parameterMeasuringUnit.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getMeasuringUnit()));
				if(rig.isValve()) {
					rigParameterTable.setVisible(false);
				} else {
					rigParameterTable.setVisible(true);
				}
				//TODO rigType and isValve should be displayed? How?
			} else {
				rigNameLabel.setText("Instalatie");
				dueDateLabel.setText(DBServices.getDatePattern().replaceAll("d", "z").replaceAll("M","l").replaceAll("y", "a"));
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
