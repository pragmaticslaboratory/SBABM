package endorsement;

import agent.Market;

public class Endorsement {

    private final int period;
    private final Market market;
    private final String attributeName;
    private final double value;

    public Endorsement(int period, Market market, String attributeName, double value) {
        this.period = period;
        this.market = market;
        this.attributeName = attributeName;
        this.value = value;
    }

    public int getPeriod() {
        return period;
    }

    public Market getMarket() {
        return market;
    }

    public double getValue() {
        return value;
    }

    public String getAttributeName() {
        return attributeName;
    }
}
