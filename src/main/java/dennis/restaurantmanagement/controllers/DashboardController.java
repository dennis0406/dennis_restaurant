package dennis.restaurantmanagement.controllers;

import dennis.restaurantmanagement.connection.DbConnect;
import dennis.restaurantmanagement.models.*;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseButton;
import javafx.scene.layout.AnchorPane;

import java.net.URL;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Optional;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;

public class DashboardController implements Initializable {
    // Four button to control the dashboard
    DbConnect conn = new DbConnect();
    public void alert(String message, Alert.AlertType type){
        var alert = new Alert(type, message);
        alert.showAndWait();
    }

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
    @FXML
    public AnchorPane dbOrders;// Container of Order

    // Create tableview for category table
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
    @FXML
    public TextField ttNameCategory;


    ObservableList<Category> selectedCate;
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
    public ChoiceBox<String> choiceCate;

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
    @FXML
    public ImageView imgPreview;


    //Functions of product =============================================================================================
    public void resetText(){
        choiceCate.setValue("Choose category");
        ttName.setText("");
        ttImage.setText("");
        ttPrice.setText("");
    }
    @FXML
    void btnCreate(ActionEvent event) {
        DbConnect connect = new DbConnect();
        if(connect.getIdCategory(choiceCate.getValue())== -1){
            var alert = new Alert(Alert.AlertType.WARNING, "Please choose the category!");
            alert.showAndWait();
        }
        String name = ttName.getText();
        int category = connect.getIdCategory(choiceCate.getValue());
        String image = ttImage.getText();
        float price = Float.parseFloat(ttPrice.getText());

        if(btnCreate.getText().equals("Create")){
            try{
                connect.insertQuery("products", "(id_category, name, image, price)", "("+category+", '"+ name+ "', '"+image+ "', "+ price + ")");
                var alert = new Alert(Alert.AlertType.INFORMATION, "Created new record!");
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
        dbOrders.setVisible(false);
        loadTable();
    }

    //Load data of product table
    public void loadTable(){
        choiceCate.getItems().clear();
        ObservableList<Product> list = FXCollections.observableArrayList(conn.getTableProducts());
        ArrayList<Category> cateList = new ArrayList<>(conn.getListCategories());
        String[] categories = new String[cateList.size()];
        for(int i = 0; i< categories.length; i++){
            categories[i] = cateList.get(i).getName();
        }
        choiceCate.getItems().addAll(categories);

        try{
            idCl.setCellValueFactory( new PropertyValueFactory<>("id"));
            categoryCl.setCellValueFactory( new PropertyValueFactory<>("name_category"));
            nameCl.setCellValueFactory( new PropertyValueFactory<>("name"));
            imageCl.setCellValueFactory( new PropertyValueFactory<>("image"));
            priceCl.setCellValueFactory( new PropertyValueFactory<>("price"));
            tableView.setItems(list);

            //Get the action when click on the row on table
            tableView.setRowFactory(tv -> {
                TableRow<Product> row = new TableRow<>();
                row.setOnMouseClicked(event -> {
                    if (! row.isEmpty() && event.getButton()== MouseButton.PRIMARY
                            && event.getClickCount() == 2) {

                        clickedRow = row.getItem();
                        choiceCate.setValue(conn.getNameCategory(clickedRow.getId_category()));
                        ttName.setText(clickedRow.getName());
                        ttImage.setText(clickedRow.getImage());
                        ttPrice.setText(""+clickedRow.getPrice());
                        btnCreate.setText("Update");
//                        showImageClicked();
                    } else if (!row.isEmpty() || (event.getButton()== MouseButton.PRIMARY
                            && event.getClickCount() == 1)) {
                        btnCreate.setText("Create");
                        resetText();
                        clickedRow = row.getItem();
//                        showImageClicked();
                    }else{
                        btnCreate.setText("Create");
                    }
                });
                return row ;
            });
        }catch (Exception e){
            Logger.getLogger(DashboardController.class.getName()).log(Level.SEVERE, null, e);
        }
    }

    public void showImageClicked(){
        if(clickedRow == null){
            return;
        }
        Image img = new Image(clickedRow.getImage());
        imgPreview.setImage(img);
    }

// Functions of category ===============================================================================================
    public void showCategory(){
        ObservableList<Category> list = FXCollections.observableArrayList(conn.getTableCategories());
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
                        ttNameCategory.setText(clickedRowCate.getName());
                        btnCreateCategory.setText("Update");
                    } else if (row.isEmpty() || (event.getButton()== MouseButton.PRIMARY
                            && event.getClickCount() == 1)) {
                        btnCreateCategory.setText("Create");
                        ttNameCategory.setText("");
                    }
                });
                return row ;
            });
        }catch (Exception e){
            Logger.getLogger(DashboardController.class.getName()).log(Level.SEVERE, null, e);
        }
    }

    @FXML
    void btnCreateCategory(ActionEvent event) {
        DbConnect connect = new DbConnect();
        String name = ttNameCategory.getText();
        if(btnCreateCategory.getText().equals("Create")){
            try{
                connect.insertQuery("categories", "( name)", "( '" + name+ "' )");
                var alert = new Alert(Alert.AlertType.INFORMATION, "Created new record!");
                alert.showAndWait();
                ttNameCategory.setText("");
                showCategory();
            }catch (Exception e){
                e.printStackTrace();
                e.getCause();
            }
        }else{
            try{
                int idEdit = clickedRowCate.getId();
                String clauseSpecifies = " name = '"+ name + "'" ;
                connect.updateQuery("categories", clauseSpecifies, "id = " + idEdit);
                var alert = new Alert(Alert.AlertType.INFORMATION, "Edited a record!");
                alert.showAndWait();
                ttNameCategory.setText("");
                showCategory();
                btnCreateCategory.setText("Create");
            }catch (Exception e){
                e.printStackTrace();
                e.getCause();
            }
        }
    }

    //Delete record in category table
    @FXML
    void btnDeleteCategory(ActionEvent event) {
        selectedCate = tableViewCategory.getSelectionModel().getSelectedItems();
        int idDel = selectedCate.get(0).getId();
        if(tableViewCategory.getSelectionModel().getSelectedItems().get(0) == null){
            return;
        }
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setHeaderText("Are you sure want to delete this category?");
        Optional<ButtonType> option = alert.showAndWait();

        if (option.get() != ButtonType.OK) {
            return;
        }
        try{
            DbConnect connect = new DbConnect();
            connect.deleteQuery("categories", "id = " + idDel);
            showCategory();
            ttNameCategory.setText("");
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    //Functions of Order ==================================================
    // Create tableview for order
    @FXML
    public Button addItem;
    @FXML
    public TextArea note;
    @FXML
    public ChoiceBox<String> choiceItem;
    @FXML
    public TableView<OrderDetail> tableViewOrder;
    @FXML
    public TableColumn<OrderDetail, String> OrderItemCl;
    @FXML
    public TableColumn<OrderDetail, Float> OrderPriceCl;
    @FXML
    public TableColumn<OrderDetail, Integer> OrderQtyCl;
    DateFormat DATEFORMAT = new SimpleDateFormat("yyyy/MM/dd hh/mm/ss");
    String data = DATEFORMAT.format(new Date());

    boolean selectedTable = false;
    Button btnSelected;
    int id_order = 0;
    @FXML
    public TextField qty;
    OrderDetail clickedRowOrder;

    public void resetValueOrder(){
        choiceItem.setValue("Chose the item");
        qty.setText("");
        note.setText("");
    }
    String PATTERN_QTY = "\\d*";
    private void showOrderItem() {
        choiceItem.getItems().clear();
        ArrayList<Product> ProductList = new ArrayList<>(conn.getListProducts());
        String[] products = new String[ProductList.size()];
        for(int i = 0; i< products.length; i++){
            products[i] = ProductList.get(i).getName();
        }
        choiceItem.getItems().addAll(products);
    }

    public void chooseTable(ActionEvent event) {
        Button btn = ((Button)event.getSource());
        if(btnSelected != null){
            btnSelected.setStyle("-fx-background-color: linear-gradient(to right, #f7ff00, #db36a4)");
        }
        btnSelected = btn;
        selectedTable = false;
        changeBgBtn();

        checkOrderExistAndShow();
    }

    private void checkOrderExistAndShow() {
        id_order = conn.getOrderId(btnSelected.getText());
        if(id_order==-1){
            //Mean this table doesn't have any order
            conn.insertQuery(" `orders` ", "(`table`, `total`, `created`)", "('"+ btnSelected.getText()+ "', '0', '"+data+ "')");
        }else{
            showOrderItemTableView();
        }
    }

    private void showOrderItemTableView() {
        ObservableList<OrderDetail> list = FXCollections.observableArrayList(conn.getTableOrderDetails(id_order));

        OrderItemCl.setCellValueFactory( new PropertyValueFactory<>("name_product"));
        OrderPriceCl.setCellValueFactory( new PropertyValueFactory<>("price"));
        OrderQtyCl.setCellValueFactory( new PropertyValueFactory<>("quantity"));
        tableViewOrder.setItems(list);

        tableViewOrder.setRowFactory(tv -> {
            TableRow<OrderDetail> row = new TableRow<>();
            row.setOnMouseClicked(event -> {
                if (! row.isEmpty()) {
                    clickedRowOrder = row.getItem();
                }
            });
            return row ;
        });
    }

    public void changeBgBtn(){
        if (selectedTable){
            btnSelected.setStyle("-fx-background-color: linear-gradient(to right, #f7ff00, #db36a4)");
            selectedTable = false;
        }else{
            btnSelected.setStyle("-fx-background-color: linear-gradient(to right, #00f260, #0575e6)");
            selectedTable = true;
        }
    }

    public void removeItem(ActionEvent event) {
        conn.deleteQuery("orderdetails", "id = " + clickedRowOrder.getId());
        conn.updateQuery("orders",
                " total = '" + conn.getTotalOrder(clickedRowOrder.getId_order())+ "' , `created` = '"+data+"'",
                "id = " + clickedRowOrder.getId_order());
        showOrderItemTableView();
    }
    public void createOrder(ActionEvent event) {
        conn.updateQuery("orders",
                "`created` = '"+data + "', `note` = '"+note.getText()+"', `status` = 'Completed'",
                "id = " + id_order);
        alert("Total amount of this order is "+ conn.getTotalOrder(id_order), Alert.AlertType.INFORMATION);
        resetValueOrder();
        tableViewOrder.getItems().clear();
        checkOrderExistAndShow();
    }
    public void addItem(ActionEvent event) { // Add item to order detail
        int id_prd = conn.getIdProduct((String) choiceItem.getValue());
        String qtyTxt = qty.getText();

        if (btnSelected == null){
            alert("Select the table!", Alert.AlertType.WARNING);
            return;
        }
        if(!qtyTxt.matches(PATTERN_QTY )){ // validate quantity
            alert("Please enter the numeric quantity!", Alert.AlertType.WARNING);
            return;
        } else if (qtyTxt=="") {
            qtyTxt = "1";
        }
        if (id_prd == -1){ //validate item select
            alert("Please choose the item!", Alert.AlertType.WARNING);
            return;
        }

        conn.insertQuery("orderdetails", "(`id_product`, `id_order`, `quantity`, `price`)", "("+id_prd+", "+id_order
                +", "+qtyTxt +" , '"+conn.getPriceProduct(id_prd)+"')");

        conn.updateQuery("orders",
                "`total` = '"+conn.getTotalOrder(id_order)+"', `created` = '"+data+"', `note` = '"+ note.getText()+"'",
                "`table` = '"+btnSelected.getText()+"'");
        resetValueOrder();
        showOrderItemTableView();

    }

    //Button to control the dashboard

    public void btnOrders(ActionEvent actionEvent) {
        dbCategory.setVisible(false);
        dbProduct.setVisible(false);
        dbOrders.setVisible(true);
        showOrderItem();
    }

    public void btnCategories(ActionEvent actionEvent) {
        dbProduct.setVisible(false);
        dbCategory.setVisible(true);
        dbOrders.setVisible(false);
        showCategory();
    }

    public void btnExit(ActionEvent actionEvent) {
        Platform.exit();
        System.exit(0);
    }

    public void btnProducts(ActionEvent event) {
        dbProduct.setVisible(true);
        dbCategory.setVisible(false);
        dbOrders.setVisible(false);
        loadTable();
    }
}
