package Model.Expressions;

import Model.ADTS.MyIDictionary;
import Model.ADTS.MyIHeap;
import Model.MyException;
import Model.Types.IType;
import Model.Types.IntType;
import Model.Values.IntValue;
import Model.Values.Value;

public class ArithExp implements Expression{
    Expression e1;
    Expression e2;
    char op;   //1:+, 2:-, 3:*, 4:/
    public ArithExp(char o,Expression a,Expression b){
        e1=a;
        e2=b;
        op=o;
    }
    @Override
    public String toString() {
        return "ArithExpr("+e1.toString()+" "+op+" "+e2.toString()+" )";
    }

    @Override
    public Value eval(MyIDictionary<String, Value> tbl, MyIHeap<Integer, Value> heap) throws Exception {
        Value v1,v2;
        v1=e1.eval(tbl,heap);
        if(v1.getType().equals(new IntType())){
            v2=e2.eval(tbl,heap);
            if(v2.getType().equals(new IntType())){
                IntValue i1=(IntValue)v1;
                IntValue i2=(IntValue)v2;
                int n1,n2;
                n1=i1.getVal();
                n2=i2.getVal();
                if(op=='+') return new IntValue(n1+n2);
                if(op=='-') return new IntValue(n1-n2);
                if(op=='*') return new IntValue(n1*n2);
                if(op=='/'){
                    if(n2==0) throw  new ExpException("division by zero");
                    else return new IntValue(n1/n2);
                }
            }
            else throw new ExpException("second operand is not an integer");
        }
        else throw new ExpException("first operand is not an integer");
        return v1;
    }

    @Override
    public IType typecheck(MyIDictionary<String, IType> typeEnv) throws MyException {
        IType t1,t2;
        t1=e1.typecheck(typeEnv);
        t2=e2.typecheck(typeEnv);
        if (t1.equals(new IntType())){
            if (t2.equals(new IntType())){
                return new IntType();
            }
            else throw new MyException("second operand is not an integer.");
        }
        else throw new MyException("first operand is not an integer,");
    }
}
