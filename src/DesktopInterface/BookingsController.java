/**
 * Author: Mo Sagnia
 * Date: April 2019
 * Objective: Create a class controller for interaction of BookingsUI to the database
 */


package DesktopInterface;

import DesktopInterface.TravelExpertClasses.*;
import DesktopInterface.TravelExpertClasses.Package;
import com.itextpdf.text.*;

import com.itextpdf.text.Font;
import com.itextpdf.text.Image;
import com.itextpdf.text.Rectangle;
import com.itextpdf.text.html.WebColors;
import com.itextpdf.text.pdf.FontSelector;
import com.itextpdf.text.pdf.PdfPCell;

import com.itextpdf.text.pdf.PdfPTable;

import com.itextpdf.text.pdf.PdfWriter;

import com.itextpdf.text.pdf.draw.DottedLineSeparator;
import com.itextpdf.text.pdf.draw.LineSeparator;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.scene.control.*;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;

import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.DateFormat;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Properties;

public class BookingsController extends JPanel {

    private Agents loggedAgent;

    private Bookings newBooking;

    private Customer cust;

    private Package pkg;

    private DateFormat df = new SimpleDateFormat("MMM dd, yyyy");
    private DateFormat df2 = new SimpleDateFormat("yyyy-MM-dd");

    //today's date in sql format
    private Date today = new Date();
    private java.sql.Date sqlToday = new java.sql.Date(today.getTime());

    //currency format
    private NumberFormat currencyFormat = NumberFormat.getCurrencyInstance();

    private File pdfFile;

    @FXML
    private AnchorPane bookings;

    @FXML
    private Pane pnSelectCustPkgNumTravelers;

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
    private Label lblVacationSummary;

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
    private Label lblErrorMessage;

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

        //hide error labels
        lblErrorMessage.setVisible(false);
        lblVacationSummary.setVisible(false);

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

