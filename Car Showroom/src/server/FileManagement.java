package server;

import cardatabase.Car;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class FileManagement {
    private static final String CAR_DATA_FILE = "cardata.txt";
    private static final String MANUFACTURER_DATA_FILE= "manufacturer.txt";

    public static synchronized ObservableList<Car> readCarFile() {
        ObservableList<Car> carObservableList = FXCollections.observableArrayList();
        FileInputStream fis = null;
        ObjectInputStream ois = null;
        try {
            fis= new FileInputStream(CAR_DATA_FILE);
            ois = new ObjectInputStream(fis);
            try {
                while(true) {
//                    System.out.println("reading car from file............");
                    Car car = (Car) ois.readUnshared();
                    if(car == null) {
                        break;
                    }
                    carObservableList.add(car);
//                    System.out.println("Car read successfully!");
                }
            } catch (EOFException e) {
                ois.close();
            }
        } catch (FileNotFoundException e) {
            System.out.println("Exception in readCarFile: "+e.getMessage());
            e.printStackTrace();
        } catch (IOException e) {
            System.out.println("Exception in readCarFile: "+e.getMessage());
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            System.out.println("Exception in readCarFile: "+e.getMessage());
            e.printStackTrace();
        }finally {
            return carObservableList;
        }

    }

    public static synchronized void writeCarFile(ObservableList<Car> carObservableList) {
        FileOutputStream fos= null;
        ObjectOutputStream oos= null;
//        for(Car car : carObservableList) {
//            System.out.println(car);
//        }
//        System.out.println("Writing.....................");
        try {
            fos = new FileOutputStream(CAR_DATA_FILE);
            oos = new ObjectOutputStream(fos);
            for(Car car: carObservableList) {
//                System.out.println("Writing in loop..........");
                oos.writeUnshared(car);
                oos.flush();
            }
            System.out.println("Car saved to hdd!");
            oos.close();
        } catch (FileNotFoundException e) {
            System.out.println("Exception in writeCarFile: "+e.getMessage());
            e.printStackTrace();
            try {
                oos.close();
            } catch (IOException ioException) {
                ioException.printStackTrace();
            }
        } catch (IOException e) {
            System.out.println("Exception in writeCarFile: "+e.getMessage());
            e.printStackTrace();
            try {
                oos.close();
            } catch (IOException ioException) {
                ioException.printStackTrace();
            }
        }
    }

    public static synchronized List<Manufacturer> readManufacturerFile() {
        List<Manufacturer> manufacturers = new ArrayList<>();
        FileInputStream fis = null;
        ObjectInputStream ois= null;
        try {
            fis = new FileInputStream(MANUFACTURER_DATA_FILE);
            ois = new ObjectInputStream(fis);
            try {
                while(true) {
                    Manufacturer manufacturer = (Manufacturer) ois.readUnshared();
                    if(manufacturer == null) {
                        break;
                    }
                    manufacturers.add(manufacturer);
                }
            }catch (EOFException e) {
                fis.close();
            }
        } catch (FileNotFoundException e) {
            System.out.println("Exception in readManufacturerFile: "+e.getMessage());
            e.printStackTrace();
        } catch (IOException e) {
            System.out.println("Exception in readManufacturerFile: "+e.getMessage());
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            System.out.println("Exception in readManufacturerFile: "+e.getMessage());
            e.printStackTrace();
        }
        return manufacturers;
    }
    public static synchronized void writeManufacturerFile(List<Manufacturer> manufacturers) {
        FileOutputStream fos = null;
        ObjectOutputStream oos = null;
        try {
            fos = new FileOutputStream(MANUFACTURER_DATA_FILE);
            oos = new ObjectOutputStream(fos);
            for(Manufacturer manufacturer : manufacturers) {
                oos.writeUnshared(manufacturer);
                oos.flush();
            }
            oos.close();
        } catch (FileNotFoundException e) {
            System.out.println("Exception in writeManufacturerFile: "+e.getMessage());
            e.printStackTrace();
        } catch (IOException e) {
            System.out.println("Exception in writeManufacturerFile: "+e.getMessage());
            e.printStackTrace();
        }
    }
}
