package ca.awoo.lillil.core.lang;

import ca.awoo.lillil.LillilRuntimeException;
import ca.awoo.lillil.sexpression.Parser;
import ca.awoo.lillil.sexpression.ParserException;
import ca.awoo.lillil.sexpression.SExpression;
import ca.awoo.lillil.sexpression.SFunction;
import ca.awoo.lillil.sexpression.SList;
import ca.awoo.lillil.sexpression.SString;
import ca.awoo.lillil.sexpression.TokenizerException;

public class ParseFunction extends SFunction {

    @Override
    public SExpression apply(SExpression... args) throws LillilRuntimeException {
        assertArity("parse", 1, args.length, false);
        SString code = assertArgType(args[0], SString.class);
        try {
            return new SList(new Parser(code.value).getExpressions());
        } catch (TokenizerException | ParserException e) {
            throw new LillilRuntimeException(this, e.getMessage(), e);
        }
    }
    
}
