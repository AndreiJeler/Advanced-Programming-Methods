package Model.Statements;

import Model.ADTS.MyIDictionary;
import Model.ADTS.MyIStack;
import Model.ADTS.MyStack;
import Model.MyException;
import Model.Program.PrgState;
import Model.Types.IType;

public class Fork implements IStmt {
    private IStmt stmt;
    public Fork(IStmt s){
        stmt=s;
    }
    @Override
    public PrgState execute(PrgState state) throws Exception {

        return new PrgState(new MyStack<>(),state.getSymTable().clone(),state.getOut(),state.getFileTable(),state.getHeap(),state.getLockTable(),stmt);
    }

    @Override
    public MyIDictionary<String, IType> typecheck(MyIDictionary<String, IType> typeEnv) throws MyException {
        stmt.typecheck(typeEnv.clone());
        return typeEnv;
    }

    @Override
    public String toString() {
        return "Fork("+stmt.toString()+")";
    }
}
