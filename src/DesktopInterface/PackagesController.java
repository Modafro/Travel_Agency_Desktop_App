/**
 * Author: Mo Sagnia, Yosuke Saito, and James Sharpe
 * Date: April 2019
 * Objective: Create a class controller for interaction of packagesUI to the database
 */



package DesktopInterface;

import DesktopInterface.TravelExpertClasses.*;
import DesktopInterface.TravelExpertClasses.Package;
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIcon;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.util.Callback;
import javafx.util.StringConverter;
import javafx.util.converter.DoubleStringConverter;

import java.text.ParseException;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.temporal.ChronoUnit;
import java.util.Date;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.ArrayList;

public class PackagesController {

    SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");

    @FXML
    private AnchorPane packages;

    @FXML
    private TableView<Package> tblPackages;

    @FXML
    private TableColumn<Package, Integer> pkgId;

    @FXML
    private TableColumn<Package, String> pkgName;

    @FXML
    private TableColumn<Package, Date> pkgStartDate;

    @FXML
    private TableColumn<Package, java.util.Date> pkgEndDate;

    @FXML
    private TableColumn<Package, String> pkgDescription;

    @FXML
    private TableColumn<Package, Double> pkgBasePrice;

    @FXML
    private TableColumn<Package, Double> pkgCommission;

    @FXML
    private Button btnDelete;

    @FXML
    private TitledPane crudPackages;

    @FXML
    private Pane pnpackagesfields;

    @FXML
    private TextField txtPkgName;

    @FXML
    private TextField txtPkgDescription;

    @FXML
    private TextField txtPkgBasePrice;

    @FXML
    private TextField txtPkgCommission;

    @FXML
    private TextField txtPackageId;

    @FXML
    private Label lblPkgNameError;

    @FXML
    private Label lblPkgDescError;

    @FXML
    private Label lblPkgStartDateError;

    @FXML
    private Label lblPkgEndDateError;

    @FXML
    private Label lblPkgBasePriceError;

    @FXML
    private Label lblPkgCommissionError;

    @FXML
    private DatePicker dpPkgStartDate;

    @FXML
    private DatePicker dpPkgEndDate;

    @FXML
    private Button btnInsert;

    @FXML
    private Button btnUpdate;

    @FXML
    private Button btnDelete1;

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
    private Button btnConfirmDelete;

    @FXML
    private FontAwesomeIcon imgConfirmDelete;

    @FXML
    private Button btnRefresh;

    @FXML
    private FontAwesomeIcon imgRefresh;

    @FXML
    private TextField txtSearch;

    private ObservableList<Package> pkgData;

    private Alert alert_info = new Alert(Alert.AlertType.INFORMATION);
    private Alert alert_error = new Alert(Alert.AlertType.ERROR);

    //string object used to know which button was clicked : Insert / Update / Delete
    private String crudBtnClicked = new String();

    //field for focus listener returning true or false
    private boolean booleanForFocusExitListeners;

