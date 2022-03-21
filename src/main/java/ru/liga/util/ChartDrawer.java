package ru.liga.util;

import lombok.extern.slf4j.Slf4j;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartUtils;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.axis.DateAxis;
import org.jfree.chart.plot.XYPlot;
import org.jfree.chart.renderer.xy.XYLineAndShapeRenderer;
import org.jfree.data.time.Day;
import org.jfree.data.time.TimeSeries;
import org.jfree.data.time.TimeSeriesCollection;
import ru.liga.model.Rate;
import ru.liga.model.currency.Currency;

import java.awt.*;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.text.DateFormat;
import java.time.LocalDate;
import java.util.List;
import java.util.Map;

import static ru.liga.util.Constant.CHART_FILE_HEIGHT;
import static ru.liga.util.Constant.CHART_FILE_WIDTH;

@Slf4j
public class ChartDrawer {

    // ToDo: переименовать и разнести логику на подметоды
    public OutputStream getChart(Map<Currency, List<Rate>> currencyRateMap) {

        TimeSeriesCollection dataset = getDataset(currencyRateMap);

        JFreeChart chart = ChartFactory.createTimeSeriesChart("Rates prediction", "date", "rate", dataset, true, false, false);
        var renderer = new XYLineAndShapeRenderer();

        XYPlot plot = (XYPlot) chart.getPlot();
        plot.setRenderer(renderer);
        plot.setBackgroundPaint(Color.WHITE);
        plot.setRangeGridlinesVisible(true);
        plot.setRangeGridlinePaint(Color.BLACK);
        plot.setDomainGridlinesVisible(true);
        plot.setDomainGridlinePaint(Color.BLACK);
        DateAxis domain = (DateAxis) plot.getDomainAxis();
        domain.setDateFormatOverride(DateFormat.getDateInstance(DateFormat.DATE_FIELD));

        OutputStream stream = new ByteArrayOutputStream();
        try {
            ChartUtils.writeChartAsPNG(stream, chart, CHART_FILE_WIDTH, CHART_FILE_HEIGHT);
        } catch (IOException e) {
            e.printStackTrace();
        }

        log.debug("successfully created chart stream");
        return stream;
    }

    private TimeSeriesCollection getDataset(Map<Currency, List<Rate>> currencyRateMap) {
        TimeSeriesCollection dataset = new TimeSeriesCollection();
        for (Map.Entry<Currency, List<Rate>> entry : currencyRateMap.entrySet()) {
            Currency currency = entry.getKey();
            List<Rate> rates = entry.getValue();
            TimeSeries series = new TimeSeries(currency.getCode());

            for (Rate rate : rates) {
                LocalDate date = rate.getDate();
                series.add(new Day(date.getDayOfMonth(), date.getMonthValue(), date.getYear()), rate.getValue());
            }

            dataset.addSeries(series);
        }

        log.debug("dataset for chart created: {}", dataset);
        return dataset;
    }
}