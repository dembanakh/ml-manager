package ui.actions;

import client.ClientController;
import server.Task;
import ui.InputParser;
import ui.OutputParser;
import ui.UIController;
import ui.commands.Command;

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

    TASKS {
        public UIAction execute(Scanner scanner) {
            Map<Integer, Task> activeTasks = UIController.getClientController().getActiveTasks();
            if (activeTasks.isEmpty()) {
                OutputParser.writeBack_TASKS();
            } else {
                OutputParser.writeBack_TASKS(activeTasks);
            }

            String input = scanner.nextLine();
            return InputParser.parseLine(input, this);
        }
    },

    TASKS_CREATE {
        public UIAction execute(Scanner scanner) {
            ClientController.setTask(null);
            OutputParser.writeBack_TASKS_CREATE();

            OutputParser.writeBack_TASKS_CREATE_dataset();
            String dataset = scanner.nextLine();

            OutputParser.writeBack_TASKS_CREATE_net();
            String net = scanner.nextLine();

            ClientController.addTask(new Task(dataset, net));

            System.out.println("Added.\n");
            return UIActions.MAIN;
        }
    },

    TASKS_DELETE {
        public UIAction execute(Scanner scanner) {
            Integer id = ClientController.getTask().getID();
            OutputParser.writeBack_TASKS_DELETE(id);
            String check = scanner.nextLine();
            if (InputParser.parseSimpleBoolean(check)) {
                ClientController.deleteTask(id);
            } else {
                ClientController.setTask(null);
            }
            return UIActions.BACK;
        }
    },

    TASKS_CHANGE {
        public UIAction execute(Scanner scanner) {
            Task currentTask = ClientController.getTask();
            OutputParser.writeBack_currentTask(currentTask.getDataset(), currentTask.getNeuralNet());

            OutputParser.writeBack_TASKS_CHANGE();
            String input = scanner.nextLine();
            return InputParser.parseLine(input, this);
        }
    },

    TASKS_CHANGE_DATASET {
        public UIAction execute(Scanner scanner) {
            Task currentTask = ClientController.getTask();
            OutputParser.writeBack_currentTask(currentTask.getDataset(), currentTask.getNeuralNet());

            OutputParser.writeBack_TASKS_CHANGE_DATASET(currentTask.getID());
            String input = scanner.nextLine();
            ClientController.changeTask_dataset(currentTask.getID(), input);

            ClientController.setTask(null);
            UIController.memory.pop();
            return UIActions.BACK;
        }
    },

    TASKS_CHANGE_NEURALNET {
        public UIAction execute(Scanner scanner) {
            Task currentTask = ClientController.getTask();
            OutputParser.writeBack_currentTask(currentTask.getDataset(), currentTask.getNeuralNet());

            OutputParser.writeBack_TASKS_CHANGE_NEURALNET(currentTask.getID());
            String input = scanner.nextLine();
            ClientController.changeTask_neuralNet(currentTask.getID(), input);

            ClientController.setTask(null);
            UIController.memory.pop();
            return UIActions.BACK;
        }
    },

    TEST {
        public UIAction execute(Scanner scanner) {
            Map<Integer, Task> activeTasks = UIController.getClientController().getActiveTasks();
            if (activeTasks.isEmpty()) {
                OutputParser.writeBack_TEST();
            } else {
                OutputParser.writeBack_TEST(activeTasks);
            }

            String input = scanner.nextLine();
            return InputParser.parseLine(input, this);
        }
    },

    TEST_ID {
        public UIAction execute(Scanner scanner) {
            Task currentTask = ClientController.getTask();
            OutputParser.writeBack_currentTask(currentTask.getDataset(), currentTask.getNeuralNet());

            OutputParser.writeBack_TEST_ID(currentTask.getNeuralNet());

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

    TRAIN_ID {
        public UIAction execute(Scanner scanner) {
            Task currentTask = ClientController.getTask();
            OutputParser.writeBack_currentTask(currentTask.getDataset(), currentTask.getNeuralNet());

            OutputParser.writeBack_TRAIN_ID(currentTask.getDataset(), currentTask.getNeuralNet());

            String input = scanner.nextLine();
            return InputParser.parseLine(input, this);
        }
    },

    CLIENT_TEST {
        public UIAction execute(Scanner scanner) {
            System.out.println("Testing...");
            float accuracy = ClientController.testCurrentTask();
            System.out.println("Done - " + accuracy);
            ClientController.setTask(null);
            return UIActions.MAIN;
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
    };

    public UIAction execute(Scanner scanner) {
        return null;
    }

}
