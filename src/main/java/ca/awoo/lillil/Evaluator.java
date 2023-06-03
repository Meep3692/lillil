package ca.awoo.lillil;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import ca.awoo.lillil.sexpr.*;
import ca.awoo.lillil.sexpr.Parser.ParseException;
import ca.awoo.lillil.sexpr.Tokenizer.TokenizerException;

/**
 * Evaluates s-expressions.
 */
public class Evaluator {

    /**
     * Evaluates an expression and casts it to the given type if possible.
     * This is mostly used internally but could be useful.
     * @param <T> The type to cast to.
     * @param expr The expression to evaluate.
     * @param env The environment to evaluate in.
     * @param type The type to cast to.
     * @return The result of the evaluation.
     * @throws LillilRuntimeException
     */
    public <T> T evalForType(Object expr, Environment env, Class<T> type) throws LillilRuntimeException{
        Object result = eval(expr, env);
        if(type.isInstance(result)){
            return type.cast(result);
        }else{
            throw new LillilRuntimeException("Expected " + type.getName() + " but got " + result.getClass().getName());
        }
    }

    /**
     * Evaluates a list of expressions.
     * @param expr The expressions to evaluate.
     * @param env The environment to evaluate in.
     * @return The results of the evaluation.
     * @throws ParseException
     * @throws TokenizerException
     * @throws LillilRuntimeException
     */
    public List<Object> evalAll(String expr, Environment env) throws ParseException, TokenizerException, LillilRuntimeException{
        return evalAll(new Parser().parseAll(expr), env);
    }

    /**
     * Evaluates a list of expressions.
     * @param exprs The expressions to evaluate.
     * @param env The environment to evaluate in.
     * @return The results of the evaluation.
     * @throws LillilRuntimeException
     */
    public List<Object> evalAll(List<Object> exprs, Environment env) throws LillilRuntimeException{
        List<Object> results = new ArrayList<>();
        for(Object expr : exprs){
            results.add(eval(expr, env));
        }
        return results;
    }

    /**
     * Evaluates a single expression.
     * @param expr The expression to evaluate.
     * @param env The environment to evaluate in.
     * @return The result of the evaluation.
     * @throws ParseException
     * @throws TokenizerException
     * @throws LillilRuntimeException
     */
    public Object eval(String expr, Environment env) throws ParseException, TokenizerException, LillilRuntimeException{
        return eval(new Parser().parse(expr), env);
    }

    /**
     * Evaluates a single expression.
     * @param expr The expression to evaluate.
     * @param env The environment to evaluate in.
     * @return The result of the evaluation.
     * @throws LillilRuntimeException
     */
    public Object eval(Object expr, Environment env) throws LillilRuntimeException {
        if (expr instanceof Symbol) {
            return env.lookup(((Symbol) expr).value);
        } else if (expr instanceof List) {
            List<Object> list = (List<Object>) expr;
            if(list.isEmpty()){
                return list;
            }
            Object first = list.get(0);
            if (first instanceof Symbol) {
                Symbol symbol = (Symbol) first;
                switch (symbol.value) {
                    case "quote":
                        return list.get(1);
                    case "if":
                        Boolean condition = evalForType(list.get(1), env, Boolean.class);
                        if ((Boolean) condition) {
                            return eval(list.get(2), env);
                        } else {
                            return eval(list.get(3), env);
                        }
                    case "define":
                        if(!(list.get(1) instanceof Symbol)){
                            throw new LillilRuntimeException("Expected symbol as first argument to define");
                        }
                        Symbol key = (Symbol) list.get(1);
                        env.bind(key.value, eval(list.get(2), env));
                        return null;
                    case "lambda":
                        List<Symbol> params = castList((List)list.get(1), Symbol.class);
                        Object body = list.get(2);
                        return new Lambda(env, params, body);
                    case "begin":
                        Object result = null;
                        for (int i = 1; i < list.size(); i++) {
                            result = eval(list.get(i), env);
                        }
                        return result;
                    case "hashmap":
                        //This is here instead of core because it's part of the language
                        //Eval list elements and pair to make hashmap
                        List<Object> elements = new ArrayList<>();
                        for(int i = 1; i < list.size(); i++){
                            elements.add(eval(list.get(i), env));
                        }
                        if(elements.size() % 2 != 0){
                            throw new LillilRuntimeException("Expected even number of elements in hashmap");
                        }
                        Map<String, Object> map = new HashMap<>();
                        //We need to ensure that key elements are of Key type
                        List<Object> keys = elements.stream().filter((e) -> elements.indexOf(e) % 2 == 0).collect(Collectors.toList());
                        List<Object> values = elements.stream().filter((e) -> elements.indexOf(e) % 2 == 1).collect(Collectors.toList());
                        for(int i = 0; i < keys.size(); i++){
                            Object keyObj = keys.get(i);
                            if(!(keyObj instanceof Key)){
                                throw new LillilRuntimeException("Expected key as key in hashmap");
                            }
                            Key mapkey = (Key) keyObj;
                            map.put(mapkey.value, values.get(i));
                        }
                        return map;
                    default:
                        //Function call or similar executable value
                        return evalExecutable(list, env);
                }
            } else {
                //Function call or similar executable value
                return evalExecutable(list, env);
            }
        } else {
            return expr;
        }
    }

    private Object evalExecutable(List<Object> list, Environment env) throws LillilRuntimeException {
        Object first = list.get(0);
        //Function call or similar executable value
        Object executable = eval(first, env);
        //Pre-emptivly check for undefined symbol and throw with some details
        if(first instanceof Symbol && executable == null){
            throw new LillilRuntimeException("Symbol " + ((Symbol) first).value + " is not defined");
        }
        //Check for macro
        if(executable instanceof Macro){
            Macro macro = (Macro) executable;
            return macro.apply(env, list.subList(1, list.size()).toArray());
        }
        List<Object> args = new ArrayList<>(list.size() - 1);
        for(int i = 0; i < list.size() - 1; i++){
            args.add(eval(list.get(i+1), env));
        }
        return evalExecutable(executable, args);
    }

    private Object evalExecutable(Object exec, List<Object> args) throws LillilRuntimeException{
        if(exec instanceof Function){
            Function function = (Function) exec;
            return function.apply(args.toArray());
        } else if(exec instanceof Key){
            Key key = (Key) exec;
            if(args.get(0) instanceof Map){
                Map<String, Object> map = (Map<String, Object>) args.get(0);
                return map.get(key.value);
            } else if(args.get(0) == null){
                throw new LillilRuntimeException("Attempt to map-index into null, key: " + key.value);
            } else {
                throw new LillilRuntimeException("Attempt to map-index into non-map: " + args.get(0).getClass().getName());
            }
        } else if (exec == null){
            throw new LillilRuntimeException("Attempt to execute null value");
        } else{
            throw new LillilRuntimeException("Attempt to execute non-executable value: " + exec.getClass().getName());
        }
    }

    private <T> List<T> castList(List<Object> list, Class<T> type) throws LillilRuntimeException {
        for (Object obj : list) {
            if (!type.isInstance(obj)) {
                throw new LillilRuntimeException("Expected " + type.getName() + " but got " + obj.getClass().getName());
            }
        }
        return (List<T>) list;
    }
}
