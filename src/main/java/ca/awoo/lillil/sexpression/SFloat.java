package ca.awoo.lillil.sexpression;

public class SFloat extends SExpression{

    public float value;

    public SFloat(float value) {
        this.value = value;
    }

    public String toString() {
        return Float.toString(value);
    }

    public boolean equals(Object o) {
        if (o instanceof SFloat) {
            return value == ((SFloat)o).value;
        }
        return false;
    }
}
