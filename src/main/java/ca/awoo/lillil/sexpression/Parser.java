package ca.awoo.lillil.sexpression;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import ca.awoo.lillil.sexpression.Tokenizer.Token;

public class Parser {
    private List<Token> tokens;
    private List<SExpression> expressions;

    public Parser(String input) throws TokenizerException, ParserException {
        Tokenizer tokenizer = new Tokenizer(input);
        tokens = new ArrayList<Token>();
        expressions = new ArrayList<SExpression>();
        while(tokenizer.hasNext()){
            tokens.add(tokenizer.next());
        }
        while(tokens.size() > 0){
            SExpression next = parseExpression();
            if(next != null)
                expressions.add(next);
        }
    }

    private boolean hasNext(){
        return tokens.size() > 0;
    }

    private Token lookahead(){
        return tokens.get(0);
    }

    private Token next(){
        return tokens.remove(0);
    }

    public List<SExpression> getExpressions(){
        return expressions;
    }

    private SExpression parseExpression() throws ParserException {
        if(!hasNext())
            return null;
        Token next = lookahead();
        SExpression result;
        switch(next.getType()){
            case OPEN_PAREN:
                result = parseList();
                break;
            case OPEN_BRACE:
                result = parseMap();
                break;
            case STRING:
                result = parseString();
                break;
            case SYMBOL:
                result = parseSymbol();
                break;
            case INTEGER:
                result = parseInteger();
                break;
            case LONG:
                result = parseLong();
                break;
            case FLOAT:
                result = parseFloat();
                break;
            case DOUBLE:
                result = parseDouble();
                break;
            case BOOLEAN:
                result = parseBoolean();
                break;
            case APOSTROPHE:
                result = parseQuote();
                break;
            case TILDE:
                result = parseUnquote();
                break;
            case COLON:
                result = parseMapKey();
                break;
            case WHITESPACE:
            case COMMENT:
                //Ignore whitespace
                //Read next to remove it from the list
                next();
                return null;
            default:
                throw new ParserException(next.start, next.line, next.column, next);
        }
        result.position = next.start;
        result.line = next.line;
        result.column = next.column;
        return result;
    }

    private SExpression parseNextExpression() throws ParserException{
        SExpression out = null;
        while(out == null){
            out = parseExpression();
        }
        return out;
    }

    private SExpression parseMap() throws ParserException {
        Token next = next();
        if(next.getType() != Tokenizer.TokenType.OPEN_BRACE)
            throw new ParserException(next.start, next.line, next.column, next, "Expected open brace");
        SList list = new SList();
        list.add(new SSymbol("new-map"));
        while(lookahead().getType() != Tokenizer.TokenType.CLOSE_BRACE){
            SExpression nextExpression = parseNextExpression();
            list.add(nextExpression);
            if(!hasNext())
                throw new ParserException(next.start, next.line, next.column, next, "Expected end of input in map. Perhaps you forgot a close brace?");
        }
        next = next();
        if(next.getType() != Tokenizer.TokenType.CLOSE_BRACE)
            throw new ParserException(next.start, next.line, next.column, next, "Expected close brace, which it was in the lookahead, so this should never happen");
        return new SList(list);
        //The parser can't be responsible for building a map
        /*Map<SMapKey, SExpression> map = new HashMap<SMapKey, SExpression>();
        while(lookahead().getType() != Tokenizer.TokenType.CLOSE_BRACE){
            SExpression keyexp = parseNextExpression();
            if(!(keyexp instanceof SMapKey)){
                throw new ParserException(keyexp.position, keyexp.line, keyexp.column, next, "Expected map key");
            }
            if(!hasNext())
                throw new ParserException(next.start, next.line, next.column, next, "Expected end of input in map. Perhaps you forgot a close brace?");
            SExpression value = parseNextExpression();
            map.put((SMapKey)keyexp, value);
            if(!hasNext())
                throw new ParserException(next.start, next.line, next.column, next, "Expected end of input in map. Perhaps you forgot a close brace?");
        }
        //Consume final closing brace
        next = next();
        if(next.getType() != Tokenizer.TokenType.CLOSE_BRACE)
            throw new ParserException(next.start, next.line, next.column, next, "Expected close brace, which it was in the lookahead, so this should never happen");
        return new SMap(map);*/
    }

