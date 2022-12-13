package reporter;

import java.util.ArrayList;
import java.util.List;

public class EndorsementData {
    public final int simulationId;
    public final int period;
    public final int buyerId;
    public final String marketName;
    public final String attribute;
    public final double value;

    public EndorsementData(int simulationId, int period, int buyerId, String marketName, String attribute, double value) {
        this.simulationId = simulationId;
        this.period = period;
        this.buyerId = buyerId;
        this.marketName = marketName;
        this.attribute = attribute;
        this.value = value;
    }

    public static List<String> getHeader() {
        return new ArrayList<String>() {{
            add("SimulationId");
            add("Period");
            add("BuyerId");
            add("Market");
            add("Attribute");
            add("Value");
        }};
    }
}