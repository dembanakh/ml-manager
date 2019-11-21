package client;

import server.ServerAPI;
import server.Task;
import ui.OutputParser;
import utility.Batch;
import utility.BatchProvider;
import utility.Utility;

import java.io.IOException;
import java.rmi.Naming;
import java.rmi.RemoteException;
import java.util.List;
import java.util.Map;

public class ClientController {

    private static ServerAPI server;

    private static Task currentTask;

    private static BatchProvider currentProvider;

    public ClientController() {
        try {
            server = (ServerAPI) Naming.lookup("rmi://40.87.143.114:1099/ServerAPI");
            System.out.println(server);
        } catch (Exception e) {
            e.printStackTrace();
        }
        currentTask = null;
    }

    @Remote
    public Map<String, Task> getActiveTasks() {
        try {
            return server.getActiveTasks();
        } catch (RemoteException e) {
            e.printStackTrace();
            return null;
        }
    }

    @Remote
    public void refreshTasks() {
        try {
            server.refreshTasks();
        } catch (RemoteException e) {
            e.printStackTrace();
        }
    }

    @Remote
    public static boolean setTask(String id) {
        if (id == null) {
            currentTask = null;
            return true;
        }

        Task task = null;
        try {
            task = server.getActiveTaskById(id);
        } catch (RemoteException e) {
            e.printStackTrace();
        }

        if (task == null) {
            currentTask = null;
            return false;
        } else {
            currentTask = task;
            return true;
        }
    }

    @Remote
    public static void addTask(Task task) {
        try {
            server.addTask(task);
        } catch (RemoteException e) {
            e.printStackTrace();
        }
    }

    public static Task getTask() {
        return currentTask;
    }

    @Remote
    public static void trainCurrentTask() {
        try {
            server.trainTask(currentTask.getTitle());
        } catch (RemoteException e) {
            e.printStackTrace();
        }
    }

    @Remote
    public static float testCurrentTask() throws IOException {
        try {
            if (currentProvider.isLocal()) {
                int correct = 0;
                int samples = 0;
                for (Batch b : currentProvider) {
                    if (b == null) throw new IOException("Some files in the directory being tested are corrupted.");
                    OutputParser.notifyBatchProcessed(b.getSize());
                    int batchCorrect = server.testTask(currentTask.getTitle(), b);
                    if (batchCorrect == -1) {
                        throw new RuntimeException("An error occurred during the testing!");
                    }
                    correct += batchCorrect;
                    samples += b.getSize();
                }
                return (float) correct / samples;
            } else {
                float precision = server.testTask(currentTask.getTitle(), currentProvider.getPath(),
                        currentProvider.getDataType().toString(), currentProvider.getBatchSize());
                if (Float.compare(precision, -1f) == 0) {
                    throw new RuntimeException("An error occurred during the testing!");
                }
                return precision;
            }
        } catch (RemoteException e) {
            e.printStackTrace();
        } finally {
            currentProvider = null;
        }

        return 0f;
    }

    public static boolean processLocalTestPath(String path) {
        try {
            currentProvider = new BatchProvider(path, Utility.DataType.IMAGE, 1, true);
            return true;
        } catch (IOException e) {
            return false;
        }
    }

    public static boolean processRemoteTestPath(String path) {
        try {
            boolean exists = server.checkPath(path);
            if (exists) currentProvider = new BatchProvider(path, Utility.DataType.IMAGE, 1, false);
            return exists;
        } catch (IOException e) {
            e.printStackTrace();
        }

        return false;
    }

    @Remote
    public static void deleteTask(String id) {
        try {
            server.deleteTask(id);
        } catch (RemoteException e) {
            e.printStackTrace();
        }
    }

    @Remote
    public static void changeTask_dataset(String id, String dataset) {
        try {
            server.changeTask_dataset(id, dataset);
        } catch (RemoteException e) {
            e.printStackTrace();
        }
    }

    @Remote
    public static void changeTask_neuralNet(String id, String dataset) {
        try {
            server.changeTask_neuralNet(id, dataset);
        } catch (RemoteException e) {
            e.printStackTrace();
        }
    }

    @Remote
    public static List<String> getDatasets() {
        try {
            return server.getDatasets();
        } catch (RemoteException e) {
            e.printStackTrace();
            return null;
        }
    }

    @Remote
    public static List<String> getNeuralNets() {
        try {
            return server.getNeuralNets();
        } catch (RemoteException e) {
            e.printStackTrace();
            return null;
        }
    }

}
