package ca.awoo.lillil;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import ca.awoo.lillil.sexpression.SExpression;
import ca.awoo.lillil.sexpression.SFunction;
import ca.awoo.lillil.sexpression.SList;
import ca.awoo.lillil.sexpression.SMacro;
import ca.awoo.lillil.sexpression.SMap;
import ca.awoo.lillil.sexpression.SMapKey;
import ca.awoo.lillil.sexpression.SSymbol;

public class Environment {
    private Environment parent;
    private Map<String, SExpression> bindings;

    public Environment(Environment parent, Map<String, SExpression> bindings) {
        this.parent = parent;
        this.bindings = bindings;
    }

    public Environment(Environment parent) {
        this.parent = parent;
        bindings = new HashMap<String, SExpression>();
    }

    public Environment() {
        this(null);
    }

    public SExpression getBinding(String name) {
        if(bindings.containsKey(name))
            return bindings.get(name);
        else if(parent != null)
            return parent.getBinding(name);
        else
            return null;
    }

    public void setBinding(String name, SExpression value) {
        bindings.put(name, value);
    }

    public SExpression evaluate (SExpression sexpr) throws LillilRuntimeException {
        if(sexpr.isAtom()){
            if(sexpr.isSymbol()){
                SExpression value = getBinding(sexpr.asSymbol().value);
                if(value == null){
                    throw new LillilRuntimeException(sexpr, "Attempted to evaluate an undefined symbol");
                }
                return value;
            }
            return sexpr;
        }
        else if(sexpr.isList()) {
            SList slist = sexpr.asList();
            SExpression first = slist.get(0);
            while(first.isList()){
                first = evaluate(first);
            }
            if(first.isSymbol()){
                SSymbol symbol = first.asSymbol();
                first = getBinding(first.asSymbol().value);
                if(first == null){
                    throw new LillilRuntimeException(symbol.position, symbol.line, symbol.column, symbol, "Attempted to call an undefined symbol");
                }
            }
            if(first instanceof SFunction){
                SFunction function = (SFunction)first;
                List<SExpression> list = new ArrayList<SExpression>(slist.size());
                for(SExpression subsexpr : slist.subList(1, sexpr.asList().size())) {
                    list.add(evaluate(subsexpr));
                }
                return function.apply(list.toArray(new SExpression[0]));
            }else if(first instanceof SMacro){
                SMacro macro = (SMacro)first;
                return macro.apply(this, slist.subList(1, slist.size()).toArray(new SExpression[0]));
            }else if(first instanceof SMapKey){
                //Retrieve value from map
                SMapKey key = (SMapKey)first;
                if(slist.size() != 2){
                    throw new LillilRuntimeArityException(sexpr, "get", 2, slist.size() + 1, false);
                }
                SExpression map = evaluate(slist.get(1));
                if(!(map instanceof SMap)){
                    throw new LillilRuntimeException(map, "Attempted to get a value from a non-map type");
                }
                return ((SMap)map).get(key);
            }else{
                throw new LillilRuntimeException(first.position, first.line, first.column, first, "Attempted to call a non-executable type");
            }
        } else {
            throw new LillilRuntimeException(sexpr.position, sexpr.line, sexpr.column, sexpr, "Attempted to evaluate a non-atomic, non-list expression, which by all means shouldn't exist");
        }
    }

    public SExpression evalAll(List<SExpression> sexprs) throws LillilRuntimeException{
        SExpression result = null;
        for(SExpression sexpr : sexprs){
            result = evaluate(sexpr);
        }
        return result;
    }

    public int hashCode(){
        return bindings.hashCode() + (parent == null ? 0 : parent.hashCode());
    }
    
}
