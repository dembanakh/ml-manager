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

    public void addTask(Task task) {
        int i = 0;
        while (true) {
            if (activeTasks.containsKey(i)) i++;
            else break;
        }

        activeTasks.put(i, task);
    }

}
