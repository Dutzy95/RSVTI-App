package com.rsvti.address;

import java.io.File;
import java.io.IOException;
import java.io.PrintStream;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.List;

import com.rsvti.address.view.AddEmployeesToRigController;
import com.rsvti.address.view.AddFirmController;
import com.rsvti.address.view.AddRigParameterController;
import com.rsvti.address.view.AddRigsToFirmController;
import com.rsvti.address.view.DueDateOverviewController;
import com.rsvti.address.view.EmployeeOverviewController;
import com.rsvti.address.view.FirmOverviewController;
import com.rsvti.address.view.MenuController;
import com.rsvti.address.view.RigOverviewController;
import com.rsvti.database.entities.Employee;
import com.rsvti.database.entities.Rig;
import com.rsvti.main.Constants;
import com.rsvti.main.Data;
import com.rsvti.main.Utils;

import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.control.TabPane.TabClosingPolicy;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;

public class JavaFxMain extends Application {

	private Stage primaryStage;
	private BorderPane rootLayout;
	private TabPane tabPane;
	private Stage addUpdateEmployeesToRigStage;
	private AddEmployeesToRigController addUpdateEmployeesToRigController;
	private AddRigsToFirmController addUpdateRigsToFirmController;
	private Stage addUpdateRigsToFirmStage;
	private AddFirmController addFirmController;
	private Tab addRigParameterTab;
	private DueDateOverviewController dueDateOverviewController;
	
	@Override
	public void start(Stage primaryStage) {
		this.primaryStage = primaryStage;
        this.primaryStage.setTitle("RSVTI App");
        
        initRootLayout();
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
            
            primaryStage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
	
	public void showFirmOverview() {
        try {
            // Load person overview.
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(JavaFxMain.class.getResource("view/FirmOverview.fxml"));
            AnchorPane personOverview = (AnchorPane) loader.load();

            // Set person overview into the center of root layout.
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
	        // Load the fxml file and create a new stage for the popup dialog.
	        FXMLLoader loader = new FXMLLoader();
	        loader.setLocation(JavaFxMain.class.getResource("view/RigOverview.fxml"));
	        AnchorPane rigOverview = (AnchorPane) loader.load();

	        // Set the person into the controller.
	        RigOverviewController controller = loader.getController();
	        controller.setRigList(rigList);
	        controller.setJavaFxMain(this);
	        
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
	        // Load the fxml file and create a new stage for the popup dialog.
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
	        controller.setJavaFxMain(this);

	    } catch (IOException e) {
	        e.printStackTrace();
	    }
	}
	
	public void addFirm() {
		try {
	        // Load the fxml file and create a new stage for the popup dialog.
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
	        // Load the fxml file and create a new stage for the popup dialog.
	        FXMLLoader loader = new FXMLLoader();
	        loader.setLocation(JavaFxMain.class.getResource("view/AddRigParameter.fxml"));
	        AnchorPane addRigParameter = (AnchorPane) loader.load();

	        addRigParameterTab = new Tab("Parametrii pentru utilaje");
            addRigParameterTab.setContent(addRigParameter);
            addRigParameterTab.setClosable(true);
            
            tabPane.setTabClosingPolicy(TabClosingPolicy.SELECTED_TAB);
            tabPane.getTabs().add(addRigParameterTab);
            tabPane.getSelectionModel().select(addRigParameterTab);
            
            AddRigParameterController addRigParameterController = loader.getController();
            addRigParameterController.setJavaFxMain(this);
	    } catch (IOException e) {
	        e.printStackTrace();
	    }
	}
	
	public void showAddUpdateRigsToFirm(Rig rig, boolean isUpdate, boolean isDueDateUpdate, String stageName) {
		try {
	        // Load the fxml file and create a new stage for the popup dialog.
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
	        Scene scene = new Scene(addUpdateRigsToFirm);
	        addUpdateRigsToFirmStage.setScene(scene);
            
            addUpdateRigsToFirmStage.showAndWait();
            
	    } catch (IOException e) {
	        e.printStackTrace();
	    }
	}
	
	public void showAddUpdateEmployeesToRig(Employee employee, boolean isUpdate, boolean isDueDateUpdate, String stageName) {
		try {
	        // Load the fxml file and create a new stage for the popup dialog.
	        FXMLLoader loader = new FXMLLoader();
	        loader.setLocation(JavaFxMain.class.getResource("view/AddEmployeesToRig.fxml"));
	        AnchorPane addUpdateEmployeesToRig = (AnchorPane) loader.load();
	        
	        AddEmployeesToRigController controller = loader.getController();
	        controller.setJavaFxMain(this);
	        controller.setFirmAndRigName(stageName);
	        controller.setIsDueDateUpdate(isDueDateUpdate);

	        addUpdateEmployeesToRigController = loader.getController();
            addUpdateEmployeesToRigController.setJavaFxMain(this);
            addUpdateEmployeesToRigController.setIsUpdate(isUpdate);
            
            if(employee != null) {
            	addUpdateEmployeesToRigController.showEmployeeDetails(employee);
            }
            
            addUpdateEmployeesToRigStage = new Stage();
	        addUpdateEmployeesToRigStage.setTitle(stageName);
	        addUpdateEmployeesToRigStage.initModality(Modality.WINDOW_MODAL);
	        addUpdateEmployeesToRigStage.initOwner(primaryStage);
	        Scene scene = new Scene(addUpdateEmployeesToRig);
	        addUpdateEmployeesToRigStage.setScene(scene);
            
            addUpdateEmployeesToRigStage.showAndWait();
            
            
	    } catch (IOException e) {
	        e.printStackTrace();
	    }
	}
	
	public void showDueDateOverview() {
		try {
	        // Load the fxml file and create a new stage for the popup dialog.
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
	
	public JavaFxMain() {
    }
	
	public Stage getPrimaryStage() {
        return primaryStage;
    }
	
	public TabPane getTabPane() {
		return tabPane;
	}
	
	public Stage getAddEmployeesToRigStage() {
		return addUpdateEmployeesToRigStage;
	}
	
	public AddEmployeesToRigController getAddEmployeesToRigController() {
		return addUpdateEmployeesToRigController;
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
//		Utils.setErrorLog();
		Data.populate();
		launch(args);
	}
}
