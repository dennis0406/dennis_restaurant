package dennis.restaurantmanagement.models;

public class Product {
    private int id;
    private int id_category;
    private String name;
    private String image;
    private int quantity;
    private float price;


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getId_category() {
        return id_category;
    }

    public void setId_category(int id) {
        this.id_category = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
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

    public void setPrice(float price) {
        this.price = price;
    }



    public Product(int id, int id_category, String name, String image, int quantity, float price) {
        this.id = id;
        this.id_category = id_category;
        this.name = name;
        this.image = image;
        this.quantity = quantity;
        this.price = price;
    }
}
