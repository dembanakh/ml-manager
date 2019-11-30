package main;

import server.ServerAPI;
import server.ServerController;

import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.RemoteException;

/*
 * Server's main class. Do not execute it if client.
 */
public class ServerMain {

    /*
     * Server must have ml-manager git directory in root, and file server.policy inside.
     */
    private static void setPolicy() {
        System.setProperty("java.security.policy", "file://" + System.getenv("HOME") + "/ml-manager/server.policy");
        if (System.getSecurityManager() == null) {
            System.setSecurityManager(new SecurityManager());
        }
    }

    public static void main(String[] args) throws RemoteException, MalformedURLException {
        setPolicy();
        ServerController controller = new ServerController();
        Naming.rebind("ServerAPI", controller);

        System.out.println("Server ready!");

        controller.start();
    }

}
