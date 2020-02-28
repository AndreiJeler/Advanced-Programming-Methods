package Model.Statements;

import Model.ADTS.MyIDictionary;
import Model.ADTS.MyIHeap;
import Model.Expressions.Expression;
import Model.MyException;
import Model.Program.PrgState;
import Model.Types.IType;
import Model.Types.IntType;
import Model.Types.StringType;
import Model.Values.IntValue;
import Model.Values.StringValue;
import Model.Values.Value;

import java.io.BufferedReader;

public class ReadFileStmt implements IStmt {
    private Expression exp;
    private String var_name;

    public ReadFileStmt(Expression e, String var) {
        exp = e;
        var_name = var;
    }

    @Override
    public PrgState execute(PrgState state) throws Exception {
        MyIDictionary<String, Value> symbT = state.getSymTable();
        MyIDictionary<StringValue, BufferedReader> fileT = state.getFileTable();
        MyIHeap<Integer, Value> heap = state.getHeap();
        Value res = symbT.get(var_name);
        Value res2 = exp.eval(symbT, heap);
        if (!symbT.isDefined(var_name)) {
            throw new StmtException("var_name is not defined");
        }
        if (!res.getType().equals(new IntType())) {
            throw new StmtException("var_name is not an int");
        }
        if (!res2.getType().equals(new StringType())) {
            throw new StmtException("exp is not a string");
        }

        if (symbT.isDefined(var_name)) {
            if (res.getType().equals(new IntType())) {
                if (res2.getType().equals(new StringType())) {
                    StringValue filepath = (StringValue) res2;
                    if (fileT.isDefined(filepath)) {
                        BufferedReader file = fileT.get(filepath);
                        String line = file.readLine();
                        int readInt;
                        if (line == null) {
                            readInt = 0;
                        } else {
                            readInt = Integer.parseInt(line);
                        }
                        symbT.update(var_name, new IntValue(readInt));
                    } else {
                        throw new StmtException("File not open.");
                    }
                } else {
                    throw new StmtException("Result of expression is not a string.");
                }
            } else {
                throw new StmtException("Variable is not an int");
            }
        } else {
            throw new StmtException("Variable does not exist");
        }
        return null;
    }

    @Override
    public MyIDictionary<String, IType> typecheck(MyIDictionary<String, IType> typeEnv) throws MyException {
        IType typeExp = exp.typecheck(typeEnv);
        IType typeVar = typeEnv.lookup(var_name);
        if (typeExp.equals(new StringType())) {
            if (typeVar.equals(new IntType()))
                return typeEnv;
            else throw new MyException("ReadFile: var is not an int");
        } else throw new MyException("ReadFile: exp is not a string");
    }

    @Override
    public String toString() {
        return "ReadFileStmt(" + exp.toString() + "->" + var_name + ")";
    }
}
