package ca.awoo.lillil.sexpression;

import ca.awoo.lillil.LillilRuntimeException;

public abstract class SFunction extends SExpression {
    public abstract SExpression apply(SExpression ... args) throws LillilRuntimeException;
}
