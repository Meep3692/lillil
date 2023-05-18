package ca.awoo.lillil.core;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.List;

import ca.awoo.lillil.Environment;
import ca.awoo.lillil.LillilRuntimeException;
import ca.awoo.lillil.sexpression.Parser;
import ca.awoo.lillil.sexpression.ParserException;
import ca.awoo.lillil.sexpression.SExpression;
import ca.awoo.lillil.sexpression.SFunction;
import ca.awoo.lillil.sexpression.SString;
import ca.awoo.lillil.sexpression.TokenizerException;

public class ImportFunction extends SFunction {

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
            Environment env = new Environment();
            CoreBindings.bindCore(env);
            return env.evalAll(expressions);
        } catch(IOException | TokenizerException | ParserException e){
            throw new LillilRuntimeException(this, e.getMessage(), e);
        }
    }
    
}
