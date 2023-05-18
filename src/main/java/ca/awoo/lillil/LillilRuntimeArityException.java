package ca.awoo.lillil;

import ca.awoo.lillil.sexpression.SExpression;

public class LillilRuntimeArityException extends LillilRuntimeException{
    public LillilRuntimeArityException(SExpression sexpr, String fname, int expected, int got, boolean varargs){
        super(sexpr, "Invalid arity: " + fname + " requires " + (varargs ? "at least " : "") + expected + " arguments, got " + got);
    }
}
