package com.rsvti.address.controller;

import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.List;

import com.rsvti.address.JavaFxMain;
import com.rsvti.common.Constants;
import com.rsvti.common.Utils;
import com.rsvti.database.entities.ParameterDetails;
import com.rsvti.database.entities.Rig;
import com.rsvti.database.entities.RigParameter;
import com.rsvti.database.entities.Valve;
import com.rsvti.database.services.DBServices;

import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.SelectionMode;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.scene.layout.HBox;

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
	private TextField productionYearField;
	@FXML
	private TextField productionNumberField;
	@FXML
	private TextField iscirRegistrationNumberField;
	@FXML
	private TextField valveRegistrationNumberField;
	@FXML
	private DatePicker valveRevisionDate;
	@FXML
	private Label valveDueDateLabel;
	@FXML
	private CheckBox valveNoExtensionCheckbox;
	@FXML
	private Label valveTitleLabel;
	@FXML
	private HBox valveHbox;
	
	private SimpleDateFormat simpleDateFormat = new SimpleDateFormat(DBServices.getDatePattern());
	
	private boolean isUpdate = false;
	private boolean isDueDateUpdate = false;
	private String firmId;
	private Rig rigToUpdate;
	
	private int selectedExtensionValue = 0;
	
	@FXML
	private JavaFxMain javaFxMain;
	
	@FXML
	public void initialize() {
		try {
			rigType.setItems(FXCollections.observableArrayList(Constants.LIFTING_RIG, Constants.PRESSURE_RIG));
			rigType.getSelectionModel().select(0);
			importedParameterTable.setItems(FXCollections.observableArrayList(DBServices.getRigParametersByType(Constants.LIFTING_RIG)));
			importedParameterTable.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
			importedParameterTable.setPlaceholder(new Label(Constants.TABLE_PLACEHOLDER_MESSAGE));
			importedParameterColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getName()));
			chosenParameterNameColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getName()));
			chosenParameterValueColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getValue()));
			chosenParameterValueColumn.setOnEditCommit(t -> {
	            ((ParameterDetails) t.getTableView().getItems().get(
	                t.getTablePosition().getRow())
	                ).setValue(t.getNewValue());
		    });
			chosenParameterMeasuringUnitColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getMeasuringUnit()));
			chosenParameterValueColumn.setCellFactory(TextFieldTableCell.forTableColumn());
			chosenParametersTable.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
			chosenParametersTable.setEditable(true);
			chosenParametersTable.setPlaceholder(new Label(Constants.TABLE_PLACEHOLDER_MESSAGE));
			rigType.valueProperty().addListener((ov, t, t1) -> {
				try {
					String selectedItem = rigType.getSelectionModel().getSelectedItem();
					filterSelectedParameters(selectedItem);
					importedParameterTable.refresh();
					if(selectedItem.equals(Constants.PRESSURE_RIG)) {
						valveTitleLabel.setVisible(true);
						valveHbox.setVisible(true);
						chosenParametersTable.setItems(FXCollections.observableArrayList(
								new ParameterDetails(Constants.RIG_PARAMETER_PRESSURE, "", "bar"),
								new ParameterDetails(Constants.RIG_PARAMETER_VOLUME, "", "litri")));
					} else {
						valveTitleLabel.setVisible(false);
						valveHbox.setVisible(false);
						chosenParametersTable.setItems(FXCollections.emptyObservableList());
					}
				} catch (Exception e) {
					DBServices.saveErrorLogEntry(e);
				}
			});
			
			authorizationExtension.setItems(FXCollections.observableArrayList("Nu extinde", "1 an", "2 ani", "3 ani", "4 ani", "5 ani"));
			authorizationExtension.getSelectionModel().select(0);
			authorizationExtension.valueProperty().addListener((ov, t, t1) -> {
				try {
					if(!authorizationExtension.getValue().equals("Nu extinde")) {
						selectedExtensionValue = authorizationExtension.getSelectionModel().getSelectedItem().charAt(0) - '0';
					} else {
						selectedExtensionValue = 0;
					}
					if(revisionDate.getValue() != null) {
						dueDateLabel.setText(simpleDateFormat.format(Utils.getCalculatedDueDate(java.sql.Date.valueOf(revisionDate.getValue()), selectedExtensionValue)));
					}
				} catch (Exception e) {
					DBServices.saveErrorLogEntry(e);
				}
			});
			
			revisionDate.setOnAction( e -> {
				try {
					if(!authorizationExtension.getValue().equals("Nu extinde")) {
						selectedExtensionValue = authorizationExtension.getSelectionModel().getSelectedItem().charAt(0) - '0';
					} else {
						selectedExtensionValue = 0;
					}
					dueDateLabel.setText(simpleDateFormat.format(Utils.getCalculatedDueDate(java.sql.Date.valueOf(revisionDate.getValue()), selectedExtensionValue)));
				} catch (Exception err) {
					DBServices.saveErrorLogEntry(err);
				}
			});
			
			Utils.setDisplayFormatForDatePicker(revisionDate);
	        Utils.setDisabledDaysForDatePicker(revisionDate);
	        revisionDate.setValue(LocalDate.now());
			
			dueDateLabel.setText(simpleDateFormat.format(Utils.getCalculatedDueDate(java.sql.Date.valueOf(LocalDate.now()), selectedExtensionValue)));
			
			valveTitleLabel.setVisible(false);
			valveHbox.setVisible(false);
			Utils.setDisabledDaysForDatePicker(valveRevisionDate);
			Utils.setDisplayFormatForDatePicker(valveRevisionDate);
			valveRevisionDate.setValue(LocalDate.now());
			valveDueDateLabel.setText(simpleDateFormat.format(Utils.getCalculatedDueDate(java.sql.Date.valueOf(LocalDate.now()), 1)));
			valveRevisionDate.setOnAction(e -> {
				if(valveNoExtensionCheckbox.selectedProperty().get()) {
					valveDueDateLabel.setText(simpleDateFormat.format(Utils.getCalculatedDueDate(java.sql.Date.valueOf(valveRevisionDate.getValue()), 0)));
				} else {
					valveDueDateLabel.setText(simpleDateFormat.format(Utils.getCalculatedDueDate(java.sql.Date.valueOf(valveRevisionDate.getValue()), 1)));
				}
			});
			valveNoExtensionCheckbox.selectedProperty().addListener((observable, oldValue, newValue) -> {
				if(newValue) {
					valveDueDateLabel.setText(simpleDateFormat.format(Utils.getCalculatedDueDate(java.sql.Date.valueOf(valveRevisionDate.getValue()), 0)));
				} else {
					valveDueDateLabel.setText(simpleDateFormat.format(Utils.getCalculatedDueDate(java.sql.Date.valueOf(valveRevisionDate.getValue()), 1)));
				}
			});
		} catch (Exception e) {
			DBServices.saveErrorLogEntry(e);
		}
	}
	
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
		try {
			List<RigParameter> selectedItems = importedParameterTable.getSelectionModel().getSelectedItems();
			if(selectedItems != null) {
				for(RigParameter index : selectedItems) {
					chosenParametersTable.getItems().add(new ParameterDetails(index.getName(),"",index.getMeasuringUnit()));
				}
				importedParameterTable.getItems().removeAll(selectedItems);
			}
		} catch (Exception e) {
			DBServices.saveErrorLogEntry(e);
		}
	}
	
	@FXML
	private void handleArrowLeft() {
		try {
			List<ParameterDetails> selectedItems = chosenParametersTable.getSelectionModel().getSelectedItems();
			if(selectedItems != null) {
				for(ParameterDetails index : selectedItems) {
					if(!index.getName().equals(Constants.RIG_PARAMETER_PRESSURE) && !index.getName().equals(Constants.RIG_PARAMETER_VOLUME)) {
						importedParameterTable.getItems().add(new RigParameter(rigType.getValue(), index.getName(),index.getMeasuringUnit()));
						chosenParametersTable.getItems().remove(index);
					}
				}
			}
		} catch (Exception e) {
			DBServices.saveErrorLogEntry(e);
		}
	}
	
	@FXML
	private void handleSave() {
		try {
			Rig newRig;
			if(isDueDateUpdate) {
				if(rigType.getValue().equals(Constants.LIFTING_RIG)) {
					newRig = new Rig(rigNameField.getText(), 
							 chosenParametersTable.getItems(), 
							 java.sql.Date.valueOf(revisionDate.getValue()),  
							 rigType.getValue(),
							 productionNumberField.getText(),
							 Integer.parseInt(productionYearField.getText()),
							 iscirRegistrationNumberField.getText());
				} else {
					newRig = new Rig(rigNameField.getText(), 
							 chosenParametersTable.getItems(), 
							 java.sql.Date.valueOf(revisionDate.getValue()),  
							 rigType.getValue(),
							 productionNumberField.getText(),
							 Integer.parseInt(productionYearField.getText()),
							 iscirRegistrationNumberField.getText(),
							 new Valve(
									 new SimpleDateFormat(DBServices.getDatePattern()).parse(valveDueDateLabel.getText()),
									 valveRegistrationNumberField.getText(),
									 valveNoExtensionCheckbox.selectedProperty().get()));
				}
				newRig.setAuthorizationExtension(selectedExtensionValue);
				if(firmId != null) {
					javaFxMain.getDueDateOverviewController().updateRigTable(firmId, rigToUpdate, newRig);
				}
			} else {
				if(isUpdate) {
					if(rigType.getValue().equals(Constants.LIFTING_RIG)) {
						newRig = new Rig(rigNameField.getText(), 
											 chosenParametersTable.getItems(), 
											 java.sql.Date.valueOf(revisionDate.getValue()), 
											 rigType.getValue(),
											 productionNumberField.getText(),
											 Integer.parseInt(productionYearField.getText()),
											 iscirRegistrationNumberField.getText());
					} else {
						newRig = new Rig(rigNameField.getText(), 
								 chosenParametersTable.getItems(), 
								 java.sql.Date.valueOf(revisionDate.getValue()), 
								 rigType.getValue(),
								 productionNumberField.getText(),
								 Integer.parseInt(productionYearField.getText()),
								 iscirRegistrationNumberField.getText(),
								 new Valve(
										 new SimpleDateFormat(DBServices.getDatePattern()).parse(valveDueDateLabel.getText()),
										 valveRegistrationNumberField.getText(),
										 valveNoExtensionCheckbox.selectedProperty().get()));
					}
					newRig.setAuthorizationExtension(selectedExtensionValue);
					javaFxMain.getAddFirmController().updateRigTable(rigToUpdate, true, newRig);
				} else {
					if(rigType.getValue().equals(Constants.LIFTING_RIG)) {
						newRig = new Rig(rigNameField.getText(), 
											 chosenParametersTable.getItems(), 
											 java.sql.Date.valueOf(revisionDate.getValue()),  
											 rigType.getValue(),
											 productionNumberField.getText(),
											 Integer.parseInt(productionYearField.getText()),
											 iscirRegistrationNumberField.getText());
					} else {
						newRig = new Rig(rigNameField.getText(), 
								 chosenParametersTable.getItems(), 
								 java.sql.Date.valueOf(revisionDate.getValue()),  
								 rigType.getValue(),
								 productionNumberField.getText(),
								 Integer.parseInt(productionYearField.getText()),
								 iscirRegistrationNumberField.getText(),
								 new Valve(
										 new SimpleDateFormat(DBServices.getDatePattern()).parse(valveDueDateLabel.getText()),
										 valveRegistrationNumberField.getText(),
										 valveNoExtensionCheckbox.selectedProperty().get()));
					}
					newRig.setAuthorizationExtension(selectedExtensionValue);
					javaFxMain.getAddFirmController().updateRigTable(newRig, false, null);
				}
			}
			javaFxMain.getAddRigsToFirmStage().close();
			isDueDateUpdate = false;
			isUpdate = false;
		} catch (Exception e) {
			DBServices.saveErrorLogEntry(e);
		}
	}
	
	public void showRigDetails(Rig rig) {
		rigToUpdate = rig;
		
		rigNameField.setText(rig.getRigName());
		dueDateLabel.setText(new java.sql.Date(rig.getRevisionDate().getTime()).toLocalDate().toString());
		rigType.setValue(rig.getType());
		chosenParametersTable.setItems(FXCollections.observableArrayList(rig.getParameters()));
		filterSelectedParameters(Constants.LIFTING_RIG);
		revisionDate.setValue(new java.sql.Date(rig.getRevisionDate().getTime()).toLocalDate());
		authorizationExtension.getSelectionModel().select(rig.getAuthorizationExtension());
		dueDateLabel.setText(simpleDateFormat.format(rig.getDueDate()));
		productionYearField.setText(rig.getProductionYear() + "");
		productionNumberField.setText(rig.getProductionNumber());
		iscirRegistrationNumberField.setText(rig.getIscirRegistrationNumber());
		if(rig.getType().equals(Constants.PRESSURE_RIG)) {
			valveRegistrationNumberField.setText(rig.getValve().getRegistrationNumber());
			valveRevisionDate.setValue(new java.sql.Date(rig.getValve().getRevisionDate().getTime()).toLocalDate());
			valveDueDateLabel.setText(simpleDateFormat.format(rig.getValve().getDueDate()));
			valveNoExtensionCheckbox.setSelected(rig.getValve().isNotExtended());
		}
	}
	
	public void setIsUpdate(boolean isUpdate) {
		this.isUpdate = isUpdate;
	}
	
	public void setIsDueDateUpdate(boolean isDueDateUpdate) {
		this.isDueDateUpdate = isDueDateUpdate;
	}

	public void setFirmId(String firmId) {
		this.firmId = firmId;
	}
	public void setJavaFxMain(JavaFxMain javaFxMain) {
		this.javaFxMain = javaFxMain;
	}
}
