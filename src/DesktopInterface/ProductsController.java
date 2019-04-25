package DesktopInterface;

import DesktopInterface.TravelExpertClasses.Product;
import DesktopInterface.TravelExpertClasses.ProductDB;
import DesktopInterface.TravelExpertClasses.Supplier;
import DesktopInterface.TravelExpertClasses.SupplierDB;
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIcon;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;

import java.util.ArrayList;

public class ProductsController {

    @FXML
    private AnchorPane products;

    @FXML
    private ListView<Product> lvProducts;

    @FXML
    private TextField txtProduct;

    @FXML
    private Button btnAddProduct;

    @FXML
    private Button btnConfirm;

    @FXML
    private Button btnUpdate;

    @FXML
    private Button btnCancel;

    @FXML
    private Button btnDelete;

    @FXML
    private Label lblError;

    @FXML
    private Button btnSave;

    @FXML
    private Button btnSaveUpdate;

    @FXML
    private TitledPane tvCrud;

    @FXML
    private FontAwesomeIcon icPlus;

    @FXML
    private FontAwesomeIcon icSave;

    @FXML
    private FontAwesomeIcon icUpdate;

    @FXML
    private FontAwesomeIcon icCancel;

    @FXML
    private FontAwesomeIcon icDelete;

    @FXML
    private FontAwesomeIcon icTrashConfirm;

    //instantiate objects and variables and functions
    private ArrayList<Product> productList;

    String selectedProduct = "";
    int selectedProductId;
    boolean insert = false;

    private Alert alert_info = new Alert(Alert.AlertType.INFORMATION);
    private Alert alert_error = new Alert(Alert.AlertType.ERROR);

    private ObservableList<Product> allProducts()
    {
        lvProducts.getItems().clear();
        ObservableList<Product> options = FXCollections.observableArrayList(productList);
        return options;
    }

    private void resetCrud (){
        txtProduct.clear();
        txtProduct.setDisable(true);

        btnDelete.setDisable(true);
        icDelete.setDisable(true);

        //crud button enable
        btnAddProduct.setVisible(true);
        btnUpdate.setVisible(true);
        btnDelete.setVisible(true);

        //confirm disable
        btnSave.setVisible(false);
        btnSaveUpdate.setVisible(false);
        btnCancel.setVisible(false);
        btnConfirm.setVisible(false);

        //icon crud visible
        icPlus.setVisible(true);
        icUpdate.setVisible(true);
        icDelete.setVisible(true);

        //confirm icon disable
        icSave.setVisible(false);
        icTrashConfirm.setVisible(false);
        icCancel.setVisible(false);

        insert = false;
    }

    @FXML
    void initialize(){

        lblError.setVisible(false);
        tvCrud.setExpanded(false);
        txtProduct.setDisable(true);

        resetCrud();

        productList = ProductDB.GetProducts();
        lvProducts.setItems(allProducts());
    }


    @FXML
    void clickCancel(ActionEvent event) {
        resetCrud();
        txtProduct.setText(selectedProduct);
        lblError.setVisible(false);
        lvProducts.setDisable(false);
    }

    @FXML
    void clickConfirm(ActionEvent event) {
        if (Validator.isEmpty(txtProduct, lblError)){
            return;
        }
        if (ProductDB.DeleteProduct(selectedProductId)){
            alert_info.setTitle("Delete Status");
            alert_info.setHeaderText("Product was deleted successfully.");
            alert_info.setContentText(selectedProduct + " has successfully been deleted from the database.");
            alert_info.showAndWait();
        }else{
            alert_error.setTitle("Delete Status");
            alert_error.setHeaderText("Supplier was not deleted");
            alert_error.setContentText("An error occurred while trying to deleted the product from the database. Please" +
                    " try again. If issue persists, contact your database administrator.");
            alert_error.showAndWait();
        }
        resetCrud();

        productList = ProductDB.GetProducts();
        lvProducts.setItems(allProducts());
    }

    @FXML
    void clickDelete(ActionEvent event) {
        //crud button disable
        btnAddProduct.setVisible(false);
        btnUpdate.setVisible(false);
        btnDelete.setVisible(false);

        //confirm disable
        btnSaveUpdate.setVisible(false);

        //confirm btn enable
        btnCancel.setVisible(true);
        btnConfirm.setVisible(true);

        //confirm icon enable
        icCancel.setVisible(true);
        icTrashConfirm.setVisible(true);

        //icon crud disable
        icPlus.setVisible(false);
        icUpdate.setVisible(false);
        icDelete.setVisible(false);
        icSave.setVisible(false);
    }

