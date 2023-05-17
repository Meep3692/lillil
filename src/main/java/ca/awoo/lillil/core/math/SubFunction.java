package ca.awoo.lillil.core.math;

import java.util.Arrays;

import ca.awoo.lillil.LillilRuntimeException;
import ca.awoo.lillil.sexpression.SDouble;
import ca.awoo.lillil.sexpression.SExpression;
import ca.awoo.lillil.sexpression.SFloat;
import ca.awoo.lillil.sexpression.SFunction;
import ca.awoo.lillil.sexpression.SInteger;
import ca.awoo.lillil.sexpression.SLong;

public class SubFunction extends SFunction {
    @Override
    public SExpression apply(SExpression... args) throws LillilRuntimeException {
        if(args[0].isInteger()){
            return subInt(1, args[0].asInteger().value, args);
        }  else if (args[0].isLong()){
            return subLong(1, args[0].asLong().value, args);
        } else if(args[0].isFloat()){
            return subFloat(1, args[0].asFloat().value, args);
        } else if(args[0].isDouble()){
            return subDouble(1, args[0].asDouble().value, args);
        } else {
            throw new LillilRuntimeException(args[0], "Cannot sub non-numeric values");
        }
    }

    private SExpression subInt(int i, int acc, SExpression... args) throws LillilRuntimeException{
        for (; i < args.length; i++) {
            SExpression sexpr = args[i];
            if(sexpr.isInteger()){
                acc -= sexpr.asInteger().value;
            }  else if (sexpr.isLong()){
                return subLong(i, acc, args);
            } else if(sexpr.isFloat()){
                return subFloat(i, acc, args);
            } else if(sexpr.isDouble()){
                return subDouble(i, acc, args);
            } else {
                throw new LillilRuntimeException(sexpr, "Cannot sub non-numeric values");
            }
        }
        return new SInteger(acc);
    }
    
    private SExpression subLong(int i, long acc, SExpression... args) throws LillilRuntimeException{
        for (; i < args.length; i++) {
            SExpression sexpr = args[i];
            if(sexpr.isInteger()){
                acc -= sexpr.asInteger().value;
            }  else if (sexpr.isLong()){
                acc -= sexpr.asLong().value;
            } else if(sexpr.isFloat()){
                return subDouble(i, acc, args);
            } else if(sexpr.isDouble()){
                return subDouble(i, acc, args);
            } else {
                throw new LillilRuntimeException(sexpr, "Cannot sub non-numeric values");
            }
        }
        return new SLong(acc);
    }

    private SExpression subFloat(int i, float acc, SExpression... args) throws LillilRuntimeException{
        for (; i < args.length; i++) {
            SExpression sexpr = args[i];
            if(sexpr.isInteger()){
                acc -= sexpr.asInteger().value;
            }  else if (sexpr.isLong()){
                return subDouble(i, acc, args);
            } else if(sexpr.isFloat()){
                acc -= sexpr.asFloat().value;
            } else if(sexpr.isDouble()){
                return subDouble(i, acc, args);
            } else {
                throw new LillilRuntimeException(sexpr, "Cannot sub non-numeric values");
            }
        }
        return new SFloat(acc);
    }

    private SExpression subDouble(int i, double acc, SExpression... args) throws LillilRuntimeException{
        for (; i < args.length; i++) {
            SExpression sexpr = args[i];
            if(sexpr.isInteger()){
                acc -= sexpr.asInteger().value;
            }  else if (sexpr.isLong()){
                acc -= sexpr.asLong().value;
            } else if(sexpr.isFloat()){
                acc -= sexpr.asFloat().value;
            } else if(sexpr.isDouble()){
                acc -= sexpr.asDouble().value;
            } else {
                throw new LillilRuntimeException(sexpr, "Cannot sub non-numeric values");
            }
        }
        return new SDouble(acc);
    }

}
