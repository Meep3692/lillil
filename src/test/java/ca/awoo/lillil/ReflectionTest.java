package ca.awoo.lillil;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

import ca.awoo.lillil.sexpr.Parser.ParseException;
import ca.awoo.lillil.sexpr.Tokenizer.TokenizerException;

public class ReflectionTest {

    private class Thing {
        public double wahoo = 0;

        public void doThing(double x){
            wahoo += x;
        }
    }

    @Test
    public void testReflection() throws ParseException, TokenizerException, LillilRuntimeException{
        Lillil lillil = new Lillil();
        lillil.useCoreModule();
        Thing thing = new Thing();
        lillil.bindPersist("thing", thing);
        lillil.evalAll("((:doThing thing) 5)((:doThing thing) (:wahoo thing))");
        assertEquals("Value not set correctly", 10.0, thing.wahoo, 0.01);
    }
}
