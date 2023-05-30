package ca.awoo.lillil;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import ca.awoo.lillil.sexpr.Key;
import ca.awoo.lillil.sexpr.Symbol;

/**
 * Core lillil environment.
 * Comes with basic functionality like math and comparison.
 */
public class CoreEnvironment extends Environment {

    /**
     * Create a new core environment.
     * @param parent The parent environment.
     */
    public CoreEnvironment(Environment parent){
        super(parent);
        bind("+", new Function() {
            @Override
            public Object apply(Object... args) {
                double sum = 0;
                for (Object arg : args) {
                    sum += (double) arg;
                }
                return sum;
            }
        });
        bind("-", new Function() {
            @Override
            public Object apply(Object... args) {
                double difference = (double) args[0];
                for (int i = 1; i < args.length; i++) {
                    difference -= (double) args[i];
                }
                return difference;
            }
        });
        bind("*", new Function() {
            @Override
            public Object apply(Object... args) {
                double product = 1;
                for (Object arg : args) {
                    product *= (double) arg;
                }
                return product;
            }
        });
        bind("/", new Function() {
            @Override
            public Object apply(Object... args) {
                double quotient = (double) args[0];
                for (int i = 1; i < args.length; i++) {
                    quotient /= (double) args[i];
                }
                return quotient;
            }
        });
        bind("=", new Function() {
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
        bind("<", new Function() {
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
        bind(">", new Function() {
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
        bind("<=", new Function() {
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
        bind(">=", new Function() {
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
        bind("and", new Function() {
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
        bind("or", new Function() {
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
        bind("not", new Function() {
            @Override
            public Object apply(Object... args) {
                return !(boolean) args[0];
            }
        });
        bind("list", new Function() {
            @Override
            public Object apply(Object... args) {
                return Arrays.asList(args);
            }
        });
        bind("car", new Function() {
            @Override
            public Object apply(Object... args) {
                return ((List<Object>) args[0]).get(0);
            }
        });
        bind("cdr", new Function() {
            @Override
            public Object apply(Object... args) {
                return ((List<Object>) args[0]).subList(1, ((List<Object>) args[0]).size());
            }
        });
        bind("cons", new Function() {
            @Override
            public Object apply(Object... args) {
                List<Object> list = new ArrayList<>((List<Object>) args[1]);
                list.add(0, args[0]);
                return list;
            }
        });
        bind("concat", new Function() {
            @Override
            public Object apply(Object... args) {
                List<Object> list = new ArrayList<>((List<Object>) args[0]);
                list.addAll((List<Object>) args[1]);
                return list;
            }
        });
        bind("append", new Function() {
            @Override
            public Object apply(Object... args) {
                List<Object> list = new ArrayList<>((List<Object>) args[0]);
                list.add(args[1]);
                return list;
            }
        });
        bind("len", new Function() {
            @Override
            public Object apply(Object... args) {
                return ((List<Object>) args[0]).size();
            }
        });
        bind("empty?", new Function() {
            @Override
            public Object apply(Object... args) {
                return ((List<Object>) args[0]).isEmpty();
            }
        });
        bind("list?", new Function() {
            @Override
            public Object apply(Object... args) {
                return args[0] instanceof List;
            }
        });
        bind("null?", new Function() {
            @Override
            public Object apply(Object... args) {
                return args[0] == null;
            }
        });
        bind("number?", new Function() {
            @Override
            public Object apply(Object... args) {
                return args[0] instanceof Double;
            }
        });
        bind("string?", new Function() {
            @Override
            public Object apply(Object... args) {
                return args[0] instanceof String;
            }
        });
        bind("boolean?", new Function() {
            @Override
            public Object apply(Object... args) {
                return args[0] instanceof Boolean;
            }
        });
        bind("symbol?", new Function() {
            @Override
            public Object apply(Object... args) {
                return args[0] instanceof Symbol;
            }
        });
        bind("key?", new Function() {
            @Override
            public Object apply(Object... args) {
                return args[0] instanceof Key;
            }
        });
        bind("symbol->string", new Function() {
            @Override
            public Object apply(Object... args) {
                return ((Symbol) args[0]).value;
            }
        });
        bind("string->symbol", new Function() {
            @Override
            public Object apply(Object... args) {
                return new Symbol((String) args[0]);
            }
        });
        bind("key->string", new Function() {
            @Override
            public Object apply(Object... args) {
                return ((Key) args[0]).value;
            }
        });
        bind("string->key", new Function() {
            @Override
            public Object apply(Object... args) {
                return new Key((String) args[0]);
            }
        });
        bind("number->string", new Function() {
            @Override
            public Object apply(Object... args) {
                return args[0].toString();
            }
        });
        bind("string->number", new Function() {
            @Override
            public Object apply(Object... args) {
                return Double.parseDouble((String) args[0]);
            }
        });
        bind("boolean->string", new Function() {
            @Override
            public Object apply(Object... args) {
                return ((Boolean)args[0]) ? "#t" : "#f";
            }
        });
        bind("string->boolean", new Function() {
            @Override
            public Object apply(Object... args) {
                return ((String)args[0]).equals("#t");
            }
        });
        bind("string-append", new Function() {
            @Override
            public Object apply(Object... args) {
                StringBuilder sb = new StringBuilder();
                for (Object arg : args) {
                    sb.append(arg);
                }
                return sb.toString();
            }
        });
        bind("string-length", new Function() {
            @Override
            public Object apply(Object... args) {
                return ((String) args[0]).length();
            }
        });
        bind("string-ref", new Function() {
            @Override
            public Object apply(Object... args) {
                return ((String) args[0]).charAt((int) (double) args[1]);
            }
        });
        bind("map", new Function() {
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

    }
}
