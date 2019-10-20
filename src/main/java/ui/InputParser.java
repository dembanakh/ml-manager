package ui;

import client.ClientController;
import ui.actions.UIAction;
import ui.actions.UIActions;
import ui.commands.Command;

public class InputParser {

    public static UIAction parseLine(String line, UIActions currentAction) {
        switch (currentAction) {
            case ROOT:
                if (line.startsWith(Command.EXIT.getUserLine())) return UIActions.EXIT;
                if (line.startsWith(Command.TEST.getUserLine())) return UIActions.TEST;
                if (line.startsWith(Command.TRAIN.getUserLine())) return UIActions.TRAIN;
                if (line.startsWith(Command.TASKS.getUserLine())) return UIActions.TASKS;
                return UIActions.ERROR;
            case TASKS:
                if (line.startsWith(Command.CREATE.getUserLine())) return UIActions.TASKS_CREATE;
                if (line.startsWith(Command.DELETE.getUserLine())) {
                    if (ClientController.setTask(Integer.parseInt(line.split(" ")[1])))
                        return UIActions.TASKS_DELETE;
                    else
                        return UIActions.ERROR;
                }
                if (line.startsWith(Command.CHANGE.getUserLine())) {
                    if (ClientController.setTask(Integer.parseInt(line.split(" ")[1])))
                        return UIActions.TASKS_CHANGE;
                    else
                        return UIActions.ERROR;
                }
                if (line.startsWith(Command.BACK.getUserLine())) return UIActions.BACK;
                return UIActions.ERROR;
            case TASKS_CHANGE:
                if (line.startsWith(Command.DATASET.getUserLine())) return UIActions.TASKS_CHANGE_DATASET;
                if (line.startsWith(Command.NEURALNET.getUserLine())) return UIActions.TASKS_CHANGE_NEURALNET;
                if (line.startsWith(Command.BACK.getUserLine())) return UIActions.BACK;
                if (line.startsWith(Command.MAIN.getUserLine())) return UIActions.MAIN;
                return UIActions.ERROR;
            case TEST:
                if (line.startsWith(Command.ID.getUserLine())) {
                    if (ClientController.setTask(Integer.parseInt(line.split(" ")[1])))
                        return UIActions.TEST_ID;
                    else
                        return UIActions.ERROR;
                }
                if (line.startsWith(Command.BACK.getUserLine())) return UIActions.BACK;
                return UIActions.ERROR;
            case TEST_ID:
                if (line.startsWith(Command.BACK.getUserLine())) return UIActions.BACK;
                if (line.startsWith(Command.MAIN.getUserLine())) return UIActions.MAIN;
                if (ClientController.processTestPath(line)) {
                    return UIActions.CLIENT_TEST;
                } else {
                    return UIActions.ERROR;
                }
            case TRAIN:
                if (line.startsWith(Command.ID.getUserLine())) {
                    if (ClientController.setTask(Integer.parseInt(line.split(" ")[1])))
                        return UIActions.TRAIN_ID;
                    else
                        return UIActions.ERROR;
                }
                if (line.startsWith(Command.BACK.getUserLine())) return UIActions.BACK;
                return UIActions.ERROR;
            case TRAIN_ID:
                if (line.startsWith(Command.YES.getUserLine())) return UIActions.CLIENT_TRAIN;
                if (line.startsWith(Command.NO.getUserLine())) return UIActions.MAIN;
                if (line.startsWith(Command.BACK.getUserLine())) return UIActions.BACK;
                if (line.startsWith(Command.MAIN.getUserLine())) return UIActions.MAIN;
                return UIActions.ERROR;
            case ERROR:
                break;
            case EXIT:
                break;
        }

        return null;
    }

    public static boolean parseSimpleBoolean(String line) {
        return line.startsWith(Command.YES.getUserLine());
    }

}
