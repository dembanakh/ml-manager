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
    }

    public Map<Integer, Task> getActiveTasks() {
        return taskManager.getActiveTasks();
    }
}
