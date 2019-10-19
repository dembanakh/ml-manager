package main;

import server.ServerAPI;
import server.ServerController;

import java.rmi.Naming;

public class ServerMain {

    public static void main(String[] args) {
        try {
            ServerAPI controller = new ServerController();
            Naming.rebind("//localhost/ServerAPI", controller);

            System.out.println("Server ready!");

            //controller.start();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
