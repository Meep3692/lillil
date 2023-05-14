package ca.awoo.lillil.std.math;

import ca.awoo.lillil.LillilRuntimeException;
import ca.awoo.lillil.sexpression.SExpression;
import ca.awoo.lillil.sexpression.SFloat;
import ca.awoo.lillil.sexpression.SFunction;
import ca.awoo.lillil.sexpression.SInteger;

public class AddFunction extends SFunction {

    @Override
    public SExpression apply(SExpression... args) throws LillilRuntimeException {
        int acc = 0;
        for (SExpression sexpr : args) {
            if(sexpr.isInteger()){
                acc += sexpr.asInteger().value;
            } else if(sexpr.isFloat()){
                return addFloat(args);
            } else {
                throw new LillilRuntimeException(sexpr, "Cannot add non-numeric values");
            }
        }
        return new SInteger(acc);
    }
    
    private SExpression addFloat(SExpression... args) throws LillilRuntimeException{
        float acc = 0;
        for (SExpression sexpr : args) {
            if(sexpr.isFloat()){
                acc += sexpr.asFloat().value;
            } else if(sexpr.isInteger()){
                acc += sexpr.asInteger().value;
            } else {
                throw new LillilRuntimeException(sexpr, "Cannot add non-numeric values");
            }
        }
        return new SFloat(acc);
    }

}
