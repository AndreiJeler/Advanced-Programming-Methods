package Model.Statements;

import Model.ADTS.MyIDictionary;
import Model.ADTS.MyILockTable;
import Model.MyException;
import Model.Program.PrgState;
import Model.Types.IType;
import Model.Types.IntType;
import Model.Values.IntValue;
import Model.Values.Value;

public class Unlock implements IStmt {
    public String var;

    public Unlock(String v){
        var=v;
    }
    @Override
    public synchronized PrgState execute(PrgState state) throws Exception {
        Value ind=state.getSymTable().get(var);

        MyIDictionary<String, Value> symbolTable = state.getSymTable();

        if(!symbolTable.isDefined(var)){
            throw new MyException("Unlock: var does not exist");
        }
        if(!symbolTable.get(var).getType().equals(new IntType())){
            throw new MyException("NewL: var is not an int");
        }

        IntValue index=(IntValue) ind;

        MyILockTable<Integer, Integer> lockTable = state.getLockTable();
        Integer lockValue = lockTable.get(index.getValue());
        if (lockValue == null)
            throw new Exception("index not in Lock Table");
        else if (lockValue==state.getId()){
            lockTable.update(index.getValue(),-1);
            state.setLockTable(lockTable);
        }
        return null;
    }

    @Override
    public MyIDictionary<String, IType> typecheck(MyIDictionary<String, IType> typeEnv) throws MyException {
        IType typevar=typeEnv.lookup(var);
        if(!typevar.equals(new IntType()))
            throw new MyException("Unlock: var is not an int");
        return typeEnv;
    }

    @Override
    public String toString() {
        return "Unlock{" +
                 var +
                '}';
    }
}
