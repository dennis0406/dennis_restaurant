package dennis.restaurantmanagement.controllers;

import dennis.restaurantmanagement.connection.DbConnect;
import dennis.restaurantmanagement.models.Category;
import dennis.restaurantmanagement.models.Product;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.scene.control.TableRow;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseButton;

import java.util.logging.Level;
import java.util.logging.Logger;

public class CategoryController extends DashboardController {
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
}
