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
System.out.println(datasetManager.getDatasetNames().size());
        try {
            neuralNetManager.load();
        } catch (FileNotFoundException e) {
            System.err.println("networks.src file not found or one of data directories doesn't exist!");
        }
System.out.println(neuralNetManager.getNeuralNetNames().size());
        try {
            taskManager.load(datasetManager, neuralNetManager);
        } catch (FileNotFoundException e) {
            System.err.println("tasks.src file not found!");
        } catch (NoSuchMLObjectException e) {
            System.err.println("A task from tasks.src contains non-existing dataset or neural network!");
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
        Task task = taskManager.getTask(id);
        if (task == null) {
            System.err.println("ERROR: No such task in map.");
            return;
        }
        System.out.println("IN: trainTask " + task.getDataset() + " " + task.getNeuralNet());
        boolean success = mlManager.train(task.getDataset().getDirectory(), task.getNeuralNet().getWeights());
        System.out.println("OUT: " + success);
    }

}
