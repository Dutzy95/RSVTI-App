package com.rsvti;

import java.io.File;
import java.util.List;
import java.util.Locale;

import com.rsvti.backup.GoogleDriveBackup;
import com.rsvti.common.Data;
import com.rsvti.common.Utils;
import com.rsvti.controller.AddEmployeesToFirmController;
import com.rsvti.controller.AddFirmController;
import com.rsvti.controller.AddRigsToFirmController;
import com.rsvti.controller.AddTestQuestionController;
import com.rsvti.controller.DueDateOverviewController;
import com.rsvti.controller.EmployeeOverviewController;
import com.rsvti.controller.FirmOverviewController;
import com.rsvti.controller.GenerateCertificateController;
import com.rsvti.controller.GenerateTableController;
import com.rsvti.controller.GenerateTestController;
import com.rsvti.controller.HomeController;
import com.rsvti.controller.MenuController;
import com.rsvti.controller.RigOverviewController;
import com.rsvti.controller.SettingsController;
import com.rsvti.model.database.DBServices;
import com.rsvti.model.entities.Employee;
import com.rsvti.model.entities.Rig;

import javafx.animation.PauseTransition;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.control.TabPane.TabClosingPolicy;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.util.Duration;

public class JavaFxMain extends Application {

	public static Stage primaryStage;
	private BorderPane rootLayout;
	private TabPane tabPane;
	public static Stage addUpdateEmployeesToFirmStage;
	private AddEmployeesToFirmController addUpdateEmployeesToFirmController;
	private AddRigsToFirmController addUpdateRigsToFirmController;
	private Stage addUpdateRigsToFirmStage;
	private AddFirmController addFirmController;
	private Tab addRigParameterTab;
	private DueDateOverviewController dueDateOverviewController;
	private PauseTransition delay = new PauseTransition(Duration.seconds(3));
	private HomeController homeController;
	
	@Override
	public void start(Stage primaryStage) {
		try {
			JavaFxMain.primaryStage = primaryStage;
			JavaFxMain.primaryStage.setTitle("RSVTI App");
	        
			JavaFxMain.primaryStage.getIcons().add(new Image(getClass().getResourceAsStream("/RSVTI_without_text.png")));
	        
//			Utils.setTray(primaryStage);
			
			initApp();
		} catch (Exception e) {
			DBServices.saveErrorLogEntry(e);
		}
	}
	
	public void initApp() {
		try {
	        FXMLLoader loader = new FXMLLoader();
	        loader.setLocation(JavaFxMain.class.getResource("view/StartImage.fxml"));
	        
	        AnchorPane pane = (AnchorPane) loader.load();
	        Scene scene = new Scene(pane);
	        String css = this.getClass().getResource("/Common.css").toExternalForm(); 
	        scene.getStylesheets().add(css);
	        
	        loader.getController();
	        
	        Stage stage = new Stage();
	        stage.setScene(scene);
	        stage.initStyle(StageStyle.UNDECORATED);
	        stage.getIcons().add(new Image(getClass().getResourceAsStream("/RSVTI_without_text.png")));
	        
	        stage.show();
	        
	        delay.setOnFinished( event -> {
	        	stage.close();
	        	initRootLayout();
		        showHome();
	        });
	        delay.play();
		} catch(Exception e) {
			DBServices.saveErrorLogEntry(e);
		}
	}
	
	public void initRootLayout() {
        try {
            // Load root layout from fxml file.
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(JavaFxMain.class.getResource("view/RootLayout.fxml"));
            
            rootLayout = (BorderPane) loader.load();
            tabPane = (TabPane) rootLayout.getCenter();

            // Show the scene containing the root layout.
            Scene scene = new Scene(rootLayout);
            String css = this.getClass().getResource("/Common.css").toExternalForm(); 
	        scene.getStylesheets().add(css);
            primaryStage.setScene(scene);
            
            MenuController menuController = loader.getController();
            menuController.setJavaFxMain(this);
            
            primaryStage.show();
        } catch (Exception e) {
            DBServices.saveErrorLogEntry(e);
        }
    }
	
