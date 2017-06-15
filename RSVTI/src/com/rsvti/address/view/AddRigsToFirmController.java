package com.rsvti.address.view;

import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import com.rsvti.address.JavaFxMain;
import com.rsvti.database.entities.Employee;
import com.rsvti.database.entities.ParameterDetails;
import com.rsvti.database.entities.Rig;
import com.rsvti.database.entities.RigParameter;
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
	private TableView<RigParameter> importedParameterTable;
	@FXML
	private TableColumn<RigParameter,String> importedParameterColumn;
	
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
	private boolean isDueDateUpdate = false;
	private String firmName;
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
		importedParameterTable.setPlaceholder(new Label(Constants.TABLE_PLACEHOLDER_MESSAGE));
		importedParameterColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getName()));
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
		chosenParameterValueColumn.setCellFactory(TextFieldTableCell.forTableColumn());
		chosenParametersTable.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
		chosenParametersTable.setEditable(true);
		chosenParametersTable.setPlaceholder(new Label(Constants.TABLE_PLACEHOLDER_MESSAGE));
		rigType.valueProperty().addListener(new ChangeListener<String>() {
			@Override 
			public void changed(ObservableValue<? extends String> ov, String t, String t1) {
				String selectedItem = rigType.getSelectionModel().getSelectedItem();
				filterSelectedParameters(selectedItem);
				importedParameterTable.refresh();
			}
		});
		
		employeeTable.setItems(FXCollections.observableArrayList(employeeList));
		employeeTable.setPlaceholder(new Label(Constants.TABLE_PLACEHOLDER_MESSAGE));
		employeeLastNameColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getLastName()));
		employeeFirstNameColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getFirstName()));
		employeeTable.setRowFactory( tv -> {
		    TableRow<Employee> row = new TableRow<>();
		    row.setOnMouseClicked(event -> {
		        if (event.getClickCount() == 2 && (! row.isEmpty()) ) {
		            Employee rowData = row.getItem();
		            javaFxMain.showAddUpdateEmployeesToRig(rowData, true, false, "Editează personal");
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
	
	//TODO: 
	private void filterSelectedParameters(String selectedItem) {
		List<RigParameter> parameters = DBServices.getRigParametersByType(selectedItem);
		List<ParameterDetails> chosenParameters = chosenParametersTable.getItems();
		for(ParameterDetails index : chosenParameters) {
			RigParameter rigParameter = new RigParameter(rigType.getValue(), index.getName(), index.getMeasuringUnit()); 
			if(parameters.contains(rigParameter)) {
				parameters.remove(rigParameter);
			}
		}
		importedParameterTable.setItems(FXCollections.observableArrayList(parameters));
	}
	
	@FXML
	private void handleArrowRight() {
		List<RigParameter> selectedItems = importedParameterTable.getSelectionModel().getSelectedItems();
		if(selectedItems != null) {
			for(RigParameter index : selectedItems) {
				chosenParametersTable.getItems().add(new ParameterDetails(index.getName(),"",index.getMeasuringUnit()));
			}
			importedParameterTable.getItems().removeAll(selectedItems);
		}
	}
	
	@FXML
	private void handleArrowLeft() {
		List<ParameterDetails> selectedItems = chosenParametersTable.getSelectionModel().getSelectedItems();
		if(selectedItems != null) {
			for(ParameterDetails index : selectedItems) {
				importedParameterTable.getItems().add(new RigParameter(rigType.getValue(), index.getName(),index.getMeasuringUnit()));
			}
			chosenParametersTable.getItems().removeAll(selectedItems);
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
		javaFxMain.showAddUpdateEmployeesToRig(null, false, false,"Adaugă personal");
	}
	
	@FXML
	private void handleSave() {
		if(isDueDateUpdate) {
			Rig newRig = new Rig(rigNameField.getText(), 
					 chosenParametersTable.getItems(), 
					 java.sql.Date.valueOf(revisionDate.getValue()), 
				 	 employeeTable.getItems(), 
					 rigType.getValue());
			newRig.setAuthorizationExtension(authorizationExtension.getSelectionModel().getSelectedItem().charAt(0) - '0');
			javaFxMain.getDueDateOverviewController().updateRigTable(firmName, rigToUpdate, newRig);
		} else {
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
		}
		javaFxMain.getAddRigsToFirmStage().close();
		isDueDateUpdate = false;
		isUpdate = false;
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
		dueDateLabel.setText(simpleDateFormat.format(rig.getDueDate()));
	}
	
	public void setIsUpdate(boolean isUpdate) {
		this.isUpdate = isUpdate;
	}
	
	public void setIsDueDateUpdate(boolean isDueDateUpdate) {
		this.isDueDateUpdate = isDueDateUpdate;
	}

	public void setFirmName(String firmName) {
		this.firmName = firmName;
	}
	public void setJavaFxMain(JavaFxMain javaFxMain) {
		this.javaFxMain = javaFxMain;
	}
}
