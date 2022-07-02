package dennis.restaurantmanagement.models;

public class OrderDetail {
    private int id;
    private int id_product;
    private int id_order;
    private int quantity;
    private float total;

    public OrderDetail(int id, int id_product, int id_order, int quantity, float total) {
        this.id = id;
        this.id_product = id_product;
        this.id_order = id_order;
        this.quantity = quantity;
        this.total = total;
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

    public float getTotal() {
        return total;
    }

    public void setTotal(float total) {
        this.total = total;
    }
}
