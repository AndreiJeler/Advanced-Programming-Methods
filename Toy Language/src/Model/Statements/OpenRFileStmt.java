package Model.Statements;

import Model.ADTS.MyIDictionary;
import Model.ADTS.MyIHeap;
import Model.ADTS.MyIStack;
import Model.Expressions.Expression;
import Model.MyException;
import Model.Program.PrgState;
import Model.Types.IType;
import Model.Types.StringType;
import Model.Values.StringValue;
import Model.Values.Value;

import java.io.BufferedReader;
import java.io.FileReader;

public class OpenRFileStmt implements IStmt {
    Expression exp;

    public OpenRFileStmt(Expression e) {
        exp = e;
    }

    @Override
    public String toString() {
        return "OpenRFile("+exp+")";
    }

    @Override
    public PrgState execute(PrgState state) throws Exception {
        MyIDictionary<String, Value> symT=state.getSymTable();
        MyIDictionary<StringValue, BufferedReader> fileT=state.getFileTable();
        MyIHeap<Integer,Value> heap=state.getHeap();

        Value res=exp.eval(symT,heap);
        if(res.getType().equals(new StringType()))
        {
            StringValue file=(StringValue)res;
            if(state.getFileTable().isDefined(file)){
                throw new StmtException("This file already exists in the program");
            }
            else{
                BufferedReader reader=new BufferedReader(new FileReader(file.getValue()));
                fileT.add(file,reader);
            }

        }
        else{
            throw  new StmtException("This file expression is not a string");
        }

        return null;
    }

    @Override
    public MyIDictionary<String, IType> typecheck(MyIDictionary<String, IType> typeEnv) throws MyException {
        IType typeExp=exp.typecheck(typeEnv);
        if(typeExp.equals(new StringType())){
            return typeEnv;
        }
        else throw new MyException("OpenRFile: exp is not a string");
    }


}
