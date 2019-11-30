package server;

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
import java.util.concurrent.locks.ReentrantLock;

public class ServerController extends UnicastRemoteObject implements ServerAPI {

    private TaskManager taskManager;
    private DatasetManager datasetManager;
    private NeuralNetManager neuralNetManager;
    private MLManager mlManager;

    private ReentrantLock lock;

    public ServerController() throws RemoteException {
        taskManager = new TaskManager();
        datasetManager = new DatasetManager();
        neuralNetManager = new NeuralNetManager();
        mlManager = new MLManager();

        lock = new ReentrantLock();
    }

    public void start() {
        System.out.println("start");

        try {
            datasetManager.load();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
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
        } catch (NoSuchMLObjectException e) {
            System.err.println("A task from collection \"tasks\" contains non-existing dataset or neural network!");
        }
    }

    @Override
    public Map<String, Task> getActiveTasks() {
        lock.lock();
        try {
            Map<String, Task> activeTasks = taskManager.getActiveTasks();
            System.out.println("IN: getActiveTasks");
            System.out.println("OUT: " + activeTasks);
            return activeTasks;
        } finally {
            lock.unlock();
        }
    }

    @Override
    public void refreshTasks() throws MLManagerException {
        lock.lock();
        try {
            System.out.println("IN: refreshTasks");
            taskManager.load(datasetManager, neuralNetManager);
            System.out.println("OUT: -");
        } catch (NoSuchMLObjectException e) {
            System.err.println("A task from tasks.src contains non-existing dataset or neural network!");
            throw new MLManagerException(Utility.ErrorCause.TASKS_SRC);
        } finally {
            lock.unlock();
        }
    }

    @Override
    public Task getActiveTaskById(String id) throws MLManagerException {
        lock.lock();
        try {
            Task task = taskManager.getActiveTasks().get(id);
            System.out.println("IN: getActiveTaskById");
            System.out.println("OUT: " + task);
            if (task == null) throw new MLManagerException(Utility.ErrorCause.NO_TASK_IN_MAP);
            return task;
        } finally {
            lock.unlock();
        }
    }

    @Override
    public void addTask(Task task) {
        lock.lock();
        try {
            System.out.println("IN: addTask");
            System.out.println("OUT: " + task.getTitle());
            taskManager.addTask(task);
        } finally {
            lock.unlock();
        }
    }

    @Override
    public void deleteTask(String id) {
        lock.lock();
        try {
            System.out.println("IN: deleteTask");
            System.out.println("OUT: -");
            taskManager.deleteTask(id);
        } finally {
            lock.unlock();
        }
    }

    @Override
    public boolean changeTask_dataset(String id, String dataset) {
        lock.lock();
        try {
            System.out.println("IN: changeTask_dataset");
            if (!taskManager.hasTask(id)) return false;
            System.out.println("OUT: " + taskManager.hasTask(id));
            taskManager.changeTask_dataset(id, dataset);
            return true;
        } finally {
            lock.unlock();
        }
    }

    @Override
    public boolean changeTask_neuralNet(String id, String net) {
        lock.lock();
        try {
            System.out.println("IN: changeTask_neuralNet");
            if (!taskManager.hasTask(id)) return false;
            System.out.println("OUT: " + taskManager.hasTask(id));
            taskManager.changeTask_neuralNet(id, net);
            return true;
        } finally {
            lock.unlock();
        }
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
    public boolean trainTask(String id) throws MLManagerException {
        Task task = taskManager.getTask(id);
        if (task == null) {
            System.err.println("ERROR: No such task in map.");
            throw new MLManagerException(Utility.ErrorCause.NO_TASK_IN_MAP);
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
    public int testTask(String id, Batch b) throws MLManagerException {
        Task task = taskManager.getTask(id);
        if (task == null) {
            System.err.println("ERROR: No such task in map.");
            throw new MLManagerException(Utility.ErrorCause.NO_TASK_IN_MAP);
        }
        Dataset ds = task.getDataset();
        NeuralNet nn = task.getNeuralNet();
        System.out.println("IN: testTask " + ds + " " + nn);
        int correct = mlManager.testLocal(task.getTitle(), b.getData(), b.getLabels());
        System.out.println("OUT: " + correct);
        if (correct == -1) throw new MLManagerException(Utility.ErrorCause.MODELH5);
        else if (correct == -2) throw new MLManagerException(Utility.ErrorCause.CORRUPTED_BATCH);
        return correct;
    }

    /*
     * Returns fraction of correct predictions.
     */
    @Override
    public float testTask(String id, String path, String dataType, int batchSize) throws MLManagerException {
        Task task = taskManager.getTask(id);
        if (task == null) {
            System.err.println("ERROR: No such task in map.");
            throw new MLManagerException(Utility.ErrorCause.NO_TASK_IN_MAP);
        }
        Dataset ds = task.getDataset();
        NeuralNet nn = task.getNeuralNet();
        System.out.println("IN: testTask " + ds + " " + nn);
        float correct = mlManager.testRemote(task.getTitle(), path, dataType, batchSize);
        System.out.println("OUT: " + correct);
        if (Float.compare(correct, -1) == 0) throw new MLManagerException(Utility.ErrorCause.REMOTE_IOEXC);
        else if (Float.compare(correct, -2) == 0) throw new MLManagerException(Utility.ErrorCause.BAD_DATA_TYPE);
        else if (Float.compare(correct, -3) == 0) throw new MLManagerException(Utility.ErrorCause.BAD_LABEL);
        return correct;
    }

}
