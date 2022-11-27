package cardatabase;

import javafx.collections.ObservableList;
import cardatabase.Car;

import java.io.Serializable;

public class CarDatabaseSystem implements Serializable {
    private ObservableList<Car> carDatabase;

    public CarDatabaseSystem(ObservableList<Car> carDatabase) {
        this.carDatabase = carDatabase;
    }

    public ObservableList<Car> getCarDatabase() {
        return carDatabase;
    }

    public void setCarDatabase(ObservableList<Car> carDatabase) {
        this.carDatabase = carDatabase;
    }

    public Car searchCar(String reg) {
        Car car = null;
        for(int i=0; i<carDatabase.size() ; i++ ) {
            car = carDatabase.get(i);
            if(car.getRegistration().equalsIgnoreCase(reg)) {
                break;
            }
        }
        return car;
    }
    public void addCar(Car car) {
        carDatabase.add(car);
    }
    public void deleteCar(Car car) {
        carDatabase.remove(car);
    }

}
