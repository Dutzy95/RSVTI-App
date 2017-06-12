package com.rsvti.address.view;

import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import com.rsvti.address.JavaFxMain;
import com.rsvti.database.entities.Employee;
import com.rsvti.database.entities.ParameterDetails;
import com.rsvti.database.entities.Rig;
import com.rsvti.database.services.DBServices;
import com.rsvti.main.Constants;
import com.rsvti.main.Utils;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
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
	private DatePicker revisionDate;
	@FXML
	private ComboBox<String> authorizationExtension;
	@FXML
	private Label dueDateLabel;
	
	@FXML
	private TableView<Employee> employeeTable;
	@FXML
	private TableColumn<Employee,String> employeeLastNameColumn;
	@FXML
	private TableColumn<Employee,String> employeeFirstNameColumn;
	
	private List<Employee> employeeList = new ArrayList<Employee>();
	
	private SimpleDateFormat simpleDateFormat = new SimpleDateFormat(Constants.DATE_FORMAT);
	
	private boolean isUpdate = false;
	private Rig rigToUpdate;
	
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
				filterSelectedParameters(selectedItem);
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
		            javaFxMain.showAddEmployeesToRig(rowData, true);
		        }
		    });
		    return row ;
		});
		
		authorizationExtension.setItems(FXCollections.observableArrayList("1 an", "2 ani", "3 ani", "4 ani", "5 ani"));
		authorizationExtension.getSelectionModel().select(0);
		authorizationExtension.valueProperty().addListener(new ChangeListener<String>() {
			@Override 
			public void changed(ObservableValue<? extends String> ov, String t, String t1) {
				int selectedExtensionValue = authorizationExtension.getSelectionModel().getSelectedItem().charAt(0) - '0';
				if(revisionDate.getValue() != null) {
					dueDateLabel.setText(simpleDateFormat.format(Rig.getDueDate(java.sql.Date.valueOf(revisionDate.getValue()), selectedExtensionValue)));
				}
			}
		});
		
		revisionDate.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent e) {
				int selectedExtensionValue = authorizationExtension.getSelectionModel().getSelectedItem().charAt(0) - '0';
				dueDateLabel.setText(simpleDateFormat.format(Rig.getDueDate(java.sql.Date.valueOf(revisionDate.getValue()), selectedExtensionValue)));
			}
		});
		
		Utils.setDisplayFormatForDatePicker(revisionDate);
        Utils.setDisabledDaysForDatePicker(revisionDate);
        revisionDate.setValue(LocalDate.now());
		
		dueDateLabel.setText(simpleDateFormat.format(Rig.getDueDate(java.sql.Date.valueOf(LocalDate.now()), 1)));
	}
	
	private void filterSelectedParameters(String selectedItem) {
		List<String> parameters = DBServices.getRigParametersByType(selectedItem);
		List<ParameterDetails> chosenParameters = chosenParametersTable.getItems();
		for(ParameterDetails index : chosenParameters) {
			if(parameters.contains(index.getName())) {
				parameters.remove(index.getName());
			}
		}
		importedParameterTable.setItems(FXCollections.observableArrayList(parameters));
	}
	
	@FXML
	private void handleArrowRight() {
		ObservableList<String> selectedItems = importedParameterTable.getSelectionModel().getSelectedItems();
		if(selectedItems != null) {
			for(String index : selectedItems) {
				chosenParametersTable.getItems().add(new ParameterDetails(index,"",""));
				importedParameterTable.getItems().remove(index);
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
	
	public void updateEmployeeList(Employee employee, boolean isUpdate, Employee updatedEmployee) {
		if(isUpdate) {
			for(int i = 0; i < employeeList.size(); i++) {
				if(employeeList.get(i).equals(employee)) {
					employeeList.set(i, updatedEmployee);
				}
			}
		} else {
			employeeList.add(employee);
		}
		employeeTable.setItems(FXCollections.observableArrayList(employeeList));
	}
	
	@FXML
	private void handleAddEmployee() {
		javaFxMain.showAddEmployeesToRig(null, false);
	}
	
	@FXML
	private void handleSave() {
		if(isUpdate) {
			Rig newRig = new Rig(rigNameField.getText(), 
								 chosenParametersTable.getItems(), 
								 java.sql.Date.valueOf(revisionDate.getValue()), 
							 	 employeeTable.getItems(), 
								 rigType.getValue());
			newRig.setAuthorizationExtension(authorizationExtension.getSelectionModel().getSelectedItem().charAt(0) - '0');
			javaFxMain.getAddFirmController().updateRigTable(rigToUpdate, true, newRig);
		} else {
			Rig newRig = new Rig(rigNameField.getText(), 
								 chosenParametersTable.getItems(), 
								 java.sql.Date.valueOf(revisionDate.getValue()), 
							 	 employeeTable.getItems(), 
								 rigType.getValue());
			newRig.setAuthorizationExtension(authorizationExtension.getSelectionModel().getSelectedItem().charAt(0) - '0');
			javaFxMain.getAddFirmController().updateRigTable(newRig, false, null);
		}
		javaFxMain.getAddRigsToFirmStage().close();
	}
	
	public void showRigDetails(Rig rig) {
		rigToUpdate = rig;
		
		rigNameField.setText(rig.getRigName());
		dueDateLabel.setText(new java.sql.Date(rig.getRevisionDate().getTime()).toLocalDate().toString());
		rigType.setValue(rig.getType());
		chosenParametersTable.setItems(FXCollections.observableArrayList(rig.getParameters()));
		employeeTable.setItems(FXCollections.observableArrayList(rig.getEmployees()));
		filterSelectedParameters("de ridicat");
		revisionDate.setValue(new java.sql.Date(rig.getRevisionDate().getTime()).toLocalDate());
		authorizationExtension.getSelectionModel().select(rig.getAuthorizationExtension() - 1);
		dueDateLabel.setText(simpleDateFormat.format(Rig.getDueDate(rig.getRevisionDate(), rig.getAuthorizationExtension())));
	}
	
	public void setIsUpdate(boolean isUpdate) {
		this.isUpdate = isUpdate;
	}
	
	public void setJavaFxMain(JavaFxMain javaFxMain) {
		this.javaFxMain = javaFxMain;
	}
}
