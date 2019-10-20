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
                return UIActions.ELSE;
            case TEST:
                break;
            case TRAIN:
                if (line.startsWith(Command.NEW.getUserLine())) return UIActions.TRAIN_NEW;
                if (line.startsWith(Command.ID.getUserLine())) {
                    if (ClientController.setTask(Integer.parseInt(line.split(" ")[1])))
                        return UIActions.TRAIN_ID;
                    else
                        return UIActions.ERROR;
                }
                if (line.startsWith(Command.BACK.getUserLine())) return UIActions.BACK;
                return UIActions.ELSE;
            case TRAIN_NEW:
            case TRAIN_ID:
                if (line.startsWith(Command.YES.getUserLine())) return UIActions.CLIENT_TRAIN;
                if (line.startsWith(Command.NO.getUserLine())) return UIActions.MAIN;
                if (line.startsWith(Command.BACK.getUserLine())) return UIActions.BACK;
                if (line.startsWith(Command.MAIN.getUserLine())) return UIActions.MAIN;
                return UIActions.ELSE;
            case ELSE:
                break;
            case EXIT:
                break;
        }

        return null;
    }

}
