package ca.awoo.lillil.core.bool;

import ca.awoo.lillil.LillilRuntimeException;
import ca.awoo.lillil.sexpression.SBoolean;
import ca.awoo.lillil.sexpression.SExpression;
import ca.awoo.lillil.sexpression.SFunction;

public class EqualsFunction extends SFunction {

    @Override
    public SExpression apply(SExpression... args) throws LillilRuntimeException {
        for(int i = 0; i < args.length; i++){
            if(!args[0].equals(args[i])){
                return new SBoolean(false);
            }
        }
        return new SBoolean(true);
    }
    
}
