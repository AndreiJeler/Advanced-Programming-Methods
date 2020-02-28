package Model.ADTS;

import java.util.List;

public interface MyIList<T> {
    void add(T v) throws ADTException;
    void delete(T v) throws ADTException;
    int size();
    T get(int idx) throws ADTException;
    List<T> getAll();
}
