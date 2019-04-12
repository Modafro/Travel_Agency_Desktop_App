package DesktopInterface;

import DesktopInterface.TravelExpertClasses.*;
import DesktopInterface.TravelExpertClasses.Package;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

public class BookingsController {

    private Agents loggedAgent;

    private Customer cust;

    private Package pkg;

    private DateFormat df = new SimpleDateFormat("MMM dd, yyyy");

    //today's date in sql format
    private Date today = new Date();
    private java.sql.Date sqlToday = new java.sql.Date(today.getTime());

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

        //set textfields values from the selected customer and package in their tables with a mouse click or arrow key released
        setCustTextfieldsFromTableOnMouseClicked();
        setCustTextfieldsFromTableOnArrowKeyReleased();
        setPkgTextfieldsFromTableOnMouseClicked();
        setPkgTextfieldsFromTableOnArrowKeyReleased();
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
        //total cost
        double totalPrice;
        totalPrice = pkg.getPkgBasePrice() * (Double.parseDouble(txtNumTravelers.getText()));
        lblNumTravelers.setText(txtNumTravelers.getText());
        lblTotalPrice.setText(Double.toString(totalPrice));

        pnsummary.setVisible(true);
    }

    //set textfields values from the selected customer in the table with a mouse click
    private void setCustTextfieldsFromTableOnMouseClicked()
    {
        tvcustomers.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                setCustLabelFieldsFromTable();
            }
        });
    }

    //set textfields values from the selected customer in the table with an arrow (up or down) key released
    private void setCustTextfieldsFromTableOnArrowKeyReleased()
    {
        tvcustomers.setOnKeyReleased(new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent event) {
                if(event.getCode() == KeyCode.UP || event.getCode() == KeyCode.DOWN)
                {
                    setCustLabelFieldsFromTable();
                }
            }
        });
    }

    //set textfields values from the selected package in the table with a mouse click
    private void setPkgTextfieldsFromTableOnMouseClicked()
    {
        tvpackages.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                setPkgLabelFieldsFromTable();
            }
        });
    }

    //set textfields values from the selected package in the table with an arrow (up or down) key released
    private void setPkgTextfieldsFromTableOnArrowKeyReleased()
    {
        tvpackages.setOnKeyReleased(new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent event) {
                if(event.getCode() == KeyCode.UP || event.getCode() == KeyCode.DOWN)
                {
                    setPkgLabelFieldsFromTable();
                }
            }
        });
    }

    //set values of labelfields in summary pane (pnsummary) to corresponding values of selected customer and package in respective tables
    public void setCustLabelFieldsFromTable()
    {
        //labels related to customer information
        cust = tvcustomers.getItems().get(tvcustomers.getSelectionModel().getSelectedIndex());
        txtCustId.setText(Integer.toString(cust.getCustomerId()));
        lblCustName.setText(cust.getCustFirstName() + " "+ cust.getCustLastName());
        lblCustHomePhone.setText(cust.getCustHomePhone());
        lblCustEmail.setText(cust.getCustEmail());
        lblCustAddress.setText(cust.getCustAddress());
    }

    //set values of labelfields in summary pane (pnsummary) to corresponding values of selected package from package table
    public void setPkgLabelFieldsFromTable() {

        pkg = tvpackages.getItems().get(tvpackages.getSelectionModel().getSelectedIndex());
        txtPkgId.setText(Integer.toString(pkg.getPackageId()));
        lblPkgName.setText(pkg.getPkgName());
        lblPkgDesc.setText(pkg.getPkgDesc());
        lblPkgStartDate.setText(df.format(pkg.getPkgStartDate()));
        lblPkgEndDate.setText(df.format(pkg.getPkgEndDate()));
        lblBasePrice.setText(Double.toString(pkg.getPkgBasePrice()));

        //number of travelers
        lblNumTravelers.setText(txtNumTravelers.getText());
    }

    //cancel ongoing booking process
    public void cancelBooking()
    {
        //hide summary info
        pnsummary.setVisible(false);

        //for customer info
        txtCustId.setText("");
        lblCustName.setText("");
        lblCustHomePhone.setText("");
        lblCustEmail.setText("");
        lblCustAddress.setText("");

        //for package info
        txtPkgId.setText("");
        lblPkgName.setText("");
        lblPkgDesc.setText("");
        lblPkgStartDate.setText("");
        lblPkgEndDate.setText("");
        lblBasePrice.setText("");
        lblTotalPrice.setText("");

        //number of travelers
        txtNumTravelers.setText("");
        lblNumTravelers.setText("");

        //refresh tables
        refreshPkgTable();
        refreshCustTable();
    }

    //create new booking and send email to customer
    public void sendInvoice()
    {
        //create new booking
        Bookings newBooking = new Bookings(sqlToday, Float.parseFloat(txtNumTravelers.getText()), cust.getCustomerId(),"L",pkg.getPackageId());

        boolean addBkgSuccessful = BookingsDB.addBookingForCustId(newBooking, cust, pkg);
        if(addBkgSuccessful)
        {
            //show dialog box
            alert_info.setTitle("Insert Status");
            alert_info.setHeaderText("Booking added successfully.");
            alert_info.setContentText(pkg.getPkgName() + " has been successfully booked for "+ cust.getCustFirstName()
                            +" "+ cust.getCustLastName() +".");
            alert_info.showAndWait();

            //set visibility
            //cancelCustChanges();

            //refresh table view
            refreshCustTable();
            refreshPkgTable();
        }
        else
        {
            alert_error.setTitle("Insert Status");
            alert_error.setHeaderText("Booking was not added");
            alert_error.setContentText("An error occurred while trying to add a new booking for customer. Please" +
                    " try again. If issue persists, contact your database administrator.");
            alert_error.showAndWait();
        }
    }
}
