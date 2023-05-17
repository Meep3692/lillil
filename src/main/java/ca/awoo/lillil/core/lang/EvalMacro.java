package ca.awoo.lillil.core.lang;

import ca.awoo.lillil.Environment;
import ca.awoo.lillil.LillilRuntimeException;
import ca.awoo.lillil.sexpression.SExpression;

public class EvalMacro extends ca.awoo.lillil.sexpression.SMacro{

    @Override
    public SExpression apply(Environment env, SExpression... args) throws LillilRuntimeException {
        if(args.length != 1){
            throw new LillilRuntimeException(this, "eval takes exactly one argument");
        }
        return env.evaluate(args[0]);
    }
    
}
