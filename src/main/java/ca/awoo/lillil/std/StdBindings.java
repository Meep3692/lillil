package ca.awoo.lillil.std;

import java.util.Map;

import ca.awoo.lillil.sexpression.SExpression;
import ca.awoo.lillil.std.lang.*;
import ca.awoo.lillil.std.math.*;

public class StdBindings {
    public static Map<String, SExpression> bindings = new java.util.HashMap<String, SExpression>(){{
        put("+", new AddFunction());
        put("-", new SubFunction());
        put("*", new MulFunction());
        put("/", new DivFunction());
        put("quote", new QuoteMacro());
    }};
}
