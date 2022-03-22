package ru.liga.model.currency;

import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
public abstract class Currency {
    private final String code;
    private final String rateFileName;

    public Currency(String code, String rateFileName) {
        this.code = code;
        this.rateFileName = rateFileName;
    }
}
