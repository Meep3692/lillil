package ca.awoo.lillil.module;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Map;

import ca.awoo.lillil.Lillil;
import ca.awoo.lillil.LillilRuntimeException;
import ca.awoo.lillil.sexpr.Parser.ParseException;
import ca.awoo.lillil.sexpr.Tokenizer.TokenizerException;

/**
 * Loads modules from a directory.
 */
public class DirectoryModuleLoader extends ModuleLoader {

    private String path;

    /**
     * Creates a new directory module loader.
     * @param lillil The lillil environment from which modules will be loaded.
     * @param path The path to the directory from which modules will be loaded.
     */
    public DirectoryModuleLoader(Lillil lillil, String path) {
        super(lillil);
        this.path = path;
    }

    @Override
    public Map<String, Object> loadModule(String module) throws ParseException, TokenizerException, LillilRuntimeException {
        module = module.replace(".", "/");
        File file = new File(path + "/" + module + ".lil");
        if (file.exists()) {
            try {
                List<Object> results = lillil.evalAllInCleanEnv(file);
                Object last = results.get(results.size() - 1);
                if (last instanceof Map) {
                    return (Map<String, Object>) last;
                } else {
                    throw new LillilRuntimeException("Error while loading module " + module + ": module does not return a map");
                }
            } catch (IOException e) {
                throw new LillilRuntimeException("IO error while loading module " + module, e);
            }
        } else {
            return null;
        }
    }
    
}
