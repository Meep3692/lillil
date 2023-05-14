package ca.awoo.lillil;

import java.util.List;

import org.junit.Test;

import ca.awoo.lillil.sexpression.Parser;
import ca.awoo.lillil.sexpression.ParserException;
import ca.awoo.lillil.sexpression.SExpression;
import ca.awoo.lillil.sexpression.SFunction;
import ca.awoo.lillil.sexpression.SInteger;
import ca.awoo.lillil.sexpression.TokenizerException;

import static org.junit.Assert.*;

public class ParserTest {
    @Test
    public void parserTest() throws TokenizerException, ParserException {
        Parser parser = new Parser("(+ 1 2)");
        List<SExpression> expressions = parser.getExpressions();
        System.out.println("Test expressions size = 1");
        assertEquals(1, expressions.size());
        System.out.println("Test reparsing");
        assertEquals(expressions, new Parser(expressions.get(0).toString()).getExpressions());
        System.out.println("Test expressions.get(0).get(0) = +");
        assertEquals("+", expressions.get(0).get(0).toString());
        System.out.println("Test expressions.get(0).get(1) = 1");
        assertEquals("1", expressions.get(0).get(1).toString());
        System.out.println("Test expressions.get(0).get(2) = 2");
        assertEquals("2", expressions.get(0).get(2).toString());
    }

    @Test
    public void quoteTest() throws TokenizerException, ParserException {
        Parser parser = new Parser("'(1 2 3)");
        List<SExpression> expressions = parser.getExpressions();
        System.out.println(expressions);
        System.out.println("Test expressions size = 1");
        assertEquals(1, expressions.size());
        System.out.println("Check quote");
        assertEquals("quote", expressions.get(0).get(0).toString());
        System.out.println("Check list");
        assertEquals("1", expressions.get(0).get(1).get(0).toString());
        assertEquals("2", expressions.get(0).get(1).get(1).toString());
        assertEquals("3", expressions.get(0).get(1).get(2).toString());
    }

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
}
