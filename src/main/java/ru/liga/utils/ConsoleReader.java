package ru.liga.utils;

import ru.liga.commands.Command;
import ru.liga.commands.HelpCommand;
import ru.liga.commands.RateCommand;

import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ConsoleReader {
    private String input;

    public void read() {
        Scanner scanner = new Scanner(System.in);
        input = scanner.nextLine();
        scanner.close();
    }


    public void parseAndExecute() {
        final String RATE_COMMAND_PATTERN = "^(rate )([A-Z]+) (tomorrow|week)$";
        final String HELP_COMMAND_PATTERN = "^help$";

        if (input.matches(RATE_COMMAND_PATTERN)) {
            final Pattern pattern = Pattern.compile(RATE_COMMAND_PATTERN, Pattern.CANON_EQ);
            final Matcher matcher = pattern.matcher(input);

            String currency = "";
            String predictType = "";

            while (matcher.find()) {
                currency = matcher.group(2);
                predictType = matcher.group(3);
            }

            Command command = new RateCommand(currency, predictType);
            command.execute();
        } else if (input.matches(HELP_COMMAND_PATTERN)) {
            Command command = new HelpCommand();
            command.execute();
        } else {
            throw new RuntimeException("Unknown command");
        }
    }
}
