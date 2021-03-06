/**
 * Author: Mo Sagnia, Yosuke Saito, and James Sharpe
 * Date: April 2019
 * Objective: Create a class controller for interaction of SuppliersUI to the database
 */


package DesktopInterface;

import java.net.URL;
import java.util.ArrayList;
import java.util.Collection;
import java.util.ResourceBundle;

import DesktopInterface.TravelExpertClasses.*;
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIcon;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;

public class SuppliersController {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private AnchorPane suppliers;

    @FXML
    private Button btnAddProductToSupplier;

    @FXML
    private Button btnRemoveProductToSupplier;

    @FXML
    private ComboBox<Supplier> cmbSupplier;

    @FXML
    private ListView<ProductSupplier> lstProductBySupplier;

    @FXML
    private ListView<Product> lstProductToAdd;

    @FXML
    private TitledPane crudSuppliers;

    @FXML
    private Button btnRemoveSupplier;

    @FXML
    private Button btnEditSupplier;

    @FXML
    private TextField txtSupplier;

    @FXML
    private Button btnAddSupplier;

    @FXML
    private Label lblError;

    @FXML
    private FontAwesomeIcon imgInsert;

    @FXML
    private FontAwesomeIcon imgUpdate;

    @FXML
    private FontAwesomeIcon imgDelete;

    @FXML
    private Button btnSave;

    @FXML
    private FontAwesomeIcon imgSave;

    @FXML
    private Button btnCancel;

    @FXML
    private FontAwesomeIcon imgCancel;

    @FXML
    private Button btnConfirmRemove;


    @FXML
    private FontAwesomeIcon imgConfirmRemove;

    //string object used to know which button was clicked : Insert / Update / Delete
    private String crudBtnClicked = new String();

    @FXML
    void addProdToSupp(ActionEvent event) {

        int selectedProd = (lstProductToAdd.getSelectionModel().getSelectedItem().getProductId());
        addProductsToDB(selectedProd);
        lstProductBySupplier.setItems(getSuppsProds(selectedSuppId, "sup"));
        lstProductToAdd.setItems(addSuppsProds());
    }

    //change buttons visibility to only show "save" and "cancel" button
    public void insertBtnClicked()
    {
        crudBtnClicked = "insert";
        txtSupplier.setText("");
        setVisibilityButtons(false);
        btnSave.setVisible(true);
        imgSave.setVisible(true);
        cmbSupplier.setDisable(true);
    }

    //change buttons visibility to only show "save" and "cancel" button
    public void updateBtnClicked()
    {
        if (Validator.isEmpty(txtSupplier))
        {
            alert_error.setTitle("Supplier Invalid");
            alert_error.setHeaderText("No supplier selected");
            alert_error.setContentText("Please select a supplier to update.");
            alert_error.showAndWait();
        }
        else
        {
            crudBtnClicked = "update";
            setVisibilityButtons(false);
            btnSave.setVisible(true);
            imgSave.setVisible(true);
            cmbSupplier.setDisable(true);
        }

    }

    public void removeBtnClicked()
    {
        if (Validator.isEmpty(txtSupplier))
        {
            alert_error.setTitle("Supplier Invalid");
            alert_error.setHeaderText("No supplier selected");
            alert_error.setContentText("Please select a supplier to update.");
            alert_error.showAndWait();
        }
        else
        {
            crudBtnClicked = "remove";
            setVisibilityButtons(false);
            btnConfirmRemove.setVisible(true);
            imgConfirmRemove.setVisible(true);
            cmbSupplier.setDisable(true);
        }
    }

    //visibility of buttons
    public void setVisibilityButtons(boolean showCrud)
    {
        if (showCrud == true)
        {
            btnAddSupplier.setVisible(true);
            imgInsert.setVisible(true);
            btnEditSupplier.setVisible(true);
            imgUpdate.setVisible(true);
            btnRemoveSupplier.setVisible(true);
            imgDelete.setVisible(true);
            btnSave.setVisible(false);
            imgSave.setVisible(false);
            btnCancel.setVisible(false);
            imgCancel.setVisible(false);
            btnConfirmRemove.setVisible(false);
            imgConfirmRemove.setVisible(false);
            txtSupplier.setDisable(true);
            cmbSupplier.setDisable(false);
        }
        else if (showCrud == false)
        {
            btnAddSupplier.setVisible(false);
            imgInsert.setVisible(false);
            btnEditSupplier.setVisible(false);
            imgUpdate.setVisible(false);
            btnRemoveSupplier.setVisible(false);
            imgDelete.setVisible(false);
            btnSave.setVisible(true);
            imgSave.setVisible(true);
            btnCancel.setVisible(true);
            imgCancel.setVisible(true);
            txtSupplier.setDisable(false);

        }
    }

