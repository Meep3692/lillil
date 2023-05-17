package ca.awoo.lillil.core;

import ca.awoo.lillil.LillilRuntimeException;
import ca.awoo.lillil.sexpression.Parser;
import ca.awoo.lillil.sexpression.ParserException;
import ca.awoo.lillil.sexpression.SExpression;
import ca.awoo.lillil.sexpression.SFunction;
import ca.awoo.lillil.sexpression.SList;
import ca.awoo.lillil.sexpression.TokenizerException;

public class ParseFunction extends SFunction {

    @Override
    public SExpression apply(SExpression... args) throws LillilRuntimeException {
        if(args.length != 1){
            throw new LillilRuntimeException(this, "parse takes exactly one argument");
        }
        if(!args[0].isString()){
            throw new LillilRuntimeException(args[0], "parse takes a string as its argument");
        }
        try {
            return new SList(new Parser(args[0].asString().value).getExpressions());
        } catch (TokenizerException | ParserException e) {
            throw new LillilRuntimeException(this, e.getMessage());
        }
    }
    
}
