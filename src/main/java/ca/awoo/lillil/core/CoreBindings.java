package ca.awoo.lillil.core;

import java.util.Map;

import ca.awoo.lillil.core.lang.*;
import ca.awoo.lillil.core.math.*;
import ca.awoo.lillil.sexpression.SExpression;

public class CoreBindings {
    public static Map<String, SExpression> bindings = new java.util.HashMap<String, SExpression>(){{
        put("+", new AddFunction());
        put("-", new SubFunction());
        put("*", new MulFunction());
        put("/", new DivFunction());
        put("quote", new QuoteMacro());
        put("lambda", new LambdaMacro());
        put("macro", new MacroMacro());
        put("eval", new EvalMacro());
        put("if", new IfMacro());
        put("define", new DefineMacro());
    }};
}
