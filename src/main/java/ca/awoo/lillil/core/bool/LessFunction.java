package ca.awoo.lillil.core.bool;

import ca.awoo.lillil.LillilRuntimeException;
import ca.awoo.lillil.sexpression.SBoolean;
import ca.awoo.lillil.sexpression.SExpression;
import ca.awoo.lillil.sexpression.SFunction;

public class LessFunction extends SFunction {

    @Override
    public SExpression apply(SExpression... args) throws LillilRuntimeException {
        for(int i = 0; i < args.length - 1; i++){
            if(args[i] instanceof Comparable && args[i+1] instanceof Comparable){
                Comparable a = (Comparable) args[i];
                Comparable b = (Comparable) args[i+1];
                if(a.compareTo(b) >= 0){
                    return new SBoolean(false);
                }
            }
        }
        return new SBoolean(true);
    }
    
}
