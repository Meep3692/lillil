package ca.awoo.lillil.sexpression;

public class SDouble extends SPrimitive<Double> implements Comparable<SPrimitive> {
    public SDouble(double value) {
        this.value = value;
    }

    @Override
    public boolean isNumber() {
        return true;
    }

    @Override
    public int compareTo(SPrimitive o) {
        if(o.isInteger()){
            return Double.compare(value, o.asInteger().value);
        }else if (o.isLong()){
            return Double.compare(value, o.asLong().value);
        }else if (o.isFloat()){
            return Double.compare(value, o.asFloat().value);
        }else if(o.isDouble()){
            return Double.compare(value, o.asDouble().value);
        } else {
            throw new ClassCastException("Cannot compare SDouble to " + o.getClass().getName());
        }
    }
}
