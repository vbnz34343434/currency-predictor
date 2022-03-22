package ru.liga.service.common.algoritm;

import ru.liga.repository.RatesRepository;

public abstract class AbstractAlgorithm implements Predictable {
    RatesRepository repository;

    public AbstractAlgorithm(RatesRepository repository) {
        this.repository = repository;
    }
}