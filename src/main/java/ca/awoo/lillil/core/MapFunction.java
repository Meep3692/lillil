package ca.awoo.lillil.core;

import java.util.ArrayList;
import java.util.List;
import java.util.Map.Entry;

import ca.awoo.lillil.LillilRuntimeException;
import ca.awoo.lillil.sexpression.SExpression;
import ca.awoo.lillil.sexpression.SFunction;
import ca.awoo.lillil.sexpression.SList;
import ca.awoo.lillil.sexpression.SMapKey;

public class MapFunction extends SFunction {

    @Override
    public SExpression apply(SExpression... args) throws LillilRuntimeException {
        assertArity("map", 2, args.length, true);
        SFunction function = assertArgType(args[0], SFunction.class);
        List<SExpression> result = new ArrayList<>();
        List<List<SExpression>> argsToPass = new ArrayList<>();
        for(int i = 1; i < args.length; i++){
            if(args[i].isList()){
                argsToPass.add(args[i].asList());
            }else if(args[i].isMap()){
                List<SExpression> entries = new ArrayList<>();
                for(Entry<SMapKey, SExpression> entry : args[i].asMap().entrySet()){
                    entries.add(new SList(entry.getKey(), entry.getValue()));
                }
                argsToPass.add(entries);
            }
            argsToPass.add(new ArrayList<>());
        }
        for(int i = 0; true; i++){
            List<SExpression> argsForThisIteration = new ArrayList<>();
            for(List<SExpression> argList : argsToPass){
                if(i < argList.size()){
                    argsForThisIteration.add(argList.get(i));
                }else{
                    return new SList(result);
                }
            }
            result.add(function.apply(argsForThisIteration.toArray(new SExpression[argsForThisIteration.size()])));
        }
    }
    
}
