package ca.awoo.lillil.sexpression;

public class SSymbol extends SExpression {
    public String value;
    public SSymbol(String value) {
        this.value = value;
    }

    public String toString() {
        return value;
    }

    public boolean equals(Object o) {
        if (o instanceof SSymbol) {
            return value.equals(((SSymbol)o).value);
        }
        return false;
    }
}
