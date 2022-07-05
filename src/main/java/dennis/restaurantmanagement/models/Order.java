package dennis.restaurantmanagement.models;

import java.time.LocalDateTime;

public class Order {
    private int id;
    private String table;
    private float total;
    private LocalDateTime created;
    private String note;

    public Order(int id, String table, float total, LocalDateTime created, String note) {
        this.id = id;
        this.table = table;
        this.total = total;
        this.created = created;
        this.note = note;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTableNumber() {
        return table;
    }

    public void setTableNumber(String tableNumber) {
        this.table = tableNumber;
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

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }
}
