package Model.ADTS;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class MyList<T> implements MyIList<T> {
    private LinkedList<T> list;

    public MyList() {
        list = new LinkedList<>();
    }

    @Override
    public void add(T v) throws ADTException {
        try {
            list.add(v);
        } catch (Exception ex) {
            throw new ADTException("Error at adding in the list");
        }
    }

    @Override
    public void delete(T v) throws ADTException {
        try {
            list.remove(v);
        } catch (Exception ex) {
            throw new ADTException("Error at deleting from the list");
        }

    }

    @Override
    public int size() {
        return list.size();
    }

    @Override
    public T get(int idx) throws ADTException {
        try {
            return list.get(idx);
        } catch (Exception ex) {
            throw new ADTException("Item does not exist");
        }
    }

    @Override
    public List<T> getAll() {
        return this.list;
    }

    @Override
    public String toString() {
        String res = "";
        for (T e : list) {
            res += e.toString() + " ";
        }
        return res;
    }
}
