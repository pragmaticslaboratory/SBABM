package gui;

import agent.Market;
import reporter.Reporter;
import reporter.SalesPerMarketData;
import simulation.Simulation;

import java.util.ArrayList;
import java.util.List;

public class DataSaleChart {
    private final String name;
    private final List<Integer> xData;
    private final List<Integer> yData;

    public DataSaleChart(String name, List<Integer> xData, List<Integer> yData) {
        this.name = name;
        this.xData = xData;
        this.yData = yData;
    }

    public List<Integer> getXData() {
        return xData;
    }

    public List<Integer> getYData() {
        return yData;
    }

    public String getName() {
        return name;
    }

    public static DataSaleChart[] createDataSaleChart(List<Market> markets) {
        DataSaleChart[] dataSaleChart = new DataSaleChart[markets.size()];
        String[] name = new String[markets.size()];
        ArrayList<Integer>[] xData = new ArrayList[markets.size()];
        ArrayList<Integer>[] yData = new ArrayList[markets.size()];

        List<? extends SalesPerMarketData> data = Reporter.getSalesPerMarketData();

        for (int i = 0; i < markets.size(); ++i) {
            name[i] = markets.get(i).getName();
            xData[i] = new ArrayList<>();
            yData[i] = new ArrayList<>();
        }

        data.iterator().forEachRemaining(sales -> {
            if (sales.simulationId == Simulation.ID) {
                for (int i = 0; i < markets.size(); ++i) {
                    xData[i].add(sales.period);
                    yData[i].add(sales.sales[i]);
                }
            }
        });

        for (int i = 0; i < markets.size(); ++i) {
            dataSaleChart[i] = new DataSaleChart(name[i], xData[i], yData[i]);
        }
        return dataSaleChart;
    }
}
