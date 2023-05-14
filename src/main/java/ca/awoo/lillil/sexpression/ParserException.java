package ca.awoo.lillil.sexpression;

import ca.awoo.lillil.sexpression.Tokenizer.Token;

public class ParserException extends Exception {
    public int position;
    public int line;
    public int column;
    public Token nextToken;

    public ParserException(int position, int line, int column, Token nextToken) {
        this(position, line, column, nextToken, "");
    }

    public ParserException(int position, int line, int column, Token nextToken, String message) {
        super("ParserException at line " + line + ", column " + column + ": " + nextToken.toString() + " " + message);
        this.position = position;
        this.line = line;
        this.column = column;
        this.nextToken = nextToken;
    }
}
