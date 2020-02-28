package Model.ADTS;

import Model.Statements.IStmt;

import java.util.Stack;

public interface MyIStack<T> {
    void push(T v);
    T pop() throws ADTException;
    boolean isEmpty();
    int size();
    MyIStack<T> getStk();
    Stack<T> getAll();
    //String toString();

}
