package server;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.List;
import java.util.Map;

public interface ServerAPI extends Remote {
    Map<Integer, Task> getActiveTasks() throws RemoteException;
    Task getActiveTaskById(Integer id) throws RemoteException;
    Integer addTask(Task task) throws RemoteException;
    void deleteTask(Integer id) throws RemoteException;
    boolean changeTask_dataset(Integer id, String dataset) throws RemoteException;
    boolean changeTask_neuralNet(Integer id, String net) throws RemoteException;

    List<String> getDatasets() throws RemoteException;
    List<String> getNeuralNets() throws RemoteException;

    void trainTask(Integer id) throws RemoteException;
}
