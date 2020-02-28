package Model.Statements;

import Model.ADTS.MyIDictionary;
import Model.ADTS.MyIHeap;
import Model.ADTS.MyIStack;
import Model.Expressions.Expression;
import Model.MyException;
import Model.Program.PrgState;
import Model.Values.Value;
import Model.Types.*;

public class AssignStmt implements IStmt {
    private String id;
    private Expression exp;
    public AssignStmt(String i,Expression e){
        id=i;
        exp=e;
    }

    @Override
    public String toString() {
        return id+"="+exp.toString();
    }

    @Override
    public PrgState execute(PrgState state) throws Exception{
        MyIStack<IStmt> stk=state.getExeStack();
        MyIDictionary<String, Value> symTbl=state.getSymTable();
        MyIHeap<Integer,Value> heap=state.getHeap();
        Value val=exp.eval(symTbl,heap);
        if (symTbl.isDefined(id)){
            IType typId=symTbl.get(id).getType();
            if (val.getType().equals(typId)) symTbl.update(id,val);
            else throw new StmtException("declared type of variable and type of the assigned expression do not match");

        }
        else throw new StmtException("the used variable was not declared before");
        return null;
    }

    @Override
    public MyIDictionary<String, IType> typecheck(MyIDictionary<String, IType> typeEnv) throws MyException {
        IType typevar=typeEnv.lookup(id);
        IType typeExp=exp.typecheck(typeEnv);
        if (typevar==null)
            throw new MyException("Assignment: var does not exist.");
        if(typevar.equals(typeExp))
            return typeEnv;
        else
            throw new MyException("Assignment: right hand side and left hand side have different types");
    }
}
