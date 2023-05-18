package ca.awoo.lillil.sexpression;

public class SInteger extends SPrimitive<Integer> implements Comparable<SPrimitive> {
    public SInteger(int value) {
        this.value = value;
    }

    @Override
    public boolean isNumber() {
        return true;
    }

    @Override
    public int compareTo(SPrimitive o) {
        if(o.isInteger()){
            return Integer.compare(value, o.asInteger().value);
        }else if (o.isLong()){
            return Long.compare(value, o.asLong().value);
        }else if (o.isFloat()){
            return Float.compare(value, o.asFloat().value);
        }else if(o.isDouble()){
            return Double.compare(value, o.asDouble().value);
        } else {
            throw new ClassCastException("Cannot compare SInteger to " + o.getClass().getName());
        }
    }
}
