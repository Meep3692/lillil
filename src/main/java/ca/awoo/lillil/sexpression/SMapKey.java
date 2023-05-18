package ca.awoo.lillil.sexpression;

public class SMapKey extends SExpression{
    public String value;
    public SMapKey(String value){
        this.value = value;
    }
    public int hashCode(){
        return value.hashCode();
    }

    public boolean equals(Object o){
        if(o instanceof SMapKey){
            return value.equals(((SMapKey)o).value);
        }
        return false;
    }

    public String toString(){
        return ":" + value;
    }
}
