package ca.awoo.lillil.core.lang;

import ca.awoo.lillil.Environment;
import ca.awoo.lillil.LillilRuntimeException;
import ca.awoo.lillil.sexpression.SExpression;
import ca.awoo.lillil.sexpression.SMacro;

public class IfMacro extends SMacro {

    @Override
    public SExpression apply(Environment env, SExpression... args) throws LillilRuntimeException {
        assertArity("if", 3, args.length, false);
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
