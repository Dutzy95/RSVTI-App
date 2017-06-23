package com.rsvti.address.view;

import com.rsvti.address.JavaFxMain;
import com.rsvti.database.services.DBServices;

import javafx.fxml.FXML;

public class MenuController {
	
	private JavaFxMain javaFxMain;

	public MenuController() {
		
	}
	
	@FXML
	private void initialize() {
		
	}
	
	@FXML
	private void handleAddFirm() {
		javaFxMain.addFirm();
	}
	
	@FXML
	private void handleAddRigParameters() {
		javaFxMain.addRigParameter();
	}
	
	@FXML
	private void handleAddTestQuestion() {
		javaFxMain.addTestQuestions();
	}
	
	@FXML
	private void handleShowSettings() {
		javaFxMain.showSettings();
	}
	
	@FXML
	public void handleFileClose() {
		System.exit(0);
	}
	
	@FXML
	private void handleViewRigs() {
		javaFxMain.showRigOverview("Utilaje", DBServices.getAllRigs());
	}
	
	@FXML
	private void handleViewFirms() {
		javaFxMain.showFirmOverview();
	}
	
	@FXML
	private void handleViewDueDateOverview() {
		javaFxMain.showDueDateOverview();
	}
	
	@FXML
	private void handleGenerateTest() {
		javaFxMain.generateTest();
	}
	
	@FXML
	private void handleGenerateTable() {
		javaFxMain.generateTable();
	}
	
	public void setJavaFxMain(JavaFxMain javaFxMain) {
		this.javaFxMain = javaFxMain;
	}
}
