package Model.Statements;

import Model.ADTS.MyIDictionary;
import Model.ADTS.MyIHeap;
import Model.ADTS.MyIStack;
import Model.Expressions.Expression;
import Model.MyException;
import Model.Program.PrgState;
import Model.Types.BoolType;
import Model.Types.IType;
import Model.Values.Value;

public class IfStmt implements IStmt {
    Expression exp;
    IStmt thenS;
    IStmt elseS;

    public IfStmt(Expression e,IStmt tS,IStmt eS){
        exp=e;
        thenS=tS;
        elseS=eS;
    }

    @Override
    public String toString() {
        return "IF ("+exp.toString()+") THEN ( " +thenS.toString()+" ) ELSE ("+elseS.toString()+")";
    }

    @Override
    public PrgState execute(PrgState state) throws Exception {
        MyIHeap<Integer,Value> heap=state.getHeap();
        MyIStack<IStmt> exeStack = state.getExeStack();
        MyIDictionary<String, Value> symTable = state.getSymTable();
        Value result = this.exp.eval(symTable,heap);
        if(!result.getValue().equals(0))
            exeStack.push(thenS);
        else
            exeStack.push(elseS);
        return null;
    }

    @Override
    public MyIDictionary<String, IType> typecheck(MyIDictionary<String, IType> typeEnv) throws MyException {
        IType typexp=exp.typecheck(typeEnv);
        if (typexp.equals(new BoolType())) {
            thenS.typecheck(typeEnv.clone());
            elseS.typecheck(typeEnv.clone());
            return typeEnv;
        }
        else
            throw new MyException("The condition of IF has not the type bool");
    }
}
