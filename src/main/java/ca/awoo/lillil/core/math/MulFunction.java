package ca.awoo.lillil.core.math;

import ca.awoo.lillil.LillilRuntimeException;
import ca.awoo.lillil.sexpression.SExpression;
import ca.awoo.lillil.sexpression.SFloat;
import ca.awoo.lillil.sexpression.SFunction;
import ca.awoo.lillil.sexpression.SInteger;

public class  MulFunction extends SFunction {

    @Override
    public SExpression apply(SExpression... args) throws LillilRuntimeException {
        int acc = 1;
        for (SExpression sexpr : args) {
            if(sexpr.isInteger()){
                acc *= sexpr.asInteger().value;
            } else if(sexpr.isFloat()){
                return mulFloat(args);
            } else {
                throw new LillilRuntimeException(sexpr, "Cannot multiply non-numeric values");
            }
        }
        return new SInteger(acc);
    }
    
    private SExpression mulFloat(SExpression... args) throws LillilRuntimeException{
        float acc = 1;
        for (SExpression sexpr : args) {
            if(sexpr.isFloat()){
                acc *= sexpr.asFloat().value;
            } else if(sexpr.isInteger()){
                acc *= sexpr.asInteger().value;
            } else {
                throw new LillilRuntimeException(sexpr, "Cannot multiply non-numeric values");
            }
        }
        return new SFloat(acc);
    }
}
