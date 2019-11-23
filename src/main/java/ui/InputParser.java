package ui;

import client.ClientController;
import ui.actions.UIAction;
import ui.actions.UIActions;
import ui.commands.Command;
import utility.Errno;

public class InputParser {

    public static UIAction parseLine(String line, UIActions currentAction) {
        line = line.trim();
        switch (currentAction) {
            case ROOT:
                if (line.equals(Command.EXIT.getUserLine())) return UIActions.EXIT;
                if (line.equals(Command.TEST.getUserLine())) return UIActions.TEST;
                if (line.equals(Command.TRAIN.getUserLine())) return UIActions.TRAIN;
                if (line.equals(Command.TASKS.getUserLine())) return UIActions.TASKS;
                ClientController.errno = Errno.SYNTAX_ERR;
                return UIActions.ERROR;
            case TASKS:
                if (line.equals(Command.REFRESH.getUserLine())) return UIActions.TASKS_REFRESH;
                if (line.equals(Command.CREATE.getUserLine())) return UIActions.TASKS_CREATE;
                if (line.startsWith(Command.DELETE.getUserLine())) {
                    String[] split = line.split(" ");
                    if (split.length != 2) return UIActions.ERROR;
                    if (UIController.getClientController().setTask(split[1]))
                        return UIActions.TASKS_DELETE;
                    else
                        return UIActions.ERROR;
                }
                if (line.startsWith(Command.CHANGE.getUserLine())) {
                    String[] split = line.split(" ");
                    if (split.length != 2) return UIActions.ERROR;
                    if (UIController.getClientController().setTask(split[1]))
                        return UIActions.TASKS_CHANGE;
                    else
                        return UIActions.ERROR;
                }
                if (line.equals(Command.BACK.getUserLine())) return UIActions.BACK;
                ClientController.errno = Errno.SYNTAX_ERR;
                return UIActions.ERROR;
            case TASKS_CHANGE:
                if (line.equals(Command.DATASET.getUserLine())) return UIActions.TASKS_CHANGE_DATASET;
                if (line.equals(Command.NEURALNET.getUserLine())) return UIActions.TASKS_CHANGE_NEURALNET;
                if (line.equals(Command.BACK.getUserLine())) return UIActions.BACK;
                if (line.equals(Command.MAIN.getUserLine())) return UIActions.MAIN;
                ClientController.errno = Errno.SYNTAX_ERR;
                return UIActions.ERROR;
            case TEST:
                if (line.startsWith(Command.ID.getUserLine())) {
                    String[] split = line.split(" ");
                    if (split.length != 2) return UIActions.ERROR;
                    if (UIController.getClientController().setTask(split[1]))
                        return UIActions.TEST_ID;
                    else
                        return UIActions.ERROR;
                }
                if (line.equals(Command.BACK.getUserLine())) return UIActions.BACK;
                ClientController.errno = Errno.SYNTAX_ERR;
                return UIActions.ERROR;
            case TEST_ID:
                if (line.equals(Command.BACK.getUserLine())) return UIActions.BACK;
                if (line.equals(Command.MAIN.getUserLine())) return UIActions.MAIN;
            {
                String[] split = line.split(" ");
                if (split.length != 2) return UIActions.ERROR;
                if (!split[0].equals(Command.LOCAL.getUserLine()) && !split[0].equals((Command.REMOTE.getUserLine()))) {
                    ClientController.errno = Errno.SYNTAX_ERR;
                    return UIActions.ERROR;
                }
                if (split[0].equals(Command.LOCAL.getUserLine()) && UIController.getClientController().processLocalTestPath(split[1])) {
                    return UIActions.CLIENT_TEST;
                } else if (line.startsWith(Command.REMOTE.getUserLine()) && UIController.getClientController().processRemoteTestPath(split[1])) {
                    return UIActions.CLIENT_TEST;
                } else {
                    return UIActions.ERROR;
                }
            }
            case TRAIN:
                if (line.startsWith(Command.ID.getUserLine())) {
                    String[] split = line.split(" ");
                    if (split.length != 2) return UIActions.ERROR;
                    if (UIController.getClientController().setTask(split[1]))
                        return UIActions.TRAIN_ID;
                    else
                        return UIActions.ERROR;
                }
                if (line.equals(Command.BACK.getUserLine())) return UIActions.BACK;
                ClientController.errno = Errno.SYNTAX_ERR;
                return UIActions.ERROR;
            case TRAIN_ID:
                if (line.equals(Command.YES.getUserLine())) return UIActions.CLIENT_TRAIN;
                if (line.equals(Command.NO.getUserLine())) return UIActions.BACK;
                if (line.equals(Command.BACK.getUserLine())) return UIActions.BACK;
                if (line.equals(Command.MAIN.getUserLine())) return UIActions.MAIN;
                ClientController.errno = Errno.SYNTAX_ERR;
                return UIActions.ERROR;
            case ERROR:
                break;
            case EXIT:
                break;
        }

        return null;
    }

    public static boolean parseSimpleBoolean(String line, String command) {
        return line.trim().equals(command);
    }

}
