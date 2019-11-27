package server;

import com.mongodb.DBCursor;
import com.mongodb.DBObject;
import utility.Utility;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

class TaskManager {

    private Map<String, Task> activeTasks;
    private Database db;

    TaskManager() {
        activeTasks = new ConcurrentHashMap<>();
        db = new Database();
    }

    void load(DatasetManager dataMan, NeuralNetManager netMan) throws NoSuchMLObjectException {
        DBCursor cursor = db.getCursor("tasks");
        while (cursor.hasNext()) {
            DBObject obj = cursor.next();
            String title = (String) obj.get("title");
            String dataset = (String) obj.get("dataset");
            String net = (String) obj.get("net");
            if (!dataMan.hasDataset(dataset) || !netMan.hasNeuralNet(net) /*||
                    !Files.exists(Paths.get(Utility.WEIGHTS + title + ".h5"))*/) {  // it doesn't need to exist if task has never been trained
                activeTasks.clear();
                dataMan.clear();
                netMan.clear();
                throw new NoSuchMLObjectException(title, dataset, net);
            }
            activeTasks.put(title, new Task(title, dataset, net));
        }
    }

    Map<String, Task> getActiveTasks() {
        return activeTasks;
    }

    void addTask(Task task) {
        activeTasks.put(task.getTitle(), task);
        db.addTask(task);
    }

    Task getTask(String id) {
        return activeTasks.get(id);
    }

    void deleteTask(String id) {
        String taskName = activeTasks.remove(id).getTitle();
        db.deleteTask(id);
        try {
            Files.delete(Paths.get(Utility.WEIGHTS + taskName + ".h5"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    boolean hasTask(String id) {
        return activeTasks.containsKey(id);
    }

    void changeTask_dataset(String id, String dataset) {
        activeTasks.get(id).setDataset(dataset);
        db.changeTask_dataset(id, dataset);
    }

    void changeTask_neuralNet(String id, String net) {
        activeTasks.get(id).setNeuralNet(net);
        db.changeTask_neuralNet(id, net);
    }

}
