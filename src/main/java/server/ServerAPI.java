package server;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.Map;

public interface ServerAPI extends Remote {
    Map<Integer, Task> getActiveTasks() throws RemoteException;
}
