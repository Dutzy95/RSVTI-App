package com.rsvti.controller;

import java.util.Optional;

import com.rsvti.JavaFxMain;
import com.rsvti.backup.GoogleDriveBackup;
import com.rsvti.common.Constants;
import com.rsvti.common.Utils;
import com.rsvti.model.database.DBServices;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ButtonType;
import javafx.scene.control.CheckMenuItem;

public class MenuController {
	
	private JavaFxMain javaFxMain;
	
	@FXML
	private CheckMenuItem checkBackupActivation;

	
	@FXML
	private void initialize() {
		checkBackupActivation.selectedProperty().set(DBServices.getBackupActivation());
	}
	
	@FXML
	private void handleAddFirm() {
		try {
			javaFxMain.addUpdateFirm(false);
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
	public void handleEditFirm() {
		try {
			javaFxMain.addUpdateFirm(true);
		} catch(Exception e) {
			DBServices.saveErrorLogEntry(e);
		}
	}
	
	@FXML
	public void handleEditRig() {
		try {
			javaFxMain.showAddUpdateRigsToFirm(null, false, false, "Modifică utilaj", null, true);
		} catch(Exception e) {
			DBServices.saveErrorLogEntry(e);
		}
	}
	
	@FXML
	public void handleEditEmployee() {
		try {
			javaFxMain.showAddUpdateEmployeesToFirm(null, false, false, "Modifică angajat", null, true);
		} catch(Exception e) {
			DBServices.saveErrorLogEntry(e);
		}
	}
	
	@FXML
	private void handleViewRigs() {
		try {
			javaFxMain.showRigOverview(null, DBServices.getAllRigs(), true);
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
	private void handleViewEmployees() {
		try {
			javaFxMain.showEmployeeOverview(null, DBServices.getAllEmployees());
		} catch(Exception e) {
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
	
	@FXML
	public void handleTechnicalRigEvaluationReport() {
		try {
			javaFxMain.generateTechnicalRigEvaluationReport();
		} catch (Exception e) {
			DBServices.saveErrorLogEntry(e);
		}
	}
	
	@FXML
	public void handleCheckBackupActivation() {
		DBServices.saveBackupActivation(checkBackupActivation.selectedProperty().get());
	}
	
	@FXML
	public void handleRecoverData() {
		try {
			Optional<ButtonType> choice = Utils.alert(AlertType.INFORMATION, "Recuperare date", "Recuperare date", "Doriti sa recuperati datele?", true);
			if(choice.get().getButtonData() == ButtonType.YES.getButtonData()) {
				if(GoogleDriveBackup.connected()) {
					new Thread(() -> {
						GoogleDriveBackup.updateLocalDBFiles();
						Platform.runLater(() -> {
							Optional<ButtonType> restartChoice = Utils.alert(AlertType.INFORMATION, "Recuperare date", "Succes!", "Datele au fost recuperate cu succes! Aplicația trebuie"
										+ " repornită pentru ca modificările să aibă loc. Doriti sa reporniți aplicația acum?", true);
							if(restartChoice.get().getButtonData() == ButtonType.YES.getButtonData()) {
								try {
									Runtime.getRuntime().addShutdownHook(new Thread() {
										@Override
										public void run() {
											try {
												Runtime.getRuntime().exec("java -jar " + Constants.APP_NAME + ".jar");
											} catch (Exception e) {
												e.printStackTrace();
											}
										}
									});
									System.exit(0);
								} catch(Exception e) {
									DBServices.saveErrorLogEntry(e);
								}
							}
						});
					}).start();
				} else {
					Utils.alert(AlertType.ERROR, "Recuperare date", "Eroare!", "Nu exista legatura la reteaua de internet!", false);
				}
			}
		} catch(Exception e) {
			DBServices.saveErrorLogEntry(e);
		}
	}
	
	public void setJavaFxMain(JavaFxMain javaFxMain) {
		this.javaFxMain = javaFxMain;
	}
}