    //method to insert or delete package
    public void saveSupChanges()
    {
        System.out.println("method savePkgChanges called");
        //if insert button was clicked
        if(crudBtnClicked.equals("insert"))
        {
            addSupplier();
        }
        //if update button was clicked
        else
        {
            editSupplier();
        }
    }


    void addSupplier()
    {
        if (Validator.isEmpty(txtSupplier, lblError)){
            return;}

        String suppCompare = txtSupplier.getText().toUpperCase();
        boolean valid = true;
        for (Supplier s : supplierList){
            if (s.supName.toUpperCase().equals(suppCompare)) {
                alert_error.setTitle("Insert Status");
                alert_error.setHeaderText("Supplier was not added");
                alert_error.setContentText("Supplier was not added; supplier already exists.");
                alert_error.showAndWait();
                valid = false;
                break;
            }
        }
        if (valid){
            if (SupplierDB.AddSupplier(txtSupplier.getText().toUpperCase())){
                alert_info.setTitle("Insert Status");
                alert_info.setHeaderText("Supplier was added successfully.");
                alert_info.setContentText(suppCompare + " has successfully been added to the database.");
                alert_info.showAndWait();
            }
            else{
                alert_error.setTitle("Insert Status");
                alert_error.setHeaderText("Supplier was not inserted");
                alert_error.setContentText("An error occurred while trying to insert the supplier to the database. Please" +
                        " try again. If issue persists, contact your database administrator.");
                alert_error.showAndWait();
            }

            lstProductBySupplier.getItems().clear();
            lstProductToAdd.getItems().clear();
            cmbSupplier.getItems().clear();
            txtSupplier.clear();

            supplierList = SupplierDB.GetSuppliers();
            cmbSupplier.setItems(getSuppNames());

            setVisibilityButtons(true);
            cmbSupplier.getSelectionModel().selectFirst();
        }
    }

    //method to cancel any ongoing changes
    public void cancelSupChanges()
    {
        cmbSupplier.setDisable(false);
        setVisibilityButtons(true);
    }

    void editSupplier() {
        if (Validator.isEmpty(txtSupplier, lblError)){
            return;
        }
        if (selectedSupplier != null){
            String newSuppName = txtSupplier.getText().toUpperCase();
            boolean valid = false;
            for (Supplier s: supplierList){
                if (s.supName.toUpperCase().equals(newSuppName)) {
                    alert_error.setTitle("Update Status");
                    alert_error.setHeaderText("Supplier was not updated");
                    alert_error.setContentText("Supplier was not updated; supplier already exists.");
                    alert_error.showAndWait();
                    valid = false;
                    break;
                }
                else {valid = true;}
            }
            if (valid){
                if (SupplierDB.UpdateSupplier(selectedSuppId, newSuppName)){
                    alert_info.setTitle("Update Status");
                    alert_info.setHeaderText("Supplier was updated successfully.");
                    alert_info.setContentText(newSuppName + " has successfully been updated to the database.");
                    alert_info.showAndWait();
                }
            }else {
                alert_error.setTitle("Update Status");
                alert_error.setHeaderText("Supplier was not updated");
                alert_error.setContentText("An error occurred while trying to update the supplier to the database. Please" +
                        " try again. If issue persists, contact your database administrator.");
                alert_error.showAndWait();
            }
        }
        lstProductBySupplier.getItems().clear();
        lstProductToAdd.getItems().clear();
        cmbSupplier.getItems().clear();
        txtSupplier.clear();

        supplierList = SupplierDB.GetSuppliers();
        cmbSupplier.setItems(getSuppNames());
        cmbSupplier.getSelectionModel().selectFirst();
        setVisibilityButtons(true);
    }

    @FXML
    void removeProdFromSupp(ActionEvent event) {
        int selectedProdSuppID = lstProductBySupplier.getSelectionModel().getSelectedItem().getProductSupplierId();
        System.out.println(selectedProdSuppID);
        removeProductToDB(selectedProdSuppID);
    }

