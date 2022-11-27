package company;

import java.util.ArrayList;
import java.util.List;

class Car {
    private String carReg;
    private int yearMade;
    private String color1;
    private String color2;
    private String color3;
    private String carMake;
    private String carModel;
    private int price;

    public Car(String carReg, int yearMade, String color1, String color2, String color3, String carMake, String carModel, int price) {
        this.carReg = carReg;
        this.yearMade = yearMade;
        this.color1 = color1;
        this.color2 = color2;
        this.color3 = color3;
        this.carMake = carMake;
        this.carModel = carModel;
        this.price = price;
    }

    public String getCarReg() {
        return carReg;
    }

    public String getCarMake() {
        return carMake;
    }

    public String getCarModel() {
        return carModel;
    }

    @Override
    public String toString() {
        return carReg+","+yearMade+","+color1+","+color2+","+color3+","+carMake+","+carModel+","+price+"\n";
    }

    public void displayAllInfo() {
        System.out.println("\nDisplaying car information:");
        System.out.println("Registration No: "+this.carReg);
        System.out.println("Car was made in: "+this.yearMade);
        System.out.println("Color 1: "+this.color1);
        System.out.println("Color 2: "+this.color2);
        System.out.println("Color 3: "+this.color3);
        System.out.println("Car Company: "+this.carMake);
        System.out.println("Car Model: "+this.carModel);
        System.out.println("Car Price: "+this.price);
        System.out.println("\n");
    }
}

public class CarDatabase {
    private List<Car> carList = new ArrayList<>();

    public CarDatabase(List<Car> carList) {
        this.carList = carList;
    }

    public List<Car> getCarList() {
        return carList;
    }

    public void setCarList(List<Car> carList) {
        this.carList = carList;
    }

    public int searchCar(String reg) {
        int index = -1;
        for(int i=0; i<carList.size() ; i++ ) {
            Car car = carList.get(i);
            if(car.getCarReg().equalsIgnoreCase(reg)) {
                index = i;
                break;
            }
        }
        return index;
    }
    public void searchCar(String carMake, String carModel) {
        boolean found = false;
        for(int i=0; i<carList.size(); i++) {
            Car car = carList.get(i);
            if(carModel.equalsIgnoreCase("any")) {
                if(car.getCarMake().equalsIgnoreCase(carMake)) {
                    found = true;
                    car.displayAllInfo();
                }
            }else if(car.getCarMake().equalsIgnoreCase(carMake) && car.getCarModel().equalsIgnoreCase(carModel)) {
                found = true;
                car.displayAllInfo();
            }
        }
        if(found == false) {
            System.out.println("No such car with this Car Make: "+carMake+", Car Model: "+carModel);
        }
    }

    public boolean addCar(Car car) {
        carList.add(car);
        System.out.println("Car successfully added!");
        return true;
    }
    public boolean deleteCar(String reg) {
       for(Car car: carList) {
           if(car.getCarReg().equalsIgnoreCase(reg)) {
               carList.remove(car);
               return true;
           }
       }
       return false;
    }
}
