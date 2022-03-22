package ru.liga.bot.command;

public class UnknownCommand implements Command {

    @Override
    public String execute() {
        return "Unknown command. Please type help to get available commands";
    }
}
