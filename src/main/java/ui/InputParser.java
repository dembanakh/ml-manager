package ui;

import ui.commands.Command;

public class InputParser {

    public static Command parseLine(String line) {
        if (line.startsWith(Command.EXIT.getUserLine())) return Command.EXIT;
        if (line.startsWith(Command.TEST.getUserLine())) return Command.TEST;
        if (line.startsWith(Command.TRAIN.getUserLine())) return Command.TRAIN;
        return Command.ELSE;
    }

}
