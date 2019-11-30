package main;

import client.FailedToGetRMIStub;
import ui.UIController;
import utility.Utility;

public class Main {

    public static void main(String[] args) {
        try {
            UIController controller = new UIController();
            controller.start();
        } catch (FailedToGetRMIStub e) {
            System.err.println("Failed to connect to the server stub. Check if the server is running at the " + Utility.RMISTUB);
        }
    }

}
