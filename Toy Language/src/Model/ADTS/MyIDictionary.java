package Model.ADTS;

import Model.MyException;

import java.util.*;

public interface MyIDictionary<K,V> {
    void add(K key,V val) throws ADTException;
    void remove(K key) throws ADTException;
    void update(K key,V val) throws ADTException;
    V get(K key) throws ADTException;
    boolean isDefined(K id);
    public Map<K,V> getContents();
    public void setContents(Map<K,V> content);
    public MyIDictionary<K,V> clone();
    public V lookup(K key) throws MyException;
    public Set<Map.Entry<K,V>> getAll();
    public Set<K> getKeys();
}
