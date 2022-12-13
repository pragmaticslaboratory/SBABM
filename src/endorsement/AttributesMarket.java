package endorsement;

import inputManager.Configuration;

import java.util.ArrayList;

public class AttributesMarket extends Attributes {

    public AttributesMarket(ArrayList<String> names, ArrayList<Double[]> values) {
        super(names, values);
    }

    public AttributesMarket copy() {
        return new AttributesMarket(new ArrayList<>(this.names), new ArrayList<>(this.values));
    }

    public AttributesMarket replace(String name, Double[] newValues) {
        ArrayList<String> resultNames = new ArrayList<>();
        ArrayList<Double[]> resultValues = new ArrayList<>();

        forEach((attrName, attrValues) -> {
            resultNames.add(attrName);
            if (attrName.equals(name)) {
                resultValues.add(newValues);
            } else {
                resultValues.add(attrValues);
            }
        });

        return new AttributesMarket(resultNames, resultValues);
    }

    public AttributesMarket replaceAll(String[] names, Double[][] newValues) {
        AttributesMarket result = copy();
        for (int i = 0; i < names.length; ++i) {
            result = result.replace(names[i], newValues[i]);
        }
        return result;
    }

    public AttributesMarket replaceAll(String[] names, AttributesMarket attm) {
        Double[][] values = new Double[names.length][Configuration.LEVELS];

        for (int i = 0; i < names.length; ++i) {
            values[i] = attm.getValues(names[i]);
        }
        return replaceAll(names, values);
    }
}
