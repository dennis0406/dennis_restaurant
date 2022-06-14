package dennis.restaurantmanagement.models;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class RecordsProducts {
    private final StringProperty id;
    private final StringProperty id_category;
    private final StringProperty name;
    private final StringProperty image;
    private final StringProperty price;

    public RecordsProducts() {
        id = new SimpleStringProperty(this, "id");
        name = new SimpleStringProperty(this, "name");
        id_category = new SimpleStringProperty(this, "id_category");
        image = new SimpleStringProperty(this, "image");
        price = new SimpleStringProperty(this, "price");
    }

    public String getId() {
        return id.get();
    }

    public StringProperty idProperty() {
        return id;
    }

    public void setId(String id) {
        this.id.set(id);
    }

    public String getId_category() {
        return id_category.get();
    }

    public StringProperty id_categoryProperty() {
        return id_category;
    }

    public void setId_category(String id_category) {
        this.id_category.set(id_category);
    }

    public String getName() {
        return name.get();
    }

    public StringProperty nameProperty() {
        return name;
    }

    public void setName(String name) {
        this.name.set(name);
    }

    public String getImage() {
        return image.get();
    }

    public StringProperty imageProperty() {
        return image;
    }

    public void setImage(String image) {
        this.image.set(image);
    }


    public String getPrice() {
        return price.get();
    }

    public StringProperty priceProperty() {
        return price;
    }

    public void setPrice(String price) {
        this.price.set(price);
    }

}
