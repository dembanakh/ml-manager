package client;

import server.ServerAPI;
import server.ServerController;
import server.Task;

import java.rmi.Naming;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.Map;

public class ClientController {

    private ServerAPI server;

    public ClientController() {
        try {
            server = (ServerAPI) Naming.lookup("//localhost/ServerController");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public Map<Integer, Task> getActiveTasks() {
        try {
            return server.getActiveTasks();
        } catch (RemoteException e) {
            e.printStackTrace();
            return null;
        }
    }

}
