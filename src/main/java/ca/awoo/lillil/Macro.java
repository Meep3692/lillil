package ca.awoo.lillil;

/**
 * A macro callable from lillil.
 * Macros are like functions, but they are called before the arguments are evaluated.
 */
public interface Macro {
    /**
     * Apply the macro to the given arguments.
     * Called by the lillil evaluator.
     * @param env The environment in which the macro is being called.
     * @param args The arguments to the macro.
     * @return The result of the macro.
     * @throws LillilRuntimeException If the macro throws an exception.
     */
    public Object apply(Environment env, Object... args) throws LillilRuntimeException;
}
