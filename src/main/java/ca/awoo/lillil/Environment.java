package ca.awoo.lillil;

import java.util.HashMap;
import java.util.Map;

/**
 * An environment for lillil.
 * Contains bindings from symbols to values.
 * Can be chained to parent environments.
 */
public class Environment {
    private final Map<String, Object> bindings = new HashMap<>();
    private final Environment parent;

    /**
     * Creates a new environment with no parent.
     */
    public Environment() {
        this(null);
    }

    /**
     * Creates a new environment with the given parent.
     * Any symbols not found in this environment will be looked up in the parent.
     * @param parent The parent environment.
     */
    public Environment(Environment parent) {
        this.parent = parent;
    }

    /**
     * Binds a symbol to a value in this environment.
     * @param key The symbol to bind.
     * @param value The value to bind it to.
     */
    public void bind(String key, Object value) {
        bindings.put(key, value);
    }

    /**
     * Looks up a symbol in this environment.
     * If it is not found, it will be looked up in the parent environment, which may look it up in its parent, and so on.
     * @param key The symbol to look up.
     * @return The value bound to the symbol, or null if it is not found.
     */
    public Object lookup(String key) {
        Object value = bindings.get(key);
        if (value == null && parent != null) {
            return parent.lookup(key);
        }
        return value;
    }
}
