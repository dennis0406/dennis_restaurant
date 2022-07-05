package dennis.restaurantmanagement.models;

import dennis.restaurantmanagement.connection.DbConnect;

public class OrderDetail {
    private int id;
    private int id_product;
    private int id_order;
    private int quantity;
    private float price;

    public String getName_product() {
        return name_product;
    }

    private String name_product;

    public OrderDetail(int id, int id_product, int id_order, int quantity, float price) {
        this.id = id;
        this.id_product = id_product;
        this.id_order = id_order;
        this.quantity = quantity;
        this.price = price;
        this.name_product = setName_product(id_product);
    }
    public String setName_product(int id_product) {
        try{
            var result = DbConnect.getConnection().prepareStatement("SELECT * FROM products").executeQuery();
            while(result.next()){
                if (result.getInt(1)== id_product){
                    return this.name_product = result.getString("name");
                }
            }
        }catch(Exception e){
            throw new Error("Not working! " + e);
        }
        return "not found";
    }
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getId_product() {
        return id_product;
    }

    public void setId_product(int id_product) {
        this.id_product = id_product;
    }

    public int getId_order() {
        return id_order;
    }

    public void setId_order(int id_order) {
        this.id_order = id_order;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public float getPrice() {
        return price;
    }

    public void setPrice(float total) {
        this.price = total;
    }
}
