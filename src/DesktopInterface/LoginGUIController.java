/*
Login controller, user the following users to login
username: jdelton or jlisle
password: peace
 */

package DesktopInterface;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import DesktopInterface.TravelExpertClasses.AgentsDB;
import DesktopInterface.TravelExpertClasses.Agents;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class LoginGUIController {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private TextField txtUsername;

    @FXML
    private TextField txtPassword;

    @FXML
    private Button btnLogin;

    @FXML
    private Label lblErrorLogin;

    @FXML
    void initialize() {
        assert txtUsername != null : "fx:id=\"txtUsername\" was not injected: check your FXML file 'LoginGUI.fxml'.";
        assert txtPassword != null : "fx:id=\"txtPassword\" was not injected: check your FXML file 'LoginGUI.fxml'.";
        assert btnLogin != null : "fx:id=\"btnLogin\" was not injected: check your FXML file 'LoginGUI.fxml'.";
        assert lblErrorLogin != null : "fx:id=\"lblErrorLogin\" was not injected: check your FXML file 'LoginGUI.fxml'.";

        btnLogin.setDisable(true);
        lblErrorLogin.setVisible(false);
        hideErrorLabel(txtPassword);
        hideErrorLabel(txtUsername);
    }

    //Create method for agent to login after successful verification
    public void loginAgent(ActionEvent actionEvent) throws IOException {
        boolean verification;
        Agents agt = new Agents(txtPassword.getText(), txtUsername.getText());
        verification = AgentsDB.isLoginVerified(agt);
        if (verification){

            //create FXMLLoader object
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("AgentGUI.fxml"));
            Parent AgentGUI = loader.load();

            Scene AgentScene = new Scene(AgentGUI);

            //access methods from AgentGUIController
            AgentGUIController controller = loader.getController();
            controller.setUserName(agt);

            //get current stage information
            Stage window = (Stage)((Node)actionEvent.getSource()).getScene().getWindow();

            //switch scenes
            window.setScene(AgentScene);
            window.show();
        }
        else{
            lblErrorLogin.setVisible(true);
        }
    }

    //Create method to verify if cell is empty on key released from textfield
    public void verifyEmpty(){
        if(!Validator.isEmpty(txtUsername) && !Validator.isEmpty(txtPassword))
        {
            btnLogin.setDisable(false);
        }
        else
        {
            btnLogin.setDisable(true);
        }
    }

    //Create method to remove error message when end-user re-enters values in relevant textfield
    public void hideErrorLabel(TextField txtName)
    {
        txtName.focusedProperty().addListener(new ChangeListener<Boolean>() {
            @Override
            public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) {
                if(newValue)
                {
                    lblErrorLogin.setVisible(false);
                }
            }
        });
    }
}

