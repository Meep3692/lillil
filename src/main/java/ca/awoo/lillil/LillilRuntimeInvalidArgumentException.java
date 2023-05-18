package ca.awoo.lillil;

import ca.awoo.lillil.sexpression.SExpression;

public class LillilRuntimeInvalidArgumentException extends LillilRuntimeException {
    public LillilRuntimeInvalidArgumentException(SExpression arg, Class expectedType){
        super(arg, "Invalid argument: expected " + expectedType.getName() + ", got " + arg.getClass().getName());
    }
}
