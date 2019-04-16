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
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;

import javax.tools.Tool;

public class CustomersController {
    private Agents loggedAgent;

    @FXML
    private BookingsController bookingsController;

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
    private TableColumn<Customer, Integer> colCustId;

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
    private TextField txtCustomerId;

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
    private FontAwesomeIcon imgRefresh;

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

    //string object used to know which button was clicked : Insert / Update / Delete
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

        //set textfields values from the selected customer in the table with a mouse click or arrow key released
        setCustTextfieldsFromTableOnMouseClicked();
        setCustTextfieldsFromTableOnArrowKeyReleased();

        //tooltips
        Tooltip.install(imgRefresh,new Tooltip("Refresh table"));
    }

    //method to get the agent object from Agent Controller
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
            colCustId.setCellValueFactory(new PropertyValueFactory<Customer, Integer>("CustomerId"));
            colCustFirstName.setCellValueFactory(new PropertyValueFactory<Customer, String>("CustFirstName"));
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
            insertCustomer();
            //System.out.println("insert method");
        }
        //if update button was clicked
        else
        {
            updateCustomer();
        }
    }

    //method to search the database on enter key pressed
    public void searchDatabase()
    {
        custData.clear();
        custData = CustomerDB.CustomerSearchResult(custData, txtSearch.getText(), loggedAgent);
    }

    //method to delete customer
    public void deleteCust()
    {
        Customer deleteCustomer = new Customer(Integer.parseInt(txtCustomerId.getText()), txtCustFirstName.getText(), txtCustLastName.getText(),txtCustAddress.getText(),
            txtCustCity.getText(), cbProvince.getValue(),txtCustPostal.getText(), "Canada",
            txtCustHomePhone.getText(),txtCustBusPhone.getText(), txtCustEmail.getText(),loggedAgent.getAgentId());

        boolean deleteCustSuccessful = CustomerDB.deleteCustomer(deleteCustomer);
        if(deleteCustSuccessful)
        {
            //show dialog box
            alert_info.setTitle("Delete Status");
            alert_info.setHeaderText("Customer deleted successfully.");
            alert_info.setContentText(deleteCustomer.getCustFirstName() + " "+ deleteCustomer.getCustLastName()+
                    " has successfully been deleted.");
            alert_info.showAndWait();

            //set visibility
            cancelCustChanges();

            //refresh table view
            refreshCustTable();

            //refresh table in BookingsController GUI
            bookingsController.refreshCustTable();
            }
        else
        {
            alert_error.setTitle("Delete Status");
            alert_error.setHeaderText("Customer was not deleted");
            alert_error.setContentText("An error occurred while trying to delete customer to database. Please" +
                    " try again. If issue persists, contact your database administrator.");
            alert_error.showAndWait();
        }

        //System.out.println("delete method");
    }

    //method to cancel any ongoing changes
    public void cancelCustChanges()
    {
        pncustomerfields.setDisable(true);
        setVisibilityButtons(true);
        tvcustomers.setDisable(false);
        clearTexfieldDataAndLabels();
        cbProvince.setPromptText("Prov");
    }

    //method to clear textfields and remove error labels
    private void clearTexfieldDataAndLabels() {
        txtCustFirstName.setText("");
        txtCustLastName.setText("");
        txtCustAddress.setText("");
        txtCustCity.setText("");
        txtCustPostal.setText("");
        cbProvince.getSelectionModel().clearSelection();
        cbProvince.setPromptText("Prov");
        txtCustEmail.setText("");
        txtCustHomePhone.setText("");
        txtCustBusPhone.setText("");
        txtCustomerId.setText("");

        //hide labels
        lblCustFirstNameError.setVisible(false);
        lblCustLastNameError.setVisible(false);
        lblCustAddressError.setVisible(false);
        lblCustPostalError.setVisible(false);
        lblCustCityError.setVisible(false);
        lblCustProvError.setVisible(false);
        lblCustEmailError.setVisible(false);
        lblCustHomePhoneError.setVisible(false);
        lblCustBusPhoneError.setVisible(false);
    }

    //method to update customer
    public void updateCustomer()
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
            Customer updateCustomer = new Customer(Integer.parseInt(txtCustomerId.getText()), txtCustFirstName.getText(), txtCustLastName.getText(),txtCustAddress.getText(),
                    txtCustCity.getText(), cbProvince.getValue(),txtCustPostal.getText(), "Canada",
                    txtCustHomePhone.getText(),txtCustBusPhone.getText(), txtCustEmail.getText(),loggedAgent.getAgentId());

            //update customer in DB
            boolean UpdateCustSuccessful = CustomerDB.updateCustomer(updateCustomer);
            if(UpdateCustSuccessful)
            {
                //show dialog box
                alert_info.setTitle("Update Status");
                alert_info.setHeaderText("Customer updated successfully.");
                alert_info.setContentText(updateCustomer.getCustFirstName() + " "+ updateCustomer.getCustLastName()+
                        " has successfully been updated.");
                alert_info.showAndWait();

                //set visibility
                cancelCustChanges();

                //refresh table view
                refreshCustTable();
            }
            else
            {
                alert_error.setTitle("Update Status");
                alert_error.setHeaderText("Customer was not updated");
                alert_error.setContentText("An error occurred while trying to update customer to database. Please" +
                        " try again. If issue persists, contact your database administrator.");
                alert_error.showAndWait();
            }
        }
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
                   cancelCustChanges();

                   //refresh table view
                   refreshCustTable();

               }
               else
               {
                   alert_error.setTitle("Insert Status");
                   alert_error.setHeaderText("Customer was not added");
                   alert_error.setContentText("An error occurred while trying to add customer to database. Please" +
                           " try again. If issue persists, contact your database administrator.");
                   alert_error.showAndWait();
               }
            }
            else
            {
                alert_error.setTitle("Insert Status");
                alert_error.setHeaderText("Customer was not added");
                alert_error.setContentText("A customer with that email address already exists in the database.");
                alert_error.showAndWait();
            }
            System.out.println("valid");
        }
        else
        {
            //will show error labels for user to rectify missing or invalid info entered
            //System.out.println("can't get to invalid inputs");
        }
    }

    //method to refresh table view
    public void refreshCustTable()
    {
        custData.clear();
        getCustomersByAgtID(loggedAgent, custData);
    }

    //change buttons visibility to only show "save" and "cancel" button
    public void insertBtnClicked()
    {
        crudBtnClicked = "insert";
        pncustomerfields.setDisable(false);
        setVisibilityButtons(false);
        tvcustomers.setDisable(true);
        clearTexfieldDataAndLabels();
        cbProvince.setPromptText("Prov");
    }

    //change buttons visibility to only show "save" and "cancel" button
    public void updateBtnClicked()
    {
        if(txtCustFirstName.getText().isEmpty())
        {
            alert_error.setTitle("Customer Invalid");
            alert_error.setHeaderText("No customer selected");
            alert_error.setContentText("Please select a customer from the table to update.");
            alert_error.showAndWait();
        }
        else
        {
            crudBtnClicked = "update";
            pncustomerfields.setDisable(false);
            setVisibilityButtons(false);
            tvcustomers.setDisable(true);
        }
    }

    //change buttons visibility to only show "confirm" and "cancel" button
    public void deleteBtnClicked()
    {
        if(txtCustFirstName.getText().isEmpty())
        {

            alert_error.setTitle("Customer Invalid");
            alert_error.setHeaderText("No customer selected");
            alert_error.setContentText("Please select a customer from the table to delete.");
            alert_error.showAndWait();
        }
        else
        {
            crudBtnClicked = "delete";
            pncustomerfields.setDisable(true);
            setVisibilityButtons(false);
            btnSave.setVisible(false);
            imgSave.setVisible(false);
            btnConfirmDelete.setVisible(true);
            imgConfirmDelete.setVisible(true);
            tvcustomers.setDisable(true);
        }
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

    //set textfields values from the selected customer in the table with a mouse click
    private void setCustTextfieldsFromTableOnMouseClicked()
    {
        tvcustomers.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                setCustTextfieldsFromTable();
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
                    setCustTextfieldsFromTable();
                }
            }
        });
    }

    //set values of textfields to corresponding values of selected customer in table
    private void setCustTextfieldsFromTable()
    {
        Customer cust = tvcustomers.getItems().get(tvcustomers.getSelectionModel().getSelectedIndex());
        txtCustomerId.setText(Integer.toString(cust.getCustomerId()));
        txtCustFirstName.setText(cust.getCustFirstName());
        txtCustLastName.setText(cust.getCustLastName());
        txtCustAddress.setText(cust.getCustAddress());
        txtCustCity.setText(cust.getCustCity());
        txtCustPostal.setText(cust.getCustPostal());
        cbProvince.getSelectionModel().select(cust.getCustProv());
        txtCustEmail.setText(cust.getCustEmail());
        txtCustHomePhone.setText(cust.getCustHomePhone());
        txtCustBusPhone.setText(cust.getCustBusPhone());
    }
}
