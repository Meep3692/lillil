package ca.awoo.lillil.sexpression;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;

public class SList extends SExpression implements List<SExpression> {
    public List<SExpression> value;

    public SList(List<SExpression> value) {
        this.value = value;
    }

    public SList(SExpression ... args) {
        this.value = Arrays.asList(args);
    }

    public SList(){
        this.value = new ArrayList<SExpression>();
    }

    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("(");
        for (SExpression sexpr : value) {
            sb.append(sexpr.toString());
            sb.append(" ");
        }
        if(value.size() > 0)
            sb.deleteCharAt(sb.length() - 1);
        sb.append(")");
        return sb.toString();
    }

    @Override
    public Iterator<SExpression> iterator() {
        return value.iterator();
    }

    @Override
    public int size() {
        return value.size();
    }

    @Override
    public boolean isEmpty() {
        return value.isEmpty();
    }

    @Override
    public boolean contains(Object o) {
        return value.contains(o);
    }

    @Override
    public Object[] toArray() {
        return value.toArray();
    }

    @Override
    public <T> T[] toArray(T[] a) {
        return value.toArray(a);
    }

    @Override
    public boolean add(SExpression e) {
        return value.add(e);
    }

    @Override
    public boolean remove(Object o) {
        return value.remove(o);
    }

    @Override
    public boolean containsAll(Collection<?> c) {
        return value.containsAll(c);
    }

    @Override
    public boolean addAll(Collection<? extends SExpression> c) {
        return value.addAll(c);
    }

    @Override
    public boolean removeAll(Collection<?> c) {
        return value.removeAll(c);
    }

    @Override
    public boolean retainAll(Collection<?> c) {
        return value.retainAll(c);
    }

    @Override
    public void clear() {
        value.clear();
    }

    @Override
    public boolean addAll(int index, Collection<? extends SExpression> c) {
        return value.addAll(index, c);
    }

    @Override
    public SExpression get(int index) {
        return value.get(index);
    }

    @Override
    public SExpression set(int index, SExpression element) {
        return value.set(index, element);
    }

    @Override
    public void add(int index, SExpression element) {
        value.add(index, element);
    }

    @Override
    public SExpression remove(int index) {
        return value.remove(index);
    }

    @Override
    public int indexOf(Object o) {
        return value.indexOf(o);
    }

    @Override
    public int lastIndexOf(Object o) {
        return value.lastIndexOf(o);
    }

    @Override
    public ListIterator<SExpression> listIterator() {
        return value.listIterator();
    }

    @Override
    public ListIterator<SExpression> listIterator(int index) {
        return value.listIterator(index);
    }

    @Override
    public List<SExpression> subList(int fromIndex, int toIndex) {
        return value.subList(fromIndex, toIndex);
    }

    public boolean equals(Object o) {
        if (o instanceof SList) {
            return value.equals(((SList)o).value);
        }
        return false;
    }

    public int hashCode() {
        return value.hashCode();
    }
}
