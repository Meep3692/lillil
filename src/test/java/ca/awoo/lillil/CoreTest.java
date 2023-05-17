package ca.awoo.lillil;

import static org.junit.Assert.assertEquals;

import java.io.IOException;
import java.util.List;

import org.junit.Test;

import ca.awoo.lillil.core.CoreBindings;
import ca.awoo.lillil.sexpression.Parser;
import ca.awoo.lillil.sexpression.ParserException;
import ca.awoo.lillil.sexpression.SExpression;
import ca.awoo.lillil.sexpression.TokenizerException;

public class CoreTest {
    @Test
    public void lambdaTest() throws TokenizerException, ParserException, LillilRuntimeException, IOException{
        Parser parser = new Parser("((lambda (x) (+ x 1)) 2)");
        List<SExpression> expressions = parser.getExpressions();
        System.out.println("Test expressions size = 1");
        assertEquals(1, expressions.size());
        Environment env = new Environment();
        CoreBindings.bindCore(env);
        SExpression result = env.evaluate(expressions.get(0));
        System.out.println("Test result = 3");
        assertEquals("3", result.toString());
    }

    @Test
    public void macroDefineTest() throws TokenizerException, ParserException, LillilRuntimeException, IOException {
        Parser parser = new Parser("(define my-defn (macro (name args body) (define name (lambda args body)))) (my-defn add (a b) (+ a b)) (add 1 2)");
        List<SExpression> expressions = parser.getExpressions();
        Environment env = new Environment();
        CoreBindings.bindCore(env);
        SExpression result = env.evalAll(expressions);
        System.out.println("Test result = 3");
        assertEquals("3", result.asInteger().toString());
    }

    @Test
    public void defnTest() throws TokenizerException, ParserException, IOException, LillilRuntimeException {
        Parser parser = new Parser("(defn add (a b) (+ a b)) (add 1 2)");
        List<SExpression> expressions = parser.getExpressions();
        Environment env = new Environment();
        CoreBindings.bindCore(env);
        SExpression result = env.evalAll(expressions);
        System.out.println("Test result = 3");
        assertEquals("3", result.asInteger().toString());
    }
}
