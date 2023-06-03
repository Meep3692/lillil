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
        Lillil lillil = new Lillil();
        lillil.useCoreModule();
        lillil.bindBase("x", 42.0);
        Object output = lillil.eval("(+ x 1)");
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
        Lillil lillil = new Lillil();
        lillil.useCoreModule();
        Object output = lillil.eval("(lambda (x) (+ x 1))");
        assertTrue("Expected a Function", output instanceof Function);
        lillil.bindBase("test", output);
        output = lillil.eval("(test 42)");
        assertEquals(43.0, output);
    }

    /**
     * Tests evaluation of define.
     * @throws LillilRuntimeException
     */
    @Test
    public void testDefine() throws ParseException, TokenizerException, LillilRuntimeException {
        Lillil lillil = new Lillil();
        lillil.useCoreModule();
        Object output = lillil.eval("(define x 42)");
        assertEquals(null, output);
        assertEquals(42.0, lillil.lookupPersist("x"));
    }
}
