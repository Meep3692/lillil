package ca.awoo.lillil;

import java.io.IOException;

import org.junit.Test;

import ca.awoo.lillil.sexpr.Parser.ParseException;
import ca.awoo.lillil.sexpr.Tokenizer.TokenizerException;

public class LillilTest {
    @Test
    public void mathTest() throws ParseException, TokenizerException, LillilRuntimeException, IOException{
        Lillil lillil = new Lillil();
        lillil.useCoreModule();
        lillil.addModuleLoader(new TestNativeModuleLoader(lillil));
        lillil.evalAll(LillilTest.class.getResourceAsStream("/math.lil"));
    }

    @Test
    public void comparisonTest() throws ParseException, TokenizerException, LillilRuntimeException, IOException{
        Lillil lillil = new Lillil();
        lillil.useCoreModule();
        lillil.addModuleLoader(new TestNativeModuleLoader(lillil));
        lillil.evalAll(LillilTest.class.getResourceAsStream("/comparison.lil"));
    }

    @Test
    public void listTest() throws ParseException, TokenizerException, LillilRuntimeException, IOException{
        Lillil lillil = new Lillil();
        lillil.useCoreModule();
        lillil.addModuleLoader(new TestNativeModuleLoader(lillil));
        lillil.evalAll(LillilTest.class.getResourceAsStream("/list.lil"));
    }

    @Test
    public void typeChecksTest() throws ParseException, TokenizerException, LillilRuntimeException, IOException {
        Lillil lillil = new Lillil();
        lillil.useCoreModule();
        lillil.addModuleLoader(new TestNativeModuleLoader(lillil));
        lillil.evalAll(LillilTest.class.getResourceAsStream("/type-checks.lil"));
    }
}
