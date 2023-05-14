package ca.awoo.lillil.std.lang;

import ca.awoo.lillil.LillilRuntimeException;
import ca.awoo.lillil.sexpression.SExpression;

public class QuoteMacro extends ca.awoo.lillil.sexpression.SMacro{

    @Override
    public SExpression apply(SExpression... args) throws LillilRuntimeException {
        if(args.length != 1){
            throw new LillilRuntimeException(this, "quote takes exactly one argument");
        }
        return args[0];
    }
    
}
