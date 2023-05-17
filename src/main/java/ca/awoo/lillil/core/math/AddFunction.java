package ca.awoo.lillil.core.math;

import ca.awoo.lillil.LillilRuntimeException;
import ca.awoo.lillil.sexpression.SDouble;
import ca.awoo.lillil.sexpression.SExpression;
import ca.awoo.lillil.sexpression.SFloat;
import ca.awoo.lillil.sexpression.SFunction;
import ca.awoo.lillil.sexpression.SInteger;
import ca.awoo.lillil.sexpression.SLong;

public class AddFunction extends SFunction {

    @Override
    public SExpression apply(SExpression... args) throws LillilRuntimeException {
        int acc = 0;
        for (int i = 0; i < args.length; i++) {
            SExpression sexpr = args[i];
            if(sexpr.isInteger()){
                acc += sexpr.asInteger().value;
            }  else if (sexpr.isLong()){
                return addLong(i, acc, args);
            } else if(sexpr.isFloat()){
                return addFloat(i, acc, args);
            } else if(sexpr.isDouble()){
                return addDouble(i, acc, args);
            } else {
                throw new LillilRuntimeException(sexpr, "Cannot add non-numeric values");
            }
        }
        return new SInteger(acc);
    }

    private SExpression addLong(int i, long acc, SExpression... args) throws LillilRuntimeException{
        for (; i < args.length; i++) {
            SExpression sexpr = args[i];
            if(sexpr.isInteger()){
                acc += sexpr.asInteger().value;
            }  else if (sexpr.isLong()){
                acc += sexpr.asLong().value;
            } else if(sexpr.isFloat()){
                return addDouble(i, acc, args);
            } else if(sexpr.isDouble()){
                return addDouble(i, acc, args);
            } else {
                throw new LillilRuntimeException(sexpr, "Cannot add non-numeric values");
            }
        }
        return new SLong(acc);
    }
    
    private SExpression addFloat(int i, float acc, SExpression... args) throws LillilRuntimeException{
        for (; i < args.length; i++) {
            SExpression sexpr = args[i];
            if(sexpr.isInteger()){
                acc += sexpr.asInteger().value;
            }  else if (sexpr.isLong()){
                return addDouble(i, acc, args);
            } else if(sexpr.isFloat()){
                acc += sexpr.asFloat().value;
            } else if(sexpr.isDouble()){
                return addDouble(i, acc, args);
            } else {
                throw new LillilRuntimeException(sexpr, "Cannot add non-numeric values");
            }
        }
        return new SFloat(acc);
    }

    private SExpression addDouble(int i, double acc, SExpression... args) throws LillilRuntimeException{
        for (; i < args.length; i++) {
            SExpression sexpr = args[i];
            if(sexpr.isInteger()){
                acc += sexpr.asInteger().value;
            }  else if (sexpr.isLong()){
                acc += sexpr.asLong().value;
            } else if(sexpr.isFloat()){
                acc += sexpr.asFloat().value;
            } else if(sexpr.isDouble()){
                acc += sexpr.asDouble().value;
            } else {
                throw new LillilRuntimeException(sexpr, "Cannot add non-numeric values");
            }
        }
        return new SDouble(acc);
    }

}
