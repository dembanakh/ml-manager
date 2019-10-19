package ui;

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
                break;
            case ELSE:
                break;
            case EXIT:
                break;
        }

        return null;
    }

}
