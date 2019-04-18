package DesktopInterface;

import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

public class Main extends Application {
    private double xOffset = 0;
    private double yOffset = 0;

    Stage window;
    Scene LoginScene;
    Parent LoginGUI;
    @Override
    public void start(Stage primaryStage) throws Exception{
        window = primaryStage;
        // AgentGUI = FXMLLoader.load(getClass().getResource("AgentGUI.fxml"));
        LoginGUI = FXMLLoader.load(getClass().getResource("Login.fxml"));
        LoginScene = new Scene(LoginGUI);
        window.initStyle(StageStyle.DECORATED.UNDECORATED);

        //make window movable and draggable
        LoginGUI.setOnMousePressed(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                xOffset = event.getSceneX();
                yOffset = event.getSceneY();
            }
        });
        LoginGUI.setOnMouseDragged(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                primaryStage.setX(event.getScreenX() - xOffset);
                primaryStage.setY(event.getScreenY() - yOffset);
            }
        });

        window.setScene(LoginScene);
        window.show();
    }


    public static void main(String[] args) {
        launch(args);
    }
}
