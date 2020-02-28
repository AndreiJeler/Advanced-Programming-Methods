package Repository;

import Model.ADTS.*;
import Model.Program.PrgState;
import Model.Statements.IStmt;
import Model.Values.StringValue;
import Model.Values.Value;

import java.io.*;
import java.util.ArrayList;
import java.util.List;


public class LogRepository implements IRepository {
    private List<PrgState> list;
    private String logFilePath;

    public LogRepository(String filePath) {
        list = new ArrayList<>();
        logFilePath = filePath;
    }

    @Override
    public PrgState getCrtPrg() throws Exception {
        return list.get(0);
    }

    @Override
    public void addPrg(PrgState prg) throws Exception {
        list.add(prg);
    }

    @Override
    public void logPrgStateExec(PrgState state){
        PrintWriter logFile = null;
        try {
            logFile = new PrintWriter(new BufferedWriter(new FileWriter(logFilePath, true)));
            MyIList<Value> o = state.getOut();
            MyIDictionary<String, Value> l = state.getSymTable();
            MyIDictionary<StringValue, BufferedReader> fT = state.getFileTable();
            MyIStack<IStmt> s = state.getExeStack();
            MyIHeap<Integer,Value> h=state.getHeap();
            MyILockTable<Integer,Integer> lt=state.getLockTable();
            logFile.write("ID:"+Integer.toString(state.getId()));
            logFile.write("\nExe Stack:\n");
            logFile.print(s);
            logFile.write("\nSymTable:\n");
            logFile.print(l);
            logFile.write("\nOut:\n");
            logFile.print(o);
            logFile.write("\nFile table:\n");
            logFile.print(fT);
            logFile.write("\nHeap:\n");
            logFile.print(h);
            logFile.write("\nLockTable:\n");
            logFile.print(lt);
            logFile.write("\n\n");
            logFile.close();
        } catch (IOException e) {
            System.out.println(e);
        }
        /*MyIList<Value> o = list.get(0).getOut();
        MyIDictionary<String, Value> l = list.get(0).getSymTable();
        MyIDictionary<StringValue, BufferedReader> fT = list.get(0).getFileTable();
        MyIStack<IStmt> s = list.get(0).getExeStack();
        MyIHeap<Integer,Value> h=list.get(0).getHeap();*/

    }

    @Override
    public List<PrgState> getPrgList() {
        return list;
    }

    @Override
    public void setPrgList(List<PrgState> list) {
        this.list=list;
    }
}
