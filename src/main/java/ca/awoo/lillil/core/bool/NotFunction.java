package ca.awoo.lillil.core.bool;

import ca.awoo.lillil.LillilRuntimeException;
import ca.awoo.lillil.sexpression.SBoolean;
import ca.awoo.lillil.sexpression.SExpression;
import ca.awoo.lillil.sexpression.SFunction;

public class NotFunction extends SFunction {

    @Override
    public SExpression apply(SExpression... args) throws LillilRuntimeException {
        assertArity("not", 1, args.length, false);
        SBoolean b = assertArgType(args[0], SBoolean.class);
        return new SBoolean(!b.value);
    }
    
}
