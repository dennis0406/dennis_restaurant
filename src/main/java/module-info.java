module dennis.restaurantmanagement {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;
    requires javafx.graphics;

    opens dennis.restaurantmanagement to javafx.fxml;
    exports dennis.restaurantmanagement;
    exports dennis.restaurantmanagement.controllers;
    opens dennis.restaurantmanagement.controllers to javafx.fxml;
    opens dennis.restaurantmanagement.models to javafx.base ;
}