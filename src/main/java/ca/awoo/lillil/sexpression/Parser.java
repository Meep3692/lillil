package ca.awoo.lillil.sexpression;

import java.util.ArrayList;
import java.util.List;

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

    private SExpression parseExpression() throws TokenizerException, ParserException {
        if(!hasNext())
            return null;
        Token next = lookahead();
        switch(next.getType()){
            case OPEN_PAREN:
                return parseList();
            case STRING:
                return parseString();
            case SYMBOL:
                return parseSymbol();
            case INTEGER:
                return parseInteger();
            case FLOAT:
                return parseFloat();
            case BOOLEAN:
                return parseBoolean();
            case WHITESPACE:
                //Ignore whitespace
                //Read next to remove it from the list
                next();
                return null;
            default:
                throw new ParserException(next.start, next.line, next.column, next);
        }
    }

    private SExpression parseBoolean() throws TokenizerException, ParserException {
        Token next = next();
        if(next.getValue().equals("#t"))
            return new SBoolean(true);
        else if(next.getValue().equals("#f"))
            return new SBoolean(false);
        else
            throw new ParserException(next.start, next.line, next.column, next, "Unexpected boolean value");
    }

    private SExpression parseFloat() throws TokenizerException, ParserException {
        Token next = next();
        try{
            return new SFloat(Float.parseFloat(next.getValue()));
        } catch(NumberFormatException e){
            throw new ParserException(next.start, next.line, next.column, next, "Unexpected float value");
        }
    }

    private SExpression parseInteger() throws TokenizerException, ParserException {
        Token next = next();
        try{
            return new SInteger(Integer.parseInt(next.getValue()));
        } catch(NumberFormatException e){
            throw new ParserException(next.start, next.line, next.column, next, "Unexpected integer value");
        }
    }

    private SExpression parseSymbol() throws TokenizerException, ParserException {
        Token next = next();
        return new SSymbol(next.getValue());
    }

    private SExpression parseString() throws TokenizerException, ParserException {
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

    private SExpression parseList() throws TokenizerException, ParserException {
        Token next = next();
        if(next.getType() != Tokenizer.TokenType.OPEN_PAREN)
            throw new ParserException(next.start, next.line, next.column, next, "Expected open paren");
        List<SExpression> list = new ArrayList<SExpression>();
        while(lookahead().getType() != Tokenizer.TokenType.CLOSE_PAREN){
            SExpression nextExpression = parseExpression();
            if(nextExpression != null)
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
