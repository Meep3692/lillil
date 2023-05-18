package ca.awoo.lillil.core.map;

import ca.awoo.lillil.LillilRuntimeException;
import ca.awoo.lillil.sexpression.SExpression;
import ca.awoo.lillil.sexpression.SFunction;
import ca.awoo.lillil.sexpression.SMapKey;
import ca.awoo.lillil.sexpression.SSymbol;

public class KeyToSymbolFunction extends SFunction {

    @Override
    public SExpression apply(SExpression... args) throws LillilRuntimeException {
        assertArity("key->symbol", 1, args.length, false);
        SMapKey key = assertArgType(args[0], SMapKey.class);
        return new SSymbol(key.value);
    }
    
}
