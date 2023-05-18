package ca.awoo.lillil.core.debug;

import ca.awoo.lillil.LillilRuntimeException;
import ca.awoo.lillil.sexpression.SBoolean;
import ca.awoo.lillil.sexpression.SExpression;
import ca.awoo.lillil.sexpression.SFunction;
import ca.awoo.lillil.sexpression.SString;

public class AssertTrueFunction extends SFunction {

    @Override
    public SExpression apply(SExpression... args) throws LillilRuntimeException {
        assertArity("assert-true", 2, args.length, true);
        SString errorMessage = assertArgType(args[0], SString.class);
        for(int i = 1; i < args.length; i++){
            SBoolean b = assertArgType(args[i], SBoolean.class);
            if(!b.value){
                throw new LillilRuntimeException(args[i], errorMessage.value);
            }
        }
        return new SBoolean(true);
    }
    
}
