package ru.liga.service.common.algoritm;

import ru.liga.repository.RatesRepositoryCacheProxy;
import ru.liga.util.MoonCalendar;

public class AlgorithmFactory {

    public AbstractAlgorithm getAlgorithm(String algorithmName) {
        return switch (algorithmName) {
            case "actual" -> new ActualAlgorithm(RatesRepositoryCacheProxy.getInstance());
            case "linear" -> new LinearAlgorithm(RatesRepositoryCacheProxy.getInstance());
            case "moon" -> new MoonAlgorithm(RatesRepositoryCacheProxy.getInstance(), MoonCalendar.getInstance());
            default -> throw new RuntimeException("unknown algorithm");
        };
    }
}
