package ui.actions;

import client.ClientController;
import server.Task;
import ui.InputParser;
import ui.OutputParser;
import ui.UIController;

import java.util.Map;
import java.util.Scanner;

public enum UIActions implements UIAction {

    EXIT,

    ROOT {
        public UIAction execute(Scanner scanner) {
            OutputParser.writePrompt();
            String input = scanner.nextLine();
            return InputParser.parseLine(input, this);
        }
    },

    TEST {
        public UIAction execute(Scanner scanner) {
            OutputParser.writePrompt();
            String input = scanner.nextLine();
            return InputParser.parseLine(input, this);
        }
    },

    TRAIN {
        public UIAction execute(Scanner scanner) {
            Map<Integer, Task> activeTasks = UIController.getClientController().getActiveTasks();
            if (activeTasks.isEmpty()) {
                OutputParser.writeBack_TRAIN();
            } else {
                OutputParser.writeBack_TRAIN(activeTasks);
            }

            String input = scanner.nextLine();
            return InputParser.parseLine(input, this);
        }
    },

    TRAIN_NEW {
        public UIAction execute(Scanner scanner) {
            ClientController.setTask(null);
            OutputParser.writeBack_currentTask(null, null);

            OutputParser.writeBack_TRAIN_NEW_dataset();
            String dataset = scanner.nextLine();

            OutputParser.writeBack_TRAIN_NEW_net();
            String net = scanner.nextLine();

            Integer taskID = ClientController.addTask(new Task(dataset, net));
            ClientController.setTask(taskID);

            OutputParser.writeBack_currentTask(dataset, net);

            OutputParser.writeBack_TRAIN_NEW(dataset, net);

            String input = scanner.nextLine();
            return InputParser.parseLine(input, this);
        }
    },

    TRAIN_ID {
        public UIAction execute(Scanner scanner) {
            Task currentTask = ClientController.getTask();
            OutputParser.writeBack_currentTask(currentTask.getDataset(), currentTask.getNeuralNet());

            OutputParser.writeBack_TRAIN_ID(currentTask.getDataset(), currentTask.getNeuralNet());

            String input = scanner.nextLine();
            return InputParser.parseLine(input, this);
        }
    },

    CLIENT_TRAIN {
        public UIAction execute(Scanner scanner) {
            System.out.println("Training...");
            ClientController.trainCurrentTask();
            System.out.println("Done");
            ClientController.setTask(null);
            return UIActions.MAIN;
        }
    },

    BACK {
        public UIAction execute(Scanner scanner) {
            UIController.memory.pop();
            UIController.memory.pop();
            return UIController.memory.pop();
        }
    },

    MAIN {
        public UIAction execute(Scanner scanner) {
            UIController.memory.clear();
            return UIActions.ROOT;
        }
    },

    ERROR {
        public UIAction execute(Scanner scanner) {
            OutputParser.writeBack_ELSE();
            UIController.memory.pop();
            return UIController.memory.pop();
        }
    },

    ELSE {
        public UIAction execute(Scanner scanner) {
            OutputParser.writeBack_ELSE();
            UIController.memory.pop();
            return UIController.memory.pop();
        }
    };

    public UIAction execute(Scanner scanner) {
        return null;
    }

}
