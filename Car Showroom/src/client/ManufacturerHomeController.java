package client;

import cardatabase.Car;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.util.converter.DoubleStringConverter;
import javafx.util.converter.IntegerStringConverter;
import server.Manufacturer;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class ManufacturerHomeController implements Initializable {
    //Table fields
    @FXML public TableView<Car> carTableView;
    @FXML private TableColumn<Car, String> registrationColumn;
    @FXML private TableColumn<Car, String> colorsColumn;
    @FXML private TableColumn<Car, String> carMakeColumn;
    @FXML private TableColumn<Car, String> carModelColumn;
    @FXML private TableColumn<Car, Double> priceColumn;
    @FXML private TableColumn<Car, Integer> quantityColumn;
    @FXML private TableColumn<Car, Integer> yearMadeColumn;

    //Button variables
    @FXML private Button deleteCarsButton;
    @FXML private Button addCarButton;
    @FXML private Button editCarButton;
    @FXML private Button saveChangeButton;
    @FXML private Button saveChangeToHDDButton;
    @FXML private Button logoutButton;

    //TextField variables
    @FXML private TextField regField;
    @FXML private TextField yearField;
    @FXML private TextField colorsField;
    @FXML private TextField carMakeField;
    @FXML private TextField carModelField;
    @FXML private TextField priceField;
    @FXML private TextField quantityField;
    @FXML private TextField searchField;

    //Others
    private boolean buttonClicks = false;
    private boolean enableEditing = false;
    private boolean enableAdding = false;
    private boolean valid = true;
    private static String msg = null;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        registrationColumn.setCellValueFactory(new PropertyValueFactory<Car, String>("registration"));
        colorsColumn.setCellValueFactory(new PropertyValueFactory<Car, String>("colors"));
        carMakeColumn.setCellValueFactory(new PropertyValueFactory<Car, String>("carMake"));
        carModelColumn.setCellValueFactory(new PropertyValueFactory<Car, String>("carModel"));
        priceColumn.setCellValueFactory(new PropertyValueFactory<Car, Double>("price"));
        quantityColumn.setCellValueFactory(new PropertyValueFactory<Car, Integer>("quantity"));
        yearMadeColumn.setCellValueFactory(new PropertyValueFactory<Car, Integer>("yearMade"));
        carTableView.setItems(Main.carObservableList);
        carTableView.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);

        // Making these columns editable
        yearMadeColumn.setCellFactory(TextFieldTableCell.forTableColumn(new IntegerStringConverter()));
        colorsColumn.setCellFactory(TextFieldTableCell.forTableColumn());
        carMakeColumn.setCellFactory(TextFieldTableCell.forTableColumn());
        carModelColumn.setCellFactory(TextFieldTableCell.forTableColumn());
        priceColumn.setCellFactory(TextFieldTableCell.forTableColumn(new DoubleStringConverter()));
        quantityColumn.setCellFactory(TextFieldTableCell.forTableColumn(new IntegerStringConverter()));

        disableFields();

        FilteredList<Car> carFilteredList = new FilteredList<>(Main.carObservableList, b->true);
        searchField.textProperty().addListener(((observable, oldValue, newValue) -> {
            carFilteredList.setPredicate( car -> {
                if(newValue == null || newValue.isEmpty()) {
                    return true;
                }
                String lowerCaseFilter = newValue.toLowerCase();

                if(car.getRegistration().toLowerCase().indexOf(lowerCaseFilter) != -1) {
                    return true; // Filter matches registration
                }else if(car.getCarMake().toLowerCase().indexOf(lowerCaseFilter) !=-1) {
                    return true;
                }else if(car.getCarModel().toLowerCase().indexOf(lowerCaseFilter) != -1) {
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

    //this is used to communicate with server
    public static void setMsg(String ob) {
        msg = ob;
    }

    // disabling fields for adding car
    public void disableFields() {
        regField.setDisable(true);
        yearField.setDisable(true);
        colorsField.setDisable(true);
        carMakeField.setDisable(true);
        carModelField.setDisable(true);
        priceField.setDisable(true);
        quantityField.setDisable(true);
    }

    //enabling fields for adding car
    public void enableFieds() {
        regField.setDisable(false);
        yearField.setDisable(false);
        colorsField.setDisable(false);
        carMakeField.setDisable(false);
        carModelField.setDisable(false);
        priceField.setDisable(false);
        quantityField.setDisable(false);
    }

    //This method let the user to edit table
    public void enableEditing() {
        if(enableEditing == false) {
            enableEditing = true;
            editCarButton.setText("Disable Edit");
            carTableView.setEditable(true);

        }else {
            enableEditing = false;
            editCarButton.setText("Enable Edit");
            carTableView.setEditable(false);
        }
    }


    public void deleteButtonClicked() {
        ObservableList<Car> selectedCars = null;
        selectedCars = carTableView.getSelectionModel().getSelectedItems();
        if(selectedCars.size() == 0) {
            return;
        }
        Main.dataExchange.sendDeletedCars(selectedCars, Main.networkUtil); //sending cars to server to delete
    }

    //clearing the fields for adding car
    public void clearFileds() {
        regField.clear();
        yearField.clear();
        colorsField.clear();
        carMakeField.clear();
        carModelField.clear();
        priceField.clear();
        quantityField.clear();
    }

    //add car button operation
    public void addCarButtonClicked() {
        valid = true;
        if(enableAdding == true) {
            String reg = regField.getText();
            String year = yearField.getText();
            String clr = colorsField.getText();
            String make = carMakeField.getText();
            String model = carModelField.getText();
            String price = priceField.getText();
            String quan = quantityField.getText();
            if(reg.equals("") || year.equals("") || clr.equals("") || make.equals("") || model.equals("") || price.equals("") || quan.equals("") ) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                AlertGenerator.generateAlert(alert, "Missing Fields", "All Fields must be added!");
                enableAdding = false;
                clearFileds();
                disableFields();
                addCarButton.setText("Add A Car");
                return;
            }
            try {
                int y = Integer.parseInt(year);
                if (y < 0) {
                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    AlertGenerator.generateAlert(alert, "Invalid Data", "Put a valid year");
                    valid = false;
                }
            }catch (Exception e) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                AlertGenerator.generateAlert(alert, "Invalid Data", "Year must be positive integer");
                valid = false;
            }
            try {
                double p = Double.parseDouble(price);
                if(p < 1000000) {
                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    AlertGenerator.generateAlert(alert, "Invalid Data", "Price can't be less than 1000000");
                    valid = false;
                }
            }catch (Exception e) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                AlertGenerator.generateAlert(alert, "Invalid Data", "Price must be a positive double value");
                valid = false;
            }
            try {
                int q = Integer.parseInt(quan);
                if (q < 0) {
                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    AlertGenerator.generateAlert(alert, "Invalid Data", "Quantity can't be less than zero");
                    valid = false;
                }
            }catch (Exception e) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                AlertGenerator.generateAlert(alert, "Invalid Data", "Quantity must be positive integer");
                valid = false;
            }
            if(valid == false) {
                return;
            }
            Car car = new Car(reg, Integer.parseInt(year, 10), clr, make, model, Double.parseDouble(price), Integer.parseInt(quan, 10));
            Main.networkUtil.writeObject("add car"); //informing the server to add a car
            Main.networkUtil.flush();
            Main.networkUtil.writeObject(car); //sending car to server to add to the carList
            Main.networkUtil.flush();

            clearFileds();
            enableAdding = false;
            disableFields();
            addCarButton.setText("Add A Car");

            while(msg == null) {
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }

            if(msg.equals("successfully added")) {
                clearFileds();
                enableAdding = false;
                disableFields();
                addCarButton.setText("Add A Car");
                Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                AlertGenerator.generateAlert(alert, "Operation Successful", "Car Added Successfully!");
            }else if(msg.equals("Cannot add")){
                Alert alert = new Alert(Alert.AlertType.ERROR);
                AlertGenerator.generateAlert(alert, "Operation Failed", "Cannot Add this car!");
                enableAdding = false;
                clearFileds();
                disableFields();
                addCarButton.setText("Add A Car");
            }
            msg = null; //setting the msg to null again for further use
        }else {
            enableAdding = true;
            enableFieds();
        }
    }


    public void editYear(TableColumn.CellEditEvent edittedCell) {
        Car car = carTableView.getSelectionModel().getSelectedItem();
        System.out.println(car);
        String year = null;
        try {
            year = edittedCell.getNewValue().toString();
            int y = Integer.parseInt(year);
            if (y < 0) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                AlertGenerator.generateAlert(alert, "Invalid Data", "Put a valid year");
                carTableView.refresh();
                return;
            }
        }catch (Exception e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            AlertGenerator.generateAlert(alert, "Invalid Data", "Year must be positive integer");
            carTableView.refresh();
            return;
        }

        car.setYearMade(Integer.parseInt(year));
        Main.dataExchange.sendCarToServer(car, Main.networkUtil, "edited car");
        carTableView.refresh();
    }

    public void editColors(TableColumn.CellEditEvent edittedCell) {
        Car car = carTableView.getSelectionModel().getSelectedItem();
        String clr = null;
        try {
            clr = edittedCell.getNewValue().toString();
            if(clr.equals("")) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                AlertGenerator.generateAlert(alert, "Invalid Data", "Color can't be blank!");
                carTableView.refresh();
                return;
            }
        }catch (Exception e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            AlertGenerator.generateAlert(alert, "Invalid Data", "Color can't be blank!");
            carTableView.refresh();
            return;
        }
        if(!clr.equals("")) {
            car.setColors(clr);
            Main.dataExchange.sendCarToServer(car, Main.networkUtil, "edited car");
        }
        carTableView.refresh();

    }

    public void editCarMake(TableColumn.CellEditEvent edittedCell) {
        Car car = carTableView.getSelectionModel().getSelectedItem();
        String make = null;
        try {
            make = edittedCell.getNewValue().toString();
            if(make.equals("")) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                AlertGenerator.generateAlert(alert, "Invalid Data", "Car Make can't be blank!");
                carTableView.refresh();
                return;
            }
        }catch (Exception e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            AlertGenerator.generateAlert(alert, "Invalid Data", "Car Make can't be blank!");
            carTableView.refresh();
            return;
        }
        if(!make.equals("")) {
            car.setCarMake(make);
            Main.dataExchange.sendCarToServer(car, Main.networkUtil, "edited car");
        }
        carTableView.refresh();

    }

    public void editCarModel(TableColumn.CellEditEvent edittedCell) {
        Car car = carTableView.getSelectionModel().getSelectedItem();
        String model = null;
        try {
            model = edittedCell.getNewValue().toString();
            if(model.equals("")) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                AlertGenerator.generateAlert(alert, "Invalid Data", "Car Model can't be blank!");
                carTableView.refresh();
                return;
            }
        }catch (Exception e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            AlertGenerator.generateAlert(alert, "Invalid Data", "Car Model can't be blank!");
            carTableView.refresh();
            return;
        }
        if(!model.equals("")) {
            car.setCarModel(model);
            Main.dataExchange.sendCarToServer(car, Main.networkUtil, "edited car");
        }
        carTableView.refresh();

    }

    public void editPrice(TableColumn.CellEditEvent edittedCell) {
        Car car = carTableView.getSelectionModel().getSelectedItem();
        String price = null;
        try {
            price = edittedCell.getNewValue().toString();
            double p = Double.parseDouble(price);
            if(p < 1000000) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                AlertGenerator.generateAlert(alert, "Invalid Data", "Price can't be less than 1000000");
                carTableView.refresh();
                return;
            }
        }catch (Exception e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            AlertGenerator.generateAlert(alert, "Invalid Data", "Price must be a positive double value");
            carTableView.refresh();
            return;
        }

        car.setPrice(Double.parseDouble(price));
        Main.dataExchange.sendCarToServer(car, Main.networkUtil, "edited car");
        carTableView.refresh();
    }

    public void editQuantity(TableColumn.CellEditEvent edittedCell) {
        Car car = carTableView.getSelectionModel().getSelectedItem();
        String quantity = null;
        try {
            quantity = edittedCell.getNewValue().toString();
            if(quantity == null) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                AlertGenerator.generateAlert(alert, "Invalid Data", "Quantity can't be blank!");
                carTableView.refresh();
                return;
            }
            int q = Integer.parseInt(quantity);
            if (q < 0) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                AlertGenerator.generateAlert(alert, "Invalid Data", "Quantity can't be less than zero");
                carTableView.refresh();
                return;
            }
        }catch (Exception e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            AlertGenerator.generateAlert(alert, "Invalid Data", "Quantity must be positive integer");
            carTableView.refresh();
            return;
        }

        car.setQuantity(Integer.parseInt(quantity));
        Main.dataExchange.sendCarToServer(car, Main.networkUtil, "edited car");
        carTableView.refresh();
    }

    public void logout(ActionEvent event) throws IOException {
        ControllerUtil controllerUtil = new ControllerUtil("Login.fxml", event);
        controllerUtil.createScene();
    }
}

