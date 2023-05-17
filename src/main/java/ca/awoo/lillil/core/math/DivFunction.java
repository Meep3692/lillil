package ca.awoo.lillil.core.math;

import ca.awoo.lillil.LillilRuntimeException;
import ca.awoo.lillil.sexpression.SDouble;
import ca.awoo.lillil.sexpression.SExpression;
import ca.awoo.lillil.sexpression.SFloat;
import ca.awoo.lillil.sexpression.SFunction;
import ca.awoo.lillil.sexpression.SInteger;
import ca.awoo.lillil.sexpression.SLong;

public class DivFunction extends SFunction {
    @Override
    public SExpression apply(SExpression... args) throws LillilRuntimeException {
        if(args[0].isInteger()){
            return divInt(1, args[0].asInteger().value, args);
        }  else if (args[0].isLong()){
            return divLong(1, args[0].asLong().value, args);
        } else if(args[0].isFloat()){
            return divFloat(1, args[0].asFloat().value, args);
        } else if(args[0].isDouble()){
            return divDouble(1, args[0].asDouble().value, args);
        } else {
            throw new LillilRuntimeException(args[0], "Cannot div non-numeric values");
        }
    }

    private SExpression divInt(int i, int acc, SExpression... args) throws LillilRuntimeException{
        for (; i < args.length; i++) {
            SExpression sexpr = args[i];
            if(sexpr.isInteger()){
                acc /= sexpr.asInteger().value;
            }  else if (sexpr.isLong()){
                return divLong(i, acc, args);
            } else if(sexpr.isFloat()){
                return divFloat(i, acc, args);
            } else if(sexpr.isDouble()){
                return divDouble(i, acc, args);
            } else {
                throw new LillilRuntimeException(sexpr, "Cannot div non-numeric values");
            }
        }
        return new SInteger(acc);
    }
    
    private SExpression divLong(int i, long acc, SExpression... args) throws LillilRuntimeException{
        for (; i < args.length; i++) {
            SExpression sexpr = args[i];
            if(sexpr.isInteger()){
                acc /= sexpr.asInteger().value;
            }  else if (sexpr.isLong()){
                acc /= sexpr.asLong().value;
            } else if(sexpr.isFloat()){
                return divDouble(i, acc, args);
            } else if(sexpr.isDouble()){
                return divDouble(i, acc, args);
            } else {
                throw new LillilRuntimeException(sexpr, "Cannot div non-numeric values");
            }
        }
        return new SLong(acc);
    }

    private SExpression divFloat(int i, float acc, SExpression... args) throws LillilRuntimeException{
        for (; i < args.length; i++) {
            SExpression sexpr = args[i];
            if(sexpr.isInteger()){
                acc /= sexpr.asInteger().value;
            }  else if (sexpr.isLong()){
                return divDouble(i, acc, args);
            } else if(sexpr.isFloat()){
                acc /= sexpr.asFloat().value;
            } else if(sexpr.isDouble()){
                return divDouble(i, acc, args);
            } else {
                throw new LillilRuntimeException(sexpr, "Cannot div non-numeric values");
            }
        }
        return new SFloat(acc);
    }

    private SExpression divDouble(int i, double acc, SExpression... args) throws LillilRuntimeException{
        for (; i < args.length; i++) {
            SExpression sexpr = args[i];
            if(sexpr.isInteger()){
                acc /= sexpr.asInteger().value;
            }  else if (sexpr.isLong()){
                acc /= sexpr.asLong().value;
            } else if(sexpr.isFloat()){
                acc /= sexpr.asFloat().value;
            } else if(sexpr.isDouble()){
                acc /= sexpr.asDouble().value;
            } else {
                throw new LillilRuntimeException(sexpr, "Cannot div non-numeric values");
            }
        }
        return new SDouble(acc);
    }
}
