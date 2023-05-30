package ca.awoo.lillil;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

import ca.awoo.lillil.sexpr.Symbol;
import ca.awoo.lillil.sexpr.Parser.ParseException;
import ca.awoo.lillil.sexpr.Tokenizer.TokenizerException;

/**
 * Tests evaluation of S-expressions.
 */
public class EvalTest {
    /**
     * Tests evaluation of a symbol.
     * @throws LillilRuntimeException
     */
    @Test
    public void testSymbol() throws LillilRuntimeException {
        Environment env = new Environment(null);
        env.bind("x", 42);
        Evaluator eval = new Evaluator();
        Object output = eval.eval(new Symbol("x"), env);
        assertEquals(42, output);
    }

    /**
     * Tests evaluation of addition.
     * @throws TokenizerException
     * @throws ParseException
     * @throws LillilRuntimeException
     */
    @Test
    public void testAdd() throws ParseException, TokenizerException, LillilRuntimeException {
        Environment env = new CoreEnvironment(null);
        env.bind("x", 42.0);
        Evaluator eval = new Evaluator();
        Object output = eval.eval("(+ x 1)", env);
        assertEquals(43.0, output);
    }

    /**
     * Tests evaluation of a lambda.
     * @throws TokenizerException
     * @throws ParseException
     * @throws LillilRuntimeException
     */
    @Test
    public void testLambda() throws ParseException, TokenizerException, LillilRuntimeException {
        Environment env = new CoreEnvironment(null);
        Evaluator eval = new Evaluator();
        Object output = eval.eval("(lambda (x) (+ x 1))", env);
        assertTrue("Expected a Function", output instanceof Function);
        env.bind("test", output);
        output = eval.eval("(test 42)", env);
        assertEquals(43.0, output);
    }

    /**
     * Tests evaluation of define.
     * @throws LillilRuntimeException
     */
    @Test
    public void testDefine() throws ParseException, TokenizerException, LillilRuntimeException {
        Environment env = new CoreEnvironment(null);
        Evaluator eval = new Evaluator();
        Object output = eval.eval("(define x 42)", env);
        assertEquals(null, output);
        assertEquals(42.0, env.lookup("x"));
    }
}
