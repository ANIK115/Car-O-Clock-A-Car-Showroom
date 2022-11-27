package client;

import cardatabase.Car;
import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import server.Manufacturer;
import util.DataExchange;
import util.NetworkUtil;

import java.util.ArrayList;
import java.util.List;

public class Main extends Application {

    public static NetworkUtil networkUtil;
    public static final int PORT = 10115;
    public static Stage stage;

    public static ObservableList<Car> carObservableList = FXCollections.observableArrayList();
    public static List<Manufacturer> manufacturerList = new ArrayList<>();
    public ClientFunctionalityThread functionalityThread; // for reading from server

    public static DataExchange dataExchange= new DataExchange();
    @Override
    public void start(Stage primaryStage) throws Exception{
        networkUtil = new NetworkUtil("localhost", PORT);
        dataExchange.receiveCarListFromServer(carObservableList, networkUtil);
        manufacturerList = dataExchange.receiveManufacturerFromServer(networkUtil);

        serveClientThread(); //the thread in this method reads from server

        primaryStage.setOnCloseRequest(e -> networkUtil.closeConnection()); //closing connection
        Parent root = FXMLLoader.load(getClass().getResource("Login.fxml"));
        primaryStage.setTitle("Car Showroom");
        primaryStage.setScene(new Scene(root, 700, 500));
        primaryStage.show();
        System.out.println("In start method");
    }


    public static void main(String[] args) {
        launch(args);
        System.out.println("Running client Main");
    }

    public void serveClientThread() {
        functionalityThread = new ClientFunctionalityThread(networkUtil);
    }
}
