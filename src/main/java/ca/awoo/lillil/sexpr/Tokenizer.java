package ca.awoo.lillil.sexpr;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Tokenizes a string into a list of tokens.
 */
public class Tokenizer {
    /**
     * Represents a token.
     */
    public class Token {
        public final String value;
        public final TokenType type;
        public final int start;
        public final int end;
        public final int length;
        public final int line;
        public final int column;


        public Token(String value, TokenType type, int start, int end, int line, int column) {
            this.value = value;
            this.type = type;
            this.start = start;
            this.end = end;
            this.length = end - start;
            this.line = line;
            this.column = column;
        }
    }

    /**
     * Type of a token.
     */
    public enum TokenType {
        OPEN_PAREN,
        CLOSE_PAREN,
        OPEN_BRACE,
        CLOSE_BRACE,
        STRING,
        NUMBER,
        BOOLEAN,
        SYMBOL,
        APOSTROPHE,
        COLON,
        COMMENT,
        WHITESPACE
    }
    
    private class TokenFinder {
        Pattern pattern;
        public final TokenType type;

        public TokenFinder(Pattern pattern, TokenType type) {
            this.pattern = pattern;
            this.type = type;
        }

        public Token find(String input, int start, int line, int column) {
            Matcher matcher = pattern.matcher(input);
            matcher.region(start, input.length());
            if (matcher.lookingAt()) {
                return new Token(matcher.group(), type, matcher.start(), matcher.end(), line, column);
            }
            return null;
        }
    }

    public class TokenizerException extends Exception {
        public final int line;
        public final int column;
        public final String message;

        public TokenizerException(int line, int column, String message) {
            this.line = line;
            this.column = column;
            this.message = message;
        }

        @Override
        public String toString() {
            return String.format("TokenizerException(line=%d, column=%d, message=%s)", line, column, message);
        }
    }

    private TokenFinder[] tokenFinders = {
        new TokenFinder(Pattern.compile("^\\("), TokenType.OPEN_PAREN),
        new TokenFinder(Pattern.compile("^\\)"), TokenType.CLOSE_PAREN),
        new TokenFinder(Pattern.compile("^\\{"), TokenType.OPEN_BRACE),
        new TokenFinder(Pattern.compile("^\\}"), TokenType.CLOSE_BRACE),
        new TokenFinder(Pattern.compile("^\"(?:\\\\.|[^\\\\\"])*\""), TokenType.STRING),
        new TokenFinder(Pattern.compile("^[+-]?\\d+(?:\\.\\d+)?"), TokenType.NUMBER),
        new TokenFinder(Pattern.compile("^(?:#t|#f)"), TokenType.BOOLEAN),
        new TokenFinder(Pattern.compile("^[^\\s()\"\\d:{};'#][^\\s()\"':;#]*"), TokenType.SYMBOL),
        new TokenFinder(Pattern.compile("^'"), TokenType.APOSTROPHE),
        new TokenFinder(Pattern.compile("^:"), TokenType.COLON),
        new TokenFinder(Pattern.compile("^;.*"), TokenType.COMMENT),
        new TokenFinder(Pattern.compile("^\\s+"), TokenType.WHITESPACE)
    };

    /**
     * Tokenizes the input string.
     * @param input String to tokenize.
     * @return List of tokens.
     * @throws TokenizerException
     */
    public List<Token> tokenize(String input) throws TokenizerException {
        return tokenize(input, 0, 1, 1);
    }

    /**
     * Tokenizes the input string. Starts tokenizing at the given index, line, and column.
     * Note that line and column do not affect tokenization, but are used for error reporting.
     * @param input String to tokenize.
     * @param start Index to start tokenizing at.
     * @param line Line number to start tokenizing at.
     * @param column Column number to start tokenizing at.
     * @return List of tokens.
     * @throws TokenizerException
     */
    public List<Token> tokenize(String input, int start, int line, int column) throws TokenizerException {
        List<Token> tokens = new ArrayList<Token>();
        int index = start;
        while (index < input.length()) {
            Token token = null;
            for (TokenFinder tokenFinder : tokenFinders) {
                Token candidate = tokenFinder.find(input, index, line, column);
                if(token == null || candidate != null && candidate.length > token.length) {
                    token = candidate;
                }
            }
            if (token == null) {
                throw new TokenizerException(line, column, "Unexpected character: " + input.substring(index, Math.min(index + 10, input.length())));
            }
            index = token.end;
            line += token.value.chars().filter(c -> c == '\n').count();
            column = token.value.length() - token.value.lastIndexOf('\n');
            if (token.type != TokenType.WHITESPACE && token.type != TokenType.COMMENT) {
                tokens.add(token);
            }
        }
        return tokens;
    }
}
