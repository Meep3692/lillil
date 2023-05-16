package ca.awoo.lillil.core;

import ca.awoo.lillil.Environment;
import ca.awoo.lillil.LillilRuntimeException;
import ca.awoo.lillil.sexpression.SExpression;
import ca.awoo.lillil.sexpression.SMacro;

public class IfMacro extends SMacro {

    @Override
    public SExpression apply(Environment env, SExpression... args) throws LillilRuntimeException {
        if(args.length != 3){
            throw new LillilRuntimeException(this, "if takes exactly three arguments");
        }
        SExpression condition = args[0];
        SExpression trueBranch = args[1];
        SExpression falseBranch = args[2];
        if(env.evaluate(condition).isBoolean() && env.evaluate(condition).asBoolean().value){
            return env.evaluate(trueBranch);
        } else {
            return env.evaluate(falseBranch);
        }
    }
    
}
