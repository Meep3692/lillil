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
        SList arguments = new SList();
        if(args.length != 2){
            throw new LillilRuntimeException(this, "lambda takes two arguments");
        }
        if(!args[0].isList()){
            throw new LillilRuntimeException(args[0], "Invalid type for argument list");
        }
        arguments = args[0].asList();
        for(SExpression sexpr : arguments){
            if(!sexpr.isSymbol()){
                throw new LillilRuntimeException(sexpr, "Invalid type for argument, expected symbol");
            }
        }
        if(!args[1].isList()){
            throw new LillilRuntimeException(args[1], "Invalid type for argument list");
        }
        return new Macro(env, arguments, args[1].asList());
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
                        throw new LillilRuntimeException(this, "Not enough arguments");
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
