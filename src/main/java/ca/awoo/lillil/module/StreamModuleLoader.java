package ca.awoo.lillil.module;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.Map;

import ca.awoo.lillil.Lillil;
import ca.awoo.lillil.LillilRuntimeException;
import ca.awoo.lillil.sexpr.Parser.ParseException;
import ca.awoo.lillil.sexpr.Tokenizer.TokenizerException;

/**
 * A base for module loaders that load modules from streams.
 */
public abstract class StreamModuleLoader extends ModuleLoader{

    public StreamModuleLoader(Lillil lillil) {
        super(lillil);
    }

    @Override
    public Map<String, Object> loadModule(String module) throws LillilRuntimeException, ParseException, TokenizerException {
        InputStream is = openStream(module);
        if (is == null) {
            return null;
        }
        try {
            List<Object> results = lillil.evalAllInCleanEnv(is);
            Object last = results.get(results.size() - 1);
            if (last instanceof Map) {
                return (Map<String, Object>) last;
            } else {
                throw new LillilRuntimeException("Error while loading module " + module + ": module does not return a map");
            }
        } catch (IOException e) {
            throw new LillilRuntimeException("IO error while loading module " + module, e);
        }
    }

    /**
     * Opens a stream to the module with the given name.
     * @param module The name of the module to open a stream to.
     * @return A stream to the module, or null if the module does not exist.
     */
    protected abstract InputStream openStream(String module);
    
}
