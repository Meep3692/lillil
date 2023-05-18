package ca.awoo.lillil.sexpression;

public class SFloat extends SPrimitive<Float> implements Comparable<SPrimitive>{
    public SFloat(float value) {
        this.value = value;
    }

    @Override
    public boolean isNumber() {
        return true;
    }

    @Override
    public int compareTo(SPrimitive o) {
        if(o.isInteger()){
            return Float.compare(value, o.asInteger().value);
        }else if (o.isLong()){
            return Float.compare(value, o.asLong().value);
        }else if (o.isFloat()){
            return Float.compare(value, o.asFloat().value);
        }else if(o.isDouble()){
            return Double.compare(value, o.asDouble().value);
        } else {
            throw new ClassCastException("Cannot compare SFloat to " + o.getClass().getName());
        }
    }
}
