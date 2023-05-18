package ca.awoo.lillil.core;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.List;
import java.util.Map;

import ca.awoo.lillil.Environment;
import ca.awoo.lillil.LillilRuntimeException;
import ca.awoo.lillil.core.lang.*;
import ca.awoo.lillil.core.math.*;
import ca.awoo.lillil.core.bool.*;
import ca.awoo.lillil.core.debug.*;
import ca.awoo.lillil.core.map.*;
import ca.awoo.lillil.sexpression.Parser;
import ca.awoo.lillil.sexpression.ParserException;
import ca.awoo.lillil.sexpression.SExpression;
import ca.awoo.lillil.sexpression.SFunction;
import ca.awoo.lillil.sexpression.SString;
import ca.awoo.lillil.sexpression.TokenizerException;

public class CoreBindings {
    private static Map<String, SExpression> bindings = new java.util.HashMap<String, SExpression>(){{
        //Math
        put("+", new AddFunction());
        put("-", new SubFunction());
        put("*", new MulFunction());
        put("/", new DivFunction());
        put("mod", new ModFunction());

        //Bool
        put("=", new EqualsFunction());
        put(">", new GreaterFunction());
        put("<", new LessFunction());
        put(">=", new GreaterEqualsFunction());
        put("<=", new LessEqualsFunction());
        put("not", new NotFunction());
        put("and", new AndFunction());
        put("or", new OrFunction());

        //Map
        put("keys", new KeysFunction());
        put("values", new ValuesFunction());
        put("key->string", new KeyToStringFunction());
        put("key->symbol", new KeyToSymbolFunction());
        put("new-map", new NewMapFunction());

        //Lang
        put("quote", new QuoteMacro());
        put("eval", new EvalMacro());
        put("if", new IfMacro());
        put("lambda", new LambdaMacro());
        put("macro", new MacroMacro());
        put("define", new DefineMacro());
        put("parse", new ParseFunction());

        put("import", new ImportFunction());
        put("use-module", new UseModuleMacro());
        put("map", new MapFunction());
        
        //Debug
        put("assert-true", new AssertTrueFunction());
        put("assert-false", new AssertFalseFunction());
        
    }};

    public static void bindCore(Environment env) throws IOException, TokenizerException, ParserException, LillilRuntimeException{
        for(String key : bindings.keySet()){
            env.setBinding(key, bindings.get(key));
        }
        //We need a way to import files without trying to load the core bindings again
        //This means the environment these files are loaded in will only have the most basic bindings
        env.setBinding("import-core", new SFunction() {
            @Override
            public SExpression apply(SExpression... args) throws LillilRuntimeException {
                assertArity("import", 1, args.length, false);
                SString path = assertArgType(args[0], SString.class);
                try{
                    String resourcepath = path.value.replace(".", "/") + ".lil";
                    InputStream is = CoreBindings.class.getClassLoader().getResourceAsStream(resourcepath);
                    BufferedReader br = new BufferedReader(new InputStreamReader(is));
                    StringBuilder sb = new StringBuilder();
                    for(String next = br.readLine(); next != null; next = br.readLine()){
                        sb.append(next);
                        sb.append("\n");
                    }
                    String code = sb.toString();
                    Parser parser = new Parser(code);
                    List<SExpression> expressions = parser.getExpressions();
                    Environment env = new Environment(null, bindings);
                    return env.evalAll(expressions);
                } catch(IOException | TokenizerException | ParserException e){
                    throw new LillilRuntimeException(this, e.getMessage(), e);
                }
            }
            
        });
        bindFile(env, "ca/awoo/lillil/core/core.lil");
        //Remove core-import binding, we don;t want it used by user code
        env.setBinding("import-core", null);
    }

    private static void bindFile(Environment env, String file) throws IOException, TokenizerException, ParserException, LillilRuntimeException{
        InputStream is = CoreBindings.class.getClassLoader().getResourceAsStream(file);
        BufferedReader br = new BufferedReader(new InputStreamReader(is));
        StringBuilder sb = new StringBuilder();
        for(String next = br.readLine(); next != null; next = br.readLine()){
            sb.append(next);
            sb.append("\n");
        }
        String code = sb.toString();
        Parser parser = new Parser(code);
        List<SExpression> expressions = parser.getExpressions();
        env.evalAll(expressions);
    }

    //This was supposed to automatically walk the directory and subdirectories, but it doesn't work
    /*private static void bindFile(Environment env, String file) throws IOException, TokenizerException, ParserException, LillilRuntimeException{
        InputStream is = CoreBindings.class.getClassLoader().getResourceAsStream(file);
        BufferedReader br = new BufferedReader(new InputStreamReader(is));
        List<String> subDirs = new ArrayList<String>();
        //Try to read like directory
        for(String next = br.readLine(); next != null; next = br.readLine()){
            if(CoreBindings.class.getClassLoader().getResource(file + "/" + next) == null){
                //Not a directory
                subDirs = null;
                break;
            }else{
                //Is a directory
                subDirs.add(next);
            }
        }
        if(subDirs == null){
            if(file.endsWith(".lil")){
                is = CoreBindings.class.getClassLoader().getResourceAsStream(file);
                br = new BufferedReader(new InputStreamReader(is));
                StringBuilder sb = new StringBuilder();
                for(String next = br.readLine(); next != null; next = br.readLine()){
                    sb.append(next);
                }
                String code = sb.toString();
                Parser parser = new Parser(code);
                List<SExpression> expressions = parser.getExpressions();
                for(SExpression expression : expressions){
                    env.evaluate(expression);
                }
            }
        }else{
            for(String subDir : subDirs){
                bindFile(env, file + "/" + subDir);
            }
        }
    }*/
}
