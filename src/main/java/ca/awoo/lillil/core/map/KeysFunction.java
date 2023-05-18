package ca.awoo.lillil.core.map;

import ca.awoo.lillil.LillilRuntimeException;
import ca.awoo.lillil.sexpression.SExpression;
import ca.awoo.lillil.sexpression.SFunction;
import ca.awoo.lillil.sexpression.SList;
import ca.awoo.lillil.sexpression.SMap;
import ca.awoo.lillil.sexpression.SMapKey;

public class KeysFunction extends SFunction{

    @Override
    public SExpression apply(SExpression... args) throws LillilRuntimeException {
        assertArity("keys", 1, args.length, false);
        SMap map = assertArgType(args[0], SMap.class);
        return new SList(map.keySet().toArray(new SMapKey[0]));
    }
    
}
