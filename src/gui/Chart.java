package gui;

import agent.Buyer;
import agent.Market;
import inputManager.Configuration;
import utils.Console;
import utils.Error;
import org.knowm.xchart.BitmapEncoder;
import org.knowm.xchart.SwingWrapper;
import org.knowm.xchart.XYChart;
import org.knowm.xchart.XYChartBuilder;
import org.knowm.xchart.style.Styler;
import simulation.Simulation;

import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Chart {
    private static XYChart chart;

    public static void displaySales(List<Market> markets) {
        Console.info("Chart: Displaying Sales");
        createXChartDriverSales();

        DataSaleChart[] sales = DataSaleChart.createDataSaleChart(markets);
        for (DataSaleChart sale : sales) {
            registerSeries2(sale);
        }

        if (Configuration.REPETITIONS == 0) drawChart();
        saveChart();
    }

    public static void displaySelection(List<Buyer> buyers, List<Market> markets) {
        Console.info("Chart: Displaying Selection");
        createXChartDriverSelection(markets);
        buyers.iterator().forEachRemaining(buyer -> registerSeries(buyer.getDataSeries()));
        if (Configuration.REPETITIONS == 0) drawChart();
        saveChart();
    }

    private static void createXChartDriverSales() {
        chart = new XYChartBuilder().width(800).height(600).title("simulation")
                .xAxisTitle("Period").yAxisTitle("Sales").build();
        chart.getStyler().setYAxisDecimalPattern("#0").setXAxisDecimalPattern("#0").setLegendPosition(Styler.LegendPosition.InsideNE);
    }

    private static void createXChartDriverSelection(List<Market> markets) {
        chart = new XYChartBuilder().width(800).height(600).title("simulation")
                .xAxisTitle("Period").yAxisTitle("Market").build();

        chart.getStyler().setYAxisDecimalPattern("#0").setXAxisDecimalPattern("#0").setYAxisMax(markets.size() * 1.0).setLegendPosition(Styler.LegendPosition.InsideNE);
        Map<Double, Object> customYAxisTickLabelsMap = new HashMap<>();
        for (Market market : markets) {
            customYAxisTickLabelsMap.put(market.getID() * 1.0, market.getName());
        }
        chart.setYAxisLabelOverrideMap(customYAxisTickLabelsMap);
    }

    private static void registerSeries2(DataSaleChart dataChart) {
        chart.addSeries(dataChart.getName(), dataChart.getXData(), dataChart.getYData());
    }

    private static void registerSeries(DataChart dataChart) {
        chart.addSeries(dataChart.getName(), dataChart.getXData(), dataChart.getYData());
    }

    private static void drawChart() {
        (new SwingWrapper<>(chart)).displayChart();
    }

    private static void saveChart() {
        String fileName = Configuration.OUTPUT_DIRECTORY + "/Simulation_" + Simulation.ID + "_";
        DateFormat df = new SimpleDateFormat("dd-MM-yy(HH-mm-ss)");
        fileName += df.format(new Date()) + ".png";

        try {
            Console.info("Chart: Saving chart");
            BitmapEncoder.saveBitmap(chart, fileName, BitmapEncoder.BitmapFormat.PNG);
        } catch (IOException ex) {
            Error.trigger("Image cannot be saved: " + fileName, ex);
        }
    }
}
