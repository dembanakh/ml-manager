package ui;

import client.ClientController;
import client.FailedToGetRMIStub;
import ui.actions.UIAction;
import ui.actions.UIActions;

import java.util.*;

public class UIController {

    private static ClientController controller;
    private Scanner scanner;

    public static Stack<UIAction> memory = new Stack<>();

    public UIController() throws FailedToGetRMIStub {
        scanner = new Scanner(System.in);
        controller = new ClientController();
        memory.push(UIActions.ROOT);
    }

    public void start() {
        while (running()) {
            memory.push(memory.peek().execute(scanner));
        }
    }

    public static ClientController getClientController() {
        return controller;
    }

    private boolean running() {
        return !memory.isEmpty() && !(memory.peek() == null);
    }

}
