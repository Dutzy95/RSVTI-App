package com.rsvti.address.view;

import java.util.List;

import com.rsvti.address.JavaFxMain;
import com.rsvti.database.entities.Rig;

import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.stage.Stage;

public class RigOverviewController {

	@FXML
	private TableView<Rig> rigTable;
	@FXML
	private TableColumn<Rig,String> rigColumn;
	
	@FXML
	private Label rigTypeLabel;
	@FXML
	private Label dueDateLabel;
	@FXML
	private TableView<String> rigParameterTable;
	@FXML
	private TableColumn<Rig,String> parameterNameColumn;
	@FXML
	private TableColumn<Rig,String> parameterValueColumn;
	
	private JavaFxMain javaFxMain;
	
	private Stage stage;
	
	@FXML
	private List<Rig> rigList;
	
	public RigOverviewController() {
		
	}
	
	private void initialize() {
	}
	
	private void showRigDetails(Rig rig) {
		if(rig != null) {
			dueDateLabel.setText(rig.getDueDate().toString());
		}
	}
	
	public void setDialogStage(Stage stage) {
		this.stage = stage;
	}
	
	public void setRigList(List<Rig> rigList) {
		this.rigList = rigList;
		rigTable.setItems(FXCollections.observableArrayList(rigList));
		rigColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getType()));
		
		rigTable.getSelectionModel().selectedItemProperty().addListener((observable, oldvalue, newValue) -> showRigDetails(newValue));
	}
	
	public void setJavaFxMain(JavaFxMain javaFxMain) {
		this.javaFxMain = javaFxMain;
	}
}