	public void showHome() {
		try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(JavaFxMain.class.getResource("view/Home.fxml"));
            BorderPane home = (BorderPane) loader.load();

            homeController = loader.getController();
            
            Tab tab = new Tab("Acasă");
            tab.setContent(home);
            tab.setClosable(false);
            
            tabPane.setTabClosingPolicy(TabClosingPolicy.ALL_TABS);
            tabPane.getTabs().add(tab);
            tabPane.getSelectionModel().select(tab);
            
        } catch (Exception e) {
            DBServices.saveErrorLogEntry(e);
        }
        
	}
	
	public void showFirmOverview() {
        try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(JavaFxMain.class.getResource("view/FirmOverview.fxml"));
            AnchorPane personOverview = (AnchorPane) loader.load();

            Tab tab = new Tab("Firme");
            tab.setContent(personOverview);
            
            tabPane.getTabs().add(tab);
            tabPane.getSelectionModel().select(tab);
            
            FirmOverviewController controller = loader.getController();
            controller.setJavaFxMain(this);
        } catch (Exception e) {
            DBServices.saveErrorLogEntry(e);
        }
    }
	
	public void showRigOverview(String firmName, List<Rig> rigList, boolean showFirmName) {
	    try {
	    	
	        FXMLLoader loader = new FXMLLoader();
	        loader.setLocation(JavaFxMain.class.getResource("view/RigOverview.fxml"));
	        AnchorPane rigOverview = (AnchorPane) loader.load();

	        // Set the person into the controller.
	        RigOverviewController controller = loader.getController();
	        controller.setRigList(rigList, showFirmName);
	        
	        Tab tab;
	        if(firmName == null) {
	        	tab = new Tab("Utilaje");
	        } else {
	        	tab = new Tab("Utilaje - " + firmName);
	        }
	        tab.setContent(rigOverview);
            tab.setClosable(true);
            
            tabPane.getTabs().add(tab);
            tabPane.getSelectionModel().select(tab);
            
	    } catch (Exception e) {
	        DBServices.saveErrorLogEntry(e);
	    }
	}
	
	public void showEmployeeOverview(String firmName, List<Employee> employeeList) {
	    try {
	        
	        FXMLLoader loader = new FXMLLoader();
	        loader.setLocation(JavaFxMain.class.getResource("view/EmployeeOverview.fxml"));
	        AnchorPane employeeOverview = (AnchorPane) loader.load();

	        Stage stage = new Stage();
	        if(firmName == null) {
	        	stage.setTitle("Angajați");
	        } else {
	        	stage.setTitle("Personal deservent - " + firmName);
	        }
	        stage.initModality(Modality.WINDOW_MODAL);
	        stage.initOwner(primaryStage);
	        stage.getIcons().add(new Image(new File(Utils.getJarFilePath() + "images\\RSVTI_without_text.png").toURI().toString()));
	        Scene scene = new Scene(employeeOverview);
	        String css = this.getClass().getResource("/Common.css").toExternalForm(); 
	        scene.getStylesheets().add(css);
	        stage.setScene(scene);
	        
	        // Set the person into the controller.
	        EmployeeOverviewController controller = loader.getController();
	        controller.setEmployeeList(employeeList);
	        
	        stage.showAndWait();

	    } catch (Exception e) {
	        DBServices.saveErrorLogEntry(e);
	    }
	}
	
	public void addUpdateFirm(boolean update) {
		try {
	        
	        FXMLLoader loader = new FXMLLoader();
	        loader.setLocation(JavaFxMain.class.getResource("view/AddFirm.fxml"));
	        AnchorPane addFirm = (AnchorPane) loader.load();

	        if(update) {
	        	addRigParameterTab = new Tab("Modifică firmă");
	        } else {
	        	addRigParameterTab = new Tab("Adaugă firmă");
	        }
            addRigParameterTab.setContent(addFirm);
            addRigParameterTab.setClosable(true);
            
            tabPane.getTabs().add(addRigParameterTab);
            tabPane.getSelectionModel().select(addRigParameterTab);
            
            addFirmController = loader.getController();
            addFirmController.setJavaFxMain(this);
        	addFirmController.setUpdate(update);
        	addFirmController.initializeIfUpdate();
            
	    } catch (Exception e) {
	        DBServices.saveErrorLogEntry(e);
	    }
	}
	
	public void addRigParameter() {
		try {
	        
	        FXMLLoader loader = new FXMLLoader();
	        loader.setLocation(JavaFxMain.class.getResource("view/AddRigParameter.fxml"));
	        AnchorPane addRigParameter = (AnchorPane) loader.load();

	        addRigParameterTab = new Tab("Parametrii pentru utilaje");
            addRigParameterTab.setContent(addRigParameter);
            addRigParameterTab.setClosable(true);
            
            tabPane.getTabs().add(addRigParameterTab);
            tabPane.getSelectionModel().select(addRigParameterTab);
            
            loader.getController();
            
	    } catch (Exception e) {
	        DBServices.saveErrorLogEntry(e);
	    }
	}
	
	public void showAddUpdateRigsToFirm(Rig rig, boolean updateForFirm, boolean isDueDateUpdate, String stageName, String firmId, boolean update) {
		try {
	        
	        FXMLLoader loader = new FXMLLoader();
	        loader.setLocation(JavaFxMain.class.getResource("view/AddRigsToFirm.fxml"));
	        AnchorPane addUpdateRigsToFirm = (AnchorPane) loader.load();
	        
	        AddRigsToFirmController controller = loader.getController();
	        controller.setJavaFxMain(this);
	        controller.setFirmId(firmId);
	        controller.setIsDueDateUpdate(isDueDateUpdate);

	        if(rig != null) {
            	controller.showRigDetails(rig);
            }
	        
	        addUpdateRigsToFirmController = loader.getController();
	        addUpdateRigsToFirmController.setJavaFxMain(this);
	        addUpdateRigsToFirmController.setUpdateForFirm(updateForFirm);
	        addUpdateRigsToFirmController.setUpdate(update);
	        addUpdateRigsToFirmController.initializeIfUpdate();
            
	        if(update) {
	        	Tab updateRigTab = new Tab("Modifică utilaj");
	            updateRigTab.setContent(addUpdateRigsToFirm);
	            updateRigTab.setClosable(true);
	            
	            tabPane.getTabs().add(updateRigTab);
	            tabPane.getSelectionModel().select(updateRigTab);
	        } else {
	            addUpdateRigsToFirmStage = new Stage();
		        addUpdateRigsToFirmStage.setTitle(stageName);
		        addUpdateRigsToFirmStage.initModality(Modality.WINDOW_MODAL);
		        addUpdateRigsToFirmStage.initOwner(primaryStage);
		        addUpdateRigsToFirmStage.getIcons().add(new Image(new File(Utils.getJarFilePath() + "images\\RSVTI_without_text.png").toURI().toString()));
		        Scene scene = new Scene(addUpdateRigsToFirm);
		        String css = this.getClass().getResource("/Common.css").toExternalForm(); 
		        scene.getStylesheets().add(css);
		        addUpdateRigsToFirmStage.setScene(scene);
	            
	            addUpdateRigsToFirmStage.showAndWait();
	        }
	    } catch (Exception e) {
	        DBServices.saveErrorLogEntry(e);
	    }
	}
	
	public void showAddUpdateEmployeesToFirm(Employee employee, boolean updateForFirm, boolean isDueDateUpdate, String stageName, String firmId, boolean update) {
		try {
	        
	        FXMLLoader loader = new FXMLLoader();
	        loader.setLocation(JavaFxMain.class.getResource("view/AddEmployeesToFirm.fxml"));
	        AnchorPane addUpdateEmployeesToFirm = (AnchorPane) loader.load();
	        
	        addUpdateEmployeesToFirmController = loader.getController();
	        addUpdateEmployeesToFirmController.setJavaFxMain(this);
	        addUpdateEmployeesToFirmController.setFirmId(firmId);
	        addUpdateEmployeesToFirmController.setIsDueDateUpdate(isDueDateUpdate);
	        addUpdateEmployeesToFirmController.setValidators(addUpdateEmployeesToFirmStage);
	        addUpdateEmployeesToFirmController.setUpdateForFirm(updateForFirm);
	        addUpdateEmployeesToFirmController.setUpdate(update);
	        addUpdateEmployeesToFirmController.initializeIfUpdate();
            
	        if(update) {
	        	Tab updateEmployeeTab = new Tab("Modifică angajat");
	            updateEmployeeTab.setContent(addUpdateEmployeesToFirm);
	            updateEmployeeTab.setClosable(true);
	            
	            tabPane.getTabs().add(updateEmployeeTab);
	            tabPane.getSelectionModel().select(updateEmployeeTab);
	        } else {
	            addUpdateEmployeesToFirmStage = new Stage();
		        addUpdateEmployeesToFirmStage.setTitle(stageName);
		        addUpdateEmployeesToFirmStage.initModality(Modality.WINDOW_MODAL);
		        addUpdateEmployeesToFirmStage.initOwner(primaryStage);
		        addUpdateEmployeesToFirmStage.getIcons().add(new Image(new File(Utils.getJarFilePath() + "images\\RSVTI_without_text.png").toURI().toString()));
		        Scene scene = new Scene(addUpdateEmployeesToFirm);
		        String css = this.getClass().getResource("/Common.css").toExternalForm(); 
		        scene.getStylesheets().add(css);
		        addUpdateEmployeesToFirmStage.setScene(scene);
	            
	            if(employee != null) {
	            	addUpdateEmployeesToFirmController.showEmployeeDetails(employee);
	            }
	            
	            addUpdateEmployeesToFirmStage.showAndWait();
	        }
            
	    } catch (Exception e) {
	        DBServices.saveErrorLogEntry(e);
	    }
	}
	
	public void showDueDateOverview() {
		try {
	        
	        FXMLLoader loader = new FXMLLoader();
	        loader.setLocation(JavaFxMain.class.getResource("view/DueDateOverview.fxml"));
	        AnchorPane dueDateOverview = (AnchorPane) loader.load();

	        Tab dueDateOverviewTab = new Tab("Date scadente");
	        dueDateOverviewTab.setContent(dueDateOverview);
	        dueDateOverviewTab.setClosable(true);
            
            tabPane.getTabs().add(dueDateOverviewTab);
            tabPane.getSelectionModel().select(dueDateOverviewTab);
            
            dueDateOverviewController = loader.getController();
            dueDateOverviewController.setJavaFxMain(this);
            
	    } catch (Exception e) {
	        DBServices.saveErrorLogEntry(e);
	    }
	}
	
	public void addTestQuestions() {
		try {
	        
	        FXMLLoader loader = new FXMLLoader();
	        loader.setLocation(JavaFxMain.class.getResource("view/AddTestQuestion.fxml"));
	        AnchorPane addTestQuestion = (AnchorPane) loader.load();

	        Tab addTestQuestionTab = new Tab("Adauga intrebări test");
	        addTestQuestionTab.setContent(addTestQuestion);
	        addTestQuestionTab.setClosable(true);
            
            tabPane.getTabs().add(addTestQuestionTab);
            tabPane.getSelectionModel().select(addTestQuestionTab);
            
            AddTestQuestionController addTestQuestionController = loader.getController();
            addTestQuestionController.setJavaFxMain(this);
            
	    } catch (Exception e) {
	        DBServices.saveErrorLogEntry(e);
	    }
	}
	
	public void generateTest() {
		try {
	        
	        FXMLLoader loader = new FXMLLoader();
	        loader.setLocation(JavaFxMain.class.getResource("view/GenerateTest.fxml"));
	        AnchorPane generateTest = (AnchorPane) loader.load();

	        Tab generateTestTab = new Tab("Genereaza test");
	        generateTestTab.setContent(generateTest);
	        generateTestTab.setClosable(true);
            
            tabPane.getTabs().add(generateTestTab);
            tabPane.getSelectionModel().select(generateTestTab);
            
            GenerateTestController generateTestController = loader.getController();
            generateTestController.setJavaFxMain(this);
            
	    } catch (Exception e) {
	        DBServices.saveErrorLogEntry(e);
	    }
	}
	
	public void showSettings() {
		try {
			
	        FXMLLoader loader = new FXMLLoader();
	        loader.setLocation(JavaFxMain.class.getResource("view/Settings.fxml"));
	        AnchorPane settings = (AnchorPane) loader.load();
	
	        Stage stage = new Stage();
	        stage.setTitle("Setări");
	        stage.initModality(Modality.WINDOW_MODAL);
	        stage.initOwner(primaryStage);
	        stage.getIcons().add(new Image(new File(Utils.getJarFilePath() + "images\\RSVTI_without_text.png").toURI().toString()));
	        Scene scene = new Scene(settings);
	        String css = this.getClass().getResource("/Common.css").toExternalForm(); 
	        scene.getStylesheets().add(css);
	        stage.setScene(scene);
            
	        stage.setOnCloseRequest(e -> {
        		homeController.refresh();
        		stage.close();
			});
	        
	        SettingsController settingsController = loader.getController();
	        settingsController.setJavaFxMain(this);
	        settingsController.setStage(stage);
	        settingsController.setHomeController(homeController);
	        
	        stage.showAndWait();
	        
		} catch(Exception e) {
			DBServices.saveErrorLogEntry(e);
		}
	}
	
	public void generateTable() {
		try {
	        
	        FXMLLoader loader = new FXMLLoader();
	        loader.setLocation(JavaFxMain.class.getResource("view/GenerateTable.fxml"));
	        AnchorPane generateTable = (AnchorPane) loader.load();

	        Tab generateTableTab = new Tab("Genereaza tabel");
	        generateTableTab.setContent(generateTable);
	        generateTableTab.setClosable(true);
            
            tabPane.getTabs().add(generateTableTab);
            tabPane.getSelectionModel().select(generateTableTab);
            
            GenerateTableController generateTableController = loader.getController();
            generateTableController.setJavaFxMain(this);
            
	    } catch (Exception e) {
	        DBServices.saveErrorLogEntry(e);
	    }
	}
	
	public void showTestLogs() {
		try {
	        
	        FXMLLoader loader = new FXMLLoader();
	        loader.setLocation(JavaFxMain.class.getResource("view/TestLogOverview.fxml"));
	        AnchorPane tableOverview = (AnchorPane) loader.load();

	        Tab tableOverviewTab = new Tab("Istoric teste generate");
	        tableOverviewTab.setContent(tableOverview);
	        tableOverviewTab.setClosable(true);
            
            tabPane.getTabs().add(tableOverviewTab);
            tabPane.getSelectionModel().select(tableOverviewTab);
            
            loader.getController();
            
	    } catch (Exception e) {
	        DBServices.saveErrorLogEntry(e);
	    }
	}
	
	public void generateCertificate() {
		try {
			FXMLLoader loader = new FXMLLoader();
			loader.setLocation(JavaFxMain.class.getResource("view/GenerateCertificate.fxml"));
			AnchorPane generateCertificate = (AnchorPane) loader.load();
			
			Tab generateCertificateTab = new Tab("Generează adeverință angajat");
			generateCertificateTab.setContent(generateCertificate);
			generateCertificateTab.setClosable(true);
			
			tabPane.getTabs().add(generateCertificateTab);
			tabPane.getSelectionModel().select(generateCertificateTab);
			
			GenerateCertificateController generateCertificateController = loader.getController();
			generateCertificateController.setJavaFxMain(this);
			
		} catch (Exception e) {
			DBServices.saveErrorLogEntry(e);
		}
	}
	
	public void generateTestResultsReport() {
		try {
			FXMLLoader loader = new FXMLLoader();
			loader.setLocation(JavaFxMain.class.getResource("view/GenerateTestResultsReport.fxml"));
			AnchorPane generateTestResultsReport = (AnchorPane) loader.load();
			
			Tab generateTestResultsReportTab = new Tab("Generează proces verbal pentru rezultate examinare");
			generateTestResultsReportTab.setContent(generateTestResultsReport);
			generateTestResultsReportTab.setClosable(true);
			
			tabPane.getTabs().add(generateTestResultsReportTab);
			tabPane.getSelectionModel().select(generateTestResultsReportTab);
			
		} catch (Exception e) {
			DBServices.saveErrorLogEntry(e);
		}
	}
	
	public void generateTechnicalRigEvaluationReport() {
		try {
			FXMLLoader loader = new FXMLLoader();
			loader.setLocation(JavaFxMain.class.getResource("view/GenerateTechnicalRigEvaluationReport.fxml"));
			AnchorPane generateTechnicalRigEvaluationReport = (AnchorPane) loader.load();
			
			Tab generateTechnicalRigEvaluationReportTab = new Tab("Generează proces verbal pentru verificarea tehnică a utilajului");
			generateTechnicalRigEvaluationReportTab.setContent(generateTechnicalRigEvaluationReport);
			generateTechnicalRigEvaluationReportTab.setClosable(true);
			
			tabPane.getTabs().add(generateTechnicalRigEvaluationReportTab);
			tabPane.getSelectionModel().select(generateTechnicalRigEvaluationReportTab);
			
		} catch (Exception e) {
			DBServices.saveErrorLogEntry(e);
		}
	}
	
	public JavaFxMain() {
    }
	
	public Stage getPrimaryStage() {
        return primaryStage;
    }
	
	public TabPane getTabPane() {
		return tabPane;
	}
	
	public Stage getAddEmployeesToFirmStage() {
		return addUpdateEmployeesToFirmStage;
	}
	
	public AddEmployeesToFirmController getAddEmployeesToRigController() {
		return addUpdateEmployeesToFirmController;
	}
	
	public AddRigsToFirmController getAddRigsToFirmController() {
		return addUpdateRigsToFirmController;
	}
	
	public Stage getAddRigsToFirmStage() {
		return addUpdateRigsToFirmStage;
	}
	
	public AddFirmController getAddFirmController() {
		return addFirmController;
	}
	
	public Tab getAddFirmTab() {
		return addRigParameterTab;
	}
	
	public DueDateOverviewController getDueDateOverviewController() {
		return dueDateOverviewController;
	}

	public static void main(String[] args) {
		Locale locale = new Locale("ro", "RO");
		Locale.setDefault(locale);
		Utils.generateReadMe();
		Utils.createDesktopShortcut();
//		Utils.setStartup();
		GoogleDriveBackup.initialize();
		Utils.createFolderHierarchy();
		Data.populate();
		launch(args);
	}
}
