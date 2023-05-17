package ca.awoo.lillil.sexpression;

public abstract class SExpression{

    public int position;
    public int line;
    public int column;

    public SBoolean asBoolean() {
        return (SBoolean)this;
    }

    public SFloat asFloat() {
        return (SFloat)this;
    }

    public SDouble asDouble(){
        return (SDouble)this;
    }

    public SInteger asInteger() {
        return (SInteger)this;
    }

    public SLong asLong() {
        return (SLong)this;
    }

    public SList asList() {
        return (SList)this;
    }

    public SString asString() {
        return (SString)this;
    }

    public SSymbol asSymbol() {
        return (SSymbol)this;
    }

    public boolean isBoolean() {
        return this instanceof SBoolean;
    }

    public boolean isFloat() {
        return this instanceof SFloat;
    }

    public boolean isDouble(){
        return this instanceof SDouble;
    }

    public boolean isInteger() {
        return this instanceof SInteger;
    }

    public boolean isLong() {
        return this instanceof SLong;
    }

    public boolean isList() {
        return this instanceof SList;
    }

    public boolean isString() {
        return this instanceof SString;
    }

    public boolean isSymbol() {
        return this instanceof SSymbol;
    }

    public boolean isNumber(){
        return false;
    }

    public boolean isAtom() {
        return !isList();
    }

    public boolean booleanValue() {
        return asBoolean().value;
    }

    public float floatValue() {
        return asFloat().value;
    }

    public int integerValue() {
        return asInteger().value;
    }

    public String stringValue() {
        return asString().value;
    }

    public String symbolValue() {
        return asSymbol().value;
    }
}
