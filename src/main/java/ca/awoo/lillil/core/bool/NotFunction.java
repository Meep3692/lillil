package ca.awoo.lillil.core.bool;

import ca.awoo.lillil.LillilRuntimeException;
import ca.awoo.lillil.sexpression.SBoolean;
import ca.awoo.lillil.sexpression.SExpression;
import ca.awoo.lillil.sexpression.SFunction;

public class NotFunction extends SFunction {

    @Override
    public SExpression apply(SExpression... args) throws LillilRuntimeException {
        if(args.length != 1){
            //TODO: Create arity exception to make this consistent
            throw new LillilRuntimeException(this, "not: arity mismatch; expected 1 argument, got " + args.length);
        }
        if(!args[0].isBoolean()){
            //TODO: Create type exception to make this consistent
            throw new LillilRuntimeException(args[0], "not: expected boolean, got " + args[0].getClass().getSimpleName());
        }
        return new SBoolean(!args[0].asBoolean().value);
    }
    
}
