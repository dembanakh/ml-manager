package ui;

import org.omg.PortableInterceptor.SYSTEM_EXCEPTION;
import server.Task;
import ui.commands.Command;

import java.util.List;
import java.util.Map;

public class OutputParser {

    public static void writePrompt() {
        StringBuilder builder = new StringBuilder();
        builder.append("Please, choose one of commands: ");
        builder.append(Command.TASKS.getUserLine());
        builder.append(", ");
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
        builder.append("Current task: dataset = ");
        builder.append(dataset);
        builder.append(" + neuralNetwork = ");
        builder.append(net);
        builder.append(".");
        System.out.println(builder);
    }

    public static void writeBack_TASKS() {
        System.out.println("There is no active tasks. You can create a new one (command: create) or go back to main menu (command: back).");
    }

    public static void writeBack_TASKS(Map<Integer, Task> tasks) {
        StringBuilder builder = new StringBuilder();
        builder.append("Active tasks:\n");
        tasks.forEach((id, task) -> {
            builder.append(task.getDataset());
            builder.append(" with ");
            builder.append(task.getNeuralNet());
            builder.append(" (id ");
            builder.append(id);
            builder.append(")\n");
        });
        builder.append("You can create a new task (command: create), delete an existing one (command: delete <id>), change contents of a particular task (command: change <id>) or go back to main menu (command: back).");
        System.out.println(builder);
    }

    public static void writeBack_TASKS_CREATE() {
        System.out.println("Creating new task: dataset = ? + neuralNetwork = ?.");
    }

    public static void writeBack_TASKS_CREATE_dataset(List<String> datasets) {
        writeBack_listDatasets(datasets);
        System.out.print("Enter the name of dataset.\ndataset = ");
    }

    private static void writeBack_listDatasets(List<String> datasets) {
        StringBuilder builder = new StringBuilder();
        builder.append("Existing datasets: ");
        for (int i = 0; i < datasets.size() - 1; i++) {
            builder.append(datasets.get(i));
            builder.append(", ");
        }
        builder.append(datasets.get(datasets.size()-1));
        builder.append(".\n");
        System.out.print(builder);
    }

    public static void writeBack_TASKS_CREATE_net(List<String> nets) {
        writeBack_listNetworks(nets);
        System.out.print("Enter the name of neural network.\nneuralnet = ");
    }

    private static void writeBack_listNetworks(List<String> nets) {
        StringBuilder builder = new StringBuilder();
        builder.append("Existing neural networks: ");
        for (int i = 0; i < nets.size() - 1; i++) {
            builder.append(nets.get(i));
            builder.append(", ");
        }
        builder.append(nets.get(nets.size()-1));
        builder.append(".\n");
        System.out.print(builder);
    }

    public static void writeBack_TASKS_DELETE(int id) {
        StringBuilder builder = new StringBuilder();
        builder.append("Are you sure you want to delete task #");
        builder.append(id);
        builder.append("? [Yes|no]");
        System.out.println(builder);
    }

    public static void writeBack_TASKS_CHANGE() {
        System.out.println("You can change the dataset that the task is trained on (command: dataset) or the neural network architecture that the task uses (command: network).\nYou may also go back (command: back) or to the main menu (command: main).");
    }

    public static void writeBack_TASKS_CHANGE_DATASET(int id, List<String> datasets) {
        writeBack_listDatasets(datasets);

        StringBuilder builder = new StringBuilder();
        builder.append("Enter the name of a new dataset for the task #");
        builder.append(id);
        builder.append(".");
        System.out.println(builder);
    }

    public static void writeBack_TASKS_CHANGE_NEURALNET(int id, List<String> nets) {
        writeBack_listNetworks(nets);

        StringBuilder builder = new StringBuilder();
        builder.append("Enter the name of a new neural network architecture for the task #");
        builder.append(id);
        builder.append(".");
        System.out.println(builder);
    }

    public static void writeBack_TEST() {
        System.out.println("There is no active tasks. Go back to main menu (command: back) and create a new one from there.");
    }

    public static void writeBack_TEST(Map<Integer, Task> tasks) {
        StringBuilder builder = new StringBuilder();
        builder.append("Choose one of the existing tasks (command: task <id>) or go back to main menu (command: back).\n");
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

    public static void writeBack_TEST_ID(String net) {
        StringBuilder builder = new StringBuilder();
        builder.append("Testing current task using neural network ");
        builder.append(net);
        builder.append(". Enter a full path to directory with testing data.\n");
        builder.append("You can also go back (command: back) or to main menu (command: main).\n");
        builder.append("path = ");
        System.out.print(builder);
    }

    public static void writeBack_TRAIN() {
        System.out.println("There is no active tasks. You can create one (command: new) or go back to main menu (command: back)");
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
