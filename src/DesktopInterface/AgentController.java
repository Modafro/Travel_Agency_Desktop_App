package DesktopInterface;

import java.net.URL;
import java.util.ResourceBundle;

import DesktopInterface.TravelExpertClasses.Agents;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;

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
//        assert lblCurrentDirectory != null : "fx:id=\"lblCurrentDirectory\" was not injected: check your FXML file 'AgentGUI.fxml'.";
//        assert btnHome != null : "fx:id=\"btnHome\" was not injected: check your FXML file 'AgentGUI.fxml'.";
//        assert btnCustomers != null : "fx:id=\"btnCustomers\" was not injected: check your FXML file 'AgentGUI.fxml'.";
//        assert btnPackages != null : "fx:id=\"btnPackages\" was not injected: check your FXML file 'AgentGUI.fxml'.";
//        assert btnSuppliers != null : "fx:id=\"btnSuppliers\" was not injected: check your FXML file 'AgentGUI.fxml'.";
//        assert btnProducts != null : "fx:id=\"btnProducts\" was not injected: check your FXML file 'AgentGUI.fxml'.";
//        assert lblWelcome != null : "fx:id=\"lblWelcome\" was not injected: check your FXML file 'AgentGUI.fxml'.";
//        assert btnLogout != null : "fx:id=\"btnLogout\" was not injected: check your FXML file 'AgentGUI.fxml'.";
//        assert vwCustomers != null : "fx:id=\"vwCustomers\" was not injected: check your FXML file 'AgentGUI.fxml'.";
//        assert vwPackages != null : "fx:id=\"vwPackages\" was not injected: check your FXML file 'AgentGUI.fxml'.";
//        assert vwSuppliers != null : "fx:id=\"vwSuppliers\" was not injected: check your FXML file 'AgentGUI.fxml'.";
//        assert vwProducts != null : "fx:id=\"vwProducts\" was not injected: check your FXML file 'AgentGUI.fxml'.";
//        assert vwSettings != null : "fx:id=\"vwSettings\" was not injected: check your FXML file 'AgentGUI.fxml'.";
//        assert vwHome != null : "fx:id=\"vwHome\" was not injected: check your FXML file 'AgentGUI.fxml'.";
        home.toFront();
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
        }
        else if(event.getSource() == btnCustomers)
        {
            lblCurrentDirectory.setText("Customers");
            customers.toFront();
        }
        else if(event.getSource() == btnPackages)
        {
            lblCurrentDirectory.setText("Packages");
            packagesController.updateTable();
            packages.toFront();
        }
        else if(event.getSource() == btnSuppliers)
        {
            lblCurrentDirectory.setText("Suppliers");
            suppliers.toFront();
        }
        else if(event.getSource() == btnProducts)
        {
            lblCurrentDirectory.setText("Products");
            products.toFront();
        }
        else if(event.getSource() == btnBookings)
        {
            lblCurrentDirectory.setText("Bookings");
            bookingsController.refreshPkgTable();
            bookings.toFront();
        }
        else if (event.getSource() == btnLogout)
        {
            System.exit(0);
        }
    }
}
