package ca.awoo.lillil.core.map;

import ca.awoo.lillil.LillilRuntimeException;
import ca.awoo.lillil.sexpression.SExpression;
import ca.awoo.lillil.sexpression.SFunction;
import ca.awoo.lillil.sexpression.SList;
import ca.awoo.lillil.sexpression.SMap;

public class ValuesFunction extends SFunction {

    @Override
    public SExpression apply(SExpression... args) throws LillilRuntimeException {
        assertArity("values", 1, args.length, false);
        SMap map = assertArgType(args[0], SMap.class);
        return new SList(map.values().toArray(new SExpression[0]));
    }
    
}
