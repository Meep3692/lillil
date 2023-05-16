package ca.awoo.lillil.core.math;

import java.util.Arrays;

import ca.awoo.lillil.LillilRuntimeException;
import ca.awoo.lillil.sexpression.SExpression;
import ca.awoo.lillil.sexpression.SFloat;
import ca.awoo.lillil.sexpression.SFunction;

public class DivFunction extends SFunction {

    @Override
    public SExpression apply(SExpression... args) throws LillilRuntimeException {
        float acc = 0;
        if(args[0].isFloat())
            acc = args[0].asFloat().value;
        else if(args[0].isInteger())
            acc = args[0].asInteger().value;
        else
            throw new LillilRuntimeException(args[0], "Cannot divide non-numeric values");
        for (SExpression sexpr : Arrays.asList(args).subList(1, args.length)) {
            if(sexpr.isFloat()){
                acc /= sexpr.asFloat().value;
            } else if(sexpr.isInteger()){
                acc /= sexpr.asInteger().value;
            } else {
                throw new LillilRuntimeException(sexpr, "Cannot divide non-numeric values");
            }
        }
        return new SFloat(acc);
    }
    
}
