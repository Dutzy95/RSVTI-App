package com.rsvti.address.controller;

import java.awt.Desktop;
import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import com.rsvti.common.Constants;
import com.rsvti.common.Utils;
import com.rsvti.database.entities.RigWithDetails;
import com.rsvti.database.services.DBServices;
import com.rsvti.generator.Generator;

import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ButtonType;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;

public class GenerateTechnicalRigEvaluationReportController {
	
	@FXML
	private TableView<RigWithDetails> rigTable;
	@FXML
	private TableColumn<RigWithDetails, String> firmNameColumn;
	@FXML
	private TableColumn<RigWithDetails, String> rigColumn;
	
	@FXML
	private TextField reportNumberField;
	@FXML
	private DatePicker reportCreationDate;
	@FXML
	private TextField rigCodeField;
	@FXML
	private Label liftingRigTypeLabel;
	@FXML
	private CheckBox firstTimeCheckBox;
	@FXML
	private ComboBox<String> liftingRigTypeComboBox;
	
	public void initialize() {
		try {
			rigTable.setItems(FXCollections.observableArrayList(removeLargePressureRigs(
					DBServices.getRigsBetweenDateInterval(
							Constants.LOW_DATE,
							Constants.HIGH_DATE,
							(r1, r2) -> r1.getRig().getRigName().compareToIgnoreCase(r2.getRig().getRigName())))));
			rigTable.getSelectionModel().selectedItemProperty().addListener((observable, oldvalue, newValue) -> {
				if(newValue.getRig().getType().equals(Constants.LIFTING_RIG)) {
					liftingRigTypeLabel.setVisible(true);
					liftingRigTypeComboBox.setVisible(true);
				} else {
					liftingRigTypeLabel.setVisible(false);
					liftingRigTypeComboBox.setVisible(false);
				}
			});
			firmNameColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getFirmName()));
			rigColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getRig().getRigName()));
			liftingRigTypeComboBox.setItems(FXCollections.observableArrayList(
					"Transpalet hidraulic",
					"Stivuitor manual",
					"Palan manual (Qmax <= 1t)",
					"Electropalan (Qmax <= 1t)"));
			liftingRigTypeComboBox.getSelectionModel().select(0);
			liftingRigTypeLabel.setVisible(false);
			liftingRigTypeComboBox.setVisible(false);
			Utils.setDisabledDaysForDatePicker(reportCreationDate);
			Utils.setDisplayFormatForDatePicker(reportCreationDate);
		} catch (Exception e) {
			DBServices.saveErrorLogEntry(e);
		}
	}
	
	private List<RigWithDetails> removeLargePressureRigs(List<RigWithDetails> rigs) {
		List<RigWithDetails> tmp = new ArrayList<>();
		for(RigWithDetails index : rigs) {
			if(index.getRig().getType().equals(Constants.PRESSURE_RIG)) {
				int product = Integer.parseInt(index.getRig().getPressure().getValue()) * Integer.parseInt(index.getRig().getVolume().getValue());
				if(product <= 200) {
					tmp.add(index);
				}
			} else {
				tmp.add(index);
			}
		}
		return tmp;
	}
	
	public void handleGenerateTechnicalRigEvaluationReport() {
		try {
			String bodyMessage;
			if(DBServices.getBackupPath().equals("")) {
				bodyMessage = "Fișierul se poate găsi in docs\\verificare tehnică utilaje.\nDoriți să vizualizați fișierul generat?";
			} else {
				bodyMessage = "Fișierul se poate găsi in docs\\verificare tehnică utilaje. Sau în " + DBServices.getBackupPath() + "\\verificare tehnică utilaje\nDoriți să vizualizați fișierul generat?";
			}
			File file = Generator.generateTechnicalRigEvaluationReport(
					rigTable.getSelectionModel().getSelectedItem(),
					reportNumberField.getText(),
					java.sql.Date.valueOf(reportCreationDate.getValue()),
					rigCodeField.getText(),
					liftingRigTypeComboBox.getSelectionModel().getSelectedItem(),
					firstTimeCheckBox.selectedProperty().get());
			
			Optional<ButtonType> choice = Utils.alert(AlertType.INFORMATION, "Generare Proces Verbal", "Generararea s-a terminat cu succes", bodyMessage, true);
			if(choice.get().getButtonData() == ButtonType.YES.getButtonData()) {
				Desktop.getDesktop().open(file);
			}
		} catch (Exception e) {
			DBServices.saveErrorLogEntry(e);
		}
	}

}
