package Model.Statements;

import Model.ADTS.MyIDictionary;
import Model.ADTS.MyIStack;
import Model.MyException;
import Model.Program.PrgState;
import Model.Statements.IStmt;
import Model.Types.IType;

public class CompStmt implements IStmt {
    private IStmt first;
    private IStmt second;

    public CompStmt(IStmt f, IStmt s) {
        first = f;
        second = s;
    }

    @Override
    public String toString() {
        return "(" + first.toString() + ";" + second.toString() + ")";
    }

    @Override
    public PrgState execute(PrgState state) {
        MyIStack<IStmt> stk = state.getExeStack();
        stk.push(second);
        stk.push(first);
        return null;
    }

    @Override
    public MyIDictionary<String, IType> typecheck(MyIDictionary<String, IType> typeEnv) throws MyException {
        //MyIDictionary<String,IType> typEnv1 = first.typecheck(typeEnv);
        //MyIDictionary<String,IType> typEnv2 = second.typecheck(typEnv1);
        //return typEnv2;
        return second.typecheck(first.typecheck(typeEnv));
    }
}
