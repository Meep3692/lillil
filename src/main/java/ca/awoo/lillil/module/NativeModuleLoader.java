package ca.awoo.lillil.module;

import java.util.HashMap;
import java.util.Map;

import ca.awoo.lillil.Lillil;

/**
 * A module loader for native modules, consisting of java code.
 * To use this, create a class that extends from it, and in the constructor add all the functions you want to export to the module map.
 * Then, add an instance of your class to the lillil environment with {@link Lillil#addModuleLoader(ModuleLoader)}.
 * You can also add the bindings directly to the lillil base binding using {@link Lillil#useNativeModule(NativeModuleLoader)}
 * @see ModuleLoader
 * @see Lillil#addModuleLoader(ModuleLoader)
 * @see Lillil#useNativeModule(NativeModuleLoader)
 */
public abstract class NativeModuleLoader extends  ModuleLoader {
    /**
     * The module map. Contains all the bindings in this module
     */
    protected Map<String, Object> module = new HashMap<>();
    /**
     * The name of the module. This is the name that will be used to import the module.
     */
    protected final String name;

    public NativeModuleLoader(Lillil lillil, String name){
        super(lillil);
        this.name = name;
    }

    @Override
    public Map<String, Object> loadModule(String module) {
        if(module.equals(name)){
            return this.module;
        }else{
            return null;
        }
    }

    public String getName() {
        return name;
    }


}
