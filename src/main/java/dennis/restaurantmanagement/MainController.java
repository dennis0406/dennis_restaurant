package dennis.restaurantmanagement;

import dennis.restaurantmanagement.connection.DbConnect;
import dennis.restaurantmanagement.models.AdminAccount;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.stage.Window;

import java.util.List;

public class MainController {

    @FXML
    private TextField ttUsername;
    @FXML
    private PasswordField ttPassword;
    @FXML
    private Label ttError;
    @FXML
    private Button btnLogin;

    private String userName;
    private String password;

    public MainController() {
        AdminAccount adminAccount = new AdminAccount();
        userName = adminAccount.getUsername();
        password = adminAccount.getPassword();
    }

    public void login(ActionEvent event){
        ttError.setText("");
        if (ttUsername.getText().isBlank() || ttPassword.getText().isBlank()){
            ttError.setText("Please fill in all blank!");
        } else if (!ttUsername.getText().equals(userName)  || !ttPassword.getText().equals(password)){
            ttError.setText("Incorrect username or password!");
        }else{
            var alert = new Alert(Alert.AlertType.INFORMATION, "Login success!");
            alert.showAndWait();
            loginSuccess();
        }
    }

    public void loginSuccess(){
        try{

            Parent root = FXMLLoader.load(getClass().getResource("dashboard-view.fxml"));
            Stage dashboardStage = new Stage();
            dashboardStage.initStyle(StageStyle.DECORATED);
            dashboardStage.setScene(new Scene(root, 920, 532));
            dashboardStage.show();

        }catch(Exception e){
            e.printStackTrace();
            e.getCause();
        }
    }

}
