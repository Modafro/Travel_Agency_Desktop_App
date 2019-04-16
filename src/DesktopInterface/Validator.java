//Objective: Create a class with static methods to verify various inputs from end-user
package DesktopInterface;

import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

import java.awt.*;
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

    //method to verify if textfield is empty
    public static boolean isEmpty(TextField txtName)
    {
        if (!txtName.getText().isEmpty() || !txtName.getText().trim().isEmpty())
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

    //method to verify if postal code format is valid
    public static boolean isPostalValid(TextField txtName, Label lblName)
    {
        //validate pattern only if textfield not empty
        if(!isEmpty(txtName))
        {
            String Pattern = "^(?!.*[DFIOQU])[A-VXY][0-9][A-Z] ?[0-9][A-Z][0-9]$";
            return regexResult(Pattern, txtName, lblName);
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

    //method to validate if not value for a given comboxbox was not selected
    public static boolean isProvinceValid(ComboBox<String> cbName, Label lblName)
    {
        if (cbName.getSelectionModel().getSelectedIndex() != -1)
        {
            lblName.setVisible(false);
            return true;
        }
        else
        {
            lblName.setVisible(true);
            lblName.setText("Select a value");
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
}
