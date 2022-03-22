package ru.liga.repository;

import org.junit.Test;
import ru.liga.model.currency.EUR;
import ru.liga.model.currency.USD;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class RatesRepositoryCacheProxyTest {
    RatesRepositoryCacheProxy repository = RatesRepositoryCacheProxy.getInstance();

    @Test
    public void clearCacheTest() {
        assertTrue(repository.getCachedRates().isEmpty());
        repository.getRates(new USD());
        repository.getRates(new EUR());
        assertEquals(2, repository.getCachedRates().size());
        repository.clearCache();
        assertTrue(repository.getCachedRates().isEmpty());
    }
}