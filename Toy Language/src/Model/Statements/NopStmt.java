package Model.Statements;

import Model.ADTS.MyIDictionary;
import Model.MyException;
import Model.Program.PrgState;
import Model.Types.IType;

public class NopStmt implements IStmt {
    public NopStmt() {
    }

    @Override
    public PrgState execute(PrgState state) throws Exception {
        return null;
    }

    @Override
    public MyIDictionary<String, IType> typecheck(MyIDictionary<String, IType> typeEnv) throws MyException {
        return typeEnv;
    }

    @Override
    public String toString() {
        return "No()";
    }
}
