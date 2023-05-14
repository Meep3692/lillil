package ca.awoo.lillil.sexpression;

public class TokenizerException extends Exception{
    public int position;
    public int line;
    public int column;
    public String nextChars;

    public TokenizerException(int position, int line, int column, String nextChars) {
        super("TokenizerException at line " + line + ", column " + column + ": " + nextChars);
        this.position = position;
        this.line = line;
        this.column = column;
        this.nextChars = nextChars;
    }
    
}
