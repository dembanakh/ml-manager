package ui.actions;

import client.ClientController;
import server.Task;
import ui.InputParser;
import ui.OutputParser;
import ui.UIController;
import ui.commands.Command;
import utility.Errno;
import utility.Utility;

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
            if (activeTasks == null) {
                return UIActions.ERROR;
            }
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
            boolean status = UIController.getClientController().refreshTasks();
            UIController.memory.pop();
            if (!status) {
                return UIActions.ERROR;
            }
            return UIController.memory.pop();
        }
    },

    TASKS_CREATE {
        public UIAction execute(Scanner scanner) {
            if (!UIController.getClientController().setTask(null)) return UIActions.ERROR;
            OutputParser.writeBack_TASKS_CREATE();

            List<String> datasets = UIController.getClientController().getDatasets();
            List<String> neuralNets = UIController.getClientController().getNeuralNets();

            if (datasets == null || neuralNets == null) {
                return UIActions.ERROR;
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

            if (!UIController.getClientController().addTask(new Task(title, dataset, net))) return UIActions.ERROR;

            System.out.println("Added.");
            return UIActions.MAIN;
        }
    },

    TASKS_DELETE {
        public UIAction execute(Scanner scanner) {
            String id = UIController.getClientController().getTask().getTitle();
            OutputParser.writeBack_TASKS_DELETE(id);
            String check = scanner.nextLine();
            if (InputParser.parseSimpleBoolean(check, Command.YES.getUserLine())) {
                if (!UIController.getClientController().deleteTask(id)) return UIActions.ERROR;
                System.out.println("Deleted.");
            } else if (InputParser.parseSimpleBoolean(check, Command.NO.getUserLine())){
                UIController.getClientController().setTask(null);
            } else {
                ClientController.errno = Errno.SYNTAX_ERR;
                return UIActions.ERROR;
            }
            return UIActions.BACK;
        }
    },

    TASKS_CHANGE {
        public UIAction execute(Scanner scanner) {
            Task currentTask = UIController.getClientController().getTask();
            OutputParser.writeBack_currentTask(currentTask.getDataset().getName(), currentTask.getNeuralNet().toString());

            OutputParser.writeBack_TASKS_CHANGE();
            String input = scanner.nextLine();
            return InputParser.parseLine(input, this);
        }
    },

    TASKS_CHANGE_DATASET {
        public UIAction execute(Scanner scanner) {
            Task currentTask = UIController.getClientController().getTask();
            OutputParser.writeBack_currentTask(currentTask.getDataset().getName(), currentTask.getNeuralNet().toString());

            List<String> datasets = UIController.getClientController().getDatasets();
            if (datasets == null) {
                return UIActions.ERROR;
            }

            OutputParser.writeBack_TASKS_CHANGE_DATASET(currentTask.getTitle(), datasets);
            String input = scanner.nextLine();
            if (!datasets.contains(input)) {
                System.err.println("There is no such dataset!\nAborting...");
                return UIActions.BACK;
            }

            if (!UIController.getClientController().changeTask_dataset(currentTask.getTitle(), input)) return UIActions.ERROR;

            System.out.println("Changed.");
            UIController.getClientController().setTask(null);
            UIController.memory.pop();
            return UIActions.BACK;
        }
    },

    TASKS_CHANGE_NEURALNET {
        public UIAction execute(Scanner scanner) {
            Task currentTask = UIController.getClientController().getTask();
            OutputParser.writeBack_currentTask(currentTask.getDataset().getName(), currentTask.getNeuralNet().toString());

            List<String> networks = UIController.getClientController().getNeuralNets();
            if (networks == null) {
                return UIActions.ERROR;
            }

            OutputParser.writeBack_TASKS_CHANGE_NEURALNET(currentTask.getTitle(), networks);
            String input = scanner.nextLine();
            if (!networks.contains(input)) {
                System.err.println("There is no such neural network architecture!\nAborting...");
                return UIActions.BACK;
            }

            if (!UIController.getClientController().changeTask_neuralNet(currentTask.getTitle(), input)) return UIActions.ERROR;

            System.out.println("Changed.");
            UIController.getClientController().setTask(null);
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
            Task currentTask = UIController.getClientController().getTask();
            OutputParser.writeBack_currentTask(currentTask.getDataset().getName(), currentTask.getNeuralNet().toString());

            OutputParser.writeBack_TEST_ID(currentTask.getNeuralNet().toString());

            String input = scanner.nextLine();
            return InputParser.parseLine(input, this);
        }
    },

    TRAIN {
        public UIAction execute(Scanner scanner) {
            Map<String, Task> activeTasks = UIController.getClientController().getActiveTasks();
            if (activeTasks == null) {
                return UIActions.ERROR;
            }
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
            Task currentTask = UIController.getClientController().getTask();
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
                float accuracy = UIController.getClientController().testCurrentTask();
                if (Float.compare(accuracy, -1) == 0) return UIActions.ERROR;
                System.out.println("Done - " + accuracy);
            } finally {
                UIController.getClientController().setTask(null);
            }
            return UIActions.MAIN;
        }
    },

    CLIENT_TRAIN {
        public UIAction execute(Scanner scanner) {
            System.out.println("Training...");
            try {
                boolean status = UIController.getClientController().trainCurrentTask();
                if (!status) return UIActions.ERROR;
                System.out.println("Done");
            } finally {
                UIController.getClientController().setTask(null);
            }
            return UIActions.MAIN;
        }
    },

    RETRY {
        public UIAction execute(Scanner scanner) {
            UIController.memory.pop();
            return UIController.memory.pop();
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
            Errno errno = ClientController.errno;
            if (errno != Errno.NONE)
                OutputParser.writeBack_error(errno.getMessage());

            UIActions nextAction = UIActions.nextAction(errno);
            ClientController.errno = Errno.NONE;
            return nextAction;
        }
    };

    public UIAction execute(Scanner scanner) {
        return null;
    }

    static UIActions nextAction(Errno errno) {
        if (errno == Errno.REMOTEEXC || errno == Errno.TASKS_SRC || errno == Errno.BAD_DATA_TYPE)
            return null;
        if (errno == Errno.FAIL_READ_DATASET || errno == Errno.FAIL_READ_H5 || errno == Errno.REMOTE_IOEXC || errno == Errno.BAD_LABEL_IN_TXT)
            return UIActions.MAIN;
        if (errno == Errno.CORRUPTED_BATCH || errno == Errno.IOEXC)
            return UIActions.BACK;
        if (errno == Errno.SYNTAX_ERR || errno == Errno.NO_TASK_IN_MAP || errno == Errno.NO_PATH_ON_SERVER)
            return UIActions.RETRY;
        if (errno == Errno.NONE) {
            System.err.println("An unknown error occurred!");
            return null;
        }
        // never reaches here
        return null;
    }

}
