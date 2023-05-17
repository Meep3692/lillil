package ca.awoo.lillil.sexpression;

public class SLong extends SPrimitive<Long> {
    public SLong(long value) {
        this.value = value;
    }

    @Override
    public boolean isNumber() {
        return true;
    }
}
