package utility;

public enum Errno {
    NONE("None"),
    SYNTAX_ERR("There is no such command in this context"),
    REMOTEEXC("Connection to the server was interrupted"),
    TASKS_SRC("A source file with a list of tasks contains illegal data"),
    NO_TASK_IN_MAP("There is no task with that id on the server"),
    FAIL_READ_DATASET("Failed to read in the data samples or labels on the server"),
    FAIL_READ_H5("Server failed to read the graph (*.h5) of the selected task"),
    CORRUPTED_BATCH("Batch sent to the server doesn't contain proper representation of samples and/or labels"),
    IOEXC("An error occurred when reading in some local files"),
    NO_PATH_ON_SERVER("Specified path was not found on the server"),
    REMOTE_IOEXC("An error occurred when reading in some files at the server"),
    BAD_DATA_TYPE("Server received illegal argument for the dataType, which is very unlikely to happen, so there is probably a bug in the source code"),
    BAD_LABEL_IN_TXT("Some label file (*.txt) contains non-integer data"),
    INPUT_WITH_SPACE("This input must contain no spaces");

    private final String message;

    Errno(String message) {
        this.message = message;
    }

    public String getMessage() {
        return this.message;
    }
}
