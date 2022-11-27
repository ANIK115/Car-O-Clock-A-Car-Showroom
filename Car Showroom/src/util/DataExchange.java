package util;

import cardatabase.Car;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import server.Manufacturer;

import java.util.ArrayList;
import java.util.List;

public class DataExchange {

    public void sendCarListToServer(ObservableList<Car> cars, NetworkUtil networkUtil) {
        networkUtil.writeObject("take carlist");
        for(Car car : cars) {
            networkUtil.writeObject(car);
            networkUtil.flush();
        }
        networkUtil.writeObject("carlist sent");

    }
    public void receiveCarListFromServer(ObservableList<Car> cars, NetworkUtil networkUtil) {
        networkUtil.writeObject("send carlist");
        networkUtil.flush();
        ObservableList<Car> carObservableList = FXCollections.observableArrayList();
        while(true) {
            Object o = networkUtil.readObject();
            if(o instanceof String) {
                break;
            }
            Car car = (Car) o;
            carObservableList.add(car);
        }
        cars.setAll(carObservableList);
    }

    public void sendCarToServer(Car car, NetworkUtil networkUtil, String msg) {
        networkUtil.writeObject(msg);
        networkUtil.flush();
        networkUtil.writeObject(car);
        networkUtil.flush();
    }

    public void sendDeletedCars(ObservableList<Car> cars, NetworkUtil networkUtil) {
        networkUtil.writeObject("delete cars");
        networkUtil.flush();
        for(Car car : cars) {
            networkUtil.writeObject(car);
            networkUtil.flush();
        }
        networkUtil.writeObject("carlist sent");
        networkUtil.flush();
    }

    public List<Manufacturer> receiveManufacturerFromServer( NetworkUtil networkUtil) {
        networkUtil.writeObject("send manufacturer");
        networkUtil.flush();
        List<Manufacturer> manufacturers= new ArrayList<>();
        while(true) {
            Object o = networkUtil.readObject();
            if(o instanceof String) {
                break;
            }
            Manufacturer manufacturer = (Manufacturer) o;
            manufacturers.add(manufacturer);
        }
       return manufacturers;
    }

}
