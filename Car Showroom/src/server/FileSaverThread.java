package server;

// Thread for saving files to Hard drive only
public class FileSaverThread implements Runnable{
    private Thread thread;

    public FileSaverThread() {
        thread = new Thread(this);
        thread.start();
    }

    @Override
    public void run() {
        while (true) {
            try {
                Thread.sleep(60000); //saving files after 60 seconds
                FileManagement.writeCarFile(Server.carObservableList);
                FileManagement.writeManufacturerFile(Server.manufacturers);
                System.out.println("File saved!");
            } catch (InterruptedException e) {
                System.out.println("FileSaverThread Exception: "+e);
            }
        }
    }
}
