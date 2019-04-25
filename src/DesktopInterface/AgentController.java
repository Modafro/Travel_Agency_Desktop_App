package DesktopInterface;

import java.net.URL;
import java.util.ResourceBundle;

import DesktopInterface.TravelExpertClasses.Agents;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TitledPane;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.CornerRadii;
import javafx.scene.paint.Color;

public class AgentController {

    @FXML
    private CustomersController customersController;

    @FXML
    private PackagesController packagesController;

    @FXML
    private BookingsController bookingsController;

    private Agents loggedAgent;

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private Label lblCurrentDirectory;


    @FXML
    private Button btnHome;

    @FXML
    private Button btnCustomers;

    @FXML
    private Button btnPackages;

    @FXML
    private Button btnSuppliers;

    @FXML
    private Button btnProducts;

    @FXML
    private Button btnBookings;

    @FXML
    private Label lblWelcome;

    @FXML
    private Button btnLogout;

    @FXML
    private AnchorPane customers;

    @FXML
    private AnchorPane packages;

    @FXML
    private AnchorPane suppliers;

    @FXML
    private AnchorPane products;

    @FXML
    private AnchorPane bookings;

    @FXML
    private AnchorPane home;

    @FXML
    void initialize() {
        home.toFront();
//        customersController.keepButtonFocusedonAnchorPaneClicked(btnCustomers);
//        packagesController.keepButtonFocusedonAnchorPaneClicked(btnPackages);
//        bookingsController.keepButtonFocusedonAnchorPaneClicked(btnBookings);
        btnHome.setStyle("-fx-background-color: #1981E9");
        hoverBackgroundColor(btnHome, false);
        btnCustomers.setStyle("-fx-background-color: #C5EEEE");
        btnPackages.setStyle("-fx-background-color: #C5EEEE");
        btnProducts.setStyle("-fx-background-color: #C5EEEE");
        btnSuppliers.setStyle("-fx-background-color: #C5EEEE");
        btnBookings.setStyle("-fx-background-color: #C5EEEE");
        btnProducts.setStyle("-fx-background-color: #C5EEEE");
        btnLogout.setStyle("-fx-background-color: #C5EEEE");
        //hoverBackgroundColor(btnHome, true);
        hoverBackgroundColor(btnCustomers, true);
        hoverBackgroundColor(btnPackages,true);
        hoverBackgroundColor(btnSuppliers, true);
        hoverBackgroundColor(btnProducts, true);
        hoverBackgroundColor(btnBookings, true);
        hoverBackgroundColor(btnLogout, true);
    }

    //method to be called in LoginGUIController. Method will set current agents object (loggedAgent) to the object agents
    //instantiated in LoginGUIController
    public void setAgentinAgentGUI(Agents agent)
    {
        loggedAgent = agent;
        lblWelcome.setText("Welcome back " +loggedAgent.getAgtFirstName()+ " " + loggedAgent.getAgtLastName());
        customersController.setAgentinCustomersGUI(loggedAgent);
        bookingsController.setAgentinBookingsGui(loggedAgent);

    }

