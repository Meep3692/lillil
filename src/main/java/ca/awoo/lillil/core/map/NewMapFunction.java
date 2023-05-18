package ca.awoo.lillil.core.map;

import java.util.HashMap;
import java.util.Map;

import ca.awoo.lillil.LillilRuntimeException;
import ca.awoo.lillil.sexpression.SExpression;
import ca.awoo.lillil.sexpression.SFunction;
import ca.awoo.lillil.sexpression.SMap;
import ca.awoo.lillil.sexpression.SMapKey;

public class NewMapFunction extends SFunction{

    @Override
    public SExpression apply(SExpression... args) throws LillilRuntimeException {
        Map<SMapKey, SExpression> map = new HashMap<SMapKey, SExpression>();
        for(int i = 0; i < args.length; i += 2){
            SMapKey key = assertArgType(args[i], SMapKey.class);
            map.put(key, args[i + 1]);
        }
        return new SMap(map);
    }
    
}
