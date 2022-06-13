package dennis.restaurantmanagement;

import dennis.restaurantmanagement.connection.DbConnect;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

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
        DbConnect connect = new DbConnect();
        userName = connect.getListAccounts().get(0).getUsername();
        password = connect.getListAccounts().get(0).getPassword();
        System.out.println(userName + " " + password);
    }

    public void login(ActionEvent event){
        ttError.setText("");
        if (ttUsername.getText().isBlank() || ttPassword.getText().isBlank()){
            ttError.setText("Please fill in all blank!");
        }
        if(!ttUsername.getText().equals(userName)  || !ttPassword.getText().equals(password)){
            ttError.setText("Incorrect username or password!");
        }else{
            ttError.setText("Login success!");
            loginSuccess();
        }
    }

    public void loginSuccess(){
        try{
            Parent root = FXMLLoader.load(getClass().getResource("dashboard-view.fxml"));
            Stage dashboardStage = new Stage();
            dashboardStage.initStyle(StageStyle.DECORATED);
            dashboardStage.setScene(new Scene(root, 520, 480));
            dashboardStage.show();

        }catch(Exception e){
            e.printStackTrace();
            e.getCause();
        }
    }

}