    @FXML
    private void handleClicks(ActionEvent event)
    {
        if(event.getSource() == btnHome)
        {
            lblCurrentDirectory.setText("Home");
            home.toFront();
            btnCustomers.setStyle("-fx-background-color: #C5EEEE");
            hoverBackgroundColor(btnCustomers,true);
            btnHome.setStyle("-fx-background-color: #1981E9");
            btnHome.setOnMouseEntered(e -> btnHome.setStyle("-fx-background-color: #1981E9"));
            btnHome.setOnMouseExited(e -> btnHome.setStyle("-fx-background-color: #1981E9"));
            btnPackages.setStyle("-fx-background-color: #C5EEEE");
            hoverBackgroundColor(btnPackages,true);
            btnProducts.setStyle("-fx-background-color: #C5EEEE");
            hoverBackgroundColor(btnProducts,true);
            btnSuppliers.setStyle("-fx-background-color: #C5EEEE");
            hoverBackgroundColor(btnSuppliers,true);
            btnBookings.setStyle("-fx-background-color: #C5EEEE");
            hoverBackgroundColor(btnBookings, true);
        }
        else if(event.getSource() == btnCustomers)
        {
            lblCurrentDirectory.setText("Customers");
            customers.toFront();
            btnCustomers.setStyle("-fx-background-color: #1981E9");
            btnCustomers.setOnMouseEntered(e -> btnCustomers.setStyle("-fx-background-color: #1981E9"));
            btnCustomers.setOnMouseExited(e -> btnCustomers.setStyle("-fx-background-color: #1981E9"));
            btnHome.setStyle("-fx-background-color: #C5EEEE");
            hoverBackgroundColor(btnHome,true);
            btnPackages.setStyle("-fx-background-color: #C5EEEE");
            hoverBackgroundColor(btnPackages,true);
            btnProducts.setStyle("-fx-background-color: #C5EEEE");
            hoverBackgroundColor(btnProducts,true);
            btnSuppliers.setStyle("-fx-background-color: #C5EEEE");
            hoverBackgroundColor(btnSuppliers,true);
            btnBookings.setStyle("-fx-background-color: #C5EEEE");
            hoverBackgroundColor(btnBookings,true);
        }
        else if(event.getSource() == btnPackages)
        {
            lblCurrentDirectory.setText("Packages");
            packagesController.updateTable();
            packages.toFront();
            btnCustomers.setStyle("-fx-background-color: #C5EEEE");
            hoverBackgroundColor(btnCustomers,true);
            btnHome.setStyle("-fx-background-color: #C5EEEE");
            hoverBackgroundColor(btnHome,true);
            btnPackages.setStyle("-fx-background-color: #1981E9");
            btnPackages.setOnMouseEntered(e -> btnPackages.setStyle("-fx-background-color: #1981E9"));
            btnPackages.setOnMouseExited(e -> btnPackages.setStyle("-fx-background-color: #1981E9"));
            btnProducts.setStyle("-fx-background-color: #C5EEEE");
            hoverBackgroundColor(btnProducts,true);
            btnSuppliers.setStyle("-fx-background-color: #C5EEEE");
            hoverBackgroundColor(btnSuppliers,true);
            btnBookings.setStyle("-fx-background-color: #C5EEEE");
            hoverBackgroundColor(btnBookings,true);
        }
        else if(event.getSource() == btnSuppliers)
        {
            lblCurrentDirectory.setText("Suppliers");
            suppliers.toFront();
            btnCustomers.setStyle("-fx-background-color: #C5EEEE");
            hoverBackgroundColor(btnCustomers,true);
            btnHome.setStyle("-fx-background-color: #C5EEEE");
            hoverBackgroundColor(btnHome,true);
            btnPackages.setStyle("-fx-background-color: #C5EEEE");
            hoverBackgroundColor(btnPackages,true);
            btnProducts.setStyle("-fx-background-color: #C5EEEE");
            hoverBackgroundColor(btnProducts,true);
            btnSuppliers.setStyle("-fx-background-color: #1981E9");
            btnSuppliers.setOnMouseEntered(e -> btnSuppliers.setStyle("-fx-background-color: #1981E9"));
            btnSuppliers.setOnMouseExited(e -> btnSuppliers.setStyle("-fx-background-color: #1981E9"));
            btnBookings.setStyle("-fx-background-color: #C5EEEE");
            hoverBackgroundColor(btnBookings,true);
        }
        else if(event.getSource() == btnProducts)
        {
            lblCurrentDirectory.setText("Products");
            products.toFront();
            btnCustomers.setStyle("-fx-background-color: #C5EEEE");
            hoverBackgroundColor(btnCustomers,true);
            btnHome.setStyle("-fx-background-color: #C5EEEE");
            hoverBackgroundColor(btnHome,true);
            btnPackages.setStyle("-fx-background-color: #C5EEEE");
            hoverBackgroundColor(btnPackages,true);
            btnProducts.setStyle("-fx-background-color: #1981E9");
            btnProducts.setOnMouseEntered(e -> btnProducts.setStyle("-fx-background-color: #1981E9"));
            btnProducts.setOnMouseExited(e -> btnProducts.setStyle("-fx-background-color: #1981E9"));
            btnSuppliers.setStyle("-fx-background-color: #C5EEEE");
            hoverBackgroundColor(btnSuppliers,true);
            btnBookings.setStyle("-fx-background-color: #C5EEEE");
            hoverBackgroundColor(btnBookings,true);
        }
        else if(event.getSource() == btnBookings)
        {
            lblCurrentDirectory.setText("Bookings");
            bookingsController.refreshCustTable();
            bookingsController.refreshPkgTable();
            bookings.toFront();
            btnCustomers.setStyle("-fx-background-color: #C5EEEE");
            hoverBackgroundColor(btnCustomers,true);
            btnHome.setStyle("-fx-background-color: #C5EEEE");
            hoverBackgroundColor(btnHome,true);
            btnPackages.setStyle("-fx-background-color: #C5EEEE");
            hoverBackgroundColor(btnPackages,true);
            btnProducts.setStyle("-fx-background-color: #C5EEEE");
            hoverBackgroundColor(btnProducts,true);
            btnSuppliers.setStyle("-fx-background-color: #C5EEEE");
            hoverBackgroundColor(btnSuppliers,true);
            btnBookings.setStyle("-fx-background-color: #1981E9");
            btnBookings.setOnMouseEntered(e -> btnBookings.setStyle("-fx-background-color: #1981E9"));
            btnBookings.setOnMouseExited(e -> btnBookings.setStyle("-fx-background-color: #1981E9"));
        }
        else if (event.getSource() == btnLogout)
        {
            System.exit(0);
        }
    }

    //make background of buttons a certain colour based on mouse entered/exited
    public void hoverBackgroundColor(Button button, boolean hover)
    {
        if(hover==true)
        {
            button.setOnMouseEntered(e -> button.setStyle("-fx-background-color: #1981E9"));
            button.setOnMouseExited(e -> button.setStyle("-fx-background-color: #C5EEEE"));
        }
        else
        {
            button.setOnMouseEntered(e -> button.setStyle("-fx-background-color: #1981E9"));
            button.setOnMouseExited(e -> button.setStyle("-fx-background-color: #1981E9"));
        }

        //button.setOnMouseClicked(e -> button.setStyle("-fx-background-color: #1981E9"));
    }
}
