package dennis.restaurantmanagement.controllers;

import dennis.restaurantmanagement.MainController;
import dennis.restaurantmanagement.connection.DbConnect;
import dennis.restaurantmanagement.models.Product;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseButton;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;

public class DashboardController implements Initializable {
    public Button btnProducts;
    public Button btnExit;
    public Button btnCategories;
    public Button btnOrders;
    public AnchorPane dbProduct;
    public AnchorPane dbCategory;
    MainController mainController = new MainController();
    ObservableList<Product> selected;
    Product clickedRow;

    @FXML
    private Button btnDelete;
    @FXML
    private Button btnCreate;
    @FXML
    private TableView<Product> tableView;
    @FXML
    private TableColumn<Product, Integer> idCl;
    @FXML
    private TableColumn<Product, Integer> categoryCl;
    @FXML
    private TableColumn<Product, String> imageCl;

    @FXML
    private TableColumn<Product, String> nameCl;

    @FXML
    private TableColumn<Product, Float> priceCl;

    @FXML
    private TextField ttCategory;

    @FXML
    private TextField ttImage;

    @FXML
    private TextField ttName;

    @FXML
    private TextField ttPrice;

    public void resetText(){
        ttCategory.setText("");
        ttName.setText("");
        ttImage.setText("");
        ttPrice.setText("");
    }
    @FXML
    void btnCreate(ActionEvent event) {
        DbConnect connect = new DbConnect();
        String name = ttName.getText();
        String category = ttCategory.getText();
        String image = ttImage.getText();
        float price = Float.parseFloat(ttPrice.getText());

        if(btnCreate.getText().equals("Create")){
            try{
                connect.insertQuery("products", "(id_category, name, image, price)", "("+category+", '"+ name+ "', '"+image+ "', "+ price + ")");
                var alert = new Alert(Alert.AlertType.INFORMATION, "Created new record!");
//            mainController.dashboardScreen();
                alert.showAndWait();
                resetText();
                loadTable();
            }catch (Exception e){
                e.printStackTrace();
                e.getCause();
            }
        }else{
            try{
                int idEdit = clickedRow.getId();
                String clauseSpecifies = "id_category = " + category + ", name = '"+ name +"', image = '"+ image +"', price = " + price;
                System.out.println(clauseSpecifies);
                connect.updateQuery("products", clauseSpecifies, "id = " + idEdit);
                var alert = new Alert(Alert.AlertType.INFORMATION, "Edited a record!");
                alert.showAndWait();
                resetText();
                loadTable();
                btnCreate.setText("Create");
            }catch (Exception e){
                e.printStackTrace();
                e.getCause();
            }
        }
    }

    @FXML
    void btnDelete(ActionEvent event) {
        selected = tableView.getSelectionModel().getSelectedItems();
        int idDel = selected.get(0).getId();
        if(tableView.getSelectionModel().getSelectedItems().get(0) == null){
            return;
        }
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setHeaderText("Are you sure want to delete this product?");
        Optional<ButtonType> option = alert.showAndWait();

        if (option.get() != ButtonType.OK) {
            return;
        }
        try{
            DbConnect connect = new DbConnect();
            connect.deleteQuery("products", "id = " + idDel);
            loadTable();
            resetText();
        }catch (Exception e){
            e.printStackTrace();
        }

    }


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        dbCategory.setVisible(false);
       loadTable();
    }

    public void loadTable(){
        ObservableList<Product> list = FXCollections.observableArrayList(DbConnect.getTableProducts());
        try{
            idCl.setCellValueFactory( new PropertyValueFactory<Product, Integer>("id"));
            categoryCl.setCellValueFactory( new PropertyValueFactory<Product, Integer>("id_category"));
            nameCl.setCellValueFactory( new PropertyValueFactory<Product, String>("name"));
            imageCl.setCellValueFactory( new PropertyValueFactory<Product, String>("image"));
            priceCl.setCellValueFactory( new PropertyValueFactory<Product, Float>("price"));
            tableView.setItems(list);

            //Get the action when click on the row on table
            tableView.setRowFactory(tv -> {
                TableRow<Product> row = new TableRow<>();
                row.setOnMouseClicked(event -> {
                    if (! row.isEmpty() && event.getButton()== MouseButton.PRIMARY
                            && event.getClickCount() == 2) {

                        clickedRow = row.getItem();
                        ttCategory.setText(""+clickedRow.getId_category());
                        ttName.setText(clickedRow.getName());
                        ttImage.setText(clickedRow.getImage());
                        ttPrice.setText(""+clickedRow.getPrice());
                        btnCreate.setText("Update");
                    } else if (row.isEmpty() || (event.getButton()== MouseButton.PRIMARY
                            && event.getClickCount() == 1)) {
                        btnCreate.setText("Create");
                        resetText();
                    }
                });
                return row ;
            });
        }catch (Exception e){
            Logger.getLogger(DashboardController.class.getName()).log(Level.SEVERE, null, e);
        }

    }

    public void btnOrders(ActionEvent actionEvent) {
        dbCategory.setVisible(false);
        dbProduct.setVisible(false);
    }

    public void btnCategories(ActionEvent actionEvent) {
        dbProduct.setVisible(false);
        dbCategory.setVisible(true);
    }

    public void btnExit(ActionEvent actionEvent) {
        Platform.exit();
        System.exit(0);
    }

    public void btnProducts(ActionEvent event) {
        dbProduct.setVisible(true);
        dbCategory.setVisible(false);
    }

    public void btnCreateCategory(ActionEvent event) {
    }

    public void btnDeleteCategory(ActionEvent event) {
    }
}
