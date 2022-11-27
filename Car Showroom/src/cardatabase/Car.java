package cardatabase;

import javafx.beans.property.SimpleStringProperty;

import java.io.Serializable;

public class Car implements Serializable {
    private String registration;
    private int yearMade;
    private String colors;
    private String carMake;
    private String carModel;
    private double price;
    private int quantity;

    public Car(String registration, int yearMade, String colors, String carMake, String carModel, double price, int quantity) {
        this.registration = registration;
        this.yearMade = yearMade;
        this.colors = colors;
        this.carMake = carMake;
        this.carModel = carModel;
        this.price = price;
        this.quantity = quantity;
    }

    public String getRegistration() {
        return registration;
    }

    public void setRegistration(String registration) {
        this.registration = registration;
    }

    public int getYearMade() {
        return yearMade;
    }

    public void setYearMade(int yearMade) {
        this.yearMade = yearMade;
    }

    public String getColors() {
        return colors;
    }

    public void setColors(String colors) {
        this.colors = colors;
    }

    public String getCarMake() {
        return carMake;
    }

    public void setCarMake(String carMake) {
        this.carMake = carMake;
    }

    public String getCarModel() {
        return carModel;
    }

    public void setCarModel(String carModel) {
        this.carModel = carModel;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    @Override
    public String toString() {
        return "Reg: "+registration+", Year: "+yearMade+", Colors: "+colors+
                ", Car Make: "+carMake+", Car Model: "+carModel+", Price: "+price+", Quantity: "+quantity+"\n";
    }

//    @Override
//    public boolean equals(Object obj) {
//        Car car = (Car) obj;
//        if(car.getRegistration().equals(this.registration)) {
//            return true;
//        }else {
//            return false;
//        }
//    }
}
