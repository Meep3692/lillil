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
import ca.awoo.lillil.sexpression.Parser;
import ca.awoo.lillil.sexpression.ParserException;
import ca.awoo.lillil.sexpression.SExpression;
import ca.awoo.lillil.sexpression.TokenizerException;

public class CoreBindings {
    private static Map<String, SExpression> bindings = new java.util.HashMap<String, SExpression>(){{
        put("+", new AddFunction());
        put("-", new SubFunction());
        put("*", new MulFunction());
        put("/", new DivFunction());
        put("mod", new ModFunction());
        put("quote", new QuoteMacro());
        put("lambda", new LambdaMacro());
        put("macro", new MacroMacro());
        put("eval", new EvalMacro());
        put("if", new IfMacro());
        put("define", new DefineMacro());
        put("parse", new ParseFunction());
    }};

    public static void bindCore(Environment env) throws IOException, TokenizerException, ParserException, LillilRuntimeException{
        for(String key : bindings.keySet()){
            env.setBinding(key, bindings.get(key));
        }
        bindFile(env, "ca/awoo/lillil/core/define.lil");
    }

    private static void bindFile(Environment env, String file) throws IOException, TokenizerException, ParserException, LillilRuntimeException{
        InputStream is = CoreBindings.class.getClassLoader().getResourceAsStream(file);
                BufferedReader br = new BufferedReader(new InputStreamReader(is));
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
