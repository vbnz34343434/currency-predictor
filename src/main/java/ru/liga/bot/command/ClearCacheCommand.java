package ru.liga.bot.command;

import ru.liga.repository.RatesRepositoryCacheProxy;

public class ClearCacheCommand implements Command {
    private final RatesRepositoryCacheProxy repository;

    private final String CLEAR_CACHE_COMMAND_MESSAGE = "dataset cache successfully cleared";

    public ClearCacheCommand() {
        repository = RatesRepositoryCacheProxy.getInstance();
    }

    @Override
    public CommandResult execute() {
        repository.clearCache();
        return new CommandResult(CLEAR_CACHE_COMMAND_MESSAGE);
    }
}
