package Model.Expressions;

import Model.ADTS.MyIDictionary;
import Model.ADTS.MyIHeap;
import Model.MyException;
import Model.Types.IType;
import Model.Values.Value;

public class ValueExp implements Expression {
    private Value value;

    public ValueExp(Value val) {
        value = val;
    }

    @Override
    public Value eval(MyIDictionary<String, Value> tbl, MyIHeap<Integer,Value> heap) throws ExpException {
        return value;
    }

    @Override
    public IType typecheck(MyIDictionary<String, IType> typeEnv) throws MyException {
        return value.getType();
    }

    @Override
    public String toString() {
        return "ValueExp(" + value.toString() + ")";
    }
}