    @FXML
    void initialize() {
        //validation on focus exit
        isStartDateValidOnFocusExit(dpPkgStartDate, dpPkgEndDate, lblPkgStartDateError);
        isEndDateValidOnFocusExit(dpPkgStartDate,dpPkgEndDate,lblPkgEndDateError);
        isTextfieldNotEmptyOnFoucsExit(txtPkgName, lblPkgNameError);
        isTextfieldNotEmptyOnFoucsExit(txtPkgDescription, lblPkgDescError);
        isTextfieldNotEmptyOnFoucsExit(txtPkgBasePrice, lblPkgBasePriceError);
        isTextfieldNotEmptyOnFoucsExit(txtPkgCommission, lblPkgCommissionError);
        isTextfieldDoubleOnFoucsExit(txtPkgBasePrice,lblPkgBasePriceError);
        isTextfieldDoubleOnFoucsExit(txtPkgCommission, lblPkgCommissionError);

        //hide CRUD operations (texfields)
        crudPackages.setExpanded(false);

        //set default visibility and edit settings
        cancelPkgChanges();

        //set textfields values from the selected package in the table with a mouse click or arrow key released
        setPkgTextfieldsFromTableOnMouseClicked();
        setPkgTextfieldsFromTableOnArrowKeyReleased();

        //set cell values
        pkgId.setCellValueFactory(new PropertyValueFactory<Package, Integer>("PackageId"));
        pkgName.setCellValueFactory(new PropertyValueFactory<Package, String>("pkgName"));
        pkgStartDate.setCellValueFactory(new PropertyValueFactory<Package, Date>("pkgStartDate"));
        pkgEndDate.setCellValueFactory(new PropertyValueFactory<Package, Date>("pkgEndDate"));

        pkgDescription.setCellValueFactory(new PropertyValueFactory<Package, String>("pkgDesc"));
        pkgBasePrice.setCellValueFactory(new PropertyValueFactory<Package, Double>("pkgBasePrice"));
        pkgCommission.setCellValueFactory(new PropertyValueFactory<Package, Double>("pkgAgencyCommission"));

        pkgName.setCellFactory(TextFieldTableCell.forTableColumn());

        //formatter.format(object)
        StringConverter<Date> dateStringConverter = new StringConverter<Date>() {
            @Override
            public String toString(Date object) {
                return formatter.format(object);
            }

            @Override
            public Date fromString(String string) {
                try {
                    Date d = formatter.parse(string);
                    return d;
                } catch (ParseException e) {
                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setTitle("Date Invalid");
                    alert.setHeaderText("Date format invalid");
                    alert.setContentText("The date has been formatted incorrectly, please format the date in the form:\nYYYY-MM-DD (eg. 2019-1-31)");
                    alert.showAndWait();
                    //e.printStackTrace();
                }
                return null;
            }
        };

       StringConverter<Double> doubleStringConverter = new StringConverter<Double>() {
           @Override
           public String toString(Double object) {
               return Double.toString(object);
           }

           @Override
           public Double fromString(String string) {
                  if(Validator.isPositiveDouble(string))
                  {
                      double doubleNumber = Double.parseDouble(string);
                      return doubleNumber;
                  }
                  else
                  {
                      Alert alert = new Alert(Alert.AlertType.ERROR);
                      alert.setTitle("Number Invalid");
                      alert.setHeaderText("Number formatted incorrectly");
                      alert.setContentText("Please enter a valid number");
                      alert.showAndWait();
                  }
                  return null;
               }
       };

       StringConverter<String> stringStringConverter = new StringConverter<String>() {
           @Override
           public String toString(String object) {
               return object;
           }

           @Override
           public String fromString(String string) {
               if(!Validator.isEmpty(string))
               {
                   return string;
               }
               else
               {
                   Alert alert = new Alert(Alert.AlertType.ERROR);
                   alert.setTitle("Invalid input");
                   alert.setHeaderText("Field required");
                   alert.setContentText("Please enter a valid value");
                   alert.showAndWait();
               }
               return null;
           }
       };

        pkgStartDate.setCellFactory(TextFieldTableCell.forTableColumn(dateStringConverter));
        pkgEndDate.setCellFactory(TextFieldTableCell.forTableColumn(dateStringConverter));
        pkgDescription.setCellFactory(TextFieldTableCell.forTableColumn());
        pkgBasePrice.setCellFactory(TextFieldTableCell.forTableColumn(doubleStringConverter));
        pkgCommission.setCellFactory(TextFieldTableCell.forTableColumn(doubleStringConverter));

        //apply event handlers for all columns
        pkgName.setOnEditCommit(new EventHandler<TableColumn.CellEditEvent<Package, String>>() {
            @Override
            public void handle(TableColumn.CellEditEvent<Package, String> event) {
                if(!Validator.isEmpty(event.getNewValue()))
                {
                    Package p = event.getTableView().getItems().get(event.getTablePosition().getRow());
                    p.setPkgName(event.getNewValue());
                    PackageDB.UpdatePackage(p);
                    txtPkgName.setText(p.getPkgName());
                    updateTable();
                }
                else
                {
                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setTitle("Name Invalid");
                    alert.setHeaderText("Valid name needed");
                    alert.setContentText("Please enter a valid name");
                    alert.showAndWait();
                    updateTable();
                }
            }
        });

        pkgStartDate.setOnEditCommit(new EventHandler<TableColumn.CellEditEvent<Package, Date>>() {
            @Override
            public void handle(TableColumn.CellEditEvent<Package, Date> event) {
                Package p = event.getTableView().getItems().get(event.getTablePosition().getRow());
                p.setPkgStartDate(new java.sql.Date(event.getNewValue().getTime()));
                PackageDB.UpdatePackage(p);
                //convert sql date to localdate type
                Date pkgStartDate = (p.getPkgStartDate());
                Instant instantPkgStartDate = Instant.ofEpochMilli(pkgStartDate.getTime());
                dpPkgStartDate.setValue(LocalDateTime.ofInstant(instantPkgStartDate,ZoneId.systemDefault()).toLocalDate());
            }
        });
        pkgEndDate.setOnEditCommit(new EventHandler<TableColumn.CellEditEvent<Package, java.util.Date>>() {
            @Override
            public void handle(TableColumn.CellEditEvent<Package, java.util.Date> event) {
                Package p = event.getTableView().getItems().get(event.getTablePosition().getRow());
                if(Validator.isDateAfterSpecificDate(p.getPkgStartDate(), event.getNewValue()))
                {
                    p.setPkgEndDate(new java.sql.Date(event.getNewValue().getTime()));
                    PackageDB.UpdatePackage(p);

                    Date pkgEndDate = (p.getPkgEndDate());
                    Instant instantPkgEndDate = Instant.ofEpochMilli(pkgEndDate.getTime());
                    dpPkgEndDate.setValue(LocalDateTime.ofInstant(instantPkgEndDate,ZoneId.systemDefault()).toLocalDate());
                }
                else
                {
                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setTitle("Date Invalid");
                    alert.setHeaderText("Date specified incorrect");
                    alert.setContentText("Please enter an end date that is after the start date");
                    alert.showAndWait();
                    updateTable();
                }
            }
        });

        pkgDescription.setOnEditCommit(new EventHandler<TableColumn.CellEditEvent<Package, String>>() {
            @Override
            public void handle(TableColumn.CellEditEvent<Package, String> event) {
                if(!Validator.isEmpty(event.getNewValue()))
                {
                    Package p = event.getTableView().getItems().get(event.getTablePosition().getRow());
                    p.setPkgDesc(event.getNewValue());
                    PackageDB.UpdatePackage(p);
                    txtPkgDescription.setText(p.getPkgDesc());
                }
                else
                {
                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setTitle("Description Invalid");
                    alert.setHeaderText("Valid description needed");
                    alert.setContentText("Please enter a valid description");
                    alert.showAndWait();
                    updateTable();
                }
            }
        });
        pkgBasePrice.setOnEditCommit(new EventHandler<TableColumn.CellEditEvent<Package, Double>>() {
            @Override
            public void handle(TableColumn.CellEditEvent<Package, Double> event) {
                Package p = event.getTableView().getItems().get(event.getTablePosition().getRow());
                p.setPkgBasePrice(event.getNewValue());
                PackageDB.UpdatePackage(p);
                txtPkgBasePrice.setText(Double.toString(p.getPkgBasePrice()));
            }
        });
        pkgCommission.setOnEditCommit(new EventHandler<TableColumn.CellEditEvent<Package, Double>>() {
            @Override
            public void handle(TableColumn.CellEditEvent<Package, Double> event) {
                Package p = event.getTableView().getItems().get(event.getTablePosition().getRow());
                p.setPkgAgencyCommission(event.getNewValue());
                PackageDB.UpdatePackage(p);
                txtPkgCommission.setText(Double.toString(p.getPkgAgencyCommission()));
            }
        });
        tblPackages.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);

