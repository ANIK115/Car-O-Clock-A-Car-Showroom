module Car.Showroom {
    requires javafx.fxml;
    requires javafx.controls;

    opens client;
    opens cardatabase;
    opens util;
    opens server;
}