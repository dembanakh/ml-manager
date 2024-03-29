package ui.commands;

public enum Command {
    EXIT("exit"), TRAIN("train"), TEST("test"), TASKS("tasks"),
    ID("task"),
    YES("Yes"), NO("no"),
    BACK("back"), MAIN("main"),
    CREATE("create"), DELETE("delete"), CHANGE("change"),
    DATASET("dataset"), NEURALNET("network"),
    LOCAL("local"), REMOTE("remote"),
    REFRESH("refresh"); // to add sth else

    private String userLine;

    Command(String userLine) {
        this.userLine = userLine;
    }

    public String getUserLine() {
        return userLine;
    }
}
