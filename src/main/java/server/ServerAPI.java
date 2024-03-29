package server;

import utility.Batch;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.List;
import java.util.Map;

public interface ServerAPI extends Remote {
    Map<String, Task> getActiveTasks() throws RemoteException;
    void refreshTasks() throws RemoteException;
    Task getActiveTaskById(String id) throws RemoteException;
    void addTask(Task task) throws RemoteException;
    void deleteTask(String id) throws RemoteException;
    boolean changeTask_dataset(String id, String dataset) throws RemoteException;
    boolean changeTask_neuralNet(String id, String net) throws RemoteException;
    boolean checkPath(String path) throws RemoteException;

    List<String> getDatasets() throws RemoteException;
    List<String> getNeuralNets() throws RemoteException;

    boolean trainTask(String id) throws RemoteException;

    int testTask(String id, Batch b) throws RemoteException;
    float testTask(String id, String path, String dataType, int batchSize) throws RemoteException;
}
