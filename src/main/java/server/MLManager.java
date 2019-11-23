package server;

class MLManager {

    MLManager() {
        System.loadLibrary("native");
    }

    /*
     * Returns true if successful.
     * If one of arguments contain strange data returns false;
     * if it couldn't read in training data (if {@param architecture != IMAGENET}) returns false.
     */
    native boolean    train (String datasetPath, String architecture, String taskName);
    /*
     * If successful, returns number of correctly predicted samples.
     * Else, returns -1 if an IOError occurred while reading in a model .h5 file;
     *       returns -2 if @param data or @param labels doesn't contain actual int **** and int * types correspondingly.
     */
    native int    testLocal (String taskName, Object[] data, Object[] labels);
    /*
     * If successful, returns accuracy on the dataset on the path @dataPath.
     * Else, returns -1 if an IOError occurred;
     *       returns -2 if @param data_type contains strange data;
     *       returns -3 if one of the file .txt (that should contain label for some sample) contains non-integer data.
     */
    native float testRemote (String taskName, String dataPath, String dataType, int batchSize);

}
