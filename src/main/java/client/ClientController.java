package client;

import server.MLManagerException;
import server.ServerAPI;
import server.Task;
import ui.OutputParser;
import utility.Batch;
import utility.BatchProvider;
import utility.Errno;
import utility.Utility;

import java.io.IOException;
import java.rmi.Naming;
import java.rmi.RemoteException;
import java.util.List;
import java.util.Map;

public class ClientController {

    private ServerAPI server;

    private Task currentTask;

    private BatchProvider currentProvider;

    public static Errno errno = Errno.NONE;

    public ClientController() throws FailedToGetRMIStub {
        try {
            server = (ServerAPI) Naming.lookup(Utility.RMISTUB);
            //System.out.println(server);
        } catch (Exception e) {
            //e.printStackTrace();
            throw new FailedToGetRMIStub();
        }
        currentTask = null;
    }

    @Remote
    public Map<String, Task> getActiveTasks() {
        try {
            return server.getActiveTasks();
        } catch (RemoteException e) {
            //e.printStackTrace();
            errno = Errno.REMOTEEXC;
            return null;
        }
    }

    @Remote
    public boolean refreshTasks() {
        try {
            server.refreshTasks();
        } catch (RemoteException e) {
            //e.printStackTrace();
            errno = Errno.REMOTEEXC;
            return false;
        } catch (MLManagerException e) {
            if (e.getErrorCause().equals(Utility.ErrorCause.TASKS_SRC)) errno = Errno.TASKS_SRC;
            else errno = Errno.NONE;
            return false;
        }
        return true;
    }