    @FXML
    void insertClick(ActionEvent event) {

        insert = true;
        txtProduct.setDisable(false);
        txtProduct.setText("");
        lvProducts.setDisable(true);

        //crud button disable
        btnAddProduct.setVisible(false);
        btnUpdate.setVisible(false);
        btnDelete.setVisible(false);

        //confirm disable
        btnSave.setVisible(true);
        btnCancel.setVisible(true);

        //confirm icon enable
        icSave.setVisible(true);
        icCancel.setVisible(true);

        //icon crud disable
        icPlus.setVisible(false);
        icUpdate.setVisible(false);
        icDelete.setVisible(false);

    }

    @FXML
    void saveProd(ActionEvent event) {
        if (Validator.isEmpty(txtProduct, lblError)){
            return;
        }
        String ProdtoAdd = txtProduct.getText();
        boolean valid = true;
        for (Product p : productList){
            if (p.getProdName().equals(ProdtoAdd)) {
                alert_error.setTitle("Insert Status");
                alert_error.setHeaderText("Product was not added");
                alert_error.setContentText("Product was not added; Product already exists.");
                alert_error.showAndWait();
                valid = false;
                break;
            }
        }
        if (valid){
            if (ProductDB.AddProduct(txtProduct.getText())){
                alert_info.setTitle("Insert Status");
                alert_info.setHeaderText("Product was added successfully.");
                alert_info.setContentText(ProdtoAdd + " has successfully been added to the database.");
                alert_info.showAndWait();
            }
            else{
                alert_error.setTitle("Insert Status");
                alert_error.setHeaderText("Product was not inserted");
                alert_error.setContentText("An error occurred while trying to insert the product to the database. Please" +
                        " try again. If issue persists, contact your database administrator.");
                alert_error.showAndWait();
            }

            productList = ProductDB.GetProducts();
            lvProducts.setDisable(false);
            lvProducts.setItems(allProducts());

            resetCrud();
        }
    }

    @FXML
    void saveUpdate(ActionEvent event) {
        if (Validator.isEmpty(txtProduct, lblError)){
            return;
        }
        String newProdName = txtProduct.getText();
        boolean valid = false;
        for (Product p: productList){
            if (p.getProdName().equals(newProdName)) {
                alert_error.setTitle("Update Status");
                alert_error.setHeaderText("Product was not updated");
                alert_error.setContentText("Product was not updated; product already exists.");
                alert_error.showAndWait();
                valid = false;
                break;
            }
            else {valid = true;}
        }
        if (valid){
            if (ProductDB.UpdatePackage(selectedProductId, newProdName)){
                alert_info.setTitle("Update Status");
                alert_info.setHeaderText("Product was updated successfully.");
                alert_info.setContentText(newProdName + " has successfully been updated to the database.");
                alert_info.showAndWait();
            }
        }else {
            alert_error.setTitle("Update Status");
            alert_error.setHeaderText("Product was not updated");
            alert_error.setContentText("An error occurred while trying to update the product to the database. Please" +
                    " try again. If issue persists, contact your database administrator.");
            alert_error.showAndWait();
        }

        productList = ProductDB.GetProducts();
        lvProducts.setItems(allProducts());
        resetCrud();
    }

    @FXML
    void updateClick(ActionEvent event) {

        txtProduct.setDisable(false);

        //crud button disable
        btnAddProduct.setVisible(false);
        btnUpdate.setVisible(false);
        btnDelete.setVisible(false);

        //confirm disable
        btnSaveUpdate.setVisible(true);
        btnCancel.setVisible(true);

        //confirm icon enable
        icSave.setVisible(true);
        icCancel.setVisible(true);

        //icon crud disable
        icPlus.setVisible(false);
        icUpdate.setVisible(false);
        icDelete.setVisible(false);
    }

    @FXML
    void selectKeyProd(KeyEvent event) {
        selectedProduct = lvProducts.getSelectionModel().getSelectedItem().getProdName();
        selectedProductId = lvProducts.getSelectionModel().getSelectedItem().getProductId();
        btnDelete.setDisable(false);
        icDelete.setDisable(false);
        txtProduct.setText(selectedProduct);
    }

    @FXML
    void selectProd(MouseEvent event) {
        selectedProduct = lvProducts.getSelectionModel().getSelectedItem().getProdName();
        selectedProductId = lvProducts.getSelectionModel().getSelectedItem().getProductId();
        btnDelete.setDisable(false);
        icDelete.setDisable(false);
        txtProduct.setText(selectedProduct);
    }

    @FXML
    void cancelClickAws(MouseEvent event) {
        resetCrud();
        txtProduct.setText(selectedProduct);
        lblError.setVisible(false);
    }

    @FXML
    void deleteClickAws(MouseEvent event) {
        //crud button disable
        btnAddProduct.setVisible(false);
        btnUpdate.setVisible(false);
        btnDelete.setVisible(false);

        //confirm disable
        btnSaveUpdate.setVisible(false);

        //confirm btn enable
        btnCancel.setVisible(true);
        btnConfirm.setVisible(true);

        //confirm icon enable
        icCancel.setVisible(true);
        icTrashConfirm.setVisible(true);

        //icon crud disable
        icPlus.setVisible(false);
        icUpdate.setVisible(false);
        icDelete.setVisible(false);
        icSave.setVisible(false);
    }

