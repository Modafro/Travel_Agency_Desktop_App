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
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.StackPane;

public class CustomersGUIController {
    private Agents loggedAgent;

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private AnchorPane vwCustomers;

    @FXML
    private TableView<Customer> tvCustomers;

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

    private ObservableList<Customer> custData = FXCollections.observableArrayList();

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

//        loggedAgent = agent;
        getCustomers();


    }

    public void setAgentinCustomersGUI(Agents agent)
    {
        //loggedAgent = agent;
        //getCustomers();
    }

    public void getCustomers()
    {
        Connection conn = TravelExpertsDB.getConnection();

        String sql = "select CustFirstName, CustLastName, CustAddress, CustCity, " +
                "CustProv, CustPostal, CustCountry, CustHomePhone, CustBusPhone, CustEmail from Customers where " +
                "AgentId=1";

        try
        {
            PreparedStatement stmt = conn.prepareStatement(sql);
            //stmt.setInt(1, loggedAgent.getAgentId());
            //int agtId = loggedAgent.getAgencyId();
            //System.out.println(agtId);

            ResultSet res = stmt.executeQuery();

            while(res.next())
            {
                custData.add(new Customer(res.getString(1),
                        res.getString(2),
                        res.getString(3),
                        res.getString(4),
                        res.getString(5),
                        res.getString(6),
                        res.getString(7),
                        res.getString(8),
                        res.getString(9),
                        res.getString(10)
                ));
            }
            //System.out.println(custData);
            //data = CustomerDB.getCustometTableView(loggedAgent, data);
            //set column values to the observableList of Customers

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
                tvCustomers.setItems(custData);
            }
            catch (Exception e) {
                System.out.println(e);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }


    }


}
