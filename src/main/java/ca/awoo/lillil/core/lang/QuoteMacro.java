package ca.awoo.lillil.core.lang;

import ca.awoo.lillil.Environment;
import ca.awoo.lillil.LillilRuntimeException;
import ca.awoo.lillil.sexpression.SExpression;

public class QuoteMacro extends ca.awoo.lillil.sexpression.SMacro{

    @Override
    public SExpression apply(Environment env, SExpression... args) throws LillilRuntimeException {
        assertArity("quote", 1, args.length, false);
        return args[0];
    }
    
}
