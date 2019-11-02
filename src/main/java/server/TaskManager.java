package server;

import utility.Dataset;
import utility.Utility;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

class TaskManager {

    private Map<String, Task> activeTasks;

    TaskManager() {
        activeTasks = new HashMap<>();
    }

    void load(DatasetManager dataMan, NeuralNetManager netMan) throws FileNotFoundException, NoSuchMLObjectException {
        String sourcePath = Utility.ROOT + "tasks.src";
        Scanner scanner = new Scanner(new File(sourcePath));
        while (scanner.hasNextLine()) {
            String[] names = scanner.nextLine().split(" ");
            String title = names[0];
            String dataset = names[1];
            String net = names[2];
            if (!dataMan.hasDataset(dataset) || !netMan.hasNeuralNet(net)) {
                activeTasks.clear();
                dataMan.clear();
                netMan.clear();
                throw new NoSuchMLObjectException();
            }
            this.addTask(new Task(title, dataset, net));
        }
    }

    Map<String, Task> getActiveTasks() {
        return activeTasks;
    }

    void addTask(Task task) {
        activeTasks.put(task.getTitle(), task);
    }

    Task getTask(String id) {
        return activeTasks.get(id);
    }

    void deleteTask(String id) {
        activeTasks.remove(id);
    }

    boolean hasTask(String id) {
        return activeTasks.containsKey(id);
    }

    void changeTask_dataset(String id, String dataset) {
        activeTasks.get(id).setDataset(dataset);
    }

    void changeTask_neuralNet(String id, String net) {
        activeTasks.get(id).setNeuralNet(net);
    }

}
