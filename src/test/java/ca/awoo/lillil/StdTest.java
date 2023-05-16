package ca.awoo.lillil;

import static org.junit.Assert.assertEquals;

import java.util.List;

import org.junit.Test;

import ca.awoo.lillil.core.StdBindings;
import ca.awoo.lillil.sexpression.Parser;
import ca.awoo.lillil.sexpression.ParserException;
import ca.awoo.lillil.sexpression.SExpression;
import ca.awoo.lillil.sexpression.TokenizerException;

public class StdTest {
    @Test
    public void lambdaTest() throws TokenizerException, ParserException, LillilRuntimeException{
        Parser parser = new Parser("((lambda (x) (+ x 1)) 2)");
        List<SExpression> expressions = parser.getExpressions();
        System.out.println("Test expressions size = 1");
        assertEquals(1, expressions.size());
        Environment env = new Environment(null, StdBindings.bindings);
        SExpression result = env.evaluate(expressions.get(0));
        System.out.println("Test result = 3");
        assertEquals("3", result.toString());
    }
}
