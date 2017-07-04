package com.rsvti.address;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Locale;

import com.rsvti.address.view.AddEmployeesToFirmController;
import com.rsvti.address.view.AddFirmController;
import com.rsvti.address.view.AddRigsToFirmController;
import com.rsvti.address.view.AddTestQuestionController;
import com.rsvti.address.view.DueDateOverviewController;
import com.rsvti.address.view.EmployeeOverviewController;
import com.rsvti.address.view.FirmOverviewController;
import com.rsvti.address.view.GenerateTableController;
import com.rsvti.address.view.GenerateTestController;
import com.rsvti.address.view.HomeController;
import com.rsvti.address.view.MenuController;
import com.rsvti.address.view.RigOverviewController;
import com.rsvti.address.view.SettingsController;
import com.rsvti.common.Data;
import com.rsvti.common.Utils;
import com.rsvti.database.entities.Employee;
import com.rsvti.database.entities.Rig;

import javafx.animation.PauseTransition;
import javafx.application.Application;
import javafx.event.EventHandler;
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
import javafx.stage.WindowEvent;
import javafx.util.Duration;

public class JavaFxMain extends Application {

	private static Stage primaryStage;
	private BorderPane rootLayout;
	private TabPane tabPane;
	private Stage addUpdateEmployeesToFirmStage;
	private AddEmployeesToFirmController addUpdateEmployeesToFirmController;
	private AddRigsToFirmController addUpdateRigsToFirmController;
	private Stage addUpdateRigsToFirmStage;
	private AddFirmController addFirmController;
	private Tab addRigParameterTab;
	private DueDateOverviewController dueDateOverviewController;
	private PauseTransition delay = new PauseTransition(Duration.seconds(3));
	private PauseTransition delay1 = new PauseTransition(Duration.seconds(3));
	private HomeController homeController;
	
	@Override
	public void start(Stage primaryStage) {
		JavaFxMain.primaryStage = primaryStage;
		JavaFxMain.primaryStage.setTitle("RSVTI App");
        
		JavaFxMain.primaryStage.getIcons().add(new Image(new File(Utils.getJarFilePath() + "images\\RSVTI_without_text.png").toURI().toString()));
        
//        Platform.setImplicitExit(false);
//		Utils.setTray(primaryStage);
		
//		initApp();
		
        initRootLayout();
        showHome();
	}
	
	public void initApp() {
		try {
	        FXMLLoader loader = new FXMLLoader();
	        loader.setLocation(JavaFxMain.class.getResource("view/StartImage.fxml"));
	        
	        AnchorPane pane = (AnchorPane) loader.load();
	        Scene scene = new Scene(pane);
	        
	        loader.getController();
	        
	        Stage stage = new Stage();
	        stage.setScene(scene);
	        stage.initStyle(StageStyle.UNDECORATED);
	        
	        stage.show();
	        
	        delay.setOnFinished( event -> stage.close() );
	        delay.play();
		} catch(Exception e) {
			e.printStackTrace();
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
            primaryStage.setScene(scene);
            
            MenuController menuController = loader.getController();
            menuController.setJavaFxMain(this);
            
            delay1.setOnFinished( event -> primaryStage.show() );
	        delay1.play();
        } catch (IOException e) {
            e.printStackTrace();
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
            
            tabPane.setTabClosingPolicy(TabClosingPolicy.SELECTED_TAB);
            tabPane.getTabs().add(tab);
            tabPane.getSelectionModel().select(tab);
            
        } catch (IOException e) {
            e.printStackTrace();
        }
        
	}
	
