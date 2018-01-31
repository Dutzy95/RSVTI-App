package com.rsvti.address.controller;

import com.rsvti.address.JavaFxMain;
import com.rsvti.database.services.DBServices;

import javafx.fxml.FXML;

public class MenuController {
	
	private JavaFxMain javaFxMain;

	@FXML
	private void handleAddFirm() {
		try {
			javaFxMain.addFirm();
		} catch (Exception e) {
			DBServices.saveErrorLogEntry(e);
		}
	}
	
	@FXML
	private void handleAddRigParameters() {
		try {
			javaFxMain.addRigParameter();
		} catch (Exception e) {
			DBServices.saveErrorLogEntry(e);
		}
	}
	
	@FXML
	private void handleAddTestQuestion() {
		try {
			javaFxMain.addTestQuestions();
		} catch (Exception e) {
			DBServices.saveErrorLogEntry(e);
		}
	}
	
	@FXML
	private void handleShowSettings() {
		try {
			javaFxMain.showSettings();
		} catch (Exception e) {
			DBServices.saveErrorLogEntry(e);
		}
	}
	
	@FXML
	public void handleFileClose() {
		System.exit(0);
	}
	
	@FXML
	private void handleViewRigs() {
		try {
			javaFxMain.showRigOverview(null , DBServices.getAllRigs());
		} catch (Exception e) {
			DBServices.saveErrorLogEntry(e);
		}
	}
	
	@FXML
	private void handleViewFirms() {
		try {
			javaFxMain.showFirmOverview();
		} catch (Exception e) {
			DBServices.saveErrorLogEntry(e);
		}
	}
	
	@FXML
	private void handleViewDueDateOverview() {
		try {
			javaFxMain.showDueDateOverview();
		} catch (Exception e) {
			DBServices.saveErrorLogEntry(e);
		}
	}
	
	@FXML
	private void handleViewTestLogs() {
		try {
			javaFxMain.showTestLogs();
		} catch (Exception e) {
			DBServices.saveErrorLogEntry(e);
		}
	}
	
	@FXML
	private void handleGenerateTest() {
		try {
			javaFxMain.generateTest();
		} catch (Exception e) {
			DBServices.saveErrorLogEntry(e);
		}
	}
	
	@FXML
	private void handleGenerateTable() {
		try {
			javaFxMain.generateTable();
		} catch (Exception e) {
			DBServices.saveErrorLogEntry(e);
		}
	}
	
	@FXML
	private void handleGenerateCertificate() {
		try {
			javaFxMain.generateCertificate();
		} catch (Exception e) {
			DBServices.saveErrorLogEntry(e);
		}
	}
	
	@FXML
	public void handleGenerateTestResultsReport() {
		try {
			javaFxMain.generateTestResultsReport();
		} catch (Exception e) {
			DBServices.saveErrorLogEntry(e);
		}
	}
	
	public void setJavaFxMain(JavaFxMain javaFxMain) {
		this.javaFxMain = javaFxMain;
	}
}
