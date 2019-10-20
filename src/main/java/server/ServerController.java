package server;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.Map;

public class ServerController extends UnicastRemoteObject implements ServerAPI {

    private TaskManager taskManager;

    public ServerController() throws RemoteException {
    }

    public void start() {
        taskManager = new TaskManager();
        taskManager.addTask(new Task("MNIST", "VGG16"));

        System.out.println("start");
    }

    public Map<Integer, Task> getActiveTasks() {
        System.out.println("IN: getActiveTasks");
        System.out.println("OUT: " + taskManager.getActiveTasks());
        return taskManager.getActiveTasks();
    }
}
