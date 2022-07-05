package dennis.restaurantmanagement.connection;

import dennis.restaurantmanagement.models.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class DbConnect {
    public static Connection conn;
    public static Connection getConnection(){
        try{
            Class.forName("com.mysql.cj.jdbc.Driver");
            conn = DriverManager.getConnection("jdbc:mysql://localhost/storemanagement", "root", "");
            System.out.println("Connect database successful!");
        }catch (Exception e){
            e.printStackTrace();
        }
        return conn;
    }
    //Insert query
    public void insertQuery( String table, String field, String values){
        String sql = "INSERT INTO  "+table +" "+ field +" VALUE " + values;
        try{
            getConnection().prepareStatement(sql).executeUpdate();
            System.out.println("Added a new record!");
        }catch (SQLException e){
            throw new RuntimeException(e);
        }
    }

    //delete query
    public void deleteQuery(String table, String condition){
        String sql = "DELETE FROM "+table+" WHERE " + condition;

        try{
            getConnection().prepareStatement(sql).executeUpdate();
            System.out.println("Deleted a record!");
        }catch (SQLException e){
            throw new RuntimeException(e);
        }
    }

    //update query
    public void updateQuery(String table, String clauseSpecifies, String condition){
        String sql = "UPDATE " + table +
                " SET " + clauseSpecifies +
                "WHERE "+ condition;

        try{
            getConnection().prepareStatement(sql).executeUpdate();
            System.out.println("Updated a record!");
        }catch (SQLException e){
            throw new RuntimeException(e);
        }
    }

    //get list product
    public List<Product> getListProducts(){
        ArrayList<Product> list = new ArrayList<>();
        try{
            var result = getConnection().prepareStatement("SELECT * FROM products").executeQuery();
            while(result.next()){
                list.add(new Product(
                        result.getInt(1),
                        result.getInt(2),
                        result.getString(3),
                        result.getString(4),
                        result.getFloat(5)
                ));

            }
            System.out.println("Success!");
        }catch(Exception e){
            throw new Error("Not working! " + e);
        }
        return list;
    }

    //Get table products
    public static ObservableList<Product> getTableProducts(){
        Connection conn = getConnection();
        ObservableList<Product> productList = FXCollections.observableArrayList();
        try{
            PreparedStatement ps = conn.prepareStatement("SELECT * FROM products");
            ResultSet rs = ps.executeQuery();
            while(rs.next()) {
                productList.add(new Product(
                        Integer.parseInt(rs.getString("id")),
                        Integer.parseInt(rs.getString("id_category")),
                        rs.getString("name"),
                        rs.getString("image"),
                        Float.parseFloat(rs.getString("price"))
                ));
            }
        }catch (Exception e){
            e.printStackTrace();
            e.getCause();
        }
        return productList;
    }

    //Get table categories
    public static ObservableList<Category> getTableCategories(){
        Connection conn = getConnection();
        ObservableList<Category> CategoryList = FXCollections.observableArrayList();
        try{
            PreparedStatement ps = conn.prepareStatement("SELECT * FROM categories");
            ResultSet rs = ps.executeQuery();
            while(rs.next()) {
                CategoryList.add(new Category(
                        Integer.parseInt(rs.getString("id")),
                        rs.getString("name")
                ));
            }
        }catch (Exception e){
            e.printStackTrace();
            e.getCause();
        }
        return CategoryList;
    }


    //get list of categories
    public List<Category> getListCategories(){
        ArrayList<Category> list = new ArrayList<>();
        try{
            var result = getConnection().prepareStatement("SELECT * FROM categories").executeQuery();
            while(result.next()){
                list.add(new Category(
                        result.getInt(1),
                        result.getString(2)
                ));

            }
            System.out.println("Success!");
        }catch(Exception e){
            throw new Error("Not working! " + e);
        }
        return list;
    }

    //Get id category
    public int getIdCategory(String nameCate){
        try{
            var result = getConnection().prepareStatement("SELECT * FROM categories").executeQuery();
            while(result.next()){
                if (result.getString(2).equals(nameCate)){
                    return result.getInt("id");
                }
            }
        }catch(Exception e){
            throw new Error("Not working! " + e);
        }
        return -1;
    }

    public static ResultSet queryReturn(String table){
        try{
            return getConnection().prepareStatement("SELECT * FROM " + table).executeQuery();
        }catch(Exception e){
            throw new Error("Not working! " + e);
        }
    }

    //Get id product
    public int getIdProduct(String namePrd){
        try{
            var result = getConnection().prepareStatement("SELECT * FROM products").executeQuery();
            while(result.next()){
                if (result.getString(3).equals(namePrd)){
                    return result.getInt("id");
                }
            }
        }catch(Exception e){
            throw new Error("Not working! " + e);
        }
        return -1;
    }
    //Get price product
    public float getPriceProduct(int id){
        try{
            var result = getConnection().prepareStatement("SELECT * FROM products").executeQuery();
            while(result.next()){
                if (result.getInt(1)== id){
                    return result.getFloat("price");
                }
            }
        }catch(Exception e){
            throw new Error("Not working! " + e);
        }
        return 0;
    }
    //Get name category
    public String getNameCategory(int id){
        try{
            var result = getConnection().prepareStatement("SELECT * FROM categories").executeQuery();
            while(result.next()){
                if (result.getInt("id")== id){
                    return result.getString("name");
                }
            }
        }catch(Exception e){
            throw new Error("Not working! " + e);
        }
        return null;
    }


    //get list of accounts
    public List<AdminAccount> getListAccounts(){
        ArrayList<AdminAccount> list = new ArrayList<>();
        try{
            var result = getConnection().prepareStatement("SELECT * FROM accounts").executeQuery();
            while(result.next()){
                list.add(new AdminAccount(
                        result.getInt(1),
                        result.getString(2),
                        result.getString(3)
                ));

            }
            System.out.println("Success!");
        }catch(Exception e){
            throw new Error("Not working! " + e);
        }
        return list;
    }

    //Get table orders
    public static ObservableList<Order> getTableOrders(){
        Connection conn = getConnection();
        ObservableList<Order> orderList = FXCollections.observableArrayList();
        try{
            PreparedStatement ps = conn.prepareStatement("SELECT * FROM orders");
            ResultSet rs = ps.executeQuery();
            while(rs.next()) {
                orderList.add(new Order(
                        Integer.parseInt(rs.getString("id")),
                        rs.getString("table"),
                        Float.parseFloat(rs.getString("total")),
                        LocalDateTime.parse(rs.getString("created")),
                        rs.getString("note")
                ));
            }
        }catch (Exception e){
            e.printStackTrace();
            e.getCause();
        }
        return orderList;
    }

    //Get table orderDetails
    public static ObservableList<OrderDetail> getTableOrderDetails(int id_order){
        Connection conn = getConnection();
        ObservableList<OrderDetail> orderDetailList = FXCollections.observableArrayList();
        try{
            PreparedStatement ps = conn.prepareStatement("SELECT * FROM orderdetails");
            ResultSet rs = ps.executeQuery();
            while(rs.next()) {
                if(rs.getInt(3)==id_order){
                    orderDetailList.add(new OrderDetail(
                            Integer.parseInt(rs.getString("id")),
                            Integer.parseInt(rs.getString("id_product")),
                            Integer.parseInt(rs.getString("id_order")),
                            Integer.parseInt(rs.getString("quantity")),
                            Float.parseFloat(rs.getString("price"))
                    ));
                }
            }
        }catch (Exception e){
            e.printStackTrace();
            e.getCause();
        }
        return orderDetailList;
    }

    //Get order Item
    public int getOrderId(String table){
        try{
            var result = getConnection().prepareStatement("SELECT * FROM orders").executeQuery();
            while(result.next()){
                if (result.getString("table").equals(table)){
                    return result.getInt("id");
                }
            }
        }catch(Exception e){
            throw new Error("Not working! " + e);
        }
        return -1;
    }

    public Float getTotalOrder(int id_order) {
        try{
            var result = getConnection().prepareStatement("SELECT SUM(quantity*price) FROM `orderdetails` WHERE id_order = "+id_order+";").executeQuery();
            while(result.next()){
                return result.getFloat(1);
            }
        }catch(Exception e){
            throw new Error("Not working! " + e);
        }
        return null;
    }
}

