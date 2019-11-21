package server;

class MLManager {

    MLManager() {
        System.loadLibrary("native");
    }

    native boolean    train (String datasetPath, String architecture, String taskName);
    native int    testLocal (String taskName, Object[] data, Object[] labels);
    native float testRemote (String taskName, String dataPath, String dataType, int batchSize);

}
