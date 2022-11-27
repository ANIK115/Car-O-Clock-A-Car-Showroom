package company;

import java.util.List;
import java.util.Scanner;

public class Menu  {
    public static void menu()
    {
        CarDatabase carDatabase = new CarDatabase(FileOperations.readFile());
        while (true) {
            System.out.println("(1) Search Cars");
            System.out.println("(2) Add Car");
            System.out.println("(3) Delete Car");
            System.out.println("(4) Exit System");
            Scanner scn = new Scanner(System.in);
            String option = scn.nextLine();
            if (option.equals("1"))
                searchCars(carDatabase);
            else if (option.equals("2"))
                addCar(carDatabase);
            else if (option.equals("3"))
                deleteCar(carDatabase);
            else if (option.equals("4")) {
                exitSystem(carDatabase);
                return;
            }
            else
                System.out.println("Invalid Option! Try Again!");
        }
    }

    public static void searchCars(CarDatabase carLists) {
        while(true) {
            System.out.println("Car Searching Options:");
            System.out.println("(1) By Registration Number");
            System.out.println("(2) By Car Make and Car Model");
            System.out.println("(3) Back to Main Menu");
            Scanner scn = new Scanner(System.in);
            String op = scn.nextLine();
                if(op.equals("1")) {
                    System.out.println("Enter registration number: ");
                    String reg = scn.nextLine();
                    int index = carLists.searchCar(reg);
                    if(index == -1) {
                        System.out.println("No such car with this Registration Number: "+reg);
                    }else {
                        carLists.getCarList().get(index).displayAllInfo();
                    }
                }
                else if(op.equals("2")) {
                    System.out.println("Enter Car Make: ");
                    String carMake = scn.nextLine();
                    System.out.println("Enter Car Model: ");
                    String carModel = scn.nextLine();
                    carLists.searchCar(carMake, carModel);
                }
                else if(op.equals("3")) {
                    return ;
                }
                else {
                    System.out.println("Invalid Option!");
                }
        }
    }

    public static void addCar(CarDatabase carDatabase) {
        Scanner scn = new Scanner(System.in);
        System.out.println("Enter registration:");
        String reg = scn.nextLine();
        List<Car> carList = carDatabase.getCarList();
        for (Car c : carList) {
            if(reg.equals(c.getCarReg())) {
                System.out.println("This car already exists!");
                System.out.println("Car information:");
                System.out.println(c);
                return ;
            }
        }
        System.out.println("Enter year:");
        int year = Integer.parseInt(scn.nextLine());
        System.out.println("Enter color 1:");
        String color1 = scn.nextLine();
        System.out.println("Enter color 2:");
        String color2 = scn.nextLine();
        System.out.println("Enter color 3:");
        String color3 = scn.nextLine();
        System.out.println("Enter company:");
        String carMake = scn.nextLine();
        System.out.println("Enter model:");
        String carModel = scn.nextLine();
        System.out.println("Enter price:");
        int price = scn.nextInt();
        Car car = new Car(reg, year, color1, color2, color3, carMake, carModel,price);
        carDatabase.addCar(car);
        return;
    }

    public static void deleteCar(CarDatabase carDatabase) {
        System.out.println("Enter registration number of the car you want to delete:");
        Scanner scn = new Scanner(System.in);
        String reg = scn.nextLine();
        if(carDatabase.deleteCar(reg) == true) {
            System.out.println("Successfully deleted car with registration no: "+reg);
        }else {
            System.out.println("Car not found with registration number: "+reg);
        }
        return;
    }
    public static void exitSystem(CarDatabase carDatabase) {
        FileOperations.writeFile(carDatabase);
        return;
    }

}
