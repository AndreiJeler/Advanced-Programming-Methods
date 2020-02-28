package Model.ADTS;

import Model.MyException;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class MyLockTable<K,V> implements MyILockTable<K,V> {
    Map<K,V> dict;
    static Integer firstfree;
    public MyLockTable(){dict=new HashMap<K,V>();firstfree=1;}
    @Override
    public synchronized void add(K key, V val) throws ADTException {
        dict.put(key,val);
    }

    @Override
    public synchronized void remove(K key) throws ADTException {
        dict.remove(key);
    }

    @Override
    public synchronized void update(K key, V val) throws ADTException {
        dict.put(key,val);
    }

    @Override
    public synchronized V get(K key) throws ADTException {
        return dict.get(key);
    }

    @Override
    public boolean isDefined(K id) {
        return dict.containsKey(id);
    }

    @Override
    public Map<K, V> getContents() {
        return dict;
    }

    @Override
    public void setContents(Map<K, V> content) {
        dict=content;
    }


    @Override
    public synchronized V lookup(K key) throws MyException {
        return dict.get(key);
    }

    @Override
    public Set<Map.Entry<K, V>> getAll() {
        return dict.entrySet();
    }

    @Override
    public Set<K> getKeys() {
        return dict.keySet();
    }

    public synchronized static Integer newFree(){
        Integer aux=firstfree;
        firstfree++;
        return aux;
    }

    @Override
    public String toString() {
        String res="";
        for(K key:dict.keySet()){
            res+=key.toString()+"->"+dict.get(key).toString()+" ";
        }
        return res;
    }

}
