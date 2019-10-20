package server;

import utility.Dataset;
import utility.Utility;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

class TaskManager {

    private Map<Integer, Task> activeTasks;

    TaskManager() {
        activeTasks = new HashMap<>();
    }

    void load() throws FileNotFoundException {
        String sourcePath = Utility.ROOT + "tasks.src";
        Scanner scanner = new Scanner(new File(sourcePath));
        int i = 0;
        while (scanner.hasNextLine()) {
            String[] names = scanner.nextLine().split(" ");
            activeTasks.put(i++, new Task(names[0], names[1]));
        }
    }

    Map<Integer, Task> getActiveTasks() {
        return activeTasks;
    }

    int addTask(Task task) {
        int i = 0;
        while (true) {
            if (hasTask(i)) i++;
            else break;
        }

        task.setID(i);
        activeTasks.put(i, task);

        return i;
    }

    void deleteTask(Integer id) {
        activeTasks.remove(id);
    }

    boolean hasTask(Integer id) {
        return activeTasks.containsKey(id);
    }

    void changeTask_dataset(Integer id, String dataset) {
        activeTasks.get(id).setDataset(dataset);
    }

    void changeTask_neuralNet(Integer id, String net) {
        activeTasks.get(id).setNeuralNet(net);
    }

}
