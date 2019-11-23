package server;

import client.Remote;
import utility.Batch;
import utility.Dataset;
import utility.NeuralNet;
import utility.Utility;

import java.io.FileNotFoundException;
import java.nio.file.Files;
import java.nio.file.Paths;
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
            e.printStackTrace();
            return;
        }

        try {
            neuralNetManager.load();
        } catch (FileNotFoundException e) {
            System.err.println("networks.src file not found or one of data directories doesn't exist!");
            return;
        } catch (NoSuchMLObjectException e) {
            System.err.println("networks.src file contains illegal architecture name!");
            return;
        }

        try {
            taskManager.load(datasetManager, neuralNetManager);
        } catch (NoSuchMLObjectException e) {
            System.err.println("A task from collection \"tasks\" contains non-existing dataset or neural network!");
        }
    }

    @Override
    public Map<String, Task> getActiveTasks() {
        System.out.println("IN: getActiveTasks");
        System.out.println("OUT: " + taskManager.getActiveTasks());
        return taskManager.getActiveTasks();
    }

    @Override
    public void refreshTasks() throws RemoteException {
        try {
            System.out.println("IN: refreshTasks");
            taskManager.load(datasetManager, neuralNetManager);
            System.out.println("OUT: -");
        } catch (NoSuchMLObjectException e) {
            System.err.println("A task from tasks.src contains non-existing dataset or neural network!");
            throw new RemoteException(Utility.TASKS_SRC);
        }
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
    public boolean checkPath(String path) {
        return Files.exists(Paths.get(path)) || Files.exists(Paths.get(Utility.HOME + path)) || Files.exists(Paths.get(Utility.DATASETS + path));
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
    public boolean trainTask(String id) throws RemoteException {
        Task task = taskManager.getTask(id);
        if (task == null) {
            System.err.println("ERROR: No such task in map.");
            throw new RemoteException(Utility.NO_TASK_IN_MAP);
        }
        Dataset ds = task.getDataset();
        NeuralNet nn = task.getNeuralNet();
        System.out.println("IN: trainTask " + ds + " " + nn);
        boolean success = mlManager.train((ds.getName().equals(Utility.IMAGENET)) ? ds.getName() : ds.getDirectory(), task.getNeuralNet().toString(), task.getTitle());
        System.out.println("OUT: " + success);
        return success;
    }

    /*
     * Returns number of correct predictions.
     */
    @Override
    public int testTask(String id, Batch b) throws RemoteException {
        Task task = taskManager.getTask(id);
        if (task == null) {
            System.err.println("ERROR: No such task in map.");
            throw new RemoteException(Utility.NO_TASK_IN_MAP);
        }
        Dataset ds = task.getDataset();
        NeuralNet nn = task.getNeuralNet();
        System.out.println("IN: testTask " + ds + " " + nn);
        int correct = mlManager.testLocal(task.getTitle(), b.getData(), b.getLabels());
        System.out.println("OUT: " + correct);
        if (correct == -1) throw new RemoteException(Utility.MODELH5);
        else if (correct == -2) throw new RemoteException(Utility.CORRUPTED_BATCH);
        return correct;
    }

    @Override
    public float testTask(String id, String path, String dataType, int batchSize) throws RemoteException {
        Task task = taskManager.getTask(id);
        if (task == null) {
            System.err.println("ERROR: No such task in map.");
            throw new RemoteException(Utility.NO_TASK_IN_MAP);
        }
        Dataset ds = task.getDataset();
        NeuralNet nn = task.getNeuralNet();
        System.out.println("IN: testTask " + ds + " " + nn);
        float correct = mlManager.testRemote(task.getTitle(), path, dataType, batchSize);
        System.out.println("OUT: " + correct);
        if (Float.compare(correct, -1) == 0) throw new RemoteException(Utility.REMOTE_IOEXC);
        else if (Float.compare(correct, -2) == 0) throw new RemoteException(Utility.BAD_DATA_TYPE);
        else if (Float.compare(correct, -3) == 0) throw new RemoteException(Utility.BAD_LABEL);
        return correct;
    }

}
