package dennis.restaurantmanagement.models;

import dennis.restaurantmanagement.connection.DbConnect;

import java.util.ArrayList;

public class Product {
    private int id;
    private int id_category;
    private String name;
    private String image;
    private float price;


    public int getId() {
        return id;
    }

    public int getId_category() {
        return id_category;
    }

    public String getName() {
        return name;
    }

    public String getImage() {
        return image;
    }


    public float getPrice() {
        return price;
    }

    public Product(int id, int id_category, String name, String image, float price) {
        this.id = id;
        this.id_category = id_category;
        this.name = name;
        this.image = image;
        this.price = price;
    }

}