        //load combo box
        productList = ProductDB.GetProducts();
        cmbProduct.setItems(getProdNames());
        cmbSupplier.setDisable(true);
        btnLinkPSP.setDisable(true);
        btnDeletePSP.setDisable(true);
        btnCancelLink.setDisable(true);
        tvLinkPSP.setDisable(true);

        lvPkgProSup.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                btnDeletePSP.setDisable(false);
            }
        });


        //updateTable();

/*        TableColumn dateColumn = new TableColumn("Date");

        dateColumn.setCellValueFactory(new PropertyValueFactory<Package, Date>("pkgEndDate"));
        dateColumn.setCellFactory(new Callback<TableColumn, TableCell>() {
            @Override
            public TableCell call(TableColumn p) {
                DatePickerCell datePick = new DatePickerCell(tblPackages.getItems(), "endDate");
                return datePick;
            }
        });
        tblPackages.getColumns().add(dateColumn);*/
    }

    //set textfields values from the selected package in the table with a mouse click
    private void setPkgTextfieldsFromTableOnMouseClicked()
    {
        tblPackages.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                tvLinkPSP.setDisable(false);
                selectPkg = tblPackages.getItems().get(tblPackages.getSelectionModel().getSelectedIndex());
                packageProSup = PackageProductSupplierDB.getPackageProSup(selectPkg.getPackageId());
                ObservableList<PackageProductSupplier> proSuppStrings = FXCollections.observableArrayList(packageProSup);
                lvPkgProSup.setItems(proSuppStrings);

                setPkgTextfieldsFromTable();
            }
        });
    }

    //set textfields values from the selected package in the table with an arrow (up or down) key released
    private void setPkgTextfieldsFromTableOnArrowKeyReleased()
    {
        tblPackages.setOnKeyReleased(new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent event) {
                setPkgTextfieldsFromTable();
            }
        });
    }

    //set values of textfields to corresponding values of selected package in table
    private void setPkgTextfieldsFromTable()
    {
        try
        {
            Package pkg = tblPackages.getItems().get(tblPackages.getSelectionModel().getSelectedIndex());
            txtPackageId.setText(Integer.toString(pkg.getPackageId()));
            txtPkgName.setText(pkg.getPkgName());
            txtPkgDescription.setText(pkg.getPkgDesc());

            //convert sql date to localdate type
            Date pkgStartDate = (pkg.getPkgStartDate());
            Instant instantPkgStartDate = Instant.ofEpochMilli(pkgStartDate.getTime());
            dpPkgStartDate.setValue(LocalDateTime.ofInstant(instantPkgStartDate,ZoneId.systemDefault()).toLocalDate());

            Date pkgEndDate = (pkg.getPkgEndDate());
            Instant instantPkgEndDate = Instant.ofEpochMilli(pkgEndDate.getTime());
            dpPkgEndDate.setValue(LocalDateTime.ofInstant(instantPkgEndDate,ZoneId.systemDefault()).toLocalDate());

            txtPkgBasePrice.setText(Double.toString(pkg.getPkgBasePrice()));
            txtPkgCommission.setText(Double.toString(pkg.getPkgAgencyCommission()));
        }
        catch(IndexOutOfBoundsException e)
        {
            //do nothing
        }

    }

    //set visibility of buttons (and corresponding images)
    private void setVisibilityButtons(boolean showCrud)
    {
        if(showCrud == true)
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

    public void updateTable() {
        ArrayList<Package> p = PackageDB.GetPackages();
        if (pkgData !=null)
        {
            pkgData.clear();
        }
        pkgData = FXCollections.observableArrayList(p);
        tblPackages.setItems(pkgData);
    }

    //method to search the database on enter key pressed
    public void searchDataBase()
    {
        ArrayList<Package> pResult = new ArrayList<>();
        pResult = PackageDB.PackageSearchResult(pResult,txtSearch.getText());
        pkgData.clear();
        pkgData = FXCollections.observableArrayList(pResult);
        tblPackages.setItems(pkgData);
    }

    //method to insert or delete package
    public void savePkgChanges()
    {
        System.out.println("method savePkgChanges called");
        //if insert button was clicked
        if(crudBtnClicked.equals("insert"))
        {
            insertPackage();
        }
        //if update button was clicked
        else
        {
            updatePackage();
        }
    }

    //change buttons visibility to only show "save" and "cancel" button
    public void insertBtnClicked()
    {
        crudBtnClicked = "insert";
        pnpackagesfields.setDisable(false);
        setVisibilityButtons(false);
        tblPackages.setDisable(true);
        clearTextfieldDataAndLabels();

        dpPkgStartDate.setStyle("-fx-opacity: 1");
        dpPkgStartDate.getEditor().setStyle("-fx-opacity: 1");
        dpPkgEndDate.setStyle("-fx-opacity: 1");
        dpPkgEndDate.getEditor().setStyle("-fx-opacity: 1");

        setDateChoiceTodayAndOnwards(dpPkgStartDate);
        setDateChoiceBasedOnOtherDatePicked(dpPkgStartDate,dpPkgEndDate,1);
    }

    //insert new package
    private void insertPackage()
    {

//                isStartDateValidOnFocusExit(dpPkgStartDate, dpPkgEndDate, lblPkgStartDateError) && isEndDateValidOnFocusExit(dpPkgStartDate, dpPkgEndDate, lblPkgEndDateError) &&
        if(!Validator.isEmpty(txtPkgName, lblPkgNameError)&& !Validator.isEmpty(txtPkgDescription, lblPkgDescError) &&
                Validator.isDateValid(dpPkgStartDate, lblPkgStartDateError) && Validator.isDateAfterToday(dpPkgStartDate, lblPkgStartDateError) &&
                Validator.isDateValid(dpPkgEndDate, lblPkgEndDateError) && Validator.isDateAfterSpecificDate(dpPkgStartDate,dpPkgEndDate, lblPkgEndDateError) &&
                !Validator.isEmpty(txtPkgBasePrice, lblPkgBasePriceError) && Validator.isPositiveDouble(txtPkgBasePrice, lblPkgBasePriceError) &&
                !Validator.isEmpty(txtPkgCommission, lblPkgCommissionError) && Validator.isPositiveDouble(txtPkgCommission, lblPkgCommissionError)
                )
        {

            Package newPackage = new Package(txtPkgName.getText(), java.sql.Date.valueOf(dpPkgStartDate.getValue()),
                    java.sql.Date.valueOf(dpPkgEndDate.getValue()), txtPkgDescription.getText(), Double.parseDouble(txtPkgBasePrice.getText()),
                    Double.parseDouble(txtPkgCommission.getText()));


            PackageDB.AddPackage(newPackage);

            alert_info.setTitle("Insert Status");
            alert_info.setHeaderText("Package added successfully.");
            alert_info.setContentText(newPackage.getPkgName() + " has successfully been added to the database.");
            alert_info.showAndWait();

            //set visibility to default settings and clear labels
            cancelPkgChanges();

            //refresh table view
            updateTable();
        }
        else
        {
            System.out.println("not doing it");
        }
    }

    //change buttons visibility to only show "save" and "cancel" button
    public void updateBtnClicked()
    {
        if(txtPkgName.getText().isEmpty())
        {
            alert_error.setTitle("Package Invalid");
            alert_error.setHeaderText("No package selected");
            alert_error.setContentText("Please select a package from the table to update.");
            alert_error.showAndWait();
        }
        else
        {
            crudBtnClicked = "update";
            pnpackagesfields.setDisable(false);
            setVisibilityButtons(false);
            tblPackages.setDisable(true);

            dpPkgStartDate.setStyle("-fx-opacity: 1");
            dpPkgStartDate.getEditor().setStyle("-fx-opacity: 1");
            dpPkgEndDate.setStyle("-fx-opacity: 1");
            dpPkgEndDate.getEditor().setStyle("-fx-opacity: 1");

            setDateChoiceTodayAndOnwards(dpPkgStartDate);
            setDateChoiceBasedOnOtherDatePicked(dpPkgStartDate,dpPkgEndDate,1);
        }
    }

    //update existing information of package
    private void updatePackage()
    {
        if(!Validator.isEmpty(txtPkgName, lblPkgNameError)&& !Validator.isEmpty(txtPkgDescription, lblPkgDescError)&&
            Validator.isDateValid(dpPkgStartDate, lblPkgStartDateError) && Validator.isDateAfterToday(dpPkgStartDate, lblPkgStartDateError) &&
            Validator.isDateValid(dpPkgEndDate, lblPkgEndDateError) && Validator.isDateAfterSpecificDate(dpPkgStartDate,dpPkgEndDate, lblPkgEndDateError) &&
            !Validator.isEmpty(txtPkgBasePrice, lblPkgBasePriceError) && Validator.isPositiveDouble(txtPkgBasePrice, lblPkgBasePriceError) &&
            !Validator.isEmpty(txtPkgCommission, lblPkgCommissionError) && Validator.isPositiveDouble(txtPkgCommission, lblPkgCommissionError))
        {
            Package updatePackage = new Package(Integer.parseInt(txtPackageId.getText()), txtPkgName.getText(),
                    java.sql.Date.valueOf(dpPkgStartDate.getValue()), java.sql.Date.valueOf(dpPkgEndDate.getValue()),
                    txtPkgDescription.getText(), Double.parseDouble(txtPkgBasePrice.getText()), Double.parseDouble(txtPkgCommission.getText()));

            PackageDB.UpdatePackage(updatePackage);
            //show dialog box
            alert_info.setTitle("Update Status");
            alert_info.setHeaderText("Package updated successfully.");
            alert_info.setContentText(updatePackage.getPkgName()+ " has successfully been deleted.");
            alert_info.showAndWait();

            //set visibility to default settings and clear textfields
            cancelPkgChanges();

            //refresh table view
            updateTable();
        }

    }

    //method to delete package
    public void deletePkg()
    {
        Package deletePackage = new Package(Integer.parseInt(txtPackageId.getText()), txtPkgName.getText(),
                java.sql.Date.valueOf(dpPkgStartDate.getValue()),java.sql.Date.valueOf(dpPkgEndDate.getValue()),
                txtPkgDescription.getText(),Double.parseDouble(txtPkgBasePrice.getText()),Double.parseDouble(txtPkgCommission.getText()));

        boolean deletePkgSuccessful = PackageDB.DeletePackage(deletePackage);
        if(deletePkgSuccessful)
        {
            //show dialog box
            alert_info.setTitle("Delete Status");
            alert_info.setHeaderText("Package deleted successfully.");
            alert_info.setContentText(deletePackage.getPkgName()+ " has successfully been deleted.");
            alert_info.showAndWait();

            //set visibility to default settings and clear textfields
            cancelPkgChanges();

            //refresh table view
            updateTable();
        }
    }

    //change buttons visibility to only show "confirm" and "cancel" button
    public void deleteBtnClicked()
    {
        if(txtPkgName.getText().isEmpty())
        {
            alert_error.setTitle("Package Invalid");
            alert_error.setHeaderText("No package selected");
            alert_error.setContentText("Please select a package from the table to delete.");
            alert_error.showAndWait();
        }
        else
        {
            crudBtnClicked = "delete";
            pnpackagesfields.setDisable(false);
            txtPkgName.setEditable(false);
            txtPkgDescription.setEditable(false);
            txtPkgCommission.setEditable(false);
            txtPkgBasePrice.setEditable(false);
            dpPkgStartDate.setDisable(true);
            dpPkgStartDate.setStyle("-fx-opacity: 1");
            dpPkgStartDate.getEditor().setStyle("-fx-opacity: 1");
            dpPkgEndDate.setDisable(true);
            dpPkgEndDate.setStyle("-fx-opacity: 1");
            dpPkgEndDate.getEditor().setStyle("-fx-opacity: 1");
            setVisibilityButtons(false);
            btnSave.setVisible(false);
            imgSave.setVisible(false);
            btnConfirmDelete.setVisible(true);
            imgConfirmDelete.setVisible(true);
            tblPackages.setDisable(true);
        }
    }

    //method to cancel any ongoing changes
    public void cancelPkgChanges()
    {
        pnpackagesfields.setDisable(true);
        dpPkgEndDate.setDisable(true);
        setVisibilityButtons(true);
        tblPackages.setDisable(false);
        clearTextfieldDataAndLabels();
        txtPkgName.setEditable(true);
        txtPkgDescription.setEditable(true);
        txtPkgCommission.setEditable(true);
        txtPkgBasePrice.setEditable(true);
        dpPkgStartDate.setDisable(false);
        dpPkgStartDate.setStyle("-fx-opacity: 0.5");
        dpPkgStartDate.getEditor().setStyle("-fx-opacity: 0.5");
        dpPkgEndDate.setDisable(false);
        dpPkgEndDate.setStyle("-fx-opacity: 0.5");
        dpPkgEndDate.getEditor().setStyle("-fx-opacity: 0.5");
    }

    //method to clear textfields and remove error labels
    private void clearTextfieldDataAndLabels() {
        txtPackageId.setText("");
        txtPkgName.setText("");
        txtPkgDescription.setText("");
        if (dpPkgStartDate.getValue() !=null)
        {
            dpPkgStartDate.setValue(null);
        }
        if(dpPkgEndDate.getValue() !=null)
        {
            dpPkgEndDate.setValue(null);
        }
        txtPkgBasePrice.setText("");
        txtPkgCommission.setText("");

        lblPkgNameError.setVisible(false);
        lblPkgDescError.setVisible(false);
        lblPkgStartDateError.setVisible(false);
        lblPkgEndDateError.setVisible(false);
        lblPkgBasePriceError.setVisible(false);
        lblPkgCommissionError.setVisible(false);
    }

    //method to set datetime picker cell choices to not include dates before today
    public void setDateChoiceTodayAndOnwards(DatePicker datePicker)
    {
        Callback<DatePicker, DateCell> startDateCellFactory = new Callback<DatePicker, DateCell>() {
            @Override
            public DateCell call(DatePicker datePicker) {
                return new DateCell(){
                    @Override
                    public void updateItem(LocalDate item, boolean empty){
                        super.updateItem(item,empty);

                        if (item.isBefore( LocalDate.now()))
                        {
                            setDisable(true);
                            setStyle("-fx-background-color: #ffc0cb;");
                            //setStyle("-fx-opacity: 0.5");
                            //getEditor().setStyle("-fx-opacity: 1");
                        }
                        else
                        {
                            setStyle("-fx-background-color: #D5F9D6;");
                        }
                    }
                };
            }
        };
        datePicker.setDayCellFactory(startDateCellFactory);
        //datePicker.getEditor().setStyle("-fx-opacity: 1");
    }

    //method to set datetime picker cell choices to be after the date specified in a different datetime picker
    public void setDateChoiceBasedOnOtherDatePicked(DatePicker firstDatePicker, DatePicker secondDatePicker, int setMinDaysDifferential)
    {
        Callback<DatePicker, DateCell> endDateCellFactory = new Callback<DatePicker, DateCell>() {
            @Override
            public DateCell call(DatePicker datePicker) {
                return new DateCell(){
                    @Override
                    public void updateItem(LocalDate item, boolean empty){
                        super.updateItem(item,empty);
                        if(firstDatePicker.getValue() != null)
                        {
                            if (item.isBefore( firstDatePicker.getValue().plusDays(setMinDaysDifferential)))
                            {
                                setDisable(true);
                                setStyle("-fx-background-color: #ffc0cb;");
                            }
                            else
                            {
                                setStyle("-fx-background-color: #D5F9D6;");
                            }

                            //show a tool tip for number of days between the two dates
                            long p = ChronoUnit.DAYS.between(firstDatePicker.getValue(), item);
                            setTooltip(new Tooltip("Vacation package duration: "+ p + " days"));
                        }
                    }
                };
            }
        };
        secondDatePicker.setDayCellFactory(endDateCellFactory);
    }

    //method to validate start date on exit of datepicker field and prevent end date to be editable until start date is valid
    public boolean isStartDateValidOnFocusExit(DatePicker startDate, DatePicker endDate,Label lblNameError)
    {
        startDate.focusedProperty().addListener(new ChangeListener<Boolean>() {
            @Override
            public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) {
                if(!newValue)
                {
                    //DatePicker dpPkgStartDateDecoy = new DatePicker();
                    try
                    {
                        //datepicker only sets value if key.ENTER is pressed, if not pressed, we have to"forcefully"
                        // set the value
                        startDate.setValue(startDate.getConverter().fromString(startDate.getEditor().getText()));
                        if(Validator.isDateValid(startDate, lblNameError) && Validator.isDateAfterToday(startDate, lblNameError))
                        {
                            endDate.setDisable(false);
                            booleanForFocusExitListeners=true;
                        }
                        else
                        {
                            endDate.setDisable(true);
                            booleanForFocusExitListeners = false;
                        }
                    }
                    catch (Exception e)
                    {
                        endDate.setDisable(true);
                        lblNameError.setVisible(true);
                        lblNameError.setText("Please enter a valid format: 01/31/2000");
                        booleanForFocusExitListeners = false;
                    }
                }
            }
        });
        return booleanForFocusExitListeners;
    }

    //method to validate if textfields are empty on focus exit
    public boolean isTextfieldNotEmptyOnFoucsExit(TextField txtName, Label lblName)
    {
        txtName.focusedProperty().addListener(new ChangeListener<Boolean>() {
            @Override
            public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) {
                if(!newValue)
                {
                    if(!Validator.isEmpty(txtName, lblName))
                    {
                        booleanForFocusExitListeners =  true;
                    }
                    else
                    {
                        booleanForFocusExitListeners = false;
                    }
                }
            }
        });
        return booleanForFocusExitListeners;
    }

    //method to validate if textfield is a double on focus exit
    public boolean isTextfieldDoubleOnFoucsExit(TextField txtName, Label lblName)
    {
        txtName.focusedProperty().addListener(new ChangeListener<Boolean>() {
            @Override
            public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) {
                if(!newValue)
                {
                    if(!Validator.isPositiveDouble(txtName, lblName))
                    {
                        booleanForFocusExitListeners =  true;
                    }
                    else
                    {
                        booleanForFocusExitListeners = false;
                    }
                }
            }
        });

        return booleanForFocusExitListeners;
    }

    //method to validate end date on exit of datepicker field
    public boolean isEndDateValidOnFocusExit(DatePicker specificDate, DatePicker dpName, Label lblNameError)
    {
            dpName.focusedProperty().addListener(new ChangeListener<Boolean>() {
                @Override
                public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) {
                    if(!newValue)
                    {
                        try
                        {
                            //datepicker only sets value if key.ENTER is pressed, if not pressed, we have to"forcefully"
                            // set the value
                            dpName.setValue(dpName.getConverter().fromString(dpName.getEditor().getText()));
                            if(Validator.isDateValid(dpName, lblNameError) && Validator.isDateAfterSpecificDate(specificDate, dpName, lblNameError))
                            {
                                //dpName.setValue(dpName.getConverter().fromString(dpName.getEditor().getText()));
                                booleanForFocusExitListeners =  true;
                            }
                            else
                            {
                                booleanForFocusExitListeners = false;
                            }
                        }
                        catch(Exception e)
                        {
                            lblNameError.setVisible(true);
                            lblNameError.setText("Please enter a valid format: 01/31/2000");
                            booleanForFocusExitListeners = false;
                        }
                    }
                }
            });
            return booleanForFocusExitListeners;
        }


     //titled pane added late in development

    @FXML
    private Button btnLinkPSP;

    @FXML
    private TitledPane tvLinkPSP;

    @FXML
    private ComboBox<Product> cmbProduct;

    @FXML
    private ComboBox<Supplier> cmbSupplier;

    @FXML
    private ListView<PackageProductSupplier> lvPkgProSup;

    @FXML
    private Button btnCancelLink;

    @FXML
    private Button btnDeletePSP;


    int selectedProd;
    int selectedSupp;
    int proSupp;
    private ArrayList<PackageProductSupplier> packageProSup;
    private ArrayList<Supplier> supplierList;
    private ArrayList<Product> productList;
    private Package selectPkg;

    @FXML
    void cmbProdSelect(ActionEvent event) {
        if (selectPkg != null || cmbProduct.getSelectionModel().getSelectedIndex() != -1){
            selectedProd = cmbProduct.getSelectionModel().getSelectedItem().getProductId();
            supplierList = SupplierDB.GetSpecSupplier(selectedProd, selectPkg.getPackageId());
            cmbSupplier.setItems(getSuppNames());
        }
        cmbSupplier.setDisable(false);
        btnCancelLink.setDisable(false);
    }

    @FXML
    void cmbSuppSelect(ActionEvent event) {
        if (cmbSupplier.getSelectionModel().getSelectedIndex() != -1){
            selectedSupp = cmbSupplier.getSelectionModel().getSelectedItem().getSupplierId();
            proSupp = ProductSupplierDB.getSpecProSuppId(selectedProd, selectedSupp);
            btnLinkPSP.setDisable(false);
            tblPackages.setDisable(true);
        }
    }

    @FXML
    void linkPSP(ActionEvent event) {
        int pkgId = Integer.parseInt(txtPackageId.getText());
        PackageProductSupplierDB.LinkPackageProdSupp(pkgId, proSupp);
        cmbSupplier.setDisable(true);
        btnLinkPSP.setDisable(true);
        tblPackages.setDisable(false);
        btnCancelLink.setDisable(true);

        cmbSupplier.getSelectionModel().clearSelection();
        cmbProduct.getSelectionModel().clearSelection();
        lvPkgProSup.getItems().clear();
        cmbSupplier.getItems().clear();

        if (selectPkg != null) {
            ArrayList<PackageProductSupplier> packageProSup = PackageProductSupplierDB.getPackageProSup(selectPkg.getPackageId());
            ObservableList<PackageProductSupplier> proSuppStrings = FXCollections.observableArrayList(packageProSup);
            lvPkgProSup.setItems(proSuppStrings);
        }
    }

    @FXML
    void btnCancelLink(ActionEvent event){
        cmbSupplier.getSelectionModel().clearSelection();
        cmbProduct.getSelectionModel().clearSelection();
        cmbSupplier.setDisable(true);
        tblPackages.setDisable(false);
        btnLinkPSP.setDisable(true);
    }

    @FXML
    void deletePSP(ActionEvent event) {
        PackageProductSupplierDB.deleteProSupp(selectPkg.getPackageId() ,lvPkgProSup.getSelectionModel().getSelectedItem().getProductSupplierId());

        lvPkgProSup.getItems().clear();
        cmbSupplier.getSelectionModel().clearSelection();

        supplierList = SupplierDB.GetSpecSupplier(selectedProd, selectPkg.getPackageId());
        cmbSupplier.setItems(getSuppNames());

        ArrayList<PackageProductSupplier> packageProSup = PackageProductSupplierDB.getPackageProSup(selectPkg.getPackageId());
        ObservableList<PackageProductSupplier> proSuppStrings = FXCollections.observableArrayList(packageProSup);
        lvPkgProSup.setItems(proSuppStrings);
        btnDeletePSP.setDisable(true);

    }

    private ObservableList<Product> getProdNames() {
        ArrayList<Product> prodNames = new ArrayList<>(productList);
        ObservableList<Product> options = FXCollections.observableArrayList(prodNames);
        return options;
    }

    private ObservableList<Supplier> getSuppNames() {
        ArrayList<Supplier> suppNames = new ArrayList<>(supplierList);
        ObservableList<Supplier> options = FXCollections.observableArrayList(suppNames);
        return options;
    }
}

