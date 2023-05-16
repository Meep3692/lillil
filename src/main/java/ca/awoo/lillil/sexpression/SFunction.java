package ca.awoo.lillil.sexpression;

import ca.awoo.lillil.LillilRuntimeException;

public abstract class SFunction extends SExpression {
    public abstract SExpression apply(SExpression ... args) throws LillilRuntimeException;

    @Override
    public boolean isAtom(){
        return true;
    }

    @Override
    public boolean isList(){
        return false;
    }

    public int hashCode() {
        return this.getClass().hashCode();
    }
}
