package ru.liga.bot.command;

import org.junit.Test;
import ru.liga.model.currency.USD;
import ru.liga.repository.RatesRepositoryCacheProxy;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

public class ClearCacheCommandTest {

    Command command = new ClearCacheCommand();

    @Test
    public void whenClearCacheExecutedThenFilesCacheCleared() {
        RatesRepositoryCacheProxy repository = RatesRepositoryCacheProxy.getInstance();

        repository.getRates(new USD());
        assertNotNull(repository.getCachedRates());
        command.execute();
        assertTrue(repository.getCachedRates().isEmpty());

    }
}