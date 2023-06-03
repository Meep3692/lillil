package ca.awoo.lillil.module;

import java.util.Map;

import ca.awoo.lillil.Lillil;
import ca.awoo.lillil.LillilRuntimeException;
import ca.awoo.lillil.sexpr.Parser.ParseException;
import ca.awoo.lillil.sexpr.Tokenizer.TokenizerException;

/**
 * A module loader is responsible for loading modules.
 */
public abstract class ModuleLoader {

    protected Lillil lillil;

    /**
     * Creates a new module loader.
     * @param lillil The lillil environment from which modules will be loaded.
     */
    public ModuleLoader(Lillil lillil) {
        this.lillil = lillil;
    }

    /**
     * Loads a module.
     * @param module The name of the module to load.
     * @return The module, or null if it is not found.
     * @throws LillilRuntimeException If an error occurs while loading the module.
     * @throws ParseException If an error occurs while parsing the module.
     * @throws TokenizerException If an error occurs while tokenizing the module.
     */
    public abstract Map<String, Object> loadModule(String module) throws LillilRuntimeException, ParseException, TokenizerException;
}
