package gui;

import java.util.ArrayList;

public class DataChart {
    private final String seriesName;
    private final ArrayList<Integer> xData;
    private final ArrayList<Integer> yData;

    public DataChart(String seriesName) {
        this.seriesName = seriesName;
        xData = new ArrayList<>();
        yData = new ArrayList<>();
    }

    public void addData(int x, int y) {
        xData.add(x);
        yData.add(y);
    }

    public ArrayList<Integer> getXData() {
        return xData;
    }

    public ArrayList<Integer> getYData() {
        return yData;
    }

    public String getName() {
        return seriesName;
    }
}
