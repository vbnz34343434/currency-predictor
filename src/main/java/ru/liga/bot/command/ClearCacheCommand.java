package ru.liga.bot.command;

import lombok.Getter;
import ru.liga.repository.RatesRepositoryCacheProxy;

public class ClearCacheCommand implements Command {
    private final RatesRepositoryCacheProxy repository;

    @Getter
    private final String CLEAR_CACHE_COMMAND_MESSAGE = "dataset cache successfully cleared";

    public ClearCacheCommand() {
        repository = RatesRepositoryCacheProxy.getInstance();
    }

    @Override
    public String execute() {
        repository.clearCache();
        return CLEAR_CACHE_COMMAND_MESSAGE;
    }
}
