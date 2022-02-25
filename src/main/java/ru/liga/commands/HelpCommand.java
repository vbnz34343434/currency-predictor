package ru.liga.commands;

public class HelpCommand implements Command {

    @Override
    public void execute() {
        System.out.println("Im help command. Im here just for example");
    }
}
