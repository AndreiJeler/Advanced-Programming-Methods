package Model.Expressions;

import Model.ADTS.MyIDictionary;
import Model.ADTS.MyIHeap;
import Model.MyException;
import Model.Types.IType;
import Model.Types.RefType;
import Model.Values.RefValue;
import Model.Values.Value;

public class ReadH implements Expression {
    private Expression exp;

    public ReadH(Expression e) {
        exp = e;
    }


    @Override
    public String toString() {
        return "rh(" + exp.toString() + ")";
    }

    @Override
    public Value eval(MyIDictionary<String, Value> tbl, MyIHeap<Integer, Value> heap) throws Exception {
        Value v = exp.eval(tbl, heap);
        if (v.getType() instanceof RefType) {
            RefValue res = (RefValue) v;
            if (heap.isDefined(res.getAddress())) {
                return heap.get(res.getAddress());
            } else {
                throw new ExpException("The address is not defined in the heap");
            }
        } else {
            throw new ExpException("Expression is not a RefValue");
        }
    }

    @Override
    public IType typecheck(MyIDictionary<String, IType> typeEnv) throws MyException {
        IType t = exp.typecheck(typeEnv);
        if (t instanceof RefType) {
            RefType refT = (RefType) t;
            return refT.getInner();
        } else throw new MyException("the rH argument is not a RefType");
    }
}
