package scenarios;

import agent.Market;
import agent.MarketFactory;
import endorsement.AttributesMarket;
import inputManager.Configuration;
import utils.Console;
import utils.Error;
import org.apache.commons.lang.StringUtils;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;

public class Scenario {
    private final int id;
    private final int start;
    private final String from;
    private final String to;
    private final String[] atts;


    public Scenario(int id, int start, String from, String to, ArrayList<String> atts) {
        this.id = id;
        this.start = start;
        this.from = from;
        this.to = to;
        this.atts = atts.toArray(new String[0]);
    }

    public void apply(int period) {
        if (period == this.start || period == -1) {
            Console.info("ScenarioManager: Applying Scenario " + Configuration.SCENARIO +"  [" + this + "]");
            copyAttributes(MarketFactory.getMarket(from), MarketFactory.getMarket(to), atts);
        }
    }

    public int getId() {
        return id;
    }

    private static void copyAttributes(@NotNull Market from, @NotNull Market to, String[] names) {
        AttributesMarket attFrom = from.getAttributes();
        AttributesMarket attTo = to.getAttributes();
        checkAttributes(attFrom, names);

        AttributesMarket newAttTo = attTo.replaceAll(names, attFrom);
        checkDifference(attTo, newAttTo, names);
        to.setAttributes(newAttTo);
    }

    private static void checkAttributes(@NotNull AttributesMarket attm, String[] names) {
        if (!attm.contains(names)) {
            Console.error("SCENARIO X: some attributes not found");
            System.exit(1);
        }
    }

    private static void checkDifference(AttributesMarket oldAtt, AttributesMarket newAtt, String @NotNull [] names) {
        for (String name : names) {
            Double[] oldValues = oldAtt.getValues(name);
            Double[] newValues = newAtt.getValues(name);

            for (int j = 0; j < oldValues.length; ++j) {
                if (oldValues[j].equals(newValues[j])) {
                    String oldTextValues = StringUtils.join(oldValues, ",");
                    String newTextValues = StringUtils.join(newValues, ",");
                    Console.warn("No difference for " + name + " => Old values:["+ oldTextValues + "]  &  New values:[" + newTextValues + "] - Value's Index:"+j);
                }
            }
        }
    }

    @Override
    public String toString() {
        StringBuilder text = new StringBuilder();
        for (String att : atts) {
            text.append(att).append(",");
        }

        return "Scenario{" +
                "ID=" + this.id +
                ", start=" + this.start +
                ", from=" + this.from +
                ", to=" + this.to +
                ", attributes= {" + text + "}" +
                '}';
    }
}
