package client;

import cardatabase.Car;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class ViewerHomeController implements Initializable {
    @FXML private TableView<Car> carTableView;
    @FXML private TableColumn<Car, String> carMakeColumn;
    @FXML private TableColumn<Car, String> carModelColumn;
    @FXML private TableColumn<Car, Double> priceColumn;
    @FXML private TableColumn<Car, Integer> quantityColumn;
    @FXML private TableColumn<Car, Integer> yearMadeColumn;
    @FXML private TableColumn<Car, String> coloursColumn;
    @FXML private TableColumn<Car, String> registrationColumn;

    @FXML private TextField registrationField;
    @FXML private TextField carMakeField;
    @FXML private TextField carModelField;

    @FXML private Button carBuyButton;
    @FXML private Button logOutButton;

    private static String serverMsg = null;
    private boolean confirmBuying = false;


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        carMakeColumn.setCellValueFactory(new PropertyValueFactory<Car, String>("carMake"));
        carModelColumn.setCellValueFactory(new PropertyValueFactory<Car, String>("carModel"));
        priceColumn.setCellValueFactory(new PropertyValueFactory<Car, Double>("price"));
        quantityColumn.setCellValueFactory(new PropertyValueFactory<Car, Integer>("quantity"));
        yearMadeColumn.setCellValueFactory(new PropertyValueFactory<Car, Integer>("yearMade"));
        coloursColumn.setCellValueFactory(new PropertyValueFactory<Car, String>("colors"));
        registrationColumn.setCellValueFactory(new PropertyValueFactory<Car, String>("registration"));
        carTableView.setItems(Main.carObservableList);


        FilteredList<Car> carFilteredList = new FilteredList<>(Main.carObservableList, b->true);

        //Listener for Searching with registration
        registrationField.textProperty().addListener(((observable, oldValue, newValue) -> {
            carFilteredList.setPredicate( car -> {
                if(newValue == null || newValue.isEmpty()) {
                    return true;
                }
                String lowerCaseFilter = newValue.toLowerCase();

                if(car.getRegistration().toLowerCase().indexOf(lowerCaseFilter) != -1) {
                    return true; // Filter matches registration
                }else {
                    return false;
                }
            });
        } ));

        //Listener for searching with Car Make
        carMakeField.textProperty().addListener(((observable, oldValue, newValue) -> {
            carFilteredList.setPredicate( car -> {
                if(newValue == null || newValue.isEmpty()) {
                    return true;
                }
                String lowerCaseFilter = newValue.toLowerCase();

               if(car.getCarMake().toLowerCase().indexOf(lowerCaseFilter) !=-1) {
                    return true;
                }else {
                    return false;
                }
            });
        } ));


        //Listener for searching with Car Model
        carModelField.textProperty().addListener(((observable, oldValue, newValue) -> {
            carFilteredList.setPredicate( car -> {
                if(newValue == null || newValue.isEmpty()) {
                    return true;
                }
                String lowerCaseFilter = newValue.toLowerCase();

               if(car.getCarModel().toLowerCase().indexOf(lowerCaseFilter) != -1) {
                    return true;
                }else {
                    return false;
                }
            });
        } ));

        SortedList<Car> carSortedList = new SortedList<>(carFilteredList);
        carSortedList.comparatorProperty().bind(carTableView.comparatorProperty());
        carTableView.setItems(carSortedList);

    }

    public static synchronized void setServerMsg(String serverMsg) {
        ViewerHomeController.serverMsg = serverMsg;
    }

    public void buyCar() {
        if(confirmBuying == false) {
            carBuyButton.setText("Confirm");
            confirmBuying = true;
            return;
        }
        Car car = null;
        car = carTableView.getSelectionModel().getSelectedItem();
        if(car == null) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            AlertGenerator.generateAlert(alert, "Can't Progress Buying" , "Select a car to buy");
            confirmBuying = false;
            carBuyButton.setText("Buy Selected Car");
            return;
        }
        Main.dataExchange.sendCarToServer(car, Main.networkUtil, "want to buy");

        while(serverMsg == null) {
            try {
                Thread.sleep(1000); //sleeping the thread so that the updated info from server can come here
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        System.out.println("out of loop and msg: "+serverMsg);
        if(serverMsg.equals("car buy successful")) {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            AlertGenerator.generateAlert(alert, "Confirmation", "You have successfully bought this car: "+car);
            serverMsg = null;
            System.out.println("entered checking..............");
        }else if(serverMsg.equals("car out of stock")) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            AlertGenerator.generateAlert(alert, "Operation Failed!", "Sorry Sir! Car out of stock!");
            serverMsg = null;
        }else {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            AlertGenerator.generateAlert(alert, "Unusual Error!", "Unusual Error Occurred!");
            serverMsg = null;
        }
        confirmBuying = false;
        carBuyButton.setText("Buy Selected Car");
//        carTableView.refresh();

    }


    public void logOut(ActionEvent event) throws IOException {
        ControllerUtil controllerUtil = new ControllerUtil("Login.fxml", event);
        controllerUtil.createScene();
    }

}
