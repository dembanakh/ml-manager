package ui.actions;

import client.ClientController;
import server.Task;
import ui.InputParser;
import ui.OutputParser;
import ui.UIController;
import ui.commands.Command;
import utility.Utility;

import java.io.IOException;
import java.util.List;
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
            Map<String, Task> activeTasks = UIController.getClientController().getActiveTasks();
            if (activeTasks.isEmpty()) {
                OutputParser.writeBack_TASKS();
            } else {
                OutputParser.writeBack_TASKS(activeTasks);
            }

            String input = scanner.nextLine();
            return InputParser.parseLine(input, this);
        }
    },

    TASKS_REFRESH {
        public UIAction execute(Scanner scanner) {
            UIController.getClientController().refreshTasks();
            UIController.memory.pop();
            return UIController.memory.pop();
        }
    },

    TASKS_CREATE {
        public UIAction execute(Scanner scanner) {
            ClientController.setTask(null);
            OutputParser.writeBack_TASKS_CREATE();

            List<String> datasets = ClientController.getDatasets();
            List<String> neuralNets = ClientController.getNeuralNets();

            if (datasets == null || neuralNets == null) {
                System.err.println("Couldn't load datasets list or neuralNets list from server!");
                return null;
            }

            OutputParser.writeBack_TASKS_CREATE_title();
            String title = scanner.nextLine();
            if (title.length() > Utility.MAX_INPUT_LENGTH) {
                OutputParser.writeBack_lengthExceeded();
                return UIActions.BACK;
            }

            OutputParser.writeBack_TASKS_CREATE_dataset(datasets);
            String dataset = scanner.nextLine();
            if (!datasets.contains(dataset)) {
                System.err.println("There is no such dataset!\nAborting...");
                return UIActions.BACK;
            }

            OutputParser.writeBack_TASKS_CREATE_net(neuralNets);
            String net = scanner.nextLine();
            if (!neuralNets.contains(net)) {
                System.err.println("There is no such neural network architecture!\nAborting...");
                return UIActions.BACK;
            }

            ClientController.addTask(new Task(title, dataset, net));

            System.out.println("Added.");
            return UIActions.MAIN;
        }
    },

    TASKS_DELETE {
        public UIAction execute(Scanner scanner) {
            String id = ClientController.getTask().getTitle();
            OutputParser.writeBack_TASKS_DELETE(id);
            String check = scanner.nextLine();
            if (InputParser.parseSimpleBoolean(check, Command.YES.getUserLine())) {
                ClientController.deleteTask(id);
                System.out.println("Deleted.");
            } else if (InputParser.parseSimpleBoolean(check, Command.NO.getUserLine())){
                ClientController.setTask(null);
            } else {
                OutputParser.writeBack_ELSE();
            }
            return UIActions.BACK;
        }
    },

    TASKS_CHANGE {
        public UIAction execute(Scanner scanner) {
            Task currentTask = ClientController.getTask();
            OutputParser.writeBack_currentTask(currentTask.getDataset().getName(), currentTask.getNeuralNet().toString());

            OutputParser.writeBack_TASKS_CHANGE();
            String input = scanner.nextLine();
            return InputParser.parseLine(input, this);
        }
    },

    TASKS_CHANGE_DATASET {
        public UIAction execute(Scanner scanner) {
            Task currentTask = ClientController.getTask();
            OutputParser.writeBack_currentTask(currentTask.getDataset().getName(), currentTask.getNeuralNet().toString());

            List<String> datasets = ClientController.getDatasets();
            if (datasets == null) {
                System.err.println("Couldn't load datasets list from server!");
                return null;
            }

            OutputParser.writeBack_TASKS_CHANGE_DATASET(currentTask.getTitle(), datasets);
            String input = scanner.nextLine();
            if (!datasets.contains(input)) {
                System.err.println("There is no such dataset!\nAborting...");
                return UIActions.BACK;
            }

            ClientController.changeTask_dataset(currentTask.getTitle(), input);

            System.out.println("Changed.");
            ClientController.setTask(null);
            UIController.memory.pop();
            return UIActions.BACK;
        }
    },

    TASKS_CHANGE_NEURALNET {
        public UIAction execute(Scanner scanner) {
            Task currentTask = ClientController.getTask();
            OutputParser.writeBack_currentTask(currentTask.getDataset().getName(), currentTask.getNeuralNet().toString());

            List<String> networks = ClientController.getNeuralNets();
            if (networks == null) {
                System.err.println("Couldn't load neuralNets list from server!");
                return null;
            }

            OutputParser.writeBack_TASKS_CHANGE_NEURALNET(currentTask.getTitle(), networks);
            String input = scanner.nextLine();
            if (!networks.contains(input)) {
                System.err.println("There is no such neural network architecture!\nAborting...");
                return UIActions.BACK;
            }

            ClientController.changeTask_neuralNet(currentTask.getTitle(), input);

            System.out.println("Changed.");
            ClientController.setTask(null);
            UIController.memory.pop();
            return UIActions.BACK;
        }
    },

    TEST {
        public UIAction execute(Scanner scanner) {
            Map<String, Task> activeTasks = UIController.getClientController().getActiveTasks();
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
            OutputParser.writeBack_currentTask(currentTask.getDataset().getName(), currentTask.getNeuralNet().toString());

            OutputParser.writeBack_TEST_ID(currentTask.getNeuralNet().toString());

            String input = scanner.nextLine();
            return InputParser.parseLine(input, this);
        }
    },

    TRAIN {
        public UIAction execute(Scanner scanner) {
            Map<String, Task> activeTasks = UIController.getClientController().getActiveTasks();
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
            OutputParser.writeBack_currentTask(currentTask.getDataset().getName(), currentTask.getNeuralNet().toString());

            OutputParser.writeBack_TRAIN_ID(currentTask.getDataset().getName(), currentTask.getNeuralNet().toString());

            String input = scanner.nextLine();
            return InputParser.parseLine(input, this);
        }
    },

    CLIENT_TEST {
        public UIAction execute(Scanner scanner) {
            System.out.println("Testing...");
            try {
                float accuracy = ClientController.testCurrentTask();
                System.out.println("Done - " + accuracy);
            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                ClientController.setTask(null);
            }
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
