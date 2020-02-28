package Model.Statements;

import Model.ADTS.MyHeap;
import Model.ADTS.MyIDictionary;
import Model.ADTS.MyIHeap;
import Model.Expressions.Expression;
import Model.MyException;
import Model.Program.PrgState;
import Model.Types.IType;
import Model.Types.RefType;
import Model.Values.RefValue;
import Model.Values.Value;

public class NewStmt implements IStmt {
    private String var;
    private Expression exp;

    public NewStmt(String v,Expression e){
        var=v;
        exp=e;
    }

    @Override
    public PrgState execute(PrgState state) throws Exception {
        MyIDictionary<String, Value> tbl = state.getSymTable();
        MyIHeap<Integer, Value> heap = state.getHeap();
        if (tbl.isDefined(var)) {
            Value v = tbl.get(var);
            if (v.getType() instanceof RefType) {
                RefValue val = (RefValue) v;
                Value r = exp.eval(tbl, heap);
                IType type = ((RefType) val.getType()).getInner();
                if (r.getType().equals(type)) {
                    int free = heap.getFirstEmpty();
                    heap.add(r);
                    tbl.update(var, new RefValue(free, type));
                    return null;
                } else {
                    throw new StmtException("Type does not match");
                }
            } else {
                throw new StmtException("Var is not a RefType");
            }
        } else {
            throw new StmtException("Variable not declared");
        }
    }

    @Override
    public MyIDictionary<String, IType> typecheck(MyIDictionary<String, IType> typeEnv) throws MyException {
        IType typevar = typeEnv.lookup(var);
        IType typexp = exp.typecheck(typeEnv);
        if (typevar.equals(new RefType(typexp)))
            return typeEnv;
        else
            throw new MyException("NEW stmt: right hand side and left hand side have different types ");
    }

    @Override
    public String toString() {
        return "new("+var+","+exp.toString()+")";
    }
}
