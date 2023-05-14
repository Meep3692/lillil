package ca.awoo.lillil.sexpression;

public class SInteger extends SExpression {
    public int value;
    public SInteger(int value) {
        this.value = value;
    }

    public String toString() {
        return Integer.toString(value);
    }

    public boolean equals(Object o) {
        if (o instanceof SInteger) {
            return value == ((SInteger)o).value;
        }
        return false;
    }
}
