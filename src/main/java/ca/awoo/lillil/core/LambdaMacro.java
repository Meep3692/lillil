package ca.awoo.lillil.core;

import ca.awoo.lillil.Environment;
import ca.awoo.lillil.LillilRuntimeException;
import ca.awoo.lillil.sexpression.SExpression;
import ca.awoo.lillil.sexpression.SFunction;
import ca.awoo.lillil.sexpression.SList;
import ca.awoo.lillil.sexpression.SSymbol;

public class LambdaMacro extends ca.awoo.lillil.sexpression.SMacro {

    @Override
    public SExpression apply(Environment env, SExpression... args) throws LillilRuntimeException {
        assertArity("lambda", 2, args.length, false);
        SList arguments  = assertArgType(args[0], SList.class);
        for(SExpression sexpr : arguments){
            assertArgType(sexpr, SSymbol.class);
        }
        SList body = assertArgType(args[1], SList.class);
        return new Lambda(env, arguments, body);
    }

    private class Lambda extends SFunction {

        Environment parentEnvironment;
        SList arguments;
        SList body;

        public Lambda(Environment parentEnvironment, SList arguments, SList body){
            this.parentEnvironment = parentEnvironment;
            this.arguments = arguments;
            this.body = body;
        }

        @Override
        public SExpression apply(SExpression... args) throws LillilRuntimeException {
            Environment env = new Environment(parentEnvironment);
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
                        assertArity("lambda", arguments.size(), args.length, false);
                    }
                    env.setBinding(arg.value, args[i]);
                }
            }
            return env.evaluate(body);
        }

        public int hashCode(){
            return arguments.hashCode() + body.hashCode() + parentEnvironment.hashCode();
        }
    }
    
}
