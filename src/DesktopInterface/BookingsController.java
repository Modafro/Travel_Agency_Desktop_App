package DesktopInterface;

import DesktopInterface.TravelExpertClasses.Agents;
import DesktopInterface.TravelExpertClasses.Customer;
import DesktopInterface.TravelExpertClasses.CustomerDB;
import DesktopInterface.TravelExpertClasses.Package;
import DesktopInterface.TravelExpertClasses.PackageDB;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;

import java.util.Date;

public class BookingsController {

    private Agents loggedAgent;

    @FXML
    private AnchorPane bookings;

    @FXML
    private TextField txtSearchCustomer;

    @FXML
    private TextField txtSearchPackage;

    @FXML
    private TableView<Customer> tvcustomers;

    @FXML
    private TableColumn<Customer, String> colCustFirstName;

    @FXML
    private TableColumn<Customer, String> colCustLastName;

    @FXML
    private TableColumn<Customer, String> colCustHomePhone;

    @FXML
    private TableColumn<Customer, String> colCustEmail;

    @FXML
    private TableColumn<Customer, String> colCustAddress;

    @FXML
    private TableColumn<Customer, Integer> colCustId;

    @FXML
    private TableView<Package> tvpackages;

    @FXML
    private TableColumn<DesktopInterface.TravelExpertClasses.Package, String> colPkgName;

    @FXML
    private TableColumn<DesktopInterface.TravelExpertClasses.Package, Integer> colPkgId;

    @FXML
    private TableColumn<DesktopInterface.TravelExpertClasses.Package, String> colPkgDesc;

    @FXML
    private TableColumn<Package, Date> colPkgStartDate;

    @FXML
    private TableColumn<Package, Date> colPkgEndDate;

    @FXML
    private TableColumn<Package, Double> colPkgBasePrice;

    @FXML
    private TableColumn<DesktopInterface.TravelExpertClasses.Package, Integer> col;

    @FXML
    private TextField txtNumTravelers;

    @FXML
    private Button btnSummary;

    @FXML
    private Pane pnsummary;

    @FXML
    private Button btnSendInvoice;

    @FXML
    private Label lblCustName;

    @FXML
    private Label lblCustHomePhone;

    @FXML
    private Label lblCustEmail;

    @FXML
    private Label lblCustAddress;

    @FXML
    private Label lblPkgName;

    @FXML
    private Label lblPkgDesc;

    @FXML
    private Label lblPkgStartDate;

    @FXML
    private Label lblPkgEndDate;

    @FXML
    private Label lblNumTravelers;

    @FXML
    private Label lblBasePrice;

    @FXML
    private Label lblTotalPrice;

    @FXML
    private Button btnCancel;

    @FXML
    private TextField txtCustId;

    @FXML
    private TextField txtPkgId;

    private ObservableList<Customer> custData = FXCollections.observableArrayList();

    private ObservableList<DesktopInterface.TravelExpertClasses.Package> pkgData = FXCollections.observableArrayList();

    private Alert alert_info = new Alert(Alert.AlertType.INFORMATION);

    private Alert alert_error = new Alert(Alert.AlertType.ERROR);


    @FXML
    void initialize() {
        //hide vacation package information
        pnsummary.setVisible(false);
    }

    //method to get the agent object from Agent Controller
    public void setAgentinBookingsGui(Agents agent)
    {
        loggedAgent = agent;
        getCustomersByAgtID(loggedAgent, custData);
        getPackage(pkgData);
    }

    //method to be called when populating customer table
    public void getCustomersByAgtID(Agents loggedAgent, ObservableList<Customer> custData)
    {
        custData = CustomerDB.getSpecificCustomerTableViewByAgtId(loggedAgent, custData);

        try {
            colCustId.setCellValueFactory(new PropertyValueFactory<Customer, Integer>("CustomerId"));
            colCustFirstName.setCellValueFactory(new PropertyValueFactory<Customer, String>("CustFirstName"));
            colCustFirstName.setCellValueFactory(new PropertyValueFactory<Customer, String>("CustFirstName"));
            colCustLastName.setCellValueFactory(new PropertyValueFactory<Customer, String>("CustLastName"));
            colCustHomePhone.setCellValueFactory(new PropertyValueFactory<Customer, String>("CustHomePhone"));
            colCustAddress.setCellValueFactory(new PropertyValueFactory<Customer, String>("CustAddress"));
            colCustEmail.setCellValueFactory(new PropertyValueFactory<Customer, String>("CustEmail"));
            tvcustomers.setItems(custData);
        }
        catch (Exception e) {
            System.out.println(e);
        }
    }

    //method to be called when populating package table
    public void getPackage(ObservableList<DesktopInterface.TravelExpertClasses.Package> pkgData)
    {
        pkgData = PackageDB.getPackageNameTableView(pkgData);

        try{
            colPkgId.setCellValueFactory(new PropertyValueFactory<DesktopInterface.TravelExpertClasses.Package, Integer>("PackagedId"));
            colPkgName.setCellValueFactory(new PropertyValueFactory<DesktopInterface.TravelExpertClasses.Package, String>("PkgName"));
            colPkgStartDate.setCellValueFactory(new PropertyValueFactory<DesktopInterface.TravelExpertClasses.Package, Date>("PkgStartDate"));
            colPkgEndDate.setCellValueFactory(new PropertyValueFactory<DesktopInterface.TravelExpertClasses.Package, Date>("PkgEndDate"));
            colPkgDesc.setCellValueFactory(new PropertyValueFactory<DesktopInterface.TravelExpertClasses.Package, String>("PkgDesc"));
            colPkgBasePrice.setCellValueFactory(new PropertyValueFactory<DesktopInterface.TravelExpertClasses.Package, Double>("PkgBasePrice"));
            tvpackages.setItems(pkgData);
        }
        catch (Exception e) {
             System.out.println(e);
            }
    }

    //method to search customer on key released
    public void searchCustomer()
    {
        custData.clear();
        custData = CustomerDB.CustomerSearchResultSpecific(custData, txtSearchCustomer.getText(),loggedAgent);
    }

    //method to search package on key released
    public void searchPackage()
    {
        pkgData.clear();
        pkgData = PackageDB.PackageNameSearchResult(pkgData, txtSearchPackage.getText());
    }

    //method to refresh customer table
    public void refreshCustTable()
    {
        custData.clear();
        getCustomersByAgtID(loggedAgent, custData);
    }

    //method to refersh package table
    public void refreshPkgTable()
    {
        pkgData.clear();
        getPackage(pkgData);
    }

    //show summary on btnSummary click
    public void showVacationSummary()
    {
        pnsummary.setVisible(true);
    }


}
