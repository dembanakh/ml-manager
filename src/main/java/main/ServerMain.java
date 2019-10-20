package main;

import server.ServerAPI;
import server.ServerController;

import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

public class ServerMain {

    private static void setPolicy() {
        System.setProperty("java.security.policy", "file:///home/dembanakh/ml-manager/server.policy");
        if (System.getSecurityManager() == null) {
            System.setSecurityManager(new SecurityManager());
        }
    }

    public static void main(String[] args) throws RemoteException, MalformedURLException {
        setPolicy();
        ServerController controller = new ServerController();
        Naming.rebind("rmi://40.87.143.114/ServerAPI", controller);

        System.out.println("Server ready!");

        controller.start();
    }

}