    @Remote
    public boolean setTask(String id) {
        if (id == null) {
            currentTask = null;
            return true;
        }

        Task task;
        try {
            task = server.getActiveTaskById(id);
        } catch (RemoteException e) {
            //e.printStackTrace();
            errno = Errno.REMOTEEXC;
            return false;
        } catch (MLManagerException e) {
            if (e.getErrorCause().equals(Utility.ErrorCause.NO_TASK_IN_MAP)) errno = Errno.NO_TASK_IN_MAP;
            else errno = Errno.NONE;
            return false;
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
    public boolean addTask(Task task) {
        try {
            server.addTask(task);
        } catch (RemoteException e) {
            //e.printStackTrace();
            errno = Errno.REMOTEEXC;
            return false;
        }
        return true;
    }

    public Task getTask() {
        return currentTask;
    }

    /*
     * Check if task was not concurrently modified or deleted from some other client.
     */
    @Remote
    public boolean checkCurrentTaskValidity() {
        try {
            return currentTask.equals(server.getActiveTaskById(currentTask.getTitle()));
        } catch (RemoteException e) {
            //e.printStackTrace();
            errno = Errno.NONE;
            return false;
        } catch (MLManagerException e) {
            //e.printStackTrace();
            errno = Errno.CONCURRENT_TASK_MODIFICATION;
            return false;
        }
    }

    /*
     * Returns false if an error occurred and errno set correspondingly
     */
    @Remote
    public boolean trainCurrentTask() {
        try {
            boolean status = server.trainTask(currentTask.getTitle());
            if (!status) errno = Errno.FAIL_READ_DATASET;
            else errno = Errno.NONE;
            return status;
        } catch (RemoteException e) {
            //e.printStackTrace();
            errno = Errno.NONE;
            return false;
        } catch (MLManagerException e) {
            if (e.getErrorCause().equals(Utility.ErrorCause.NO_TASK_IN_MAP)) errno = Errno.NO_TASK_IN_MAP;
            return false;
        }
    }

    /*
     * Returns -1f if an error occurred and errno set correspondingly
     */
    @Remote
    public float testCurrentTask() {
        try {
            if (currentProvider.isLocal()) {
                int correct = 0;
                int samples = 0;
                for (Batch b : currentProvider) {
                    if (b == null) {
                        errno = Errno.CORRUPTED_BATCH;
                        return -1f;
                    }
                    OutputParser.notifyBatchProcessed(b.getSize());
                    int batchCorrect = server.testTask(currentTask.getTitle(), b);
                    correct += batchCorrect;
                    samples += b.getSize();
                }
                return (float) correct / samples;
            } else {
                return server.testTask(currentTask.getTitle(), currentProvider.getPath(),
                        currentProvider.getDataType().toString(), currentProvider.getBatchSize());
            }
        } catch (RemoteException e) {
            errno = Errno.REMOTEEXC;
            return -1f;
        } catch (MLManagerException e) {
            //e.printStackTrace();
            switch (e.getErrorCause()) {
                case MODELH5:
                    errno = Errno.FAIL_READ_H5;
                    break;
                case CORRUPTED_BATCH:
                    errno = Errno.CORRUPTED_BATCH;
                    break;
                case REMOTE_IOEXC:
                    errno = Errno.REMOTE_IOEXC;
                    break;
                case BAD_DATA_TYPE:
                    errno = Errno.BAD_DATA_TYPE;
                    break;
                case BAD_LABEL:
                    errno = Errno.BAD_LABEL_IN_TXT;
                    break;
                default:
                    errno = Errno.NONE;
                    break;
            }
            return -1f;
        } finally {
            currentProvider = null;
        }
    }

    /*
     * Constructs a batch provider and checks if @param path exists locally.
     */
    public boolean processLocalTestPath(String path) {
        try {
            currentProvider = new BatchProvider(path, Utility.DataType.IMAGE, 1, true);
            // batchSize could be anything but Azure server does not have much RAM
            return true;
        } catch (IOException e) {
            //e.printStackTrace();
            errno = Errno.IOEXC;
            return false;
        }
    }

    /*
     * Constructs a batch provider and checks if @param path exists remotely.
     */
    @Remote
    public boolean processRemoteTestPath(String path) {
        try {
            boolean exists = server.checkPath(path);
            if (exists) currentProvider = new BatchProvider(path, Utility.DataType.IMAGE, 1, false);
            else errno = Errno.NO_PATH_ON_SERVER;
            return exists;
        } catch (RemoteException e) {
            //e.printStackTrace();
            errno = Errno.REMOTEEXC;
            return false;
        } catch (IOException e) {
            //e.printStackTrace();
            errno = Errno.IOEXC;
            return false;
        }
    }

    @Remote
    public boolean deleteTask(String id) {
        try {
            server.deleteTask(id);
        } catch (RemoteException e) {
            //e.printStackTrace();
            errno = Errno.REMOTEEXC;
            return false;
        }
        return true;
    }

    @Remote
    public boolean changeTask_dataset(String id, String dataset) {
        try {
            boolean status = server.changeTask_dataset(id, dataset);
            if (!status) errno = Errno.NO_TASK_IN_MAP;
            return status;
        } catch (RemoteException e) {
            //e.printStackTrace();
            errno = Errno.REMOTEEXC;
            return false;
        }
    }

    @Remote
    public boolean changeTask_neuralNet(String id, String dataset) {
        try {
            boolean status = server.changeTask_neuralNet(id, dataset);
            if (!status) errno = Errno.NO_TASK_IN_MAP;
            return status;
        } catch (RemoteException e) {
            //e.printStackTrace();
            errno = Errno.REMOTEEXC;
            return false;
        }
    }

    @Remote
    public List<String> getDatasets() {
        try {
            return server.getDatasets();
        } catch (RemoteException e) {
            //e.printStackTrace();
            errno = Errno.REMOTEEXC;
            return null;
        }
    }

    @Remote
    public List<String> getNeuralNets() {
        try {
            return server.getNeuralNets();
        } catch (RemoteException e) {
            //e.printStackTrace();
            errno = Errno.REMOTEEXC;
            return null;
        }
    }

}
