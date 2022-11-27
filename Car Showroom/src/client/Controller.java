package client;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import server.Manufacturer;

import java.io.IOException;

public class Controller {

    @FXML
    private TextField username;
    @FXML
    private PasswordField passwordField;
    @FXML
    private Button loginButton;
    private ControllerUtil controllerUtil;

    //for viewer login

    public void LoginButtonPushed(ActionEvent event) throws Exception
    {
        if(username.getText().equals("viewer")) {
            controllerUtil = new ControllerUtil("viewerHome.fxml", event);
            ViewerHomeController controller = controllerUtil.getLoader().getController();
            controllerUtil.createScene();
        }else {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            AlertGenerator.generateAlert(alert, "Log in Error", "No matching found! Use 'viewer' as username ");
        }
    }

    //for manufacturer login

    public void manufacturerLogin(ActionEvent event) throws IOException {
        for(Manufacturer m : Main.manufacturerList) {
            if(m.getUserName().equals(username.getText()) && m.getPassword().equals(passwordField.getText())) {
                controllerUtil = new ControllerUtil("ManufacturerHome.fxml", event);
                ManufacturerHomeController controller = controllerUtil.getLoader().getController();
                controllerUtil.createScene();
                return;
            }
        }
        Alert alert = new Alert(Alert.AlertType.ERROR);
        AlertGenerator.generateAlert(alert, "Log in Error", "No matching found! Try again, please");
    }
}