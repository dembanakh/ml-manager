package server;

import java.util.HashMap;
import java.util.Map;

public class TaskManager {

    private Map<Integer, Task> activeTasks;

    public TaskManager() {
        activeTasks = new HashMap<Integer, Task>();
    }

    public Map<Integer, Task> getActiveTasks() {
        return activeTasks;
    }

}
