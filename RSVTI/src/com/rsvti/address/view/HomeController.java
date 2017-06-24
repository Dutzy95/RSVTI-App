package com.rsvti.address.view;

import java.io.File;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.Calendar;
import java.util.Date;

import com.rsvti.database.entities.EmployeeDueDateDetails;
import com.rsvti.database.entities.RigDueDateDetails;
import com.rsvti.database.services.DBServices;
import com.rsvti.main.Constants;
import com.rsvti.main.Utils;

import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableRow;
import javafx.scene.control.TableView;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class HomeController {

	@FXML
	private ImageView imageView;
	
	@FXML
	private Label titleLabel;
	
	@FXML
	private TableView<RigDueDateDetails> rigTable;
	@FXML
	private TableColumn<RigDueDateDetails,String> rigColumn;
	@FXML
	private TableColumn<RigDueDateDetails,String> firmNameColumn;
	@FXML
	private TableColumn<RigDueDateDetails,String> rigDueDateColumn;
	
	private SimpleDateFormat simpleDateFormat = new SimpleDateFormat(Constants.DATE_FORMAT);
	
	@FXML
	private TableView<EmployeeDueDateDetails> employeeTable;
	@FXML
	private TableColumn<EmployeeDueDateDetails,String> employeeFirstNameColumn;
	@FXML
	private TableColumn<EmployeeDueDateDetails,String> employeeLastNameColumn;
	@FXML
	private TableColumn<EmployeeDueDateDetails,String> employeeFirmNameColumn;
	@FXML
	private TableColumn<EmployeeDueDateDetails,String> employeeDueDateColumn;
	
	public HomeController() {
	}
	
	@FXML
	private void initialize() {
		imageView.setImage(new Image(new File(Utils.getJarFilePath() + "images\\RSVTI_with_text.png").toURI().toString()));
		
		Calendar extendedDate = Calendar.getInstance();
		if(!DBServices.getHomeDateDisplayInterval().equals("")) {
			extendedDate = Calendar.getInstance();
			extendedDate.add(Integer.parseInt(DBServices.getHomeDateDisplayInterval().split(" ")[1]), 
					Integer.parseInt(DBServices.getHomeDateDisplayInterval().split(" ")[0]));
		}
		rigTable.setItems(FXCollections.observableArrayList(DBServices.getRigsBetweenDateInterval(java.sql.Date.valueOf(LocalDate.now()), extendedDate.getTime())));
		rigTable.setPlaceholder(new Label(Constants.TABLE_PLACEHOLDER_MESSAGE));
		rigColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getRig().getRigName()));
		firmNameColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getFirmName()));
		rigDueDateColumn.setCellValueFactory(cellData -> new SimpleStringProperty(simpleDateFormat.format(cellData.getValue().getDueDate())));
		
		employeeTable.setItems(FXCollections.observableArrayList(DBServices.getEmployeesBetweenDateInterval(java.sql.Date.valueOf(LocalDate.now()), extendedDate.getTime())));
		employeeTable.setPlaceholder(new Label(Constants.TABLE_PLACEHOLDER_MESSAGE));
		employeeFirstNameColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getEmployee().getFirstName()));
		employeeLastNameColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getEmployee().getLastName()));
		employeeFirmNameColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getFirmName()));
		employeeDueDateColumn.setCellValueFactory(cellData -> new SimpleStringProperty(simpleDateFormat.format(cellData.getValue().getDueDate())));
		
		String homeDateIntervalUnit = "";
		if(!DBServices.getHomeDateDisplayInterval().split(" ")[0].equals("")) {
			int homeDateIntervalNumberOf = Integer.parseInt(DBServices.getHomeDateDisplayInterval().split(" ")[0]);
			switch(Integer.parseInt(DBServices.getHomeDateDisplayInterval().split(" ")[1])) {
			case Calendar.DATE : {
				if(homeDateIntervalNumberOf > 1) {
					homeDateIntervalUnit = "zile";
				} else {
					homeDateIntervalUnit = "zi";
				}
				break;
			}
			case Calendar.MONTH : {
				if(homeDateIntervalNumberOf > 1) {
					homeDateIntervalUnit = "luni";
				} else {
					homeDateIntervalUnit = "lună";
				}
				break;
			}
			case Calendar.YEAR : {
				if(homeDateIntervalNumberOf > 1) {
					homeDateIntervalUnit = "ani";
				} else {
					homeDateIntervalUnit = "an";
				}
				break;
			}
			}
			titleLabel.setText("Datele scadente pe perioada de " + DBServices.getHomeDateDisplayInterval().split(" ")[0] + " " + homeDateIntervalUnit);
		}
	}
}
