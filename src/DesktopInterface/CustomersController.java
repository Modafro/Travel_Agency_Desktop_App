package DesktopInterface;

import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;

import DesktopInterface.TravelExpertClasses.Agents;
import DesktopInterface.TravelExpertClasses.Customer;
import DesktopInterface.TravelExpertClasses.CustomerDB;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;

public class CustomersController {
    private Agents loggedAgent;

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private AnchorPane customers;

    @FXML
    private TableView<Customer> tvcustomers;

    @FXML
    private TableColumn<Customer, String> colCustFirstName;

    @FXML
    private TableColumn<Customer, String> colCustLastName;

    @FXML
    private TableColumn<Customer, String> colCustAddress;

    @FXML
    private TableColumn<Customer, String> colCustCIty;

    @FXML
    private TableColumn<Customer, String> colCustProv;

    @FXML
    private TableColumn<Customer, String> colCustPostal;

    @FXML
    private TableColumn<Customer, String> colCustCountry;

    @FXML
    private TableColumn<Customer, String> colCustHomePhone;

    @FXML
    private TableColumn<Customer, String> colCustBusPhone;

    @FXML
    private TableColumn<Customer, String> colCustEmail;

    @FXML
    private TextField txtSearch;

    @FXML
    private Pane crudcustomer;

    @FXML
    private TextField txtCustFirstName;

    @FXML
    private TextField txtCustLastName;

    @FXML
    private TextField txtCustAddress;

    @FXML
    private TextField txtCustEmail;

    @FXML
    private TextField txtCustCity;

    @FXML
    private TextField txtCustPostal;

    @FXML
    private ComboBox<String> cbProvince;

    @FXML
    private TextField txtCustHomePhone;

    @FXML
    private TextField txtCustBusPhone;

    @FXML
    private Button btnInsert;

    @FXML
    private Button btnUpdate;

    @FXML
    private Button btnDelete;

    private ObservableList<Customer> custData = FXCollections.observableArrayList();

    private ObservableList<String> provData = FXCollections.observableArrayList("AB",
            "BC","MB","NB","NL","NT","NS","NU","ON","PE","QC","SK","YT");

    @FXML
    void initialize() {

//        assert vwCustomers != null : "fx:id=\"vwCustomers\" was not injected: check your FXML file 'CustomersGUI.fxml'.";
//        assert tvCustomers != null : "fx:id=\"tvCustomers\" was not injected: check your FXML file 'CustomersGUI.fxml'.";
//        assert colCustFirstName != null : "fx:id=\"colCustFirstName\" was not injected: check your FXML file 'CustomersGUI.fxml'.";
//        assert colCustLastName != null : "fx:id=\"colCustLastName\" was not injected: check your FXML file 'CustomersGUI.fxml'.";
//        assert colCustAddress != null : "fx:id=\"colCustAddress\" was not injected: check your FXML file 'CustomersGUI.fxml'.";
//        assert colCustCIty != null : "fx:id=\"colCustCIty\" was not injected: check your FXML file 'CustomersGUI.fxml'.";
//        assert colCustProv != null : "fx:id=\"colCustProv\" was not injected: check your FXML file 'CustomersGUI.fxml'.";
//        assert colCustPostal != null : "fx:id=\"colCustPostal\" was not injected: check your FXML file 'CustomersGUI.fxml'.";
//        assert colCustCountry != null : "fx:id=\"colCustCountry\" was not injected: check your FXML file 'CustomersGUI.fxml'.";
//        assert colCustHomePhone != null : "fx:id=\"colCustHomePhone\" was not injected: check your FXML file 'CustomersGUI.fxml'.";
//        assert colCustBusPhone != null : "fx:id=\"colCustBusPhone\" was not injected: check your FXML file 'CustomersGUI.fxml'.";
//        assert colCustEmail != null : "fx:id=\"colCustEmail\" was not injected: check your FXML file 'CustomersGUI.fxml'.";
//        assert txtSearch != null : "fx:id=\"txtSearch\" was not injected: check your FXML file 'CustomersGUI.fxml'.";
        cbProvince.setItems(provData);
    }

    public void setAgentinCustomersGUI(Agents agent)
    {
       loggedAgent = agent;
       getCustomersByAgtID();
    }

    public void getCustomersByAgtID()
    {
        custData = CustomerDB.getCustomerTableView(loggedAgent, custData);

        try {
            colCustFirstName.setCellValueFactory(new PropertyValueFactory<Customer, String>("CustFirstName"));
            colCustLastName.setCellValueFactory(new PropertyValueFactory<Customer, String>("CustLastName"));
            colCustAddress.setCellValueFactory(new PropertyValueFactory<Customer, String>("CustAddress"));
            colCustCIty.setCellValueFactory(new PropertyValueFactory<Customer, String>("CustCity"));
            colCustProv.setCellValueFactory(new PropertyValueFactory<Customer, String>("CustProv"));
            colCustPostal.setCellValueFactory(new PropertyValueFactory<Customer, String>("CustPostal"));
            colCustCountry.setCellValueFactory(new PropertyValueFactory<Customer, String>("CustCountry"));
            colCustHomePhone.setCellValueFactory(new PropertyValueFactory<Customer, String>("CustHomePhone"));
            colCustBusPhone.setCellValueFactory(new PropertyValueFactory<Customer, String>("CustBusPhone"));
            colCustEmail.setCellValueFactory(new PropertyValueFactory<Customer, String>("CustEmail"));
            tvcustomers.setItems(custData);
        }
        catch (Exception e) {
            System.out.println(e);
        }
    }

    //method to verify user's input
    public void validateInput()
    {
        if(Validator.isEmailValid(txtCustEmail) && !Validator.isEmpty(txtCustEmail))
        {
            System.out.println("valid");
        }
        else
        {
            System.out.println("not valid");
        }
    }
}
