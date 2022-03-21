package ru.liga.util;

import lombok.extern.slf4j.Slf4j;
import org.shredzone.commons.suncalc.MoonPhase;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

@Slf4j
public class MoonCalendar {
    private static MoonCalendar instance;
    private final MoonPhase.Parameters parameters;

    private MoonCalendar() {
        this.parameters = MoonPhase.compute().phase(MoonPhase.Phase.FULL_MOON);
    }

    public static MoonCalendar getInstance() {
        if (instance == null) {
            instance = new MoonCalendar();
        }
        return instance;
    }

    public synchronized List<LocalDate> getLastThreeFullMoons(LocalDate date) {
        List<LocalDate> fullMoonDates = new ArrayList<>();

        MoonPhase moonPhase = parameters.on(date.minusMonths(3)).execute();
        LocalDate nextFullMoon = moonPhase.getTime().toLocalDate();

        while (date.isAfter(nextFullMoon)) {
            fullMoonDates.add(nextFullMoon);

            moonPhase = parameters.on(nextFullMoon.plusDays(Constant.DAY)).execute();
            nextFullMoon = moonPhase.getTime().toLocalDate();
        }

        fullMoonDates = fullMoonDates
                .stream()
                .sorted(Comparator.comparing(LocalDate::toEpochDay).reversed())
                .limit(3)
                .toList();

        log.debug("last full-moon dates: {}", fullMoonDates);

        return fullMoonDates;
    }

}