package ca.awoo.lillil.sexpression;

public class SBoolean extends SExpression {
    public boolean value;
    public SBoolean(boolean value) {
        this.value = value;
    }

    public String toString() {
        return value ? "#t" : "#f";
    }

    public boolean equals(Object o) {
        if (o instanceof SBoolean) {
            return value == ((SBoolean)o).value;
        }
        return false;
    }

    public int hashCode() {
        return value ? 1 : 0;
    }
}
