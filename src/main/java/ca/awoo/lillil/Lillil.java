package ca.awoo.lillil;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import ca.awoo.lillil.module.CoreModuleLoader;
import ca.awoo.lillil.module.DirectoryModuleLoader;
import ca.awoo.lillil.module.ModuleLoader;
import ca.awoo.lillil.module.NativeModuleLoader;
import ca.awoo.lillil.sexpr.Parser.ParseException;
import ca.awoo.lillil.sexpr.Tokenizer.TokenizerException;

/**
 * Represent a full lillil execution environment.
 * This is the main class to use to evaluate lillil code.
 * You may use Evaluator directly, but you will not have access to module loading.
 * There are 2 environments in a lillil environment: the base environment and the persistent environment.
 * The base environment is the environment in which all code is executed, including modules.
 * The persistent environment is the environment in which use code should be executed.
 * The persistent environment is a child of the base environment, so all bindings in the base environment are accessable from the persistent environment.
 * The persistent environment is used to store bindings that should not be accessable from modules, but should be accessable from subsequent calls to eval.
 */
public class Lillil {
    
    private final List<ModuleLoader> moduleLoaders = new ArrayList<>();
    private Environment baseEnvironment;
    private Environment persistEnvironment;
    private Evaluator evaluator;

    /**
     * Creates a new lillil environment.
     */
    public Lillil(){
        baseEnvironment = new Environment();
        persistEnvironment = new Environment(baseEnvironment);
        evaluator = new Evaluator();
    }

    /**
     * Creates a new lillil environment with the given base environment.
     * @param base The base environment.
     */
    public Lillil(Environment base){
        baseEnvironment = base;
        persistEnvironment = new Environment(baseEnvironment);
        evaluator = new Evaluator();
    }

    /**
     * Bind a module to the base environment.
     * Keep in mind that the contents of this module will be accessable by all code executed in this environment including modules.
     * @param module The module to bind.
     */
    public void useModule(Map<String, Object> module){
        for (String key : module.keySet()) {
            baseEnvironment.bind(key, module.get(key));
        }
    }

    /**
     * Bind a native module to the base environment.
     * Keep in mind that the contents of this module will be accessable by all code executed in this environment including modules.
     * @param module The module to bind.
     */
    public void useNativeModule(NativeModuleLoader module){
        useModule(module.loadModule(module.getName()));
    }

    /**
     * Bind the core module to the base environment.
     */
    public void useCoreModule(){
        useNativeModule(new CoreModuleLoader(this));
    }

    /**
     * Bind a value to a name in the base environment.
     * Keep in mind that this binding will be accessable from all code executed in this environment including modules.
     * @param name
     * @param value
     */
    public void bindBase(String name, Object value){
        baseEnvironment.bind(name, value);
    }

    /**
     * Lookup a name in the base environment.
     * @param name The name to lookup.
     * @return The value bound to the name, or null if it is not found.
     */
    public Object lookupBase(String name){
        return baseEnvironment.lookup(name);
    }

    /**
     * Bind a value to a name in the persistent environment.
     * Keep in mind that this binding will be accessable from all code executed in this environment including modules.
     * @param name
     * @param value
     */
    public void bindPersist(String name, Object value){
        persistEnvironment.bind(name, value);
    }

    /**
     * Lookup a name in the persistent environment.
     * @param name The name to lookup.
     * @return The value bound to the name, or null if it is not found.
     */
    public Object lookupPersist(String name){
        return persistEnvironment.lookup(name);
    }

    /**
     * Evaluate some code.
     * @param code The code to evaluate.
     * @return The result of the evaluation.
     * @throws ParseException If an error occurs while parsing the code.
     * @throws TokenizerException If an error occurs while tokenizing the code.
     * @throws LillilRuntimeException If an error occurs while evaluating the code.
     */
    public Object eval(String code) throws ParseException, TokenizerException, LillilRuntimeException{
        return evaluator.eval(code, persistEnvironment);
    }

    /**
     * Evaluate some code in a clean environment.
     * This means that the code will have access only to the base bindings and not the persistent bindings.
     * This should be used for evaluating module code.
     * @param code The code to evaluate.
     * @return The result of the evaluation.
     * @throws ParseException If an error occurs while parsing the code.
     * @throws TokenizerException If an error occurs while tokenizing the code.
     * @throws LillilRuntimeException If an error occurs while evaluating the code.
     */
    public Object evalInCleanEnv(String code) throws ParseException, TokenizerException, LillilRuntimeException {
        Environment env = new Environment(baseEnvironment);
        return evaluator.eval(code, env);
    }

