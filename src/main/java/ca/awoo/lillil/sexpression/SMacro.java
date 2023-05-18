package ca.awoo.lillil.sexpression;

import ca.awoo.lillil.Environment;
import ca.awoo.lillil.LillilRuntimeArityException;
import ca.awoo.lillil.LillilRuntimeException;
import ca.awoo.lillil.LillilRuntimeInvalidArgumentException;

public abstract class SMacro extends SExpression {
    public abstract SExpression apply(Environment env, SExpression ... args) throws LillilRuntimeException;

    public int hashCode() {
        return this.getClass().hashCode();
    }

    protected void assertArity(String fname, int expected, int got, boolean varargs) throws LillilRuntimeArityException{
        if(got < expected || (!varargs && got > expected)){
            throw new LillilRuntimeArityException(this, fname, expected, got, varargs);
        }
    }

    protected <T> T assertArgType(SExpression arg, Class<T> expectedType) throws LillilRuntimeException{
        if(!expectedType.isInstance(arg)){
            throw new LillilRuntimeInvalidArgumentException(arg, expectedType);
        }else{
            return expectedType.cast(arg);
        }
    }
}
