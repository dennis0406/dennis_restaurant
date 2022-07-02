package dennis.restaurantmanagement.models;

import java.time.LocalDateTime;

public class Order {
    private int id;
    private String tableNumber;
    private float total;
    private LocalDateTime created;

    public Order(int id, String tableNumber, float total, LocalDateTime created) {
        this.id = id;
        this.tableNumber = tableNumber;
        this.total = total;
        this.created = created;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTableNumber() {
        return tableNumber;
    }

    public void setTableNumber(String tableNumber) {
        this.tableNumber = tableNumber;
    }

    public float getTotal() {
        return total;
    }

    public void setTotal(float total) {
        this.total = total;
    }

    public LocalDateTime getCreated() {
        return created;
    }

    public void setCreated(LocalDateTime created) {
        this.created = created;
    }
}
