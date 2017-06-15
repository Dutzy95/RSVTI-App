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
	
	public void setJavaFxMain(JavaFxMain javaFxMain) {
		this.javaFxMain = javaFxMain;
	}
}
