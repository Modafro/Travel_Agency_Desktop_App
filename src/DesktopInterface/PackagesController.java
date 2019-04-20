package DesktopInterface;

import DesktopInterface.TravelExpertClasses.Package;
import DesktopInterface.TravelExpertClasses.PackageDB;
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIcon;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.util.Callback;
import javafx.util.StringConverter;
import javafx.util.converter.DateStringConverter;
import javafx.util.converter.DoubleStringConverter;

import java.sql.SQLException;
import java.text.DateFormat;
import java.text.ParseException;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Calendar;

public class PackagesController {

    SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
    //private DateFormat df2 = new SimpleDateFormat("yyyy-MM-dd");

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

    @FXML
    void initialize() {
        //hide CRUD operations (texfields)
        crudPackages.setExpanded(false);

        //set visibility of error labels
        lblPkgNameError.setVisible(false);
        lblPkgDescError.setVisible(false);
        lblPkgStartDateError.setVisible(false);
        lblPkgEndDateError.setVisible(false);
        lblPkgBasePriceError.setVisible(false);
        lblPkgCommissionError.setVisible(false);

        //disable packages text fields (CRUD pane) on load
        pnpackagesfields.setDisable(true);

        //set visibility of buttons
        setVisibilityButtons(true);

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
                    Alert alert = new Alert(Alert.AlertType.WARNING);
                    alert.setTitle("Date Format Error");
                    alert.setHeaderText(null);
                    alert.setContentText("The date has been formatted incorrectly, please format the date in the form:\nYYYY-MM-DD (eg. 2019-1-31)");
                    alert.showAndWait();
                    //e.printStackTrace();
                }
                return null;
            }
        };

        pkgStartDate.setCellFactory(TextFieldTableCell.forTableColumn(dateStringConverter));
        pkgEndDate.setCellFactory(TextFieldTableCell.forTableColumn(dateStringConverter));
        pkgDescription.setCellFactory(TextFieldTableCell.forTableColumn());
        pkgBasePrice.setCellFactory(TextFieldTableCell.forTableColumn(new DoubleStringConverter()));
        pkgCommission.setCellFactory(TextFieldTableCell.forTableColumn(new DoubleStringConverter()));

        //apply event handlers for all columns
        pkgName.setOnEditCommit(new EventHandler<TableColumn.CellEditEvent<Package, String>>() {
            @Override
            public void handle(TableColumn.CellEditEvent<Package, String> event) {
                Package p = event.getTableView().getItems().get(event.getTablePosition().getRow());
                p.setPkgName(event.getNewValue());
                txtPkgName.setText(p.getPkgName());
            }
        });

        pkgStartDate.setOnEditCommit(new EventHandler<TableColumn.CellEditEvent<Package, Date>>() {
            @Override
            public void handle(TableColumn.CellEditEvent<Package, Date> event) {
                Package p = event.getTableView().getItems().get(event.getTablePosition().getRow());
                p.setPkgStartDate(new java.sql.Date(event.getNewValue().getTime()));
            }
        });
        pkgEndDate.setOnEditCommit(new EventHandler<TableColumn.CellEditEvent<Package, java.util.Date>>() {
            @Override
            public void handle(TableColumn.CellEditEvent<Package, java.util.Date> event) {
                Package p = event.getTableView().getItems().get(event.getTablePosition().getRow());
                p.setPkgEndDate(new java.sql.Date(event.getNewValue().getTime()));
            }
        });
        pkgDescription.setOnEditCommit(new EventHandler<TableColumn.CellEditEvent<Package, String>>() {
            @Override
            public void handle(TableColumn.CellEditEvent<Package, String> event) {
                Package p = event.getTableView().getItems().get(event.getTablePosition().getRow());
                p.setPkgDesc(event.getNewValue());
            }
        });
        pkgBasePrice.setOnEditCommit(new EventHandler<TableColumn.CellEditEvent<Package, Double>>() {
            @Override
            public void handle(TableColumn.CellEditEvent<Package, Double> event) {
                Package p = event.getTableView().getItems().get(event.getTablePosition().getRow());
                p.setPkgBasePrice(event.getNewValue());
            }
        });
        pkgCommission.setOnEditCommit(new EventHandler<TableColumn.CellEditEvent<Package, Double>>() {
            @Override
            public void handle(TableColumn.CellEditEvent<Package, Double> event) {
                Package p = event.getTableView().getItems().get(event.getTablePosition().getRow());
                p.setPkgAgencyCommission(event.getNewValue());

            }
        });
        tblPackages.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
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
        //if insert button was clicked
        if(crudBtnClicked =="insert")
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
    }

    //insert new package
    private void insertPackage()
    {
        Package newPackage = new Package(txtPkgName.getText(), java.sql.Date.valueOf(dpPkgStartDate.getValue()),
                java.sql.Date.valueOf(dpPkgEndDate.getValue()),txtPkgDescription.getText(),Double.parseDouble(txtPkgBasePrice.getText()),
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
        }
    }

    //update existing information of package
    private void updatePackage()
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
            pnpackagesfields.setDisable(true);
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
        setVisibilityButtons(true);
        tblPackages.setDisable(false);
        clearTextfieldDataAndLabels();
    }

    //method to clear textfields and remove error labels
    private void clearTextfieldDataAndLabels()
    {
        txtPackageId.setText("");
        txtPkgName.setText("");
        txtPkgDescription.setText("");
        dpPkgStartDate.setValue(null);
        dpPkgEndDate.setValue(null);
        txtPkgBasePrice.setText("");
        txtPkgCommission.setText("");

        lblPkgNameError.setVisible(false);
        lblPkgDescError.setVisible(false);
        lblPkgStartDateError.setVisible(false);
        lblPkgEndDateError.setVisible(false);
        lblPkgBasePriceError.setVisible(false);
        lblPkgCommissionError.setVisible(false);
    }

    /**
     * Internal class for date cells
     * @param <S>
     * @param <T>
     */
    public class DatePickerCell<S, T> extends TableCell<Package, Date> {

        private DatePicker datePicker;
        private ObservableList<Package> packageData;

        public DatePickerCell(ObservableList<Package> packageData, String cellType) {
            super();

            this.packageData = packageData;

            if (datePicker == null) {
                createDatePicker(cellType);
            }
            setGraphic(datePicker);
            setContentDisplay(ContentDisplay.GRAPHIC_ONLY);

            Platform.runLater(new Runnable() {
                @Override
                public void run() {
                    datePicker.requestFocus();
                }
            });
        }

        @Override
        public void updateItem(Date item, boolean empty) {

            super.updateItem(item, empty);

            SimpleDateFormat smp = new SimpleDateFormat("dd/MM/yyyy");

            if (null == this.datePicker) {
                System.out.println("datePicker is NULL");
            }

            if (empty) {
                setText(null);
                setGraphic(null);
            } else {

                if (isEditing()) {
                    setContentDisplay(ContentDisplay.TEXT_ONLY);
                } else {
                    setDatePickerDate(smp.format(item));
                    setText(smp.format(item));
                    setGraphic(this.datePicker);
                    setContentDisplay(ContentDisplay.GRAPHIC_ONLY);
                }
            }
        }

        private void setDatePickerDate(String dateAsStr) {

            LocalDate ld = null;
            int jour, mois, annee;

            jour = mois = annee = 0;
            try {
                jour = Integer.parseInt(dateAsStr.substring(0, 2));
                mois = Integer.parseInt(dateAsStr.substring(3, 5));
                annee = Integer.parseInt(dateAsStr.substring(6, dateAsStr.length()));
            } catch (NumberFormatException e) {
                System.out.println("setDatepikerDate / unexpected error " + e);
            }

            ld = LocalDate.of(annee, mois, jour);
            datePicker.setValue(ld);
        }

        private void createDatePicker(String cellType) {
            this.datePicker = new DatePicker();
            datePicker.setPromptText("jj/mm/aaaa");
            datePicker.setEditable(true);
            datePicker.setOnAction(new EventHandler() {
                public void handle(Event t) {
                    LocalDate date = datePicker.getValue();
                    int index = getIndex();
                    SimpleDateFormat smp = new SimpleDateFormat("dd/MM/yyyy");
                    Calendar cal = Calendar.getInstance();
                    cal.set(Calendar.DAY_OF_MONTH, date.getDayOfMonth());
                    cal.set(Calendar.MONTH, date.getMonthValue() - 1);
                    cal.set(Calendar.YEAR, date.getYear());

                    setText(smp.format(cal.getTime()));
                    commitEdit(cal.getTime());

                    ObservableList<Package> observableList = getBirthdayData();

                    if (getTableRow() != null && null != getBirthdayData() && cellType.equals("endDate")) {
                        getTableRow().getItem().setPkgEndDate(new java.sql.Date(cal.getTime().getTime()));
                        //getBirthdayData().get(index).setPkgEndDate(new java.sql.Date(cal.getTime().getTime()));
                    }
                    else if(null != getBirthdayData() && cellType.equals("startDate")) {
                        getBirthdayData().get(index).setPkgStartDate(new java.sql.Date(cal.getTime().getTime()));
                    }

                }
            });

            setAlignment(Pos.CENTER);
        }

        @Override
        public void startEdit() {
            super.startEdit();
        }

        @Override
        public void cancelEdit() {
            super.cancelEdit();
            setContentDisplay(ContentDisplay.TEXT_ONLY);
        }

        public ObservableList<Package> getBirthdayData() {
            return packageData;
        }

        public void setBirthdayData(ObservableList<Package> birthdayData) {
            this.packageData = birthdayData;
        }

        public DatePicker getDatePicker() {
            return datePicker;
        }

        public void setDatePicker(DatePicker datePicker) {
            this.datePicker = datePicker;
        }
    }
}
