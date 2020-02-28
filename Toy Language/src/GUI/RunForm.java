package GUI;

import Controller.Controller;
import Model.ADTS.*;
import Model.Expressions.*;
import Model.Program.PrgState;
import Model.Statements.*;
import Model.Types.BoolType;
import Model.Types.IntType;
import Model.Types.RefType;
import Model.Types.StringType;
import Model.Values.BoolValue;
import Model.Values.IntValue;
import Model.Values.StringValue;
import Model.Values.Value;
import Repository.IRepository;
import Repository.LogRepository;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.stage.Stage;

import java.beans.EventHandler;
import java.io.*;
import java.net.URL;
import java.time.chrono.IsoChronology;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.ResourceBundle;
import java.util.stream.Collectors;

public class RunForm implements Initializable {
    private List<IStmt> stmt;
    private MainForm mainWindow;
    @FXML
    private ListView<String> listV;
    @FXML
    private Button btn;
    static Controller ctr;
    static PrgState prg;


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        createStmts();
        listV.setItems(FXCollections.observableArrayList(stmt.stream().map(s->s.toString()).collect(Collectors.toList())));
//        btn.setOnAction(actionEvent -> {
////            int index = listV.getSelectionModel().getSelectedIndex();
////
////            if(index < 0)
////                return;
////
////
////
////            MyIStack<IStmt> stk = new MyStack<IStmt>();
////            MyIDictionary<String, Value> symTbl = new MyDictionary<String, Value>();
////            MyIDictionary<StringValue, BufferedReader> fT = new MyDictionary<StringValue, BufferedReader>();
////            MyIList<Value> out = new MyList<Value>();
////            MyIHeap<Integer, Value> heap = new MyHeap();
////            PrgState prg = new PrgState(stk, symTbl, out, fT, heap, stmt.get(index));
////            IRepository repo = new LogRepository("log.txt");
////            Controller ctr = new Controller(repo);
////            try {
////                ctr.addProgram(prg);
////            } catch (Exception e) {
////                System.out.println("Error");
////            }
////
////            //mainWindow.setController(ctrl);
////        });
    }
    @FXML
    public void pressBtn() throws Exception {
        int index = listV.getSelectionModel().getSelectedIndex();

        if(index < 0)
            return;

        PrintWriter logFile = new PrintWriter(new BufferedWriter(new FileWriter("log.txt")));
        logFile.close();


        MyIStack<IStmt> stk = new MyStack<IStmt>();
        MyIDictionary<String, Value> symTbl = new MyDictionary<String, Value>();
        MyIDictionary<StringValue, BufferedReader> fT = new MyDictionary<StringValue, BufferedReader>();
        MyIList<Value> out = new MyList<Value>();
        MyIHeap<Integer, Value> heap = new MyHeap();
        stmt.get(index).typecheck(new MyDictionary<>());
        MyILockTable<Integer,Integer> lt=new MyLockTable<Integer, Integer>();
        //lt.add(1,1);
        prg = new PrgState(stk, symTbl, out, fT, heap,lt, stmt.get(index));
        IRepository repo = new LogRepository("log.txt");
         ctr = new Controller(repo);
        try {
            ctr.addProgram(prg);
        } catch (Exception e) {
            System.out.println("Error");
        }
        FXMLLoader runLoader=new FXMLLoader();
        runLoader.setLocation(getClass().getResource("MainForm.fxml"));
        Parent runP=runLoader.load();
        MainForm runCtr=runLoader.getController();
       // runCtr.setController(ctr);
        //runCtr.populateNr();
        Stage runS=new Stage();
        runS.setTitle("Main windows");
        runS.setScene(new Scene(runP,977,511));
        runS.showAndWait();
    }

    private void createStmts(){
        IStmt ex1 = new CompStmt(new VarDeclStmt("v", new IntType()),
                new CompStmt(new AssignStmt("v", new ValueExp(new IntValue(2))), new PrintStmt(new
                        VarExpr("v"))));

        IStmt ex2 = new CompStmt(new VarDeclStmt("a", new IntType()),
                new CompStmt(new VarDeclStmt("b", new IntType()),
                        new CompStmt(new AssignStmt("a", new ArithExp('+', new ValueExp(new IntValue(2)), new
                                ArithExp('*', new ValueExp(new IntValue(3)), new ValueExp(new IntValue(5))))),
                                new CompStmt(new AssignStmt("b", new ArithExp('+', new VarExpr("a"), new
                                        ValueExp(new IntValue(1)))), new PrintStmt(new VarExpr("b"))))));


        IStmt ex3 = new CompStmt(new VarDeclStmt("a", new BoolType()),
                new CompStmt(new VarDeclStmt("v", new IntType()),
                        new CompStmt(new AssignStmt("a", new ValueExp(new BoolValue(true))),
                                new CompStmt(new IfStmt(new VarExpr("a"), new AssignStmt("v", new ValueExp(new
                                        IntValue(2))), new AssignStmt("v", new ValueExp(new IntValue(3)))), new PrintStmt(new
                                        VarExpr("v"))))));

        IStmt ex4 = new CompStmt(new VarDeclStmt("varf", new StringType()),
                new CompStmt(new AssignStmt("varf", new ValueExp(new StringValue("test.in"))),
                        new CompStmt(new OpenRFileStmt(new VarExpr("varf")),
                                new CompStmt(new VarDeclStmt("varc", new IntType()),
                                        new CompStmt(new ReadFileStmt(new VarExpr("varf"), "varc"),
                                                new CompStmt(new PrintStmt(new VarExpr("varc")),
                                                        new CompStmt(new ReadFileStmt(new VarExpr("varf"), "varc"),
                                                                new CompStmt(new PrintStmt(new VarExpr("varc")), new CloseRFileStmt(new VarExpr("varf"))))))))));


        IStmt ex5 = new CompStmt(new VarDeclStmt("v", new RefType(new IntType())),
                new CompStmt(new NewStmt("v", new ValueExp(new IntValue(20))),
                        new CompStmt(new VarDeclStmt("a", new RefType(new RefType(new IntType()))),
                                new CompStmt(new NewStmt("a", new VarExpr("v")),
                                        new CompStmt(new PrintStmt(new VarExpr("v")), new PrintStmt(new VarExpr("a")))))));



        IStmt ex6 = new CompStmt(new VarDeclStmt("v", new RefType(new IntType())),
                new CompStmt(new NewStmt("v", new ValueExp(new IntValue(20))),
                        new CompStmt(new VarDeclStmt("a", new RefType(new RefType(new IntType()))),
                                new CompStmt(new NewStmt("a", new VarExpr("v")),
                                        new CompStmt(new PrintStmt(new ReadH(new VarExpr("v"))),
                                                new PrintStmt(new ArithExp('+', new ReadH(new ReadH(new VarExpr("a"))), new ValueExp(new IntValue(5)))))))));



        IStmt ex7 = new CompStmt(new VarDeclStmt("v", new RefType(new IntType())),
                new CompStmt(new NewStmt("v", new ValueExp(new IntValue(20))),
                        new CompStmt(new PrintStmt(new ReadH(new VarExpr("v"))),
                                new CompStmt(new WriteHStmt("v", new ValueExp(new IntValue(30))),
                                        new PrintStmt(new ArithExp('+', new ReadH(new VarExpr("v")), new ValueExp(new IntValue(5))))))));
        ;



        IStmt ex8 = new CompStmt(new VarDeclStmt("v", new RefType(new IntType())),
                new CompStmt(new NewStmt("v", new ValueExp(new IntValue(20))),
                        new CompStmt(new VarDeclStmt("a", new RefType(new RefType(new IntType()))),
                                new CompStmt(new NewStmt("a", new VarExpr("v")),
                                        new CompStmt(new NewStmt("v", new ValueExp(new IntValue(30))),
                                                new PrintStmt(new ReadH(new ReadH(new VarExpr("a")))))))));




        IStmt ex9 = new CompStmt(new VarDeclStmt("v", new IntType()),
                new CompStmt(new AssignStmt("v", new ValueExp(new IntValue(4))),
                        new CompStmt(new WhileStmt(new RelationalExp(">", new VarExpr("v"), new ValueExp(new IntValue(0))),
                                new CompStmt(new PrintStmt(new VarExpr("v")), new AssignStmt("v", new ArithExp('-', new VarExpr("v"), new ValueExp(new IntValue(1)))))),
                                new PrintStmt(new VarExpr("v"))
                        )));
        ;



        IStmt ex10 = new CompStmt(new VarDeclStmt("v", new IntType()),
                new CompStmt(new VarDeclStmt("a", new RefType(new IntType())),
                        new CompStmt(new AssignStmt("v", new ValueExp(new IntValue(10))),
                                new CompStmt(new NewStmt("a", new ValueExp(new IntValue(22))),
                                        new CompStmt(new Fork(new CompStmt(new WriteHStmt("a", new ValueExp(new IntValue(30))), new CompStmt(new AssignStmt("v", new ValueExp(new IntValue(32))), new CompStmt(new PrintStmt(new VarExpr("v")), new PrintStmt(new ReadH(new VarExpr("a"))))))),
                                                new CompStmt(new PrintStmt(new VarExpr("v")), new PrintStmt(new ReadH(new VarExpr("a")))))))));


        IStmt s1=new VarDeclStmt("a",new RefType(new IntType()));
        IStmt s2=new NewStmt("a",new ValueExp(new IntValue(20)));
        //IStmt s3=new ForStmt(new ValueExp(new IntValue(0)),new RelationalExp("<",new VarExpr("v"),new ValueExp(new IntValue(3))),new ArithExp('+',new VarExpr("v"),new ValueExp(new IntValue(1))),new Fork(new CompStmt(new PrintStmt(new VarExpr("v")),new AssignStmt("v",new ArithExp('*',new VarExpr("v"),new ReadH(new VarExpr("a")))))));
        IStmt s3=new ForStmt(new ValueExp(new IntValue(0)),new ValueExp(new IntValue(3)),new ArithExp('+',new VarExpr("v"),new ValueExp(new IntValue(1))),new Fork(new CompStmt(new PrintStmt(new VarExpr("v")),new AssignStmt("v",new ArithExp('*',new VarExpr("v"),new ReadH(new VarExpr("a")))))));
        IStmt s4=new PrintStmt(new ReadH(new VarExpr("a")));

        IStmt s5=new CompStmt(s1,s2);
        IStmt s6=new CompStmt(s3,s4);
        IStmt ex11=new CompStmt(s5,s6);

        IStmt a1=new VarDeclStmt("v1",new RefType(new IntType()));
        IStmt a2=new VarDeclStmt("v2",new RefType(new IntType()));
        IStmt a3=new VarDeclStmt("x",new IntType());
        IStmt a4=new VarDeclStmt("q",new IntType());
        IStmt a5=new NewStmt("v1",new ValueExp(new IntValue(20)));
        IStmt a6=new NewStmt("v2",new ValueExp(new IntValue(30)));
        IStmt f1=new Fork(new CompStmt(new Lock("x"),new CompStmt(new WriteHStmt("v1",new ArithExp('-',new ReadH(new VarExpr("v1")),new ValueExp(new IntValue(1)))),new Unlock("x"))));
        IStmt a7=new CompStmt(new Lock("x"),new CompStmt(new WriteHStmt("v1",new ArithExp('*',new ReadH(new VarExpr("v1")),new ValueExp(new IntValue(10)))),new Unlock("x")));
        IStmt fork1=new Fork(new CompStmt(f1,a7));
        IStmt n1=new newLock("x");
        IStmt n2=new newLock("q");
        IStmt l1=new Lock("x");
        IStmt l2=new Lock("q");
        IStmt u1=new Unlock("x");
        IStmt u2=new Unlock("q");
        IStmt f2=new Fork(new CompStmt(new Lock("q"),new CompStmt(new WriteHStmt("v2",new ArithExp('+',new ReadH(new VarExpr("v2")),new ValueExp(new IntValue(5)))),new Unlock("q"))));
        IStmt a8=new CompStmt(new Lock("q"),new CompStmt(new WriteHStmt("v2",new ArithExp('*',new ReadH(new VarExpr("v2")),new ValueExp(new IntValue(10)))),new Unlock("q")));
        IStmt fork2=new Fork(new CompStmt(f2,a8));
        IStmt a9=new PrintStmt(new ReadH(new VarExpr("v1")));
        IStmt a10=new PrintStmt(new ReadH(new VarExpr("v2")));
        IStmt nop=new NopStmt();

        IStmt c1=new CompStmt(a1,a2);
        IStmt c2=new CompStmt(a3,a4);
        IStmt c3=new CompStmt(a5,a6);
        IStmt c4=new CompStmt(n1,fork1);
        IStmt c5=new CompStmt(n2,fork2);
        IStmt c6=new CompStmt(nop,nop);// *2
        IStmt c7=new CompStmt(l1,a9);
        IStmt c8=new CompStmt(u1,l2);
        IStmt c9=new CompStmt(a10,u2);

        IStmt ex12=new CompStmt(c1,new CompStmt(c2,new CompStmt(c3,new CompStmt(c4,new CompStmt(c5,new CompStmt(c6,new CompStmt(c6,new CompStmt(c7,new CompStmt(c8,c9)))))))));

        /*IStmt ex11 = new CompStmt(new VarDeclStmt("v", new IntType()),
                new CompStmt(new VarDeclStmt("a", new RefType(new IntType())),
                        new CompStmt(new AssignStmt("v", new ValueExp(new IntValue(10))),
                                new CompStmt(new NewStmt("a", new ValueExp(new IntValue(22))),
                                        new CompStmt(new Fork(new CompStmt(new WriteHStmt("a", new ValueExp(new IntValue(30))), new CompStmt(new AssignStmt("v", new ValueExp(new IntValue(32))), new CompStmt(new PrintStmt(new VarExpr("v")), new PrintStmt(new ReadH(new VarExpr("a"))))))),
                                                new CompStmt(new PrintStmt(new VarExpr("v")), new PrintStmt(new ReadH(new VarExpr("a")))))))));
*/
        stmt= new ArrayList<>(Arrays.asList(ex1, ex2, ex3, ex4, ex5, ex6, ex7, ex8, ex9, ex10,ex11,ex12));

    }
}
