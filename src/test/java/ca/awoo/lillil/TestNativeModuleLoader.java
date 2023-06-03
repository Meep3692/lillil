package ca.awoo.lillil;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import ca.awoo.lillil.module.NativeModuleLoader;

public class TestNativeModuleLoader extends NativeModuleLoader {

    public TestNativeModuleLoader(Lillil lillil) {
        super(lillil, "test");
        module.put("assert-true", new Function() {
            @Override
            public Object apply(Object... args) throws LillilRuntimeException {
                assertTrue((boolean)args[0]);
                return null;
            }
        });
        module.put("assert-false", new Function() {
            @Override
            public Object apply(Object... args) throws LillilRuntimeException {
                assertTrue(!(boolean)args[0]);
                return null;
            }
        });
        module.put("assert-equals", new Function() {
            @Override
            public Object apply(Object... args) throws LillilRuntimeException {
                Object arg = args[0];
                for (int i = 1; i < args.length; i++) {
                    assertEquals(arg, args[i]);
                }
                return null;
            }
        });
    }
    
}
