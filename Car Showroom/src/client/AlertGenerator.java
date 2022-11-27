package client;

import javafx.scene.control.Alert;

//this class generates alerts only

public class AlertGenerator {

    public static void generateAlert(Alert alert, String title, String context) {
        alert.setTitle(title);
        alert.setHeaderText(title);
        alert.setContentText(context);
        alert.setWidth(350);
        alert.setHeight(300);
        alert.showAndWait();
    }

}

