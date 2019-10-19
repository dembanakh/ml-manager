package ui.commands;

public enum Command {
    EXIT("exit"), TRAIN("train"), TEST("test"), ELSE(""); // to add sth else

    private String userLine;

    Command(String userLine) {
        this.userLine = userLine;
    }

    public String getUserLine() {
        return userLine;
    }
}
