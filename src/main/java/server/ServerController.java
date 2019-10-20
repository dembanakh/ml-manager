package server;

import java.io.FileNotFoundException;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.List;
import java.util.Map;

public class ServerController extends UnicastRemoteObject implements ServerAPI {

    private TaskManager taskManager;
    private DatasetManager datasetManager;
    private NeuralNetManager neuralNetManager;

    public ServerController() throws RemoteException {
        taskManager = new TaskManager();
        datasetManager = new DatasetManager();
        neuralNetManager = new NeuralNetManager();
    }

    public void start() {
        System.out.println("start");

        try {
            datasetManager.load();
        } catch (FileNotFoundException e) {
            System.err.println("datasets.src file not found or one of data directories doesn't exist!");
        }

        try {
            neuralNetManager.load();
        } catch (FileNotFoundException e) {
            System.err.println("networks.src file not found or one of data directories doesn't exist!");
        }

        try {
            taskManager.load();
        } catch (FileNotFoundException e) {
            System.err.println("tasks.src file not found!");
        }
    }

    @Override
    public Map<Integer, Task> getActiveTasks() {
        System.out.println("IN: getActiveTasks");
        System.out.println("OUT: " + taskManager.getActiveTasks());
        return taskManager.getActiveTasks();
    }

    @Override
    public Task getActiveTaskById(Integer id) {
        Task task = taskManager.getActiveTasks().get(id);
        System.out.println("IN: getActiveTaskById");
        System.out.println("OUT: " + task);
        return task;
    }

    @Override
    public Integer addTask(Task task) {
        System.out.println("IN: addTask");
        System.out.println("OUT: " + task.getID());
        return taskManager.addTask(task);
    }

    @Override
    public void deleteTask(Integer id) {
        System.out.println("IN: deleteTask");
        System.out.println("OUT: -");
        taskManager.deleteTask(id);
    }

    @Override
    public boolean changeTask_dataset(Integer id, String dataset) {
        System.out.println("IN: changeTask_dataset");
        if (!taskManager.hasTask(id)) return false;
        System.out.println("OUT: " + taskManager.hasTask(id));
        taskManager.changeTask_dataset(id, dataset);
        return true;
    }

    @Override
    public boolean changeTask_neuralNet(Integer id, String net) {
        System.out.println("IN: changeTask_neuralNet");
        if (!taskManager.hasTask(id)) return false;
        System.out.println("OUT: " + taskManager.hasTask(id));
        taskManager.changeTask_neuralNet(id, net);
        return true;
    }

    @Override
    public List<String> getDatasets() {
        return datasetManager.getDatasetNames();
    }

    @Override
    public List<String> getNeuralNets() {
        return neuralNetManager.getNeuralNetNames();
    }

    @Override
    public void trainTask(Integer id) {
        System.out.println("Training task " + id + "...");
        //TODO: use JNI to implement C++ training
    }

}
