package ru.liga.bot.command;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class UnknownCommandTest {
    Command command = new UnknownCommand();

    @Test
    public void whenUnknownCommandExecutedThenSentCorrectTextMessage() {
        assertEquals("Unknown command. Please type help to get available commands", command.execute());
    }
}