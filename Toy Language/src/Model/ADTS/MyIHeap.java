package Model.ADTS;

import Model.Values.Value;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

public interface MyIHeap<K, V> {
    public void add(V val) throws ADTException;

    public void update(K key, V val) throws ADTException;

    public void remove(K key) throws ADTException;

    public Value get(K key) throws ADTException;

    public boolean isEmpty();

    public Map<K,V> getContents();

    public void setContents(Map<K, V> newdict);

    public boolean isDefined(K id);

    public int getFirstEmpty();

    public Set<Map.Entry<K, V>> getAll();

    public Set<K> getKeys();
}
