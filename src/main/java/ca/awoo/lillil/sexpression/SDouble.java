package ca.awoo.lillil.sexpression;

public class SDouble extends SPrimitive<Double> {
    public SDouble(double value) {
        this.value = value;
    }

    @Override
    public boolean isNumber() {
        return true;
    }
}
