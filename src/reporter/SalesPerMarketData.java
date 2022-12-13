package reporter;

import inputManager.Markets;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class SalesPerMarketData {
    public final int simulationId;
    public final int period;
    public final int[] sales;

    public SalesPerMarketData(int simulationId, int period, int[] sales) {
        this.simulationId = simulationId;
        this.period = period;
        this.sales = sales.clone();
    }

    public static List<String> getHeader() {
        return new ArrayList<String>() {{
            add("SimulationId");
            add("Period");
            addAll(Arrays.asList(Markets.marketNames().split(" ")));
        }};
    }
}
