package Model.Statements;

import Model.ADTS.ADTException;
import Model.ADTS.MyIDictionary;
import Model.MyException;
import Model.Program.PrgState;
import Model.Types.IType;
import Model.Types.IntType;
import Model.Values.BoolValue;
import Model.Values.IntValue;
import Model.Values.Value;

public class VarDeclStmt implements IStmt {
    private String name;
    private IType type;

    public VarDeclStmt(String n, IType t) {
        name = n;
        type = t;
    }

    @Override
    public String toString() {
        return type.toString()+" "+name;
    }

    @Override
    public PrgState execute(PrgState state) throws Exception {
        MyIDictionary<String, Value> symbolTable = state.getSymTable();
        if (symbolTable.isDefined(this.name)) throw new Exception("Variable already exists");

        symbolTable.add(name,type.defaultValue());
       /* if (type.equals(new IntType())) {
            Value<Integer> v = new IntValue(0);
            symbolTable.add(name, v);
        }
        else{
            Value<Boolean> v = new BoolValue(false);
            symbolTable.add(name, v);
        }*/
        return null;
    }

    @Override
    public MyIDictionary<String, IType> typecheck(MyIDictionary<String, IType> typeEnv) throws MyException {
        try {
            typeEnv.add(name, type);
            return typeEnv;
        }
        catch (ADTException e) {
            throw new MyException("VarDecl error");
        }

    }
}
