package dennis.restaurantmanagement.connection;

import dennis.restaurantmanagement.models.AdminAccount;
import dennis.restaurantmanagement.models.Category;
import dennis.restaurantmanagement.models.Product;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class DbConnect {
    public Connection conn;
    public Connection getConnection(){
        try{
            Class.forName("com.mysql.cj.jdbc.Driver");
            conn = DriverManager.getConnection("jdbc:mysql://localhost/storemanagement", "root", "");
            System.out.println("Connect database successful!");
        }catch (Exception e){
            e.printStackTrace();
        }
        return conn;
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
                        result.getString(4),
                        result.getString(4),
                        result.getInt(5),
                        result.getFloat(6)
                ));

            }
            System.out.println("Success!");
        }catch(Exception e){
            throw new Error("Not working! " + e);
        }
        return list;
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
}

