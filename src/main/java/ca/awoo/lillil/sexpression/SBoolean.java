package ca.awoo.lillil.sexpression;

public class SBoolean extends SPrimitive<Boolean> {

    public SBoolean(boolean value) {
        this.value = value;
    }

    public String toString() {
        return value ? "#t" : "#f";
    }
}
