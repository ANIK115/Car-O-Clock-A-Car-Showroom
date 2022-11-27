package client;

import cardatabase.Car;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

//this class is used for changing the scene and getting controller

public class ControllerUtil {
    private FXMLLoader loader;
    private Parent parent;
    private Scene scene;
    private Stage stage;
    private String fxmlFile;
    private ActionEvent event;
    public ObservableList<Car> cars;

    public ControllerUtil(String fxmlFile, ActionEvent event) throws IOException {
        this.fxmlFile = fxmlFile;
        this.event = event;
        loader = new FXMLLoader();
        loader.setLocation(getClass().getResource(fxmlFile));
        parent = loader.load();

        scene = new Scene(parent);
        stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        stage.setScene(scene);
    }

    public void setParent(Parent parent) {
        this.parent = parent;
    }

    public void setScene(Scene scene) {
        this.scene = scene;
    }

    public void setStage(Stage stage) {
        this.stage = stage;
    }

    public void setFxmlFile(String fxmlFile) {
        this.fxmlFile = fxmlFile;
    }

    public void setEvent(ActionEvent event) {
        this.event = event;
    }

    public Parent getParent() {
        return parent;
    }

    public Scene getScene() {
        return scene;
    }

    public Stage getStage() {
        return stage;
    }

    public String getFxmlFile() {
        return fxmlFile;
    }

    public ActionEvent getEvent() {
        return event;
    }

    public FXMLLoader getLoader() {
        return loader;
    }

    public void createScene() throws IOException {
        stage.show();
    }
}
