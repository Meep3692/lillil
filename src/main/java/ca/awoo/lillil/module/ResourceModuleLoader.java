package ca.awoo.lillil.module;

import java.io.InputStream;

import ca.awoo.lillil.Lillil;

public class ResourceModuleLoader extends StreamModuleLoader {

    public ResourceModuleLoader(Lillil lillil) {
        super(lillil);
    }

    @Override
    protected InputStream openStream(String module) {
        return getClass().getResourceAsStream("/" + module.replace(".", "/") + ".lil");
    }
    
}
