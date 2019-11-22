package server;

public class NoSuchMLObjectException extends Exception {

    private final String taskTitle;
    private final String taskDs;
    private final String taskNn;

    NoSuchMLObjectException() {
        this.taskTitle = this.taskDs = this.taskNn = null;
    }

    NoSuchMLObjectException(String taskTitle, String taskDs, String taskNn) {
        this.taskTitle = taskTitle;
        this.taskDs = taskDs;
        this.taskNn = taskNn;
    }

    @Override
    public String toString() {
        if (taskDs == null) {
            return super.toString();
        } else {
            StringBuilder builder = new StringBuilder();
            builder.append("Task: ");
            builder.append(taskTitle);
            builder.append(", ");
            builder.append(taskDs);
            builder.append(", ");
            builder.append(taskNn);
            builder.append(":\n");
            builder.append(super.toString());
            return builder.toString();
        }
    }
}
