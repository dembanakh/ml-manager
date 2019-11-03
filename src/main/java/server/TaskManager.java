package server;

import com.mongodb.DBCursor;
import com.mongodb.DBObject;

import java.util.HashMap;
import java.util.Map;

class TaskManager {

    private Map<String, Task> activeTasks;
    private Database db;

    TaskManager() {
        activeTasks = new HashMap<>();
        db = new Database();
    }

    void load(DatasetManager dataMan, NeuralNetManager netMan) throws NoSuchMLObjectException {
        DBCursor cursor = db.getCursor("tasks");
        while (cursor.hasNext()) {
            DBObject obj = cursor.next();
            String title = (String) obj.get("title");
            String dataset = (String) obj.get("dataset");
            String net = (String) obj.get("net");
            if (!dataMan.hasDataset(dataset) || !netMan.hasNeuralNet(net)) {
                activeTasks.clear();
                dataMan.clear();
                netMan.clear();
                throw new NoSuchMLObjectException();
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
        activeTasks.remove(id);
        db.deleteTask(id);
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
