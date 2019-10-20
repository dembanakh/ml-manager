package main;

import server.ServerController;

import java.rmi.Naming;

public class ServerMain {

    public static void main(String[] args) {
        try {
            ServerController controller = new ServerController();
            Naming.rebind("rmi://40.87.143.114/ServerController", controller);

            System.out.println("Server ready!");

            controller.start();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
