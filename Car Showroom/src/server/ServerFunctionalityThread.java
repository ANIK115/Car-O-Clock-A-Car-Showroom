package server;

import cardatabase.Car;
import client.Main;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import util.NetworkUtil;

import java.io.IOException;
import java.net.SocketException;
import java.util.ArrayList;
import java.util.List;

public class ServerFunctionalityThread implements Runnable{
    public Thread serverThread;
    public NetworkUtil networkUtil;
    public static List<ServerFunctionalityThread> threadList = new ArrayList<>();

    public ServerFunctionalityThread(NetworkUtil networkUtil) {
        this.networkUtil = networkUtil;
        serverThread = new Thread(this);
        serverThread.start();
        threadList.add(this);
    }

    //this method tells every clients to take updates
    public void notifyUpdate() {
       for(ServerFunctionalityThread thread : threadList) {
           if(thread.serverThread.isAlive()) {
               thread.networkUtil.writeObject("take updates");
               thread.networkUtil.flush();
           }else {
//               threadList.remove(thread);
               System.out.println("Dead Thread removed!");
           }
       }
    }

    @Override
    public void run() {
        try {
        while(true) {

                Object object = networkUtil.readObject();
                System.out.println("Passed readLine..........");
                if(object == null)
                    break;
                if (object instanceof String) {
                    String req = (String) object;
                    if (req.equals("send carlist")) {
                        for (Car car : Server.carObservableList) {
                            networkUtil.writeObject(car);
                            networkUtil.flush();
                        }
                        networkUtil.writeObject("carlist sent");
                        networkUtil.flush();
                    }else if(req.equals("want to buy")) {
                        Car car = (Car) networkUtil.readObject();
                        System.out.println(car);
                        synchronized (this) {
                            int q = car.getQuantity();
                            if (q > 0) {
                                q--;
                                for (Car c : Server.carObservableList) {
                                    if (c.getRegistration().equals(car.getRegistration())) {
                                        c.setQuantity(q);
                                        System.out.println("Car bought!!!");
                                        break;
                                    }
                                }
                                networkUtil.writeObject("car buy successful");
                                networkUtil.flush();
                                notifyUpdate();

                            } else {
                                networkUtil.writeObject("car out of stock");
                                networkUtil.flush();
                            }
                        }
                    }else if(req.equals("add car")) {
                        Car car = (Car) networkUtil.readObject();
                        boolean flag = false;
                        for(Car c : Server.carObservableList) {
                            if(c.getRegistration().equals(car.getRegistration())) {
                                flag = true;
                                break;
                            }
                        }
                        if(flag) {
                            networkUtil.writeObject("Cannot add");
                            networkUtil.flush();
                        }else {
                            synchronized (this) {
                                Server.carObservableList.add(car);
                                networkUtil.writeObject("successfully added");
                                networkUtil.flush();
                                notifyUpdate();
                            }
                        }
                    }else if(req.equals("edited car")) {
                        Car car = (Car) networkUtil.readObject();
                        synchronized (this) {
                            for (int i = 0; i < Server.carObservableList.size(); i++) {
                                Car c = Server.carObservableList.get(i);
                                if (c.getRegistration().equals(car.getRegistration())) {
                                    Server.carObservableList.set(i, car);
                                    System.out.println("Found match with edited car!");
                                    break;
                                }
                            }
                            notifyUpdate();
                        }
                    }else if(req.equals("delete cars")) {
                        ObservableList<Car> deletedCars = FXCollections.observableArrayList();
                        while(true) {
                            synchronized (this) {
                                Object o = networkUtil.readObject();
                                if (o instanceof String) {
                                    break;
                                }
                                if (o instanceof Car) {
                                    Car car = (Car) o;
                                    for (Car c : Server.carObservableList) {
                                        if (car.getRegistration().equals(c.getRegistration())) {
                                            Server.carObservableList.remove(c);
                                            break;
                                        }
                                    }
                                }

                            }
                        }

                        notifyUpdate();
                    }else if (req.equals("send manufacturer")) {
                        for(Manufacturer m: Server.manufacturers) {
                            networkUtil.writeObject(m);
                            networkUtil.flush();
                        }
                        networkUtil.writeObject("manufacturer sent");
                        networkUtil.flush();
                    }else {
                        System.out.println("No matching req in server: " + req);
                    }
                }
            }
        }catch (Exception e) {
            System.out.println("Exception in ServerFunctionality: "+e);
        }finally {
            FileManagement.writeCarFile(Server.carObservableList); //saving the files
            System.out.println("Out of loop..................");
        }

    }
}
