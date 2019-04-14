package DesktopInterface;

import DesktopInterface.TravelExpertClasses.Package;
import DesktopInterface.TravelExpertClasses.PackageDB;
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
import javafx.scene.layout.AnchorPane;
import javafx.util.Callback;
import javafx.util.converter.DateStringConverter;
import javafx.util.converter.DoubleStringConverter;

import java.util.Date;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Calendar;

public class PackagesController {

    SimpleDateFormat formatter = new SimpleDateFormat("dd-MMM-yyyy");

    @FXML
    private AnchorPane packages;

    @FXML
    private TableView<Package> tblPackages;

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
    void initialize() {
        //set cell values
        pkgName.setCellValueFactory(new PropertyValueFactory<Package, String>("pkgName"));
        pkgStartDate.setCellValueFactory(new PropertyValueFactory<Package, Date>("pkgStartDate"));
        pkgEndDate.setCellValueFactory(new PropertyValueFactory<Package, Date>("pkgEndDate"));

        pkgDescription.setCellValueFactory(new PropertyValueFactory<Package, String>("pkgDesc"));
        pkgBasePrice.setCellValueFactory(new PropertyValueFactory<Package, Double>("pkgBasePrice"));
        pkgCommission.setCellValueFactory(new PropertyValueFactory<Package, Double>("pkgAgencyCommission"));

        SimpleDateFormat format = new SimpleDateFormat("dd-MM-yyyy");
        Callback<TableColumn<Package, Date>, TableCell<Package, Date>> dateCellFactory = column -> {
            TableCell<Package, Date> cell = new TableCell<Package, Date>() {
                @Override
                protected void updateItem(Date item, boolean empty) {
                    super.updateItem(item, empty);
                    if (empty) {
                        setText(null);
                    } else {
                        setText(format.format(item));
                    }
                }
            };
            return cell;
        };
        pkgName.setCellFactory(TextFieldTableCell.forTableColumn());
        pkgStartDate.setCellFactory(dateCellFactory);
        pkgEndDate.setCellFactory(TextFieldTableCell.forTableColumn(new DateStringConverter()));
        pkgDescription.setCellFactory(TextFieldTableCell.forTableColumn());
        pkgBasePrice.setCellFactory(TextFieldTableCell.forTableColumn(new DoubleStringConverter()));
        pkgCommission.setCellFactory(TextFieldTableCell.forTableColumn(new DoubleStringConverter()));

        //apply event handlers for all columns
        pkgName.setOnEditCommit(new EventHandler<TableColumn.CellEditEvent<Package, String>>() {
            @Override
            public void handle(TableColumn.CellEditEvent<Package, String> event) {
                Package p = event.getTableView().getItems().get(event.getTablePosition().getRow());
                p.setPkgName(event.getNewValue());
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

        updateTable();

        TableColumn dateColumn = new TableColumn("Date");

        dateColumn.setCellValueFactory(new PropertyValueFactory<Package, Date>("pkgEndDate"));
        dateColumn.setCellFactory(new Callback<TableColumn, TableCell>() {
            @Override
            public TableCell call(TableColumn p) {
                DatePickerCell datePick = new DatePickerCell(tblPackages.getItems(), "endDate");
                return datePick;
            }
        });
        tblPackages.getColumns().add(dateColumn);
    }

    public void updateTable() {
        ArrayList<Package> p = PackageDB.GetPackages();
        ObservableList<Package> packages = FXCollections.observableArrayList(p);
        tblPackages.setItems(packages);
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
                    if (null != getBirthdayData() && cellType.equals("endDate")) {
                        getBirthdayData().get(index).setPkgEndDate(new java.sql.Date(cal.getTime().getTime()));
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
