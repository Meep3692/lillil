package ca.awoo.lillil.core;

import ca.awoo.lillil.Environment;
import ca.awoo.lillil.LillilRuntimeException;
import ca.awoo.lillil.sexpression.SExpression;
import ca.awoo.lillil.sexpression.SMacro;
import ca.awoo.lillil.sexpression.SSymbol;

public class DefineMacro extends SMacro {

    @Override
    public SExpression apply(Environment env, SExpression... args) throws LillilRuntimeException {
        assertArity("define", 2, args.length, false);
        SSymbol name = assertArgType(args[0], SSymbol.class);
        SExpression value = args[1];
        env.setBinding(name.value, env.evaluate(value));
        return name;
    }
    
}
