package ui;

import org.omg.PortableInterceptor.SYSTEM_EXCEPTION;
import server.Task;
import ui.commands.Command;

import java.util.Map;

public class OutputParser {

    public static void writePrompt() {
        StringBuilder builder = new StringBuilder();
        builder.append("Please, choose one of commands: ");
        builder.append(Command.TRAIN.getUserLine());
        builder.append(", ");
        builder.append(Command.TEST.getUserLine());
        builder.append(", ");
        builder.append(Command.EXIT.getUserLine());
        builder.append(".");
        System.out.println(builder);
    }

    public static void writeBack_ELSE() {
        System.out.println("Sorry, but there is no such command in current context.");
    }

    public static void writeBack_currentTask(String dataset, String net) {
        StringBuilder builder = new StringBuilder();
        builder.append("Current task: ");
        builder.append(dataset != null ? dataset : "?");
        builder.append(" + ");
        builder.append(net != null ? net : "?");
        builder.append(".");
        System.out.println(builder);
    }

    public static void writeBack_TRAIN() {
        System.out.println("There is no active tasks. You can create one (command: new) or go back to main menu (command: back)");
    }

    public static void writeBack_TRAIN_NEW_dataset() {
        System.out.print("Enter the name of dataset.\ndataset = ");
    }

    public static void writeBack_TRAIN_NEW_net() {
        System.out.print("Enter the name of neural network.\nneuralnet = ");
    }

    public static void writeBack_TRAIN_NEW(String dataset, String net) {
        StringBuilder builder = new StringBuilder();
        builder.append("Do you really want to train this new task on dataset ");
        builder.append(dataset);
        builder.append(" using neural network ");
        builder.append(net);
        builder.append("? [Yes|no]\n");
        builder.append("You can also go back (command: back) or to main menu (command: main).");
        System.out.println(builder);
    }

    public static void writeBack_TRAIN_ID(String dataset, String net) {
        StringBuilder builder = new StringBuilder();
        builder.append("Do you really want to retrain this task on dataset ");
        builder.append(dataset);
        builder.append(" using neural network ");
        builder.append(net);
        builder.append("? [Yes|no]\n");
        builder.append("You can also go back (command: back) or to main menu (command: main).");
        System.out.println(builder);
    }

    public static void writeBack_TRAIN(Map<Integer, Task> tasks) {
        StringBuilder builder = new StringBuilder();
        builder.append("Choose one of the existing tasks (command: task <id>), create a new one (command: new) or go back to main menu (command: back).\n");
        builder.append("Tasks:\n");
        tasks.forEach((id, task) -> {
            builder.append(task.getDataset());
            builder.append(" with ");
            builder.append(task.getNeuralNet());
            builder.append(" (id ");
            builder.append(id);
            builder.append(")\n");
        });
        System.out.print(builder);
    }

}
