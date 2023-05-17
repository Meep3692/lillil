package ca.awoo.lillil.core.math;

import ca.awoo.lillil.LillilRuntimeException;
import ca.awoo.lillil.sexpression.SDouble;
import ca.awoo.lillil.sexpression.SExpression;
import ca.awoo.lillil.sexpression.SFloat;
import ca.awoo.lillil.sexpression.SFunction;
import ca.awoo.lillil.sexpression.SInteger;
import ca.awoo.lillil.sexpression.SLong;

public class ModFunction extends SFunction {

    @Override
    public SExpression apply(SExpression... args) throws LillilRuntimeException {
        if(args.length != 2){
            throw new LillilRuntimeException(this, "mod takes exactly two arguments");
        }
        if(!args[0].isNumber()){
            throw new LillilRuntimeException(args[0], "mod takes a number as its first argument");
        }
        if(!args[1].isNumber()){
            throw new LillilRuntimeException(args[1], "mod takes a number as its second argument");
        }
        if(args[0].isInteger()){
            if(args[1].isInteger()){
                return new SInteger(args[0].asInteger().value % args[1].asInteger().value);
            }else if (args[1].isLong()){
                return new SLong(args[0].asInteger().value % args[1].asLong().value);
            }else if(args[1].isFloat()){
                return new SFloat(args[0].asInteger().value % args[1].asFloat().value);
            } else if(args[1].isDouble()){
                return new SDouble(args[0].asInteger().value % args[1].asDouble().value);
            }
        } else if(args[0].isLong()){
            if(args[1].isInteger()){
                return new SLong(args[0].asLong().value % args[1].asInteger().value);
            } else if (args[1].isLong()){
                return new SLong(args[0].asLong().value % args[1].asLong().value);
            } else if(args[1].isFloat()){
                return new SFloat(args[0].asLong().value % args[1].asFloat().value);
            } else if(args[1].isDouble()){
                return new SDouble(args[0].asLong().value % args[1].asDouble().value);
            }
        } else if(args[0].isFloat()){
            if(args[1].isInteger()){
                return new SFloat(args[0].asFloat().value % args[1].asInteger().value);
            } else if (args[1].isLong()){
                return new SFloat(args[0].asFloat().value % args[1].asLong().value);
            } else if(args[1].isFloat()){
                return new SFloat(args[0].asFloat().value % args[1].asFloat().value);
            } else if(args[1].isDouble()){
                return new SDouble(args[0].asFloat().value % args[1].asDouble().value);
            }
        } else if(args[0].isDouble()){
            if(args[1].isInteger()){
                return new SDouble(args[0].asDouble().value % args[1].asInteger().value);
            } else if (args[1].isLong()){
                return new SDouble(args[0].asDouble().value % args[1].asLong().value);
            } else if(args[1].isFloat()){
                return new SDouble(args[0].asDouble().value % args[1].asFloat().value);
            } else if(args[1].isDouble()){
                return new SDouble(args[0].asDouble().value % args[1].asDouble().value);
            }
        }
        throw new LillilRuntimeException(this, "mod got numbers it didn't recognize: " + args[0].getClass().getName() + " and " + args[1].getClass().getName());

    }
    
}
