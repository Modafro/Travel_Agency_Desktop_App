/**
 * Author: Mo Sagnia
 * Date: April 2019
 * Objective: Create a class with static methods to verify various inputs from end-user
 */




package DesktopInterface;

import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

import java.awt.*;
import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;
import java.util.logging.SimpleFormatter;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Validator {

    //method to return true or false for a given regex and set relevant error label visible/false
    public static boolean regexResult(String regexPattern, TextField txtName, Label lblNameError)
    {
        Pattern pattern = Pattern.compile(regexPattern);
        Matcher matcher = pattern.matcher(txtName.getText().trim());
        if(matcher.matches())
        {
            lblNameError.setVisible(false);
            return true;
        }
        else
        {
            lblNameError.setVisible(true);
            lblNameError.setText("Format invalid");
            return false;
        }
    }

    public static boolean regexResult(String regexPattern, String txtName)
    {
        Pattern pattern = Pattern.compile(regexPattern);
        Matcher matcher = pattern.matcher(txtName.trim());
        if(matcher.matches())
        {

            return true;
        }
        else
        {
            return false;
        }
    }

    //method to verify if textfield is empty
    public static boolean isEmpty(TextField txtName)
    {
        if (!txtName.getText().isEmpty() || !txtName.getText().trim().isEmpty())
        {
            return false;
        }
        return true;
    }

    public static boolean isEmpty(String txtName)
    {
        if (!txtName.isEmpty() || !txtName.trim().isEmpty())
        {
            return false;
        }
        return true;
    }

    //method to verify if textfield is empty and set relevant label visible true/false
    public static boolean isEmpty(TextField txtName, Label lblNameError)
    {
        if (!txtName.getText().isEmpty() || !txtName.getText().trim().isEmpty())
        {
            lblNameError.setVisible(false);
            return false;
        }
        else
        {
            lblNameError.setVisible(true);
            lblNameError.setText("Field required");
            return true;
        }
    }

    //method to validate date format
    public static boolean isDateValid(DatePicker dpName, Label lblNameError)
    {
        if(dpName.getValue() != null)
        {
            lblNameError.setVisible(false);
            return true;
        }
        else
        {
            lblNameError.setVisible(true);
            lblNameError.setText("Please enter a valid format: 01/31/2000");
            return false;
        }
    }

    //method to validate if date is before today's date
    public static boolean isDateAfterToday(DatePicker dpName, Label lblNameError)
    {
        Date dateToValidate = DateConverter.FromDatePickerToUtilDate(dpName);
        Date todayDate = DateConverter.FromLocalDateToUtilDate(LocalDate.now());

        if(dateToValidate.compareTo(todayDate) >= 0)
        {
            lblNameError.setVisible(false);
            return true;
        }
        else
        {
            lblNameError.setVisible(true);
            lblNameError.setText("Date must be today or after today's date");
            return false;
        }
    }

    //method to validate if a date is after a specific date (in the context of this project, after the start date of package)
    public static boolean isDateAfterSpecificDate (DatePicker dpSpecificDate, DatePicker dpName, Label lblNameError)
    {
        Date dateSpecific = DateConverter.FromDatePickerToUtilDate(dpSpecificDate);
        Date dateToValidate= DateConverter.FromDatePickerToUtilDate(dpName);

        if(dateToValidate.compareTo(dateSpecific) > 0)
        {
           lblNameError.setVisible(false);
           return true;
        }
        else
        {
            lblNameError.setVisible(true);
            lblNameError.setText("Date must be after start date");
            return false;
        }
    }

    public static boolean isDateAfterSpecificDate (Date dpSpecificDate, Date dpName)
    {
        //Date dateSpecific = DateConverter.FromDatePickerToUtilDate(dpSpecificDate);
        //Date dateToValidate= DateConverter.FromDatePickerToUtilDate(dpName);

        if(dpName.compareTo(dpSpecificDate) > 0)
        {

            return true;
        }
        else
        {
            return false;
        }
    }

    public static boolean isDateAfterSpecificDate (DatePicker dpSpecificDate, DatePicker dpName)
    {
        Date dateSpecific = DateConverter.FromDatePickerToUtilDate(dpSpecificDate);
        Date dateToValidate= DateConverter.FromDatePickerToUtilDate(dpName);

        if(dateToValidate.compareTo(dateSpecific) > 0)
        {
            //lblNameError.setVisible(false);
            return true;
        }
        else
        {
            //lblNameError.setVisible(true);
           // lblNameError.setText("Date must be after start date");
            return false;
        }
    }

    //method to verify if email format is valid and set relevant error label visible true/false
    public static boolean isEmailValid(TextField txtName, Label lblNameError)
    {
        //validate pattern only if textfield not empty
        if(!isEmpty(txtName))
        {
            String Pattern = "^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";
            return regexResult(Pattern, txtName, lblNameError);
        }
        //return true if textfield empty (run "isEmpty" method before current method if textfield is not to be empty)
        else
        {
            return true;
        }
    }

    public static boolean isEmailValid(String txtName)
    {
        //validate pattern only if textfield not empty
        if(!isEmpty(txtName))
        {
            String Pattern = "^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";
            return regexResult(Pattern, txtName);
        }
        //return true if textfield empty (run "isEmpty" method before current method if textfield is not to be empty)
        else
        {
            return true;
        }
    }
    //method to verify if postal code format is valid
    public static boolean isPostalValid(TextField txtName, Label lblName)
    {
        //validate pattern only if textfield not empty
        if(!isEmpty(txtName))
        {
            String Pattern = "^([A-Za-z]\\d[A-Za-z][- ]?\\d[A-Za-z]\\d)+$";
            return regexResult(Pattern, txtName, lblName);
        }
        //return true if textfield empty (run "isEmpty" method before current method if textfield is not to be empty)
        else
        {
            return true;
        }
    }

    public static boolean isPostalValid(String txtName)
    {
        //validate pattern only if textfield not empty
        if(!isEmpty(txtName))
        {
            String Pattern = "^([A-Za-z]\\d[A-Za-z][- ]?\\d[A-Za-z]\\d)+$";
            return regexResult(Pattern, txtName);
        }
        //return true if textfield empty (run "isEmpty" method before current method if textfield is not to be empty)
        else
        {
            return true;
        }
    }

    //method to verify if phone number format is valid
    public static boolean isPhoneValid(TextField txtName, Label lblName)
    {
        //validate pattern only if textfield not empty
        if(!isEmpty(txtName))
        {
            String Pattern = "^\\(?([0-9]{3})\\)?[-.\\s]?([0-9]{3})[-.\\s]?([0-9]{4})$";
            return regexResult(Pattern, txtName, lblName);
        }
        //return true if textfield empty (run "isEmpty" method before current method if textfield is not to be empty)
        else
        {
            return true;
        }
    }

    public static boolean isPhoneValid(String txtName)
    {
        //validate pattern only if textfield not empty
        if(!isEmpty(txtName))
        {
            String Pattern = "^\\(?([0-9]{3})\\)?[-.\\s]?([0-9]{3})[-.\\s]?([0-9]{4})$";
            return regexResult(Pattern, txtName);
        }
        //return true if textfield empty (run "isEmpty" method before current method if textfield is not to be empty)
        else
        {
            return true;
        }
    }

    //method to verify if textfield contains letters and specific characters allowed in a person's name
    //and set relevant error label visible true/false
    public static boolean isNameValid(TextField txtName, Label lblName)
    {
        //validate pattern only if textfield not empty
        if(!isEmpty(txtName))
        {
            String Pattern = "^[a-zA-Z -'.]+$";
            return regexResult(Pattern, txtName, lblName);
        }
        //return true if textfield empty (run "isEmpty" method before current method if textfield is not to be empty)
        else
        {
            return true;
        }
    }

    public static boolean isNameValid(String txtName)
    {
        //validate pattern only if textfield not empty
        if(!isEmpty(txtName))
        {
            String Pattern = "^[a-zA-Z -'.]+$";
            return regexResult(Pattern, txtName);
        }
        //return true if textfield empty (run "isEmpty" method before current method if textfield is not to be empty)
        else
        {
            return true;
        }
    }

    //method to validate if value for a given comboxbox was selected
    public static boolean isProvinceValid(ComboBox<String> cbName, Label lblName)
    {
        if (cbName.getSelectionModel().getSelectedItem() != "Prov")
        {
            lblName.setVisible(false);
            return true;
        }
        else
        {
            lblName.setVisible(true);
            lblName.setText("Select a province");
            return false;
        }
    }

    public static boolean isProvinceValid(String txtName)
    {
        if(txtName.equals("AB") ||txtName.equals("BC") ||txtName.equals("MB") ||txtName.equals("NB") ||txtName.equals("NL") ||txtName.equals("NT") ||txtName.equals("NS") ||
                txtName.equals("NU") ||txtName.equals("ON") ||txtName.equals("PE") ||txtName.equals("QC") ||txtName.equals("SK") ||txtName.equals("YT"))
        {
            return true;
        }
        else
        {
            return false;
        }
    }

    //method to validate if input is a positive integer
    public static boolean isPositiveInteger(TextField txtName)
    {

        if(!isEmpty(txtName))
        {
            try {
               int isNumber = Integer.parseInt(txtName.getText());

               if (isNumber>0)
               {
                   return true;
               }
               else
               {
                   return false;
               }

            } catch (NumberFormatException e) {
                return false; //if an exception is caught, parseInt is false
            }
            //return false;
        }
        //return true if textfield empty (run "isEmpty" method before current method if textfield is not to be empty)
        else
        {
            return true;
        }
    }

    //method to validate if input is a positive decimal
    public static boolean isPositiveDouble(TextField txtName, Label lblNameError)
    {

        if(!isEmpty(txtName))
        {
            try {
                double isNumber = Double.parseDouble(txtName.getText());

                if (isNumber>=0)
                {
                    return true;
                }
                else
                {
                    return false;
                }

            } catch (NumberFormatException e) {
                lblNameError.setVisible(true);
                lblNameError.setText("Format Invalid");
                return false; //if an exception is caught, parseInt is false
            }
            //return false;
        }
        //return true if textfield empty (run "isEmpty" method before current method if textfield is not to be empty)
        else
        {
            return true;
        }
    }

    public static boolean isPositiveDouble(String txtName)
    {

        if(!isEmpty(txtName))
        {
            try {
                double isNumber = Double.parseDouble(txtName);

                if (isNumber>=0)
                {
                    return true;
                }
                else
                {
                    return false;
                }

            } catch (NumberFormatException e) {
                return false; //if an exception is caught, parseInt is false
            }
            //return false;
        }
        //return true if textfield empty (run "isEmpty" method before current method if textfield is not to be empty)
        else
        {
            return false;
        }
    }
}
