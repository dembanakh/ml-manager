package ui;

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
        System.out.println("Sorry, but there is no such command");
    }

    public static void writeBack_TRAIN() {
        System.out.println("There is no active tasks. You can create one (command: new) or go back to main menu (command: back)");
    }

    public static void writeBack_TRAIN(Map<Integer, Task> tasks) {

    }

}
