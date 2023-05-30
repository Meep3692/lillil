package ca.awoo.lillil;

import static org.junit.Assert.assertEquals;

import java.util.List;

import org.junit.Test;

import ca.awoo.lillil.sexpr.Parser;
import ca.awoo.lillil.sexpr.Symbol;
import ca.awoo.lillil.sexpr.Key;
import ca.awoo.lillil.sexpr.Parser.ParseException;
import ca.awoo.lillil.sexpr.Tokenizer.TokenizerException;

/**
 * Unit test for Parser.
 */
public class ParserTest {
    /**
     * Parse a string
     * @throws ParseException
     * @throws TokenizerException
     */
    @Test
    public void testParseString() throws ParseException, TokenizerException {
        String input = "\"hello\"";
        Parser parser = new Parser();
        String output = (String) parser.parse(input);
        assertEquals("hello", output);
    }

    /**
     * Parse a number
     * @throws ParseException
     * @throws TokenizerException
     */
    @Test
    public void testParseNumber() throws ParseException, TokenizerException {
        String input = "72.6";
        Parser parser = new Parser();
        double output = (double) parser.parse(input);
        assertEquals(72.6, output, 0.0001);
    }

    /**
     * Parse a symbol
     * @throws ParseException
     * @throws TokenizerException
     */
    @Test
    public void testParseSymbol() throws ParseException, TokenizerException {
        String input = "hello";
        Parser parser = new Parser();
        Symbol output = (Symbol) parser.parse(input);
        assertEquals("hello", output.value);
    }

    /**
     * Parse a boolean
     * @throws ParseException
     * @throws TokenizerException
     */
    @Test
    public void testParseBoolean() throws ParseException, TokenizerException {
        String input = "#t";
        Parser parser = new Parser();
        boolean output = (boolean) parser.parse(input);
        assertEquals(true, output);
    }

    /**
     * Parse a list
     * @throws ParseException
     * @throws TokenizerException
     */
    @Test
    public void testParseList() throws ParseException, TokenizerException {
        String input = "(1 2 3)";
        Parser parser = new Parser();
        List<Object> output = (List<Object>) parser.parse(input);
        assertEquals(3, output.size());
        assertEquals(1.0, (double)output.get(0), 0.0001);
        assertEquals(2.0, (double)output.get(1), 0.0001);
        assertEquals(3.0, (double)output.get(2), 0.0001);
    }

    /**
     * Parse a list with a nested list
     * @throws ParseException
     * @throws TokenizerException
     */
    @Test
    public void testParseNestedList() throws ParseException, TokenizerException {
        String input = "(1 (2 3) 4)";
        Parser parser = new Parser();
        List<Object> output = (List<Object>) parser.parse(input);
        assertEquals(3, output.size());
        assertEquals(1.0, (double)output.get(0), 0.0001);
        assertEquals(4.0, (double)output.get(2), 0.0001);
        List<Object> nested = (List<Object>) output.get(1);
        assertEquals(2.0, (double)nested.get(0), 0.0001);
        assertEquals(3.0, (double)nested.get(1), 0.0001);
    }

    /**
     * Parse a quoted list
     * @throws ParseException
     * @throws TokenizerException
     */
    @Test
    public void testParseQuotedList() throws ParseException, TokenizerException {
        String input = "'(1 2 3)";
        Parser parser = new Parser();
        List<Object> output = (List<Object>) parser.parse(input);
        assertEquals(2, output.size());
        assertEquals("quote", ((Symbol)output.get(0)).value);
        List<Object> nested = (List<Object>) output.get(1);
        assertEquals(1.0, (double)nested.get(0), 0.0001);
        assertEquals(2.0, (double)nested.get(1), 0.0001);
        assertEquals(3.0, (double)nested.get(2), 0.0001);
    }

    /**
     * Parse a map
     * @throws ParseException
     * @throws TokenizerException
     */
    @Test
    public void testParseMap() throws ParseException, TokenizerException {
        String input = "{:a 1 :b 2}";
        Parser parser = new Parser();
        List<Object> output = (List<Object>) parser.parse(input);
        assertEquals(5, output.size());
        assertEquals("hashmap", ((Symbol)output.get(0)).value);
        assertEquals("a", ((Key)output.get(1)).value);
        assertEquals(1.0, (double)output.get(2), 0.0001);
        assertEquals("b", ((Key)output.get(3)).value);
        assertEquals(2.0, (double)output.get(4), 0.0001);
    }
}
