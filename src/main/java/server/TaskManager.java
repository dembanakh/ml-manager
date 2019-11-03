package server;

import utility.Dataset;
import utility.Utility;

import java.io.*;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

class TaskManager {
    private final static String sourcePath = Utility.ROOT + "tasks.src";

    private Map<String, Task> activeTasks;

    TaskManager() {
        activeTasks = new HashMap<>();
    }

    void load(DatasetManager dataMan, NeuralNetManager netMan) throws FileNotFoundException, NoSuchMLObjectException {
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
        addTaskToSourceFile(task);
    }

    private void addTaskToSourceFile(Task task) {
        try {
            FileOutputStream fos = new FileOutputStream(sourcePath);
            DataOutputStream dos = new DataOutputStream(new BufferedOutputStream(fos));
            dos.writeUTF(task.getTitle());
            dos.writeUTF(" ");
            dos.writeUTF(task.getDataset().getName());
            dos.writeUTF(" ");
            dos.writeUTF(task.getNeuralNet().toString());
            dos.writeUTF("\n");
        } catch (FileNotFoundException e) {
            System.err.println("File tasks.src not found!");
        } catch (IOException e) {
            e.printStackTrace();
        }
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
