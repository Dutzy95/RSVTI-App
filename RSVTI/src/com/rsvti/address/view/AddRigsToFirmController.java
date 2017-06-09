package com.rsvti.address.view;

import java.util.List;
import java.util.ArrayList;

import com.rsvti.address.JavaFxMain;
import com.rsvti.database.entities.Employee;
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
import javafx.scene.control.DatePicker;
import javafx.scene.control.SelectionMode;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableColumn.CellEditEvent;
import javafx.scene.control.TableRow;
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
	private TableView<ParameterDetails> chosenParametersTable;
	@FXML
	private TableColumn<ParameterDetails,String> chosenParameterNameColumn;
	@FXML
	private TableColumn<ParameterDetails,String> chosenParameterValueColumn;
	@FXML
	private TableColumn<ParameterDetails,String> chosenParameterMeasuringUnitColumn;
	
	@FXML
	private TextField rigNameField;
	@FXML
	private DatePicker dueDateField;
	
	@FXML
	private TableView<Employee> employeeTable;
	@FXML
	private TableColumn<Employee,String> employeeLastNameColumn;
	@FXML
	private TableColumn<Employee,String> employeeFirstNameColumn;
	
	private List<Employee> employeeList = new ArrayList<Employee>();
	
	
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
		chosenParameterNameColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getName()));
		chosenParameterValueColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getValue()));
		chosenParameterValueColumn.setOnEditCommit(new EventHandler<CellEditEvent<ParameterDetails, String>>() {
	        @Override
	        public void handle(CellEditEvent<ParameterDetails, String> t) {
	            ((ParameterDetails) t.getTableView().getItems().get(
	                t.getTablePosition().getRow())
	                ).setValue(t.getNewValue());
	        }
	    });
		chosenParameterMeasuringUnitColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getMeasuringUnit()));
		chosenParameterMeasuringUnitColumn.setOnEditCommit(new EventHandler<CellEditEvent<ParameterDetails, String>>() {
	        @Override
	        public void handle(CellEditEvent<ParameterDetails, String> t) {
	            ((ParameterDetails) t.getTableView().getItems().get(
	                t.getTablePosition().getRow())
	                ).setMeasuringUnit(t.getNewValue());
	        }
	    });
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
		
		employeeTable.setItems(FXCollections.observableArrayList(employeeList));
		employeeLastNameColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getLastName()));
		employeeFirstNameColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getFirstName()));
		employeeTable.setRowFactory( tv -> {
		    TableRow<Employee> row = new TableRow<>();
		    row.setOnMouseClicked(event -> {
		        if (event.getClickCount() == 2 && (! row.isEmpty()) ) {
		            Employee rowData = row.getItem();
		            javaFxMain.showAddEmployeesToRig(rowData);
		        }
		    });
		    return row ;
		});
	}
	
	@FXML
	private void handleArrowRight() {
		ObservableList<String> selectedItems = importedParameterTable.getSelectionModel().getSelectedItems();
		if(selectedItems != null) {
			for(int i = 0; i < selectedItems.size(); i++) {
				chosenParametersTable.getItems().add(new ParameterDetails(selectedItems.get(i),"",""));
				importedParameterTable.getItems().remove(selectedItems.get(i));
			}
		}
	}
	
	@FXML
	private void handleArrowLeft() {
		ObservableList<ParameterDetails> selectedItems = chosenParametersTable.getSelectionModel().getSelectedItems();
		if(selectedItems != null) {
			for(ParameterDetails index : selectedItems) {
				importedParameterTable.getItems().add(index.getName());
				chosenParametersTable.getItems().remove(index);
			}
		}
	}
	
	public void updateEmployeeList(Employee employee) {
		employeeList.add(employee);
		employeeTable.setItems(FXCollections.observableArrayList(employeeList));
	}
	
	@FXML
	private void handleAddEmployee() {
		javaFxMain.showAddEmployeesToRig(null);
	}
	
	@FXML
	private void handleSave() {
		System.out.println(chosenParametersTable.getItems());
	}
	
	public void setJavaFxMain(JavaFxMain javaFxMain) {
		this.javaFxMain = javaFxMain;
	}
}
