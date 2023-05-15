package ca.awoo.lillil.std;

import ca.awoo.lillil.Environment;
import ca.awoo.lillil.LillilRuntimeException;
import ca.awoo.lillil.sexpression.SExpression;
import ca.awoo.lillil.sexpression.SFunction;
import ca.awoo.lillil.sexpression.SList;
import ca.awoo.lillil.sexpression.SSymbol;

public class LambdaMacro extends ca.awoo.lillil.sexpression.SMacro {

    Environment parentlEnvironment;
    SList arguments;
    SList body;

    @Override
    public SExpression apply(Environment env, SExpression... args) throws LillilRuntimeException {
        if(args.length != 2){
            throw new LillilRuntimeException(this, "lambda takes two arguments");
        }
        if(!args[0].isList()){
            throw new LillilRuntimeException(args[0], "Invalid type for argument list");
        }
        this.arguments = args[0].asList();
        for(SExpression sexpr : arguments){
            if(!sexpr.isSymbol()){
                throw new LillilRuntimeException(sexpr, "Invalid type for argument, expected symbol");
            }
        }
        if(!args[1].isList()){
            throw new LillilRuntimeException(args[1], "Invalid type for argument list");
        }
        this.body = args[1].asList();
        this.parentlEnvironment = env;
        return lambda;
    }

    private SFunction lambda = new SFunction() {
        @Override
        public SExpression apply(SExpression... args) throws LillilRuntimeException {
            Environment env = new Environment(parentlEnvironment);
            for(int i = 0; i < arguments.size(); i++){
                SSymbol arg = arguments.get(i).asSymbol();
                if(arg.value == "."){
                    //Varargs
                    SSymbol varname = arguments.get(i+1).asSymbol();
                    SList varargs = new SList();
                    for(int j = i; j < args.length; j++){
                        varargs.add(args[j]);
                    }
                    env.setBinding(varname.value, varargs);
                    break;
                }else{
                    if(i >= args.length){
                        throw new LillilRuntimeException(this, "Not enough arguments");
                    }
                    env.setBinding(arg.value, args[i]);
                }
            }
            return env.evaluate(body);
        }
    };
    
}
