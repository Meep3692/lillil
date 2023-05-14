package ca.awoo.lillil.sexpression;

import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;

public abstract class SExpression implements List<SExpression>{

    public int position;
    public int line;
    public int column;

    public SBoolean asBoolean() {
        return (SBoolean)this;
    }

    public SFloat asFloat() {
        return (SFloat)this;
    }

    public SInteger asInteger() {
        return (SInteger)this;
    }

    public SList asList() {
        return (SList)this;
    }

    public SString asString() {
        return (SString)this;
    }

    public SSymbol asSymbol() {
        return (SSymbol)this;
    }

    public boolean isBoolean() {
        return this instanceof SBoolean;
    }

    public boolean isFloat() {
        return this instanceof SFloat;
    }

    public boolean isInteger() {
        return this instanceof SInteger;
    }

    public boolean isList() {
        return this instanceof SList;
    }

    public boolean isString() {
        return this instanceof SString;
    }

    public boolean isSymbol() {
        return this instanceof SSymbol;
    }

    public boolean isAtom() {
        return isBoolean() || isFloat() || isInteger() || isString() || isSymbol();
    }

    public boolean booleanValue() {
        return asBoolean().value;
    }

    public float floatValue() {
        return asFloat().value;
    }

    public int integerValue() {
        return asInteger().value;
    }

    public String stringValue() {
        return asString().value;
    }

    public String symbolValue() {
        return asSymbol().value;
    }

    @Override
    public Iterator<SExpression> iterator() {
        return asList().iterator();
    }

    @Override
    public int size() {
        return asList().size();
    }

    @Override
    public boolean isEmpty() {
        return asList().isEmpty();
    }

    @Override
    public boolean contains(Object o) {
        return asList().contains(o);
    }

    @Override
    public Object[] toArray() {
        return asList().toArray();
    }

    @Override
    public <T> T[] toArray(T[] a) {
        return asList().toArray(a);
    }

    @Override
    public boolean add(SExpression e) {
        return asList().add(e);
    }

    @Override
    public boolean remove(Object o) {
        return asList().remove(o);
    }

    @Override
    public boolean containsAll(Collection<?> c) {
        return asList().containsAll(c);
    }

    @Override
    public boolean addAll(Collection<? extends SExpression> c) {
        return asList().addAll(c);
    }

    @Override
    public boolean removeAll(Collection<?> c) {
        return asList().removeAll(c);
    }

    @Override
    public boolean retainAll(Collection<?> c) {
        return asList().retainAll(c);
    }

    @Override
    public void clear() {
        asList().clear();
    }

    @Override
    public boolean addAll(int index, Collection<? extends SExpression> c) {
        return asList().addAll(index, c);
    }

    @Override
    public SExpression get(int index) {
        return asList().get(index);
    }

    @Override
    public SExpression set(int index, SExpression element) {
        return asList().set(index, element);
    }

    @Override
    public void add(int index, SExpression element) {
        asList().add(index, element);
    }

    @Override
    public SExpression remove(int index) {
        return asList().remove(index);
    }

    @Override
    public int indexOf(Object o) {
        return asList().indexOf(o);
    }

    @Override
    public int lastIndexOf(Object o) {
        return asList().lastIndexOf(o);
    }

    @Override
    public ListIterator<SExpression> listIterator() {
        return asList().listIterator();
    }

    @Override
    public ListIterator<SExpression> listIterator(int index) {
        return asList().listIterator(index);
    }

    @Override
    public List<SExpression> subList(int fromIndex, int toIndex) {
        return asList().subList(fromIndex, toIndex);
    }
}
