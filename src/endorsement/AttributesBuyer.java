package endorsement;

import java.util.ArrayList;

public class AttributesBuyer extends Attributes {
    public AttributesBuyer(ArrayList<String> names, ArrayList<Double[]> values) {
        super(names, values);
    }

    public double getValue(int i) {
        return getValues(i)[0];
    }
    public double getValue(String name) {
        return getValues(name)[0];
    }
}
