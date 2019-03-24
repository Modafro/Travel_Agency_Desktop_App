/*
Login controller, user the following users to login
username: jdelton or jlisle
password: peace
 */

package DesktopInterface;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
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
    void initialize() {
        assert txtUsername != null : "fx:id=\"txtUsername\" was not injected: check your FXML file 'LoginGUI.fxml'.";
        assert txtPassword != null : "fx:id=\"txtPassword\" was not injected: check your FXML file 'LoginGUI.fxml'.";
        assert btnLogin != null : "fx:id=\"btnLogin\" was not injected: check your FXML file 'LoginGUI.fxml'.";
    }

    //Create method for agent to login after successful verification
    public void loginAgent(ActionEvent actionEvent) throws IOException {
        boolean verification;
        Agents agt = new Agents(txtPassword.getText(), txtUsername.getText());
        verification = AgentsQuery.isLoginVerified(agt);
        if (verification){

            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("AgentGUI.fxml"));
            Parent AgentGUI = loader.load();

            Scene AgentScene = new Scene(AgentGUI);

            AgentGUIController controller = loader.getController();
            controller.setUserName(agt);
            //get current stage information
            Stage window = (Stage)((Node)actionEvent.getSource()).getScene().getWindow();

            //switch scenes
            window.setScene(AgentScene);
            window.show();
        }
        else{
            System.out.println("failure");
        }
    }
}

