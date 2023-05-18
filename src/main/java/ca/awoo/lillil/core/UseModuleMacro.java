package ca.awoo.lillil.core;

import java.util.Map.Entry;

import ca.awoo.lillil.Environment;
import ca.awoo.lillil.LillilRuntimeException;
import ca.awoo.lillil.sexpression.SExpression;
import ca.awoo.lillil.sexpression.SMacro;
import ca.awoo.lillil.sexpression.SMap;
import ca.awoo.lillil.sexpression.SMapKey;

public class UseModuleMacro extends SMacro{

    @Override
    public SExpression apply(Environment env, SExpression... args) throws LillilRuntimeException {
        assertArity("use-module", 1, args.length, false);
        SExpression arg = env.evaluate(args[0]);
        SMap module = assertArgType(arg, SMap.class);
        for(Entry<SMapKey, SExpression> entry : module.entrySet()){
            env.setBinding(entry.getKey().value, entry.getValue());
        }
        return module;
    }
    
}
