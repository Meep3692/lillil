package ca.awoo.lillil.core;

import java.util.HashMap;
import java.util.Map;

import ca.awoo.lillil.Environment;
import ca.awoo.lillil.LillilRuntimeException;
import ca.awoo.lillil.sexpression.SExpression;
import ca.awoo.lillil.sexpression.SList;
import ca.awoo.lillil.sexpression.SMacro;
import ca.awoo.lillil.sexpression.SSymbol;

public class MacroMacro extends SMacro {
    @Override
    public SExpression apply(Environment env, SExpression... args) throws LillilRuntimeException {
        assertArity("macro", 2, args.length, false);
        SList arguments  = assertArgType(args[0], SList.class);
        for(SExpression sexpr : arguments){
            assertArgType(sexpr, SSymbol.class);
        }
        SList body = assertArgType(args[1], SList.class);
        return new Macro(env, arguments, body);
    }

    private SList findAndReplace(SList list, Map<SExpression, SExpression> replace){
        SList out = new SList();
        for(int i = 0; i < list.size(); i++){
            SExpression sexpr = list.get(i);
            if(sexpr.isList()){
                out.add(findAndReplace(sexpr.asList(), replace));
            }else if(replace.containsKey(sexpr)){
                out.add(replace.get(sexpr));
            }else{
                out.add(sexpr);
            }
        }
        return out;
    }

    private class Macro extends SMacro {

        Environment parentEnvironment;
        SList arguments;
        SList body;

        public Macro(Environment parentEnvironment, SList arguments, SList body){
            this.parentEnvironment = parentEnvironment;
            this.arguments = arguments;
            this.body = body;
        }

        @Override
        public SExpression apply(Environment callingEnvironment, SExpression... args) throws LillilRuntimeException {
            Map<SExpression, SExpression> replace = new HashMap<>();
            for(int i = 0; i < arguments.size(); i++){
                SSymbol arg = arguments.get(i).asSymbol();
                if(arg.value == "."){
                    //Varargs
                    SSymbol varname = arguments.get(i+1).asSymbol();
                    SList varargs = new SList();
                    for(int j = i; j < args.length; j++){
                        varargs.add(args[j]);
                    }
                    replace.put(varname, varargs);
                    break;
                }else{
                    if(i >= args.length){
                        assertArity("macro", arguments.size(), args.length, false);
                    }
                    replace.put(arg, args[i]);
                }
            }
            SList newbody = findAndReplace(body, replace);
            return callingEnvironment.evaluate(newbody);
        }

        public int hashCode(){
            return arguments.hashCode() + body.hashCode() + parentEnvironment.hashCode();
        }
    }
}
