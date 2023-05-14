package ca.awoo.lillil;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import ca.awoo.lillil.sexpression.SExpression;
import ca.awoo.lillil.sexpression.SFunction;
import ca.awoo.lillil.sexpression.SMacro;
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

    SExpression evaluate (SExpression sexpr) throws LillilRuntimeException {
        if(sexpr.isAtom())
            return sexpr;
        else if(sexpr.isList()) {
            SExpression first = sexpr.asList().get(0);
            while(first.isList()){
                first = evaluate(first);
            }
            if(first.isSymbol()){
                
                SSymbol symbol = first.asSymbol();
                SExpression value = getBinding(symbol.value);
                if(value == null){
                    throw new LillilRuntimeException(symbol.position, symbol.line, symbol.column, symbol, "Attempted to call an undefined symbol");
                }
                if(value instanceof SFunction){
                    SFunction function = (SFunction)value;
                    List<SExpression> list = new ArrayList<SExpression>(sexpr.size());
                    for(SExpression subsexpr : sexpr.subList(1, sexpr.size())) {
                        list.add(evaluate(subsexpr));
                    }
                    return function.apply(list.toArray(new SExpression[0]));
                }else if(value instanceof SMacro){
                    SMacro macro = (SMacro)value;
                    return macro.apply(sexpr.subList(1, sexpr.size()).toArray(new SExpression[0]));
                }else{
                    throw new LillilRuntimeException(symbol.position, symbol.line, symbol.column, value, "Attempted to call a non-executable type");
                }
            }else{
                //The first element of the list is not a symbol, but is atomic
                throw new LillilRuntimeException(first.position, first.line, first.column, first, "Attempted to call a literal");
            }
        } else {
            throw new LillilRuntimeException(sexpr.position, sexpr.line, sexpr.column, sexpr, "Attempted to evaluate a non-atomic, non-list expression, which by all means shouldn't exist");
        }
    }

    public void setBinding(String name, SExpression value) {
        bindings.put(name, value);
    }
}
