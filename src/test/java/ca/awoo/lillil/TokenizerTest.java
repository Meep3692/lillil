package ca.awoo.lillil;

import static org.junit.Assert.assertEquals;

import java.util.List;

import org.junit.Test;

import ca.awoo.lillil.sexpr.Tokenizer;
import ca.awoo.lillil.sexpr.Tokenizer.Token;
import ca.awoo.lillil.sexpr.Tokenizer.TokenType;
import ca.awoo.lillil.sexpr.Tokenizer.TokenizerException;

/**
 * Test the Tokenizer class.
 */
public class TokenizerTest {
    
    /**
     * Read a string with each token in it to ensure each token is parsed correctly.
     * @throws TokenizerException
     */
    @Test
    public void testTokenize() throws TokenizerException {
        String input = "(){}\"hello\"74.8world':;comment\n\t#t";
        Tokenizer tokenizer = new Tokenizer();
        List<Token> tokens = tokenizer.tokenize(input);
        assertEquals(10, tokens.size());
        assertEquals("(", tokens.get(0).value);
        assertEquals(TokenType.OPEN_PAREN, tokens.get(0).type);
        assertEquals(")", tokens.get(1).value);
        assertEquals(TokenType.CLOSE_PAREN, tokens.get(1).type);
        assertEquals("{", tokens.get(2).value);
        assertEquals(TokenType.OPEN_BRACE, tokens.get(2).type);
        assertEquals("}", tokens.get(3).value);
        assertEquals(TokenType.CLOSE_BRACE, tokens.get(3).type);
        assertEquals("\"hello\"", tokens.get(4).value);
        assertEquals(TokenType.STRING, tokens.get(4).type);
        assertEquals("74.8", tokens.get(5).value);
        assertEquals(TokenType.NUMBER, tokens.get(5).type);
        assertEquals("world", tokens.get(6).value);
        assertEquals(TokenType.SYMBOL, tokens.get(6).type);
        assertEquals("'", tokens.get(7).value);
        assertEquals(TokenType.APOSTROPHE, tokens.get(7).type);
        assertEquals(":", tokens.get(8).value);
        assertEquals(TokenType.COLON, tokens.get(8).type);
        assertEquals("#t", tokens.get(9).value);
        assertEquals(TokenType.BOOLEAN, tokens.get(9).type);
        //Comments are whitespace aren't included in the token list in order to make it easier to parse.
    }
}
