package dennis.restaurantmanagement;

import dennis.restaurantmanagement.connection.DbConnect;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.io.IOException;

public class MainApplication extends Application {
    public Stage window;

    @Override
    public void start(Stage primaryStage) throws IOException {
        window = primaryStage;
        Parent root = FXMLLoader.load(getClass().getResource("login-view.fxml"));
        window.initStyle(StageStyle.DECORATED);
        window.setScene(new Scene(root));
        window.centerOnScreen();
        window.show();
    }

    public static void main(String[] args) {
        launch();
    }
}