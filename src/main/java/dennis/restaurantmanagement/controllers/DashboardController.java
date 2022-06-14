package dennis.restaurantmanagement.controllers;

import dennis.restaurantmanagement.connection.DbConnect;
import dennis.restaurantmanagement.models.Product;
import dennis.restaurantmanagement.models.RecordsProducts;
import javafx.beans.Observable;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.HBox;

import java.sql.PreparedStatement;
import java.util.ArrayList;

public class DashboardController {

    PreparedStatement pst;
    @FXML
    private Button btnCreate;
    @FXML
    private TableView<?> table;
    @FXML
    private TableColumn<?, ?> categoryCl;

    @FXML
    private TableColumn<?, ?> imageCl;

    @FXML
    private TableColumn<?, ?> nameCl;

    @FXML
    private TableColumn<?, ?> priceCl;

    @FXML
    private TableColumn<?, ?> quantityCl;

    @FXML
    private TextField ttCategory;

    @FXML
    private TextField ttImage;

    @FXML
    private TextField ttName;

    @FXML
    private TextField ttPrice;

    @FXML
    void btnCreate(ActionEvent event) {
        DbConnect connect = new DbConnect();
        String name = ttName.getText();
        String category = ttCategory.getText();
        String image = ttImage.getText();
        float price = Float.parseFloat(ttPrice.getText());

        try{
            connect.insertQuery("products", "(id_category, name, image, price)", "("+category+", '"+ name+ "', '"+image+ "', "+ price + ")");
            var alert = new Alert(Alert.AlertType.INFORMATION, "Created new record!");
            alert.showAndWait();
        }catch (Exception e){
            e.printStackTrace();
            e.getCause();
        }
    }

    public void table(){
        ObservableList<RecordsProducts> records = FXCollections.observableArrayList();
    }

    public DashboardController(){
        DbConnect connect = new DbConnect();
        ArrayList<Product> products = new ArrayList<>(connect.getListProducts());
        for (Product product : products) {

        }
    }
}
