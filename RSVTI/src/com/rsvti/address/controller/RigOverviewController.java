package com.rsvti.address.controller;

import java.text.SimpleDateFormat;
import java.util.List;

import com.rsvti.common.Constants;
import com.rsvti.database.entities.ParameterDetails;
import com.rsvti.database.entities.Rig;
import com.rsvti.database.services.DBServices;

import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.layout.GridPane;

public class RigOverviewController {

	@FXML
	private TableView<Rig> rigTable;
	@FXML
	private TableColumn<Rig, String> firmNameColumn;
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
	private Label rigTypeLabel;
	@FXML
	private Label valveTitleLabel;
	@FXML
	private Label valveDueDateLabel;
	@FXML
	private Label valveRegistrationNumberLabel;
	@FXML
	private GridPane valveGridPane;
	@FXML
	private TableView<ParameterDetails> rigParameterTable;
	@FXML
	private TableColumn<ParameterDetails, String> parameterNameColumn;
	@FXML
	private TableColumn<ParameterDetails, String> parameterValueColumn;
	@FXML
	private TableColumn<ParameterDetails, String> parameterMeasuringUnit;
	
	@FXML
	private List<Rig> rigList;
	
	private void showRigDetails(Rig rig) {
		try {
			SimpleDateFormat format = new SimpleDateFormat(DBServices.getDatePattern());
			if(rig != null) {
				rigNameLabel.setText(rig.getRigName());
				dueDateLabel.setText(format.format(rig.getDueDate()));
				productionNumberLabel.setText(rig.getProductionNumber());
				productionYearLabel.setText(format.format(rig.getProductionYear()));
				iscirRegistrationNumberLabel.setText(rig.getIscirRegistrationNumber());
				rigTypeLabel.setText(rig.getType());
				rigParameterTable.setItems(FXCollections.observableArrayList(rig.getParameters()));
				rigParameterTable.setPlaceholder(new Label(Constants.TABLE_PLACEHOLDER_MESSAGE));
				parameterNameColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getName()));
				parameterValueColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getValue()));
				parameterMeasuringUnit.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getMeasuringUnit()));
				if(rig.getType().equals(Constants.PRESSURE_RIG)) {
					valveTitleLabel.setVisible(true);
					valveGridPane.setVisible(true);
					valveDueDateLabel.setText(format.format(rig.getValve().getDueDate(rig.getValve().isNotExtended())));
					valveRegistrationNumberLabel.setText(rig.getValve().getRegistrationNumber());
				} else {
					valveTitleLabel.setVisible(false);
					valveGridPane.setVisible(false);
				}
			} else {
				rigNameLabel.setText("Instalatie");
				dueDateLabel.setText("");
				productionNumberLabel.setText("");
				productionYearLabel.setText("");
				iscirRegistrationNumberLabel.setText("");
				rigTypeLabel.setText("");
				valveTitleLabel.setVisible(false);
				valveGridPane.setVisible(false);
			}
		} catch (Exception e) {
			DBServices.saveErrorLogEntry(e);
		}
	}
	
	public void setRigList(List<Rig> rigList, boolean showFirmName) {
		try {
			this.rigList = rigList;
			rigTable.setItems(FXCollections.observableArrayList(rigList));
			rigTable.setPlaceholder(new Label(Constants.TABLE_PLACEHOLDER_MESSAGE));
			rigNameColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getRigName()));
			if(showFirmName) {
				firmNameColumn.setCellValueFactory(cellData -> new SimpleStringProperty(DBServices.getFirmForRig(cellData.getValue())));
			} else {
				firmNameColumn.setVisible(false);
			}
			
			showRigDetails(null);
			
			rigTable.getSelectionModel().selectedItemProperty().addListener((observable, oldvalue, newValue) -> showRigDetails(newValue));
		} catch (Exception e) {
			DBServices.saveErrorLogEntry(e);
		}
	}
}
