package ca.awoo.lillil;

import static org.junit.Assert.assertEquals;

import java.util.List;

import org.junit.Test;

import ca.awoo.lillil.core.CoreBindings;
import ca.awoo.lillil.sexpression.Parser;
import ca.awoo.lillil.sexpression.ParserException;
import ca.awoo.lillil.sexpression.SExpression;
import ca.awoo.lillil.sexpression.TokenizerException;

public class CoreTest {
    @Test
    public void lambdaTest() throws TokenizerException, ParserException, LillilRuntimeException{
        Parser parser = new Parser("((lambda (x) (+ x 1)) 2)");
        List<SExpression> expressions = parser.getExpressions();
        System.out.println("Test expressions size = 1");
        assertEquals(1, expressions.size());
        Environment env = new Environment(null, CoreBindings.bindings);
        SExpression result = env.evaluate(expressions.get(0));
        System.out.println("Test result = 3");
        assertEquals("3", result.toString());
    }

    @Test
    public void macroDefineTest() throws TokenizerException, ParserException, LillilRuntimeException {
        Parser parser = new Parser("(define defn (macro (name args body) (define name (lambda args body)))) (defn add (a b) (+ a b)) (add 1 2)");
        List<SExpression> expressions = parser.getExpressions();
        Environment env = new Environment(null, CoreBindings.bindings);
        env.evaluate(expressions.get(0));
        env.evaluate(expressions.get(1));
        SExpression result = env.evaluate(expressions.get(2));
        System.out.println("Test result = 3");
        assertEquals("3", result.asInteger().toString());
    }
}
