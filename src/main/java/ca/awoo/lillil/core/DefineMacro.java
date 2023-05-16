package ca.awoo.lillil.core;

import ca.awoo.lillil.Environment;
import ca.awoo.lillil.LillilRuntimeException;
import ca.awoo.lillil.sexpression.SExpression;
import ca.awoo.lillil.sexpression.SMacro;

public class DefineMacro extends SMacro {

    @Override
    public SExpression apply(Environment env, SExpression... args) throws LillilRuntimeException {
        if(args.length != 2){
            throw new LillilRuntimeException(this, "define takes exactly two arguments");
        }
        SExpression name = args[0];
        SExpression value = args[1];
        if(name.isSymbol()){
            env.setBinding(name.asSymbol().value, env.evaluate(value));
            return name;
        } else {
            throw new LillilRuntimeException(this, "define takes a symbol as its first argument");
        }
    }
    
}
