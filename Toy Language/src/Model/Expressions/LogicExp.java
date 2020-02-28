package Model.Expressions;

import Model.ADTS.MyIDictionary;
import Model.ADTS.MyIHeap;
import Model.MyException;
import Model.Types.*;
import Model.Values.*;
import Model.Values.Value;

public class LogicExp implements Expression {
    Expression e1;
    Expression e2;
    String op; //1:&&,2:||

    public LogicExp(String o, Expression a, Expression b) {
        e1 = a;
        e2 = b;
        op = o;
    }


    @Override
    public String toString() {
        return "LogicalExp(" + e1.toString() + " " + op + " " + e2.toString() + " )";
    }

    @Override
    public Value eval(MyIDictionary<String, Value> tbl, MyIHeap<Integer, Value> heap) throws Exception {
        Value v1, v2;
        v1 = e1.eval(tbl,heap);
        if (v1.getType().equals(new BoolType())) {
            v2 = e2.eval(tbl,heap);
            if (v2.getType().equals(new BoolType())) {
                BoolValue i1 = (BoolValue) v1;
                BoolValue i2 = (BoolValue) v2;
                boolean n1, n2;
                n1 = i1.getVal();
                n2 = i2.getVal();
                if (op.equals("AND")) return new BoolValue(n1 && n2);
                if (op.equals("OR")) return new BoolValue(n1 || n2);
            } else throw new ExpException("second operand is not a bool");
        } else throw new ExpException("first operand is not a bool");
        return v1;
    }

    @Override
    public IType typecheck(MyIDictionary<String, IType> typeEnv) throws MyException {
        IType t1,t2;
        t1=e1.typecheck(typeEnv);
        t2=e2.typecheck(typeEnv);
        if (t1.equals(new BoolType())){
            if (t2.equals(new BoolType())){
                return new BoolType();
            }
            else throw new MyException("second operand is not an boolean.");
        }
        else throw new MyException("first operand is not an boolean,");
    }
}

