package ui;

import client.ClientController;
import server.Task;
import ui.commands.Command;

import java.util.Map;
import java.util.Scanner;

public class UIController {

    ClientController controller;
    Scanner scanner;

    Command currentCommand;

    public UIController() {
        scanner = new Scanner(System.in);
        controller = new ClientController();
        currentCommand = null;
    }

    public void start() {
        while (true) {
            if (currentCommand == null)
                stageRoot();
            else switch (currentCommand) {
                case EXIT:
                    return;
                case TRAIN:
                    stageTrain();
                    break;
                case TEST:
                    stageTest();
                    break;
                case ELSE:
                    OutputParser.writeBack_ELSE();
                    break;
            }
        }
    }

    private void stageRoot() {
        OutputParser.writePrompt();
        String input = scanner.nextLine();
        currentCommand = InputParser.parseLine(input);
    }

    private void stageTrain() {
        Map<Integer, Task> activeTasks = controller.getActiveTasks();
        if (activeTasks.isEmpty()) {
            OutputParser.writeBack_TRAIN();
        } else {
            OutputParser.writeBack_TRAIN(activeTasks);
        }
    }

    private void stageTest() {

    }

}
