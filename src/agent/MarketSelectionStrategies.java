package agent;

import utils.Error;

import java.util.Map;

public class MarketSelectionStrategies {

    public static int BY_MAX(Map<Integer, Double> evaluations) {
        int selected = -1;
        double max = Double.MAX_VALUE * -1;

        for (Map.Entry<Integer, Double> entry : evaluations.entrySet()) {
            if (max < entry.getValue()) {
                max = entry.getValue();
                selected = entry.getKey();
            }
        }

        Error.setAssert(selected != -1, "MarketSelectionStrategies.BY_MAX: no market selected info{size:" + evaluations.size() + ",max:" + max + "}");
        return selected;
    }

    public static int BY_PROBABILITY(Map<Integer, Double> evaluations) {
        int selected = -1;
        double random = Math.random();
        double sum = 0;
        double acc = 0;

        for (Map.Entry<Integer, Double> entry : evaluations.entrySet()) {
            sum += entry.getValue();
        }

        for (Map.Entry<Integer, Double> entry : evaluations.entrySet()) {
            acc += entry.getValue() / sum;

            if (acc >= random) {
                selected = entry.getKey();
                break;
            }
        }

        Error.setAssert(selected != -1, "MarketSelectionStrategies.BY_PROBABILITY: no market selected, info{size:" + evaluations.size() + ",acc:" + acc + ",random:" + random + "}");
        return selected;
    }
}
