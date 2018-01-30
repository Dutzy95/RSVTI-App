package com.rsvti.address.controller;

import java.awt.Desktop;
import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import com.rsvti.address.JavaFxMain;
import com.rsvti.common.Constants;
import com.rsvti.common.Utils;
import com.rsvti.database.entities.Firm;
import com.rsvti.database.services.DBServices;
import com.rsvti.generator.Generator;

import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.SelectionMode;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;

public class GenerateTableController {
	@FXML
	private JavaFxMain javaFxMain;
	
	@FXML
	private TableView<Firm> firmTable;
	@FXML
	private TableColumn<Firm,String> firmNameColumn;
	
	@FXML
	private Button generateButton;
	
	@FXML
	private void initialize() {
		try {
			firmTable.setItems(FXCollections.observableArrayList(DBServices.getAllFirms()));
			firmNameColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getFirmName()));
			generateButton.setDisable(true);
			firmTable.getSelectionModel().selectedItemProperty().addListener(event -> {generateButton.setDisable(false);});
			firmTable.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
			firmTable.setPlaceholder(new Label(Constants.TABLE_PLACEHOLDER_MESSAGE));
		} catch (Exception e) {
			DBServices.saveErrorLogEntry(e);
		}
	}
	
	@FXML
	private void handleGenerate() {
		try {
			List<Firm> selection = firmTable.getSelectionModel().getSelectedItems();
			List<File> files = new ArrayList<File>();
			for(Firm index : selection) {
				files.add(Generator.generateExcelTable(index));
			}
			
			String bodyMessage;
			if(DBServices.getBackupPath().equals("")) {
				bodyMessage = "Fișierele se pot găsi in docs\\tabele utilaje.\nDoriți să vizualizați fișierele generate?";
			} else {
				bodyMessage = "Fișierele se pot găsi in docs\\tabele utilaje. Sau în " + DBServices.getBackupPath() + "\\tabele utilaje\nDoriți să vizualizați fișierele generate?";
			}
			
			Optional<ButtonType> choice = Utils.alert(AlertType.INFORMATION, "Generare Tabel", "Generararea s-a terminat cu succes", bodyMessage);
			if(choice.get().getButtonData() == ButtonType.YES.getButtonData()) {
				for(File file : files) {
					try {
						Desktop.getDesktop().open(file);
					} catch(Exception e) {
						e.printStackTrace();
					}
				}
			}
		} catch (Exception e) {
			DBServices.saveErrorLogEntry(e);
		}
	}
	
	public void setJavaFxMain(JavaFxMain javaFxMain) {
		this.javaFxMain = javaFxMain;
	}
}
