package ca.awoo.lillil;

import org.junit.Test;

import ca.awoo.lillil.core.CoreBindings;
import ca.awoo.lillil.sexpression.Parser;
import ca.awoo.lillil.sexpression.ParserException;
import ca.awoo.lillil.sexpression.SExpression;
import ca.awoo.lillil.sexpression.SFunction;
import ca.awoo.lillil.sexpression.SInteger;
import ca.awoo.lillil.sexpression.SList;
import ca.awoo.lillil.sexpression.SMacro;
import ca.awoo.lillil.sexpression.TokenizerException;

import static org.junit.Assert.*;

import java.util.List;

public class EvaluatorTest {
    @Test
    public void externalFunctionTest() throws TokenizerException, ParserException, LillilRuntimeException {
        Parser parser = new Parser("(+ 1 2)");
        List<SExpression> expressions = parser.getExpressions();
        System.out.println("Test expressions size = 1");
        assertEquals(1, expressions.size());
        Environment env = new Environment();
        env.setBinding("+", new SFunction(){
            public SExpression apply(SExpression ... args) throws LillilRuntimeException{
                int acc = 0;
                for(SExpression arg : args){
                    if(!arg.isInteger())
                        throw new LillilRuntimeException(arg, "Attempted to add a non-integer");
                    acc += arg.asInteger().value;
                }
                return new SInteger(acc);
            }
        });
        SExpression result = env.evaluate(expressions.get(0));
        System.out.println("Test result = 3");
        assertEquals("3", result.toString());
    }

    @Test
    public void externalMacroTest() throws TokenizerException, ParserException, LillilRuntimeException {
        Parser parser = new Parser("'(1 2)");
        List<SExpression> expressions = parser.getExpressions();
        System.out.println("Test expressions size = 1");
        assertEquals(1, expressions.size());
        Environment env = new Environment();
        env.setBinding("quote", new SMacro(){
            public SExpression apply(Environment env, SExpression ... args) throws LillilRuntimeException{
                if(args.length < 1){
                    return new SList();
                }
                return args[0];
            }
        });
        SExpression result = env.evaluate(expressions.get(0));
        System.out.println("Test result = (1 2)");
        assertTrue("Is result list", result.isList());
        assertEquals("1", result.asList().get(0).toString());
        assertEquals("2", result.asList().get(1).toString());
    }
}
