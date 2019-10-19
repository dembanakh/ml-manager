package ui;

import ui.commands.Command;

public class InputParser {

    public static Command parseLine(String line) {
        if (line.startsWith(Command.EXIT.getUserLine())) return Command.EXIT;

        return Command.ELSE;
    }

}
