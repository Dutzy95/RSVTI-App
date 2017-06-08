package com.rsvti.address.view;

import java.util.HashMap;
import java.util.Map;

import com.rsvti.address.JavaFxMain;
import com.rsvti.database.entities.ParameterDetails;
import com.rsvti.database.services.DBServices;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.SelectionMode;
import javafx.scene.control.SelectionModel;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableColumn.CellEditEvent;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.TextFieldTableCell;

public class AddRigsToFirmController {

	@FXML
	private ComboBox<String> rigType;
	
	@FXML
	private TableView<String> importedParameterTable;
	@FXML
	private TableColumn<String,String> importedParameterColumn;
	
	@FXML
	private TableView<String> chosenParametersTable;
	@FXML
	private TableColumn<String,String> chosenParameterNameColumn;
	@FXML
	private TableColumn<String,String> chosenParameterValueColumn;
	@FXML
	private TableColumn<String,String> chosenParameterMeasuringUnitColumn;
	
	@FXML
	private TextField rigNameField;
	@FXML
	private TextField dueDateField;
	
	
	@FXML
	private JavaFxMain javaFxMain;
	
	public AddRigsToFirmController() {
	}
	
	@FXML
	public void initialize() {
		rigType.setItems(FXCollections.observableArrayList("de ridicat", "sub presiune"));
		rigType.getSelectionModel().select(0);
		importedParameterTable.setItems(FXCollections.observableArrayList(DBServices.getRigParametersByType("de ridicat")));
		importedParameterTable.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
		importedParameterColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue()));
		chosenParameterNameColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue()));
		chosenParameterValueColumn.setCellFactory(TextFieldTableCell.forTableColumn());
		chosenParameterMeasuringUnitColumn.setCellFactory(TextFieldTableCell.forTableColumn());
		chosenParametersTable.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
		chosenParametersTable.setEditable(true);
		rigType.valueProperty().addListener(new ChangeListener<String>() {
			@Override 
			public void changed(ObservableValue<? extends String> ov, String t, String t1) {
				String selectedItem = rigType.getSelectionModel().getSelectedItem();
				importedParameterTable.setItems(FXCollections.observableArrayList(DBServices.getRigParametersByType(selectedItem)));
				importedParameterTable.refresh();
			}
		});
	}
	
	@FXML
	private void handleArrowRight() {
		ObservableList<String> selectedItems = importedParameterTable.getSelectionModel().getSelectedItems();
		if(selectedItems != null) {
			for(int i = 0; i < selectedItems.size(); i++) {
				chosenParametersTable.getItems().add(selectedItems.get(i));
				importedParameterTable.getItems().remove(selectedItems.get(i));
			}
		}
	}
	
	@FXML
	private void handleArrowLeft() {
		ObservableList<String> selectedItems = chosenParametersTable.getSelectionModel().getSelectedItems();
		if(selectedItems != null) {
			for(String index : selectedItems) {
				importedParameterTable.getItems().add(index);
				chosenParametersTable.getItems().remove(index);
			}
		}
	}
	
	private Map<String,ParameterDetails> getParametersFromTable() {
		Map<String,ParameterDetails> parameters = new HashMap<String,ParameterDetails>();
		
		return parameters;
	}
	
	public void setJavaFxMain(JavaFxMain javaFxMain) {
		this.javaFxMain = javaFxMain;
	}
}