    @FXML
    void plusClickAws(MouseEvent event) {
        txtProduct.setDisable(false);
        txtProduct.setText("");
        lvProducts.setDisable(true);

        //crud button disable
        btnAddProduct.setVisible(false);
        btnUpdate.setVisible(false);
        btnDelete.setVisible(false);

        //confirm disable
        btnSave.setVisible(true);
        btnCancel.setVisible(true);

        //confirm icon enable
        icSave.setVisible(true);
        icCancel.setVisible(true);

        //icon crud disable
        icPlus.setVisible(false);
        icUpdate.setVisible(false);
        icDelete.setVisible(false);
    }

    @FXML
    void saveClickAws(MouseEvent event) {
        if (insert){
            if (Validator.isEmpty(txtProduct, lblError)){
                return;
            }
            String ProdtoAdd = txtProduct.getText();
            boolean valid = true;
            for (Product p : productList){
                if (p.getProdName().equals(ProdtoAdd)) {
                    alert_error.setTitle("Insert Status");
                    alert_error.setHeaderText("Product was not added");
                    alert_error.setContentText("Product was not added; Product already exists.");
                    alert_error.showAndWait();
                    valid = false;
                    break;
                }
            }
            if (valid){
                if (ProductDB.AddProduct(txtProduct.getText())){
                    alert_info.setTitle("Insert Status");
                    alert_info.setHeaderText("Product was added successfully.");
                    alert_info.setContentText(ProdtoAdd + " has successfully been added to the database.");
                    alert_info.showAndWait();
                }
                else{
                    alert_error.setTitle("Insert Status");
                    alert_error.setHeaderText("Product was not inserted");
                    alert_error.setContentText("An error occurred while trying to insert the product to the database. Please" +
                            " try again. If issue persists, contact your database administrator.");
                    alert_error.showAndWait();
                }

                productList = ProductDB.GetProducts();
                lvProducts.setDisable(false);
                lvProducts.setItems(allProducts());

                resetCrud();
            }
        }else{
            if (Validator.isEmpty(txtProduct, lblError)){
                return;
            }
            String newProdName = txtProduct.getText();
            boolean valid = false;
            for (Product p: productList){
                if (p.getProdName().equals(newProdName)) {
                    alert_error.setTitle("Update Status");
                    alert_error.setHeaderText("Product was not updated");
                    alert_error.setContentText("Product was not updated; product already exists.");
                    alert_error.showAndWait();
                    valid = false;
                    break;
                }
                else {valid = true;}
            }
            if (valid){
                if (ProductDB.UpdatePackage(selectedProductId, newProdName)){
                    alert_info.setTitle("Update Status");
                    alert_info.setHeaderText("Product was updated successfully.");
                    alert_info.setContentText(newProdName + " has successfully been updated to the database.");
                    alert_info.showAndWait();
                }
            }else {
                alert_error.setTitle("Update Status");
                alert_error.setHeaderText("Product was not updated");
                alert_error.setContentText("An error occurred while trying to update the product to the database. Please" +
                        " try again. If issue persists, contact your database administrator.");
                alert_error.showAndWait();
            }

            productList = ProductDB.GetProducts();
            lvProducts.setItems(allProducts());
            lvProducts.setDisable(false);
            resetCrud();
        }
    }

    @FXML
    void updateClickAws(MouseEvent event) {
        txtProduct.setDisable(false);

        //crud button disable
        btnAddProduct.setVisible(false);
        btnUpdate.setVisible(false);
        btnDelete.setVisible(false);

        //confirm disable
        btnSaveUpdate.setVisible(true);
        btnCancel.setVisible(true);

        //confirm icon enable
        icSave.setVisible(true);
        icCancel.setVisible(true);

        //icon crud disable
        icPlus.setVisible(false);
        icUpdate.setVisible(false);
        icDelete.setVisible(false);
    }

    @FXML
    void deleteConfirmClickAws(MouseEvent event) {
        if (Validator.isEmpty(txtProduct, lblError)){
            return;
        }
        if (ProductDB.DeleteProduct(selectedProductId)){
            alert_info.setTitle("Delete Status");
            alert_info.setHeaderText("Product was deleted successfully.");
            alert_info.setContentText(selectedProduct + " has successfully been deleted from the database.");
            alert_info.showAndWait();
        }else{
            alert_error.setTitle("Delete Status");
            alert_error.setHeaderText("Supplier was not deleted");
            alert_error.setContentText("An error occurred while trying to deleted the product from the database. Please" +
                    " try again. If issue persists, contact your database administrator.");
            alert_error.showAndWait();
        }
        resetCrud();

        productList = ProductDB.GetProducts();
        lvProducts.setItems(allProducts());
    }

}
