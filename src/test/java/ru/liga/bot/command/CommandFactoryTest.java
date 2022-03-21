package ru.liga.bot.command;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class CommandFactoryTest {

    CommandFactory commandFactory = new CommandFactory();

    @Test
    public void whenReceivedRateMessageThenReturnedRateCommand() {
        assertEquals(RateCommand.class, commandFactory.retrieveCommand("rate USD -date tomorrow -alg actual").getClass());
    }

    @Test
    public void whenReceivedHelpMessageThenReturnedHelpCommand() {
        assertEquals(HelpCommand.class, commandFactory.retrieveCommand("help").getClass());
    }

    @Test
    public void whenReceivedClearCacheMessageThenReturnedClearCacheCommand() {
        assertEquals(ClearCacheCommand.class, commandFactory.retrieveCommand("clearcache").getClass());
    }

    @Test
    public void whenReceivedUnknownMessageThenReturnedUnknownCommand() {
        assertEquals(UnknownCommand.class, commandFactory.retrieveCommand("lkmagslk").getClass());
    }

}