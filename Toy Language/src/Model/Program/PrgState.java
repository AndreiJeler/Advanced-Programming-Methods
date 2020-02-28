package Model.Program;

import Model.ADTS.*;
import Model.Statements.IStmt;
import Model.Values.StringValue;
import Model.Values.Value;

import java.io.BufferedReader;
import java.time.chrono.IsoChronology;

public class PrgState {
    private MyIStack<IStmt> exeStack;
    private MyIDictionary<String, Value> symTable;
    private MyIList<Value> out;
    private MyIDictionary<StringValue, BufferedReader> fileTable;
    private IStmt originalProgram;
    private MyIHeap<Integer,Value> heap;
    private MyILockTable<Integer,Integer> lockTable;

    private int id;
    static int currentID=1;

    public PrgState(MyIStack<IStmt> exeS, MyIDictionary<String, Value> symT, MyIList<Value> ot, MyIDictionary<StringValue, BufferedReader> fT,MyIHeap<Integer,Value> hp,MyILockTable<Integer,Integer> lt, IStmt prg) {
        exeStack = exeS;
        symTable = symT;
        out = ot;
        originalProgram = prg;
        fileTable = fT;
        heap=hp;
        exeS.push(originalProgram);
        id=newId();
        lockTable=lt;
    }

    public MyIList<Value> getOut() {
        return out;
    }

    public MyIDictionary<String, Value> getSymTable() {
        return symTable;
    }
    public void setSymTable(MyIDictionary<String,Value> m) {symTable=m;}

    public IStmt getOriginalProgram() {
        return originalProgram;
    }

    public MyIStack<IStmt> getExeStack() {
        return exeStack;
    }

    public MyIHeap<Integer, Value> getHeap() {
        return heap;
    }

    public MyIDictionary<StringValue, BufferedReader> getFileTable() {
        return fileTable;
    }

    @Override
    public String toString() {
        String res = "";
        res += "ID:"+getId()+"\nStack:" + exeStack.toString() + "\nSymTbl:" + symTable.toString() + "\nOut:" + out.toString() +"\nFileTable"+fileTable.toString()+"\nHeap"+heap.toString()+"\nLockTable:"+lockTable.toString()+ "\n\n";
        return res;
    }

    public Boolean isNotCompleted(){
        return !exeStack.isEmpty();
    }

    public PrgState oneStep() throws Exception{
        if(exeStack.isEmpty()) throw new ADTException("stack is empty");
        IStmt crtStmt=exeStack.pop();
        // System.out.println(crtStmt);
        //repo.logPrgStateExec();
        return crtStmt.execute(this);
    }

    public int getId(){
        return id;
    }

    public static synchronized int newId(){
        int aux=currentID;
        currentID++;
        return aux;
    }


    public MyILockTable<Integer, Integer> getLockTable() {
        return lockTable;
    }

    public void setLockTable(MyILockTable<Integer, Integer> lockTable) {
        this.lockTable = lockTable;
    }
    public void setStack(MyIStack<IStmt> st){this.exeStack=st;}
}
