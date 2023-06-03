package ca.awoo.lillil.module;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;

import ca.awoo.lillil.Lillil;

/**
 * Loads modules from a directory.
 */
public class DirectoryModuleLoader extends StreamModuleLoader {

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
    protected InputStream openStream(String module) {
        module = module.replace(".", "/");
        File file = new File(path + "/" + module + ".lil");
        if (file.exists()) {
            try {
                return new FileInputStream(file);
            } catch (FileNotFoundException e) {
                //This should never happen
                e.printStackTrace();
                throw new RuntimeException(e);
            }
        } else {
            return null;
        }
    }
    
}
