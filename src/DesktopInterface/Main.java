package DesktopInterface;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Main extends Application {
    Stage window;
    Scene LoginScene;
    Parent LoginGUI;
    @Override
    public void start(Stage primaryStage) throws Exception{
        window = primaryStage;
        // AgentGUI = FXMLLoader.load(getClass().getResource("AgentGUI.fxml"));
        LoginGUI = FXMLLoader.load(getClass().getResource("LoginGUI.fxml"));
        LoginScene = new Scene(LoginGUI);
        window.setScene(LoginScene);
        window.show();
    }


    public static void main(String[] args) {
        launch(args);
    }
}
