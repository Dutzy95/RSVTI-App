package com.rsvti.address;

import java.io.IOException;
import java.util.List;

import com.rsvti.address.view.EmployeeOverviewController;
import com.rsvti.address.view.FirmOverviewController;
import com.rsvti.address.view.MenuController;
import com.rsvti.address.view.RigOverviewController;
import com.rsvti.database.entities.Employee;
import com.rsvti.database.entities.Firm;
import com.rsvti.database.entities.Rig;
import com.rsvti.database.services.DBServices;
import com.rsvti.main.Data;

import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.ObservableMap;
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
	private ObservableList<Firm> firmData = FXCollections.observableArrayList();
	private TabPane tabPane;
	
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
	        AnchorPane page = (AnchorPane) loader.load();

	        // Create the dialog Stage.
	        Stage dialogStage = new Stage();
	        dialogStage.setTitle("Personal deservent");
	        dialogStage.initModality(Modality.WINDOW_MODAL);
	        dialogStage.initOwner(primaryStage);
	        Scene scene = new Scene(page);
	        dialogStage.setScene(scene);

	        // Set the person into the controller.
	        EmployeeOverviewController controller = loader.getController();
	        controller.setDialogStage(dialogStage);
	        controller.setEmployeeList(employeeList);
	        controller.setJavaFxMain(this);
	        // Show the dialog and wait until the user closes it
	        dialogStage.showAndWait();

	    } catch (IOException e) {
	        e.printStackTrace();
	    }
	}
	
	public JavaFxMain() {
        // Add some sample data
        List<Firm> firms = DBServices.getAllFirms();
        for(Firm index : firms) {
        	firmData.add(index);
        }
        
    }
	
	public ObservableList<Firm> getFirmData() {
        return firmData;
    }
	
	public Stage getPrimaryStage() {
        return primaryStage;
    }
	
	public TabPane getTabPane() {
		return tabPane;
	}

	public static void main(String[] args) {
		Data.populate();
		launch(args);
	}
}