    /**
     * Evaluate some code.
     * @param code The code to evaluate.
     * @return The result of the evaluation.
     * @throws LillilRuntimeException If an error occurs while evaluating the code.
     */
    public Object eval(Object code) throws LillilRuntimeException{
        return evaluator.eval(code, persistEnvironment);
    }

    /**
     * Evaluate some code in a clean environment.
     * This means that the code will have access only to the base bindings and not the persistent bindings.
     * This should be used for evaluating module code.
     * @param code The code to evaluate.
     * @return The result of the evaluation.
     * @throws LillilRuntimeException If an error occurs while evaluating the code.
     */
    public Object evalInCleanEnv(Object code) throws LillilRuntimeException {
        Environment env = new Environment(baseEnvironment);
        return evaluator.eval(code, env);
    }

    /**
     * Evaluate some code.
     * @param code The code to evaluate.
     * @return The result of the evaluation.
     * @throws ParseException If an error occurs while parsing the code.
     * @throws TokenizerException If an error occurs while tokenizing the code.
     * @throws LillilRuntimeException If an error occurs while evaluating the code.
     */
    public List<Object> evalAll(String code) throws ParseException, TokenizerException, LillilRuntimeException{
        return evaluator.evalAll(code, persistEnvironment);
    }

    /**
     * Evaluate some code in a clean environment.
     * This means that the code will have access only to the base bindings and not the persistent bindings.
     * This should be used for evaluating module code.
     * @param code The code to evaluate.
     * @return The result of the evaluation.
     * @throws ParseException If an error occurs while parsing the code.
     * @throws TokenizerException If an error occurs while tokenizing the code.
     * @throws LillilRuntimeException If an error occurs while evaluating the code.
     */
    public List<Object> evalAllInCleanEnv(String code) throws ParseException, TokenizerException, LillilRuntimeException {
        Environment env = new Environment(baseEnvironment);
        return evaluator.evalAll(code, env);
    }

    private String readAllFile(File file) throws IOException {
        StringBuilder sb = new StringBuilder();
        BufferedReader br = new BufferedReader(new FileReader(file));
        for(String line = br.readLine(); line != null; line = br.readLine()){
            sb.append(line);
            sb.append("\n");
        }
        br.close();
        return sb.toString();
    }

    /**
     * Evaluate some code.
     * @param file The file containing the code to evaluate.
     * @return The result of the evaluation.
     * @throws ParseException If an error occurs while parsing the code.
     * @throws TokenizerException If an error occurs while tokenizing the code.
     * @throws LillilRuntimeException If an error occurs while evaluating the code.
     * @throws IOException If an error occurs while reading the file.
     */
    public List<Object> evalAll(File file) throws ParseException, TokenizerException, LillilRuntimeException, IOException{
        String code = readAllFile(file);
        return evalAll(code);
    }

    /**
     * Evaluate some code in a clean environment.
     * This means that the code will have access only to the base bindings and not the persistent bindings.
     * This should be used for evaluating module code.
     * @param file The file containing the code to evaluate.
     * @return The result of the evaluation.
     * @throws ParseException If an error occurs while parsing the code.
     * @throws TokenizerException If an error occurs while tokenizing the code.
     * @throws LillilRuntimeException If an error occurs while evaluating the code.
     * @throws IOException If an error occurs while reading the file.
     */
    public List<Object> evalAllInCleanEnv(File file) throws ParseException, TokenizerException, LillilRuntimeException, IOException {
        String code = readAllFile(file);
        return evalAllInCleanEnv(code);
    }

    /**
     * Adds a module loader to this environment. This module loader will be accessable by all code executed in this environment including modules.
     * @param loader The module loader to add.
     */
    public void addModuleLoader(ModuleLoader loader) {
        moduleLoaders.add(loader);
    }

    /**
     * Adds a directory module loader to this environment. This module loader will be accessable by all code executed in this environment including modules.
     * @param path The path to the directory to load modules from.
     */
    public void addModuleFolder(String path){
        addModuleLoader(new DirectoryModuleLoader(this, path));
    }

    /**
     * Get a module from this environment.
     * @param module The name of the module to get.
     * @throws TokenizerException If an error occurs while tokenizing the module.
     * @throws ParseException If an error occurs while parsing the module.
     * @throws LillilRuntimeException If an error occurs while evaluating the module.
     */
    public Map<String, Object> getModule(String module) throws LillilRuntimeException, ParseException, TokenizerException {
        for (ModuleLoader loader : moduleLoaders) {
            Map<String, Object> result = loader.loadModule(module);
            if (result != null) {
                return result;
            }
        }
        return null;
    }
}
