package Model.ADTS;

import Model.Statements.IStmt;

import java.util.Stack;

public class MyStack<T> implements MyIStack<T> {
    private Stack<T> stack;

    public MyStack() {
        stack = new Stack<T>();
    }

    @Override
    public void push(T v) {
        stack.push(v);
    }

    @Override
    public T pop() throws ADTException {
        if (this.isEmpty())
            throw new ADTException("Stack is empty");
        return stack.pop();
    }

    @Override
    public boolean isEmpty() {
        return stack.isEmpty();
    }

    @Override
    public int size() {
        return stack.size();
    }

    @Override
    public MyIStack<T> getStk() {
        MyIStack<T> aux = new MyStack<T>();
        for (T t : stack) {
            aux.push(t);
        }
        return aux;
    }

    @Override
    public Stack<T> getAll() {
        return stack;
    }

    @Override
    public String toString() {
        String aux = "";
        for (T e : stack) {
            aux += e.toString() + " ";
        }
        return aux;
    }
}
