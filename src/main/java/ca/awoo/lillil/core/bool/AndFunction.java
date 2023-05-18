package ca.awoo.lillil.core.bool;

import ca.awoo.lillil.LillilRuntimeException;
import ca.awoo.lillil.sexpression.SBoolean;
import ca.awoo.lillil.sexpression.SExpression;
import ca.awoo.lillil.sexpression.SFunction;

public class AndFunction extends SFunction{

    @Override
    public SExpression apply(SExpression... args) throws LillilRuntimeException {
        for(SExpression arg : args){
            SBoolean b = assertArgType(arg, SBoolean.class);
            if(!b.value){
                return arg;
            }
        }
        return new SBoolean(true);
    }
    
}
