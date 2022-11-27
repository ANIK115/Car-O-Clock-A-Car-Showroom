package server;

import cardatabase.Car;
import javafx.collections.ObservableList;
import javafx.scene.Parent;
import util.NetworkUtil;

import java.io.IOException;
import java.net.CacheRequest;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.List;


public class Server {
    private static final int PORT = 10115;
    public static ObservableList<Car> carObservableList;
    public static List<Manufacturer> manufacturers;

    public static void main(String[] args) {

    carObservableList = FileManagement.readCarFile();
    manufacturers = FileManagement.readManufacturerFile();
    new FileSaverThread(); //continuously saves the files after a certain period of time

    System.out.println("Car read");

        try {
            ServerSocket server = new ServerSocket(PORT);
            while(true) {
                Socket client = server.accept();
                serve(client);
                System.out.println("Client connected!");
            }
        } catch (IOException e) {
            System.out.println("Exception in server: "+e.getMessage());
            e.printStackTrace();
        }finally {
            System.out.println("Server closed!");
        }
    }

    private static void serve(Socket client) {
        NetworkUtil networkUtil= new NetworkUtil(client);
        new ServerFunctionalityThread(networkUtil); //it serves the clients
    }


}
