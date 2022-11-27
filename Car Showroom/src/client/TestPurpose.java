package client;

import server.FileManagement;
import server.Manufacturer;

import java.util.ArrayList;
import java.util.List;

public class TestPurpose {
    public static void main(String[] args) throws Exception {

        List<Manufacturer> manufacturers = new ArrayList<>();
        Manufacturer m1 = new Manufacturer("manufacturer1", "1231");
        Manufacturer m2 = new Manufacturer("manufacturer2", "1232");
        Manufacturer m3 = new Manufacturer("manufacturer3", "1233");
        Manufacturer m4 = new Manufacturer("manufacturer4", "1234");
        Manufacturer m5 = new Manufacturer("manufacturer5", "1235");

        manufacturers.add(m1);
        manufacturers.add(m2);
        manufacturers.add(m3);
        manufacturers.add(m4);
        manufacturers.add(m5);

        FileManagement.writeManufacturerFile(manufacturers);

        manufacturers = FileManagement.readManufacturerFile();

        for(Manufacturer m : manufacturers) {
            System.out.println(m);
        }

    }
}
