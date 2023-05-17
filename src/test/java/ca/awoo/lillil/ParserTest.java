package ca.awoo.lillil;

import java.util.List;

import org.junit.Test;

import ca.awoo.lillil.sexpression.Parser;
import ca.awoo.lillil.sexpression.ParserException;
import ca.awoo.lillil.sexpression.SExpression;
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
        assertEquals("+", expressions.get(0).asList().get(0).toString());
        System.out.println("Test expressions.get(0).get(1) = 1");
        assertEquals("1", expressions.get(0).asList().get(1).toString());
        System.out.println("Test expressions.get(0).get(2) = 2");
        assertEquals("2", expressions.get(0).asList().get(2).toString());
    }

    @Test
    public void quoteTest() throws TokenizerException, ParserException {
        Parser parser = new Parser("'(1 2 3)");
        List<SExpression> expressions = parser.getExpressions();
        System.out.println(expressions);
        System.out.println("Test expressions size = 1");
        assertEquals(1, expressions.size());
        System.out.println("Check quote");
        assertEquals("quote", expressions.get(0).asList().get(0).toString());
        System.out.println("Check list");
        assertEquals("1", expressions.get(0).asList().get(1).asList().get(0).toString());
        assertEquals("2", expressions.get(0).asList().get(1).asList().get(1).toString());
        assertEquals("3", expressions.get(0).asList().get(1).asList().get(2).toString());
    }

    @Test
    public void typeTest() throws TokenizerException, ParserException{
        Parser parser = new Parser("#t 3.141592 3.14f 7 1234567890L \"Hello, world!\" symbol (list elements)");
        List<SExpression> expressions = parser.getExpressions();
        System.out.println(expressions);
        assertTrue("Expected SBoolean but got " + expressions.get(0).getClass().getSimpleName(), expressions.get(0).isBoolean());
        assertTrue("Expected SDouble but got "  + expressions.get(1).getClass().getSimpleName(), expressions.get(1).isDouble());
        assertTrue("Expected SFloat but got "   + expressions.get(2).getClass().getSimpleName(), expressions.get(2).isFloat());
        assertTrue("Expected SInteger but got " + expressions.get(3).getClass().getSimpleName(), expressions.get(3).isInteger());
        assertTrue("Expected SLong but got "    + expressions.get(4).getClass().getSimpleName(), expressions.get(4).isLong());
        assertTrue("Expected SString but got "  + expressions.get(5).getClass().getSimpleName(), expressions.get(5).isString());
        assertTrue("Expected SSymbol but got "  + expressions.get(6).getClass().getSimpleName(), expressions.get(6).isSymbol());
        assertTrue("Expected SList but got "    + expressions.get(7).getClass().getSimpleName(), expressions.get(7).isList());
    }
    
}
