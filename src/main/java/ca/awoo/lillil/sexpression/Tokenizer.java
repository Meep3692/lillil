package ca.awoo.lillil.sexpression;

import java.util.Arrays;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Tokenizer {
    private String input;
    private int index;
    private int line;
    private int column;

    enum TokenType {
        OPEN_PAREN,
        CLOSE_PAREN,
        STRING,
        SYMBOL,
        INTEGER,
        FLOAT,
        BOOLEAN,
        WHITESPACE,
        APOSTROPHE,
    }

    class Token {
        private TokenType type;
        private String value;
        public int start;
        public int line;
        public int column;

        public Token(TokenType type, String value) {
            this.type = type;
            this.value = value;
        }

        public TokenType getType() {
            return type;
        }

        public String getValue() {
            return value;
        }
    }

    class TokenReader{
        private Pattern regex;
        private TokenType type;

        public TokenReader(TokenType type, String regex) {
            if(regex.charAt(0) != '^')
                regex = "^" + regex;
            this.regex = Pattern.compile(regex);
            this.type = type;
        }

        public Token read(String text){
            Matcher matcher = regex.matcher(text);
            if (matcher.find()) {
                return new Token(type, matcher.group());
            }
            return null;

        }
    }

    private final List<TokenReader> readers = Arrays.asList(
        new TokenReader(TokenType.OPEN_PAREN, "\\("),
        new TokenReader(TokenType.CLOSE_PAREN, "\\)"),
        new TokenReader(TokenType.STRING, "\"(?:[^\"\\\\]|\\\\\"|\\\\)*\""),
        new TokenReader(TokenType.SYMBOL, "[^\\s\\(\\)\"\\d'][^\\s\\(\\)\"]*"),
        new TokenReader(TokenType.INTEGER, "-?\\d+"),
        new TokenReader(TokenType.FLOAT, "-?\\d*\\.\\d+"),
        new TokenReader(TokenType.BOOLEAN, "#[tf]"),
        new TokenReader(TokenType.WHITESPACE, "\\s+"),
        new TokenReader(TokenType.APOSTROPHE, "'")
    );

    public Tokenizer(String input) {
        this.input = input;
        this.index = 0;
    }

    public boolean hasNext() {
        return index < input.length();
    }

    public Token next() throws TokenizerException {
        Token token = lookahead();
        if(token == null) return null;
        index += token.getValue().length();
        for(char c : token.getValue().toCharArray()) {
            if (c == '\n') {
                line++;
                column = 0;
            } else {
                column++;
            }
        }
        return token;

    }

    public Token lookahead() throws TokenizerException{
        if (index >= input.length()) {
            return null;
        }

        Token longest = null;
        for(TokenReader reader : readers) {
            Token token = reader.read(input.substring(index));
            if (token != null){
                if(longest == null || token.getValue().length() > longest.getValue().length()) {
                    longest = token;
                }
            }
        }
        if(longest == null) throw new TokenizerException(index, line, column, input.substring(index, Math.min(index + 25, input.length())));
        longest.start = index;
        longest.line = line;
        longest.column = column;
        return longest;
    }
}
