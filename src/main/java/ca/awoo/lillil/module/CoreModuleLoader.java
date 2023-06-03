package ca.awoo.lillil.module;

import java.util.*;

import ca.awoo.lillil.*;
import ca.awoo.lillil.sexpr.*;
import ca.awoo.lillil.sexpr.Parser.ParseException;
import ca.awoo.lillil.sexpr.Tokenizer.TokenizerException;

/**
 * A loader for loading the core module.
 */
public class CoreModuleLoader extends NativeModuleLoader{
    public CoreModuleLoader(Lillil lillil){
        super(lillil, "core");
        this.module.put("+", new Function() {
            @Override
            public Object apply(Object... args) {
                double sum = 0;
                for (Object arg : args) {
                    sum += (double) arg;
                }
                return sum;
            }
        });
        this.module.put("-", new Function() {
            @Override
            public Object apply(Object... args) {
                double difference = (double) args[0];
                for (int i = 1; i < args.length; i++) {
                    difference -= (double) args[i];
                }
                return difference;
            }
        });
        this.module.put("*", new Function() {
            @Override
            public Object apply(Object... args) {
                double product = 1;
                for (Object arg : args) {
                    product *= (double) arg;
                }
                return product;
            }
        });
        this.module.put("/", new Function() {
            @Override
            public Object apply(Object... args) {
                double quotient = (double) args[0];
                for (int i = 1; i < args.length; i++) {
                    quotient /= (double) args[i];
                }
                return quotient;
            }
        });
        this.module.put("=", new Function() {
            @Override
            public Object apply(Object... args) {
                Object first = args[0];
                for (int i = 1; i < args.length; i++) {
                    if (!first.equals(args[i])) {
                        return false;
                    }
                }
                return true;
            }
        });
        this.module.put("<", new Function() {
            @Override
            public Object apply(Object... args) {
                double first = (double) args[0];
                for (int i = 1; i < args.length; i++) {
                    if (first >= (double) args[i]) {
                        return false;
                    }
                }
                return true;
            }
        });
        this.module.put(">", new Function() {
            @Override
            public Object apply(Object... args) {
                double first = (double) args[0];
                for (int i = 1; i < args.length; i++) {
                    if (first <= (double) args[i]) {
                        return false;
                    }
                }
                return true;
            }
        });
        this.module.put("<=", new Function() {
            @Override
            public Object apply(Object... args) {
                double first = (double) args[0];
                for (int i = 1; i < args.length; i++) {
                    if (first > (double) args[i]) {
                        return false;
                    }
                }
                return true;
            }
        });
        this.module.put(">=", new Function() {
            @Override
            public Object apply(Object... args) {
                double first = (double) args[0];
                for (int i = 1; i < args.length; i++) {
                    if (first < (double) args[i]) {
                        return false;
                    }
                }
                return true;
            }
        });
        this.module.put("and", new Function() {
            @Override
            public Object apply(Object... args) {
                for (Object arg : args) {
                    if (!(boolean) arg) {
                        return false;
                    }
                }
                return true;
            }
        });
        this.module.put("or", new Function() {
            @Override
            public Object apply(Object... args) {
                for (Object arg : args) {
                    if ((boolean) arg) {
                        return true;
                    }
                }
                return false;
            }
        });
        this.module.put("not", new Function() {
            @Override
            public Object apply(Object... args) {
                return !(boolean) args[0];
            }
        });
        this.module.put("list", new Function() {
            @Override
            public Object apply(Object... args) {
                return Arrays.asList(args);
            }
        });
        this.module.put("car", new Function() {
            @Override
            public Object apply(Object... args) {
                return ((List<Object>) args[0]).get(0);
            }
        });
        this.module.put("cdr", new Function() {
            @Override
            public Object apply(Object... args) {
                return ((List<Object>) args[0]).subList(1, ((List<Object>) args[0]).size());
            }
        });
        this.module.put("cons", new Function() {
            @Override
            public Object apply(Object... args) {
                List<Object> list = new ArrayList<>((List<Object>) args[1]);
                list.add(0, args[0]);
                return list;
            }
        });
        this.module.put("concat", new Function() {
            @Override
            public Object apply(Object... args) {
                List<Object> list = new ArrayList<>((List<Object>) args[0]);
                list.addAll((List<Object>) args[1]);
                return list;
            }
        });
        this.module.put("append", new Function() {
            @Override
            public Object apply(Object... args) {
                List<Object> list = new ArrayList<>((List<Object>) args[0]);
                list.add(args[1]);
                return list;
            }
        });
        this.module.put("len", new Function() {
            @Override
            public Object apply(Object... args) {
                return ((List<Object>) args[0]).size();
            }
        });
        this.module.put("empty?", new Function() {
            @Override
            public Object apply(Object... args) {
                return ((List<Object>) args[0]).isEmpty();
            }
        });
        this.module.put("list?", new Function() {
            @Override
            public Object apply(Object... args) {
                return args[0] instanceof List;
            }
        });
        this.module.put("null?", new Function() {
            @Override
            public Object apply(Object... args) {
                return args[0] == null;
            }
        });
        this.module.put("number?", new Function() {
            @Override
            public Object apply(Object... args) {
                return args[0] instanceof Double;
            }
        });
        this.module.put("string?", new Function() {
            @Override
            public Object apply(Object... args) {
                return args[0] instanceof String;
            }
        });
        this.module.put("boolean?", new Function() {
            @Override
            public Object apply(Object... args) {
                return args[0] instanceof Boolean;
            }
        });
        this.module.put("symbol?", new Function() {
            @Override
            public Object apply(Object... args) {
                return args[0] instanceof Symbol;
            }
        });
        this.module.put("key?", new Function() {
            @Override
            public Object apply(Object... args) {
                return args[0] instanceof Key;
            }
        });
        this.module.put("symbol->string", new Function() {
            @Override
            public Object apply(Object... args) {
                return ((Symbol) args[0]).value;
            }
        });
        this.module.put("string->symbol", new Function() {
            @Override
            public Object apply(Object... args) {
                return new Symbol((String) args[0]);
            }
        });
        this.module.put("key->string", new Function() {
            @Override
            public Object apply(Object... args) {
                return ((Key) args[0]).value;
            }
        });
        this.module.put("string->key", new Function() {
            @Override
            public Object apply(Object... args) {
                return new Key((String) args[0]);
            }
        });
        this.module.put("number->string", new Function() {
            @Override
            public Object apply(Object... args) {
                return args[0].toString();
            }
        });
        this.module.put("string->number", new Function() {
            @Override
            public Object apply(Object... args) {
                return Double.parseDouble((String) args[0]);
            }
        });
        this.module.put("boolean->string", new Function() {
            @Override
            public Object apply(Object... args) {
                return ((Boolean)args[0]) ? "#t" : "#f";
            }
        });
        this.module.put("string->boolean", new Function() {
            @Override
            public Object apply(Object... args) {
                return ((String)args[0]).equals("#t");
            }
        });
        this.module.put("string-append", new Function() {
            @Override
            public Object apply(Object... args) {
                StringBuilder sb = new StringBuilder();
                for (Object arg : args) {
                    sb.append(arg);
                }
                return sb.toString();
            }
        });
        this.module.put("string-length", new Function() {
            @Override
            public Object apply(Object... args) {
                return ((String) args[0]).length();
            }
        });
        this.module.put("string-ref", new Function() {
            @Override
            public Object apply(Object... args) {
                return ((String) args[0]).charAt((int) (double) args[1]);
            }
        });
        this.module.put("map", new Function() {
            @Override
            public Object apply(Object... args) throws LillilRuntimeException {
                List<Object> list = new ArrayList<>();
                Function function = (Function) args[0];
                if(args[1] instanceof List) {
                    for (Object arg : (List<Object>) args[1]) {
                        list.add(function.apply(arg));
                    }
                } else if(args[1] instanceof Map){
                    for (Map.Entry<Object, Object> entry : ((Map<Object, Object>) args[1]).entrySet()) {
                        list.add(function.apply(entry.getKey(), entry.getValue()));
                    }
                } else {
                    throw new LillilRuntimeException("map: second argument must be a list");
                }
                System.out.println(list);
                return list;
            }
        });
        this.module.put("use", new Macro() {
            @Override
            public Object apply(Environment env, Object... args) throws LillilRuntimeException {
                Evaluator eval = new Evaluator();
                Object arg = eval.eval(args[0], env);
                if(arg instanceof Map) {
                    Map<Object, Object> map = (Map<Object, Object>) arg;
                    for (Map.Entry<Object, Object> entry : map.entrySet()) {
                        if(entry.getKey() instanceof String) {
                            env.bind((String)entry.getKey(), entry.getValue());
                        } else {
                            throw new LillilRuntimeException("use: get weird map with non-string keys");
                        }
                    }
                    return null;
                    
                } else {
                    throw new LillilRuntimeException("use: first argument must be a map");
                }
            }
        });
        this.module.put("use-import", new Macro() {

            @Override
            public Object apply(Environment env, Object... args) throws LillilRuntimeException {
                if(args[0] instanceof String){
                    try {
                        return ((Macro)(module.get("use"))).apply(env, lillil.getModule((String) args[0]));
                    } catch (ParseException | TokenizerException e) {
                        throw new LillilRuntimeException("use-import: failed to import module " + args[0], e);
                    }
                } else {
                    throw new LillilRuntimeException("use-import: first argument must be a string");
                }
            }

        });
    }
}
