package Model.Statements;

import Model.ADTS.MyIDictionary;
import Model.Expressions.Expression;
import Model.Expressions.RelationalExp;
import Model.Expressions.ValueExp;
import Model.Expressions.VarExpr;
import Model.MyException;
import Model.Program.PrgState;
import Model.Types.BoolType;
import Model.Types.IType;
import Model.Types.IntType;

public class ForStmt  implements IStmt{

    //private IStmt stmt1,stmt2,stmt3;
   // private Expression exp;
    private IStmt stmt;
    private Expression exp1,exp2,exp3;

    public ForStmt(Expression e1,Expression e2,Expression e3,IStmt s){
        stmt=s;
        exp2=e2;
        exp3=e3;
        exp1=e1;
    }

    @Override
    public PrgState execute(PrgState state) throws Exception {
        // s1=new WhileStmt(exp,new CompStmt(stmt3,stmt2));
        IStmt s1=new VarDeclStmt("v",new IntType());
        IStmt s2=new AssignStmt("v",exp1);
        IStmt s3=new AssignStmt("v",exp3);
        Expression e=new RelationalExp("<",new VarExpr("v"),exp2);
        IStmt s4=new CompStmt(s1,s2);
        IStmt s5=new WhileStmt(e,new CompStmt(stmt,s3));
        IStmt s=new CompStmt(s4,s5);
        state.getExeStack().push(s);
        return null;
    }

    @Override
    public MyIDictionary<String, IType> typecheck(MyIDictionary<String, IType> typeEnv) throws MyException {
        try {
            typeEnv.add("v", new IntType());
        }
        catch (Exception e){
            throw new MyException("V is already declared");
        }
        IType t1=exp1.typecheck(typeEnv);
        IType t2=exp2.typecheck(typeEnv);
        IType t3=exp3.typecheck(typeEnv);
        if (t1.equals(new IntType()) && t2.equals(new IntType()) && t3.equals(new IntType()))
        { stmt.typecheck(typeEnv.clone());
            return typeEnv;
        }
        else throw new MyException("The expressions are not int");
    }

    @Override
    public String toString() {
        return "for(v="+exp1.toString()+";v<"+exp2.toString()+";v="+exp3.toString()+")"+stmt.toString();
    }
}