    @FXML
    void removeSupplier(ActionEvent event) {
        String deletedSupp = txtSupplier.getText();
        if (SupplierDB.DeleteSupplier(selectedSuppId)){
            alert_info.setTitle("Delete Status");
            alert_info.setHeaderText("Supplier was deleted successfully.");
            alert_info.setContentText(deletedSupp + " has successfully been deleted from the database.");
            alert_info.showAndWait();
        }else{
            alert_error.setTitle("Delete Status");
            alert_error.setHeaderText("Supplier was not deleted");
            alert_error.setContentText("An error occurred while trying to deleted the supplier from the database. Please" +
                    " try again. If issue persists, contact your database administrator.");
            alert_error.showAndWait();
        }

        lstProductBySupplier.getItems().clear();
        lstProductToAdd.getItems().clear();
        cmbSupplier.getItems().clear();
        txtSupplier.clear();

        supplierList = SupplierDB.GetSuppliers();
        cmbSupplier.setItems(getSuppNames());

        setVisibilityButtons(true);
    }

    //select supplier from combo box and set list boxes to specific products
    @FXML
    void selectSupplier(ActionEvent event) {
        if(cmbSupplier.getItems().isEmpty())
            return;
        selectedSupplier = cmbSupplier.getValue();
        selectedSuppId = cmbSupplier.getValue().supplierId;
        supplierText = cmbSupplier.getValue().supName;
        txtSupplier.setText(supplierText);
        lstProductBySupplier.setItems(getSuppsProds(selectedSuppId, "sup"));
        lstProductToAdd.setItems(addSuppsProds());
    }

    @FXML
    void initialize() {

        productsToRemove = new ArrayList<Product>();
        crudSuppliers.setExpanded(false);
        supplierList = SupplierDB.GetSuppliers();
        cmbSupplier.setItems(getSuppNames());
        productList = ProductDB.GetProducts();
        txtSupplier.isEditable();
        lblError.setVisible(false);
        btnSave.setVisible(false);
        imgSave.setVisible(false);
        btnCancel.setVisible(false);
        imgCancel.setVisible(false);
        btnConfirmRemove.setVisible(false);
        imgConfirmRemove.setVisible(false);
        setVisibilityButtons(true);
    }

    private Alert alert_info = new Alert(Alert.AlertType.INFORMATION);
    private Alert alert_error = new Alert(Alert.AlertType.ERROR);

    // instantiate object lists and vars
    private ArrayList<Supplier> supplierList;
    private ArrayList<Product> productList;
    private ArrayList<ProductSupplier> specProdSuppList;
    private ArrayList<Product> productsToRemove;
    private ArrayList<Product> specProducts;
    private int selectedSuppId;
    private String supplierText;
    Supplier selectedSupplier;

    //gets suppliers list and returns names to populate the combobox
    private ObservableList<Supplier> getSuppNames() {
        ArrayList<Supplier> suppNames = new ArrayList<>();
        for (Supplier s: supplierList){
            suppNames.add(s);
        }
        ObservableList<Supplier> options = FXCollections.observableArrayList(suppNames);
        return options;
    }

    //Method to get all products from a specific supplier and return a list to populate a combobox
    private ObservableList<ProductSupplier> getSuppsProds(int id, String s)
    {
        specProdSuppList = ProductSupplierDB.GetProSupList(id, s);
        productsToRemove.clear();
        for (ProductSupplier ps: specProdSuppList){
            productsToRemove.add(new Product(ps.getProductId(), ps.getProductName()));
        }
        ObservableList<ProductSupplier> options = FXCollections.observableArrayList(specProdSuppList);
        return options;
    }

    // gets products not already with the supplier
    private ObservableList<Product> addSuppsProds()
    {
        lstProductToAdd.getItems().clear();
        specProducts = new ArrayList<>(productList);
        specProducts.removeAll(productsToRemove);

        ObservableList<Product> options = FXCollections.observableArrayList(specProducts);
        return options;
    }

    //links the product to the supplier
    private String addProductsToDB(int prodID){
        String dbSuccess = "";
        try
        {
            ProductSupplierDB.LinkProductSuppliers(selectedSuppId, prodID);
            dbSuccess = "Link Successful";
        } catch (Exception e) {
            e.printStackTrace();
        }
        return dbSuccess;
    }

    //remove selected product from supplier
    private void removeProductToDB(int prosuppID){
        try
        {
            ProductSupplierDB.deleteProdSupp(prosuppID);
        } catch (Exception e){
            e.printStackTrace();
        }
        lstProductBySupplier.getItems().clear();
        lstProductToAdd.getItems().clear();
        lstProductBySupplier.setItems(getSuppsProds(selectedSuppId, "sup"));
        lstProductToAdd.setItems(addSuppsProds());
    }
}
