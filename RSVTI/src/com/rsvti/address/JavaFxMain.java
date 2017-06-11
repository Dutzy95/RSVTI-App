package com.rsvti.address;

import java.io.IOException;
import java.util.List;

import com.rsvti.address.view.AddEmployeesToRigController;
import com.rsvti.address.view.AddFirmController;
import com.rsvti.address.view.AddRigsToFirmController;
import com.rsvti.address.view.EmployeeOverviewController;
import com.rsvti.address.view.FirmOverviewController;
import com.rsvti.address.view.MenuController;
import com.rsvti.address.view.RigOverviewController;
import com.rsvti.database.entities.Employee;
import com.rsvti.database.entities.Rig;
import com.rsvti.main.Data;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.control.TabPane.TabClosingPolicy;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class JavaFxMain extends Application {

	private Stage primaryStage;
	private BorderPane rootLayout;
	private TabPane tabPane;
	private Stage addEmployeesToRigStage;
	private AddRigsToFirmController addRigsToFirmController;
	private Stage addRigsToFirmStage;
	private AddFirmController addFirmController;
	private Tab addFirmTab;
	
	@Override
	public void start(Stage primaryStage) {
		this.primaryStage = primaryStage;
        this.primaryStage.setTitle("RSVTI App");

        initRootLayout();

        showFirmOverview();
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

	        addFirmTab = new Tab("Adaugă firmă");
            addFirmTab.setContent(addFirm);
            addFirmTab.setClosable(true);
            
            tabPane.setTabClosingPolicy(TabClosingPolicy.SELECTED_TAB);
            tabPane.getTabs().add(addFirmTab);
            tabPane.getSelectionModel().select(addFirmTab);
            
            addFirmController = loader.getController();
            addFirmController.setJavaFxMain(this);
	    } catch (IOException e) {
	        e.printStackTrace();
	    }
	}
	
	public void showAddRigsToFirm(Rig rig) {
		try {
	        // Load the fxml file and create a new stage for the popup dialog.
	        FXMLLoader loader = new FXMLLoader();
	        loader.setLocation(JavaFxMain.class.getResource("view/AddRigsToFirm.fxml"));
	        AnchorPane addRigsToFirm = (AnchorPane) loader.load();
	        
	        AddRigsToFirmController controller = loader.getController();
	        controller.setJavaFxMain(this);

	        if(rig != null) {
            	controller.showRigDetails(rig);
            }
	        
	        addRigsToFirmController = loader.getController();
	        addRigsToFirmController.setJavaFxMain(this);
            
	        addRigsToFirmStage = new Stage();
	        addRigsToFirmStage.setTitle("Adaugă utilaje");
	        addRigsToFirmStage.initModality(Modality.WINDOW_MODAL);
	        addRigsToFirmStage.initOwner(primaryStage);
	        Scene scene = new Scene(addRigsToFirm);
	        addRigsToFirmStage.setScene(scene);
            
            addRigsToFirmStage.showAndWait();
            
            
	    } catch (IOException e) {
	        e.printStackTrace();
	    }
	}
	
	public void showAddEmployeesToRig(Employee employee) {
		try {
	        // Load the fxml file and create a new stage for the popup dialog.
	        FXMLLoader loader = new FXMLLoader();
	        loader.setLocation(JavaFxMain.class.getResource("view/AddEmployeesToRig.fxml"));
	        AnchorPane addEmployeesToRig = (AnchorPane) loader.load();

	        AddEmployeesToRigController controller = loader.getController();
            controller.setJavaFxMain(this);
            
            if(employee != null) {
            	controller.showEmployeeDetails(employee);
            }
            
            addEmployeesToRigStage = new Stage();
	        addEmployeesToRigStage.setTitle("Adaugă personal");
	        addEmployeesToRigStage.initModality(Modality.WINDOW_MODAL);
	        addEmployeesToRigStage.initOwner(primaryStage);
	        Scene scene = new Scene(addEmployeesToRig);
	        addEmployeesToRigStage.setScene(scene);
            
            addEmployeesToRigStage.showAndWait();
            
            
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
		return addEmployeesToRigStage;
	}
	
	public AddRigsToFirmController getAddRigsToFirmController() {
		return addRigsToFirmController;
	}
	
	public Stage getAddRigsToFirmStage() {
		return addRigsToFirmStage;
	}
	
	public AddFirmController getAddFirmController() {
		return addFirmController;
	}
	
	public Tab getAddFirmTab() {
		return addFirmTab;
	}

	public static void main(String[] args) {
		Data.populate();
		launch(args);
	}
}
