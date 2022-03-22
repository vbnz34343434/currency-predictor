package ru.liga.bot.command;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.cli.*;

import java.util.Arrays;
import java.util.Locale;

@Slf4j
public class CommandFactory {
    private final Options options;
    private final CommandLineParser commandParser;


    public CommandFactory() {
        this.options = new RateCommandOptions().getOptions();
        this.commandParser = new DefaultParser();
    }


    public Command retrieveCommand(String message) {
        String[] args = message.split(" ");

        return switch (args[0].toLowerCase(Locale.ROOT)) {
            case "rate" -> new RateCommand(parseRateCommandOptions(args));
            case "help" -> new HelpCommand();
            case "clearcache" -> new ClearCacheCommand();
            default -> new UnknownCommand();
        };
    }


    private CommandLine parseRateCommandOptions(String[] args) {
        String[] params = Arrays.copyOfRange(args, 1, args.length);

        CommandLine cmd;
        try {
            cmd = commandParser.parse(options, params);
        } catch (ParseException e) {
            throw new RuntimeException("Error parsing command: " + e.getMessage());
        }
        return cmd;
    }
}