package DesktopInterface;

import java.net.URL;
import java.util.ResourceBundle;

import DesktopInterface.TravelExpertClasses.Agents;
import DesktopInterface.TravelExpertClasses.Customer;
import DesktopInterface.TravelExpertClasses.CustomerDB;
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIcon;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
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
    private TitledPane crudCustomers;

    @FXML
    private Pane pncustomerfields;

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

    @FXML
    private Button btnSave;

    @FXML
    private Button btnCancel;

    @FXML
    private Button btnConfirmDelete;

    @FXML
    private FontAwesomeIcon imgInsert;

    @FXML
    private FontAwesomeIcon imgUpdate;

    @FXML
    private FontAwesomeIcon imgDelete;



    @FXML
    private FontAwesomeIcon imgConfirmDelete;

    @FXML
    private FontAwesomeIcon imgSave;

    @FXML
    private FontAwesomeIcon imgCancel;

    @FXML
    private Label lblCustFirstNameError;

    @FXML
    private Label lblCustAddressError;

    @FXML
    private Label lblCustLastNameError;

    @FXML
    private Label lblCustCityError;

    @FXML
    private Label lblCustPostalError;

    @FXML
    private Label lblCustProvError;

    @FXML
    private Label lblCustEmailError;

    @FXML
    private Label lblCustHomePhoneError;

    @FXML
    private Label lblCustBusPhoneError;

    private ObservableList<Customer> custData = FXCollections.observableArrayList();

    private ObservableList<String> provData = FXCollections.observableArrayList("AB",
            "BC","MB","NB","NL","NT","NS","NU","ON","PE","QC","SK","YT");

    private Alert alert_info = new Alert(Alert.AlertType.INFORMATION);

    private Alert alert_error = new Alert(Alert.AlertType.ERROR);

    //string used to know which button was clicked : Insert / Update / Delete
    private String crudBtnClicked = new String();

    @FXML
    void initialize() {

        //hide CRUD operations (texfields)
        crudCustomers.setExpanded(false);

        //load combo box for province
        cbProvince.setItems(provData);

        //set visibility of error labels
        lblCustFirstNameError.setVisible(false);
        lblCustLastNameError.setVisible(false);
        lblCustAddressError.setVisible(false);
        lblCustPostalError.setVisible(false);
        lblCustCityError.setVisible(false);
        lblCustProvError.setVisible(false);
        lblCustEmailError.setVisible(false);
        lblCustHomePhoneError.setVisible(false);
        lblCustBusPhoneError.setVisible(false);

        //disable customer fields on load
        pncustomerfields.setDisable(true);

        //set visibility of buttons
        setVisibilityButtons(true);
    }

    public void setAgentinCustomersGUI(Agents agent)
    {
        loggedAgent = agent;
        getCustomersByAgtID(loggedAgent, custData);
    }

    //method to be called when populating customer table
    public void getCustomersByAgtID(Agents loggedAgent, ObservableList<Customer> custData)
    {
        custData = CustomerDB.getCustomerTableViewByAgtId(loggedAgent, custData);

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

    //method to insert or update customer
    public void saveCustChanges()
    {
        //if insert button was clicked
        if(crudBtnClicked=="insert")
        {
            //insertCustomer();
            System.out.println("insert method");
        }
        else
        {
            updateCustomer();
        }
    }

    //method to delete customer
    public void deleteCust()
    {
        pncustomerfields.setDisable(true);
        setVisibilityButtons(true);
        System.out.println("delete method");
    }

    //method to cancel any ongoing changes
    public void cancelCustChanges()
    {
        pncustomerfields.setDisable(true);
        setVisibilityButtons(true);
        txtCustFirstName.setText("");
        txtCustLastName.setText("");
        txtCustAddress.setText("");
        txtCustCity.setText("");
        txtCustPostal.setText("");
        cbProvince.getSelectionModel().clearSelection();
        txtCustEmail.setText("");
        txtCustHomePhone.setText("");
        txtCustBusPhone.setText("");
    }

    //method to update customer
    public void updateCustomer()
    {
        System.out.println("update method");
    }

    //method to insert customer
    public void insertCustomer()
    {
        //verify inputs
        if(!Validator.isEmpty(txtCustFirstName, lblCustFirstNameError) && Validator.isNameValid(txtCustFirstName, lblCustFirstNameError) &&
                !Validator.isEmpty(txtCustLastName, lblCustLastNameError) && Validator.isNameValid(txtCustLastName, lblCustLastNameError) &&
                !Validator.isEmpty(txtCustEmail, lblCustEmailError) && Validator.isEmailValid(txtCustEmail, lblCustEmailError) &&
                !Validator.isEmpty(txtCustAddress, lblCustAddressError) &&
                !Validator.isEmpty(txtCustCity, lblCustCityError) && Validator.isNameValid(txtCustCity, lblCustCityError) &&
                !Validator.isEmpty(txtCustPostal, lblCustPostalError) && Validator.isPostalValid(txtCustPostal, lblCustPostalError) &&
                Validator.isProvinceValid(cbProvince,lblCustProvError) &&
                !Validator.isEmpty(txtCustHomePhone, lblCustHomePhoneError) && Validator.isPhoneValid(txtCustHomePhone, lblCustHomePhoneError) &&
                Validator.isPhoneValid(txtCustBusPhone,lblCustBusPhoneError))
        {
//            String custFirstName, String custLastName, String custAddress, String custCity,
//                String custProv, String custPostal, String custCountry, String custHomePhone, String custBusPhone,
//                String custEmail
            Customer newCustomer = new Customer(txtCustFirstName.getText(), txtCustLastName.getText(),txtCustAddress.getText(),
                                    txtCustCity.getText(), cbProvince.getValue(),txtCustPostal.getText(), "Canada",
                                    txtCustHomePhone.getText(),txtCustBusPhone.getText(), txtCustEmail.getText(),loggedAgent.getAgentId());

            //verify that customer does not exist
            if (!CustomerDB.customerExist(newCustomer))
            {
                //add new customer to DB
               boolean addCustSucceful = CustomerDB.addCustomerForAgtId(loggedAgent, newCustomer);
               if(addCustSucceful)
               {
                   //show dialog box
                   alert_info.setTitle("Insert Status");
                   alert_info.setHeaderText("Customer added successfully.");
                   alert_info.setContentText(newCustomer.getCustFirstName() + " "+ newCustomer.getCustLastName()+
                           " has successfully been added to your customer base.");
                   alert_info.showAndWait();

                   //set visibility
                   pncustomerfields.setDisable(true);
                   setVisibilityButtons(true);
                   crudCustomers.setExpanded(false);

                   //refresh table view
                   getCustomersByAgtID(loggedAgent, custData);
               }
               else
               {
                   alert_error.setTitle("Insert Status");
                   alert_error.setHeaderText("Customer was not added");
                   alert_error.setContentText("An error occurred while trying to add customer to database. Please" +
                           " try again. If issue persists, contact your database administrator");
                   alert_error.showAndWait();
               }
            }
            else
            {
                alert_error.setTitle("Insert Status");
                alert_error.setHeaderText("Customer was not added");
                alert_error.setContentText(newCustomer.getCustFirstName() + " "+ newCustomer.getCustLastName()+
                        " already exists in the database.");
                alert_error.showAndWait();
            }
            System.out.println("valid");
        }
        else
        {
            //will show error labels for user to rectify missing or invalid info entered
            System.out.println("can't get to ");
        }
    }

    public void insertClicked()
    {
        crudBtnClicked = "insert";
        pncustomerfields.setDisable(false);
        setVisibilityButtons(false);
    }

    public void updateClicked()
    {
        crudBtnClicked = "update";
        pncustomerfields.setDisable(false);
        setVisibilityButtons(false);
    }

    public void deleteClicked()
    {
        crudBtnClicked = "delete";
        pncustomerfields.setDisable(false);
        setVisibilityButtons(false);
        btnSave.setVisible(false);
        imgSave.setVisible(false);
        btnConfirmDelete.setVisible(true);
        imgConfirmDelete.setVisible(true);
    }

    //set visibility of buttons (and corresponding images)
    public void setVisibilityButtons(boolean showCrud)
    {
        if (showCrud == true)
        {
            btnInsert.setVisible(true);
            imgInsert.setVisible(true);
            btnUpdate.setVisible(true);
            imgUpdate.setVisible(true);
            btnDelete.setVisible(true);
            imgDelete.setVisible(true);
            btnSave.setVisible(false);
            imgSave.setVisible(false);
            btnCancel.setVisible(false);
            imgCancel.setVisible(false);
            btnConfirmDelete.setVisible(false);
            imgConfirmDelete.setVisible(false);
        }
        else if (showCrud == false)
        {
            btnInsert.setVisible(false);
            imgInsert.setVisible(false);
            btnUpdate.setVisible(false);
            imgUpdate.setVisible(false);
            btnDelete.setVisible(false);
            imgDelete.setVisible(false);
            btnSave.setVisible(true);
            imgSave.setVisible(true);
            btnCancel.setVisible(true);
            imgCancel.setVisible(true);
        }
    }
}
