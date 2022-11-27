package company;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.util.ArrayList;
import java.util.List;

public class FileOperations {

    private static final String FILE_NAME = "cars.txt";

    public static List<Car> readFile() {
        List<Car> carList = new ArrayList<>();
        try {
            String line;
            BufferedReader br = new BufferedReader(new FileReader(FILE_NAME));
            while (true) {
                line = br.readLine();
                if (line == null) break;
                String[] s = line.split(",");
                String reg = s[0];
                int yearMade = Integer.parseInt(s[1]);
                String color1 = s[2];
                String color2 = s[3];
                String color3 = s[4];
                String carMake = s[5];
                String carModel = s[6];
                int price = Integer.parseInt(s[7]);
                Car car = new Car(reg, yearMade, color1, color2, color3, carMake, carModel, price);
                carList.add(car);
            }
            br.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return carList;
    }

    public static void writeFile(CarDatabase carDatabase) {
        try {
            BufferedWriter bw = new BufferedWriter(new FileWriter(FILE_NAME));
            List<Car> carList = carDatabase.getCarList();
            for(Car car: carList) {
                bw.write(car.toString());
            }
            bw.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return;
    }

}