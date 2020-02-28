package Model.Expressions;

import Model.ADTS.ADTException;
import Model.ADTS.MyIDictionary;
import Model.ADTS.MyIHeap;
import Model.MyException;
import Model.Types.IType;
import Model.Values.Value;

public class VarExpr implements Expression {
    private String id;

    public VarExpr(String i) {
        id = i;
    }

    @Override
    public Value eval(MyIDictionary<String, Value> tbl, MyIHeap<Integer, Value> heap) throws ExpException {
        try {
            return tbl.get(id);
        } catch (ADTException ex) {
            throw new ExpException("Variable does not exist");
        }
    }

    @Override
    public IType typecheck(MyIDictionary<String, IType> typeEnv) throws MyException {
        try {
            return typeEnv.get(id);
        }
        catch (ADTException ex){
            throw new MyException("Error at VarExpr");
        }
    }

    @Override
    public String toString() {
        return "Var(" + id + ")";
    }
}
