package ca.awoo.lillil.sexpression;

public abstract class SPrimitive<T> extends SExpression {
    public T value;

    public SPrimitive() {
    }

    public SPrimitive(T value) {
        this.value = value;
    }

    public String toString() {
        return value.toString();
    }

    public boolean equals(Object o) {
        if (o instanceof SPrimitive) {
            return value.equals(((SPrimitive)o).value);
        }
        return false;
    }

    public int hashCode() {
        return value.hashCode();
    }

}
