package ca.awoo.lillil.sexpression;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class SMap extends SExpression implements Map<SMapKey, SExpression>{
    public Map<SMapKey, SExpression> value;

    public SMap(Map<SMapKey, SExpression> value){
        this.value = value;
    }

    public SMap(){
        this.value = new HashMap<SMapKey, SExpression>();
    }

    public int hashCode(){
        return value.hashCode();
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
    public boolean containsKey(Object key) {
        return value.containsKey(key);
    }

    @Override
    public boolean containsValue(Object value) {
        return this.value.containsValue(value);
    }

    @Override
    public SExpression get(Object key) {
        return value.get(key);
    }

    @Override
    public SExpression put(SMapKey key, SExpression value) {
        return this.value.put(key, value);
    }

    @Override
    public SExpression remove(Object key) {
        return value.remove(key);
    }

    @Override
    public void putAll(Map<? extends SMapKey, ? extends SExpression> m) {
        value.putAll(m);
    }

    @Override
    public void clear() {
        value.clear();
    }

    @Override
    public Set<SMapKey> keySet() {
        return value.keySet();
    }

    @Override
    public Collection<SExpression> values() {
        return value.values();
    }

    @Override
    public Set<Entry<SMapKey, SExpression>> entrySet() {
        return value.entrySet();
    }
}
