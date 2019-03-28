//Objective: Create a class with static methods to verify various inputs from end-user
package DesktopInterface;

import javafx.scene.control.TextField;

public class Validator {
    public static boolean isEmpty(TextField txtName)
    {
        if (!txtName.getText().isEmpty() || !txtName.getText().trim().isEmpty())
        {
            return false;
        }
        return true;
    }
}
