package ca.awoo.lillil.core.debug;

import ca.awoo.lillil.LillilRuntimeException;
import ca.awoo.lillil.sexpression.SBoolean;
import ca.awoo.lillil.sexpression.SExpression;
import ca.awoo.lillil.sexpression.SFunction;

public class AssertFalseFunction extends SFunction {

    @Override
    public SExpression apply(SExpression... args) throws LillilRuntimeException {
        if(args.length < 2){
            throw new LillilRuntimeException(this, "assert-false requires at least 2 arguments");
        }
        if(!args[0].isString()){
            throw new LillilRuntimeException(args[0], "assert-false requires a string argument");
        }
        for(int i = 1; i < args.length; i++){
            if(!args[i].isBoolean()){
                throw new LillilRuntimeException(args[i], "assert-false requires a boolean argument, got " + args[i].getClass().getSimpleName() + " instead");
            }
            if(args[i].asBoolean().value){
                throw new LillilRuntimeException(args[i], args[0].asString().value);
            }
        }
        return new SBoolean(true);
    }
    
}
