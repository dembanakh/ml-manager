package server;

import java.rmi.RemoteException;
import java.util.Map;

public class ServerController implements ServerAPI {

    private TaskManager taskManager;

    public void start() {
        taskManager = new TaskManager();
    }

    public Map<Integer, Task> getActiveTasks() {
        return taskManager.getActiveTasks();
    }
}
