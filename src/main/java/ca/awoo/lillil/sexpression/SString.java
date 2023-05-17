package ca.awoo.lillil.sexpression;

public class SString extends SPrimitive<String>{
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

}
