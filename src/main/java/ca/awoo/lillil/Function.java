package ca.awoo.lillil;

/**
 * A function callable from lillil.
 */
public interface Function {
    /**
     * Apply the function to the given arguments.
     * Called by the lillil evaluator.
     * @param args The arguments to the function.
     * @return The result of the function.
     * @throws LillilRuntimeException If the function throws an exception.
     */
    public Object apply(Object... args) throws LillilRuntimeException;
}
