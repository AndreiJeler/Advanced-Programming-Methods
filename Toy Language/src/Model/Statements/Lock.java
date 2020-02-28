package Model.Statements;

import Model.ADTS.MyIDictionary;
import Model.ADTS.MyILockTable;
import Model.ADTS.MyIStack;
import Model.MyException;
import Model.Program.PrgState;
import Model.Types.IType;
import Model.Types.IntType;
import Model.Values.IntValue;
import Model.Values.Value;

public class Lock implements IStmt {
    private String var;

    public Lock(String v){var=v;}

    @Override
    public synchronized PrgState execute(PrgState state) throws Exception {
        Value ind=state.getSymTable().get(var);
        IntValue index=(IntValue) ind;
        MyIDictionary<String, Value> symbolTable = state.getSymTable();

        if(!symbolTable.isDefined(var)){
            throw new MyException("Lock: var does not exist");
        }
        if(!symbolTable.get(var).getType().equals(new IntType())){
            throw new MyException("NewL: var is not an int");
        }


        MyILockTable<Integer,Integer> lockT=state.getLockTable();
        Integer lockVal=lockT.get(index.getValue());
        if(lockVal==null){
            throw new MyException("No such index in Lock Table");
        }
        else if(lockVal==-1){
            lockT.update(index.getValue(),state.getId());
            state.setLockTable(lockT);
        }
        else{
            MyIStack<IStmt> st=state.getExeStack();
            st.push(this);
            state.setStack(st);
        }
        return null;
    }

    @Override
    public MyIDictionary<String, IType> typecheck(MyIDictionary<String, IType> typeEnv) throws MyException {
        IType typevar=typeEnv.lookup(var);
        if(!typevar.equals(new IntType()))
            throw new MyException("Lock: var is not an int");
        return typeEnv;
    }

    @Override
    public String toString() {
        return "Lock{" +
                var +
                '}';
    }
}
