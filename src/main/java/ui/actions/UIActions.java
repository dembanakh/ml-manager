package ui.actions;

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

    ELSE {
        public UIAction execute(Scanner scanner) {
            OutputParser.writeBack_ELSE();
            return UIController.getPreviousAction();
        }
    };

    public UIAction execute(Scanner scanner) {
        return null;
    }

}
