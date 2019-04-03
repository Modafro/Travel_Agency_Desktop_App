//Objective: Create a class with static methods to verify various inputs from end-user
package DesktopInterface;

import javafx.scene.control.TextField;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Validator {

    //method to verify if textfield is empty
    public static boolean isEmpty(TextField txtName)
    {
        if (!txtName.getText().isEmpty() || !txtName.getText().trim().isEmpty())
        {
            return false;
        }
        return true;
    }

    //method to verify if email format is valid
    public static boolean isEmailValid(TextField txtName)
    {
        String emailPattern = "^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";
        Pattern pattern = Pattern.compile(emailPattern);
        Matcher matcher = pattern.matcher(txtName.getText().trim());
        if(matcher.matches())
        {
            return true;
        }
        return false;
    }
}
