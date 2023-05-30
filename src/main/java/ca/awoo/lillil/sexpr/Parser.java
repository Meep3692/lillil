package ca.awoo.lillil.sexpr;

import ca.awoo.lillil.sexpr.Tokenizer.Token;
import ca.awoo.lillil.sexpr.Tokenizer.TokenizerException;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Parses a list of tokens.
 */
public class Parser {
    
    /**
     * Represents an error parsing the tokens.
     */
    public static class ParseException extends Exception {
        public final int line;
        public final int column;
        public final String message;

        public ParseException(int line, int column, String message) {
            this.line = line;
            this.column = column;
            this.message = message;
        }

        @Override
        public String toString() {
            return String.format("ParseError(line=%d, column=%d, message=%s)", line, column, message);
        }
    }

    /**
     * Parse all s-expressions in a string.
     * @param input The string to parse.
     * @return The list of parsed s-expressions.
     */
    public List<Object> parseAll(String input) throws ParseException, TokenizerException {
        Tokenizer tokenizer = new Tokenizer();
        List<Token> tokens = tokenizer.tokenize(input);
        return parseAll(tokens);
    }

    /**
     * Parse all s-expressions in a list of tokens.
     * @param tokens The list of tokens to parse.
     * @return The list of parsed s-expressions.
     */
    public List<Object> parseAll(List<Token> tokens){
        List<Object> result = new ArrayList<>();
        while(!tokens.isEmpty()){
            try {
                result.add(parse(tokens));
            } catch (ParseException e) {
                e.printStackTrace();
            }
        }
        return result;
    }

    /**
     * Parses a string into an s-expression.
     * @param input The string to parse.
     * @return The parsed s-expression.
     * @throws ParseException If there is an error parsing the string.
     * @throws TokenizerException
     */
    public Object parse(String input) throws ParseException, TokenizerException {
        Tokenizer tokenizer = new Tokenizer();
        List<Token> tokens = tokenizer.tokenize(input);
        return parse(tokens);
    }

    /**
     * Parses a list of tokens into an s-expression.
     * @param tokens The list of tokens to parse.
     * @return The parsed s-expression.
     * @throws ParseException If there is an error parsing the tokens.
     */
    public Object parse(List<Token> tokens) throws ParseException {
        Token token = tokens.remove(0);
        switch (token.type) {
            case OPEN_PAREN:
                return parseList(tokens, token.line, token.column);
            case OPEN_BRACE:
                return parseMap(tokens, token.line, token.column);
            case STRING:
                //Strip the quotes from the string
                return token.value.substring(1, token.value.length() - 1);
            case NUMBER:
                //TODO: We may want to use weird number types here. I think java has some build in.
                return Double.parseDouble(token.value);
            case BOOLEAN:
                return token.value.equals("#t");
            case SYMBOL:
                return new Symbol(token.value);
            case APOSTROPHE:
                //The apostrephe is syntactic sugar for the quote macro
                return Arrays.asList(new Symbol("quote"), parse(tokens));
            case COLON:
                return parseKey(tokens, token.line, token.column);
            default:
                throw new ParseException(token.line, token.column, "Unexpected token: " + token.value);
        }
    }

    private Object parseKey(List<Token> tokens, int line, int column) throws ParseException {
        //A key is represented in the token list as a colon token followed by a symbol token
        //In the parsed data structure, it is represented as a Key object
        //Key objects are actually executable to retrieve the value from a map
        Token token = tokens.remove(0);
        if (token.type != Tokenizer.TokenType.SYMBOL) {
            throw new ParseException(token.line, token.column, "Expected symbol after colon");
        }
        return new Key(token.value);
    }

    private Object parseList(List<Token> tokens, int line, int column) throws ParseException {
        List<Object> list = new ArrayList<>();
        //Gather list items until we hit a close paren
        while (tokens.size() > 0 && tokens.get(0).type != Tokenizer.TokenType.CLOSE_PAREN) {
            list.add(parse(tokens));
        }
        if (tokens.size() == 0) {
            //We hit the end of the input before we found a close paren
            throw new ParseException(line, column, "Unexpected end of input in list");
        } else {
            //Eat the close paren
            tokens.remove(0);
        }
        return list;
    }

    private Object parseMap(List<Token> tokens, int line, int column) throws ParseException {
        //This part is a bit odd because when we declare a literal map, we want it to contain the values of the evaluated expressions rather than the expressions themselves.
        //So instead of parsing it as a map, we parse it into a map building expression, which we then evaluate to get the map.
        List<Object> list = new ArrayList<>();
        list.add(new Symbol("hashmap"));
        //Gather list items until we hit a close brace
        while (tokens.size() > 0 && tokens.get(0).type != Tokenizer.TokenType.CLOSE_BRACE) {
            list.add(parse(tokens));
        }
        if (tokens.size() == 0) {
            //We hit the end of the input before we found a close brace
            throw new ParseException(line, column, "Unexpected end of input in map");
        } else {
            //Eat the close brace
            tokens.remove(0);
        }
        return list;
    }
}
