package Model.Expressions;

import Model.ADTS.MyIDictionary;
import Model.ADTS.MyIHeap;
import Model.MyException;
import Model.Types.IType;
import Model.Values.Value;

public interface Expression {
    Value eval(MyIDictionary<String,Value> tbl, MyIHeap<Integer,Value> heap) throws Exception;
    IType typecheck(MyIDictionary<String,IType> typeEnv) throws MyException;
}
