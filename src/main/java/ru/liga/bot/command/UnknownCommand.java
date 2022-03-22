package ru.liga.bot.command;

public class UnknownCommand implements Command {

    @Override
    public CommandResult execute() {
        return new CommandResult("Unknown command. Please type help to get available commands", null);
    }
}
