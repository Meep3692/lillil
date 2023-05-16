package ca.awoo.lillil.sexpression;

public class SString extends SExpression{
    public String value;
    public SString(String value) {
        this.value = value;
    }

    public String toString() {
        return "\"" + value.replace( "\"", "\\\"")
                    .replace( "\n", "\\n")
                    .replace( "\t", "\\t")
                    .replace( "\\", "\\\\")
                    .replace( "\r", "\\r") + "\"";
    }

    public boolean equals(Object o) {
        if (o instanceof SString) {
            return value.equals(((SString)o).value);
        }
        return false;
    }

    public int hashCode() {
        return value.hashCode();
    }
}