        if (Validator.isEmpty(txtCustId))
        {
            lblErrorMessage.setVisible(true);
            lblErrorMessage.setText("Choose a customer");
        }
        else if (Validator.isEmpty(txtPkgId))
        {
            lblErrorMessage.setVisible(true);
            lblErrorMessage.setText("Choose a package");
        }
        else if(Validator.isEmpty(txtNumTravelers) || !Validator.isPositiveInteger(txtNumTravelers))
        {
            lblErrorMessage.setVisible(true);
            lblErrorMessage.setText("Enter valid number of travelers");
        }
        //total cost
        else{
            lblErrorMessage.setVisible(false);
            //create new booking
            newBooking = new Bookings(sqlToday, Float.parseFloat(txtNumTravelers.getText()), cust.getCustomerId(),"L",pkg.getPackageId());

            boolean addBkgSuccessful = BookingsDB.addBookingForCustId(newBooking, cust, pkg);

            if(addBkgSuccessful)
            {
                double totalPrice;
                totalPrice = pkg.getPkgBasePrice() * (Double.parseDouble(txtNumTravelers.getText()));
                lblNumTravelers.setText(txtNumTravelers.getText());
                lblTotalPrice.setText(currencyFormat.format(totalPrice));

                pnsummary.setVisible(true);
                lblVacationSummary.setVisible(true);
                pnSelectCustPkgNumTravelers.setDisable(true);
            }
            else
            {
                alert_error.setTitle("Summary Status");
                alert_error.setHeaderText("Unable to load summary");
                alert_error.setContentText("An error occurred while trying to view summary for customer. Please" +
                        " try again. If issue persists, contact your database administrator.");
                alert_error.showAndWait();
            }
        }
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
        try
        {
            //labels related to customer information
            cust = tvcustomers.getItems().get(tvcustomers.getSelectionModel().getSelectedIndex());
            txtCustId.setText(Integer.toString(cust.getCustomerId()));
            lblCustName.setText(cust.getCustFirstName() + " "+ cust.getCustLastName());
            lblCustHomePhone.setText(cust.getCustHomePhone());
            lblCustEmail.setText(cust.getCustEmail().trim());
            lblCustAddress.setText(cust.getCustAddress());
        }
        catch(IndexOutOfBoundsException e)
        {
            //do nothing
        }

    }

    //set values of labelfields in summary pane (pnsummary) to corresponding values of selected package from package table
    public void setPkgLabelFieldsFromTable() {
        try
        {
            pkg = tvpackages.getItems().get(tvpackages.getSelectionModel().getSelectedIndex());
            txtPkgId.setText(Integer.toString(pkg.getPackageId()));
            lblPkgName.setText(pkg.getPkgName());
            lblPkgDesc.setText(pkg.getPkgDesc());
            lblPkgStartDate.setText(df.format(pkg.getPkgStartDate()));
            lblPkgEndDate.setText(df.format(pkg.getPkgEndDate()));
            lblBasePrice.setText(currencyFormat.format(pkg.getPkgBasePrice()));

            //number of travelers
            lblNumTravelers.setText(txtNumTravelers.getText());
        }
        catch(IndexOutOfBoundsException e)
        {
            //do nothing
        }
    }

    //reset all fields to original format
    public void resetAll()
    {
        //hide summary info
        pnsummary.setVisible(false);
        lblVacationSummary.setVisible(false);

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

        //enable pain containing search functions / tables / number of customers
        pnSelectCustPkgNumTravelers.setDisable(false);

        //delete pdf file if it exists
        try {
            if(pdfFile.exists())
            {
                pdfFile.delete();
            }
        } catch (Exception e) {
            //do nothing
        }
    }

    //cancel ongoing booking
    public void cancelBooking()
    {
        resetAll();
        BookingsDB.deleteBooking(newBooking);
    }

    //create new booking and send email to customer
    public void sendInvoice() throws IOException
    {
        createInvoicePdf();

        sendEmailInvoice();

        //show dialog box
        alert_info.setTitle("Insert Status");
        alert_info.setHeaderText("Booking added successfully.");
        alert_info.setContentText(pkg.getPkgName() + " has been successfully booked for "+ cust.getCustFirstName()
                        +" "+ cust.getCustLastName() +". Invoice has been sent to customer.");
        alert_info.showAndWait();

        //set visibility
        resetAll();

    }

    //show pdf
    public void showInvoiceInPdf() throws IOException {

        pdfFile = new File("Invoices/"+cust.getCustLastName()+"Invoice_Booking" + newBooking.getBookingId()+".pdf");
        if (pdfFile.exists())
        {
            openPdf(pdfFile);
        }
        else
        {
            createInvoicePdf();
            openPdf(pdfFile);
        }

        System.out.println("Done");
    }

    //send email
    public void sendEmailInvoice(){
        //get or set respective email addresses
        String emailTo = cust.getCustEmail();
        String emailFrom = "travelExpertsTeam7@gmail.com";
        String password = "Travel123";

        //set SMTP server properties
        Properties properties = new Properties();
        properties.put("mail.smtp.auth", "true");
        properties.put("mail.smtp.starttls.enable","true");
        properties.put("mail.smtp.host","smtp.gmail.com");
        properties.put("mail.smtp.port",587);

        //create a new session with an authenticator
        Authenticator auth = new Authenticator(){
            public PasswordAuthentication getPasswordAuthentication(){
                return new PasswordAuthentication(emailFrom, password);
            }
        };
        Session session = Session.getDefaultInstance(properties, auth);

        try {
            //create new email message
            MimeMessage msg = new MimeMessage(session);
            msg.setFrom(new InternetAddress(emailFrom));
            msg.addRecipient(Message.RecipientType.TO, new InternetAddress(emailTo));
            msg.setSubject("Vacation Package Invoice, Booking No. "+ newBooking.getBookingId());
            //msg.setSentDate(new Date());

            // creates message part
            MimeBodyPart messageBodyPart = new MimeBodyPart();
            messageBodyPart.setContent("Congratulations <b>"+cust.getCustFirstName()+"</b>!<br/><br/>" +
                    "You have successfully booked your vacation. All the details pertaining to your booking are in the attachment.<br/><br/>" +
                    "Enjoy your vacation and thank you for choosing Travel Experts.", "text/html");

            // creates multi-part
            Multipart multipart = new MimeMultipart();
            multipart.addBodyPart(messageBodyPart);

            // adds attachments
            MimeBodyPart attachPart = new MimeBodyPart();

            try {
                attachPart.attachFile("Invoices/"+cust.getCustLastName()+"Invoice_Booking" + newBooking.getBookingId()+".pdf");
            } catch (IOException ex) {
                ex.printStackTrace();
            }

            multipart.addBodyPart(attachPart);

            // sets the multi-part as e-mail's content
            msg.setContent(multipart);

            // sends the e-mail
            Transport.send(msg);
        } catch (MessagingException e) {
            e.printStackTrace();
        }

        //delete pdf file if it exists
        try {
            if(pdfFile.exists())
            {
                pdfFile.delete();
            }
        } catch (Exception e) {
            //do nothing
        }
    }

    //create pdf
    public void createInvoicePdf()throws IOException{
        Document document = new Document();
        PdfWriter writer = null;
        try {
            writer = PdfWriter.getInstance(document, new FileOutputStream("Invoices/"+cust.getCustLastName()+"Invoice_Booking" + newBooking.getBookingId()+".pdf"));
        } catch (DocumentException e) {
            e.printStackTrace();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        document.open();
        try {
            File pdffile = new File("Invoices/"+cust.getCustLastName()+"Invoice_Booking" + newBooking.getBookingId()+".pdf");
            try {
                if(pdffile.exists())
                {
                    pdffile.delete();
                }
            } catch (Exception e) {
                //do nothing
            }

            BaseColor color1 = WebColors.getRGBColor("#3390FF");
            BaseColor color2 = WebColors.getRGBColor("#BDECF7");

            FontSelector heading1 = new FontSelector();
            Font font1 = FontFactory.getFont(FontFactory.HELVETICA_BOLDOBLIQUE, 25);
            font1.setColor(color1);
            heading1.addFont(font1);

            FontSelector heading2 = new FontSelector();
            Font font2 = FontFactory.getFont(FontFactory.HELVETICA_BOLD, 20);
            font2.setColor(color1);
            heading2.addFont(font2);

            FontSelector heading3 = new FontSelector();
            Font font3 = FontFactory.getFont(FontFactory.HELVETICA_BOLDOBLIQUE, 15);
            font3.setColor(BaseColor.GRAY);
            heading3.addFont(font3);

            FontSelector heading4 = new FontSelector();
            Font font4 = FontFactory.getFont(FontFactory.HELVETICA_BOLD, 12);
            font4.setColor(color1);
            heading4.addFont(font4);

            FontSelector normal = new FontSelector();
            Font fontNormal = FontFactory.getFont(FontFactory.TIMES, 12);
            fontNormal.setColor(BaseColor.BLACK);
            normal.addFont(fontNormal);

            FontSelector normalBold = new FontSelector();
            Font fontNormalBold = FontFactory.getFont(FontFactory.TIMES_BOLD,12);
            fontNormalBold.setColor(BaseColor.BLACK);
            normalBold.addFont(fontNormalBold);

            PdfPTable headerTable = new PdfPTable(2);
            headerTable.setWidthPercentage(100);
            headerTable.setSpacingAfter(10f);

            PdfPCell cell;

            String TravelExperts = "Travel Experts\n";
            Phrase headerTitle = heading1.process(TravelExperts);

            cell = new PdfPCell(headerTitle);
            cell.setBorder(Rectangle.NO_BORDER);
            //cell.setColspan(1);

            headerTable.addCell(cell);

            String address = "1155 8th Ave SW\nCalgary, AB, T2P 1N3\nphone: 4032719873\nfax: 4032719872";
            Phrase addressPhrase =  new Phrase(address);

            // now we add a cell with rowspan 2

            Image img = Image.getInstance("src/map1.png");
            img.scaleAbsolute(85f,75f);
            cell = new PdfPCell(img);
            cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
            cell.setBorder(Rectangle.NO_BORDER);
            cell.setRowspan(2);
            headerTable.addCell(cell);

            cell = new PdfPCell(addressPhrase);
            cell.setBorder(Rectangle.NO_BORDER);
            headerTable.addCell(cell);

            document.add(headerTable);

            Chunk line = new Chunk(new LineSeparator(2,100,BaseColor.GRAY,1,0));
            document.add(line);

            Paragraph paragraph1 = new Paragraph("");
            paragraph1.setSpacingAfter(20f);
            document.add(paragraph1);

            String invoice = "INVOICE";
            Phrase invoicePhrase = heading2.process(invoice);
            Paragraph invoicePara = new Paragraph(invoicePhrase);
            invoicePara.setSpacingAfter(20f);
            document.add(invoicePara);

            setParagraphText("Agent", heading3,0,document);

            setParagraphText(loggedAgent.getAgtFirstName()+" " + loggedAgent.getAgtLastName() +"\n"+ loggedAgent.getAgtBusPhone()+"\n"+loggedAgent.getAgtEmail(), normal,10f,document);

            PdfPTable customerInfoTable = new PdfPTable(2);
            customerInfoTable.setWidthPercentage(100);
            customerInfoTable.setSpacingAfter(10f);

            setCellText("Invoice To", heading3, customerInfoTable,"left");
            setCellText("Booking #: " + newBooking.getBookingId(), heading3, customerInfoTable,"right");
            setCellText(cust.getCustFirstName()+" "+cust.getCustLastName()+"\n"+cust.getCustAddress()+"\n"+cust.getCustHomePhone()+"\n"+cust.getCustEmail(), normal, customerInfoTable, "left");
            setCellText("Date: "+df.format(sqlToday), normal, customerInfoTable,"right");
            customerInfoTable.completeRow();
            document.add(customerInfoTable);

            Chunk line2 = new Chunk(new LineSeparator(1,100,color1,1,0));
            document.add(line2);

            Paragraph paragraph2 = new Paragraph("");
            paragraph2.setSpacingAfter(5f);
            document.add(paragraph2);

            setParagraphText("DETAILS",heading4,10f,document);

            PdfPTable detailsTable = new PdfPTable(6);
            detailsTable.setSpacingAfter(0f);
            detailsTable.setWidthPercentage(100);
            detailsTable.setWidths(new int[]{3,1,1,1,1,1});

            setCellText("Description",normal,detailsTable,"left",color2);
            setCellText("Start Date",normal,detailsTable,"center",color2);
            setCellText("End Date",normal,detailsTable,"center",color2);
            setCellText("Qty",normal,detailsTable,"center",color2);
            setCellText("Rate",normal,detailsTable,"center",color2);
            setCellText("Amount",normal,detailsTable,"center",color2);

            setCellText(pkg.getPkgName(), normalBold, detailsTable, "left");
            setCellText(df2.format(pkg.getPkgStartDate()), normal, detailsTable,"center");
            setCellText(df2.format(pkg.getPkgEndDate()), normal, detailsTable,"center");
            setCellText(lblNumTravelers.getText(), normal, detailsTable,"center");
            setCellText(lblBasePrice.getText(), normal, detailsTable,"center");
            setCellText(lblTotalPrice.getText(), normal, detailsTable,"center");

            setCellText(pkg.getPkgDesc(), normal,detailsTable,"left");
            setCellText("", normal,detailsTable,"left");
            setCellText("", normal,detailsTable,"left");
            setCellText("", normal,detailsTable,"left");
            setCellText("", normal,detailsTable,"left");
            setCellText("", normal,detailsTable,"left");

            document.add(detailsTable);

            Chunk line3 = new Chunk(new DottedLineSeparator());
            document.add(line3);

            Paragraph paragraph3 = new Paragraph("");
            paragraph3.setSpacingAfter(5f);
            document.add(paragraph3);

            PdfPTable priceDetails = new PdfPTable(2);
            priceDetails.setWidthPercentage(45);
            priceDetails.setWidths(new int[]{2,1});
            priceDetails.setHorizontalAlignment(2);

            setCellText("Total:",normalBold,priceDetails,"right");
            setCellText(lblTotalPrice.getText(),normalBold,priceDetails,"center");
            setCellText("Amount Received:",normalBold,priceDetails,"right");
            setCellText("$0",normalBold,priceDetails,"center");
            setCellText("Balance Due:",normalBold,priceDetails,"right");
            setCellText(lblTotalPrice.getText(),normalBold,priceDetails,"center");

            document.add(priceDetails);

            Paragraph paragraph4 = new Paragraph("");
            paragraph4.setSpacingAfter(130f);
            document.add(paragraph4);

            setParagraphText("Thank you for trusting Travel Experts.", normal,0f,document,1);
            setParagraphText("We hope you enjoy your next vacation. Let us know if you appreciated our business.", normal,0f,document,1);

        } catch (DocumentException e) {
            e.printStackTrace();
        }
        document.close();
        writer.close();
    }

    private void setParagraphText(String text, FontSelector fselector, float spacing, Document doc) throws DocumentException
    {
        Phrase phrase= fselector.process(text);
        Paragraph para = new Paragraph(phrase);
        para.setSpacingAfter(spacing);
        doc.add(para);
    }

    private void setParagraphText(String text, FontSelector fselector, float spacing, Document doc, int alignement) throws DocumentException
    {
        Phrase phrase= fselector.process(text);
        Paragraph para = new Paragraph(phrase);
        para.setSpacingAfter(spacing);
        para.setAlignment(alignement);
        doc.add(para);
    }

    private void setCellText(String text, FontSelector fSelector, PdfPTable table, String alignement)
    {
        Phrase phrase= fSelector.process(text);
        PdfPCell cell = new PdfPCell(phrase);
        if (alignement=="center")
        {
            cell.setHorizontalAlignment(Element.ALIGN_CENTER);
        }
        else if(alignement=="right")
        {
            cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
        }
        cell.setBorder(Rectangle.NO_BORDER);
        table.addCell(cell);
    }

    private void setCellText(String text, FontSelector fSelector, PdfPTable table, String alignement, BaseColor backgroundColor)
    {
        Phrase phrase= fSelector.process(text);
        PdfPCell cell = new PdfPCell(phrase);
        if (alignement=="center")
        {
            cell.setHorizontalAlignment(Element.ALIGN_CENTER);
        }
        else if(alignement=="right")
        {
            cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
        }
        cell.setBorder(Rectangle.NO_BORDER);
        cell.setBackgroundColor(backgroundColor);
        table.addCell(cell);
    }

    //open pdf
    public void openPdf(File pdfFile){
        if (Desktop.isDesktopSupported()) {
            try {
                Desktop.getDesktop().open(pdfFile);
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else {
            System.out.println("Awt Desktop is not supported!");
        }
    }

    //method to keep the button of a given view (customers,packages,etc.) focused when clicking anywhere in the pane of
    //that view
//    public void keepButtonFocusedonAnchorPaneClicked(Button button)
//    {
//        txtSearchCustomer.setOnMouseClicked(new EventHandler<MouseEvent>() {
//            @Override
//            public void handle(MouseEvent event) {
//                button.setBackground(new Background(new BackgroundFill(javafx.scene.paint.Color.web("#1981E9"), CornerRadii.EMPTY, javafx.geometry.Insets.EMPTY)));
//            }
//        });
//
//        txtSearchPackage.setOnMouseClicked(new EventHandler<MouseEvent>() {
//            @Override
//            public void handle(MouseEvent event) {
//                button.setBackground(new Background(new BackgroundFill(javafx.scene.paint.Color.web("#1981E9"), CornerRadii.EMPTY, javafx.geometry.Insets.EMPTY)));
//            }
//        });
//
//        pnsummary.setOnMouseClicked(new EventHandler<MouseEvent>() {
//            @Override
//            public void handle(MouseEvent event) {
//                button.setBackground(new Background(new BackgroundFill(javafx.scene.paint.Color.web("#1981E9"), CornerRadii.EMPTY, javafx.geometry.Insets.EMPTY)));
//            }
//        });
//
//        btnSummary.setOnMouseClicked(new EventHandler<MouseEvent>() {
//            @Override
//            public void handle(MouseEvent event) {
//                button.setBackground(new Background(new BackgroundFill(javafx.scene.paint.Color.web("#1981E9"), CornerRadii.EMPTY, javafx.geometry.Insets.EMPTY)));
//            }
//        });
//
//        txtNumTravelers.setOnMouseClicked(new EventHandler<MouseEvent>() {
//            @Override
//            public void handle(MouseEvent event) {
//                button.setBackground(new Background(new BackgroundFill(javafx.scene.paint.Color.web("#1981E9"), CornerRadii.EMPTY, javafx.geometry.Insets.EMPTY)));
//            }
//        });
//
//        tvcustomers.setOnMouseClicked(new EventHandler<MouseEvent>() {
//            @Override
//            public void handle(MouseEvent event) {
//                button.setBackground(new Background(new BackgroundFill(Color.web("#1981E9"), CornerRadii.EMPTY, Insets.EMPTY)));
//            }
//        });
//
//        tvpackages.setOnMouseClicked(new EventHandler<MouseEvent>() {
//            @Override
//            public void handle(MouseEvent event) {
//                button.setBackground(new Background(new BackgroundFill(Color.web("#1981E9"), CornerRadii.EMPTY, Insets.EMPTY)));
//            }
//        });
//    }
}
