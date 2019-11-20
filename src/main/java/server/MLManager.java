package server;

class MLManager {

    MLManager() {
        System.loadLibrary("native");
    }

    native boolean train(String datasetPath, String architecture, String taskName);
    native int     test (String architecture, String taskName, Object[] data);
    native float   test (String architecture, String taskName, String dataPath, String dataType, int batchSize);

}
