package Model.Statements;

import Model.ADTS.MyIDictionary;
import Model.ADTS.MyILockTable;
import Model.ADTS.MyLockTable;
import Model.MyException;
import Model.Program.PrgState;
import Model.Types.IType;
import Model.Types.IntType;
import Model.Values.IntValue;
import Model.Values.Value;

public class newLock implements IStmt {
    private String var;

    public newLock(String v){
        var=v;
    }
    @Override
    public synchronized PrgState execute(PrgState state) throws Exception {
        MyILockTable<Integer, Integer> lockTable = state.getLockTable();
        MyIDictionary<String, Value> symbolTable = state.getSymTable();
        if(!symbolTable.isDefined(var)){
            throw new MyException("NewL: var does not exist");
        }
        if(!symbolTable.get(var).getType().equals(new IntType())){
            throw new MyException("NewL: var is not an int");
        }

        Integer location= MyLockTable.newFree();
        lockTable.add(location,-1);
        symbolTable.update(var,new IntValue(location));
        state.setLockTable(lockTable);
        state.setSymTable(symbolTable);
        return null;
    }

    @Override
    public MyIDictionary<String, IType> typecheck(MyIDictionary<String, IType> typeEnv) throws MyException {
        IType typevar=typeEnv.lookup(var);
        if(!typevar.equals(new IntType()))
            throw new MyException("NewL: var is not an int");
        return typeEnv;
    }

    @Override
    public String toString() {
        return "newLock("
                + var +
                ')';
    }
}
