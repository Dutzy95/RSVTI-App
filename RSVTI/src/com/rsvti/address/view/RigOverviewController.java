package com.rsvti.address.view;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.Map;

import com.rsvti.address.JavaFxMain;
import com.rsvti.database.entities.ParameterDetails;
import com.rsvti.database.entities.Rig;

import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.stage.Stage;
import javafx.util.Callback;

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
	private Button employeeOverviewButton;
	
	private JavaFxMain javaFxMain;
	
	private Stage stage;
	
	@FXML
	private List<Rig> rigList;
	
	public RigOverviewController() {
		
	}
	
	private void initialize() {
	}
	
	private void showRigDetails(Rig rig) {
		employeeOverviewButton.setDisable(false);
		if(rig != null) {
			rigNameLabel.setText(rig.getRigName());
			SimpleDateFormat format = new SimpleDateFormat("dd-MM-yyyy");
			dueDateLabel.setText(format.format(rig.getRevisionDate()));
			rigParameterTable.setItems(FXCollections.observableArrayList(rig.getParameters()));
			parameterNameColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getName()));
			parameterValueColumn.setCellValueFactory(cellData -> new SimpleDoubleProperty(Double.parseDouble(cellData.getValue().getValue())).asObject());
			parameterMeasuringUnit.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getMeasuringUnit()));
		} else {
			rigNameLabel.setText("Instalatie");
			dueDateLabel.setText("zz-ll-aaaa");
		}
	}
	
	public void setRigList(List<Rig> rigList) {
		this.rigList = rigList;
		rigTable.setItems(FXCollections.observableArrayList(rigList));
		rigNameColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getRigName()));
		
		showRigDetails(null);
		
		employeeOverviewButton.setDisable(true);
		
		rigTable.getSelectionModel().selectedItemProperty().addListener((observable, oldvalue, newValue) -> showRigDetails(newValue));
	}
	
	@FXML
	private void handleOpenEmployeeOverview() {
		int selectedIndex = rigTable.getSelectionModel().getSelectedIndex();
		if(selectedIndex >= 0 ) {
			javaFxMain.showEmployeeOverview(rigTable.getItems().get(selectedIndex).getEmployees());
		}
	}
	
	public void setJavaFxMain(JavaFxMain javaFxMain) {
		this.javaFxMain = javaFxMain;
	}
}
