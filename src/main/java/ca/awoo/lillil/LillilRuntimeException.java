package ca.awoo.lillil;

import ca.awoo.lillil.sexpression.SExpression;

public class LillilRuntimeException extends Exception {
    public int position;
    public int line;
    public int column;
    public SExpression sexpr;

    public LillilRuntimeException(int position, int line, int column, SExpression sexpr) {
        this(position, line, column, sexpr, "");
    }

    public LillilRuntimeException(int position, int line, int column, SExpression sexpr, String message) {
        super("RuntimeException at line " + line + ", column " + column + ": " + sexpr.toString() + " " + message);
        this.position = position;
        this.line = line;
        this.column = column;
        this.sexpr = sexpr;
    }
    
    public LillilRuntimeException(SExpression sexpr, String message){
        this(sexpr.position, sexpr.line, sexpr.column, sexpr, message);
    }

    public LillilRuntimeException(SExpression sexpr){
        this(sexpr, "");
    }
}
