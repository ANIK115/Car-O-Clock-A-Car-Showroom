package client;

import cardatabase.Car;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.Alert;
import util.DataExchange;
import util.NetworkUtil;

public class ClientFunctionalityThread implements Runnable {
    public Thread clientThread;
    public NetworkUtil networkUtil;

    public ClientFunctionalityThread(NetworkUtil networkUtil) {
        this.networkUtil = networkUtil;
        clientThread = new Thread(this);
        clientThread.start();
    }

    @Override
    public void run() {
        while (clientThread.isAlive()) {
            Object ob = networkUtil.readObject();
            System.out.println("Printing in ClientFunctionality run: "+ob);
            if(ob == null) {
                break;
            }
            if(ob instanceof String) {
                if(ob.equals("take updates")) {
                    Main.dataExchange.receiveCarListFromServer(Main.carObservableList, Main.networkUtil);
//                    System.out.println("Update taken!");
//                    for(Car car : Main.carObservableList) {
//                        System.out.println("Printing car: "+car);
//                    }
                }else if(ob.equals("successfully added")) {
                    ManufacturerHomeController.setMsg("successfully added");
                }else if(ob.equals("Cannot add")) {
                    ManufacturerHomeController.setMsg("Cannot add");
                }else if(ob.equals("car out of stock")) {
                    ViewerHomeController.setServerMsg("car out of stock");
                }else if(ob.equals("car buy successful")) {
                    ViewerHomeController.setServerMsg("car buy successful");
                }
            }
        }
    }
}
