package client;

import server.ServerAPI;
import server.ServerController;
import server.Task;

import java.rmi.Naming;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.HashMap;
import java.util.Map;

public class ClientController {

    private static ServerAPI server;

    private static Task currentTask;

    public ClientController() {
        try {
            server = (ServerAPI) Naming.lookup("rmi://40.87.143.114/ServerController");
        } catch (Exception e) {
            e.printStackTrace();
        }
        currentTask = null;
    }

    public Map<Integer, Task> getActiveTasks() {
        try {
            return server.getActiveTasks();
        } catch (RemoteException e) {
            e.printStackTrace();
            return null;
        }
    }

    public static boolean setTask(Integer id) {
        if (id == null) {
            currentTask = null;
            return true;
        }

        Task task = null;
        try {
            task = server.getActiveTasks().get(id);
        } catch (RemoteException e) {
            e.printStackTrace();
        }
        if (task == null) {
            return false;
        } else {
            currentTask = task;
            return true;
        }
    }

    public static Integer addTask(Task task) {
        return 0;
    }

    public static Task getTask() {
        return currentTask;
    }

    public static void trainCurrentTask() {

    }

}
