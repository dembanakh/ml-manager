package main;

import server.ServerAPI;
import server.ServerController;

import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;

public class ServerMain {

    public static void main(String[] args) {
        try {
            ServerController controller = new ServerController();
            ServerAPI stub = (ServerAPI) UnicastRemoteObject.exportObject(controller, 0);

            Registry registry = LocateRegistry.getRegistry();
            registry.bind("ServerAPI", stub);

            System.out.println("Server ready!");

            controller.start();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
