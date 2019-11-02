package server;

import utility.Dataset;
import utility.NeuralNet;
import utility.Utility;

import java.io.FileNotFoundException;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.List;
import java.util.Map;

public class ServerController extends UnicastRemoteObject implements ServerAPI {

    private TaskManager taskManager;
    private DatasetManager datasetManager;
    private NeuralNetManager neuralNetManager;
    private MLManager mlManager;

    public ServerController() throws RemoteException {
        taskManager = new TaskManager();
        datasetManager = new DatasetManager();
        neuralNetManager = new NeuralNetManager();
        mlManager = new MLManager();
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
        } catch (NoSuchMLObjectException e) {
            System.err.println("networks.src file contains illegal architecture name!");
        }

        try {
            taskManager.load(datasetManager, neuralNetManager);
        } catch (FileNotFoundException e) {
            System.err.println("tasks.src file not found!");
        } catch (NoSuchMLObjectException e) {
            System.err.println("A task from tasks.src contains non-existing dataset or neural network!");
        }
    }

    @Override
    public Map<String, Task> getActiveTasks() {
        System.out.println("IN: getActiveTasks");
        System.out.println("OUT: " + taskManager.getActiveTasks());
        return taskManager.getActiveTasks();
    }

    @Override
    public Task getActiveTaskById(String id) {
        Task task = taskManager.getActiveTasks().get(id);
        System.out.println("IN: getActiveTaskById");
        System.out.println("OUT: " + task);
        return task;
    }

    @Override
    public void addTask(Task task) {
        System.out.println("IN: addTask");
        System.out.println("OUT: " + task.getTitle());
        taskManager.addTask(task);
    }

    @Override
    public void deleteTask(String id) {
        System.out.println("IN: deleteTask");
        System.out.println("OUT: -");
        taskManager.deleteTask(id);
    }

    @Override
    public boolean changeTask_dataset(String id, String dataset) {
        System.out.println("IN: changeTask_dataset");
        if (!taskManager.hasTask(id)) return false;
        System.out.println("OUT: " + taskManager.hasTask(id));
        taskManager.changeTask_dataset(id, dataset);
        return true;
    }

    @Override
    public boolean changeTask_neuralNet(String id, String net) {
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
    public void trainTask(String id) {
        Task task = taskManager.getTask(id);
        if (task == null) {
            System.err.println("ERROR: No such task in map.");
            return;
        }
        Dataset ds = task.getDataset();
        NeuralNet nn = task.getNeuralNet();
        System.out.println("IN: trainTask " + ds + " " + nn);
        boolean success = mlManager.train((ds.getName().equals(Utility.IMAGENET)) ? ds.getName() : ds.getDirectory(), task.getNeuralNet().toString());
        System.out.println("OUT: " + success);
    }

}
