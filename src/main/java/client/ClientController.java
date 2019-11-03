package client;

import server.ServerAPI;
import server.Task;

import java.rmi.Naming;
import java.rmi.RemoteException;
import java.util.List;
import java.util.Map;

public class ClientController {

    private static ServerAPI server;

    private static Task currentTask;

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
    public static float testCurrentTask() {
        return 0f;
    }

    public static boolean processLocalTestPath(String path) {

        return false;
    }

    public static boolean processRemoteTestPath(String path) {

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
