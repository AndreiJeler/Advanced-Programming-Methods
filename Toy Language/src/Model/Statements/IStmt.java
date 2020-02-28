package Model.Statements;

import Model.ADTS.MyIDictionary;
import Model.MyException;
import Model.Program.PrgState;
import Model.Types.IType;

public interface IStmt {
    PrgState execute(PrgState state) throws Exception;
    MyIDictionary<String, IType> typecheck(MyIDictionary<String,IType> typeEnv) throws MyException;

}
