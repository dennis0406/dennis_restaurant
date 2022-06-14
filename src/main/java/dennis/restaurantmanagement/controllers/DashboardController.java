package dennis.restaurantmanagement.controllers;

import dennis.restaurantmanagement.MainController;
import dennis.restaurantmanagement.connection.DbConnect;
import dennis.restaurantmanagement.models.Product;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseButton;

import java.net.URL;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;

public class DashboardController implements Initializable {
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
        DbConnect connect = new DbConnect();
        connect.deleteQuery("products", "id = " + idDel);
        loadTable();
    }


    @Override
    public void initialize(URL location, ResourceBundle resources) {
//        idCl.setCellValueFactory(features -> features.getValue().nameProperty());
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
                    }
                });
                return row ;
            });
        }catch (Exception e){
            Logger.getLogger(DashboardController.class.getName()).log(Level.SEVERE, null, e);
        }

    }
}