    private SExpression parseMapKey() throws ParserException {
        Token next = next();
        if(!(next.getType() == Tokenizer.TokenType.COLON))
            throw new ParserException(next.start, next.line, next.column, next, "Expected colon");
        SExpression key = parseNextExpression();
        if(!(key instanceof SSymbol))
            throw new ParserException(key.position, key.line, key.column, next, "Map key must be a symbol");
        return new SMapKey(key.asSymbol().value);
    }

    private SExpression parseQuote() throws ParserException {
        Token next = next();
        if(!(next.getType() == Tokenizer.TokenType.APOSTROPHE))
            throw new ParserException(next.start, next.line, next.column, next, "Expected apostrophe");
        SExpression quoted = parseNextExpression();
        return new SList(new SSymbol("quote"), quoted);
    }

    private SExpression parseUnquote() throws ParserException {
        Token next = next();
        if(!(next.getType() == Tokenizer.TokenType.TILDE))
            throw new ParserException(next.start, next.line, next.column, next, "Expected tilde");
        SExpression quoted = parseNextExpression();
        return new SList(new SSymbol("eval"), quoted);
    }

    private SExpression parseBoolean() throws ParserException {
        Token next = next();
        if(next.getValue().equals("#t"))
            return new SBoolean(Boolean.TRUE);
        else if(next.getValue().equals("#f"))
            return new SBoolean(Boolean.FALSE);
        else
            throw new ParserException(next.start, next.line, next.column, next, "Unexpected boolean value");
    }

    private SExpression parseFloat() throws ParserException {
        Token next = next();
        try{
            return new SFloat(Float.parseFloat(next.getValue()));
        } catch(NumberFormatException e){
            throw new ParserException(next.start, next.line, next.column, next, "Unable to parse float value");
        }
    }

    private SExpression parseDouble() throws ParserException {
        Token next = next();
        try{
            return new SDouble(Double.parseDouble(next.getValue()));
        } catch(NumberFormatException e){
            throw new ParserException(next.start, next.line, next.column, next, "Unable to parse double value");
        }
    }

    private SExpression parseInteger() throws ParserException {
        Token next = next();
        try{
            return new SInteger(Integer.parseInt(next.getValue()));
        } catch(NumberFormatException e){
            throw new ParserException(next.start, next.line, next.column, next, "Unable to parse integer value");
        }
    }

    private SExpression parseLong() throws ParserException {
        Token next = next();
        try{
            return new SLong(Long.parseLong(next.getValue().substring(0, next.getValue().length() - 1)));
        } catch(NumberFormatException e){
            throw new ParserException(next.start, next.line, next.column, next, "Unable to parse long value");
        }
    }

    private SExpression parseSymbol() throws ParserException {
        Token next = next();
        return new SSymbol(next.getValue());
    }

    private SExpression parseString() throws ParserException {
        Token next = next();
        String value = next.getValue();
        value = value.substring(1, value.length() - 1)
                     .replace("\\\"", "\"")
                     .replace("\\n",  "\n")
                     .replace("\\t",  "\t")
                     .replace("\\\\", "\\")
                     .replace("\\r",  "\r");
        return new SString(value);
    }

    private SExpression parseList() throws ParserException {
        Token next = next();
        if(next.getType() != Tokenizer.TokenType.OPEN_PAREN)
            throw new ParserException(next.start, next.line, next.column, next, "Expected open paren");
        List<SExpression> list = new ArrayList<SExpression>();
        while(lookahead().getType() != Tokenizer.TokenType.CLOSE_PAREN){
            SExpression nextExpression = parseNextExpression();
            list.add(nextExpression);
            if(!hasNext())
                throw new ParserException(next.start, next.line, next.column, next, "Expected end of input in list. Perhaps you forgot a close paren?");
        }
        next = next();
        if(next.getType() != Tokenizer.TokenType.CLOSE_PAREN)
            throw new ParserException(next.start, next.line, next.column, next, "Expected close paren, which it was in the lookahead, so this should never happen");
        return new SList(list);
    }
}
