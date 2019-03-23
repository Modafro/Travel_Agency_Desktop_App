package DesktopInterface;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

public class AgentGUIController {
    private Agents loggedAgent;

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private Label lblWelcome;

    @FXML
    void initialize() {
        assert lblWelcome != null : "fx:id=\"lblWelcome\" was not injected: check your FXML file 'AgentGUI.fxml'.";
    }


    public void setUserName(Agents agent)
    {
        loggedAgent = agent;
        lblWelcome.setText("Welcome back " +loggedAgent.getAgtFirstName()+ " " + loggedAgent.getAgtLastName());
    }
}
