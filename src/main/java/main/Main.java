package main;

import client.FailedToGetRMIStub;
import ui.UIController;

public class Main {

    public static void main(String[] args) throws FailedToGetRMIStub {
        UIController controller = new UIController();
        controller.start();
    }

}