	public void showFirmOverview() {
        try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(JavaFxMain.class.getResource("view/FirmOverview.fxml"));
            AnchorPane personOverview = (AnchorPane) loader.load();

            Tab tab = new Tab("Firme");
            tab.setContent(personOverview);
            
            tabPane.setTabClosingPolicy(TabClosingPolicy.SELECTED_TAB);
            tabPane.getTabs().add(tab);
            tabPane.getSelectionModel().select(tab);
            
            FirmOverviewController controller = loader.getController();
            controller.setJavaFxMain(this);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
	
	public void showRigOverview(String firmName, List<Rig> rigList) {
	    try {
	        
	        FXMLLoader loader = new FXMLLoader();
	        loader.setLocation(JavaFxMain.class.getResource("view/RigOverview.fxml"));
	        AnchorPane rigOverview = (AnchorPane) loader.load();

	        // Set the person into the controller.
	        RigOverviewController controller = loader.getController();
	        controller.setRigList(rigList);
	        
	        Tab tab = new Tab(firmName);
            tab.setContent(rigOverview);
            tab.setClosable(true);
            
            tabPane.setTabClosingPolicy(TabClosingPolicy.SELECTED_TAB);
            tabPane.getTabs().add(tab);
            tabPane.getSelectionModel().select(tab);

	    } catch (IOException e) {
	        e.printStackTrace();
	    }
	}
	
	public void showEmployeeOverview(List<Employee> employeeList) {
	    try {
	        
	        FXMLLoader loader = new FXMLLoader();
	        loader.setLocation(JavaFxMain.class.getResource("view/EmployeeOverview.fxml"));
	        AnchorPane employeeOverview = (AnchorPane) loader.load();

	        Tab tab = new Tab("Personal deservent");
            tab.setContent(employeeOverview);
            tab.setClosable(true);
            
            tabPane.setTabClosingPolicy(TabClosingPolicy.SELECTED_TAB);
            tabPane.getTabs().add(tab);
            tabPane.getSelectionModel().select(tab);

	        // Set the person into the controller.
	        EmployeeOverviewController controller = loader.getController();
	        controller.setEmployeeList(employeeList);

	    } catch (IOException e) {
	        e.printStackTrace();
	    }
	}
	
	public void addFirm() {
		try {
	        
	        FXMLLoader loader = new FXMLLoader();
	        loader.setLocation(JavaFxMain.class.getResource("view/AddFirm.fxml"));
	        AnchorPane addFirm = (AnchorPane) loader.load();

	        addRigParameterTab = new Tab("Adaugă firmă");
            addRigParameterTab.setContent(addFirm);
            addRigParameterTab.setClosable(true);
            
            tabPane.setTabClosingPolicy(TabClosingPolicy.SELECTED_TAB);
            tabPane.getTabs().add(addRigParameterTab);
            tabPane.getSelectionModel().select(addRigParameterTab);
            
            addFirmController = loader.getController();
            addFirmController.setJavaFxMain(this);
	    } catch (IOException e) {
	        e.printStackTrace();
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
            
            tabPane.setTabClosingPolicy(TabClosingPolicy.SELECTED_TAB);
            tabPane.getTabs().add(addRigParameterTab);
            tabPane.getSelectionModel().select(addRigParameterTab);
            
            loader.getController();
	    } catch (IOException e) {
	        e.printStackTrace();
	    }
	}
	
	public void showAddUpdateRigsToFirm(Rig rig, boolean isUpdate, boolean isDueDateUpdate, String stageName) {
		try {
	        
	        FXMLLoader loader = new FXMLLoader();
	        loader.setLocation(JavaFxMain.class.getResource("view/AddRigsToFirm.fxml"));
	        AnchorPane addUpdateRigsToFirm = (AnchorPane) loader.load();
	        
	        AddRigsToFirmController controller = loader.getController();
	        controller.setJavaFxMain(this);
	        controller.setFirmName(stageName);
	        controller.setIsDueDateUpdate(isDueDateUpdate);

	        if(rig != null) {
            	controller.showRigDetails(rig);
            }
	        
	        addUpdateRigsToFirmController = loader.getController();
	        addUpdateRigsToFirmController.setJavaFxMain(this);
	        addUpdateRigsToFirmController.setIsUpdate(isUpdate);
            
	        addUpdateRigsToFirmStage = new Stage();
	        addUpdateRigsToFirmStage.setTitle(stageName);
	        addUpdateRigsToFirmStage.initModality(Modality.WINDOW_MODAL);
	        addUpdateRigsToFirmStage.initOwner(primaryStage);
	        addUpdateRigsToFirmStage.getIcons().add(new Image(new File(Utils.getJarFilePath() + "images\\RSVTI_without_text.png").toURI().toString()));
	        Scene scene = new Scene(addUpdateRigsToFirm);
	        addUpdateRigsToFirmStage.setScene(scene);
            
            addUpdateRigsToFirmStage.showAndWait();
            
	    } catch (IOException e) {
	        e.printStackTrace();
	    }
	}
	
	public void showAddUpdateEmployeesToFirm(Employee employee, boolean isUpdate, boolean isDueDateUpdate, String stageName) {
		try {
	        
	        FXMLLoader loader = new FXMLLoader();
	        loader.setLocation(JavaFxMain.class.getResource("view/AddEmployeesToFirm.fxml"));
	        AnchorPane addUpdateEmployeesToFirm = (AnchorPane) loader.load();
	        
	        AddEmployeesToFirmController controller = loader.getController();
	        controller.setJavaFxMain(this);
	        controller.setFirmName(stageName);
	        controller.setIsDueDateUpdate(isDueDateUpdate);

	        addUpdateEmployeesToFirmController = loader.getController();
            addUpdateEmployeesToFirmController.setJavaFxMain(this);
            addUpdateEmployeesToFirmController.setIsUpdate(isUpdate);
            
            if(employee != null) {
            	addUpdateEmployeesToFirmController.showEmployeeDetails(employee);
            }
            
            addUpdateEmployeesToFirmStage = new Stage();
	        addUpdateEmployeesToFirmStage.setTitle(stageName);
	        addUpdateEmployeesToFirmStage.initModality(Modality.WINDOW_MODAL);
	        addUpdateEmployeesToFirmStage.initOwner(primaryStage);
	        addUpdateEmployeesToFirmStage.getIcons().add(new Image(new File(Utils.getJarFilePath() + "images\\RSVTI_without_text.png").toURI().toString()));
	        Scene scene = new Scene(addUpdateEmployeesToFirm);
	        addUpdateEmployeesToFirmStage.setScene(scene);
            
            addUpdateEmployeesToFirmStage.showAndWait();
            
            
	    } catch (IOException e) {
	        e.printStackTrace();
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
            
            tabPane.setTabClosingPolicy(TabClosingPolicy.SELECTED_TAB);
            tabPane.getTabs().add(dueDateOverviewTab);
            tabPane.getSelectionModel().select(dueDateOverviewTab);
            
            dueDateOverviewController = loader.getController();
            dueDateOverviewController.setJavaFxMain(this);
	    } catch (IOException e) {
	        e.printStackTrace();
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
            
            tabPane.setTabClosingPolicy(TabClosingPolicy.SELECTED_TAB);
            tabPane.getTabs().add(addTestQuestionTab);
            tabPane.getSelectionModel().select(addTestQuestionTab);
            
            AddTestQuestionController addTestQuestionController = loader.getController();
            addTestQuestionController.setJavaFxMain(this);
	    } catch (IOException e) {
	        e.printStackTrace();
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
            
            tabPane.setTabClosingPolicy(TabClosingPolicy.SELECTED_TAB);
            tabPane.getTabs().add(generateTestTab);
            tabPane.getSelectionModel().select(generateTestTab);
            
            GenerateTestController generateTestController = loader.getController();
            generateTestController.setJavaFxMain(this);
	    } catch (IOException e) {
	        e.printStackTrace();
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
	        stage.setScene(scene);
            
	        stage.setOnCloseRequest(new EventHandler<WindowEvent>() {
	        	@Override
	        	public void handle(WindowEvent e) {
	        		homeController.refresh();
	        		stage.close();
	        	}
			});
	        
	        stage.showAndWait();
            
	        SettingsController settingsController = loader.getController();
	        settingsController.setJavaFxMain(this);
		} catch(Exception e) {
			e.printStackTrace();
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
            
            tabPane.setTabClosingPolicy(TabClosingPolicy.SELECTED_TAB);
            tabPane.getTabs().add(generateTableTab);
            tabPane.getSelectionModel().select(generateTableTab);
            
            GenerateTableController generateTableController = loader.getController();
            generateTableController.setJavaFxMain(this);
	    } catch (IOException e) {
	        e.printStackTrace();
	    }
	}
	
	public void showTestLogs() {
		try {
	        
	        FXMLLoader loader = new FXMLLoader();
	        loader.setLocation(JavaFxMain.class.getResource("view/TestLogOverview.fxml"));
	        AnchorPane tableOverview = (AnchorPane) loader.load();

	        Tab tableOverviewTab = new Tab("Genereaza tabel");
	        tableOverviewTab.setContent(tableOverview);
	        tableOverviewTab.setClosable(true);
            
            tabPane.setTabClosingPolicy(TabClosingPolicy.SELECTED_TAB);
            tabPane.getTabs().add(tableOverviewTab);
            tabPane.getSelectionModel().select(tableOverviewTab);
            
            loader.getController();
	    } catch (IOException e) {
	        e.printStackTrace();
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
//		Utils.setErrorLog();
//		Utils.setStartup();
		Utils.createFolderHierarchy();
		Data.populate();
		launch(args);
	}
}
