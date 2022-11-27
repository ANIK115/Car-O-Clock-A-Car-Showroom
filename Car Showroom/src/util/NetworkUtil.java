package util;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

public class NetworkUtil {
    public Socket socket;
    public ObjectInputStream ois;
    public ObjectOutputStream oos;


    public NetworkUtil(String ip, int port) {
        try{
            this.socket = new Socket(ip, port);
            oos = new ObjectOutputStream(socket.getOutputStream());
            ois= new ObjectInputStream(socket.getInputStream());
        } catch (Exception e) {
            System.out.println("NetworkUtil Exception: "+e.getMessage());
            e.printStackTrace();
        }
    }

    public NetworkUtil(Socket socket)
    {
        try{
            this.socket=socket;
            oos=new ObjectOutputStream(socket.getOutputStream());
            ois=new ObjectInputStream(socket.getInputStream());
        }catch (Exception e)
        {
            System.out.println("NetworkUtil Exception: "+e.getMessage());
            e.printStackTrace();
        }
    }

    public Object readObject() {
        Object object = null;
        try {
            object = ois.readUnshared();
        } catch (IOException | ClassNotFoundException e) {
            System.out.println("Exception in readObject: "+e.getMessage());
//            e.printStackTrace();
        }
        return object;
    }

    public void writeObject(Object object) {
        try {
//            System.out.println("Write Object...........");
            oos.writeUnshared(object);
        } catch (IOException e) {
            System.out.println("Exception in writeObject: "+e.getMessage());
//            e.printStackTrace();
        }finally {

        }
    }
    public void flush()  {
        try {
            oos.flush();
        } catch (IOException e) {
            System.out.println("Exception in flush: "+e.getMessage());
        }
    }

    public void closeConnection()
    {
        try{
            ois.close();
            oos.close();
        }catch (Exception e)
        {
            System.out.println("Exception in closing NetworkUtil: "+e.getMessage());
        }
    }
}
