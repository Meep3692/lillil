package ca.awoo.lillil;

import static org.junit.Assert.assertEquals;

import java.io.IOException;
import java.util.List;

import org.junit.Test;

import ca.awoo.lillil.core.CoreBindings;
import ca.awoo.lillil.sexpression.Parser;
import ca.awoo.lillil.sexpression.ParserException;
import ca.awoo.lillil.sexpression.SExpression;
import ca.awoo.lillil.sexpression.SFunction;
import ca.awoo.lillil.sexpression.SList;
import ca.awoo.lillil.sexpression.SMacro;
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

    @Test
    public void mathTest() throws TokenizerException, ParserException, IOException, LillilRuntimeException {
        Parser parser = new Parser("(assert-equals 6 (+ 2 4) (* 2 3) (/ 12 2) (/ 36 3 2) (- 10 4) (- 12 4 2) (mod 16 10))");
        List<SExpression> expressions = parser.getExpressions();
        Environment env = new Environment();
        CoreBindings.bindCore(env);
        env.setBinding("assert-equals", new SMacro() {
            @Override
            public SExpression apply(Environment env, SExpression... args) throws LillilRuntimeException {
                SExpression expected = env.evaluate(args[0]);
                for(int i = 1; i < args.length; i++){
                    System.out.println("Test: " + args[i].toString() + " = " + expected.toString());
                    assertEquals(expected, env.evaluate(args[i]));
                }
                return expected;
            }
        });
        assertEquals("6", env.evalAll(expressions).toString());
    }

    @Test
    public void compareTest() throws TokenizerException, ParserException, LillilRuntimeException, IOException {
        Parser parser = new Parser("(assert-true \"One does not equal one\" (= 1 1) (= 1 1 1))" + 
                                   "(assert-true \"Failed greater than\" (> 5 4 3 2 1))" +
                                   "(assert-true \"Failed less than!\" (< 1 2 3 4 5))" +
                                   "(assert-true \"Failed greater than or equal to\" (>= 5 4 3 2 1) (>= 5 5))" + 
                                   "(assert-true \"Failed less than or equal to\" (<= 1 2 3 4 5) (<= 5 5))" + 
                                   "(assert-false \"Failed greater than\" (> 5 4 7 2 1 0))" + 
                                   "(assert-false \"Failed less than!\" (< 1 2 3 4 5 0))" +
                                   "(assert-false \"Failed greater than or equal to\" (>= 5 4 7 2 1 0) (>= 5 6))" +
                                   "(assert-false \"Failed less than or equal to\" (<= 1 2 3 4 5 0) (<= 5 4))");
        List<SExpression> expressions = parser.getExpressions();
        Environment env = new Environment();
        CoreBindings.bindCore(env);
        env.evalAll(expressions);
    }
}
