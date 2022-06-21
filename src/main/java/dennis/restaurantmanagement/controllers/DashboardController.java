package dennis.restaurantmanagement.controllers;

import dennis.restaurantmanagement.MainController;
import dennis.restaurantmanagement.connection.DbConnect;
import dennis.restaurantmanagement.models.Category;
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
    // Four button to control the dashboard
    @FXML
    public Button btnProducts;
    @FXML
    public Button btnExit;
    @FXML
    public Button btnCategories;
    @FXML
    public Button btnOrders;

    @FXML
    public AnchorPane dbProduct;//Container of product
    @FXML
    public AnchorPane dbCategory;//Container of category

    //Create tableview for category table
    @FXML
    public TableView<Category> tableViewCategory;
    @FXML
    public TableColumn<Category, Integer> idClCate;
    @FXML
    public TableColumn<Category, String> nameClCate;
    @FXML
    public Button btnDeleteCategory;
    @FXML
    public Button btnCreateCategory;
    ObservableList<Product> selectedCate;
    Category clickedRowCate;

    //Properties of product
    @FXML
    public TableView<Product> tableView;
    @FXML
    public TableColumn<Product, Integer> idCl;
    @FXML
    public TableColumn<Product, Integer> categoryCl;
    @FXML
    public TableColumn<Product, String> imageCl;

    @FXML
    public TableColumn<Product, String> nameCl;

    @FXML
    public TableColumn<Product, Float> priceCl;

    @FXML
    public TextField ttCategory;

    @FXML
    public TextField ttImage;

    @FXML
    public TextField ttName;

    @FXML
    public TextField ttPrice;

    ObservableList<Product> selected;
    Product clickedRow;

    @FXML
    public Button btnDelete;
    @FXML
    public Button btnCreate;


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

    //Load data of product table
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


    public void show(){
        ObservableList<Category> list = FXCollections.observableArrayList(DbConnect.getTableCategories());
        try{
            idClCate.setCellValueFactory( new PropertyValueFactory<Category, Integer>("id"));
            nameClCate.setCellValueFactory( new PropertyValueFactory<Category, String>("name"));
            tableViewCategory.setItems(list);

            //Get the action when click on the row on table
            tableViewCategory.setRowFactory(tv -> {
                TableRow<Category> row = new TableRow<>();
                row.setOnMouseClicked(event -> {
                    if (! row.isEmpty() && event.getButton()== MouseButton.PRIMARY
                            && event.getClickCount() == 2) {
                        clickedRowCate = row.getItem();
                        ttName.setText(clickedRowCate.getName());
                        btnCreateCategory.setText("Update");
                    } else if (row.isEmpty() || (event.getButton()== MouseButton.PRIMARY
                            && event.getClickCount() == 1)) {
                        btnCreateCategory.setText("Create");
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
