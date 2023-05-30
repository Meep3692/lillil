package ca.awoo.lillil;

import java.util.Arrays;
import java.util.List;

import ca.awoo.lillil.sexpr.Symbol;

/**
 * A function written in lillil.
 */
public class Lambda implements Function {

    private final Evaluator eval = new Evaluator();
    private final Environment env;
    private final List<Symbol> params;
    private final Object body;

    /**
     * Create a new lambda.
     * @param env The environment in which the lambda was defined.
     * @param params The parameters of the lambda.
     * @param body The body of the lambda.
     */
    public Lambda(Environment env, List<Symbol> params, Object body) {
        this.env = env;
        this.params = params;
        this.body = body;
    }

    @Override
    public Object apply(Object... args) throws LillilRuntimeException {
        Environment local = new Environment(env);
        //Collect and bind parameters
        for(int i = 0; i < params.size(); i++){
            //Varargs
            if(params.get(i).value.equals(".")){
                List<Object> varargs = Arrays.asList(args).subList(i, args.length);
                local.bind(params.get(i+1).value, varargs);
            }else{
                local.bind(params.get(i).value, args[i]);
            }
        }
        return eval.eval(body, local);
    }
    
}
