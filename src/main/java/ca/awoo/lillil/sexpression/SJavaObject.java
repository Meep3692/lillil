package ca.awoo.lillil.sexpression;

public class SJavaObject<T> extends SExpression {
    public T value;
    public SJavaObject(T value){
        this.value = value;
    }
}
